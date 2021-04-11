package com.example.localuser.retrofittest.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.ScalingUtil;

public class ConstraintLayoutTestActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;
    private ImageView mBackgroundImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_constraintlayout_test_activity_new);
        mLinearLayout = (LinearLayout) findViewById(R.id.general_layout);
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mBackgroundImageView = (ImageView) findViewById(R.id.background_imageview);
//        mBackgroundImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                //调整背景图片的高度
//                adjustViews();
//            }
//        });
        adjustViews();
    }

    //调整背景图片的高度
//    private void adjustBackgroundImageViewHeight()
//    {
//        Log.d(TAG,"mBackgroundImageView.getHeight() = "+mBackgroundImageView.getHeight());
//        Log.d(TAG,"mConstraintLayout.getHeight() = "+mConstraintLayout.getHeight());
//        Log.d(TAG,"mScrollView.getHeight() = "+ mScrollView.getHeight());
//        Point outSize = new Point();
//        getWindowManager().getDefaultDisplay().getRealSize(outSize);
//        int x = outSize.x;
//        int y = outSize.y;
//        Log.d(TAG, "x = " + x + ",y = " + y);
//        int height = Math.max(mConstraintLayout.getHeight(),mScrollView.getHeight());
////        int height = x*812/375;
//        Log.d(TAG,"height = "+height);
//        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mBackgroundImageView.getLayoutParams();
//        lp.height = height;
//        mBackgroundImageView.setLayoutParams(lp);
//        mBackgroundImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG,"mConstraintLayout.getHeight() = "+mConstraintLayout.getHeight());
//                Log.d(TAG,"mScrollView.getHeight() = "+ mScrollView.getHeight());
//            }
//        });
//
//        ScrollView.LayoutParams lp2 = (ScrollView.LayoutParams) mConstraintLayout.getLayoutParams();
//        lp2.height = height;
//        mConstraintLayout.setLayoutParams(lp2);
//        mConstraintLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG,"mConstraintLayout.getHeight() = "+mConstraintLayout.getHeight());
//                Log.d(TAG,"mScrollView.getHeight() = "+ mScrollView.getHeight());
//            }
//        });
//    }

    private static int IMAGE_WIDTH = 375;
    private static int IMAGE_HEIGHT = 812;

    private void adjustViews()
    {
        Log.d(TAG,"mLinearLayout.getHeight() = "+mLinearLayout.getHeight()+",mLinearLayout.getWidth() = "+mLinearLayout.getWidth());
        Log.d(TAG,"mScrollView.getHeight() = "+ mScrollView.getHeight()+",mScrollView.getWidth() = "+mScrollView.getWidth());
        Log.d(TAG,"mBackgroundImageView.getHeight() = "+mBackgroundImageView.getHeight()+","+mBackgroundImageView.getWidth());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Log.d(TAG,"dm.density = "+dm.density);
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int windowWidth = outSize.x;
        int windowHeight = outSize.y;
        float scale = windowWidth/(IMAGE_WIDTH*dm.density);
        Log.d(TAG,"scale = "+scale);
        ScalingUtil.scaleViewAndChildren(mLinearLayout,scale);
        mBackgroundImageView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"mLinearLayout.getHeight() = "+mLinearLayout.getHeight()+",mLinearLayout.getWidth() = "+mLinearLayout.getWidth());
                Log.d(TAG,"mScrollView.getHeight() = "+ mScrollView.getHeight()+",mScrollView.getWidth() = "+mScrollView.getWidth());
            }
        });
    }
}
