package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

public class MyColorRingView extends BaseView {
    public MyColorRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(600,600,195,mPaint);
        canvas.drawCircle(600,600,305,mPaint);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(80);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        int[] color = {Color.GREEN,Color.RED};
        float[] position = {0,1};
        canvas.save();
        canvas.rotate(-90,600,600);
//        mPaint.setShader(new SweepGradient(600,600,Color.GREEN,Color.RED));
        double radians = Math.asin(50.0/250);
        double degree = Math.toDegrees(radians);
        float degreeFloat = Float.valueOf(degree+"");
        //这里需要做一下小处理，对两头的角度做一下处理，否则显示会有问题
//        canvas.drawArc(350,350,850,850,degreeFloat,360-2*degreeFloat,false,mPaint);
//        canvas.drawArc(350,350,850,850,-90,90,false,mPaint);
        gradient2(canvas);
        canvas.restore();

        mPaint.setShader(new LinearGradient(300,1200,900,1200,color,position,Shader.TileMode.CLAMP));
        canvas.drawLine(300,1200,900,1200,mPaint);
    }

    /**
     * 在两个颜色交界处,加个短渐变，否则有条线看着太丑,有效
     * @param canvas
     */
    private void gradient1(Canvas canvas)
    {
        double radians = Math.asin(50.0/250);
        int degree = (int) Math.toDegrees(radians);
        int startColor = Color.parseColor("#7FACFF");
        int endColor = Color.parseColor("#607DFE");
        int middleColor = getGradientColor(0.5f,startColor,endColor);
        mPaint.setShader(new SweepGradient(600,600,new int[]{middleColor,startColor,endColor,middleColor},new float[]{0,0.027778f,0.972222f,1}));
        canvas.drawArc(350,350,850,850,0,360,false,mPaint);
    }

    /**
     * 在两个颜色交界处,加个短渐变，否则有条线看着太丑
     * @param canvas
     */
    private void gradient2(Canvas canvas)
    {
        double radians = Math.asin(50.0/250);
        int degree = (int) Math.toDegrees(radians);
//        canvas.drawArc(350,350,850,850,0,360,false,mPaint);

//        canvas.rotate(-10,600,600);
//        mPaint.setShader(null);
        int startDegree = 0;
        mPaint.setShader(null);
//        for(int i = 0;i < 360;i++)
//        {
//            mPaint.setColor(getGradientColor(i/360.0f,Color.RED,Color.GREEN));
//            canvas.drawArc(350,350,850,850,startDegree,1f,false,mPaint);
//            startDegree+=1;
//        }
        canvas.drawArc(350,350,850,850,0.5f,0.5f,false,mPaint);
//        mPaint.setShader(new SweepGradient(600,600,new int[]{Color.RED,Color.GREEN},new float[]{0,0.055556f}));
//        canvas.drawArc(350,350,850,850,0,20,false,mPaint);
    }

    /**
     * 计算两个颜色之间的一个渐变色
     * @return
     */
    public int getGradientColor(float ratio,int startColor,int endColor)
    {
        float R = Color.red(startColor) + (Color.red(endColor) - Color.red(startColor)) * ratio;
        float G = Color.green(startColor) + (Color.green(endColor) - Color.green(startColor)) * ratio;
        float B = Color.blue(startColor) + (Color.blue(endColor) - Color.blue(startColor)) * ratio;

        int cl = Color.argb(255,(int)R,(int)G,(int)B);
        return cl;
    }
}
