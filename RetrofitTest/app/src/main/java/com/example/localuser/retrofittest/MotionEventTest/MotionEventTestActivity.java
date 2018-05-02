package com.example.localuser.retrofittest.MotionEventTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/25.
 */

public class MotionEventTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "MotionEventTes--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_motionevent_activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent"+ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent"+event.toString());
        return super.onTouchEvent(event);
    }
}
