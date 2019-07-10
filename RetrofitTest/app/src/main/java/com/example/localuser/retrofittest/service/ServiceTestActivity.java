package com.example.localuser.retrofittest.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.KeyEventDispatcher;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.aidl.IBookManager;
import com.example.localuser.retrofittest.MainActivity;

public class ServiceTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "ServiceTest---";
    private String TAG = TAG_PREFIX + getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"oncreate,threadId = "+Thread.currentThread().getId());
        Log.d("activityB","oncreate");
//        test();
        testRemoteService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activityB","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activityB","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activityB","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activityB","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activityB","onStop");
        unbindService(remoteServiceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activityB","onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("test","haha");
        super.onSaveInstanceState(outState);
        Log.d("activityB","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("activityB","onRestoreInstanceState");
        if(savedInstanceState != null)
        {
            Log.d("activityB",savedInstanceState.getString("test"));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG,"onConfigurationChanged");
    }

    private BindService mBindService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected");
            BindService.MyBinder myBinder = (BindService.MyBinder) service;
            mBindService = myBinder.getService();
            if(mBindService != null)
            {
                for(int i = 0;i< 10;i++)
                {
                    Log.d(TAG,mBindService.getNumber()+"");
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void test()
    {
        Intent intent = new Intent(this,BindService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        if(mBindService != null)
        {
            for(int i = 0;i< 10;i++)
            {
                Log.d(TAG,mBindService.getNumber()+"");
            }
        }
    }

    private ServiceConnection remoteServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"remoteServiceConnection onServiceConnected");
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void testRemoteService()
    {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.example.yudongzhou.remoteservice","com.example.yudongzhou.remoteservice.MyRemoteService");
        intent.setComponent(componentName);
        bindService(intent,remoteServiceConnection,BIND_AUTO_CREATE);
    }
}
