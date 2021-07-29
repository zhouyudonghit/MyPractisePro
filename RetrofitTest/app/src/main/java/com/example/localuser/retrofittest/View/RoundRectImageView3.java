package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.example.localuser.retrofittest.R;

public class RoundRectImageView3 extends android.support.v7.widget.AppCompatImageView {
    private String TAG = getClass().getSimpleName();
    private int mRadius;
    private Path mRoundedRectPath;
    private RectF mRectF;
    private boolean isShowTopCorner = true;
    private boolean isShowBottomCorner = true;

    public RoundRectImageView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView3);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundRectImageView3_radius_2,0);
        Log.d(TAG,"mRadius = "+mRadius);
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setOutLine()
    {
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Log.d(TAG,"getWidth() = "+getWidth()+",getHeight() = "+getHeight());
                Log.e(TAG,"",new Exception());
                outline.setRoundRect(0,0,getWidth(),getHeight(),mRadius);
            }
        });
        setClipToOutline(true);
    }

    /**
     * 重写draw方法有效，可以使得background的边界按照设定的来
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();
        mRectF.set(left, top, right, bottom);
        mRoundedRectPath = new Path();
        if(isShowTopCorner && isShowBottomCorner) {
            mRoundedRectPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        }else{
            if(isShowTopCorner)
            {
                mRoundedRectPath.addRoundRect(mRectF, new float[]{mRadius,mRadius,mRadius,mRadius,0,0,0,0}, Path.Direction.CCW);
            }else{
                mRoundedRectPath.addRoundRect(mRectF, new float[]{0,0,0,0,mRadius,mRadius,mRadius,mRadius}, Path.Direction.CCW);
            }
        }
        canvas.clipPath(mRoundedRectPath);
        super.draw(canvas);
    }
}
