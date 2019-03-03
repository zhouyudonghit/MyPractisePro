package com.example.localuser.retrofittest.OKhttpTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class OKHttpTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_OKHTTP+getClass().getSimpleName();
    private OkHttpTest mOkHttpTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_main);
        mOkHttpTest = new OkHttpTest();
        mOkHttpTest.test2();
    }
}
