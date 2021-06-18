package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.localuser.retrofittest.Utils.AppUtils;

public class RoundCornerRelativeLayout extends RelativeLayout {
    private String TAG = getClass().getSimpleName();
    private float mRadius;
    private Path mRoundedRectPath;
    private RectF mRectF;
    private boolean isShowTopCorner = true;
    private boolean isShowBottomCorner = true;

    public RoundCornerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        mRadius = 2.0f * density;
        mRectF = new RectF();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
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
        super.dispatchDraw(canvas);
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
}
