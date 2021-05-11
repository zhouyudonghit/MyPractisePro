package com.example.localuser.retrofittest.autosleep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.DefaultDatabaseErrorHandler;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.SerializeTest.D;
import com.example.localuser.retrofittest.Utils.DateUtil;
import com.example.localuser.retrofittest.textviewtest.TextViewTestActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

public class SleepWatcherService extends Service{
    private String TAG = LogConfigs.TAG_PREFIX_SLEEP_WATCHER_TEST + getClass().getSimpleName();
    //默认开始睡觉时间的小时值
    private static final int DEFAULT_BEGIN_SLEEP_TIME_HOUR = 21;
    //默认开始睡觉时间的分钟值
    private static final int DEFAULT_BEGIN_SLEEP_TIME_MINUTE = 0;
    //默认结束睡觉时间的小时值
    private static final int DEFAULT_END_SLEEP_TIME_HOUR = 6;
    //默认结束睡觉时间的分钟值
    private static final int DEFAULT_END_SLEEP_TIME_MINUTE = 0;
    //默认的睡眠开始时间和结束时间是否跨天
    public static final boolean IS_CROSS_DAY = true;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SleepWatcherWorkBroadcastReceiver mReceiver;

    public class SleepWatcherServiceBinder extends Binder implements ISleepWatcherServiceBinder{
        @Override
        public SleepWatcherService getService() {
            return SleepWatcherService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBInd");
        return new SleepWatcherServiceBinder();
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate()");
        super.onCreate();
        EventBus.getDefault().register(this);
        mReceiver = new SleepWatcherWorkBroadcastReceiver();
        //AlarmManager的广播好像动态注册才能生效
        registerReceiver(mReceiver,new IntentFilter(SleepWatcherWorkBroadcastReceiver.SLEEP_WATCHER_WORKER_BROADCAST_FILTER));
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(SleepWatcherWorkBroadcastReceiver.SLEEP_WATCHER_WORKER_BROADCAST_FILTER);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        Intent intent = new Intent(this, TextViewTestActivity.class);
//        pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        setAlarm();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return START_STICKY;
    }

    private void setAlarm()
    {
        Date date = new Date();
        if(shouldWorkImmediately(date))
        {
            //当前时间是21:00以后或者6:00之前，不用再设置闹钟，立即工作即可
            SleepWatcherManager.getInstance().startWork();
            return;
        }

        long nextTime = getNextAlarmTime(date);
        Log.d(TAG,"nextTime = "+DateUtil.format(nextTime,DateUtil.DATE_PATTERN_1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG,"alarmManager.setExactAndAllowWhileIdle");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG,"alarmManager.setExact");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
        } else {
            Log.d(TAG,"alarmManager.set");
            alarmManager.set(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
        }
    }

    private boolean shouldWorkImmediately(Date date)
    {
        if(IS_CROSS_DAY)
        {
            if(date.getTime() >= getDefaultSleepBeginTime(date) || date.getTime() < getDefaultSleepEndTime(date))
            {
                return true;
            }else{
                return false;
            }
        }else{
            if(date.getTime() >= getDefaultSleepBeginTime(date) && date.getTime() < getDefaultSleepEndTime(date))
            {
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d(TAG,"unbindService");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SleepWatcherManager.getInstance().stopWork(false);
        alarmManager.cancel(pendingIntent);
        unregisterReceiver(mReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(SleepEvent event){
        if(event instanceof SetNextSleepAlarmEvent)
        {
            setAlarm();
        }
    }

    private long getNextAlarmTime(Date currentDate)
    {
        return getDefaultSleepBeginTime(currentDate);
        //for test，10秒之后
//        return System.currentTimeMillis() + 24L*3600*1000;
    }

    public static long getDefaultSleepBeginTime(Date dayDate)
    {
        return DateUtil.clearDateForWeekMonthData(dayDate).getTime() + DEFAULT_BEGIN_SLEEP_TIME_HOUR*3600*1000L + DEFAULT_BEGIN_SLEEP_TIME_MINUTE * 60 * 1000;
    }

    public static long getDefaultSleepEndTime(Date dayDate)
    {
        return DateUtil.clearDateForWeekMonthData(dayDate).getTime() + DEFAULT_END_SLEEP_TIME_HOUR*3600*1000L + DEFAULT_END_SLEEP_TIME_MINUTE * 60 * 1000;
    }
}
