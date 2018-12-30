package com.example.localuser.retrofittest.NotificationTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
                sendNotification();
            }
        });
    }

    public void sendNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setContentTitle("test notifiy").setContentText("notify content")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.content_films))
                .setSmallIcon(R.mipmap.content_music).setWhen(System.currentTimeMillis())
                .setTicker("set ticker").setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
