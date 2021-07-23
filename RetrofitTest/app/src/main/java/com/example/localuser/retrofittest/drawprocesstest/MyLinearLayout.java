package com.example.localuser.retrofittest.drawprocesstest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class MyLinearLayout extends LinearLayout {
    private String TAG = LogConfigs.TAG_PREFIX_DRAW_PROCESS_TEST + getClass().getSimpleName();

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure"+hashCode());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG,"onLayout"+hashCode());
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw"+hashCode());
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw()");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Exception e = new Exception();
        Log.e(TAG,"",e);
        Log.d(TAG,"dispatchDraw()");
        super.dispatchDraw(canvas);
    }
}
