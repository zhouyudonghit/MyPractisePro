package com.example.localuser.retrofittest.SocketTest;

import android.util.Log;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPServer {
    private String TAG = SocketTestActivity.TAG_PREFIX+getClass().getSimpleName();
	public final static String UDP_BROADCAST_IP = "225.0.0.112";
	private MulticastSocket sock;
    private InetAddress multicastInet;
    private Thread serverThread;
    private int mUserDataMaxLen;
    private volatile boolean mOpenFlag;
    private int port = 7912;
    
	/**
     * ��
     * ����������
     */
    public synchronized boolean init() {
        try {
            sock = new MulticastSocket(port);
            multicastInet = InetAddress.getByName(UDP_BROADCAST_IP);
            sock.joinGroup(multicastInet);
            sock.setLoopbackMode(false);
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return false;
        }

        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiveAndSend();
            }
        });
        mOpenFlag = true;
        serverThread.start();
        return true;
    }
    
    public void receiveAndSend() {
        byte[] buf = new byte[2024];
        DatagramPacket recePack = new DatagramPacket(buf, buf.length);

        if (sock == null || sock.isClosed() || recePack == null) {
            return;
        }

        while (mOpenFlag) {
            try {
                // waiting for search from host
                sock.receive(recePack);
                Log.d(TAG,"server after =receive,recePack.getLength() = "+recePack.getLength());
                // verify the data
                if (verifySearchReq(recePack)) {
                	
                    ResponseBean rb = new ResponseBean();
                    rb.setSn_cmd_id(0);
                    rb.setSn_device_id("test_device_id");
                    rb.setSn_tcp_port(5666);
                    rb.setSn_model_id("test_model_id");
                    rb.setSn_wifi_mac("test_wifi_mac");
                    String json = new Gson().toJson(rb);
                    byte[] bytes = json.getBytes("utf-8");
                    byte[] lengthBytes = intToBytes(bytes.length);

                    byte[] sendData = new byte[4+bytes.length];
                    System.arraycopy(lengthBytes,0,sendData,0,4);
                    System.arraycopy(bytes,0,sendData,4,bytes.length);
                    if (sendData == null) {
                        return;
                    }

                    Log.d(TAG,"recePack.getAddress():"+recePack.getAddress().getHostAddress());
                    DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length,
                            recePack.getAddress(), recePack.getPort());
                    sock.send(sendPack);
                }

            } catch (IOException e) {
                break;
            }
        }
    }
    
    /**
     * �ر�
     */
    public synchronized void close() {
       
        mOpenFlag = false;
        if (serverThread != null) {
            serverThread.interrupt();
        }

        if (sock != null) {
            try {
                sock.leaveGroup(multicastInet);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                sock.close();
                sock = null;
            }
        }
    }
    
    private boolean verifySearchReq(DatagramPacket pack) {
       

        
        return true;
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[3] = (byte) ((value >> 24) & 0xFF);
        return src;
    }

    public static int bytesToInt(byte[] src) {
        if (src == null || src.length != 4) {
            return -1;
        }
        int sendSeq;
        sendSeq = src[0] & 0xFF;
        sendSeq |= (src[1] << 8) & 0xFF00;
        sendSeq |= (src[2] << 16) & 0xFF0000;
        sendSeq |= (src[3] << 24) & 0xFF000000;
        return sendSeq;
    }
}
