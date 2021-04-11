package com.example.localuser.retrofittest.SerializeTest;

import android.os.Parcel;

public class C extends B {
    private int c;

    public C()
    {

    }

    protected C(Parcel in) {
        super(in);
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return super.toString() + "c = "+c;
    }
}
