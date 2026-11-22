package com.example.localuser.retrofittest.aroutertest.providers

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = "/aroutertest/itestprovider")
class TestImpl : ITestProvider {
    private val TAG = javaClass.simpleName + "--"

    override fun test() {
        Log.d(TAG,"test")
    }

    override suspend fun test2() {
        Log.d(TAG, "test2")
    }

    override fun init(context: Context?) {
        Log.d(TAG, "init")
    }
}