package com.example.localuser.retrofittest.autosleep;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.localuser.retrofittest.Bluetooth.Events.BaseEvent;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.DateUtil;
import com.example.localuser.retrofittest.Utils.SpUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SleepWatcherTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_SLEEP_WATCHER_TEST + getClass().getSimpleName();
    private Button mStartButton;
    private Button mEndButton;
    private TextView mLightTextView;
    private TextView mAccelerometerTextView;
    private TextView mStartTextView;
    private TextView mReallyStartTextView;
    private TextView mEndTextView;
    private TextView mSleepTextView;
    private RecyclerView mRecylerView;
    private List<SleepEvent> datas = new ArrayList<>();
    private EventAdapter mAdapter;
    private SleepWatcherService mService;
    private ServiceConnection mServiceConnection;
    private boolean serviceBinded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sleep_wathcher_test_activity);
        mStartButton = findViewById(R.id.start_service);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startService();
            }
        });
        mStartButton.setClickable(false);
        mEndButton = findViewById(R.id.stop_service);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serviceBinded) {
//                    stopService();
                }
            }
        });
        mEndButton.setClickable(false);
        mLightTextView = findViewById(R.id.light_value);
        mLightTextView.setVisibility(View.GONE);
        mAccelerometerTextView = findViewById(R.id.accelerometer);
        mStartTextView = findViewById(R.id.start_sleep_time);
        mReallyStartTextView = findViewById(R.id.really_sleep_begin_time);
        mEndTextView = findViewById(R.id.end_sleep_time);
        mSleepTextView = findViewById(R.id.sleep_time);
        mRecylerView = findViewById(R.id.recycler_view);
        mAdapter = new EventAdapter(datas);
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        EventBus.getDefault().register(this);
        startService();
    }

    private void startService()
    {
        datas = new ArrayList<>();
        Intent intent = new Intent(this,SleepWatcherService.class);
        mStartTextView.setText("开始睡眠时间：");
        mStartButton.setText("");
        mEndButton.setText("");
        mEndTextView.setText("");
        mSleepTextView.setText("");
        mReallyStartTextView.setText("");
        if(mServiceConnection  == null) {
            mServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mService = ((SleepWatcherService.SleepWatcherServiceBinder) service).getService();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        startService(new Intent(this,SleepWatcherService.class));
//        if (bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)) {
//            serviceBinded = true;
//        }
    }

    private void stopService()
    {
        if (serviceBinded) {
            unbindService(mServiceConnection);
            serviceBinded = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(SleepEvent event){
        if(event instanceof LightValueEvent) {
            mLightTextView.setText("光照强度 : " + ((LightValueEvent)event).lightValue);
        }else if(event instanceof AccelerometerChangedEvent)
        {
            if(datas == null)
            {
                datas = new ArrayList<>();
            }
            mReallyStartTextView.setText("最后一次使用手机时间："+DateUtil.format(SleepWatcherManager.getInstance().getSleepBeginTime(),DateUtil.DATE_PATTERN_1));
            if(datas.size() >= 301) {
                datas.remove(0);
            }
            datas.add((AccelerometerChangedEvent) event);
            mAdapter.updateDatas(datas);
            mRecylerView.scrollToPosition(datas.size() - 1);
            saveDataToSp();
        }else if(event instanceof ScreenEvent)
        {
            mReallyStartTextView.setText("最后一次使用手机时间："+DateUtil.format(SleepWatcherManager.getInstance().getSleepBeginTime(),DateUtil.DATE_PATTERN_1));
        }else if(event instanceof StartWatchingSleepEvent)
        {
            mStartButton.setText("开始监测睡觉了");
        }else if(event instanceof EndSleepEvent){
            mEndButton.setText("睡眠结束");
            mStartButton.setText("");
            mStartTextView.setText("开始睡眠时间："+DateUtil.format(SleepWatcherManager.getInstance().getSleepBeginTime(), DateUtil.DATE_PATTERN_1));
            mEndTextView.setText("结束睡眠时间：" + DateUtil.format(SleepWatcherManager.getInstance().getSleepEndTime(), DateUtil.DATE_PATTERN_1));
            long sleepTimeSecondes = SleepWatcherManager.getInstance().getSleepTime()/1000;
            long hour = sleepTimeSecondes/3600;
            long minute = (sleepTimeSecondes%3600)/60;
            long secondes = (sleepTimeSecondes%3600)%60;
            mSleepTextView.setText("真实睡眠时长：" + hour + "时"+minute+"分"+secondes+"秒");
            mReallyStartTextView.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy();
//        stopService();
//        if(serviceBinded) {
//            unbindService(mServiceConnection);
//            serviceBinded = false;
//        }
        EventBus.getDefault().unregister(this);
        datas = null;
    }

    private static final String KEY_SLEEP_DATA = "key_sleep_data";
    private void saveDataToSp()
    {
        if(datas != null && datas.size()%3 == 1)
        {
            SpUtil.savePreferencesString(this,KEY_SLEEP_DATA+DateUtil.format(new Date().getTime(),DateUtil.DATE_PATTERN_2),new Gson().toJson(datas));
        }
    }
}
