package com.example.localuser.retrofittest.aroutertest

import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.localuser.retrofittest.R
import com.example.localuser.retrofittest.aroutertest.providers.ITestProvider
import com.example.localuser.retrofittest.base.BaseActivity
@Route(path = "/aroutertest/second")
class ARouterSecondActivity : BaseActivity() {

    @Autowired(name = "from")
    @JvmField
    var from: String? = null

    @Autowired
    lateinit var testService: ITestProvider

    override fun getResLayoutId(): Int = R.layout.activity_arouter_second

    override fun initData() {
        // 必须调用 inject 才能注入 @Autowired 参数和服务（否则 lateinit 未初始化崩溃）
        ARouter.getInstance().inject(this)

        findViewById<TextView>(R.id.tv_route_info).text =
            "路由路径: /aroutertest/second\n来源参数 from: ${from ?: "无"}"
        testService.test()
    }

    override fun onTextClicked(textView: TextView) {
        test()
    }


    fun test() {
        test1()
        test2()
    }

    fun test1() {
        Log.d(TAG,"test1")
        val testService: ITestProvider = ARouter.getInstance().navigation(ITestProvider::class.java)
        testService.test()
    }

    fun test2() {
        Log.d(TAG,"test2")
        val testService: ITestProvider = ARouter.getInstance()
            .build("/aroutertest/itestprovider")   // 路径必须以 / 开头且至少两级
            .navigation() as ITestProvider
        testService.test()
    }
}