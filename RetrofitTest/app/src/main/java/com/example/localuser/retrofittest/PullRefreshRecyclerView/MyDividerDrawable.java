package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyDividerDrawable extends Drawable{
    private Paint mPaint;

    public MyDividerDrawable()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(100);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawLine(getBounds().left,getBounds().top,getBounds().right,getBounds().bottom,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return 40;
    }
}
