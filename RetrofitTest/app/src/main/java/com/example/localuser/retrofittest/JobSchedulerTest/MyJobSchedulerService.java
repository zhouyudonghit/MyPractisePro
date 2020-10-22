package com.example.localuser.retrofittest.JobSchedulerTest;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.localuser.retrofittest.Utils.AppUtils;

import java.util.List;

public class MyJobSchedulerService extends JobService {
    //private String TAG = JobSchedulerTestActivity.TAG_PREFIX+getClass().getSimpleName();
    private String TAG = "MyJobSchedulerService";
    private JobScheduler mJobScheduler;
    private int kJobId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
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
        scheduleJob(getJobInfo());
        //stopSelf();
        return START_NOT_STICKY;
//        return START_REDELIVER_INTENT;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"onStartJob");
        boolean isLocalServiceWork = AppUtils.isServiceWork(this, MyService.class.getName());
//        boolean isRemoteServiceWork = isServiceWork(this, "com.marswin89.marsdaemon.demo.Service2");
        if(!isLocalServiceWork){
            this.startService(new Intent(this,MyService.class));
//            this.startService(new Intent(this,Service2.class));
//            Toast.makeText(this, "进程启动", Toast.LENGTH_SHORT).show();
            Log.i("onStartJob", "启动service1");
        }
        mJobScheduler.cancel(kJobId);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"onStopJob");
        scheduleJob(getJobInfo());
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy-begin");
        super.onDestroy();
        Log.d(TAG,"onDestroy-end");
    }

//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }

    //将任务作业发送到作业调度中去
    public void scheduleJob(JobInfo t) {
        Log.i("MyJobDaemonService", "调度job");
        mJobScheduler.schedule(t);
    }

    public JobInfo getJobInfo(){
        JobInfo.Builder builder = new JobInfo.Builder(kJobId, new ComponentName(this.getPackageName(), MyJobSchedulerService.class.getName()));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setOverrideDeadline(5000);
        //间隔1000毫秒
//        builder.setPeriodic(1000);
        return builder.build();
    }

}
