package com.example.localuser.retrofittest.aroutertest

import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.localuser.retrofittest.base.AsmFunction
import com.example.localuser.retrofittest.base.BaseActivity

/**
 * ARouter 路由测试 Activity
 * - @Route: ARouter 路由注册，可通过 ARouter.getInstance().build(path).navigation() 跳转
 * - @AsmFunction: MainActivity 功能入口自动注册
 */
@Route(path = "/aroutertest/main")
@AsmFunction(functionName = "arouter_test", visible = true)
class ARouterTestActivity : BaseActivity() {

    override fun onTextClicked(textView: TextView) {
        // 通过 ARouter 路由跳转到二级页面（无需 import 目标类，按路径跳转）
        ARouter.getInstance()
            .build("/aroutertest/second")
            .withString("from", "ARouterTestActivity")
            .navigation()
    }
}