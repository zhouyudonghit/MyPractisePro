package com.example.localuser.retrofittest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BindService extends Service {
    private String TAG = ServiceTestActivity.TAG_PREFIX + getClass().getSimpleName();
    private int number = 0;
    private MyBinder mBinder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        mBinder = new MyBinder();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG,"onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    public int getNumber()
    {
        return number++;
    }

    public class MyBinder extends Binder
    {
        public BindService getService()
        {
            return BindService.this;
        }
    }

}
