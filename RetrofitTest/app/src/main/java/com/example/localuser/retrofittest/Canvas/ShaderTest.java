package com.example.localuser.retrofittest.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Translation;

public class ShaderTest extends BaseTest {
    public ShaderTest(View view) {
        super(view);
    }

    //测试线性渐变
    public void testShader1(Canvas canvas)
    {
        Shader shader = new LinearGradient(0,mViewHeight/2,mViewWidth,mViewHeight/2,Color.parseColor("#ffffff"),
                Color.parseColor("#000000"), Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
//        canvas.drawCircle(mViewWidth/2,mViewHeight/4,mViewHeight/4,mPaint);
//        mPaint.setStrokeWidth(mViewWidth);
//        canvas.drawLine(0,mViewHeight/2,mViewWidth,mViewHeight/2,mPaint);
        canvas.drawRect(0,0,mViewWidth+20,mViewHeight+20,mPaint);
//        Shader shader2 = new LinearGradient(200,100,400,100,Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
//        mPaint.setShader(shader2);
//        canvas.drawCircle(mViewWidth/2,mViewHeight/4*3,mViewHeight/4,mPaint);
    }

    //测试辐射渐变
    public void testShader2(Canvas canvas)
    {
        Shader shader = new RadialGradient(mViewWidth/2,mViewHeight/2,mViewHeight,Color.parseColor("#ff000000"),
                Color.parseColor("#ffffffff"), Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
//        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewHeight/4,mPaint);
        canvas.drawRect(0,0,mViewWidth+20,mViewHeight+20,mPaint);
    }

    //测试扫描渐变
    public void testShader3(Canvas canvas)
    {
        Shader shader = new SweepGradient(mViewWidth/2,mViewHeight/2,Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        mPaint.setShader(shader);
        canvas.drawRect(mViewWidth/4,mViewHeight/4,mViewWidth/4*3,mViewHeight/4*3,mPaint);
    }

    public void testShader4(Canvas canvas, Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.timg);
        Shader shader = new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewHeight/4,mPaint);
    }
}
