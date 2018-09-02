package com.example.localuser.retrofittest.JobSchedulerTest;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyJobSchedulerService extends Service {
    //private String TAG = JobSchedulerTestActivity.TAG_PREFIX+getClass().getSimpleName();
    private String TAG = "MyService";
    private JobScheduler mJobScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        if(intent == null)
        {
            Log.d(TAG,"intent = null");
        }else {
            Log.d(TAG, "intent = " + intent.toString());
        }
//        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(),getClass().getName()))
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                .setPersisted(true)
////                .setOverrideDeadline(60*1000)
//                .setPeriodic(20*1000);
//        mJobScheduler.schedule(builder.build());
        //stopSelf();
//        return START_STICKY;
        return START_REDELIVER_INTENT;
    }

//    @Override
//    public boolean onStartJob(JobParameters params) {
//        Log.d(TAG,"onStartJob");
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        Log.d(TAG,"onStopJob");
//        return false;
//    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy-begin");
        super.onDestroy();
        Log.d(TAG,"onDestroy-end");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
