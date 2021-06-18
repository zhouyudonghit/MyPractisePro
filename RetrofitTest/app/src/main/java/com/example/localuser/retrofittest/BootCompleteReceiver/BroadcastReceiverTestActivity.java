package com.example.localuser.retrofittest.BootCompleteReceiver;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

import java.util.List;

public class BroadcastReceiverTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_BROADCAST_TEST + getClass().getSimpleName();
    private TextView tv1,tv2,tv3;
    private BootCompletedReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_broadcast_test_activity_main);
        mReceiver = new BootCompletedReceiver();
        tv1 = findViewById(R.id.register);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"isReceiverRegistered() = "+isReceiverRegistered());
                register();
                Log.d(TAG,"isReceiverRegistered() = "+isReceiverRegistered());
            }
        });
        tv2 = findViewById(R.id.unregister);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"isReceiverRegistered() = "+isReceiverRegistered());
                unregister();
                Log.d(TAG,"isReceiverRegistered() = "+isReceiverRegistered());
            }
        });

        tv3 = findViewById(R.id.send);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent(BootCompletedReceiver.ACTION));
            }
        });
    }

    private void register()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BootCompletedReceiver.ACTION);
        registerReceiver(mReceiver,intentFilter);
    }

    private void unregister()
    {
        unregisterReceiver(mReceiver);
    }

    private boolean isReceiverRegistered()
    {
        boolean registered = false;
        Intent intent = new Intent();
        intent.setAction(BootCompletedReceiver.ACTION);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        if(resolveInfos != null && !resolveInfos.isEmpty()){
            //查询到相应的BroadcastReceiver
            registered = true;
        }
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        return registered;
    }
}
