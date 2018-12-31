package com.example.localuser.retrofittest.JsonTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.R;
import com.google.gson.Gson;

import java.util.Date;

public class JsonTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "jsontest--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test_main);
        TestBean testBean = new TestBean();
        testBean.date = new Date();
        String json = new Gson().toJson(testBean);
        Log.d(TAG,json);
        //Dec 27, 2018 18:33:55
            json = "{\"date\":\"Dec 27, 2018 18:33:55 am\"}";
        try{
            TestBean testBean1 = new Gson().fromJson(json,TestBean.class);
            Log.d(TAG,testBean1.date.toString());
        }catch (Exception e)
        {
            Log.d(TAG,"",e);
        }
    }
}
