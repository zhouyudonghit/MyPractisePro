package com.example.localuser.retrofittest.SuningPushTets;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessageService extends Service {
    private String TAG = SuningPushTestActivity.TAG_PREFIX+getClass().getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind,intent = "+intent.toString());
        MyBinder binder = new MyBinder();
        return binder;
    }

    public class MyBinder extends Binder
    {
        public MessageService getService(){
            return MessageService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"oncreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    public void registerMessageCallback(IMessageCallback callback)
    {

    }
}
