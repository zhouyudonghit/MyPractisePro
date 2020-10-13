package com.example.localuser.retrofittest.MaterialDesignTest.BottomSheetBehaviorTest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

/**
 * 参考链接 https://www.jianshu.com/p/92180b45aaf7
 */
public class BottomSheetBehaviorTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MATERIAL_DESIGN + getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior_test_main);
        final BottomSheetBehavior bottomSheetBehavior= BottomSheetBehavior.from(findViewById(R.id.design_bottom_sheet1));
        //设置默认先隐藏
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据状态不同显示隐藏
//                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                }
                BottomSheetDialogTestFragment bottomSheetDialogFragmenttest=new BottomSheetDialogTestFragment();
                bottomSheetDialogFragmenttest.show(getSupportFragmentManager(),BottomSheetDialogTestFragment.class.getSimpleName());
            }
        });
        //设置监听事件
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //拖动
                Log.d(TAG,"onStateChanged,newState = "+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //状态变化
                Log.d(TAG,"onSlide,slideOffset = " + slideOffset);
            }
        });
    }
}
