package com.example.localuser.retrofittest.aroutertest.providers

import com.alibaba.android.arouter.facade.template.IProvider


interface ITestProvider: IProvider {
    fun test()
    suspend fun  test2()
}