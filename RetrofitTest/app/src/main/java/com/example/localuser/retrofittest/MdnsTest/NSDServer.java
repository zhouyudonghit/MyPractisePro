package com.example.localuser.retrofittest.MdnsTest;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

public class NSDServer extends BaseNsdTest{
    private NsdManager.RegistrationListener mRegistrationListener;

    public NSDServer(Activity activity)
    {
        super(activity);
    }

    public void init() {
        super.init();
        mRegistrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "NsdServiceInfo onRegistrationFailed");

            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.i(TAG, "onUnregistrationFailed serviceInfo: " + serviceInfo + " ,errorCode:" + errorCode);

            }

            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "onServiceRegistered: " + serviceInfo);

            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "onServiceUnregistered serviceInfo: " + serviceInfo);

            }
        };
    }

    public void registerService(String serviceName, int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setPort(port);
        serviceInfo.setServiceType(SERVICE_TYPE);//客户端发现服务器是需要对应的这个Type字符串
        mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    public void stopNSDServer() {
        mNsdManager.unregisterService(mRegistrationListener);
    }
}
