package com.example.localuser.retrofittest.Utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.localuser.retrofittest.MyApplication;
import com.example.localuser.retrofittest.edittext.EditTextTestActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

    // 判断服务是否正在运行
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * 将sp转换为px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getClipboardContent(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            ClipData data = cm.getPrimaryClip();
            if (data != null && data.getItemCount() > 0) {
                ClipData.Item item = data.getItemAt(0);
                if (item != null) {
                    CharSequence sequence = item.coerceToText(context);
                    if (sequence != null) {
                        return sequence.toString();
                    }
                }
            }
        }
        return null;
    }

    public static String getClipboardContent2() {
        ClipboardManager manager = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }

    public static void setClipboardContent(Context context, String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    public static void clearClipboard(Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //这种写法也会报空指针异常，不可取
            cm.clearPrimaryClip();
        } else {
            //这种写法会报空指针异常，不可取
            cm.setPrimaryClip(null);
        }
    }

    public static void startActivity() {
        Intent intent = new Intent(MyApplication.getInstance(), EditTextTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivity(MyApplication.getInstance(), intent, null);
    }

    public static String getIccid1(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = tm.getSimSerialNumber();
        return iccid;
    }

    public static String getIccid2(Context context)
    {
        String iccid = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = tm.getClass().getDeclaredMethod("getSubscriberInfo");
            try {
                method.setAccessible(true);
                Object obj = method.invoke(tm);
                Method method2 = obj.getClass().getDeclaredMethod("getPhone",int.class);
                method2.setAccessible(true);
                Object obj2 = method2.invoke(obj,0);
                Method method3 = obj2.getClass().getMethod("getFullIccSerialNumber");
                iccid = (String) method3.invoke(obj2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return iccid;
    }

    public static float getSreenScaleByWidth(Context context)
    {
        return ScreenUtils.getScreenWidth(context)*1.0f/AppUtils.dip2px(context,375);
    }
}
