package com.example.localuser.retrofittest.Bluetooth.Events;

public class ReceiveDataEvent extends BaseEvent {
    public String data;
    public ReceiveDataEvent(String msg)
    {
        this.data = msg;
    }
}
