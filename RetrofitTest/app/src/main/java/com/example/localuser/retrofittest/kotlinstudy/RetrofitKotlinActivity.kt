package com.example.localuser.retrofittest.kotlinstudy

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.localuser.retrofittest.R
import com.example.localuser.retrofittest.base.AsmFunction
import com.example.localuser.retrofittest.base.BaseActivity
import kotlin.getValue

/**
 * RetrofitKotlinActivity - Kotlin Retrofit + MVVM 示例
 * 使用 ViewModel + Repository 架构
 */
@AsmFunction(functionName = "retrofit_kotlin_demo", visible = true)
class RetrofitKotlinActivity : BaseActivity() {

    companion object {
        private const val TAG = "RetrofitKotlinActivity"
    }

    // 使用 viewModels() 创建 ViewModel
    private val viewModel: RetrofitViewModel by viewModels()

    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_kotlin)

        resultTextView = findViewById(R.id.result_text)
        val getPostBtn: Button = findViewById(R.id.btn_get_post)
        val getAllPostsBtn: Button = findViewById(R.id.btn_get_all_posts)

        // 观察单个 Post 数据 - 显式指定 lambda 参数类型
        viewModel.post.observe(this) { state: RetrofitViewModel.PostState ->
            when (state) {
                is RetrofitViewModel.PostState.Loading -> {
                    resultTextView.text = "正在请求..."
                }
                is RetrofitViewModel.PostState.Success -> {
                    val post = state.post
                    resultTextView.text = """
                        请求成功！
                        
                        ID: ${post.id}
                        UserID: ${post.userId}
                        Title: ${post.title}
                        Body: ${post.body}
                    """.trimIndent()
                    Log.d(TAG, "getPost success: $post")
                    showLogToPage(TAG, "GET单个Post成功: ${post.id}")
                }
                is RetrofitViewModel.PostState.Error -> {
                    resultTextView.text = "请求失败: ${state.message}"
                    Log.e(TAG, "getPost error: ${state.message}")
                }
            }
        }

        // 观察所有 Posts 数据 - 显式指定 lambda 参数类型
        viewModel.posts.observe(this) { state: RetrofitViewModel.PostsState ->
            when (state) {
                is RetrofitViewModel.PostsState.Loading -> {
                    resultTextView.text = "正在请求..."
                }
                is RetrofitViewModel.PostsState.Success -> {
                    val posts = state.posts
                    resultTextView.text = """
                        请求成功！
                        
                        共获取 ${posts.size} 条数据
                        
                        前3条数据：
                        ${posts.take(3).joinToString("\n\n") { 
                            "ID: ${it.id}\nTitle: ${it.title.take(50)}..." 
                        }}
                    """.trimIndent()
                    Log.d(TAG, "getAllPosts success: ${posts.size} items")
                    showLogToPage(TAG, "GET所有Posts成功: ${posts.size} 条")
                }
                is RetrofitViewModel.PostsState.Error -> {
                    resultTextView.text = "请求失败: ${state.message}"
                    Log.e(TAG, "getAllPosts error: ${state.message}")
                }
            }
        }

        // 获取单个 Post
        getPostBtn.setOnClickListener {
            viewModel.getPost(1)
        }

        // 获取所有 Posts
        getAllPostsBtn.setOnClickListener {
            viewModel.getAllPosts()
        }

        showLogToPage(TAG, "RetrofitKotlinActivity 已打开")
    }
}
