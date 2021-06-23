package com.example.localuser.retrofittest.imageviewtest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;

public class ImageViewTestActivity2 extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    public static final int IMAGEVIEW_SUGGEST_WIDTH = 282;//dp
    public static final int IMAGEVIEW_SUGGEST_HEIGHT = 285;//dp
    private ImageView mImageView;
    private ImageView mImageView2;
    private float scale = 1.04f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_imageview_test_activity2);
        mImageView = findViewById(R.id.rule_image);
        mImageView2 = findViewById(R.id.rule_image_2);
        final Drawable drawable = getDrawable(R.drawable.medal_rule_test);
        Log.d(TAG,"drawable.getIntrinsicHeight() = "+drawable.getIntrinsicHeight()+",drawable.getMinimumWidth() = "+drawable.getMinimumWidth());
//        mImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                adjustImageView(drawable);
//            }
//        });
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"mImageView.getWidth() = "+mImageView.getWidth()+",mImageView.getHeight() = "+mImageView.getHeight());
                Log.d(TAG,"mImageView.getMeasuredWidth() = "+mImageView.getMeasuredWidth()+",mImageView.getMeasuredHeight() = "+mImageView.getMeasuredHeight());
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
//                lp.height = 1031;
//                mImageView.setLayoutParams(lp);
                test();
            }
        });
    }

    private void adjustImageView(Drawable resource)
    {
        int picWidth = resource.getIntrinsicWidth();
        int picHeight = resource.getIntrinsicHeight();
        Log.d(TAG,"picWidth = "+picWidth+",picHeight = "+picHeight);
        if(picWidth == 0 || picHeight == 0)
        {
            return;
        }
        int desiredHeight = (int) ((AppUtils.dp2px(IMAGEVIEW_SUGGEST_WIDTH)*scale*1.0f/picWidth)*picHeight);
        if(desiredHeight < AppUtils.dp2px(IMAGEVIEW_SUGGEST_HEIGHT)*scale)
        {
            desiredHeight = (int) (AppUtils.dp2px(IMAGEVIEW_SUGGEST_HEIGHT)*scale);
        }

        Log.d(TAG,"desiredHeight = "+ desiredHeight);
        final int finalDesiredHeight = desiredHeight;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        lp.height = finalDesiredHeight;
        mImageView.setLayoutParams(lp);
    }

    private void test()
    {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
        lp.height = AppUtils.dp2px(200);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) mImageView2.getLayoutParams();
        lp2.height = AppUtils.dp2px(200);
        mImageView2.setLayoutParams(lp2);
        //实验验证，第一个ImageView的高度设置有效
    }
}
