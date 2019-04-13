package com.example.localuser.retrofittest.MemoryLeak;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.localuser.retrofittest.R;


public class MemoryLeakActivity extends AppCompatActivity {
    private static String TAG_PREFIX = "memory-leak";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private Activity mActivity = this;
    private char[] mChars = new char[10*1024*1024];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak_test_main);
        Log.d(TAG,"onCreate");
        test(new ICallback(){

            @Override
            public void test() {
                Log.d(TAG,"test");
                if(mActivity != null)
                {
                    Log.d(TAG,"activity is not null,mactivity = " + mActivity);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    public void test(final ICallback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200000);
                    Log.d(TAG,"sleep over");
                    callback.test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
