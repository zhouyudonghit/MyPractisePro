package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.Map;

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

    public void testDrawTextBaseLineWithTextBound(Canvas canvas)
    {
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,80,mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(mViewWidth/2,0,mViewWidth/2,mViewHeight,mPaint);
        canvas.drawLine(0,mViewHeight/2,mViewWidth,mViewHeight/2,mPaint);
        String text = "9";
        mPaint.setTextSize(40);
        mPaint.setColor(Color.BLUE);
        Rect rect = new Rect();
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.getTextBounds(text,0,text.length(),rect);
        int textBoundWidth = rect.right - rect.left;
        int textBoundHeight = rect.bottom - rect.top;
        Log.d(TAG,"rect = "+rect);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        Log.d(TAG,"fm.ascent = "+fm.ascent+",fm.descent = "+fm.descent+",fm.top = "+fm.top+",fm.bottom = "+fm.bottom);
//        canvas.drawText(text, (mViewWidth - textBoundWidth) / 2-rect.left, mViewHeight / 2 - rect.top / 2, mPaint);
        canvas.drawText(text, (mViewWidth - textBoundWidth) / 2-rect.left, mViewHeight / 2 + textBoundHeight/2 - rect.bottom, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine((mViewWidth - textBoundWidth) / 2,0,(mViewWidth - textBoundWidth) / 2,mViewHeight,mPaint);
        canvas.drawLine(0,mViewHeight / 2 - textBoundHeight / 2 - rect.top,(mViewWidth ) / 2,mViewHeight / 2 - textBoundHeight / 2 - rect.top,mPaint);

//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.getTextBounds(text,0,text.length(),rect);
//        int textBoundWidth = rect.right - rect.left;
//        int textBoundHeight = rect.bottom - rect.top;
//        Log.d(TAG,"rect = "+rect);
//        canvas.drawRect(rect,mPaint);
//        canvas.drawText(text,(mViewWidth)/2,mViewHeight/2+textBoundHeight/2,mPaint);

//        mPaint.setTextAlign(Paint.Align.RIGHT);
//        mPaint.getTextBounds(text,0,text.length(),rect);
//        int textBoundWidth = rect.right - rect.left;
//        int textBoundHeight = rect.bottom - rect.top;
//        Log.d(TAG,"rect = "+rect);
//        canvas.drawRect(rect,mPaint);
////            canvas.drawText(text,(mViewWidth + textBoundWidth)/2-rect.left,mViewHeight/2+textBoundHeight/2-rect.bottom,mTextPaint);
//        canvas.drawText(text,(mViewWidth)/2,mViewHeight/2+textBoundHeight/2-rect.bottom,mPaint);

    }

    public void testDrawTextBaseLineWithFontMetrics(Canvas canvas)
    {
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,100,mPaint);
        int baselineY = mViewHeight/2;
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(1);
//        canvas.drawLine(mViewWidth/2,0,mViewWidth/2,mViewHeight,mPaint);
        canvas.drawLine(0,baselineY,mViewWidth,baselineY,mPaint);

        mPaint.setTextSize(120);
        mPaint.setColor(Color.BLUE);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        Log.d(TAG,"fm.ascent = "+fm.ascent+",fm.descent = "+fm.descent+",fm.top = "+fm.top+",fm.bottom = "+fm.bottom);
        String text = "abg";
        canvas.drawText(text,mViewWidth/2,baselineY,mPaint);
        mPaint.setColor(Color.GREEN);
        float top = baselineY + fm.top;
        float ascent = baselineY + fm.ascent;
        float dscent = baselineY + fm.descent;
        float bottom = baselineY + fm.bottom;
        canvas.drawLine(0,top,mViewWidth,top,mPaint);
        canvas.drawLine(0,ascent,mViewWidth,ascent,mPaint);
        canvas.drawLine(0,dscent,mViewWidth,dscent,mPaint);
        canvas.drawLine(0,bottom,mViewWidth,bottom,mPaint);

        float width = mPaint.measureText(text);
        Log.d(TAG,"mPaint.measureText(text) = "+width);

        Rect rect = new Rect();
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.getTextBounds(text,0,text.length(),rect);
        int textBoundWidth = rect.right - rect.left;
        int textBoundHeight = rect.bottom - rect.top;
        Log.d(TAG,"rect = "+rect);

        canvas.drawLine(mViewWidth/2,0,mViewWidth/2,mViewHeight,mPaint);
        canvas.drawLine(mViewWidth/2+width,0,mViewWidth/2+width,mViewHeight,mPaint);
        canvas.drawLine(mViewWidth/2+rect.left,0,mViewWidth/2+rect.left,mViewHeight,mPaint);
        canvas.drawLine(mViewWidth/2+textBoundWidth+rect.left,0,mViewWidth/2+textBoundWidth+rect.left,mViewHeight,mPaint);
        canvas.drawLine(0,baselineY+rect.bottom,mViewWidth,baselineY+rect.bottom,mPaint);
        canvas.drawLine(0,baselineY+rect.top,mViewWidth,baselineY+rect.top,mPaint);
    }
}
