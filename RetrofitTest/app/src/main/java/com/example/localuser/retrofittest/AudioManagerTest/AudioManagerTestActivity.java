package com.example.localuser.retrofittest.AudioManagerTest;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class AudioManagerTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_AUDIOMANAGER_TEST + getClass().getSimpleName();
    private Button increaseBtn,decreaseBtn;
    private TextView volume;
    private AudioManager audioManager;
    private int curVolume,maxVolume;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager_test_main);
        initview();
        initData();
    }

    private void initview()
    {
        increaseBtn = findViewById(R.id.increase);
        decreaseBtn = findViewById(R.id.decrease);
        volume = findViewById(R.id.volume);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseVolume();
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseVolume();
            }
        });
    }

    private void initData()
    {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG,"amxVolume = "+maxVolume);
        volume.setText(curVolume+"");
    }

    private void increaseVolume()
    {
        curVolume += 1;
        if(curVolume > maxVolume)
        {
            curVolume = maxVolume;
        }
        setVolume(curVolume,false);
        volume.setText(curVolume+"");
    }

    private void decreaseVolume()
    {
        curVolume -= 1;
        if(curVolume < 0)
        {
            curVolume = 0;
        }
        setVolume(curVolume,false);
        volume.setText(curVolume+"");
    }

    private int getVolume()
    {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    private void setVolume(int volume,boolean permission)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (permission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        }else{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,AudioManager.FLAG_SHOW_UI);
            curVolume = getVolume();
        }
    }
}
