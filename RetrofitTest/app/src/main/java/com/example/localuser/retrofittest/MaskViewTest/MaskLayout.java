package com.example.localuser.retrofittest.MaskViewTest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class MaskLayout extends FrameLayout {
    private String TAG = LogConfigs.TAG_PREFIX_MASKVIEW_TEST + getClass().getSimpleName();
    private View oldView;
    private TextView newView;

    public MaskLayout(@NonNull Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#000000"));
        setAlpha(0.8f);
    }

    public void addHighlightView(View view)
    {
        Log.d(TAG,"addHighlightView");
        oldView = view;
        view.post(new Runnable() {
            @Override
            public void run() {
                showNewView();
            }
        });
    }

    private void showNewView()
    {
        Log.d(TAG,"showNewView()");
        newView = new TextView(getContext());
        newView.setText("maskview");
        newView.setTextSize(20);
        newView.setBackgroundColor(Color.parseColor("#00ffffff"));
//        newView.setAlpha(1);
        newView.setTextColor(Color.RED);
        int[] oldViewLocation = new int[2];
        oldView.getLocationInWindow(oldViewLocation);
        Log.d(TAG,"oldViewLocation[0] = "+oldViewLocation[0]+",oldViewLocation[1] = "+oldViewLocation[1]);
        int width = oldView.getWidth();
        int height = oldView.getHeight();
        Log.d(TAG,"width = "+width+",height = "+height);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width,height);
        lp.leftMargin = oldViewLocation[0];
        lp.topMargin = oldViewLocation[1];
        addView(newView,lp);
        oldView.getDrawingCache();
        oldView.setVisibility(View.GONE);
        startAnimation();
    }

    private void startAnimation()
    {
        TranslateAnimation translateAnimation1 = new TranslateAnimation(newView.getLeft(),newView.getLeft()-200,newView.getTop(),newView.getTop());
        translateAnimation1.setDuration(2000);
        translateAnimation1.setFillAfter(true);
        final TranslateAnimation translateAnimation2 = new TranslateAnimation(newView.getLeft()-200,newView.getLeft(),newView.getTop(),newView.getTop());
        translateAnimation2.setInterpolator(new BounceInterpolator());
        translateAnimation2.setDuration(2500);
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f,0);
        alphaAnimation.setDuration(1000);
        translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                newView.startAnimation(translateAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        translateAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation(alphaAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(View.GONE);
                oldView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        newView.startAnimation(translateAnimation1);
    }
}
