package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyRoundRectView extends View {
    private Paint paint;
    public MyRoundRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(100,200,800,400,8,8,paint);
        canvas.drawRect(100,600,800,800,paint);
    }
}
