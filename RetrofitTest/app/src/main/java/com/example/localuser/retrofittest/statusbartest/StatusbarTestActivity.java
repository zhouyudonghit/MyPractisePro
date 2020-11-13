package com.example.localuser.retrofittest.statusbartest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.R;

public class StatusbarTestActivity extends AppCompatActivity {
    private Button low_profile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        method1();
        setContentView(R.layout.activity_statusbar_test_main);
        initView();
    }

    private void initView()
    {
        low_profile = findViewById(R.id.SYSTEM_UI_FLAG_LOW_PROFILE);
        low_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
        });
    }

    private void method1()
    {
        //设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果。这个标志一般用于游戏，电子书，视频，或者不需要去分散用户注意力的应用软件
        //有效，状态栏电池、网络图标会消失，导航栏没什么变化，下拉状态栏图标会出现
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    private void method2()
    {
        //有效
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
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
