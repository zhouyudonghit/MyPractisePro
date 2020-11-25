package com.example.localuser.retrofittest.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    /**
     * 设置状态栏图标和文字颜色为黑色或白色,以及透明状态栏
     * @param activity 需要设置的activity
     * @param dark 图标和文字是否设置为黑色
     */
    public static void setStatusBarDarkIcon(Activity activity, boolean dark){
        setStatusBarDarkIcon(activity, dark, false);
    }

    public static void setStatusBarDarkIcon(Activity activity, boolean dark, boolean showNavigation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //设置图标和文字颜色
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
            if (dark){
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

//            if (!showNavigation) {
//                option |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//                option |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            }
            decorView.setSystemUiVisibility(option);
            if (SystemUtil.isMeizu()){
                //魅族手机设置
                MeiZuStatusBarUtils.setStatusBarDarkIcon(activity, dark);
            }

            //设置状态栏透明
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (!showNavigation){
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
            activity.getWindow().setStatusBarColor(Color.RED);
        }
    }

    public static void setStatusBarDartIconOnly(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int flags = decorView.getSystemUiVisibility();
            if (dark) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

            if (SystemUtil.isMeizu()) {
                //魅族手机设置
                MeiZuStatusBarUtils.setStatusBarDarkIcon(activity, dark);
            }

            decorView.setSystemUiVisibility(flags);
        }
    }

    /**
     * 设置状态栏和导航栏颜色为黑色或白色,以及透明状态栏
     * @param activity 需要设置的activity
     * @param dark 状态栏和导航栏颜色是否设置为黑色
     */
    public static void setStatusBarDarkColor(Activity activity, boolean dark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //设置图标和文字颜色
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            if (dark){
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(option);
            if (SystemUtil.isMeizu()){
                //魅族手机设置
                MeiZuStatusBarUtils.setStatusBarDarkIcon(activity, dark);
            }

            //设置状态栏透明
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (dark) {
                activity.getWindow().setNavigationBarColor(Color.BLACK);
                activity.getWindow().setStatusBarColor(Color.BLACK);
            } else {
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public static void setStatusBarDarkIcon(Window window, boolean dark, boolean showNavigation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //设置图标和文字颜色
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            if (dark){
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

            if (!showNavigation) {
                option |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                option |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            decorView.setSystemUiVisibility(option);
            if (SystemUtil.isMeizu()){
                //魅族手机设置
                MeiZuStatusBarUtils.setStatusBarDarkIcon(window, dark);
            }

            //设置状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (!showNavigation){
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
