package com.example.localuser.retrofittest.SocketTest;

public interface CommCallBack<T> {
    void doSuccess(T t);
    void doFail(Exception e,String dest);
}
