package com.example.localuser.retrofittest.ShareAnimator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.localuser.retrofittest.R;

public class ShareAnimatorActivity extends AppCompatActivity {
    public static String TAG_PREFFIX = "ShareAnimatorActivity--";
    private String TAG = TAG_PREFFIX + getClass().getSimpleName();
    private ImageView shareImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"oncreate");
        setContentView(R.layout.activity_share_animator_main);
        shareImg = (ImageView) findViewById(R.id.share_pic);
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick");
                Intent intent = new Intent(ShareAnimatorActivity.this,SecondShareAnimActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(ShareAnimatorActivity.this,shareImg,getString(R.string.share_pic_str)).toBundle();
                startActivity(intent,bundle);
            }
        });
    }
}
