package com.example.localuser.retrofittest.SuningPushTets;

public interface IMessageWorker {
    void startWork();
    void stopWork();
    void setMessageCallback(IMessageCallback callback);
}
