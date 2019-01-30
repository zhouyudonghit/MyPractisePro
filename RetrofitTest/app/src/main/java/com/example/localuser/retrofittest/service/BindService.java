package com.example.localuser.retrofittest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BindService extends Service {
    private int number = 0;
    private MyBinder mBinder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MyBinder();
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
