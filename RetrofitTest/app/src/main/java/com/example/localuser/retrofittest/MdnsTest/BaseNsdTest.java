package com.example.localuser.retrofittest.MdnsTest;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public abstract class BaseNsdTest {
    protected String TAG = LogConfigs.TAG_PREFIX_MDNS+getClass().getSimpleName();
    protected Activity mActivity;
    protected NsdManager mNsdManager;
    public static String SERVICE_TYPE = "_http._tcp.";

    public BaseNsdTest(Activity activity)
    {
        mActivity = activity;
        init();
    }

    protected void init()
    {
        mNsdManager = (NsdManager) mActivity.getSystemService(Context.NSD_SERVICE);
    }
}
