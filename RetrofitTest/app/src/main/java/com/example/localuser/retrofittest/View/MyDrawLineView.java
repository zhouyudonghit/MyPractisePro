package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.util.Random;

/**
 *动态绘制轨迹
 */
public class MyDrawLineView extends View {
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW + getClass().getSimpleName();
    public static int PERIOD_BWTEEN_TWO_POINTS = 300;//单位是ms
    public static int TOTAL_DURATION = 3000;
    public static int POINTS_NUMBER = 40;
    private Paint mPaint;
    private Point[] mPoints = new Point[POINTS_NUMBER];
    private int[] originColors = new int[]{Color.BLACK,Color.RED,Color.YELLOW,Color.GREEN};
    private int[] mDrawColors = new int[POINTS_NUMBER];
    private int currentIndex = 0;
    private Animation drawLineAnimation;

    public MyDrawLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(40);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        makeTestDatas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        super.onDraw(canvas);
        Log.d(TAG,"currentIndex = "+currentIndex);
        if(currentIndex <= POINTS_NUMBER-2) {
            drawLines(canvas);
            //currentIndex++;
            drawEndPoint(canvas,mPoints[currentIndex+1]);
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

    public void drawEndPoint(Canvas canvas,Point endPoint)
    {
        setDrawEndPointPaint();
        canvas.drawPoint(endPoint.x,endPoint.y,mPaint);
    }

    public void setDrawLinePaint(int index)
    {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(mDrawColors[index]);
    }

    public void setDrawEndPointPaint()
    {
        mPaint.setStrokeWidth(60);
        mPaint.setColor(Color.BLACK);
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
                    invalidate();
                }
            }
        };
        drawLineAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getContext(),"draw finish",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        drawLineAnimation.setDuration(PERIOD_BWTEEN_TWO_POINTS*(POINTS_NUMBER-1));
        drawLineAnimation.setDuration(TOTAL_DURATION);
//        drawLineAnimation.setInterpolator(new LinearInterpolator());
        drawLineAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(drawLineAnimation);
    }

    public void makeTestDatas()
    {
        Random random = new Random(2);
        mPoints = new Point[POINTS_NUMBER];
        int startX = 100,startY = 100;
        for(int i = 0;i < POINTS_NUMBER;i++) {
            mPoints[i] = new Point(startX,startY);
            startX += random.nextInt(40)+10;
            startY += random.nextInt(40)+10;
        }

        for(int i = 0;i < POINTS_NUMBER-1;i++)
        {
//            mDrawColors[i] = originColors[random.nextInt(3)];
            mDrawColors[i] = originColors[3];
        }
    }
}
