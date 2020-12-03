package com.example.localuser.retrofittest.MotionEventTest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by localuser on 2018/4/26.
 */

public class MyViewGroup extends LinearLayout {
    private String TAG = MotionEventTestActivity.TAG_PREFIX+getClass().getSimpleName();
    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent"+ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent"+ev.toString());
//        switch (ev.getAction())
//        {
//            case MotionEvent.ACTION_MOVE:
//                return true;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent"+event.toString());
        boolean handled = super.onTouchEvent(event);
        Log.d(TAG,"onTouchEvent return "+handled);
        return false;
    }
}
