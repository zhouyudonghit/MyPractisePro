package com.example.localuser.retrofittest.TimerTest;

import android.app.usage.ConfigurationStats;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest {
    private String TAG = LogConfigs.TAG_PREFIX_TIMER+getClass().getSimpleName();
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    public ScheduleThreadPoolTest()
    {
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
    }

    public void test()
    {
        Log.d(TAG,"test()");
        mScheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"run1(),thread = "+Thread.currentThread().getId());
            }
        },2,TimeUnit.SECONDS);

        mScheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"run2(),thread = "+Thread.currentThread().getId());
            }
        },2,TimeUnit.SECONDS);
    }
}
