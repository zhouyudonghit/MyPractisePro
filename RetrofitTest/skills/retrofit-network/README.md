---
name: "retrofit-network"
description: "Android Retrofit网络请求技能，提供标准化的网络请求开发流程、接口定义、数据解析和错误处理"
version: "1.0.0"
author: "CodeBuddy"
category: "android-network"

# 何时使用此技能
trigger_conditions:
  - "用户需要实现HTTP网络请求"
  - "用户提到Retrofit、OkHttp、网络API"
  - "用户需要与RESTful API交互"
  - "用户需要实现登录、数据获取等网络功能"

# 适用场景
use_cases:
  - GET请求获取数据
  - POST请求提交数据
  - 文件上传下载
  - 与RxJava结合使用
  - 自定义拦截器和日志

# 依赖要求
dependencies:
  - name: "Retrofit"
    version: "2.0.2"
    gradle: "com.squareup.retrofit2:retrofit:2.0.2"
  - name: "Gson Converter"
    version: "2.0.2"
    gradle: "com.squareup.retrofit2:converter-gson:2.0.2"
  - name: "RxJava Adapter"
    version: "2.3.0"
    gradle: "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
  - name: "RxJava"
    version: "2.1.3"
    gradle: "io.reactivex.rxjava2:rxjava:2.1.3"
  - name: "RxAndroid"
    version: "2.0.1"
    gradle: "io.reactivex.rxjava2:rxandroid:2.0.1"

# 权限要求
permissions:
  - "android.permission.INTERNET"
  - "android.permission.ACCESS_NETWORK_STATE"

---

# Retrofit 网络请求技能指南

## 概述

本技能提供使用 Retrofit 进行 Android 网络请求的完整解决方案，包括接口定义、数据模型创建、请求配置和错误处理。

## 基本使用流程

### 1. 定义数据模型

创建与 API 响应对应的 Java Bean 类：

```java
// 翻译响应示例
public class Translation {
    private int status;
    private String content;
    private Map<String, String> dictionary;
    
    // getter 和 setter 方法
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Map<String, String> getDictionary() { return dictionary; }
    public void setDictionary(Map<String, String> dictionary) { 
        this.dictionary = dictionary; 
    }
}
```

### 2. 定义 API 接口

使用注解定义网络请求接口：

```java
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface GetRequest_Interface {
    
    // GET 请求示例
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();
    
    // 带查询参数的 GET 请求
    @GET("get_translation")
    Call<Translation> getTranslation(
        @Query("word") String word,
        @Query("from") String from,
        @Query("to") String to
    );
    
    // POST 请求示例
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
        @Field("username") String username,
        @Field("password") String password
    );
}
```

### 3. 创建 Retrofit 实例

配置并创建 Retrofit 对象：

```java
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://fy.iciba.com/";
    
    private static Retrofit retrofit;
    
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
}
```

### 4. 执行网络请求

同步或异步执行网络请求：

```java
// 异步请求示例
GetRequest_Interface request = RetrofitClient.getRetrofitInstance()
    .create(GetRequest_Interface.class);
    
Call<Translation> call = request.getCall();

call.enqueue(new Callback<Translation>() {
    @Override
    public void onResponse(Call<Translation> call, Response<Translation> response) {
        if (response.isSuccessful()) {
            Translation translation = response.body();
            // 处理成功响应
            String result = translation.getContent();
            Log.d("Retrofit", "翻译结果: " + result);
        } else {
            // 处理错误响应
            Log.e("Retrofit", "响应错误: " + response.code());
        }
    }
    
    @Override
    public void onFailure(Call<Translation> call, Throwable t) {
        // 处理网络错误
        Log.e("Retrofit", "请求失败", t);
    }
});
```

## 与 RxJava 结合使用

### 1. 添加 RxJava 支持

```java
GetRequest_Interface request = new Retrofit.Builder()
    .baseUrl("http://fy.iciba.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(GetRequest_Interface.class);
```

### 2. 定义 RxJava 接口

```java
import io.reactivex.Observable;

public interface GetRequestRxjava_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation> getCall();
}
```

### 3. 使用 RxJava 执行请求

```java
request.getCall()
    .subscribeOn(Schedulers.io())           // 在IO线程执行网络请求
    .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理结果
    .subscribe(new Observer<Translation>() {
        @Override
        public void onSubscribe(Disposable d) {
            // 请求开始
        }
        
        @Override
        public void onNext(Translation translation) {
            // 请求成功，处理数据
            String result = translation.getContent();
            textView.setText(result);
        }
        
        @Override
        public void onError(Throwable e) {
            // 请求失败
            Log.e("RxJava", "请求错误", e);
        }
        
        @Override
        public void onComplete() {
            // 请求完成
        }
    });
```

## 高级功能

### 1. 添加拦截器

```java
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClient {
    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        return new OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    }
    
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
```

### 2. 文件上传

```java
@Multipart
@POST("upload")
Call<UploadResponse> uploadFile(
    @Part MultipartBody.Part file,
    @Part("description") RequestBody description
);

// 使用示例
File file = new File(filePath);
RequestBody requestFile = RequestBody.create(
    MediaType.parse("multipart/form-data"), file
);
MultipartBody.Part body = MultipartBody.Part.createFormData(
    "file", file.getName(), requestFile
);

String descriptionString = "file description";
RequestBody description = RequestBody.create(
    okhttp3.MultipartBody.FORM, descriptionString
);

Call<UploadResponse> call = apiService.uploadFile(body, description);
```

### 3. 错误处理统一封装

```java
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    
    public boolean isSuccess() {
        return code == 200;
    }
    
    // getter 和 setter
}

public abstract class ApiCallback<T> implements Callback<ApiResponse<T>> {
    @Override
    public void onResponse(Call<ApiResponse<T>> call, Response<ApiResponse<T>> response) {
        if (response.isSuccessful() && response.body() != null) {
            ApiResponse<T> apiResponse = response.body();
            if (apiResponse.isSuccess()) {
                onSuccess(apiResponse.getData());
            } else {
                onError(apiResponse.getMessage());
            }
        } else {
            onError("网络请求失败");
        }
    }
    
    @Override
    public void onFailure(Call<ApiResponse<T>> call, Throwable t) {
        onError(t.getMessage());
    }
    
    protected abstract void onSuccess(T data);
    protected abstract void onError(String message);
}
```

## 最佳实践

### 1. 单例模式管理 Retrofit

确保整个应用使用同一个 Retrofit 实例，避免重复创建。

### 2. 线程管理

- 网络请求在 IO 线程执行
- UI 更新在主线程执行
- 使用 RxJava 时使用 `subscribeOn` 和 `observeOn`

### 3. 错误处理

- 统一处理网络异常
- 区分业务错误和系统错误
- 提供友好的错误提示

### 4. 内存泄漏预防

- 在 Activity/Fragment 销毁时取消请求
- 使用 CompositeDisposable 管理 RxJava 订阅

```java
public class BaseActivity extends AppCompatActivity {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose(); // 取消所有订阅
    }
}
```

## 常见问题

### Q1: 如何处理 HTTPS 证书？

A: 自定义 OkHttp 客户端配置 SSLContext 和 TrustManager。

### Q2: 如何实现请求重试？

A: 使用 RxJava 的 `retryWhen` 操作符或自定义拦截器。

### Q3: 如何添加请求头？

A: 使用 `@Header` 注解或通过拦截器统一添加。

### Q4: 如何实现缓存？

A: 配置 OkHttp 的 Cache 和拦截器。

## 参考资料

- [Retrofit 官方文档](https://square.github.io/retrofit/)
- [OkHttp 官方文档](https://square.github.io/okhttp/)
- [RxJava 中文文档](https://mcxiaoke.gitbooks.io/rxjava-docs-cn/)

## 示例项目

完整示例代码可参考本项目中的以下文件：
- `GetRequest_Interface.java` - 基本 API 接口定义
- `GetRequestRxjava_Interface.java` - RxJava 接口定义
- `MainActivity.java` - 网络请求使用示例
- `Translation.java` - 数据模型示例
