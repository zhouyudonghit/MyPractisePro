package com.example.localuser.retrofittest.Utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import com.example.localuser.retrofittest.MyApplication;

public class ScreenUtils {
    public static String TAG = "ScreenUtils";

    /**
     * 应用程序显示区域宽度
     * @return
     */
    public static int getWindowWidth1(Window window)
    {
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    /**
     * 应用程序显示区域高度
     * @return
     */
    public static int getWindowHeight1(Window window)
    {
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    /**
     * 实际显示区域宽度
     * @return
     */
    public static int getWindowWidth2(Window window)
    {
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.x;
    }

    /**
     * 实际显示区域高度
     * @return
     */
    public static int getWindowHeight2(Window window)
    {
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }

    public static void getWindowSizeMethod1(Window window)
    {
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int x = point.x;
        int y = point.y;
        Log.i(TAG, "x = " + x + ",y = " + y);
    }

    public static void getWindowSizeMethod2(Window window)
    {
        Rect outSize = new Rect();
        window.getWindowManager().getDefaultDisplay().getRectSize(outSize);
        int left = outSize.left;
        int top = outSize.top;
        int right = outSize.right;
        int bottom = outSize.bottom;
        Log.d(TAG, "left = " + left + ",top = " + top + ",right = " + right + ",bottom = " + bottom);
    }

    public static void getWindowSizeMethod3(Window window)
    {
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.i(TAG, "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
    }

    public static void getWindowSizeMethod4(Window window)
    {
        Point outSize = new Point();
        window.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int x = outSize.x;
        int y = outSize.y;
        Log.w(TAG, "x = " + x + ",y = " + y);
    }

    public static void getWindowSizeMethod5(Window window)
    {
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;
        Log.w(TAG, "widthPixel = " + widthPixel + ",heightPixel = " + heightPixel);
    }
}
