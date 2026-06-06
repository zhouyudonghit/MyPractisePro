package com.example.localuser.retrofittest.layouttest;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.localuser.retrofittest.base.AsmFunction;
import com.example.localuser.retrofittest.base.BaseActivity;

/**
 * 布局测试 Activity
 * 注意：TextView的ID必须为 layout_test_activity
 */
@AsmFunction(functionName = "layout_test", visible = true)
public class LayoutTestActivity extends BaseActivity {

    @Override
    protected void onTextClicked(@NonNull TextView textView) {
        Intent intent = new Intent(this, LinearLayoutTestActivity.class);
        startActivity(intent);
    }
}
