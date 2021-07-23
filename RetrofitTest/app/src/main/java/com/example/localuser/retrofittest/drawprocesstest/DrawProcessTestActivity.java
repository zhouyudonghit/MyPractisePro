package com.example.localuser.retrofittest.drawprocesstest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.example.localuser.retrofittest.Utils.ScreenUtils;

public class DrawProcessTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_DRAW_PROCESS_TEST + getClass().getSimpleName();
    private MyLinearLayout myLinearLayout;
    private MyTextView myTextView1,myTextView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_process_test_main);
        initView();
    }

    private void initView()
    {
        myTextView1 = findViewById(R.id.tv1);
        myTextView2 = findViewById(R.id.tv2);
        myTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myTextView1.requestLayout();
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) myTextView1.getLayoutParams();
//                Log.d(TAG,"lp = "+lp);
//                lp.height = AppUtils.dp2px(160);
//
                LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) myTextView2.getLayoutParams();
                Log.d(TAG,"lp2 = "+lp2);
                lp2.height = AppUtils.dp2px(40);
                myTextView2.setLayoutParams(lp2);
            }
        });
        myTextView2.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                Exception exception = new Exception();
                Log.e(TAG,"",exception);
                Log.d(TAG,"ViewTreeObserver.OnDrawListener(),onDraw()");
            }
        });
    }
}
