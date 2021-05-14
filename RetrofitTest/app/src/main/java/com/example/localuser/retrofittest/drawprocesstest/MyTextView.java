package com.example.localuser.retrofittest.drawprocesstest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import com.example.localuser.retrofittest.Configs.LogConfigs;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private String TAG = LogConfigs.TAG_PREFIX_DRAW_PROCESS_TEST + getClass().getSimpleName();

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure"+this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout"+this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw"+this);
    }
}
