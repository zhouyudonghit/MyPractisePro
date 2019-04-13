package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class CircleTest extends BaseTest{
    public CircleTest(View view) {
        super(view);
    }

    public void drawRing1(Canvas canvas)
    {
        //FILL_AND_STROKE和FILL效果差不多
        mPaint.setStyle(Paint.Style.STROKE);
        //这个40貌似就是环的宽度，转换成px
        int ringWidth = 40;
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setColor(Color.RED);
        int radiu = Math.min(mViewHeight,mViewWidth)/2;
        //画圆环的方法，如果圆环宽度为width，传入的参数半径为radiu，那么实际上从圆心到圆环外边的长度为width/2+radiu
        //知道这个就知道怎么定半径长度了
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,radiu-ringWidth/2,mPaint);
    }

    public void drawRing2(Canvas canvas)
    {
        Path mPath = new Path();
        int radiu = Math.min(mViewHeight,mViewWidth)/2;
        int ringWidth = 40;
        mPath.addCircle(mViewWidth/2,mViewHeight/2,radiu-ringWidth,Path.Direction.CW);
        mPath.close();
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath,mPaint);
    }
}
