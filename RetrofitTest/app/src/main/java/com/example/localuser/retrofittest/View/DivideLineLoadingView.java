package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.localuser.retrofittest.Utils.AppUtils;
import com.example.localuser.retrofittest.Utils.ScreenUtils;

/**
 * 短线加载view
 */
public class DivideLineLoadingView extends View{
    private String TAG = getClass().getSimpleName();
    private static final int MESSAGE_WHAT_UPDATING_LINES = 0x01;
    private static final int MESSAGE_WHAT_LOADING_COMPLETE = 0x02;
    private static final int UPDATEING_DURATION = 1000;//更新界面的周期
    private float roundCorner;
    private int divideLineDefaultColor;//短线默认颜色
    private int divideLineUpdatingColor;//短线动态显示颜色
    private int lineTotalNum;//短线的总数量
    private float lineIntervalDistance;
    private int lineLoadingNum;//加载的短线的数量
    private float lineWidth;
    private int viewWidth;
    private int viewHeight;
    private Paint mPaint;

    public DivideLineLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MESSAGE_WHAT_UPDATING_LINES:
                    lineLoadingNum++;
                    if(lineLoadingNum > lineTotalNum)
                    {
                        lineLoadingNum = 0;
                    }
                    invalidate();
                    sendEmptyMessageDelayed(MESSAGE_WHAT_UPDATING_LINES,UPDATEING_DURATION);
                    break;
                case MESSAGE_WHAT_LOADING_COMPLETE:
                    lineLoadingNum = lineTotalNum;
                    invalidate();
                    break;
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewWidth = getWidth();
        viewHeight = getHeight();
        Log.d(TAG,"onDraw,width = "+viewWidth+",height = "+viewHeight);
        lineWidth = (int) ((viewWidth - (lineTotalNum - 1)*lineIntervalDistance)/lineTotalNum);
        drawLoadingView(canvas);
        drawRestView(canvas);
    }

    private void drawLoadingView(Canvas canvas)
    {
        if(lineLoadingNum  <= 0)
        {
            return;
        }
        mPaint.setColor(divideLineUpdatingColor);
        int left = 0;
        for(int i = 0; i < (lineLoadingNum);i++)
        {
            canvas.drawRoundRect(left,0,left+lineWidth,viewHeight,roundCorner,roundCorner,mPaint);
            left += lineIntervalDistance+lineWidth;
        }
    }

    private void drawRestView(Canvas canvas)
    {
        int number = lineTotalNum - lineLoadingNum;
        if(number <= 0)
        {
            return;
        }
        mPaint.setColor(divideLineDefaultColor);
        int left = (int) ((lineWidth + lineIntervalDistance)*lineLoadingNum);
        for(int i = 0; i < number;i++)
        {
            canvas.drawRoundRect(left,0,left+lineWidth,viewHeight,roundCorner,roundCorner,mPaint);
            left += lineIntervalDistance+lineWidth;
        }
    }

    /**
     * 设置必要的尺寸参数
     * @param cornerRadioInDp 短线的圆角角度
     * @param lineIntervalDistanceInDp 短线之间的间距
     * @param scaleWithScreen 是否需要根据屏幕进行缩放
     */
    public void setDimention(int cornerRadioInDp,int lineIntervalDistanceInDp,boolean scaleWithScreen)
    {
        roundCorner = AppUtils.dip2px(getContext(),cornerRadioInDp);
        lineIntervalDistance = AppUtils.dip2px(getContext(),lineIntervalDistanceInDp);
        if(scaleWithScreen)
        {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            float scale = point.x / AppUtils.dip2px(getContext(),375);
            lineIntervalDistance *= scale;
        }
    }

    /**
     * 设置必要的颜色参数
     * @param lineDefaultColor
     * @param divideLineUpdatingColor
     */
    public void setColors(int lineDefaultColor,int divideLineUpdatingColor)
    {
        this.divideLineDefaultColor = lineDefaultColor;
        this.divideLineUpdatingColor = divideLineUpdatingColor;
    }

    public void setLineTotalNum(int lineTotalNum) {
        this.lineTotalNum = lineTotalNum;
    }

    public void startLoading()
    {
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT_UPDATING_LINES,UPDATEING_DURATION);
    }

    public void loadingComplete()
    {
        mHandler.removeMessages(MESSAGE_WHAT_UPDATING_LINES);
        mHandler.sendEmptyMessage(MESSAGE_WHAT_LOADING_COMPLETE);
    }
}
