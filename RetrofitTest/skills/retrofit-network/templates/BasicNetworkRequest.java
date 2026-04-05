// BasicNetworkRequest.java - Retrofit基本网络请求模板
// 使用说明：复制此文件并根据您的API需求修改

package com.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

// ==================== 1. 定义数据模型 ====================
/**
 * API响应数据模型
 * 根据您的API响应结构修改此字段
 */
public class ApiResponse {
    private int code;
    private String message;
    private Data data;

    // Getter和Setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}

/**
 * 数据详情模型
 * 根据实际数据结构修改
 */
public class Data {
    private String content;
    private long timestamp;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}

// ==================== 2. 定义API接口 ====================
/**
 * 网络请求接口
 * 使用注解定义请求方式和参数
 */
public interface ApiService {
    
    /**
     * GET请求示例
     * 使用Query参数
     */
    @GET("api/data")
    Call<ApiResponse> getData(
        @Query("param1") String param1,
        @Query("param2") int param2
    );
    
    /**
     * POST请求示例
     * 使用FormUrlEncoded表单数据
     */
    @FormUrlEncoded
    @POST("api/submit")
    Call<ApiResponse> submitData(
        @Field("username") String username,
        @Field("password") String password
    );
}

// ==================== 3. Retrofit客户端管理 ====================
/**
 * Retrofit单例管理类
 * 确保整个应用使用同一个Retrofit实例
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://api.example.com/"; // 修改为您的API基础URL
    private static Retrofit retrofit;
    
    private RetrofitClient() {}
    
    /**
     * 获取Retrofit实例（单例模式）
     */
    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
    
    /**
     * 创建API服务实例
     */
    public static ApiService createApiService() {
        return getInstance().create(ApiService.class);
    }
}

// ==================== 4. 网络请求执行示例 ====================
/**
 * 网络请求执行示例
 * 展示如何调用API和处理响应
 */
public class NetworkRequestExample {
    
    /**
     * 执行GET请求示例
     */
    public void performGetRequest() {
        ApiService apiService = RetrofitClient.createApiService();
        
        Call<ApiResponse> call = apiService.getData("value1", 100);
        
        // 异步执行
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    
                    // 处理成功响应
                    if (apiResponse.getCode() == 200) {
                        Data data = apiResponse.getData();
                        String content = data.getContent();
                        // 更新UI或处理数据
                    } else {
                        // 处理业务错误
                        String errorMessage = apiResponse.getMessage();
                    }
                } else {
                    // 处理HTTP错误
                    int errorCode = response.code();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // 处理网络错误
                String error = t.getMessage();
            }
        });
    }
    
    /**
     * 执行POST请求示例
     */
    public void performPostRequest(String username, String password) {
        ApiService apiService = RetrofitClient.createApiService();
        
        Call<ApiResponse> call = apiService.submitData(username, password);
        
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    
                    if (apiResponse.getCode() == 200) {
                        // 提交成功
                    } else {
                        // 提交失败，显示错误信息
                    }
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // 网络错误处理
            }
        });
    }
}

// ==================== 5. 在Activity/Fragment中使用示例 ====================
/**
 * 在Activity中使用网络请求的示例
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 执行网络请求
        loadData();
    }
    
    private void loadData() {
        NetworkRequestExample example = new NetworkRequestExample();
        example.performGetRequest();
    }
    
    // 注意：在Activity销毁时应该取消未完成的网络请求
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消请求的代码（如果有引用Call对象）
    }
}
