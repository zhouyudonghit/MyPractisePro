package com.example.localuser.retrofittest;

import android.app.Application;

import com.longzhu.msg.MsgConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MsgConfig.instance().config(MsgConfig.DEVICE_ID, "3")
                .config(MsgConfig.APPID,getPackageName())
                .config(MsgConfig.VERSION_ID, BuildConfig.VERSION_NAME);
    }
}
