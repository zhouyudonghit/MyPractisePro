package com.example.localuser.retrofittest.statusbartest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.StatusBarUtil;

import java.lang.reflect.Field;

public class StatusbarTestActivity extends AppCompatActivity {
    private Button low_profile;
    private View statusBarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //这里调用无效
//        method1();
        //这里调用无效
//        method2();
        //这里调用有效
//        method3();
        //效果不好
//        method4();
        //这里调用有效
//        method5();
        //设置成全屏，跟状态背景无关，反而是把状态栏隐藏了
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statusbar_test_main);
        initView();
        StatusBarUtil.setStatusBarDarkIcon(this, true, true);
        statusBarColor();
        getWindowManager();
//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                if (isStatusBar()) {
//                    initStatusBar();
//                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                        @Override
//                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                            initStatusBar();
//                        }
//                    });
//                }
//                //只走一次
//                return false;
//            }
//        });
        //这里调用无效
//        method1();
        //这里调用无效
//        method2();
        //这里调用有效
//        method3();
        //设置状态栏透明
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////        if (!showNavigation){
////            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
////        }
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected boolean isStatusBar() {
        return true;
    }

    private void initView()
    {
        low_profile = findViewById(R.id.SYSTEM_UI_FLAG_LOW_PROFILE);
        low_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                  //这里调用无效
//                method1();
                //这里调用无效
//                method2();
                //这里调用有效
//                method3();
                //效果不好
//                method4();
                //这里调用也有效
                method5();
            }
        });
    }

    private void method1()
    {
        //设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果。这个标志一般用于游戏，电子书，视频，或者不需要去分散用户注意力的应用软件
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    private void method2()
    {
        //隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void method3()
    {
        //隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现。
        //有效，状态栏是一黑条，从状态栏位置下拉会出现
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void method4()
    {
        //将布局内容拓展到导航栏的后面
        //无效
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    private void method5()
    {
        //将布局内容拓展到状态的后面
        //有效，zuk手机上测试有效，布局拓展至状态栏后面，即状态栏如果透明，可以看到底下的布局
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
    }
}
