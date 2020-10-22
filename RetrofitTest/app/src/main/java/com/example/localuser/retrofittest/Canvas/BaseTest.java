package com.example.localuser.retrofittest.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public abstract class BaseTest {
    protected String TAG = LogConfigs.TAG_PREFIX_CANVAS+getClass().getSimpleName();
    protected View mView;
    protected int mViewWidth;
    protected int mViewHeight;
    protected Paint mPaint;

    public BaseTest(View view)
    {
        mView = view;
        mViewHeight = mView.getHeight();
        mViewWidth = mView.getWidth();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }
}
