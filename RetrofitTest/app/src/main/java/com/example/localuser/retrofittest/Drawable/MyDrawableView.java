package com.example.localuser.retrofittest.Drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class MyDrawableView extends android.support.v7.widget.AppCompatImageView {
    private String TAG = LogConfigs.TAG_PREFIX_DRAWABLE_TEST +getClass().getSimpleName();
    public MyDrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure()");
        Log.d(TAG,""+getMeasuredWidth()+","+getMeasuredHeight());
        Log.e(TAG,"",new Exception());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw()");
    }
}
