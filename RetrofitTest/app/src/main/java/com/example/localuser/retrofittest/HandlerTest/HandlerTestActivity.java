package com.example.localuser.retrofittest.HandlerTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class HandlerTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "handlertest--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        test2();
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        test3();
    }

    public void test1()
    {
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"");
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.d(TAG,"begin loop");
                Handler handler1 = new Handler();
                handler1.sendEmptyMessage(0);
                //Toast.makeText(HandlerTestActivity.this, "test", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.d(TAG,"after loop");
            }
        }).start();
    }

    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"msg.what"+msg.what);
            switch (msg.what)
            {
                case 0:
                    sendEmptyMessage(0);
                    break;
                 default:
                    break;
            }
        }
    };
    public void test2()
    {
        mHandler.sendEmptyMessage(0);
    }

    public void test3()
    {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"runnable running");
            }
        });
        Log.d(TAG,"write a log");
    }
}
