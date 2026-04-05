package com.example.localuser.retrofittest.ShareAnimator;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.R;

public class SecondShareAnimActivity extends AppCompatActivity {
    private String TAG = ShareAnimatorActivity.TAG_PREFFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_sencond_share_animator_main);
    }
}
