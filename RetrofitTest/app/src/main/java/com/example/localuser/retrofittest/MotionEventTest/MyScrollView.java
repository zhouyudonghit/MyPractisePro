package com.example.localuser.retrofittest.MotionEventTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private String TAG = MotionEventTestActivity.TAG_PREFIX+getClass().getSimpleName();
    private int mTouchSlop;
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent,"+ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent,"+ev.toString());
        Log.d(TAG,"SCROLL_AXIS_VERTICAL = "+SCROLL_AXIS_VERTICAL);
        Log.d(TAG,"configuration.getScaledTouchSlop() = " + mTouchSlop);
        Log.d(TAG,"getNestedScrollAxes() = "+getNestedScrollAxes());
        Log.d(TAG,"getScrollY() = " + getScrollY());
        boolean result = super.onInterceptTouchEvent(ev);
        Log.d(TAG,"onInterceptTouchEvent,return "+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onTouchEvent,"+ev.toString());
        boolean result = super.onTouchEvent(ev);
        Log.d(TAG,"onTouchEvent,return = "+result);
        return result;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        if (scrollY <= 0) {
//            isScrolledToTop = clampedY;
//            isScrolledToBottom = false;
//        } else {
//            isScrolledToTop = false;
//            isScrolledToBottom = clampedY;
//        }
//        notifyScrollChangedListeners();
    }
}
