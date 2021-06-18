package com.example.localuser.retrofittest.imageviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.localuser.retrofittest.Utils.ScreenUtils;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {
    private String TAG = getClass().getSimpleName();
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"MeasureSpec.getSize(heightMeasureSpec) = "+MeasureSpec.getSize(heightMeasureSpec));
        Log.d(TAG,"MeasureSpec.getMode(heightMeasureSpec) = "+ ScreenUtils.getMeasureSpecMode(heightMeasureSpec));
        Log.d(TAG,"getDrawable().getIntrinsicHeight() = "+getDrawable().getIntrinsicHeight());
        Log.d(TAG,"getAdjustViewBounds() = "+getAdjustViewBounds());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"getMeasuredHeight() = "+getMeasuredHeight());

    }
}
