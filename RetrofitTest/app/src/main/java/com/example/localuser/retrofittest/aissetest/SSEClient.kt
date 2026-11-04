package com.example.localuser.retrofittest.aissetest

import android.util.Log
import com.example.localuser.retrofittest.aissetest.model.ChatChunk
import com.example.localuser.retrofittest.aissetest.model.ChatCompletion
import com.example.localuser.retrofittest.aissetest.model.ChatRequest
import com.example.localuser.retrofittest.aissetest.model.StreamEvent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * SSE 流式请求客户端
 *
 * 标准做法：OkHttp + Response.body().source() 逐行读取字节流，
 * 解析 "data:" 行，遇到 "data: [DONE]" 结束。
 * 不使用第三方 SSE 库。
 */
object SSEClient {
    private var TAG = javaClass.simpleName + "--"

    /** 智谱大模型 SSE 接口 */
    private const val API_URL = "https://open.bigmodel.cn/api/paas/v4/chat/completions"

    // API Key 来自 local.properties -> BuildConfig.ZHIPU_API_KEY（不进版本库）
    const val API_KEY = com.example.localuser.retrofittest.BuildConfig.ZHIPU_API_KEY

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        // 流式读取不能设置读超时，否则长回答会被掐断
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private val gson = Gson()

    /**
     * 发起流式对话请求，返回事件流（IO 线程读取，配合 flowOn 使用）
     */
    fun stream(request: ChatRequest): Flow<StreamEvent> = callbackFlow {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
        val body = RequestBody.create(mediaType, gson.toJson(request))
        val httpRequest = Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Accept", "text/event-stream")
            .post(body)
            .build()

        val call = client.newCall(httpRequest)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (!call.isCanceled()) {
                    trySend(StreamEvent.Error(e))
                }
                close()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { resp ->
                    try {
                        // 注意：不能在这里用 body.string() 打印响应体，string() 会消费掉整个流，
                        // 导致后面 source() 读不到任何数据（SSE 流只能读一次！）
                        Log.d(TAG, "HTTP ${resp.code}, isSuccessful=${resp.isSuccessful}")
                        if (!resp.isSuccessful) {
                            throw IOException("HTTP ${resp.code}: ${resp.body?.string()}")
                        }
                        val source = resp.body?.source() ?: throw IOException("响应体为空")

                        // ===== 逐行读取，解析 SSE =====
                        while (true) {
                            // readUtf8Line: okio 内部维护缓冲区，自动拼接被 TCP 分片
                            // 拆到两个 buffer 的字节，直到读到完整一行（\n）
                            // —— 这就是"缓冲区拼接"，手写 read(buffer) 最容易踩的坑
                            val line = source.readUtf8Line() ?: break

                            if (line.isBlank()) continue          // SSE 事件间有空行
                            if (!line.startsWith("data:")) continue

                            val data = line.removePrefix("data:").trim()
                            if (data == "[DONE]") break            // 流结束标记

                            val chunk = gson.fromJson(data, ChatChunk::class.java)
                            val content = chunk.choices
                                ?.firstOrNull()?.delta?.content ?: continue

                            android.util.Log.d("AiSSE--", "SSE解析到delta: [$content]")
                            trySend(
                                StreamEvent.Delta(
                                    completionId = chunk.id ?: "",
                                    content = content
                                )
                            )
                        }
                        trySend(StreamEvent.Done)
                    } catch (e: Exception) {
                        if (!call.isCanceled()) {
                            trySend(StreamEvent.Error(e))
                        }
                    } finally {
                        close()
                    }
                }
            }
        })

        // Flow 被取消（如手动停止）时，自动关闭 OkHttp 请求
        awaitClose { call.cancel() }
    }

    /**
     * 非流式对话：stream=false，接口一次性返回完整 JSON
     * 适合不需要打字机效果的场景，代码更简单
     */
    suspend fun chatOnce(request: ChatRequest): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
                val body = RequestBody.create(mediaType, gson.toJson(request.copy(stream = false)))
                val httpRequest = Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .post(body)
                    .build()

                client.newCall(httpRequest).execute().use { resp ->
                    if (!resp.isSuccessful) {
                        return@withContext Result.failure(
                            IOException("HTTP ${resp.code}: ${resp.body?.string()}")
                        )
                    }
                    // 非流式模式下 body 只读一次，string() 是正确用法
                    val json = resp.body?.string()
                        ?: return@withContext Result.failure(IOException("响应体为空"))

                    val completion = gson.fromJson(json, ChatCompletion::class.java)
                    val content = completion.choices
                        ?.firstOrNull()?.message?.content
                        ?: return@withContext Result.failure(IOException("响应解析为空"))

                    Result.success(content)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
