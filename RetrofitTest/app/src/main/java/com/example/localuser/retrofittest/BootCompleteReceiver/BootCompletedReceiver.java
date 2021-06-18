package com.example.localuser.retrofittest.BootCompleteReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class BootCompletedReceiver extends BroadcastReceiver {
    private String TAG = LogConfigs.TAG_PREFIX_BROADCAST_TEST + getClass().getSimpleName();
    public static final String ACTION = "test";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"receive boot complete broadcast");
    }
}
