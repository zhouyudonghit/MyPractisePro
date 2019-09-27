package com.example.localuser.retrofittest;

import android.app.Application;

public class MyApplication extends Application {
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getInstance()
    {
        return mApplication;
    }
}
