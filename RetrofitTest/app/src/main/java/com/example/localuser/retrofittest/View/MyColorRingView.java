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
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(600,600,195,mPaint);
        canvas.drawCircle(600,600,305,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(100);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        int[] color = {Color.GREEN,Color.RED};
        float[] position = {0,1};
//        canvas.rotate(-90,600,600);
        mPaint.setShader(new SweepGradient(600,600,Color.GREEN,Color.RED));
        double radians = Math.asin(50.0/250);
        double degree = Math.toDegrees(radians);
        float degreeFloat = Float.valueOf(degree+"");
        //这里需要做一下小处理，对两头的角度做一下处理，否则显示会有问题
        canvas.drawArc(350,350,850,850,degreeFloat,360-2*degreeFloat,false,mPaint);

        mPaint.setShader(new LinearGradient(300,1200,900,1200,color,position,Shader.TileMode.CLAMP));
        canvas.drawLine(300,1200,900,1200,mPaint);
    }
}
