package com.example.localuser.retrofittest.MdnsTest;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Message;
import android.util.Log;

public class NSDClient extends BaseNsdTest {
    private NsdManager.DiscoveryListener mNSDDiscoveryListener;
    private NsdManager.ResolveListener mResolveListener;
    private NsdServiceInfo mNsdServiceInfo;

    public NSDClient(Activity activity) {
        super(activity);
    }

    public void init()
    {
        super.init();
        mNSDDiscoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.i(TAG, "onStartDiscoveryFailed--> " + serviceType + ":" + errorCode);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.i(TAG, "onStopDiscoveryFailed--> " + serviceType + ":" + errorCode);
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                Log.i(TAG, "onDiscoveryStarted--> " + serviceType );
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "onDiscoveryStopped--> " + serviceType );
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "onServiceFound Info: --> " + serviceInfo);
                mNsdServiceInfo = serviceInfo;
                resolveServer();
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "onServiceLost--> " + serviceInfo);
                mNsdServiceInfo = null;
            }
        };

        mResolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.d(TAG,"onResolveFailed,serviceInfo = "+serviceInfo+",errorCode = "+errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.i(TAG, "serviceInfo = "+serviceInfo+",resolution : " + serviceInfo.getServiceName() + " \n host_from_server: " + serviceInfo.getHost() +
                        "\n port from server: " + serviceInfo.getPort());
                String hostAddress = serviceInfo.getHost().getHostAddress();
                Log.i(TAG, "hostAddress ip--> " + hostAddress );
                stopDiscover();
            }
        };
    }

    public void discoverNsdServer()
    {
        mNsdManager.discoverServices(SERVICE_TYPE,NsdManager.PROTOCOL_DNS_SD,mNSDDiscoveryListener);
    }

    public void resolveServer()
    {
        mNsdManager.resolveService(mNsdServiceInfo,mResolveListener);
    }

    public void stopDiscover()
    {
        mNsdManager.stopServiceDiscovery(mNSDDiscoveryListener);
    }
}
