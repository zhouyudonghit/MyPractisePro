package com.example.localuser.retrofittest.FoResultTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class ForResultTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_FORRESULT_TEST + getClass().getSimpleName();
    private Button mJumpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forresult_test_main);
        mJumpButton = findViewById(R.id.jump);
        mJumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForResultTestActivity.this,TargetActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult,requestCode = "+requestCode + ",resultCode = "+resultCode);
    }
}
