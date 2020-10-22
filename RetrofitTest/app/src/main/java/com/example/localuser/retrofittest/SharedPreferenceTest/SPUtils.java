package com.example.localuser.retrofittest.SharedPreferenceTest;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    public static String SP_FILE_NAME = "test";

    public static void read(Context context,String var)
    {
        if(context != null)
        {
            SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
            sp.getString(var,"");
        }
    }

    public static void set(Context context,String var,String value)
    {
        if(context != null)
        {
            SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(var,value);
            editor.commit();
        }
    }

    public static boolean contains(Context context,String key)
    {
        if(context != null)
        {
            SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
            sp.contains(key);
        }
        return false;
    }
}
