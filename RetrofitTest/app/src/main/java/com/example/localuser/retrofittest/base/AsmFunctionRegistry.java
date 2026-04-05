package com.example.localuser.retrofittest.base;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ASM功能注册表，自动扫描@AsmFunction注解的类
 */
public class AsmFunctionRegistry {
    private static final String TAG = "AsmFunctionRegistry";
    private static AsmFunctionRegistry sInstance;
    private Map<String, FunctionInfo> mFunctionMap = new HashMap<>();

    // 已知需要注册的类列表 - 新增功能时需要在此添加
    private static final String[] KNOWN_CLASSES = {
        "com.example.localuser.retrofittest.asmtest1.AsmTest1Activity"
    };

    public static class FunctionInfo {
        public Class<? extends BaseActivity> activityClass;
        public boolean visible;

        public FunctionInfo(Class<? extends BaseActivity> activityClass, boolean visible) {
            this.activityClass = activityClass;
            this.visible = visible;
        }
    }

    public static AsmFunctionRegistry getInstance() {
        if (sInstance == null) {
            synchronized (AsmFunctionRegistry.class) {
                if (sInstance == null) {
                    sInstance = new AsmFunctionRegistry();
                }
            }
        }
        return sInstance;
    }

    /**
     * 扫描包并注册所有带@AsmFunction注解的类
     */
    public void scanPackage() {
        mFunctionMap.clear();
        for (String className : KNOWN_CLASSES) {
            registerClass(className);
        }
    }

    private void registerClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(AsmFunction.class)) {
                AsmFunction annotation = clazz.getAnnotation(AsmFunction.class);
                String functionName = annotation.functionName();
                boolean visible = annotation.visible();

                if (!clazz.isInterface() && !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())
                    && BaseActivity.class.isAssignableFrom(clazz)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends BaseActivity> activityClass = (Class<? extends BaseActivity>) clazz;
                    mFunctionMap.put(functionName, new FunctionInfo(activityClass, visible));
                    Log.d(TAG, "Registered function: " + functionName + " -> " + className);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading class: " + className, e);
        }
    }

    /**
     * 根据功能名称获取Activity类
     */
    public Class<? extends BaseActivity> getFunctionClass(String functionName) {
        FunctionInfo info = mFunctionMap.get(functionName);
        return info != null ? info.activityClass : null;
    }

    /**
     * 根据功能名称判断是否可见
     */
    public boolean isVisible(String functionName) {
        FunctionInfo info = mFunctionMap.get(functionName);
        return info != null ? info.visible : true;
    }

    /**
     * 获取所有注册的功能名称
     */
    public Set<String> getAllFunctionNames() {
        return mFunctionMap.keySet();
    }

    /**
     * 根据functionName生成对应的TextView资源ID名称
     * 例如: "asmtest1" -> "asmtest1_activity"
     */
    public static String getTextViewIdName(String functionName) {
        return functionName + "_activity";
    }
}
