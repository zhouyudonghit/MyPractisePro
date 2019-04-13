package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class OvalTest extends BaseTest{
    public OvalTest(View view) {
        super(view);
    }

    public void drawOval(Canvas canvas)
    {
        mPaint.setColor(Color.RED);
        canvas.drawOval(0,0,mViewWidth,mViewHeight,mPaint);
    }

    //绘制弧形或者扇形
    public void drawArc1(Canvas canvas)
    {
        canvas.drawArc(100,200,700,600,0,45,false,mPaint);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(100,200,700,600,45,45,true,mPaint);
    }
}
