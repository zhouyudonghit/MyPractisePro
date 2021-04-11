package com.example.localuser.retrofittest.MaterialDesignTest.BottomSheetBehaviorTest;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;

/**
 * 参考链接 https://www.jianshu.com/p/04711494868e
 */
public class CopyMeiTuanBottomSheetTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MATERIAL_DESIGN + getClass().getSimpleName();
    private int heightPixels = 0;
    private int peekHeight = 0;
    private int marginTop = 0;
    private int offsetDistance = 0;
    private Handler mHandler;
    private NestedScrollView nestedScrollView;
    private View maskView;
    private TabLayout tabLayout;
    private ConstraintLayout constraint;
    private FrameLayout frameLayout;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copymeituan_bottom_sheet_test);
        initView();
        //初始屏幕相关的参数
        initSystem();
        initBehavior();
    }

    private void initView()
    {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        maskView = findViewById(R.id.maskView);
        tabLayout = findViewById(R.id.tabLayout);
        constraint = findViewById(R.id.constraint);
        frameLayout = findViewById(R.id.frameLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("费用说明"));
        tabLayout.addTab(tabLayout.newTab().setText("预定须知"));
        tabLayout.addTab(tabLayout.newTab().setText("退款政策"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                {
                    frameLayout.setBackgroundColor(Color.parseColor("#ff0000"));
                }else if(tab.getPosition() == 1)
                {
                    frameLayout.setBackgroundColor(Color.parseColor("#0000ff"));
                }else{
                    frameLayout.setBackgroundColor(Color.parseColor("#00ff00"));
                }
            }
        });
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CopyMeiTuanBottomSheetTestActivity.this, "转发", Toast.LENGTH_SHORT).show();
            }
        });
        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CopyMeiTuanBottomSheetTestActivity.this, "收藏", Toast.LENGTH_SHORT).show();
            }
        });
        mHandler = new Handler();
    }

    private void initSystem() {
        //获取屏幕高度
        heightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.i(TAG, "heightPixels: "+heightPixels);

        float behaviorHeight = AppUtils.px2dip(this, (heightPixels / 2));
        peekHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, behaviorHeight, getResources().getDisplayMetrics());
        Log.i(TAG, "peekHeight: "+peekHeight);

        imageView.post(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
                //获取状态栏高度
                int statusBarHeight = 0;
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = getResources().getDimensionPixelSize(resourceId);
                }
                //返回按钮至屏幕顶部的高度
                marginTop = imageView.getHeight() + lp.topMargin + lp.bottomMargin / 2 + statusBarHeight;
                Log.d(TAG,"marginTop = "+marginTop);
                //返回按钮至根布局的距离
                offsetDistance = lp.topMargin;
                Log.d(TAG,"offsetDistance = "+offsetDistance);
                ViewGroup.LayoutParams layoutParams = nestedScrollView.getLayoutParams();
                //如果控件本身的Height值就小于返回按钮的高度，就不用做处理
                Log.d(TAG,"nestedScrollView.getHeight() = "+nestedScrollView.getHeight());
                if(nestedScrollView.getHeight() > heightPixels - marginTop)
                {
                    layoutParams.height = heightPixels - marginTop;
                    Log.d(TAG,"layoutParams.height = "+layoutParams.height);
                    nestedScrollView.setLayoutParams(layoutParams);
                }
            }
        });
    }


    private void initBehavior()
    {
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(nestedScrollView);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float distance = 0;
                maskView.setAlpha(v);
                distance = offsetDistance*v;
                if(distance > 0)
                {
                    constraint.setTranslationY(-distance);
//                    constraint.scrollTo(0,(int)-distance);
                }
                Log.i(TAG,String.format("onSlide,slideOffset -->>> %s bottomSheet.getHeight() -->>> %s heightPixels -->>> %s",v,view.getHeight(),heightPixels));
                Log.i(TAG, String.format("distance -->>> %s", distance));
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                behavior.setHideable(false);
                behavior.setPeekHeight(peekHeight);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ObjectAnimator.ofFloat(nestedScrollView, "alpha", 0f, 1f).setDuration(500).start();
            }
        }, 200);
    }
}
