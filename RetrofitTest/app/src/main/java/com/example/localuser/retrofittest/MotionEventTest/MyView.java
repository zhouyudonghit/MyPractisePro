package com.example.localuser.retrofittest.MotionEventTest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by localuser on 2018/4/26.
 */

public class MyView extends View {
    private String TAG = MotionEventTestActivity.TAG_PREFIX+getClass().getSimpleName();
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG,"dispatchTouchEvent"+event.toString());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent"+event.toString());
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return super.onTouchEvent(event);
    }
}
