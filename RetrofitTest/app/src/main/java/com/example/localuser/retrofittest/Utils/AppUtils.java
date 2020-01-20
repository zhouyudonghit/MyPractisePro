package com.example.localuser.retrofittest.Utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.localuser.retrofittest.MyApplication;

public class AppUtils {
    private static Application mApplicationContext = MyApplication.getInstance();
    private static String TAG = "AppUtils";
    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName() {
        String packageName = "com.suning.health";
        if (mApplicationContext != null) {
            packageName = mApplicationContext.getPackageName();
        }
        return packageName;
    }

    /**
     * 获取URL请求使用的appversion
     */
    public static String getUrlAppVersion() {
        Object urlAppVersion = "";
        Log.d(TAG, "getUrlAppVersion mApplicationContext: " + mApplicationContext);
        if (mApplicationContext != null) {
            try {
                ApplicationInfo appInfo = mApplicationContext.getPackageManager().getApplicationInfo(
                        mApplicationContext.getPackageName(), PackageManager.GET_META_DATA);
                urlAppVersion = appInfo.metaData.get("ENV_URL_APP_VERSION");
                Log.d(TAG, "getUrlAppVersion urlAppVersion: " + urlAppVersion);
            } catch (Exception e) {
                e.printStackTrace();
                return String.valueOf(urlAppVersion);
            }
        }

        return String.valueOf(urlAppVersion);
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName() {
        String versionName = "0";

        if (mApplicationContext == null) {
            return versionName;
        }

        try {
            versionName = mApplicationContext.getPackageManager()
                    .getPackageInfo(mApplicationContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;

        if (mApplicationContext == null) {
            return versionCode;
        }

        try {
            versionCode = mApplicationContext.getPackageManager()
                    .getPackageInfo(mApplicationContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * judge whether the netWork is avaliable
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        boolean flag;
        flag = networkinfo != null && networkinfo.isAvailable();
        return flag;
    }
}
