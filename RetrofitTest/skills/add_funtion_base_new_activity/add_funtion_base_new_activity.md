# 技能名称：实现MainActivity自动增加功能项

# 技能描述：当新增一个测试功能时，通过新建的Activity，自动为MainActivity添加功能入口。使用运行时反射自动扫描@AsmFunction注解，自动完成注册工作。

# 输入要求：依次询问我下面三个问题：
1、询问我在哪个包下面创建Activity；
2、询问我创建的Activity的类名；
3、询问我注解的参数functionName的值；

# 步骤：
1、判断包名是否存在，若不存在则新建包目录；
2、在包目录下创建新的Activity，和MainActivity继承自同一父类（BaseActivity），并为该类添加注解@AsmFunction；
3、复用父类的布局文件即可；
4、在AndroidManifest.xml里面注册这个Activity；
5、根据新建Activity以及其注解，在activity_main.xml中添加对应的TextView（android:id="@+id/{functionName}_activity"）

6、运行应用，AsmFunctionRegistry会自动注册所有带@AsmFunction注解的类，并自动绑定TextView的点击事件和可见性

# 代码实现细节

## 1. 注解类 AsmFunction（位于 base 包）

```java
package com.example.mytestprojectnew.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ASM功能注解，用于标记需要自动注册的功能入口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AsmFunction {
    /**
     * 功能名称
     */
    String functionName();

    /**
     * 是否可见，默认为true
     */
    boolean visible() default true;
}
```

## 2. 注册表 AsmFunctionRegistry（位于 base 包）

- 维护已知类列表 KNOWN_CLASSES
- 使用 Class.forName() 直接加载类
- 提供 getFunctionClass()、isVisible()、getAllFunctionNames() 等方法

```java
package com.example.mytestprojectnew.base;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AsmFunctionRegistry {
    private static final String TAG = "AsmFunctionRegistry";
    private static AsmFunctionRegistry sInstance;
    private Map<String, FunctionInfo> mFunctionMap = new HashMap<>();

    // 已知需要注册的类列表
    private static final String[] KNOWN_CLASSES = {
        "com.example.mytestprojectnew.asmtest.SampleActivity",
        "com.example.mytestprojectnew.asmtest.SampleActivity2",
        "com.example.mytestprojectnew.asmtest3.AsmTest3Activity"
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

    public Class<? extends BaseActivity> getFunctionClass(String functionName) {
        FunctionInfo info = mFunctionMap.get(functionName);
        return info != null ? info.activityClass : null;
    }

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
```

## 3. Activity 示例

```java
package com.example.mytestprojectnew.asmtest;

import com.example.mytestprojectnew.R;
import com.example.mytestprojectnew.base.AsmFunction;
import com.example.mytestprojectnew.base.BaseActivity;

/**
 * ASM功能示例Activity
 * 注意：TextView的ID必须为 {functionName}_activity
 * 例如：functionName="asmtest1" -> android:id="@+id/asmtest1_activity"
 */
@AsmFunction(functionName = "asm_sample_function", visible = true)
public class SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
    }
}
```

## 4. MainActivity 中调用

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        
        // 初始化ASM功能注册表
        AsmFunctionRegistry registry = AsmFunctionRegistry.getInstance();
        registry.scanPackage();
        
        // 自动绑定所有ASM功能
        bindAsmFunctions(registry);
    }

    /**
     * 自动绑定ASM功能注册表中的所有功能
     * 根据functionName动态查找TextView并设置点击事件和可见性
     */
    private void bindAsmFunctions(AsmFunctionRegistry registry) {
        for (String functionName : registry.getAllFunctionNames()) {
            String textViewIdName = AsmFunctionRegistry.getTextViewIdName(functionName);
            int resId = getResources().getIdentifier(textViewIdName, "id", getPackageName());
            if (resId != 0) {
                TextView tv = findViewById(resId);
                if (tv != null) {
                    // 设置可见性
                    tv.setVisibility(registry.isVisible(functionName) ? View.VISIBLE : View.GONE);
                    // 设置点击事件
                    final String fn = functionName;
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Class<? extends BaseActivity> activityClass = registry.getFunctionClass(fn);
                            if (activityClass != null) {
                                Intent intent = new Intent(MainActivity.this, activityClass);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "功能未注册: " + fn, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Log.d(TAG, "Bound ASM function: " + functionName + " -> " + textViewIdName);
                }
            } else {
                Log.w(TAG, "TextView not found for function: " + functionName + " (id: " + textViewIdName + ")");
            }
        }
    }
}
```

## 5. 新增功能时的修改

当新增一个带 @AsmFunction 注解的 Activity 时，只需修改以下文件：

1. **创建新的 Activity**，继承 BaseActivity，添加 @AsmFunction 注解
2. **在 AsmFunctionRegistry.KNOWN_CLASSES 中添加类名**
3. **在 AndroidManifest.xml 中注册 Activity**
4. **在 activity_main.xml 中添加 TextView**

**重要：TextView 的 ID 必须遵循命名规则：`{functionName}_activity`**

例如，functionName = "asmtest1"，则：
```xml
<TextView
    android:id="@+id/asmtest1_activity"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="asmtest1" />
```

**无需修改 MainActivity！** 自动绑定方法会自动处理所有功能。

## 6. 整体工作流程

```
┌─────────────────────────────────────────────────────────────┐
│  MainActivity.onResume()                                     │
│  └─> AsmFunctionRegistry.scanPackage()                      │
│       └─> 遍历 KNOWN_CLASSES                                │
│            └─> Class.forName() 加载类                      │
│                 └─> 检测 @AsmFunction 注解                  │
│                      └─> 注册到 mFunctionMap               │
│                                                               │
│  bindAsmFunctions(registry)                                   │
│  └─> 遍历 registry.getAllFunctionNames()                    │
│       └─> getTextViewIdName("asmtest1") = "asmtest1_activity"
│       └─> getResources().getIdentifier() 动态查找View      │
│       └─> tv.setVisibility() 根据 visible 参数设置         │
│       └─> tv.setOnClickListener() 动态绑定跳转逻辑         │
└─────────────────────────────────────────────────────────────┘
```

#注意事项：

- AsmFunction有两个参数：String functionName, boolean visible
- 注解相关的类放到base目录下面，作为项目公共能力
- Android打包后没有独立的.class文件，使用 Class.forName() 直接加载
- KNOWN_CLASSES 数组需要手动维护，新增类时需要添加到此列表
- 如需扫描其他包下的类，在 KNOWN_CLASSES 中添加即可
- **TextView ID 必须遵循命名规则：`{functionName}_activity`**
