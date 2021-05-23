package com.example.localuser.retrofittest.View;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class VerticalMarqueeView3 extends LinearLayout {
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

    public VerticalMarqueeView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        Animator animator = new Animator() {
            @Override
            public long getStartDelay() {
                return 0;
            }

            @Override
            public void setStartDelay(long startDelay) {

            }

            @Override
            public Animator setDuration(long duration) {
                return null;
            }

            @Override
            public long getDuration() {
                return 0;
            }

            @Override
            public void setInterpolator(TimeInterpolator value) {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }
}
