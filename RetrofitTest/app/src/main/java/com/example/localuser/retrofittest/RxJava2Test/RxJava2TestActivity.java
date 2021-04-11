package com.example.localuser.retrofittest.RxJava2Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class RxJava2TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//        EventBus.getDefault().unregister(this);
//        EventBus.getDefault().post(new Object());
        RxJava2Test test = new RxJava2Test();
        test.test();
}
}
