package com.example.localuser.retrofittest.SerializeTest;

public class A {
    protected int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return " a = "+a;
    }
}
