package com.example.localuser.retrofittest.autosleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class SleepWatcherWorkBroadcastReceiver extends BroadcastReceiver {
    private String TAG = LogConfigs.TAG_PREFIX_SLEEP_WATCHER_TEST + getClass().getSimpleName();
    public static final String SLEEP_WATCHER_WORKER_BROADCAST_FILTER = "sleepwatcher.beginwork";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"receiver,intent = "+intent);
        if(SLEEP_WATCHER_WORKER_BROADCAST_FILTER.equals(intent.getAction())) {
            SleepWatcherManager.getInstance().startWork();
        }
    }
}
