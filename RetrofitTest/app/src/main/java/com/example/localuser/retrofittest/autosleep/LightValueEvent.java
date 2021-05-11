package com.example.localuser.retrofittest.autosleep;

public class LightValueEvent extends SleepEvent{
    public float lightValue;

    public LightValueEvent(float lightValue)
    {
        this.lightValue = lightValue;
    }
}
