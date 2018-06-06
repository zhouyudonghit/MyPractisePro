package com.example.localuser.retrofittest.Drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyDrawable extends Drawable{
    private String TAG = DrawableActivity.TAG_PREX+getClass().getSimpleName();
    private Paint mPaint;

    public MyDrawable()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.d("TAG","draw()");
        canvas.drawLine(0,0,300,0,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
