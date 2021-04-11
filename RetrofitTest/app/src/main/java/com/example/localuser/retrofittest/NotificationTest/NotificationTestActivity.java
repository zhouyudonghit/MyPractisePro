package com.example.localuser.retrofittest.NotificationTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.localuser.retrofittest.R;

import java.util.Date;
import java.util.Timer;

import static android.app.Notification.FLAG_NO_CLEAR;

public class NotificationTestActivity extends AppCompatActivity{
    private Button mSend,mUpdate;
    private NotificationManager notificationManager;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mSend = findViewById(R.id.send_notification);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendNotificationWithPendingIntent();
                monitorDownload();
            }
        });
        mUpdate = findViewById(R.id.update_notification);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });
    }

    public void sendNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setContentTitle("test notifiy")
                .setContentText("notify content")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.icn_1))
                .setSmallIcon(R.mipmap.icn_1,10000)
                .setWhen(System.currentTimeMillis())
                .setTicker("set ticker")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setProgress(100,30,false);
        notificationManager.notify(1,builder.build());
    }

    public void sendNotification1()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_layout);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.icn_1))
                .setSmallIcon(R.mipmap.icn_1)//必须设置这个，否则报错
                .setDefaults(Notification.DEFAULT_SOUND);
        notificationManager.notify(1,builder.build());
    }

    public void sendNotificationWithPendingIntent()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setSmallIcon(R.mipmap.icn_1);
        builder.setContentTitle("notify title");
        builder.setContentText("notify context");
        builder.setWhen(new Date().getTime());
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(),NotificationTestActivity.class.getName()));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        notificationManager.notify(1,builder.build());
    }

    public void updateNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setContentTitle("test notifiy")
                .setContentText("notify content2")
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.content_films))
                .setSmallIcon(R.mipmap.content_music)
                .setWhen(System.currentTimeMillis())
                .setTicker("set ticker")
                .setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }

    public void monitorDownload()
    {
        AsyncTask<Void,Integer,Void> asyncTask = new AsyncTask<Void,Integer,Void>() {
            @Override
            protected Void doInBackground(Void[] objects) {
                int progress = 0;
                for(;progress <= 100;progress+=10) {
                    try {
                        publishProgress(progress);
                        if(progress == 100)
                        {
                            notificationManager.cancel(1);
                            break;
                        }
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer[] values) {
                if(values[0] == 100)
                {
                    notificationManager.cancel(1);
                    return;
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationTestActivity.this, "1");
                builder.setContentText("progress " + values[0] + "%");
                builder.setContentTitle("download progress");
                builder.setSmallIcon(R.mipmap.icn_2);
                builder.setWhen(new Date().getTime());
                if (values[0] == 0) {
                    builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
                }
                builder.setProgress(100,values[0],false);
                Notification notification = builder.build();
                notification.flags |= FLAG_NO_CLEAR;
                notificationManager.notify(1,notification);
            }
        };
        asyncTask.execute();
    }
}
