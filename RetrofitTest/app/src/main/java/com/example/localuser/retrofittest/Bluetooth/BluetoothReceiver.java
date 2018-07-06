package com.example.localuser.retrofittest.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {
    private String TAG = BluetoothActivity.TAG_PREFIX+getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,intent.getAction());
        if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND))
        {

        }
    }
}
