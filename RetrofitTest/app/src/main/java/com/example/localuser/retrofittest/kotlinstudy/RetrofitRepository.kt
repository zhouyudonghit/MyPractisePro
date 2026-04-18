package com.example.localuser.retrofittest.kotlinstudy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * RetrofitRepository - 负责数据层
 * 处理所有网络请求逻辑
 */
class RetrofitRepository {

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    // 数据模型类
    data class Post(val userId: Int, val id: Int, val title: String, val body: String)

    // API 接口 - 使用 suspend 函数
    interface JsonPlaceholderApi {
        @GET("posts/{id}")
        suspend fun getPost(@Path("id") id: Int): Post

        @GET("posts")
        suspend fun getAllPosts(): List<Post>
    }

    // 单例 Retrofit 实例
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: JsonPlaceholderApi by lazy {
        retrofit.create(JsonPlaceholderApi::class.java)
    }

    /**
     * 获取单个 Post
     */
    suspend fun getPost(id: Int): Result<Post> = withContext(Dispatchers.IO) {
        try {
            val post = api.getPost(id)
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 获取所有 Posts
     */
    suspend fun getAllPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            val posts = api.getAllPosts()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
