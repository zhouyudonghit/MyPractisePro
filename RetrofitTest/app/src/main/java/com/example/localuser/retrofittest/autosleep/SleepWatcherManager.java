package com.example.localuser.retrofittest.autosleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MyApplication;
import com.example.localuser.retrofittest.SharedPreferenceTest.SPUtils;
import com.example.localuser.retrofittest.Utils.DateUtil;
import com.example.localuser.retrofittest.Utils.SpUtil;

import org.greenrobot.eventbus.EventBus;
import java.util.Date;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class SleepWatcherManager implements ISleepWatcherManager{
    private static volatile SleepWatcherManager instance;
    private String TAG = LogConfigs.TAG_PREFIX_SLEEP_WATCHER_TEST + getClass().getSimpleName();
    private static final String KEY_SLEEP_BEGIN_TIME = "sleep_begin_time";
    private Context mContext;
    public static float MOVE_THRESHOLD_X_Y = 1;
    public static float MOVE_THRESHOLD_Z = 3;
    private SensorManager sensorManager;
    private float[] lastValue;
    private ScreenStateBroadcastReceiver receiver;
    private Date sleepBeginTime;
    private Date sleepEndTime;
    private Date defaultSleepEndTime;
    private long lastReportTime;
    private boolean isWorking;
    private Date mStartWorkDate;
    private boolean isStartedYesterday;


    public class ScreenStateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "ScreenBroadcastReceiver --> ACTION_SCREEN_ON");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "ScreenBroadcastReceiver --> ACTION_SCREEN_OFF");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d(TAG, "ScreenBroadcastReceiver --> ACTION_USER_PRESENT");
                    updateSleepTime();
                    EventBus.getDefault().post(new ScreenEvent());
                }
            }
        }
    }

    private SleepWatcherManager()
    {
        mContext = MyApplication.getInstance();
        sensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
    }

    public static SleepWatcherManager getInstance()
    {
        if(instance == null)
        {
            synchronized (SleepWatcherManager.class)
            {
                if(instance == null)
                {
                    instance = new SleepWatcherManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void startWork() {
        Log.d(TAG,"startWork()");
        isWorking = true;
        EventBus.getDefault().post(new StartWatchingSleepEvent());
        mStartWorkDate = new Date();
        if(SleepWatcherService.IS_CROSS_DAY) {
            if (mStartWorkDate.getTime() >= SleepWatcherService.getDefaultSleepBeginTime(mStartWorkDate)) {
                //晚上21:00之后开始开始开始工作
                isStartedYesterday = true;
                defaultSleepEndTime = new Date(SleepWatcherService.getDefaultSleepEndTime(mStartWorkDate) + 24 * 3600 * 1000L);
            } else {
                isStartedYesterday = false;
                defaultSleepEndTime = new Date(SleepWatcherService.getDefaultSleepEndTime(mStartWorkDate));
            }
        }else{
            isStartedYesterday = false;
            defaultSleepEndTime = new Date(SleepWatcherService.getDefaultSleepEndTime(mStartWorkDate));
        }
        long lastSleepBeginTime = SpUtil.readPreferencesLong(mContext,KEY_SLEEP_BEGIN_TIME,0);
        if(lastSleepBeginTime != 0 && mStartWorkDate.getTime() >= lastSleepBeginTime && mStartWorkDate.getTime() < defaultSleepEndTime.getTime())
        {
            Log.d(TAG,"restart work,process may be killed during one sleep");
            sleepBeginTime = new Date(lastSleepBeginTime);
        }else{
            sleepBeginTime = mStartWorkDate;
        }
        Log.d(TAG,"sleepBeginTime = "+sleepBeginTime+",defaultSleepEndTime = "+defaultSleepEndTime+",isStartedYesterday = "+isStartedYesterday+",lastSleepBeginTime = "+lastSleepBeginTime);
        updateSleepTime();
        registerBroadcastReceiver();
        registerAccelerometer();
        //        registerLightSensor();
    }

    @Override
    public void stopWork(boolean needNextAlarm) {
        Log.d(TAG,"stopWork,needNextAlarm = "+needNextAlarm+",isWorking = "+isWorking);
        if(isWorking) {
            sensorManager.unregisterListener(accelerometerListener);
//        sensorManager.unregisterListener(lightListener);
            mContext.unregisterReceiver(receiver);
            if(needNextAlarm)
            {
                EventBus.getDefault().post(new EndSleepEvent());
                //发送通知SleepWatchService续订AlarmManager
                EventBus.getDefault().post(new SetNextSleepAlarmEvent());
            }
            SpUtil.savePreferencesLong(mContext,KEY_SLEEP_BEGIN_TIME,0);
            isWorking = false;
        }
        resetData();
    }

    private void resetData()
    {
        mStartWorkDate = null;
        isStartedYesterday = false;
        sleepBeginTime = null;
        sleepEndTime = null;
        isStartedYesterday = false;
        lastReportTime = 0;
        lastValue = null;
        defaultSleepEndTime = null;
    }

    SensorEventListener accelerometerListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if(lastReportTime == 0) {
                    lastReportTime = new Date().getTime();
                    isMove(event.values);
                }else {
                    long now = new Date().getTime();
                    if(now - lastReportTime >= 1000)
                    {
                        if(isMove(event.values))
                        {
                            lastReportTime = now;
                        }
                    }
                }
//                if (a_x > 14 || a_y > 14 || a_z > 14) {
//                    Toast.makeText(SleepWatcherService.this, "摇一摇成功",
//                            Toast.LENGTH_LONG).show();
//                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void registerBroadcastReceiver()
    {
        receiver = new ScreenStateBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(receiver,filter);
    }

    private void registerAccelerometer()
    {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAccelerometerAvaliable = sensorManager.registerListener(accelerometerListener, sensor, SensorManager.SENSOR_DELAY_UI);
        if(isAccelerometerAvaliable)
        {
            Log.d(TAG,"Accelerometer is available");
        }else{
            Log.d(TAG,"Accelerometer is not available");
        }
    }

    SensorEventListener lightListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
//            float light = event.values[0];
//            Log.d(TAG,"light value = "+light);
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                float light = event.values[0];
//                Log.d(TAG,"light value = "+light);
                EventBus.getDefault().post(new LightValueEvent(light));
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void registerLightSensor()
    {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        boolean isAvailable = sensorManager.registerListener(lightListener,sensor,SensorManager.SENSOR_DELAY_UI);
        if(isAvailable)
        {
            Log.d(TAG,"light is available");
        }else{
            Log.d(TAG,"light is not available");
        }
    }

    private boolean isMove(float[] currentValues)
    {
        boolean moved = true;
        float a_x = currentValues[0];
        float a_y = currentValues[1];
        float a_z = currentValues[2];
        if(lastValue == null)
        {
            lastValue = new float[3];
            lastValue[0] = a_x;
            lastValue[1] = a_y;
            lastValue[2] = a_z;
            return false;
        }
//        Log.d(TAG,"a_x = "+a_x+",a_y = "+a_y+",a_z = "+a_z);
        float changeX = Math.abs(lastValue[0]-a_x);
        float changeY = Math.abs(lastValue[1]-a_y);
        float changeZ = Math.abs(lastValue[2]-a_z);
        lastValue[0] = a_x;
        lastValue[1] = a_y;
        lastValue[2] = a_z;
        if(changeX < MOVE_THRESHOLD_X_Y && changeY < MOVE_THRESHOLD_X_Y && changeZ < MOVE_THRESHOLD_Z)
        {
            moved = false;
        }else {
            Log.d(TAG,"moved");
            //如果检测到手机在动，时间更新
            updateSleepTime();
        }
        return moved;
    }

    public long getSleepTime()
    {
        Log.d(TAG,"sleepEndTime = "+sleepEndTime+",sleepBeginTime = "+sleepBeginTime);
        return sleepEndTime.getTime() - sleepBeginTime.getTime();
    }

    public long getSleepBeginTime()
    {
        return sleepBeginTime.getTime();
    }

    public long getSleepEndTime()
    {
        return sleepEndTime.getTime();
    }

    private void updateSleepTime()
    {
        if(!isWorking)
        {
            return;
        }
        Date currentDate = new Date();
        Log.d(TAG,"updateSleepTime(),isStartedYesterday = "+isStartedYesterday);
        if(isStartedYesterday)
        {
            if(currentDate.getTime() <= getBeginTimeLimit())
            {
                sleepBeginTime = currentDate;
                SpUtil.savePreferencesLong(mContext,KEY_SLEEP_BEGIN_TIME,sleepBeginTime.getTime());
            }
        }
        if(currentDate.getTime() >= defaultSleepEndTime.getTime())
        {
            sleepEndTime = currentDate;
            //监测到默认起床时间后的第一次使用手机，判定用户睡觉结束
            Log.d(TAG,"sleepBeginTime = "+sleepBeginTime + ",sleepEndTime = "+sleepEndTime);
            stopWork(true);
        }
    }

    //统计睡眠开始时间的截止时间，比如每天的23::59:59.999
    private long getBeginTimeLimit()
    {
        return DateUtil.clearDateForWeekMonthData(mStartWorkDate).getTime() + 24*3600*1000L - 1;
    }
}
