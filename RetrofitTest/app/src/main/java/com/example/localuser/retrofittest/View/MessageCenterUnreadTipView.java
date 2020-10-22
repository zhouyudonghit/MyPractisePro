package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

/**
 *参考TextTest类
 */
public class MessageCenterUnreadTipView extends View {
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW + getClass().getSimpleName();
    public static int DEFAULT_BACKGROUND_COLOR = 0xEA3F28;
    public static int DEFAULT_TEXT_COLOR = 0xFFFFFF;
    public static int DEFAULT_TEXT_SIZE_SP = 11;
    public static int DEFAULT_BACKGROUND_CIRCLE_WIDTH_DP = 12;
    public static int DEFAULT_BACKGROUND_ROUND_RECT_WITDH_DP = 18;
    private Paint mPaint;
    private Paint mTextPaint;
    private int mBackgroundColor;
    private int mTextColor;
    private int mTextSizPx;
    private int mValue = 9;
    private int mCircleWidth;
    private int mRoundCircleWidth;
    private int mRoundRectRadio;
    private int mViewWidth;
    private int mViewHeight;

    public MessageCenterUnreadTipView(Context context) {
        super(context);
    }

    public MessageCenterUnreadTipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if(attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MessageCenterUnreadTipView);
            mBackgroundColor = a.getColor(R.styleable.MessageCenterUnreadTipView_background_color,DEFAULT_BACKGROUND_COLOR);
            mTextColor = a.getColor(R.styleable.MessageCenterUnreadTipView_text_color,DEFAULT_TEXT_COLOR);
            mTextSizPx = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_text_size,DEFAULT_TEXT_SIZE_SP);
            mCircleWidth = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_circle_witdh,DEFAULT_BACKGROUND_CIRCLE_WIDTH_DP);
            mRoundCircleWidth = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_round_rect_width,DEFAULT_BACKGROUND_ROUND_RECT_WITDH_DP);
            mRoundRectRadio = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_round_rect_radio,0);
        }
        initPaint();
    }

    public MessageCenterUnreadTipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MessageCenterUnreadTipView);
            mBackgroundColor = a.getColor(R.styleable.MessageCenterUnreadTipView_background_color,DEFAULT_BACKGROUND_COLOR);
            mTextColor = a.getColor(R.styleable.MessageCenterUnreadTipView_text_color,DEFAULT_TEXT_COLOR);
            mTextSizPx = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_text_size,DEFAULT_TEXT_SIZE_SP);
            mCircleWidth = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_circle_witdh,DEFAULT_BACKGROUND_CIRCLE_WIDTH_DP);
            mRoundCircleWidth = a.getDimensionPixelSize(R.styleable.MessageCenterUnreadTipView_round_rect_width,DEFAULT_BACKGROUND_ROUND_RECT_WITDH_DP);
        }
        initPaint();
    }

    private void initPaint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBackgroundColor);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(mTextSizPx);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure,mViewWidth = "+mViewWidth+",mViewHeight = "+mViewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mValue <= 0) {
            return;
        }
        if(mValue < 10)
        {
            mViewWidth = mCircleWidth;
            mViewHeight = mCircleWidth;
        }else{
            mViewWidth = mRoundCircleWidth;
            mViewHeight = mCircleWidth;
        }
        Log.d(TAG,"onMeasure,mViewWidth = "+mViewWidth+",mViewHeight = "+mViewHeight);
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
        if(mValue <= 0)
        {
            return;
        }
        //先画底部背景
        canvas.drawRoundRect(0,0,mViewWidth,mViewHeight,mRoundRectRadio,mRoundRectRadio,mPaint);
        drawText(canvas);
    }

    public void updatevalue(int value)
    {
        if(value == 0)
        {
            setVisibility(View.GONE);
        }else{
            setVisibility(VISIBLE);
            requestLayout();
        }
    }

    private void drawText(Canvas canvas)
    {
        drawText1(canvas);
    }

    private void drawText1(Canvas canvas)
    {
        //然后画字
        String text = String.valueOf(mValue);
        mPaint.setColor(Color.BLUE);
        Rect rect = new Rect();
//        mTextPaint.setTextAlign(Paint.Align.LEFT);
//            mTextPaint.getTextBounds(text,0,text.length(),rect);
//            int textBoundWidth = rect.right - rect.left;
//            int textBoundHeight = rect.bottom - rect.top;
//            Log.d(TAG,"rect = "+rect);
//            canvas.drawRect((mViewWidth - textBoundWidth) / 2,rect.top + mViewHeight/2+textBoundHeight/2-rect.bottom,rect.right + (mViewWidth - textBoundWidth) / 2 - rect.left,mViewHeight/2+textBoundHeight/2,mPaint);
//            canvas.drawText(text,(mViewWidth - textBoundWidth) / 2 - rect.left,mViewHeight/2+textBoundHeight/2-rect.bottom,mTextPaint);

//        mTextPaint.setTextAlign(Paint.Align.RIGHT);
//        mTextPaint.getTextBounds(text,0,text.length(),rect);
//        int textBoundWidth = rect.right - rect.left;
//        int textBoundHeight = rect.bottom - rect.top;
//        Log.d(TAG,"rect = "+rect);
//        canvas.drawRect((mViewWidth - textBoundWidth) / 2,rect.top + mViewHeight/2+textBoundHeight/2-rect.bottom,rect.right + (mViewWidth - textBoundWidth) / 2 - rect.left,mViewHeight/2+textBoundHeight/2,mPaint);
//        canvas.drawText(text,(mViewWidth + textBoundWidth) / 2 - rect.left,mViewHeight/2+textBoundHeight/2-rect.bottom,mTextPaint);
        if(mValue >= 0)
        {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.getTextBounds(text,0,text.length(),rect);
            int textBoundWidth = rect.right - rect.left;
            int textBoundHeight = rect.bottom - rect.top;
            Log.d(TAG,"rect = "+rect);
            canvas.drawRect(rect,mPaint);
//            canvas.drawText(text,(mViewWidth + textBoundWidth)/2-rect.left,mViewHeight/2+textBoundHeight/2-rect.bottom,mTextPaint);
            canvas.drawText(text,(mViewWidth)/2,mViewHeight/2+textBoundHeight/2-rect.bottom,mTextPaint);
        }else {
            mTextPaint.setTextAlign(Paint.Align.LEFT);
            mTextPaint.getTextBounds(text,0,text.length(),rect);
            int textBoundWidth = rect.right - rect.left;
            int textBoundHeight = rect.bottom - rect.top;
            Log.d(TAG,"rect = "+rect);
            canvas.drawText(text, (mViewWidth - textBoundWidth) / 2 - rect.left, mViewHeight / 2 + textBoundHeight / 2 - rect.bottom, mTextPaint);
//            canvas.drawText(text, 0, 0, mTextPaint);
        }
    }

    private void drawText2(Canvas canvas)
    {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow");
    }
}
