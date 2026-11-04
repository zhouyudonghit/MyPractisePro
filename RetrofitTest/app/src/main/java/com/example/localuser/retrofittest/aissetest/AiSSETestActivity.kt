package com.example.localuser.retrofittest.aissetest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localuser.retrofittest.R
import com.example.localuser.retrofittest.base.AsmFunction
import com.example.localuser.retrofittest.base.BaseActivity
import kotlinx.coroutines.launch

@AsmFunction(functionName = "ai_sse_test", visible = true)
class AiSSETestActivity : BaseActivity() {

    private val viewModel: AiSSEViewModel by viewModels()
    private val adapter = ChatAdapter()

    override fun getResLayoutId(): Int = R.layout.activity_ai_sse_test

    override fun initData() {
        val recycler = findViewById<RecyclerView>(R.id.recycler_messages)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val editInput = findViewById<EditText>(R.id.edit_input)
        val btnSend = findViewById<Button>(R.id.btn_send)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnSend.setOnClickListener {
            val text = editInput.text.toString().trim()
            if (text.isNotEmpty()) {
                editInput.text.clear()
                viewModel.sendMessage(text)
            }
        }
        // 非流式发送：等待完整回答后一次性展示
        findViewById<Button>(R.id.btn_send_ns).setOnClickListener {
            val text = editInput.text.toString().trim()
            if (text.isNotEmpty()) {
                editInput.text.clear()
                viewModel.sendNonStream(text)
            }
        }
        btnStop.setOnClickListener { viewModel.stopStreaming() }

        // 观察消息列表：流式增量到达时刷新并滚动到底部
        lifecycleScope.launch {
            viewModel.messages.collect { list ->
                android.util.Log.d("AiSSE--", "UI收到: size=${list.size}, lastContent=[${list.lastOrNull()?.content?.take(30)}]")
                adapter.submit(list)
                if (list.isNotEmpty()) recycler.scrollToPosition(list.size - 1)
            }
        }

        // 观察流式状态：切换 发送/停止 按钮
        lifecycleScope.launch {
            viewModel.isStreaming.collect { streaming ->
                btnSend.isEnabled = !streaming
                btnStop.isEnabled = streaming
            }
        }
    }
}