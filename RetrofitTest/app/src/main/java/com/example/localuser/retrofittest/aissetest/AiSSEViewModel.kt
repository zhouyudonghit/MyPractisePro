package com.example.localuser.retrofittest.aissetest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localuser.retrofittest.aissetest.db.ChatDatabase
import com.example.localuser.retrofittest.aissetest.db.MessageEntity
import com.example.localuser.retrofittest.aissetest.model.ChatMessage
import com.example.localuser.retrofittest.aissetest.model.ChatRequest
import com.example.localuser.retrofittest.aissetest.model.StreamEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

/** AI SSE 流式对话 ViewModel：StateFlow 抛流式文本、多轮历史、Room 持久化、可手动停止 */
class AiSSEViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ChatDatabase.getInstance(application).messageDao()
    private var streamJob: Job? = null

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isStreaming = MutableStateFlow(false)
    val isStreaming: StateFlow<Boolean> = _isStreaming

    init {
        // 只加载一次历史（避免 Room Flow 持续发射，覆盖流式过程中的内存状态）
        viewModelScope.launch {
            _messages.value = dao.getAllMessages().first()
                .map { ChatMessage(it.role, it.content) }
        }
    }

    /** 发送消息；每轮重新生成 completionId */
    fun sendMessage(text: String) {
        if (text.isBlank() || _isStreaming.value) return
        val completionId = UUID.randomUUID().toString().replace("-", "")
        val history = _messages.value.filter { it.content.isNotBlank() }
        val userMsg = ChatMessage("user", text)
        _messages.value = _messages.value + userMsg + ChatMessage("assistant", "")
        _isStreaming.value = true
        viewModelScope.launch {
            dao.insert(MessageEntity(role = "user", content = text,
                completionId = completionId, createTime = System.currentTimeMillis()))
        }
        val request = ChatRequest("glm-4-flash", history + userMsg, true)
        streamJob = SSEClient.stream(request)
            .flowOn(Dispatchers.IO)   // 子线程读流，禁止阻塞主线程
            .onEach { event ->
                when (event) {
                    is StreamEvent.Delta -> appendDelta(event.content)
                    StreamEvent.Done -> saveAiMessage(completionId, null)
                    is StreamEvent.Error -> saveAiMessage(completionId, event.error)
                }
            }
            .launchIn(viewModelScope)
    }

    /** 手动停止：取消 Job -> awaitClose 里 call.cancel() */
    fun stopStreaming() {
        streamJob?.cancel()
        streamJob = null
        _isStreaming.value = false
    }

    /**
     * 非流式对话：stream=false，等待完整回答后一次性展示
     * 消息流程与流式一致（历史上下文 + Room 持久化），只是不做增量拼接
     */
    fun sendNonStream(text: String) {
        if (text.isBlank() || _isStreaming.value) return
        val completionId = UUID.randomUUID().toString().replace("-", "")
        val history = _messages.value.filter { it.content.isNotBlank() }
        val userMsg = ChatMessage("user", text)
        _messages.value = _messages.value + userMsg + ChatMessage("assistant", "")
        _isStreaming.value = true   // 复用发送/停止按钮的禁用状态
        viewModelScope.launch {
            dao.insert(MessageEntity(role = "user", content = text,
                completionId = completionId, createTime = System.currentTimeMillis()))
        }
        viewModelScope.launch {
            SSEClient.chatOnce(ChatRequest("glm-4-flash", history + userMsg, false))
                .onSuccess { reply ->
                    replaceLastAssistant(reply)
                    dao.insert(MessageEntity(role = "assistant", content = reply,
                        completionId = completionId, createTime = System.currentTimeMillis()))
                }
                .onFailure { e -> replaceLastAssistant("[错误] ${e.message}") }
            _isStreaming.value = false
        }
    }

    /** 把最后一条 assistant 消息替换为完整内容 */
    private fun replaceLastAssistant(content: String) {
        val list = _messages.value.toMutableList()
        if (list.isNotEmpty() && list.last().role == "assistant") {
            list[list.lastIndex] = list.last().copy(content = content)
            _messages.value = list
        }
    }

    private fun appendDelta(delta: String) {
        val list = _messages.value.toMutableList()
        android.util.Log.d("AiSSE--", "appendDelta收到: [$delta], lastRole=${list.lastOrNull()?.role}")
        if (list.isNotEmpty() && list.last().role == "assistant") {
            list[list.lastIndex] = list.last().copy(content = list.last().content + delta)
            _messages.value = list
        }
    }

    /** 流结束：AI 回复存 Room */
    private fun saveAiMessage(completionId: String, error: Throwable?) {
        _isStreaming.value = false
        val last = _messages.value.lastOrNull() ?: return
        if (last.role != "assistant") return
        val content = if (error != null && last.content.isEmpty()) "[错误] ${error.message}" else last.content
        viewModelScope.launch {
            dao.insert(MessageEntity(role = "assistant", content = content,
                completionId = completionId, createTime = System.currentTimeMillis()))
        }
    }
}
