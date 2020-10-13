package com.example.localuser.retrofittest.MaterialDesignTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import com.example.localuser.retrofittest.R;

/**
 * 参考链接：https://www.jianshu.com/p/c6a6d08f4a2b
 */
public class CoordinatorLayoutCollapsingToolbarLayoutTestActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_collapsingtoolbarlayout);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("test");
    }
}
