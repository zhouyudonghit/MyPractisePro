package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.localuser.retrofittest.R;

/**
 * 用Flipper不太靠谱，有时候会水平混动，可以会卡顿
 */
public class VerticalMarqueeView extends LinearLayout {
    public VerticalMarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initView();
    }

    public void initView()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_vertical_marqueen,this);
    }
}
