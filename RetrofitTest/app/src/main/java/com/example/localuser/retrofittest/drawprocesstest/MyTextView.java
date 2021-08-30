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
        Log.d(TAG,"onMeasure"+hashCode());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG,"onLayout"+hashCode());
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw()"+hashCode()+",height = "+getHeight());
//        Log.e(TAG,"draw()",new Exception());
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG,"dispatchDraw()"+hashCode());
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw"+hashCode());
        super.onDraw(canvas);
    }
}
