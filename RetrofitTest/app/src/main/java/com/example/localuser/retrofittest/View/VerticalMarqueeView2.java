package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import com.example.localuser.retrofittest.Configs.LogConfigs;

/**
 * Animation实现的
 */
public class VerticalMarqueeView2 extends LinearLayout {
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW +getClass().getSimpleName();
    public static final int ANIM_DUARATION = 1500;
    public static final int SHOW_TIME = 2*1000;
    public static final int MESSAGE_WHAT_START_SCROLL = 0x01;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MESSAGE_WHAT_START_SCROLL:
                    startScrollAnimation();
                    break;
            }
        }
    };

    public VerticalMarqueeView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        startScrolling();
    }

    public void startScrolling()
    {
        Log.d(TAG,"startScrolling(),getChildCount() = "+getChildCount());
        if(getChildCount() <= 1)
        {
            //孩子数量小于2个，不执行垂直方向的滚动
            return;
        }
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT_START_SCROLL,SHOW_TIME);
    }

    private void startScrollAnimation()
    {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int height = getHeight();
                if(height != 0)
                {
                    int scrooByDis = interpolatedTime == 1 ? height : (int)(height*interpolatedTime);
                    Log.d(TAG,"scrooByDis = "+scrooByDis+",interpolatedTime = "+interpolatedTime);
                    scrollTo(0,scrooByDis);
                }
            }
        };
        animation.setDuration(ANIM_DUARATION);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handleChildren();
//                setScrollY(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG,"t = "+t+",oldt = "+oldt);
    }

    private void handleChildren()
    {
        Log.d(TAG,"handleChildren()");
//        scrollTo(0,0);
        //之所以放在post方法里面执行，是因为如果scrollTo(0,0)放在外面，动画的applyTransformation会在该句代码之后有执行，造成对scrollTo(0,0)的覆盖，导致无效
        post(new Runnable() {
            @Override
            public void run() {
                scrollTo(0,0);
                int childCount = getChildCount();
                View firstChild = getChildAt(0);
                removeViewAt(0);
                addView(firstChild,childCount-1);
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT_START_SCROLL,SHOW_TIME);
            }
        });
    }

    @Override
    public void scrollTo(int x, int y) {
        Log.d(TAG,"scrollTo, y = "+y+",getScrollY() = "+getScrollY());
        super.scrollTo(x, y);
    }
}
