package com.example.localuser.retrofittest.TimerTest;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.PullRefreshListView.Configs;

public class CountDownTimerTest {
    private String TAG = LogConfigs.TAG_PREFIX_TIMER+getClass().getSimpleName();

    public void test()
    {
        CountDownTimer timer = new CountDownTimer(20*1000,2*1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"on tick,millisUntilFinished = "+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.d(TAG,"onFinish()");
            }
        };
        timer.start();
    }
}
