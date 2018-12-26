package com.example.localuser.retrofittest.TimerTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTestActivity extends AppCompatActivity{
    private String TAG = "TimerTest--";
    Timer timer;
    TimerTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_test_main);
        timer = new Timer();
        //timer.schedule(task,0,300);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv1,tv2;
        tv1 = findViewById(R.id.start);
        tv2 = findViewById(R.id.stop);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new TimerTask() {
                    @Override
                    public void run() {
                        Log.d(TAG,"run");
                    }
                };
                timer.schedule(task,0,300);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    timer.cancel();
                }catch (Exception e)
                {
                    Log.d(TAG,e.getLocalizedMessage());
                }
            }
        });
    }
}
