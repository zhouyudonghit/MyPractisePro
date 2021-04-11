package com.example.localuser.retrofittest.SurfaceViewTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,

                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        DisplayMetrics outMetrics = new DisplayMetrics();

        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        GameSurfaceView.SCREEN_WIDTH = outMetrics.widthPixels;

        GameSurfaceView.SCREEN_HEIGHT = outMetrics.heightPixels;

        GameSurfaceView gameView = new GameSurfaceView(this);

        setContentView(gameView);
    }
}
