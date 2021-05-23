package com.example.localuser.retrofittest.AnimatorTest.loopplayanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ContentView extends View {
    private Paint mPaint;
    public ContentView(Context context) {
        super(context);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    private void initPaint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
