package com.example.localuser.retrofittest.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.PullRefreshListView.Configs;
import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/12.
 */

public class MyTextView extends AppCompatTextView {
    private String TAG = LogConfigs.TAG_PREFIX_CANVAS+getClass().getSimpleName();
    private int moveLenght = 0;
    private Scroller mScroller;
    private Paint mPaint;
    private Bitmap mBitmap;

    private int mViewWidth;
    private int mViewHeight;

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewHeight = getWidth();
        mViewWidth = getHeight();
        Log.d(TAG,"onMeasure,mViewWidth = "+mViewWidth+",mViewHeight = "+mViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewHeight = getWidth();
        mViewWidth = getHeight();
        Log.d(TAG,"onLayout,mViewWidth = "+mViewWidth+",mViewHeight = "+mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
//        Matrix matrix = getMatrix();
//        matrix.setTranslate(20,0);
//        canvas.setMatrix(matrix);
//        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        super.onDraw(canvas);
//        canvas.translate(20,0);
//        canvas.drawText("ggg",0,0,mPaint);
//        canvas.save();
//        canvas.scale(5,5);
//        //mPaint.getTextBounds("ggg",);
//        canvas.drawText("ggg",0,0,mPaint);
//        canvas.restore();
//        canvas.drawText("ggg",100,0,mPaint);
//        canvas.save();
//        Matrix matrix = new Matrix();
//        //matrix.setTranslate(30,30);
//        //canvas.translate(30,30);
//        canvas.drawBitmap(mBitmap,matrix,mPaint);
//        //canvas.restore();
//        canvas.drawText("test",0,100,mPaint);
//        testOval(canvas);
//        testPath(canvas);
//        testCircle(canvas);
        testShader(canvas);
    }

    public void move()
    {
        moveLenght += 20;
        invalidate();
    }

    public void smoothScroll()
    {
        mScroller.startScroll(0,0,200,0,2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void testCircle(Canvas canvas)
    {
        BaseTest test = new CircleTest(this);
        ((CircleTest) test).drawRing1(canvas);
        //((CircleTest) test).drawRing2(canvas);
    }

    public void testRect(Canvas canvas)
    {
        BaseTest test = new RectTest(this);
        ((RectTest) test).drawRoundRect(canvas);
    }

    public void testPoint(Canvas canvas)
    {
        BaseTest test = new PointTest(this);
        ((PointTest) test).drawPoints(canvas);
    }

    public void testOval(Canvas canvas)
    {
        BaseTest test = new OvalTest(this);
        ((OvalTest) test).drawArc1(canvas);
    }

    public void testLine(Canvas canvas)
    {
        BaseTest test = new LineTest(this);
        ((LineTest) test).drawLine1(canvas);
    }

    public void testPath(Canvas canvas)
    {
        BaseTest test = new PathTest(this);
        ((PathTest) test).drawPath6(canvas);
    }

    public void testText(Canvas canvas)
    {
        BaseTest test = new TextTest(this);
        ((TextTest) test).drawText(canvas);
    }

    public void testShader(Canvas canvas)
    {
        BaseTest test = new ShaderTest(this);
        ((ShaderTest) test).testShader1(canvas);
    }
}
