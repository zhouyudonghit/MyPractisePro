// RxJavaNetworkRequest.java - Retrofit + RxJava网络请求模板
// 使用说明：复制此文件并根据您的API需求修改

package com.example.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

// ==================== 1. 定义数据模型 ====================
/**
 * API响应数据模型
 */
public class ApiResponse {
    private int code;
    private String message;
    private Data data;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
    
    public boolean isSuccess() {
        return code == 200;
    }
}

public class Data {
    private String content;
    private long timestamp;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}

// ==================== 2. 定义RxJava API接口 ====================
/**
 * 使用RxJava的网络请求接口
 * 返回Observable而非Call
 */
public interface RxApiService {
    
    /**
     * GET请求 - 返回Observable
     */
    @GET("api/data")
    Observable<ApiResponse> getData(
        @Query("param1") String param1,
        @Query("param2") int param2
    );
    
    /**
     * POST请求 - 返回Observable
     */
    @FormUrlEncoded
    @POST("api/submit")
    Observable<ApiResponse> submitData(
        @Field("username") String username,
        @Field("password") String password
    );
}

// ==================== 3. Retrofit + RxJava 客户端管理 ====================
/**
 * Retrofit + RxJava 客户端管理
 * 配置了RxJava适配器
 */
public class RxRetrofitClient {
    private static final String BASE_URL = "https://api.example.com/";
    private static Retrofit retrofit;
    
    private RxRetrofitClient() {}
    
    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return retrofit;
    }
    
    public static RxApiService createApiService() {
        return getInstance().create(RxApiService.class);
    }
}

// ==================== 4. 网络请求管理器 ====================
/**
 * 网络请求管理器
 * 统一管理所有网络请求和订阅
 */
public class NetworkManager {
    private static NetworkManager instance;
    private CompositeDisposable compositeDisposable;
    
    private NetworkManager() {
        compositeDisposable = new CompositeDisposable();
    }
    
    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }
    
    /**
     * 执行GET请求
     * @param param1 参数1
     * @param param2 参数2
     * @param callback 回调接口
     */
    public void getData(String param1, int param2, NetworkCallback<Data> callback) {
        RxApiService apiService = RxRetrofitClient.createApiService();
        
        Observable<ApiResponse> observable = apiService.getData(param1, param2);
        
        Disposable disposable = observable
            .subscribeOn(Schedulers.io())              // 在IO线程执行网络请求
            .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理结果
            .subscribe(
                // onNext - 成功回调
                response -> {
                    if (response.isSuccess()) {
                        callback.onSuccess(response.getData());
                    } else {
                        callback.onError(response.getMessage());
                    }
                },
                // onError - 错误回调
                throwable -> {
                    callback.onError(throwable.getMessage());
                },
                // onComplete - 完成回调
                () -> {
                    // 请求完成
                }
            );
        
        // 添加到CompositeDisposable统一管理
        compositeDisposable.add(disposable);
    }
    
    /**
     * 执行POST请求
     * @param username 用户名
     * @param password 密码
     * @param callback 回调接口
     */
    public void submitData(String username, String password, NetworkCallback<String> callback) {
        RxApiService apiService = RxRetrofitClient.createApiService();
        
        Observable<ApiResponse> observable = apiService.submitData(username, password);
        
        Disposable disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                response -> {
                    if (response.isSuccess()) {
                        callback.onSuccess("提交成功");
                    } else {
                        callback.onError(response.getMessage());
                    }
                },
                throwable -> callback.onError(throwable.getMessage())
            );
        
        compositeDisposable.add(disposable);
    }
    
    /**
     * 取消所有请求
     * 在Activity/Fragment销毁时调用
     */
    public void cancelAllRequests() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = new CompositeDisposable();
        }
    }
}

// ==================== 5. 回调接口定义 ====================
/**
 * 网络请求回调接口
 */
public interface NetworkCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}

// ==================== 6. 在Activity中使用示例 ====================
/**
 * 在Activity中使用RxJava网络请求的示例
 * 注意：继承BaseActivity以自动管理订阅
 */
public class RxMainActivity extends AppCompatActivity {
    private static final String TAG = "RxMainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 执行网络请求
        loadData();
    }
    
    private void loadData() {
        NetworkManager.getInstance().getData(
            "param1", 
            100, 
            new NetworkCallback<Data>() {
                @Override
                public void onSuccess(Data data) {
                    // 请求成功，更新UI
                    String content = data.getContent();
                    Log.d(TAG, "获取数据成功: " + content);
                }
                
                @Override
                public void onError(String errorMessage) {
                    // 请求失败，显示错误
                    Log.e(TAG, "获取数据失败: " + errorMessage);
                }
            }
        );
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消所有网络请求，防止内存泄漏
        NetworkManager.getInstance().cancelAllRequests();
    }
}

// ==================== 7. BaseActivity示例（可选） ====================
/**
 * BaseActivity示例
 * 自动管理网络请求的生命周期
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 自动取消所有网络请求
        NetworkManager.getInstance().cancelAllRequests();
    }
}

// ==================== 8. 链式调用示例 ====================
/**
 * 链式请求示例
 * 演示如何处理多个依赖的网络请求
 */
public class ChainRequestExample {
    
    /**
     * 先请求用户信息，再请求用户详情
     */
    public void loadUserDetail(String userId) {
        RxApiService apiService = RxRetrofitClient.createApiService();
        
        apiService.getUser(userId)
            .flatMap(user -> {
                // 使用第一个请求的结果执行第二个请求
                return apiService.getUserDetail(user.getId());
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                userDetail -> {
                    // 最终处理用户详情
                },
                throwable -> {
                    // 错误处理
                }
            );
    }
}

// ==================== RxJava常用操作符说明 ====================
/*
1. map - 转换数据
   .map(response -> response.getData())

2. flatMap - 链式请求
   .flatMap(data -> apiService.getNextRequest(data.getId()))

3. filter - 过滤数据
   .filter(data -> data.isValid())

4. retry - 重试机制
   .retry(3) // 重试3次

5. retryWhen - 条件重试
   .retryWhen(errors -> errors.zipWith(Observable.range(1, 3), (err, attempt) -> {
       if (attempt == 3) return Observable.error(err);
       return Observable.timer((long) Math.pow(2, attempt), TimeUnit.SECONDS);
   }))

6. timeout - 超时控制
   .timeout(10, TimeUnit.SECONDS)

7. doOnSubscribe - 订阅时操作
   .doOnSubscribe(() -> showLoading())

8. doFinally - 无论成功失败都会执行
   .doFinally(() -> hideLoading())

9. compose - 自定义操作符
   .compose(bindToLifecycle())
*/
