package com.example.localuser.retrofittest.service;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.localuser.retrofittest.MainActivity;

public class ServiceTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "ServiceTest---";
    private String TAG = TAG_PREFIX + getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"oncreate,threadId = "+Thread.currentThread().getId());
        Log.d("activityB","oncreate");
        new Thread(new Runnable()
        {

            @Override
            public void run() {
                Log.d(TAG,"new threadid = "+Thread.currentThread().getId());
                //Intent intent = new Intent(ServiceTestActivity.this,ThreadService.class);
                //startService(intent);
                if(Looper.myLooper() == null)
                {
                    //Looper.prepare();
                }
                Toast.makeText(ServiceTestActivity.this,"test",Toast.LENGTH_LONG).show();
                //Looper.loop();
            }
        }).start();
        if(savedInstanceState != null)
        {
            Log.d("activityB",savedInstanceState.getString("test"));
        }
        WindowManager mWM = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater.from(this);
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
        Toast.makeText(this,"1",Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"2",Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"3",Toast.LENGTH_LONG).show();

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


}
