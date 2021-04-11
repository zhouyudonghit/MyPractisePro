package com.example.localuser.retrofittest.MatrixTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class MatrixTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MATRIX_TEST + getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_matrix_test_activity_main);
        MatrixTest matrixTest = new MatrixTest();
        matrixTest.test();
        getSystemService(Context.ACTIVITY_SERVICE);
        Log.d(TAG,"getPackageManager() = " + getPackageManager());
    }
}
