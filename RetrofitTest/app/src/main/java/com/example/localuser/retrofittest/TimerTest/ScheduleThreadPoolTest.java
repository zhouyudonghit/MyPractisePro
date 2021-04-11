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

    public void test2()
    {
        Log.d(TAG,"test2()");
        //指定时延后开始执行任务，以后每隔period的时长再次执行该任务
//        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG,"run1(),thread = "+Thread.currentThread().getId());
//            }
//        },1,2,TimeUnit.SECONDS);
        //指定时延后开始执行任务，以后任务执行完成后等待delay时长，再次执行任务
        //自己亲测，下面代码确实是间隔5秒执行一次
        mScheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"run2(),thread = "+Thread.currentThread().getId());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1,2,TimeUnit.SECONDS);
    }
}
