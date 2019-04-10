package com.example.localuser.retrofittest.MdnsTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class MdnsTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MDNS+getClass().getSimpleName();
    private NSDClient mClient;
    private NSDServer mServer;
    private Button startButton,scanButton;
    private static String SERVICE_NAME = "test_nsd_server_zyd";
    private static int SERVICE_PORT = 62202;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdn_test_main);
        initData();
        initView();
    }

    public void initData()
    {
        mClient = new NSDClient(this);
        mServer = new NSDServer(this);
    }

    public void initView()
    {
        startButton = findViewById(R.id.start_server);
        scanButton = findViewById(R.id.scan_server);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServer.registerService(SERVICE_NAME,SERVICE_PORT);
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.discoverNsdServer();
            }
        });
    }
}
