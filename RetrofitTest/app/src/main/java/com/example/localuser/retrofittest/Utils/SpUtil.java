package com.example.localuser.retrofittest.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    /**
     * SHAREDNAME.
     */
    public static final String SHAREDNAME = "Configuration";

    /**
     * 震动开关
     */
    public static final String ENABLE_VIBRATE_NAME = "enable_vibrate";

    /**
     * 是否已经显示权限设置
     */
    public static final String SHOWED_SYSTEM_PERMISSION_SETTINGS = "showed_system_permission_settings";


    /**
     * 运动会列表页是否已经显示权限设置
     */
    public static final String RACE_LIST_SHOWED_SYSTEM_PERMISSION_SETTINGS = "race_list_showed_system_permission_settings";

    /**
     * savePreferencesString.
     *
     * @param name  name
     * @param value value
     */
    public static void savePreferencesString(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(name, value);
        preferencesEditor.commit();
    }

    /**
     * 读取震动开关状态
     *
     * @param name
     * @param value
     * @return
     */
    public static boolean readEnableVibrateSPString(Context context, String name, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(ENABLE_VIBRATE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(name, value);
    }

    /**
     * readPreferencesString.
     *
     * @param name  name
     * @param value value
     * @return preference value
     */
    public static String readPreferencesString(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.getString(name, value);
    }

    /**
     * savePreferencesBoolean.
     *
     * @param name  name
     * @param value value
     */
    public static void savePreferencesBoolean(Context context, String name, Boolean value) {

        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean(name, value);
        preferencesEditor.commit();

    }

    /**
     * readPreferencesBoolean.
     *
     * @param name  name
     * @param value value
     * @return preference value
     */
    public static boolean readPreferencesBoolean(Context context, String name, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(name, value);
    }

    /**
     * savePreferencesInt.
     *
     * @param name  name
     * @param value value
     */
    public static void savePreferencesInt(Context context, String name, int value) {

        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(name, value);
        preferencesEditor.commit();

    }

    /**
     * readPreferencesInt.
     *
     * @param name  name
     * @param value value
     * @return preference value
     */
    public static int readPreferencesInt(Context context, String name, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.getInt(name, value);
    }


    /**
     * readPreferencesLong.
     *
     * @param name  name
     * @param value value
     * @return preference value
     */
    public static long readPreferencesLong(Context context, String name, long value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.getLong(name, value);
    }

    /**
     * savePreferencesInt.
     *
     * @param name  name
     * @param value value
     */
    public static void savePreferencesLong(Context context, String name, long value) {

        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putLong(name, value);
        preferencesEditor.commit();

    }

    /**
     * savePreferencesInt.
     */
    public static void clearPreferencesByKey(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove(key);
        preferencesEditor.commit();

    }

    /**
     * isKeyExist.
     *
     * @param name  name
     * @return preference value
     */
    public static boolean isKeyExist(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.contains(name);
    }

}
