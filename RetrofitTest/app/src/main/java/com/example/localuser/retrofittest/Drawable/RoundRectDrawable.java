package com.example.localuser.retrofittest.Drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RoundRectDrawable extends Drawable {
    private float mRadius;
    private Paint mPaint;
    private boolean mGradient;//是否渐变
    private boolean mVertical;//是否是竖直方向渐变
    private int mStartColor;
    private int mEndColor;

    public RoundRectDrawable(float radius,int startColor)
    {
        mRadius = radius;
        mStartColor = startColor;
        mEndColor = startColor;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * @param radius radius单位为px
     */
    public RoundRectDrawable(float radius, boolean gradient,boolean vertical,int startColor,int endColor)
    {
        mRadius = radius;
        mGradient = gradient;
        mVertical = vertical;
        mStartColor = startColor;
        mEndColor = endColor;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {
        if(mGradient)
        {
            int[] colors = new int[]{mStartColor,mEndColor};
            float[] positions = new float[]{0,1};
            if(mVertical) {
                LinearGradient linearGradient = new LinearGradient(getBounds().left,getBounds().top,getBounds().left,getBounds().bottom,colors,positions, Shader.TileMode.CLAMP);
                mPaint.setShader(linearGradient);
            }else{
                LinearGradient linearGradient = new LinearGradient(getBounds().left,getBounds().top,getBounds().right,getBounds().top,colors,positions, Shader.TileMode.CLAMP);
                mPaint.setShader(linearGradient);
            }
        }else{
            mPaint.setColor(mStartColor);
        }
        canvas.drawRoundRect(getBounds().left,getBounds().top,getBounds().right,getBounds().bottom,mRadius,mRadius,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
