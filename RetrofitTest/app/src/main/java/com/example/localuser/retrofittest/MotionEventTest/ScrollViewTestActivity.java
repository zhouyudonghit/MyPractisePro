package com.example.localuser.retrofittest.MotionEventTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localuser.retrofittest.R;

public class ScrollViewTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrollview_test_activity);
        MyScrollView scrollView = findViewById(R.id.scrollview);
        scrollView.requestDisallowInterceptTouchEvent(true);
    }
}
