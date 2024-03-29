package com.example.localuser.retrofittest.MemoryTest;

import android.app.Activity;
import android.app.ActivityManager;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import static android.content.Context.ACTIVITY_SERVICE;

public class MemoryTest {
    private String TAG = LogConfigs.TAG_PREFIX_MEMORY_TEST+getClass().getSimpleName();
    private Activity mActivity;

    public MemoryTest(Activity activity)
    {
        mActivity = activity;
    }

    public void test()
    {
        long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
        long totalMemory = ((int) Runtime.getRuntime().totalMemory())/1024/1024;
        //应用程序已获得内存中未使用内存
        long freeMemory = ((int) Runtime.getRuntime().freeMemory())/1024/1024;
        Log.d(TAG,"---> maxMemory="+maxMemory+"M,totalMemory="+totalMemory+"M,freeMemory="+freeMemory+"M");

        ActivityManager am = (ActivityManager) mActivity.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        Log.d(TAG,"outInfo.availMem = "+outInfo.availMem/1024/1024+",outInfo.threshold = "+outInfo.threshold/1024/1024+",outInfo.totalMem = "+outInfo.totalMem/1024/1024+",outInfo.lowMemory = "+outInfo.lowMemory);
        int memoryClass = am.getMemoryClass();
        Log.d(TAG,"memoryClass = "+memoryClass);
        int largeMemoryClass = am.getLargeMemoryClass();
        Log.d(TAG,"largeMemoryClass = "+largeMemoryClass);

        applyMemory();
    }

    private void applyMemory()
    {
        long[] i = new long[640*1024*1024/8];
    }
}
