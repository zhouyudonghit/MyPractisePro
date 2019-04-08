package com.example.localuser.retrofittest.MsdnTest;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MsdnTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MSDN+getClass().getSimpleName();
    private Button StartConnect,scan;
//目的主机ip和端口
private EditText IP,Port;
//本机 监听端口
//private EditText LOCAL_PORT;
private TextView destination,porttext;

 //目的ip和端口
 private String Server_IP;
private String Server_Port;

private Map<String, Object> map;
private MyApplication myApplication;
private WIFIAdmin wifiAdmin;
private WifiManager mWifiManager;
Handler handler = new Handler();
//mdns服务类型
 private String type = "_udpserver._udp.local.";
//    private String type = "_sleep-proxy._udp.local.";
 private JmDNS jmdns = null;
private ServiceListener listener = null;
private ServiceInfo serviceInfo;
 //组播锁
private WifiManager.MulticastLock lock;
//展示扫描的mDNS服务
private ListView mlistView;
private Utils utils = new Utils();
//存放扫描的mDNS服务
    private List<Map<String, Object>> mdnsList= new ArrayList<Map<String,Object>>();
private Map<String, Object> mdnsMap;
    private boolean flag =true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdn_test_main);

    }
}
