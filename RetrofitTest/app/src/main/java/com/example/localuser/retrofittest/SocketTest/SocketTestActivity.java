package com.example.localuser.retrofittest.SocketTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "SocketTest--";
    private String TAG = TAG_PREFIX + "SocketTestActivity";
    private final static int DEFAULT_SIZE = 1024;
    private TextView connectServerTV,sendDataTV,startUDPTV;
    private Socket mSocket;
    private String serverIP = "10.200.166.54";
    private int serverPort = 10086;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test_main);
        connectServerTV = findViewById(R.id.connect_server);
        sendDataTV = findViewById(R.id.send_data);
        startUDPTV = findViewById(R.id.start_udp_server);

        connectServerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClientThread(serverIP,serverPort).start();
            }
        });

        sendDataTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WriteThread().start();
            }
        });

        startUDPTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UDPServer server = new UDPServer();
                server.init();
            }
        });
    }

    public class ClientThread extends Thread
    {
        private String mIp;
        private int mPort;

        public ClientThread(String ip,int port)
        {
            mIp = ip;
            mPort = port;
        }

        @Override
        public void run() {
            try {
                mSocket = new Socket(mIp,mPort);
                //mSocket.setKeepAlive(true);
                new ReadThread().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class WriteThread extends Thread
    {
        private OutputStream os;
        private BufferedOutputStream bos;

        @Override
        public void run() {
            if(mSocket == null || mSocket.isClosed() || !mSocket.isConnected())
            {
                return;
            }
            try {
                os = mSocket.getOutputStream();
                bos = new BufferedOutputStream(os);
                String s = "hello,server";
                bos.write(s.getBytes("utf-8"));
                bos.flush();
                bos.write(0);
                bos.flush();
                Log.d(TAG,"send data over:"+s);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
//                if(bos != null)
//                {
//                    try {
//                        os.close();
//                        bos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    }

    public class ReadThread extends Thread
    {
        private InputStream is;
        private BufferedInputStream bis;

        @Override
        public void run() {
            Log.d(TAG,"a new client is running");
            if(mSocket == null)
            {
                return;
            }
            try {
                is = mSocket.getInputStream();
                bis = new BufferedInputStream(is);
                byte[] originData = new byte[DEFAULT_SIZE];
                while (bis != null)
                {
                    byte[] temp = null;
                    int readLength;
                    int totalLength = 0;
                    while((readLength = bis.read(originData)) != -1)
                    {
                        if(temp == null)
                        {
                            //解析长度
                            int length = 1024;
                            temp = new byte[length];
                        }
                        if(readLength == 1)
                        {
                            int data = SocketUtils.bytes2Int(originData);
                            break;
                        }
                        totalLength += readLength;
                        System.arraycopy(originData, 0, temp, 0, readLength);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(new String(temp,0,totalLength,"utf-8"));
                    Log.d(TAG,"receive data:"+sb.toString());
                    //new WriteThread().start();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
//                if(bis != null)
//                {
//                    try {
//                        is.close();
//                        bis.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    }
}
