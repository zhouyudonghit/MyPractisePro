package com.example.localuser.retrofittest.Utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;

public class SystemUtil {
    public static final String TAG = "SystemUtil";

    public static final String SYS_EMUI = "sys_emui";

    public static final String SYS_MIUI = "sys_miui";

    public static final String SYS_FLYME = "sys_flyme";

    public static final String SYS_COLOROS = "sys_colorOS";

    public static final String SYS_FUNTOUCH = "sys_funtouch";

    public static final String SYS_OTHER = "sys_other";

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";

    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";

    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    private static final String KEY_COLOROS_ROM = "ro.build.version.opporom";

    private static final String KEY_FUNTOUCH_VERSION = "ro.vivo.os.version";

    public static String getSystem(Context context) {
        String sysType = SpUtil.readPreferencesString(context, "sysType", "");
        if (TextUtils.isEmpty(sysType)) {
            try {
                sysType = SYS_OTHER;
                if (isMiui()) {
                    // 小米
                    sysType = SYS_MIUI;
                } else if (isEmui()) {
                    // 华为
                    sysType = SYS_EMUI;
                } else if (isMeizu()) {
                    // 魅族
                    sysType = SYS_FLYME;
                } else if (isColoros()) {
                    // oppo
                    sysType = SYS_COLOROS;
                } else if (isFuntouch()) {
                    // vivo
                    sysType = SYS_FUNTOUCH;
                }
                SpUtil.savePreferencesString(context, "sysType", sysType);
            } catch (Exception e) {
                e.printStackTrace();
                return sysType;
            }
        }
        Log.d(TAG, "sysType:" + sysType);
        return sysType;
    }

    /**
     * 判断是否为魅族设备
     */
    public static boolean isMeizu() {
        String model = getSystemProperty("ro.meizu.product.model", "");
        return (!TextUtils.isEmpty(model)) || "meizu".equalsIgnoreCase(Build.BRAND)
                || "22c4185e".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 判断是否为小米手机
     */
    public static boolean isMiui() {
        return !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_CODE, null))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_NAME, null))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_INTERNAL_STORAGE, null))
                || "Xiaomi".equals(Build.MANUFACTURER);
    }

    /**
     * 判断是否为华为手机
     */
    public static boolean isEmui() {
        return !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_API_LEVEL, null))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_VERSION, null))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null));
    }

    /**
     * 判断是否为oppo手机
     */
    public static boolean isColoros() {
        return !TextUtils.isEmpty(getSystemProperty(KEY_COLOROS_ROM, null));
    }

    /**
     * 判断是否为vivo手机
     */
    public static boolean isFuntouch() {
        return !TextUtils.isEmpty(getSystemProperty(KEY_FUNTOUCH_VERSION, null));
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

}
