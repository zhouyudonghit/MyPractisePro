package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;

public class RoundRectImageView2 extends AppCompatImageView {
    private String TAG = getClass().getSimpleName();
    private float mRadius;
    private Path mRoundedRectPath;
    private RectF mRectF;
    private boolean isShowTopCorner = true;
    private boolean isShowBottomCorner = true;

    public RoundRectImageView2(Context context) {
        super(context);
        init(context);
    }

    public RoundRectImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundRectImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        mRadius = 2.0f * density;
        mRectF = new RectF();
    }

    private void init(Context context,AttributeSet attrs) {
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView2);
        mRadius = a.getDimensionPixelSize(R.styleable.RoundRectImageView2_radius,0);
        Log.d(TAG,"mRadius = "+mRadius);
        mRectF = new RectF();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
        super.onDraw(canvas);
    }

    /**
     *
     * @param radius dp
     */
    public void setRadius(float radius)
    {
        mRadius = AppUtils.dp2px(radius);
        invalidate();
    }

    public void setShowCorner(boolean showTopCorner,boolean showBottomCorner)
    {
        isShowTopCorner = showTopCorner;
        isShowBottomCorner = showBottomCorner;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
