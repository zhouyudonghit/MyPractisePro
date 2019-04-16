package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.View;

public class ShaderTest extends BaseTest {
    public ShaderTest(View view) {
        super(view);
    }

    public void testShader1(Canvas canvas)
    {
        Shader shader = new LinearGradient(200,100,600,100,Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
        canvas.drawCircle(mViewWidth/2,mViewHeight/4,mViewHeight/4,mPaint);
        Shader shader2 = new LinearGradient(200,100,600,100,Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        mPaint.setShader(shader2);
        canvas.drawCircle(mViewWidth/2,mViewHeight/4*3,mViewHeight/4,mPaint);
    }
}
