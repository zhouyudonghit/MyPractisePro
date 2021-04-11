package com.example.localuser.retrofittest.HandlerTest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.example.localuser.retrofittest.Configs.LogConfigs;

public class HandlerTest {
    private String TAG = LogConfigs.TAG_PREFIX_HANDLER+getClass().getSimpleName();
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"handleMessage");
            try {
                Thread.sleep(5*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"sleep over");
        }
    };

    public void test()
    {
        mHandler.sendEmptyMessage(0);
        mHandler.sendEmptyMessage(0);
        mHandler.sendEmptyMessage(0);
        mHandler.sendEmptyMessage(0);
        mHandler.sendEmptyMessage(0);
        Log.d(TAG,"message send over");
    }

    public void test2()
    {
//        Looper.myQueue().
    }
}
