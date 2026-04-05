package com.example.localuser.retrofittest.MaterialDesignTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.localuser.retrofittest.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
