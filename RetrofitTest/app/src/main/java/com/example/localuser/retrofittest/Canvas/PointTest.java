package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PointTest extends BaseTest {
    public PointTest(View view) {
        super(view);
    }

    public void drawPoint(Canvas canvas)
    {
        mPaint.setColor(Color.RED);
        //针对画点来说，Cap.BUTT和Cap.SQUARE效果一样
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        //此处设置宽度，则是圆点的直径，方点的宽度
        mPaint.setStrokeWidth(100);
        //好像有点像 FILL 模式下的 drawCircle() 和 drawRect() ？事实上确实是这样的，它们和 drawPoint() 的绘制效果没有区别
        canvas.drawPoint(mViewWidth/2,mViewHeight/2,mPaint);
    }

    public void drawPoints(Canvas canvas)
    {
        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        mPaint.setColor(Color.RED);
        //针对画点来说，Cap.BUTT和Cap.SQUARE效果一样
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        //此处设置宽度，则是圆点的直径，方点的宽度
        mPaint.setStrokeWidth(20);
        canvas.drawPoints(points,mPaint);
    }
}