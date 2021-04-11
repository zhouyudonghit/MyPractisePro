package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Exercise extends BaseTest{
    public Exercise(View view) {
        super(view);
    }

    public void exercise1(Canvas canvas)
    {
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mViewWidth/4,mViewHeight/4,90,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeWidth(5);
        canvas.drawCircle(mViewWidth/4*3,mViewHeight/4,90,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mViewWidth/4,mViewHeight/4*3,90,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mViewWidth/4*3,mViewHeight/4*3,90,mPaint);
    }
}
