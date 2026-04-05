package com.example.localuser.retrofittest.MergeTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
