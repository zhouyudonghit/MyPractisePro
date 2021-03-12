package com.example.localuser.retrofittest.Utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontUtils {
    public static final String PATH_PHT_REGULAR = "fonts/AlibabaSans-Regular.otf";
    public static final String PATH_PHT_MEDIUM = "fonts/AlibabaSans-Medium.otf";
    public static final String PATH_PHT_BOLD = "fonts/AlibabaSans-Bold.otf";
    public static final String PATH_PHT_HEAVY = "fonts/AlibabaSans-Heavy.otf";
    public static final String PATH_PHT_HEAVY_ITALIC = "fonts/AlibabaSans-HeavyItalic.otf";
    public static final String PATH_BEBAS_REGULAR = "fonts/Bebas-Regular.otf";
    private static final HashMap<String, Typeface> sTypefaceCacheList = new HashMap<>();

    public static Typeface getPHTRegular(Context context){
        return getTypeface(context, PATH_PHT_REGULAR);
    }

    public static Typeface getPHTMedium(Context context){
        return getTypeface(context, PATH_PHT_MEDIUM);
    }

    public static Typeface getPHTBold(Context context){
        return getTypeface(context, PATH_PHT_BOLD);
    }

    public static Typeface getPHTHeavy(Context context){
        return getTypeface(context, PATH_PHT_HEAVY);
    }

    public static Typeface getPHTHeavyItalic(Context context){
        return getTypeface(context, PATH_PHT_HEAVY_ITALIC);
    }

    public static Typeface getBebasRegular(Context context){
        return getTypeface(context, PATH_BEBAS_REGULAR);
    }

    private static Typeface getTypeface(Context context, String path) {
        Typeface typeface;
        if (sTypefaceCacheList.containsKey(path)) {
            typeface = sTypefaceCacheList.get(path);
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), path);
            sTypefaceCacheList.put(path, typeface);
        }
        return typeface;
    }
}
