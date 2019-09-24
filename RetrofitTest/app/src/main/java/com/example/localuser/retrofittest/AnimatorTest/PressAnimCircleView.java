package com.example.localuser.retrofittest.AnimatorTest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class PressAnimCircleView extends AppCompatTextView {
    private String TAG = LogConfigs.TAG_PREFIX_ANIM_TEST + getClass().getSimpleName();
    public static int STAGE_IDLE = 0;//无动画状态
    public static int STAGE_FIRST_ANIM = 1;//外层圆环的缩放动画；
    public static int STAGE_SECOND_ANIM = 2;//环形进度条动画；
    public static int PERIOD_FIRST_ANIM_MS = 1000;
    public static int PERIOD_SECOND_ANIM_MS = 3*1000;
    private Paint mPaint;
    private int mViewHeight;
    private int mViewWidth;
    private int mLineWidth = 6;
    private String text = "结束";
    private int mCurStage = STAGE_IDLE;
    private int mCurMotionType = MotionEvent.ACTION_DOWN;
    private float mCurArcDegree;

    public PressAnimCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        float radius = Math.min(mViewHeight,mViewWidth)/2;
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,radius,mPaint);

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
                Log.d(TAG,"evetn = "+event);
        }
        return super.onTouchEvent(event);
    }

    private void drawAnim(Canvas canvas)
    {

    }

    private void handleActionDown()
    {

    }

    private void handleActionUp()
    {

    }
}
