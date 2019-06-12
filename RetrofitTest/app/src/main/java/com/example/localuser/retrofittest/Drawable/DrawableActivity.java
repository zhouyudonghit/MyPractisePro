package com.example.localuser.retrofittest.Drawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.R;

public class DrawableActivity extends AppCompatActivity {
    public static String TAG_PREX = "DrawableActivity--";
    private String TAG = TAG_PREX+getClass().getSimpleName();
    private MyDrawableView myDrawableView;
    private MyDrawLineDrawable myDrawable;
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
