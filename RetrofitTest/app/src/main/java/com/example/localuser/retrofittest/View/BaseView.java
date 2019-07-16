package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseView extends View {
    protected Paint mPaint;
    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
