package com.example.localuser.retrofittest.autosleep;

public class AccelerometerChangedEvent extends SleepEvent{
    public float changedX;
    public float changedY;
    public float changedZ;

    public AccelerometerChangedEvent(float x,float y,float z)
    {
        changedX = x;
        changedY = y;
        changedZ = z;
    }

    public String myString() {
        return TAG+"{" +
                "changedX=" + changedX +
                ", changedY=" + changedY +
                ", changedZ=" + changedZ +
                '}';
    }
}