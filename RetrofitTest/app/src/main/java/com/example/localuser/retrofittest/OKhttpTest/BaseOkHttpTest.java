package com.example.localuser.retrofittest.OKhttpTest;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public abstract class BaseOkHttpTest {
    protected String TAG = LogConfigs.TAG_PREFIX_OKHTTP+getClass().getSimpleName();
    protected OkHttpClient mOkHttpClient;

    public BaseOkHttpTest()
    {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
    }
}
