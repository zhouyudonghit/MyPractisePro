package com.example.localuser.retrofittest.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 基础Activity，提供通用方法、布局加载、视图初始化和日志输出
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName() + "--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayoutId());
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    /**
     * 获取布局资源ID，子类可重写此方法返回自定义布局
     *
     * @return 默认返回 activity_common 布局
     */
    protected int getResLayoutId() {
        return com.example.localuser.retrofittest.R.layout.activity_common;
    }

    /**
     * 初始化视图：获取布局中的 TextView 和 Button 并绑定点击事件
     */
    private void initView() {
        TextView textView = findViewById(com.example.localuser.retrofittest.R.id.text);
        if (textView != null) {
            textView.setOnClickListener(v -> onTextClicked(textView));
        }

        Button button = findViewById(com.example.localuser.retrofittest.R.id.button);
        if (button != null) {
            button.setOnClickListener(v -> onButtonClicked(button));
        }
    }

    /**
     * TextView 点击事件回调，子类重写处理 TextView 的点击逻辑
     *
     * @param textView 被点击的 TextView
     */
    protected void onTextClicked(@NonNull TextView textView) {
        Log.d(TAG, "TextView clicked");
    }

    /**
     * Button 点击事件回调，子类重写处理 Button 的点击逻辑
     *
     * @param button 被点击的 Button
     */
    protected void onButtonClicked(@NonNull Button button) {
        Log.d(TAG, "Button clicked");
    }

    /**
     * 初始化数据，由子类重写
     */
    protected void initData() {
        // 子类可重写此方法进行数据初始化
    }

    /**
     * 显示Toast消息
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 输出日志到页面（可以重写实现UI显示）
     */
    protected void showLogToPage(String tag, String msg) {
        Log.d(tag, msg);
    }
}
