package com.example.localuser.retrofittest.Drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;

/**
 * 动态绘制轨迹的drawable
 */
public class MyDrawLineDrawable extends Drawable{
    private String TAG = LogConfigs.TAG_PREFIX_DRAWABLE_TEST +getClass().getSimpleName();
    public static int POINTS_NUMBER = 4;
    public static int TOTAL_DURATION = 3000;
    private Paint mPaint;
    private Point[] mPoints = new Point[]{new Point(100,200),new Point(300,200),new Point(500,200),new Point(700,200)};
    private int currentIndex;
    private Animation drawLineAnimation;
    private View mView;

    public MyDrawLineDrawable(View view)
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mView = view;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.d(TAG,"draw(),currentIndex = "+currentIndex+",this = "+this);
        if(currentIndex <= POINTS_NUMBER-2) {
            drawLines(canvas);
            //currentIndex++;
//            drawEndPoint(canvas,mPoints[currentIndex+1]);
        }else{
            currentIndex = 0;
        }
    }

    public void drawLines(Canvas canvas)
    {
        for(int i = 0;i <= currentIndex;i++)
        {
            setDrawLinePaint(i);
            canvas.drawLine(mPoints[i].x,mPoints[i].y,mPoints[i+1].x,mPoints[i+1].y,mPaint);
        }
    }

    public void setDrawLinePaint(int index)
    {
        mPaint.setStrokeWidth(10);
//        mPaint.setColor(mDrawColors[index]);
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

    public void startAnimation()
    {
        if(drawLineAnimation != null && !drawLineAnimation.hasEnded())
        {
            return;
        }
        drawLineAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                Log.d(TAG,"applyTransformation,interpolatedTime = "+interpolatedTime);
                super.applyTransformation(interpolatedTime, t);
                int tempIndex =(int) (interpolatedTime*(POINTS_NUMBER-2));
//                currentIndex = tempIndex;
//                if(currentIndex < POINTS_NUMBER-1) {
//                    invalidate();
//                }
                if(tempIndex != currentIndex)
                {
                    currentIndex = tempIndex;
                    Log.d(TAG,"applyTransformation,currentIndex = "+currentIndex);
                    mView.invalidate();
                }
            }
        };
        drawLineAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(mView.getContext(),"draw finish",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        drawLineAnimation.setDuration(PERIOD_BWTEEN_TWO_POINTS*(POINTS_NUMBER-1));
        drawLineAnimation.setDuration(TOTAL_DURATION);
//        drawLineAnimation.setInterpolator(new LinearInterpolator());
        drawLineAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mView.startAnimation(drawLineAnimation);
    }
}
