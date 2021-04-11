package com.example.localuser.retrofittest.Utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 缩放处理类
 */
public class ScalingUtil {

    public static final String TAG = "ScalingUtil";

    /**
     * 布局适配屏幕的宽高进行等比缩放
     * @param view          缩放的布局
     * @param designHeight  布局设计高度
     * @param designWidth   布局设计宽度
     * @param marginStart   布局距离左边的margin
     * @param marginTop     布局距离顶部的margin
     * @param marginEnd     布局距离右边的margin
     * @param marginBottom  布局距离底部的margin
     */
    public static void scaleViewAdaptToScreen(View view, float designHeight, float designWidth, float marginStart, float marginTop, float marginEnd, float marginBottom) {
        if (view == null) {
            return;
        }

        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        //获取屏幕的宽度
        int screenWidth = point.x;
        //获取屏幕的高度
        int screenHeight = point.y;
        Log.d(TAG, "scaleView screenWidth = " + screenWidth + ",screenHeight = " + screenHeight);

        //获取适配屏幕宽度后view实际的高度
        float newHeight = screenHeight - marginTop - marginBottom;
        //获取适配屏幕宽度后view实际的宽度
        float newWidth = screenWidth - marginStart - marginEnd;

        //计算屏幕高度和ui设计的高度的比例
        float heightScale = newHeight / designHeight;
        //计算适配后宽度和ui设计的宽度的比例
        float widthScale = newWidth / designWidth;

        //获得实际缩放比例
        float newScale = Math.min(heightScale, widthScale);
        Log.d(TAG, "scaleView newScale:" + newScale);

        //等比缩放布局
        ScalingUtil.scaleViewAndChildren(view, newScale);
    }

    /**
     * 缩放View和它的子View
     *
     * @param view   根View
     * @param factor 缩放比例
     */
    public static void scaleViewAndChildren(View view, float factor) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        // 如果宽高是具体数值，则进行缩放。(MATCH_PARENT、WRAP_CONTENT 等都是负数)
        if (layoutParams.width > 0) {
            layoutParams.width *= factor;
        }
        if (layoutParams.height > 0) {
            layoutParams.height *= factor;
        }

        // 缩放margin
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginParams.leftMargin *= factor;
            marginParams.topMargin *= factor;
            marginParams.rightMargin *= factor;
            marginParams.bottomMargin *= factor;
        }
        view.setLayoutParams(layoutParams);

        // EditText 有特殊的padding，不处理
        if (!(view instanceof EditText)) {
            // 缩放padding
            view.setPadding(
                    (int) (view.getPaddingLeft() * factor),
                    (int) (view.getPaddingTop() * factor),
                    (int) (view.getPaddingRight() * factor),
                    (int) (view.getPaddingBottom() * factor)
            );
        }

        // 缩放文字
        if (view instanceof TextView) {
            scaleTextSize((TextView) view, factor, layoutParams);
        }

        // 如果是ViewGroup，继续缩放它的子View
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                scaleViewAndChildren(vg.getChildAt(i), factor);
            }
        }
    }

    // 缩放文字
    public static void scaleTextSize(TextView tv, float factor, ViewGroup.LayoutParams layoutParams) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * factor);
    }
}
