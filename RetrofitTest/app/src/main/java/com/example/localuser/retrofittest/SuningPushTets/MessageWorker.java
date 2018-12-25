package com.example.localuser.retrofittest.SuningPushTets;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.longzhu.chat.MsgCallback;
import com.longzhu.chat.WsStatus;
import com.longzhu.msg.LzMsgService;
import com.longzhu.msg.WsConnectStatus;
import com.longzhu.msg.WsDataConfig;

import java.util.HashMap;
import java.util.Map;

import static com.example.localuser.retrofittest.SuningPushTets.SuningPushTestActivity.TAG_PREFIX;

public class MessageWorker implements IMessageWorker {
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private MessageService mMessageSerivce;
    private LzMsgService mLzMsgService;
    private MyHandler mHandler;
    private String roomId = "1";
    private IMessageCallback mMessageCallback;

    public MessageWorker(MessageService service)
    {
        mMessageSerivce = service;
        mHandler = new MyHandler();
    }
    public void startWork()
    {
        connectServer();
    }

    @Override
    public void stopWork() {
        mLzMsgService.onDestroy();
    }

    @Override
    public void setMessageCallback(IMessageCallback callback) {
        mMessageCallback = callback;
    }

    public void connectServer()
    {
        if (mLzMsgService == null) {
            mLzMsgService = new LzMsgService();
        }
        Map map = new HashMap<String, String>();
        map.put("roomId", String.valueOf(roomId));
        WsDataConfig wsDataConfig = new WsDataConfig.Builder()
                .setDebug(true)  //release设为false
                .setRoomId(String.valueOf(roomId))
                .setParaMap(map)
                .setDomain("http://10.200.150.3/")
                .build();
        mLzMsgService.init(wsDataConfig);
        mLzMsgService.setMsgCallback(new MsgCallback() {
            @Override
            public void onTextMessage(final String msg) {
                //此处回调在子线程中
                Log.d(TAG,"onTextMessage,msg = "+msg);
                if(mMessageCallback != null)
                {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mMessageCallback.onGetMessage(msg);
                        }
                    });
                }
            }
        });
        mLzMsgService.setWsConnectStatus(new WsConnectStatus() {
            @Override
            public void onConnectStatusChanged(WsStatus wsStatus) {
                //此处回调在子线程中
                if (wsStatus == WsStatus.CONNECTED) {
                    if(mMessageCallback != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMessageCallback.onConnected();
                            }
                        });
                    }
                }
            }
        });
    }

    public static class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}