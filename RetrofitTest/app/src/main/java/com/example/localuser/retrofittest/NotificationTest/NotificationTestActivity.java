package com.example.localuser.retrofittest.NotificationTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.R;

public class NotificationTestActivity extends AppCompatActivity{
    private Button mSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test_main);
        mSend = findViewById(R.id.send_notification);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
