package com.example.localuser.retrofittest.DialogView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.View.RoundRectImageView;

public class MedalRuleInfoDialog extends Dialog {
    private String TAG = getClass().getSimpleName();
    public static final int IMAGEVIEW_SUGGEST_WIDTH = 282;//dp
    public static final int IMAGEVIEW_SUGGEST_HEIGHT = 390;//dp
    private ImageView mImageView;
    private ImageView mCloseImageView;
    private ScrollView mScrollView;
    private String mUrl;

    public MedalRuleInfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_medal_rule_info_dialog);
        initView();
    }

    private void initView()
    {
        mScrollView = findViewById(R.id.scrollview);
        mImageView = findViewById(R.id.rule_image);
        if(!TextUtils.isEmpty(mUrl))
        {
//            ImageLoaderUtil.getInstance().displayImage(getContext(), mUrl, mImageView, new RequestListener<Drawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    //这里是主线程
//                    //下载完图片之后，需要调整ImageView的高度
//                    adjustImageView(resource);
//                    return false;
//                }
//            });
        }
//        mImageView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    public void setImageUrl(String url)
    {
        mUrl = url;
    }

}
