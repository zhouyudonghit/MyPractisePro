package com.example.localuser.retrofittest.ViewpagerTest;

import android.os.Bundle;

public class MyFragment4 extends BaseFragment {
    public static BaseFragment getInstance(String str, int layoutId)
    {
        BaseFragment mFragment = new MyFragment4();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_STRING,str);
        bundle.putInt(LAYOUT_RES_ID,layoutId);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    public static BaseFragment getInstance(String str)
    {
        return getInstance(str,-1);
    }
}
