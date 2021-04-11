package com.example.localuser.retrofittest.autosleep;

public abstract class SleepEvent {
    protected String TAG = getClass().getSimpleName();
    public long time;

    public String myString() {
        return TAG+"{" +
                ", time=" + time +
                '}';
    }
}
