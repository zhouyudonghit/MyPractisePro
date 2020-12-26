package com.example.localuser.retrofittest.MaskViewTest.masklayout2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.example.localuser.retrofittest.Configs.LogConfigs;

/**
 * 遮罩上挖洞的Drawable
 */
public class HoleDrawable extends Drawable {
    private String TAG = LogConfigs.TAG_PREFIX_MASKVIEW_TEST + getClass().getSimpleName();
    private View mHighLightView;
    private View mAttachedView;
    private Paint mPaint;

    public HoleDrawable(View attachedView,View highlightView)
    {
        this.mAttachedView = attachedView;
        this.mHighLightView = highlightView;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawViewBackground(Color.parseColor("#99000000"),canvas);
//        drawViewBackground(Color.RED,canvas);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    private void drawViewBackground(int color,Canvas canvas)
    {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int[] position = new int[2];
        mAttachedView.getLocationOnScreen(position);
        Log.d(TAG,"position[0] = "+position[0]+",position[1] = "+position[1]);
        int width = mAttachedView.getWidth();
        int height = mAttachedView.getHeight();
        Log.d(TAG,"width = "+width+",height ="+height);
        Path path1 = new Path();
        path1.setFillType(Path.FillType.EVEN_ODD);
        path1.addRect(0,0,width,height,Path.Direction.CCW);
        mHighLightView.getLocationOnScreen(position);
        Log.d(TAG,"position[0] = "+position[0]+",position[1] = "+position[1]);
        width = mHighLightView.getWidth();
        height = mHighLightView.getHeight();
        Log.d(TAG,"width = "+width+",heiht = "+height);
        path1.addRect(position[0],position[1],position[0]+width,position[1]+height,Path.Direction.CW);
        canvas.drawPath(path1,mPaint);
    }
}
