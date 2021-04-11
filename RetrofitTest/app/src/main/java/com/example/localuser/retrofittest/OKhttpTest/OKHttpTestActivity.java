package com.example.localuser.retrofittest.OKhttpTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.OKhttpTest.SNNetConfig.SuningNetTest;
import com.example.localuser.retrofittest.R;

import java.util.LinkedHashMap;

public class OKHttpTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_OKHTTP+getClass().getSimpleName();
    private BaseOkHttpTest mOkHttpTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_main);
//        mOkHttpTest = new OkHttpTestPost();
//        ((OkHttpTestPost) mOkHttpTest).testPostKeyValue();
//        mOkHttpTest = new OkHttpTestGet(false);
//        ((OkHttpTestGet) mOkHttpTest).test3();
        mOkHttpTest = new SuningNetTest(false);
        ((SuningNetTest) mOkHttpTest).test1();
    }
}
