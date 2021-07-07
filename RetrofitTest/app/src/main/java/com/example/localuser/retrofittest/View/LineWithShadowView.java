package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.example.localuser.retrofittest.Utils.AppUtils;

public class LineWithShadowView extends BaseView {
    private float shadowBlur;
    private float diffY;

    public LineWithShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(AppUtils.dip2px(getContext(),3));
        shadowBlur = AppUtils.dip2px(getContext(),4);
        diffY = AppUtils.dip2px(getContext(),11);
        mPaint.setShadowLayer(shadowBlur, 0, diffY, Color.parseColor("#33FD7717"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(100,100,300,100,mPaint);
    }


}
