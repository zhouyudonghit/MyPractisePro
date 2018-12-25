package com.example.localuser.retrofittest.SuningPushTets;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class SuningPushTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "SuningPushTest-zyd-";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private TextView startTV,stopTV,jumpTV;
    private MessageService mService;
    private boolean isBinded = false;
    private ServiceConnection serviceConnection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suningpushtest_main);
        startTV = findViewById(R.id.start_service);
        stopTV = findViewById(R.id.stop_service);
        jumpTV = findViewById(R.id.jump);
        //startService(new Intent(this,SuningPushService.class));
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"onServiceConnected");
                isBinded = true;
                mService = ((MessageService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"onServiceDisconnected");
                isBinded = false;
            }
        };
        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuningPushTestActivity.this,MessageService.class);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            }
        });
        stopTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBinded) {
                    isBinded = false;
                    unbindService(serviceConnection);
                }
            }
        });
        jumpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuningPushTestActivity.this,SuningPushTestActivity2.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(isBinded)
        {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
