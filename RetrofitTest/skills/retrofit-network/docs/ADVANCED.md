# Retrofit 进阶指南

## 高级功能

### 1. 拦截器（Interceptor）

拦截器可以在请求发送前和响应返回后进行处理。

#### 日志拦截器

```java
HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
logging.setLevel(HttpLoggingInterceptor.Level.BODY);

OkHttpClient client = new OkHttpClient.Builder()
    .addInterceptor(logging)
    .build();
```

#### 自定义拦截器

```java
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        
        // 添加请求头
        Request request = original.newBuilder()
            .header("Authorization", "Bearer " + getToken())
            .method(original.method(), original.body())
            .build();
        
        return chain.proceed(request);
    }
}

OkHttpClient client = new OkHttpClient.Builder()
    .addInterceptor(new TokenInterceptor())
    .build();
```

#### 重试拦截器

```java
public class RetryInterceptor implements Interceptor {
    private int maxRetry;
    
    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        IOException exception = null;
        
        int retryCount = 0;
        while (retryCount < maxRetry) {
            try {
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
                retryCount++;
            } catch (IOException e) {
                exception = e;
                retryCount++;
                if (retryCount >= maxRetry) {
                    throw e;
                }
            }
        }
        return response;
    }
}
```

### 2. 动态 Base URL

```java
public interface ApiService {
    @GET
    @Url
    Call<User> getUser(@Url String url);
}

// 使用
apiService.getUser("https://api.example.com/users/1");
```

### 3. 缓存配置

```java
int cacheSize = 10 * 1024 * 1024; // 10MB
Cache cache = new Cache(context.getCacheDir(), cacheSize);

OkHttpClient client = new OkHttpClient.Builder()
    .cache(cache)
    .addInterceptor(chain -> {
        Request request = chain.request();
        if (!isNetworkAvailable(context)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        }
        
        Response response = chain.proceed(request);
        if (isNetworkAvailable(context)) {
            response = response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 60)
                .build();
        } else {
            response = response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", 
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build();
        }
        return response;
    })
    .build();
```

### 4. HTTPS 配置

```java
public class SSLConfig {
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // 创建信任所有证书的 TrustManager
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                        X509Certificate[] chain, String authType) {}
                    
                    @Override
                    public void checkServerTrusted(
                        X509Certificate[] chain, String authType) {}
                    
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
            };
            
            // 安装 TrustManager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            
            return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

### 5. 文件下载

```java
@Streaming
@GET("download/{filename}")
Call<ResponseBody> downloadFile(@Path("filename") String filename);

// 使用
Call<ResponseBody> call = apiService.downloadFile("example.pdf");
call.enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            InputStream inputStream = response.body().byteStream();
            // 保存文件
            saveFile(inputStream, getFilePath());
        }
    }
    
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        // 处理错误
    }
});

private void saveFile(InputStream inputStream, String filePath) {
    try {
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
        fos.close();
        inputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### 6. 文件上传（带进度）

```java
@Multipart
@POST("upload")
Call<UploadResponse> uploadFile(
    @Part MultipartBody.Part file
);

// 创建带进度的 RequestBody
public class ProgressRequestBody extends RequestBody {
    private File file;
    private UploadListener listener;
    
    public ProgressRequestBody(File file, UploadListener listener) {
        this.file = file;
        this.listener = listener;
    }
    
    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }
    
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte(8192);
        long uploaded = 0;
        
        try (FileInputStream source = new FileInputStream(file)) {
            int read;
            while ((read = source.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                listener.onProgress((int) (100 * uploaded / fileLength));
            }
        }
    }
    
    public interface UploadListener {
        void onProgress(int percent);
    }
}

// 使用
File file = new File(filePath);
ProgressRequestBody requestBody = new ProgressRequestBody(file, percent -> {
    runOnUiThread(() -> {
        progressBar.setProgress(percent);
    });
});

MultipartBody.Part body = MultipartBody.Part.createFormData(
    "file", file.getName(), requestBody
);

Call<UploadResponse> call = apiService.uploadFile(body);
```

### 7. 超时设置

```java
OkHttpClient client = new OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build();
```

### 8. 自定义 Converter

```java
public class StringConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
        Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type.equals(String.class)) {
            return value -> value.string();
        }
        return null;
    }
    
    @Override
    public Converter<?, RequestBody> requestBodyConverter(
        Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type.equals(String.class)) {
            return value -> RequestBody.create(
                MediaType.parse("text/plain"), 
                (String) value
            );
        }
        return null;
    }
}

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(new StringConverterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .build();
```

## RxJava 高级用法

### 1. 操作符组合

```java
apiService.getData()
    .subscribeOn(Schedulers.io())
    .flatMap(data -> {
        // 链式请求
        return apiService.getDetail(data.getId());
    })
    .map(detail -> {
        // 数据转换
        return detail.getName();
    })
    .filter(name -> {
        // 数据过滤
        return !name.isEmpty();
    })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        name -> {
            // 最终处理
        },
        throwable -> {
            // 错误处理
        }
    );
```

### 2. 错误恢复

```java
apiService.getData()
    .retryWhen(errors -> {
        return errors.zipWith(
            Observable.range(1, 3),
            (error, retryCount) -> {
                if (retryCount >= 3) {
                    return Observable.error(error);
                }
                return Observable.timer((long) Math.pow(2, retryCount), 
                    TimeUnit.SECONDS);
            }
        );
    })
    .subscribe(...);
```

### 3. 并发请求

```java
Observable.zip(
    apiService.getUser(userId),
    apiService.getPosts(userId),
    apiService.getComments(userId),
    (user, posts, comments) -> {
        // 合并结果
        return new UserProfile(user, posts, comments);
    }
)
.subscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
.subscribe(...);
```

## 性能优化

### 1. 单例模式

```java
public class RetrofitClient {
    private static Retrofit instance;
    
    private RetrofitClient() {}
    
    public static synchronized Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return instance;
    }
}
```

### 2. 连接池复用

OkHttp 默认支持连接池复用，无需额外配置。

### 3. Gzip 压缩

OkHttp 默认支持 Gzip 压缩，只需服务器端配置即可。

### 4. 序列化优化

使用更高效的 JSON 库如 Moshi：

```gradle
implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'
```

## 测试

### Mock Web Server

```gradle
testImplementation 'com.squareup.okhttp3:mockwebserver:4.9.0'
```

```java
MockWebServer server = new MockWebServer();
server.start();

server.enqueue(new MockResponse()
    .setBody("{\"name\":\"Test\"}")
    .setResponseCode(200));

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(server.url("/").toString())
    .build();

// 测试完成后关闭
server.shutdown();
```

## 参考资源

- [Retrofit 官方文档](https://square.github.io/retrofit/)
- [OkHttp 官方文档](https://square.github.io/okhttp/)
- [RxJava 文档](https://github.com/ReactiveX/RxJava)
