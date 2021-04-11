package com.example.localuser.retrofittest.SocketTest;

public interface ICommWorker {
    void connect(String deviceId,CommCallBack callBack);
    void disconnect();
}
