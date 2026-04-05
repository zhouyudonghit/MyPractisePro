package com.example.localuser.retrofittest.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity，提供通用方法和日志输出
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
