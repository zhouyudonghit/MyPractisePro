package com.example.localuser.retrofittest.aissetest.model

/**
 * 聊天消息（UI 展示 + 请求体通用）
 */
data class ChatMessage(
    val role: String,      // "user" / "assistant" / "system"
    val content: String
)

/**
 * 智谱大模型请求体
 */
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val stream: Boolean
)

/**
 * SSE 流式响应 chunk（增量数据）
 * 格式: data: {"id":"xxx","choices":[{"delta":{"content":"你"}}]}
 */
data class ChatChunk(
    val id: String?,
    val choices: List<ChunkChoice>?
)

data class ChunkChoice(
    val delta: ChunkDelta?
)

data class ChunkDelta(
    val role: String?,
    val content: String?
)

/**
 * 非流式响应（stream=false 时返回的完整 JSON）
 */
data class ChatCompletion(
    val id: String?,
    val choices: List<CompletionChoice>?
)

data class CompletionChoice(
    val message: ResponseMessage?,
    val finish_reason: String?
)

data class ResponseMessage(
    val role: String?,
    val content: String?
)

/**
 * SSE 流事件（抛给 ViewModel/UI 层）
 */
sealed class StreamEvent {
    /** 增量文本，id 为智谱返回的 completion_id */
    data class Delta(val completionId: String, val content: String) : StreamEvent()

    /** 流结束（收到 data: [DONE]） */
    object Done : StreamEvent()

    /** 流错误 */
    data class Error(val error: Throwable) : StreamEvent()
}
