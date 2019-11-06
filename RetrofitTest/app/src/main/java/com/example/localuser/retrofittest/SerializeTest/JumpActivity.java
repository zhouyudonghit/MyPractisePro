package com.example.localuser.retrofittest.SerializeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import static com.example.localuser.retrofittest.SerializeTest.SerializeTestActivity.KEY1;
import static com.example.localuser.retrofittest.SerializeTest.SerializeTestActivity.KEY2;

public class JumpActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_SERIALIZE + getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if((intent = getIntent()) != null)
        {
            Log.d(TAG,"get intent");
            B b = intent.getParcelableExtra(KEY1);
            Log.d(TAG," b = " + b);

            B c = intent.getParcelableExtra(KEY2);
            Log.d(TAG,"c = "+c);
        }
    }
}
