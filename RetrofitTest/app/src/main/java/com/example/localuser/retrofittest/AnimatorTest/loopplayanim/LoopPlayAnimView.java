package com.example.localuser.retrofittest.AnimatorTest.loopplayanim;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 循环无限朝一个方向移动的动画
 */
public class LoopPlayAnimView extends LinearLayout implements View.OnClickListener {
    private Animation mTranslateAnim;

    public LoopPlayAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
    }
}
