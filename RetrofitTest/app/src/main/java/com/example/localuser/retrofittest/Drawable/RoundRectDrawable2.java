package com.example.localuser.retrofittest.Drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 *
 */
public class RoundRectDrawable2 extends Drawable {
    private float mRadius;
    private Paint mPaint;
    private boolean mGradient;//是否渐变
    private boolean mVertical;//是否是竖直方向渐变
    private int mStartColor;
    private int mEndColor;
    private boolean mShowTopCorner = true;
    private boolean mShowBottomCorner = true;
    private float[] mRadii;

    public RoundRectDrawable2(float radius,int startColor)
    {
        mRadius = radius;
        mRadii = new float[]{mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius};
        mStartColor = startColor;
        mEndColor = startColor;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public RoundRectDrawable2(float leftTopRadius,float rightTopRadius,float leftBottomRadius,float rightBottomRadius,int startColor)
    {
//        mRadius = radius;
        mRadii = new float[]{leftTopRadius,leftTopRadius,rightTopRadius,rightTopRadius,rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius};
        mStartColor = startColor;
        mEndColor = startColor;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * @param radius radius单位为px
     */
    public RoundRectDrawable2(float radius, boolean gradient,boolean vertical,int startColor,int endColor)
    {
        mRadius = radius;
        mRadii = new float[]{mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius};
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
        RectF mRectF = new RectF();
        mRectF.set(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom);
        Path mRoundedRectPath = new Path();
        if(mShowTopCorner && mShowBottomCorner) {
            mRoundedRectPath.addRoundRect(mRectF, mRadii, Path.Direction.CW);
        }else{
            if(mShowTopCorner)
            {
                mRoundedRectPath.addRoundRect(mRectF, new float[]{mRadius,mRadius,mRadius,mRadius,0,0,0,0}, Path.Direction.CCW);
            }else{
                mRoundedRectPath.addRoundRect(mRectF, new float[]{0,0,0,0,mRadius,mRadius,mRadius,mRadius}, Path.Direction.CCW);
            }
        }
        canvas.clipPath(mRoundedRectPath);
        canvas.drawRect(getBounds().left,getBounds().top,getBounds().right,getBounds().bottom,mPaint);
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

    public boolean isShowTopCorner() {
        return mShowTopCorner;
    }

    public void setShowTopCorner(boolean showTopCorner) {
        this.mShowTopCorner = showTopCorner;
    }

    public boolean isShowBottomCorner() {
        return mShowBottomCorner;
    }

    public void setShowBottomCorner(boolean showBottomCorner) {
        this.mShowBottomCorner = showBottomCorner;
    }
}
