package com.example.localuser.retrofittest.AnimatorTest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class PressAnimCircleView extends AppCompatTextView {
    private String TAG = LogConfigs.TAG_PREFIX_ANIM_TEST + getClass().getSimpleName();
    public static int STAGE_IDLE = 0;//无动画状态
    public static int STAGE_CIRLCE_SCALE_ANIM = 1;//外层圆环的缩放动画；
    public static int STAGE_ARC_PROGRESS_ANIM = 2;//环形进度条动画；
    public static int PERIOD_CIRLCE_SCALE_ANIM_MS = 500;
    public static int PERIOD_ARC_PROGRESS_ANIM_MS = 2*1000;
    public static int DISTANCE_SCALE_CIRCLE_ANIM = 60;
    public static int SCALE_CIRCLE_RING_WIDTH = 20;
    private Paint mPaint;
    private Paint mScaleAnimPaint;
    private Paint mArcAnimPaint;
    private int mViewHeight;
    private int mViewWidth;
    private String text = "结束";
    private int mCurStage = STAGE_IDLE;
    private int mCurMotionType = MotionEvent.ACTION_DOWN;
    private float mCurArcDegree;
    private Animation mCircleScaleAnim;
    private Animation mArcProgressIncreaseAnim;
    private Animation mArcProgressDecreaseAnim;
    private float mScaleCircleRadius;
    private float mBackCircleRadius;
    private float totalAnimDegree = 360 - mCurArcDegree;
    private float oldArcDegree = mCurArcDegree;

    public PressAnimCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleAnimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleAnimPaint.setStyle(Paint.Style.STROKE);
        mScaleAnimPaint.setStrokeWidth(SCALE_CIRCLE_RING_WIDTH);
        mScaleAnimPaint.setColor(Color.BLUE);
        mArcAnimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcAnimPaint.setStyle(Paint.Style.STROKE);
        mArcAnimPaint.setStrokeWidth(SCALE_CIRCLE_RING_WIDTH);
        mArcAnimPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewHeight = getHeight();
        mViewWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mBackCircleRadius = Math.min(mViewHeight,mViewWidth)/2-DISTANCE_SCALE_CIRCLE_ANIM;
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mBackCircleRadius,mPaint);

        Rect textRect = new Rect();
        mPaint.setTextSize(80);
        //这个方法是以当前View的坐标原点为基准线写字得到的字符串的边界,通过该边界的值，可以推算出矩形中线的y坐标，然后
        //根据想要写字的区域的中线的y坐标相减，就是我们的目标基线的y坐标。
        mPaint.getTextBounds(text,0,text.length(),textRect);
        Log.d(TAG,textRect.toString());
        mPaint.setColor(Color.BLACK);
        canvas.drawText(text,mViewWidth/2-(textRect.right-textRect.left)/2,mViewHeight/2-(textRect.bottom+textRect.top)/2,mPaint);

        drawAnim(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                handleActionDown();
                return true;
            case MotionEvent.ACTION_UP:
                handleActionUp();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void drawAnim(Canvas canvas)
    {
        if(mCurStage == STAGE_IDLE)
        {
            return;
        }
        if(mCurStage == STAGE_CIRLCE_SCALE_ANIM)
        {
            canvas.drawCircle(mViewWidth/2,mViewHeight/2,mScaleCircleRadius,mScaleAnimPaint);
        }else if(mCurStage == STAGE_ARC_PROGRESS_ANIM)
        {
            canvas.drawCircle(mViewWidth/2,mViewHeight/2,mScaleCircleRadius,mScaleAnimPaint);
            canvas.drawArc(mViewWidth/2-mScaleCircleRadius,mViewHeight/2-mScaleCircleRadius,mViewWidth/2+mScaleCircleRadius,mViewHeight/2+mScaleCircleRadius,-90,mCurArcDegree,false,mArcAnimPaint);
        }
    }

    private void handleActionDown()
    {
        mCurMotionType = MotionEvent.ACTION_DOWN;
        if(mCurStage == STAGE_IDLE)
        {
            mCurStage = STAGE_CIRLCE_SCALE_ANIM;
            startScaleAnim();
        }else if(mCurStage == STAGE_ARC_PROGRESS_ANIM)
        {
            clearAnimation();
            startArcProgressIncreaseAnim();
        }
    }

    private void handleActionUp()
    {
        mCurMotionType = MotionEvent.ACTION_UP;
        if(mCurStage == STAGE_CIRLCE_SCALE_ANIM)
        {
            mCurStage = STAGE_IDLE;
//            clearAnimation();
            mCircleScaleAnim.cancel();
//            invalidate();
        }else if(mCurStage == STAGE_ARC_PROGRESS_ANIM){
//            clearAnimation();
            mArcProgressIncreaseAnim.cancel();
            startArcProgressDecreaseAnim();
        }
    }

    private void startScaleAnim()
    {
        if(mCircleScaleAnim == null) {
            mCircleScaleAnim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    Log.d(TAG, "mCircleScaleAnim,interpolatedTime = " + interpolatedTime);
                    mScaleCircleRadius = mBackCircleRadius - SCALE_CIRCLE_RING_WIDTH / 2 + interpolatedTime * DISTANCE_SCALE_CIRCLE_ANIM;
                    if (mScaleCircleRadius <= Math.min(mViewHeight, mViewWidth) / 2 - SCALE_CIRCLE_RING_WIDTH / 2) {
                        invalidate();
                    }
                }
            };
            mCircleScaleAnim.setDuration(PERIOD_CIRLCE_SCALE_ANIM_MS);
            mCircleScaleAnim.setInterpolator(new LinearInterpolator());
            mCircleScaleAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurMotionType == MotionEvent.ACTION_DOWN) {
                        mCurStage = STAGE_ARC_PROGRESS_ANIM;
                        startArcProgressIncreaseAnim();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        startAnimation(mCircleScaleAnim);
    }

    private void startArcProgressIncreaseAnim() {
        totalAnimDegree = 360 - mCurArcDegree;
        oldArcDegree = mCurArcDegree;
        long duration = new Double(Math.ceil(totalAnimDegree / 360 * PERIOD_ARC_PROGRESS_ANIM_MS)).intValue();
        Log.d(TAG, "startArcProgressIncreaseAnim,totalAnimDegree = " + totalAnimDegree + ",duration = " + duration);
        if (mArcProgressIncreaseAnim == null) {
            mArcProgressIncreaseAnim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    Log.d(TAG, "mArcProgressIncreaseAnim, interpolatedTime = " + interpolatedTime);
                    mCurArcDegree = oldArcDegree + interpolatedTime * totalAnimDegree;
                    Log.d(TAG,"interpolatedTime * totalAnimDegree = "+interpolatedTime * totalAnimDegree+",totalAnimDegree = "+totalAnimDegree);
                    Log.d(TAG, "mArcProgressIncreaseAnim,mCurArcDegree = " + mCurArcDegree+",oldArcDegree = "+oldArcDegree);
                    if(mCurStage == STAGE_IDLE)
                    {
                        dealIdleStage();
                        return;
                    }
                    if (mCurArcDegree <= 360) {
                        invalidate();
                    } else {
                        mCurArcDegree = 360;
                    }
                }
            };
            mArcProgressIncreaseAnim.setInterpolator(new LinearInterpolator());
            mArcProgressIncreaseAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(mCurArcDegree == 360) {
                        dealIdleStage();
                        invalidate();
                        Toast.makeText(PressAnimCircleView.this.getContext(), "stop running?", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        mArcProgressIncreaseAnim.setDuration(duration);
        startAnimation(mArcProgressIncreaseAnim);
    }

    private void startArcProgressDecreaseAnim()
    {
        oldArcDegree = mCurArcDegree;
        long duration = new Double(Math.ceil(oldArcDegree/360*PERIOD_ARC_PROGRESS_ANIM_MS)).intValue();
        if(mArcProgressDecreaseAnim == null) {
            mArcProgressDecreaseAnim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    mCurArcDegree = oldArcDegree - interpolatedTime*oldArcDegree;
                    if(mCurArcDegree >= 0)
                    {
                        invalidate();
                    }else{
                        mCurArcDegree = 0;
                    }
                }
            };
            mArcProgressDecreaseAnim.setInterpolator(new LinearInterpolator());
            mArcProgressDecreaseAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(mCurArcDegree == 0) {
                        dealIdleStage();
                        invalidate();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        mArcProgressDecreaseAnim.setDuration(duration);
        startAnimation(mArcProgressDecreaseAnim);
    }

    private void dealIdleStage()
    {
        mCurStage = STAGE_IDLE;
        mCurArcDegree = 0;
        Log.d(TAG,"dealIdleStage, mCurArcDegree = "+mCurArcDegree);
    }
}
