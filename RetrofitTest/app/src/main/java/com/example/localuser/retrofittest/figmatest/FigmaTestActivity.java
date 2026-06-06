package com.example.localuser.retrofittest.figmatest;

import android.os.Bundle;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.base.AsmFunction;
import com.example.localuser.retrofittest.base.BaseActivity;

/**
 * FigmaTestActivity - Figma 测试功能
 * 注意：TextView的ID必须为 figma_test_activity
 */
@AsmFunction(functionName = "figma_test", visible = true)
public class FigmaTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figma_test);
    }
}
