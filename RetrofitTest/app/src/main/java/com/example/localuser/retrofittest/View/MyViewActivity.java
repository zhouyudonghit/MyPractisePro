package com.example.localuser.retrofittest.View;

import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ListView;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MainActivity;
import com.example.localuser.retrofittest.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by localuser on 2018/4/16.
 */

public class MyViewActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW +getClass().getSimpleName();
    private MyView myView;
    private MyAdhesionView myAdhesionView;
    private MyDrawLineView myDrawLineView;
    private MyColorRingView myColorRingView;
    private MyRoundRectView myRoundRectView;
    private MessageCenterUnreadTipView messageCenterUnreadTipView;
    private RoundRectImageView mRoundRectImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myview_activity_main);
        myView = (MyView) findViewById(R.id.myview);
        myView.setVisibility(View.GONE);
        final Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.d(MainActivity.TAG,"interpolatedTime="+interpolatedTime);
                myView.setVaribale(interpolatedTime*(100.0f));
            }
        };
        animation.setDuration(10000);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAnimation();
////                myView.startAnimation(animation);
//                Toast.makeText(MyViewActivity.this,"click",Toast.LENGTH_LONG).show();
                myView.requestLayout();
            }
        });

        myAdhesionView = (MyAdhesionView) findViewById(R.id.my_adhesion_view);
        myAdhesionView.setVisibility(View.GONE);

        myDrawLineView = findViewById(R.id.my_drawline_view);
        myDrawLineView.setOnClickListener(this);
        myDrawLineView.setVisibility(View.GONE);

        myRoundRectView = findViewById(R.id.my_roundrect_view);
        myRoundRectView.setVisibility(View.GONE);

        myColorRingView = findViewById(R.id.my_color_ring_view);
        myColorRingView.setVisibility(View.GONE);

        messageCenterUnreadTipView = findViewById(R.id.message_view);
        messageCenterUnreadTipView.setVisibility(View.VISIBLE);
        messageCenterUnreadTipView.setVisibility(View.GONE);

        mRoundRectImageView  = findViewById(R.id.round_rect_imageview);
        mRoundRectImageView.setImageResource(R.mipmap.content_films);
    }

    public void startAnimation()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,10000);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                myView.beginWave(value);
            }
        });
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.my_drawline_view:
                myDrawLineViewOnclick();
                break;
            default:
        }
    }

    public void myDrawLineViewOnclick()
    {
        Log.d(TAG,"myDrawLineViewOnclick()");
//        myDrawLineView.invalidate();
        myDrawLineView.startAnimation();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
