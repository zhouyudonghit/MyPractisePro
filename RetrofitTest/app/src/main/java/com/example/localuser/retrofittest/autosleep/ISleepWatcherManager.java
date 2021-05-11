package com.example.localuser.retrofittest.autosleep;

public interface ISleepWatcherManager {
    void startWork();
    void stopWork(boolean needNextAlarm);
}
