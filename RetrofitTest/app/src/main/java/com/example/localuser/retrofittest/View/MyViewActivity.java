package com.example.localuser.retrofittest.View;

import android.animation.ValueAnimator;
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

import com.example.localuser.retrofittest.MainActivity;
import com.example.localuser.retrofittest.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by localuser on 2018/4/16.
 */

public class MyViewActivity extends AppCompatActivity{
    public static String TGA_PREFIX = "MyViewActivity--";
    private String TAG = TGA_PREFIX+getClass().getSimpleName();
    private MyView myView;
    private MyAdhesionView myAdhesionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myview_activity_main);
        myView = (MyView) findViewById(R.id.myview);
        //myView.setVisibility(View.GONE);
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
                startAnimation();
//                myView.startAnimation(animation);
                Toast.makeText(MyViewActivity.this,"click",Toast.LENGTH_LONG).show();
            }
        });

        myAdhesionView = (MyAdhesionView) findViewById(R.id.my_adhesion_view);
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
}
