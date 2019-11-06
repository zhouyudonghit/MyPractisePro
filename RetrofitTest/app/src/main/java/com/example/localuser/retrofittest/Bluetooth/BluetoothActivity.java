package com.example.localuser.retrofittest.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.localuser.retrofittest.Bluetooth.Events.BaseEvent;
import com.example.localuser.retrofittest.Bluetooth.Events.ReceiveDataEvent;
import com.example.localuser.retrofittest.ListViewTest.ListViewTestActivity;
import com.example.localuser.retrofittest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    public static String SERVER_SOCKET_NAME = "my_bluetooth_server";
    public static String TAG_PREFIX = "Bluetooth--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private BluetoothReceiver bluetoothReceiver;
    private Button mButton,connectBtn,sendBtn;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> bondedDevices = new HashSet<>();
    private BluetoothDevice bondedDevice;
    private BluetoothSocket mSocket;
    BluetoothSocket clienSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);
        mButton = (Button) findViewById(R.id.open_bluetooth);
        connectBtn = (Button) findViewById(R.id.create_connection);
        sendBtn = (Button) findViewById(R.id.send_message);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isEnabled()) {
                    //弹出对话框提示用户是后打开
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enabler);
                    if(bluetoothAdapter.enable()) {
                        Log.d(TAG,"打开蓝牙成功");
                    }else{
                        Log.d(TAG,"打开蓝牙失败");
                    }
                    Intent intent =  new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivity(intent);
                }
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createClient();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WriteThread("test message").start();
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothReceiver = new BluetoothReceiver();
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        foundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        foundFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bluetoothReceiver,foundFilter);

        getBondedDevices();
        createServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
    }

    public class BluetoothReceiver extends BroadcastReceiver {
        private String TAG = BluetoothActivity.TAG_PREFIX+getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getAction());
            if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND))
            {

            }else if(intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){

            }else if(intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG,"正在配对");
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG,"完成配对");
                } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG,"取消配对");
                }
            }
        }
    }

    public void getBondedDevices()
    {
        bondedDevices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device:bondedDevices)
        {
            bondedDevice = device;
        }
    }

    public void createServer()
    {
        new ServerThread().start();
    }

    public class ServerThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            UUID uuid = UUID.fromString(SPP_UUID);
            try {
                BluetoothServerSocket serverSocket = bluetoothAdapter. listenUsingRfcommWithServiceRecord(SERVER_SOCKET_NAME,uuid);
                mSocket = serverSocket.accept();
                Log.d(TAG,"server create success");

                new ReadThread().start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createClient()
    {
        new ClientThread().start();
    }

    private class ClientThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            UUID uuid = UUID.fromString(SPP_UUID);
            try {
                mSocket = bondedDevice.createRfcommSocketToServiceRecord(uuid);
                mSocket.connect();
                Log.d(TAG,"connect server success");
                new ReadThread().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读取数据
    private class ReadThread extends Thread {
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream is = null;
            try {
                is = mSocket.getInputStream();
                while (true) {
                    if ((bytes = is.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        EventBus.getDefault().post(new ReceiveDataEvent(new String(buf_data,"utf-8")));
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private class WriteThread extends Thread
    {
        private String msg;

        public WriteThread(String msg)
        {
            this.msg = msg;
        }

        @Override
        public void run() {
            super.run();
            if (mSocket == null) {
                Toast.makeText(BluetoothActivity.this, "没有连接", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                OutputStream os = mSocket.getOutputStream();
                os.write(msg.getBytes());
                Log.d(TAG,"send over");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(BaseEvent event){
        Log.d(TAG,"receive event = "+event);
        if(event instanceof ReceiveDataEvent)
        {
            Toast.makeText(this,((ReceiveDataEvent) event).data,Toast.LENGTH_LONG).show();
            new WriteThread("receive data").start();
        }
    }
}
