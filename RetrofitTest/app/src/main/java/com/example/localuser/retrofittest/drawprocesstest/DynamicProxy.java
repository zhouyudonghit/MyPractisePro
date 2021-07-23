package com.example.localuser.retrofittest.drawprocesstest;

import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {
    protected ViewGroup mViewGroup;
    public DynamicProxy(ViewGroup viewGroup)
    {
        mViewGroup = viewGroup;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(mViewGroup, args);
    }
}
