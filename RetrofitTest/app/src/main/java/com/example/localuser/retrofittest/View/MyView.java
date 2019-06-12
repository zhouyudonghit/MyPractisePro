package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MainActivity;

/**
 * Created by localuser on 2018/4/16.
 */

public class MyView extends View {
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW +getClass().getSimpleName();
    private Paint mPait;
    private Path mPath;
    private float oldOffset;
    private float offset;
    private int waveOffset;
    private int width,height;
    private Point point1,point2,point3,point4,point5,point6,point7,point8,point9;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init()
    {
        Log.d(TAG,"init()");
        mPait = new Paint();
        mPath = new Path();
        point1 = new Point();
        point2 = new Point();
        point3 = new Point();
        point4 = new Point();
        point5 = new Point();
        point6 = new Point();
        point7 = new Point();
        point8 = new Point();
        point9 = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(300,300,200,mPait);
//        //canvas.drawColor(Color.parseColor("#88880000"));
//
//        mPath.addArc(200, 200, 400, 400, -225, 225);
//        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
//        mPath.lineTo(400, 542);
        //mPath.quadTo(100,100,200,0);
//        mPait.setStyle(Paint.Style.STROKE);
//        mPath.lineTo(offset,offset);
//        canvas.drawPath(mPath,mPait);
        drawWave(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG,"onMeasure,width = "+width+",height="+height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout");
    }

    public void setVaribale(float offset)
    {
        this.offset = offset;
        Log.d(MainActivity.TAG,"offset="+offset);
        invalidate();
    }

    public void beginWave(int offset)
    {
        waveOffset = (int)(offset*(width/10000.0));
        Log.d(MainActivity.TAG,"beginWave,offset = "+offset+",waveoffset="+waveOffset);
        invalidate();
    }

    public void drawWave(Canvas canvas)
    {
        mPath.reset();
        mPait.setColor(Color.GREEN);
        point1.set(-width+waveOffset,500);
        point2.set(-width*3/4+waveOffset,300);
        point3.set(-width/2+waveOffset,500);
        point4.set(-width/4+waveOffset,700);
        point5.set(waveOffset,500);
        point6.set(width/4+waveOffset,300);
        point7.set(width/2+waveOffset,500);
        point8.set(width*3/4+waveOffset,700);
        point9.set(width+waveOffset,500);
        mPath.moveTo(point1.x,point1.y);
        mPath.quadTo(point2.x,point2.y,point3.x,point3.y);
        mPath.quadTo(point4.x,point4.y,point5.x,point5.y);
        mPath.quadTo(point6.x,point6.y,point7.x,point7.y);
        mPath.quadTo(point8.x,point8.y,point9.x,point9.y);
        mPath.lineTo(point9.x,1200);
        mPath.lineTo(point1.x,1200);
        mPath.lineTo(point1.x,point1.y);
        canvas.drawPath(mPath,mPait);
    }

    public void drawAdhesion(Canvas canvas)
    {
        mPath.moveTo(0,500);
        mPath.cubicTo(200,300,400,300,800,500);
        canvas.drawPath(mPath,mPait);
    }
}
