package com.example.localuser.retrofittest.Drawable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class DrawableActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_DRAWABLE_TEST +getClass().getSimpleName();
    private MyDrawableView myDrawableView;
    private MyDrawLineDrawable myDrawable;
    private View drawableTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_main);
        myDrawableView = (MyDrawableView) findViewById(R.id.drawable_view);

        myDrawableView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"view.post.run");
                Log.d(TAG,"myDrawableView.getWidth() = "+myDrawableView.getWidth()+",myDrawableView.getHeight() = "+myDrawableView.getHeight());
            }
        });

        myDrawable = new MyDrawLineDrawable(myDrawableView);
        myDrawableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawable.startAnimation();
            }
        });

        drawableTest = findViewById(R.id.drawable_test);
        MyDrawable1 drawable1 = new MyDrawable1();
        drawableTest.setBackground(drawable1);
        Log.d(TAG,"drawable1.getMinimumHeight() = "+drawable1.getMinimumHeight());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
        Log.d(TAG,"myDrawableView.getWidth() = "+myDrawableView.getWidth()+",myDrawableView.getHeight() = "+myDrawableView.getHeight());
        Log.d(TAG,"myDrawableView.getMeasuredWidth() = "+myDrawableView.getMeasuredWidth()+",myDrawableView.getMeasuredHeight() = "+myDrawableView.getMeasuredHeight());
        myDrawableView.setImageDrawable(myDrawable);
        //myDrawableView.setImageDrawable(new MyDividerDrawable());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG,"onWindowFocusChanged()");
        Log.d(TAG,"myDrawableView.getWidth() = "+myDrawableView.getWidth()+",myDrawableView.getHeight() = "+myDrawableView.getHeight());

    }
}
