package com.example.localuser.retrofittest.JobSchedulerTest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.R;

public class JobSchedulerTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "JobSchedulerTest--";
    Button stopServiceBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobscheduler_activity_main);
        final Intent intent = new Intent(this,MyJobSchedulerService.class);
        startService(intent);
        stopServiceBtn = (Button) findViewById(R.id.stop_service);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyService","onclick");
                stopService(intent);
            }
        });

        Handler mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable()
        {

            @Override
            public void run() {
                stopServiceBtn.setText("fei ui");
            }
        }).start();
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable()
                {

                    @Override
                    public void run() {
                        stopServiceBtn.setVisibility(View.GONE);
                    }
                }).start();
            }
        });
    }
}
