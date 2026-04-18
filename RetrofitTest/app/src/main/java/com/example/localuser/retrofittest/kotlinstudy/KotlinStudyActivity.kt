package com.example.localuser.retrofittest.kotlinstudy

import android.os.Bundle
import android.util.Log
import com.example.localuser.retrofittest.R
import com.example.localuser.retrofittest.base.AsmFunction
import com.example.localuser.retrofittest.base.BaseActivity

/**
 * KotlinStudyActivity - Kotlin学习功能测试
 * 使用 Kotlin 实现，继承 BaseActivity
 */
@AsmFunction(functionName = "koltin_study", visible = true)
class KotlinStudyActivity : BaseActivity() {
    
    companion object {
        private const val TAG = "KotlinStudyActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        Log.d(TAG, "KotlinStudyActivity onCreate")
        showLogToPage(TAG, "KotlinStudyActivity 功能页面 - 使用 Kotlin 开发")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "KotlinStudyActivity onDestroy")
    }
}
