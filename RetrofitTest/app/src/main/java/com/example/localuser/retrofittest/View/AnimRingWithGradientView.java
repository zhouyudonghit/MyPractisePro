package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.Utils.AppUtils;

/**
 * 圆环，带有动画和渐变色
 */
public class AnimRingWithGradientView extends View {
    private String TAG = LogConfigs.TAG_PREFIX_DRAWABLE_TEST + getClass().getSimpleName();
    private static final int ANIM_DURATION = 3*1000;
    private int mRingStrokeWidth;
    private Paint mRingPaint;
    private Paint mAnimPaint;
    private int mStartColor;
    private int mEndColor;
    private int mDefaultRingColor;
    private boolean reverse;
    private float mStartAngle;
    private float mSweepAngle;
    private int mDrawingStartColor;
    private int mDrawingEndColor;

    public AnimRingWithGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint()
    {
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mRingStrokeWidth);
        mRingPaint.setColor(mDefaultRingColor);
        mAnimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnimPaint.setStyle(Paint.Style.STROKE);
        mAnimPaint.setStrokeWidth(mRingStrokeWidth);
        mAnimPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        //先画默认的白色圆环
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG,"onDraw,width = "+width+",height = "+height);
        float cx = (width)/2;
        float cy = (height)/2;
        float radius = (width - mRingStrokeWidth)/2;
//        canvas.drawCircle(cx,cy,radius,mRingPaint);

        //画彩色渐变圆弧
        canvas.save();
        canvas.rotate(-90,cx,cy);
        double radians = Math.asin((mRingStrokeWidth/2.0)/(cx - (mRingStrokeWidth/2.0)));
        int degree = (int) Math.toDegrees(radians);
//        SweepGradient gradient = new SweepGradient(cx,cy, mDrawingStartColor,mDrawingEndColor);
        SweepGradient gradient = new SweepGradient(cx,cy, mStartColor,mEndColor);
        mAnimPaint.setShader(gradient);
        canvas.drawArc(mRingStrokeWidth/2,mRingStrokeWidth/2,width-mRingStrokeWidth/2,height-mRingStrokeWidth/2,mStartAngle+degree,mSweepAngle,false,mAnimPaint);
        canvas.restore();
    }

    public void startForwardAnim()
    {
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.d(TAG,"interpolatedTime = "+interpolatedTime);
                mSweepAngle = interpolatedTime * 360;
                mDrawingEndColor = getGradientColor(interpolatedTime);
                invalidate();
            }
        };
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                reverse = false;
                mStartAngle = 0;
                mDrawingStartColor = mStartColor;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startReverseAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.setRepeatCount(1);
        startAnimation(anim);
    }

    private void startReverseAnim()
    {
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.d(TAG,"interpolatedTime = "+interpolatedTime+",reverse = "+reverse);
            }
        };
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                reverse = false;
                mDrawingEndColor = mStartColor;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startForwardAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.setRepeatCount(1);
        startAnimation(anim);
    }

    public int getRingStrokeWidth() {
        return mRingStrokeWidth;
    }

    public void setRingStrokeWidth(int ringStrokeWidth) {
        this.mRingStrokeWidth = AppUtils.dip2px(getContext(),ringStrokeWidth);
        initPaint();
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setStartColor(int mStartColor) {
        this.mStartColor = mStartColor;
    }

    public int getEndColor() {
        return mEndColor;
    }

    public void setEndColor(int mEndColor) {
        this.mEndColor = mEndColor;
    }

    public int getDefaultRingColor() {
        return mDefaultRingColor;
    }

    public void setDefaultRingColor(int mDefaultRingColor) {
        this.mDefaultRingColor = mDefaultRingColor;
    }

    /**
     * 计算两个颜色之间的一个渐变色
     * @return
     */
    public int getGradientColor(float ratio)
    {
        float R = Color.red(mStartColor) + (Color.red(mEndColor) - Color.red(mStartColor)) * ratio;
        float G = Color.green(mStartColor) + (Color.green(mEndColor) - Color.green(mStartColor)) * ratio;
        float B = Color.blue(mStartColor) + (Color.blue(mEndColor) - Color.blue(mStartColor)) * ratio;

        int cl = Color.argb(255,(int)R,(int)G,(int)B);
        return cl;
    }
}
