package com.example.localuser.retrofittest.MergeTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.localuser.retrofittest.R;

public class MergeTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "MergeTest--";
    public String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_merge_test_activity);
    }
}
