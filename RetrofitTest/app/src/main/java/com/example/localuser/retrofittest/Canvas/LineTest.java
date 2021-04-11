package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class LineTest extends BaseTest {
    public LineTest(View view) {
        super(view);
    }

    public void drawLine1(Canvas canvas)
    {
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100,100,mViewWidth-100,mViewHeight-100,mPaint);
    }
}
