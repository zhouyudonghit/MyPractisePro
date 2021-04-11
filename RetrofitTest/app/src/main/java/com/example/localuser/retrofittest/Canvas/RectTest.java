package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class RectTest extends BaseTest{

    public RectTest(View view) {
        super(view);
    }

    public void drawRect1(Canvas canvas)
    {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        int lineWidth = 20;
        mPaint.setStrokeWidth(lineWidth);
        //此处画描边矩形，实现逻辑跟换圆环差不多，下面方法里面传的坐标参数并不是真正的画出来的矩形定点坐标，需要往外扩充变宽的一半。
        canvas.drawRect(20,20,mViewWidth-20,mViewHeight-20,mPaint);
    }

    public void drawRoundRect(Canvas canvas)
    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawRoundRect(100,100,700,700,50,50,mPaint);
    }
}
