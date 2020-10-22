package com.example.localuser.retrofittest.JobSchedulerTest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.localuser.retrofittest.Utils.AppUtils;

public class MyService extends Service {
    private String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        if(!AppUtils.isServiceWork(this, MyJobSchedulerService.class.getName()))
        {
            this.startService(new Intent(this,MyJobSchedulerService.class));
        }
    }
}
