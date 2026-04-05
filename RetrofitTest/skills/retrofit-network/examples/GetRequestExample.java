// GetRequestExample.java - GET请求示例
// 基于项目中的实际代码修改

package com.example.localuser.retrofittest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import android.util.Log;
import android.widget.TextView;

/**
 * 翻译API请求示例
 * 演示如何使用Retrofit执行GET请求
 */
public class GetRequestExample {
    
    private static final String TAG = "GetRequestExample";
    private static final String BASE_URL = "http://fy.iciba.com/";
    
    // ==================== 数据模型 ====================
    public static class Translation {
        private int status;
        private String content;
        private java.util.Map<String, String> dictionary;
        
        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public java.util.Map<String, String> getDictionary() { return dictionary; }
        public void setDictionary(java.util.Map<String, String> dictionary) { 
            this.dictionary = dictionary; 
        }
    }
    
    // ==================== API接口定义 ====================
    public interface GetRequest_Interface {
        /**
         * 翻译请求 - 固定参数
         */
        @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
        Call<Translation> getCall();
        
        /**
         * 翻译请求 - 动态参数
         * @param word 要翻译的单词
         */
        @GET("ajax.php")
        Call<Translation> translate(
            @Query("a") String a,
            @Query("f") String f,
            @Query("t") String t,
            @Query("w") String w
        );
    }
    
    // ==================== 请求执行示例 ====================
    
    /**
     * 示例1: 使用固定参数的GET请求
     */
    public void executeFixedGetRequest(final TextView resultTextView) {
        // 创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        // 创建API接口
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        
        // 执行请求
        Call<Translation> call = request.getCall();
        
        // 异步请求
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response.isSuccessful()) {
                    Translation translation = response.body();
                    if (translation != null) {
                        // 显示翻译结果
                        String result = translation.getContent();
                        Log.d(TAG, "翻译结果: " + result);
                        
                        if (resultTextView != null) {
                            resultTextView.setText(result);
                        }
                    }
                } else {
                    Log.e(TAG, "响应错误: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.e(TAG, "请求失败", t);
            }
        });
    }
    
    /**
     * 示例2: 使用动态参数的GET请求
     */
    public void executeDynamicGetRequest(final TextView resultTextView, String word) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        
        // 使用动态参数
        Call<Translation> call = request.translate("fy", "auto", "auto", word);
        
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response.isSuccessful()) {
                    Translation translation = response.body();
                    if (translation != null) {
                        String result = translation.getContent();
                        Log.d(TAG, "翻译结果: " + result);
                        
                        if (resultTextView != null) {
                            resultTextView.setText(result);
                        }
                    }
                }
            }
            
            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.e(TAG, "请求失败", t);
            }
        });
    }
    
    /**
     * 示例3: 同步请求（在后台线程中使用）
     */
    public Translation executeSyncRequest(String word) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            
            GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
            
            Call<Translation> call = request.translate("fy", "auto", "auto", word);
            
            // 同步执行
            Response<Translation> response = call.execute();
            
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.e(TAG, "响应错误: " + response.code());
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "同步请求失败", e);
            return null;
        }
    }
    
    /**
     * 示例4: 取消请求
     */
    public void cancelRequest() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        
        Call<Translation> call = request.getCall();
        
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 处理响应
            }
            
            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                // 处理错误
            }
        });
        
        // 在适当时机取消请求（如Activity销毁时）
        call.cancel();
    }
    
    /**
     * 示例5: 处理多个请求
     */
    public void executeMultipleRequests(final TextView textView) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        
        String[] words = {"hello", "world", "retrofit"};
        
        for (String word : words) {
            Call<Translation> call = request.translate("fy", "auto", "auto", word);
            call.enqueue(new Callback<Translation>() {
                @Override
                public void onResponse(Call<Translation> call, Response<Translation> response) {
                    if (response.isSuccessful()) {
                        Translation translation = response.body();
                        if (translation != null && textView != null) {
                            String currentText = textView.getText().toString();
                            textView.setText(currentText + "\n" + translation.getContent());
                        }
                    }
                }
                
                @Override
                public void onFailure(Call<Translation> call, Throwable t) {
                    Log.e(TAG, "请求失败", t);
                }
            });
        }
    }
}
