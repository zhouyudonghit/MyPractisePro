package com.example.localuser.retrofittest.MotionEventTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/25.
 */

public class MotionEventTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "MotionEventTes--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private MyFragment myFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.layout_motionevent_activity_main);
        myFragment = MyFragment.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,myFragment).commit();
        final MyView myView = (MyView) findViewById(R.id.MyView1);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"myView onClick");
                myView.requestLayout();
            }
        });
        MyViewGroup myViewGroup = findViewById(R.id.MyViewGroup);
        myViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"myViewGroup onClick");
            }
        });
//        myView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop()");
        super.onStop();
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
