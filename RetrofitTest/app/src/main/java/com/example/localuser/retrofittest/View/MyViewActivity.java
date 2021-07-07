package com.example.localuser.retrofittest.View;

import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MainActivity;
import com.example.localuser.retrofittest.R;

import java.io.UnsupportedEncodingException;
import java.util.Date;

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
    private ShadowViewCard mShadowViewCard;

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
        messageCenterUnreadTipView.setVisibility(View.GONE);

        mRoundRectImageView  = findViewById(R.id.round_rect_imageview);
        mRoundRectImageView.setImageResource(R.mipmap.content_films);

        mRoundRectImageView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"mRoundRectImageView.getWidth() = " + mRoundRectImageView.getWidth());
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRoundRectImageView.getLayoutParams();
//                lp.width = 5.76;
            }
        });
        mRoundRectImageView.setVisibility(View.GONE);

        ImageView imageView = findViewById(R.id.round_rect_imageview_2);
        imageView.setVisibility(View.GONE);

        AnimRingWithGradientView view = findViewById(R.id.animRingWithGradientView);
        view.setDefaultRingColor(Color.parseColor("#B3DCE4EF"));
        view.setStartColor(Color.parseColor("#7FACFF"));
        view.setEndColor(Color.parseColor("#607DFE"));
        view.setRingStrokeWidth(12);
        view.startForwardAnim();
        view.setVisibility(View.GONE);

        DivideLineLoadingView divideLineLoadingView = findViewById(R.id.divideLineLoadingView);
        divideLineLoadingView.setDimention(2,8,false);
        divideLineLoadingView.setColors(Color.parseColor("#D6E0EA"),Color.parseColor("#6686FE"));
        divideLineLoadingView.setLineTotalNum(4);
        divideLineLoadingView.startLoading();
        divideLineLoadingView.setVisibility(View.GONE);

        final VerticalMarqueeView verticalMarqueeView = findViewById(R.id.verticalMarqueeView);
        verticalMarqueeView.initView();
        verticalMarqueeView.post(new Runnable() {
            @Override
            public void run() {
//                verticalMarqueeView.getHeight();
                findViewById(R.id.root).getHeight();
            }
        });
        verticalMarqueeView.setVisibility(View.GONE);

        final VerticalMarqueeView2 verticalMarqueeView2 = findViewById(R.id.verticalMarqueeView2);
        verticalMarqueeView2.startScrolling();
        verticalMarqueeView2.setVisibility(View.GONE);

        mShadowViewCard = findViewById(R.id.ShadowViewCard);
        mShadowViewCard.setVisibility(View.GONE);

        LineWithShadowView lineWithShadowView = findViewById(R.id.LineWithShadowView);
        lineWithShadowView.setVisibility(View.GONE);

        SmoothCurveView smoothCurveView =  findViewById(R.id.SmoothCurveView);
        smoothCurveView.setData(new Date(),null);
        smoothCurveView.setVisibility(View.VISIBLE);
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
