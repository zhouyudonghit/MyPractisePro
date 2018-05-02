package com.example.localuser.retrofittest.AnimatorTest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/17.
 */

public class AnimatorTestActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animatortest_activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAnimator();
            }
        });
    }

    public void runAnimator()
    {
        ObjectAnimator.ofFloat(mTextView,"translationX",0f,250f,500f).setDuration(3000).start();
        ValueAnimator animator = ValueAnimator.ofInt(0, 100); // 产生一个从0到100变化的整数的动画
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue(); // 动态的获取当前运行到的属性值
                mTextView.setText(value + "");
            }
        });
        animator.start(); // 开始播放动画
    }
}
