package com.example.localuser.retrofittest.SharedPreferenceTest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class SharedPreferenceTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_SHARED_PREFERENCE +getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils.set(this,"var","test string");
        String action = "pptv_tvsports://tvsports_web?from_internal=1&h5=http://m.ppsport.com/ott/h5/activity/2";
        Uri uri = Uri.parse(action);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        Log.d("zhouyudong","uri = "+uri);
        Log.d(TAG,SPUtils.contains(this,"v")+"");
        Log.d(TAG,SPUtils.contains(this,"v%s")+"");
    }
}
