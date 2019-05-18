package com.example.localuser.retrofittest.ViewpagerTest;

import android.os.Bundle;

public class MyFragment1 extends BaseFragment {
    public static BaseFragment getInstance(String str,int layoutId)
    {
        BaseFragment mFragment = new MyFragment1();
        getInstance(mFragment,str,layoutId);
        return fragment;
    }
}
