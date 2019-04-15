package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TextTest extends BaseTest {
    public TextTest(View view) {
        super(view);
    }

    public void drawText(Canvas canvas)
    {
        mPaint.setTextSize(80);
        canvas.drawText("gello world",mViewWidth/2,mViewHeight/2,mPaint);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(mViewWidth/2,mViewHeight/2,mPaint);
    }
}
