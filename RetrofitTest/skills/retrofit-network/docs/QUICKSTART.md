# Retrofit 快速开始指南

## 5分钟上手 Retrofit

### 第一步：添加依赖

在 `app/build.gradle` 中添加以下依赖：

```gradle
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.0.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.0.2'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.1.3'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
}
```

### 第二步：添加网络权限

在 `AndroidManifest.xml` 中添加：

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 第三步：定义数据模型

```java
public class User {
    private int id;
    private String name;
    private String email;
    
    // 生成 getter 和 setter
}
```

### 第四步：定义 API 接口

```java
public interface ApiService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int userId);
    
    @POST("users")
    Call<User> createUser(@Body User user);
}
```

### 第五步：创建 Retrofit 实例

```java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.example.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build();
```

### 第六步：执行请求

```java
ApiService apiService = retrofit.create(ApiService.class);
Call<User> call = apiService.getUser(1);

call.enqueue(new Callback<User>() {
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            User user = response.body();
            // 处理数据
        }
    }
    
    @Override
    public void onFailure(Call<User> call, Throwable t) {
        // 处理错误
    }
});
```

## 常用注解

### 请求方法注解

| 注解 | 说明 |
|------|------|
| `@GET` | GET 请求 |
| `@POST` | POST 请求 |
| `@PUT` | PUT 请求 |
| `@DELETE` | DELETE 请求 |
| `@HEAD` | HEAD 请求 |

### 参数注解

| 注解 | 说明 | 示例 |
|------|------|------|
| `@Query` | 查询参数 | `@Query("page") int page` |
| `@Path` | 路径参数 | `@Path("id") int userId` |
| `@Body` | 请求体 | `@Body User user` |
| `@Field` | 表单字段 | `@Field("name") String name` |
| `@Header` | 请求头 | `@Header("Authorization") String token` |

### 其他注解

| 注解 | 说明 |
|------|------|
| `@FormUrlEncoded` | 表单编码 |
| `@Multipart` | 多部分表单（文件上传） |
| `@Streaming` | 流式响应（大文件下载） |

## 常见场景代码示例

### 1. 带 Query 参数的 GET 请求

```java
@GET("search")
Call<SearchResult> search(
    @Query("q") String query,
    @Query("page") int page
);
```

### 2. 表单 POST 请求

```java
@FormUrlEncoded
@POST("login")
Call<LoginResponse> login(
    @Field("username") String username,
    @Field("password") String password
);
```

### 3. JSON POST 请求

```java
@POST("users")
Call<User> createUser(@Body User user);
```

### 4. 文件上传

```java
@Multipart
@POST("upload")
Call<UploadResponse> upload(
    @Part MultipartBody.Part file,
    @Part("description") RequestBody description
);
```

### 5. 添加请求头

```java
@GET("profile")
Call<User> getProfile(
    @Header("Authorization") String token
);
```

## 错误处理

### 基本错误处理

```java
call.enqueue(new Callback<User>() {
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            // 成功
        } else {
            // HTTP 错误 (404, 500 等)
            int code = response.code();
        }
    }
    
    @Override
    public void onFailure(Call<User> call, Throwable t) {
        // 网络错误、解析错误等
    }
});
```

### 统一错误处理

```java
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    
    public boolean isSuccess() {
        return code == 200;
    }
}
```

## 调试技巧

### 添加日志拦截器

```java
OkHttpClient client = new OkHttpClient.Builder()
    .addInterceptor(new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY))
    .build();

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build();
```

## 下一步

- 查看 [完整示例代码](../examples/)
- 阅读 [进阶指南](ADVANCED.md)
- 了解 [最佳实践](BEST_PRACTICES.md)
