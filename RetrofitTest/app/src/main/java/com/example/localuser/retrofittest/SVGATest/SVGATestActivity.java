package com.example.localuser.retrofittest.SVGATest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.localuser.retrofittest.R;
import com.opensource.svgaplayer.SVGAImageView;

public class SVGATestActivity extends AppCompatActivity {
    private SVGAImageView mSvgaImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_svga_test_activity_main);
    }
}
