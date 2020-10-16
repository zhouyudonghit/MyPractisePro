package com.example.localuser.retrofittest.MaterialDesignTest.BottomSheetBehaviorTest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copymeituan_bottom_sheet_test);
        //初始屏幕相关的参数
        initSystem();
        initView();
        initBehavior();
    }

    private void initView()
    {
        maskView = findViewById(R.id.maskView);
        tabLayout = findViewById(R.id.);
    }

    private void initBehavior()
    {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(nestedScrollView)
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                //如果控件本身的Height值就小于返回按钮的高度，就不用做处理
                if(view.getHeight() > heightPixels - marginTop)
                {
                    layoutParams.height = heightPixels - marginTop;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float distance = 0;

            }
        });
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            val layoutParams = bottomSheet.layoutParams
            //如果控件本身的Height值就小于返回按钮的高度，就不用做处理
            if (bottomSheet.height > heightPixels - marginTop) {
                //屏幕高度减去marinTop作为控件的Height
                layoutParams.height = heightPixels - marginTop
                bottomSheet.layoutParams = layoutParams
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            var distance: Float = 0F;
            /**
             * slideOffset为底部的新偏移量，值在[-1,1]范围内。当BottomSheetBehavior处于折叠(STATE_COLLAPSED)和
             * 展开(STATE_EXPANDED)状态之间时,它的值始终在[0,1]范围内，向上移动趋近于1，向下区间于0。[-1,0]处于
             * 隐藏状态(STATE_HIDDEN)和折叠状态(STATE_COLLAPSED)之间。
             */

            //这里的BottomSheetBehavior初始化完成后，界面设置始终可见，所以不用考虑[-1,0]区间
            //色差值变化->其实是遮罩的透明度变化，拖拽至最高，顶部成半透明色
            maskView.alpha = slideOffset
            //offsetDistance是initSystem()中获得的，是返回按钮至根布局的距离
            distance = offsetDistance * slideOffset
            //当BottomSheetBehavior由隐藏状态变为折叠状态(即gif图开始的由底部滑出至设置的最小高度)
            //slide在[-1,0]的区间内，不加判断会出现顶部布局向下偏移的情况。
            if (distance > 0) {
                constraint.translationY = -distance
            }

            Log.i(
                    TAG,
                    String.format(
                            "slideOffset -->>> %s bottomSheet.getHeight() -->>> %s heightPixels -->>> %s",
                            slideOffset,
                            bottomSheet.height,
                            heightPixels
                    )
            )
            Log.i(TAG, String.format("distance -->>> %s", distance))
        }

    })
        mHandler.postDelayed({
                behavior.isHideable = false
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                behavior.peekHeight = peekHeight
                ObjectAnimator.ofFloat(nestedScrollView, "alpha", 0f, 1f).setDuration(500).start()
        }, 200)
    }
}
