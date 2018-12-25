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

import static com.example.localuser.retrofittest.SuningPushTets.SuningPushTestActivity.TAG_PREFIX;

public class SuningPushTestActivity2 extends AppCompatActivity{
        private TextView startTV,stopTV;
        private MessageService mService;
        private boolean isBinded = false;
        private ServiceConnection serviceConnection;
        private String TAG = TAG_PREFIX+getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushing_test_2);
        startTV = findViewById(R.id.start_service);
        stopTV = findViewById(R.id.stop_service);
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
                Intent intent = new Intent(SuningPushTestActivity2.this,MessageService.class);
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
    }
}
