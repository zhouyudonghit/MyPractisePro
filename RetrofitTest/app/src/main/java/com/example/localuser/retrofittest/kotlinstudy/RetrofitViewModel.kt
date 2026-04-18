package com.example.localuser.retrofittest.kotlinstudy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * RetrofitViewModel - 负责 UI 状态管理
 * 处理业务逻辑，通过 Repository 获取数据
 */
class RetrofitViewModel : ViewModel() {

    private val repository = RetrofitRepository()

    // 单个 Post 状态
    private val _post = MutableLiveData<PostState>()
    val post: LiveData<PostState> = _post

    // 所有 Posts 状态
    private val _posts = MutableLiveData<PostsState>()
    val posts: LiveData<PostsState> = _posts

    // Post 状态类
    sealed class PostState {
        object Loading : PostState()
        data class Success(val post: RetrofitRepository.Post) : PostState()
        data class Error(val message: String) : PostState()
    }

    // Posts 列表状态类
    sealed class PostsState {
        object Loading : PostsState()
        data class Success(val posts: List<RetrofitRepository.Post>) : PostsState()
        data class Error(val message: String) : PostsState()
    }

    /**
     * 获取单个 Post
     */
    fun getPost(id: Int) {
        viewModelScope.launch {
            _post.value = PostState.Loading
            val result = repository.getPost(id)
            _post.value = if (result.isSuccess) {
                PostState.Success(result.getOrThrow())
            } else {
                PostState.Error(result.exceptionOrNull()?.message ?: "未知错误")
            }
        }
    }

    /**
     * 获取所有 Posts
     */
    fun getAllPosts() {
        viewModelScope.launch {
            _posts.value = PostsState.Loading
            val result = repository.getAllPosts()
            _posts.value = if (result.isSuccess) {
                PostsState.Success(result.getOrThrow())
            } else {
                PostsState.Error(result.exceptionOrNull()?.message ?: "未知错误")
            }
        }
    }
}
