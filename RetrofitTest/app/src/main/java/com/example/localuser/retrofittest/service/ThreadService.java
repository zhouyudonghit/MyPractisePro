package com.example.localuser.retrofittest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ThreadService extends Service {
    private String TAG = ServiceTestActivity.TAG_PREFIX+getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"oncreate, threadId = "+Thread.currentThread().getId());
    }
}
