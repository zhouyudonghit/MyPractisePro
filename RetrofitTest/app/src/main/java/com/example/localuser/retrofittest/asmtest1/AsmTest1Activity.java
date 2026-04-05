package com.example.localuser.retrofittest.asmtest1;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.base.AsmFunction;
import com.example.localuser.retrofittest.base.BaseActivity;

/**
 * AsmTest1Activity - ASM功能测试1
 */
@AsmFunction(functionName = "asmtest1", visible = true)
public class AsmTest1Activity extends BaseActivity {
    private static final String TAG = "AsmTest1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        Log.d(TAG, "AsmTest1Activity onCreate");
        showLogToPage(TAG, "AsmTest1Activity 功能页面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "AsmTest1Activity onDestroy");
    }
}
