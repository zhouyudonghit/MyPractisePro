// RxJavaRequestExample.java - Retrofit + RxJava请求示例
// 演示如何结合RxJava使用Retrofit进行网络请求

package com.example.localuser.retrofittest;

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
import android.util.Log;
import android.widget.TextView;

/**
 * RxJava + Retrofit 网络请求示例
 */
public class RxJavaRequestExample {
    
    private static final String TAG = "RxJavaRequestExample";
    private static final String BASE_URL = "http://fy.iciba.com/";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    
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
    
    // ==================== RxJava API接口定义 ====================
    public interface GetRequestRxjava_Interface {
        @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
        Observable<Translation> getCall();
        
        @GET("ajax.php")
        Observable<Translation> translate(
            @Query("a") String a,
            @Query("f") String f,
            @Query("t") String t,
            @Query("w") String w
        );
    }
    
    // ==================== 创建Retrofit实例 ====================
    private GetRequestRxjava_Interface createRxApiService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava适配器
            .build();
        
        return retrofit.create(GetRequestRxjava_Interface.class);
    }
    
    // ==================== 示例1: 基本RxJava请求 ====================
    public void basicRxJavaRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        request.getCall()
            .subscribeOn(Schedulers.io())              // 在IO线程执行网络请求
            .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理结果
            .subscribe(new Observer<Translation>() {
                @Override
                public void onSubscribe(Disposable d) {
                    // 订阅开始，可以显示加载动画
                    Log.d(TAG, "开始请求");
                }
                
                @Override
                public void onNext(Translation translation) {
                    // 请求成功，处理数据
                    if (translation != null) {
                        String result = translation.getContent();
                        Log.d(TAG, "翻译结果: " + result);
                        
                        if (resultTextView != null) {
                            resultTextView.setText(result);
                        }
                    }
                }
                
                @Override
                public void onError(Throwable e) {
                    // 请求失败
                    Log.e(TAG, "请求失败", e);
                }
                
                @Override
                public void onComplete() {
                    // 请求完成
                    Log.d(TAG, "请求完成");
                }
            });
    }
    
    // ==================== 示例2: 使用Lambda简化代码 ====================
    public void lambdaRxJavaRequest(final TextView resultTextView, String word) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        Observable<Translation> observable = request.translate("fy", "auto", "auto", word);
        
        Disposable disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                translation -> {
                    // onNext
                    if (translation != null) {
                        String result = translation.getContent();
                        Log.d(TAG, "翻译结果: " + result);
                        if (resultTextView != null) {
                            resultTextView.setText(result);
                        }
                    }
                },
                throwable -> {
                    // onError
                    Log.e(TAG, "请求失败", throwable);
                },
                () -> {
                    // onComplete
                    Log.d(TAG, "请求完成");
                }
            );
        
        // 将Disposable添加到CompositeDisposable中统一管理
        compositeDisposable.add(disposable);
    }
    
    // ==================== 示例3: 链式请求 ====================
    /**
     * 先翻译单词，再翻译结果的第一个字符
     */
    public void chainRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        request.getCall()
            .subscribeOn(Schedulers.io())
            .flatMap(translation -> {
                // 使用第一个请求的结果
                String content = translation.getContent();
                String firstChar = content.substring(0, 1);
                
                // 返回第二个请求
                return request.translate("fy", "auto", "auto", firstChar);
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                translation -> {
                    // 处理最终结果
                    if (translation != null && resultTextView != null) {
                        resultTextView.setText("链式翻译结果: " + translation.getContent());
                    }
                },
                throwable -> {
                    Log.e(TAG, "链式请求失败", throwable);
                }
            );
    }
    
    // ==================== 示例4: 错误重试 ====================
    public void retryRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        request.getCall()
            .subscribeOn(Schedulers.io())
            .retry(3) // 失败时重试3次
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                translation -> {
                    if (translation != null && resultTextView != null) {
                        resultTextView.setText(translation.getContent());
                    }
                },
                throwable -> {
                    Log.e(TAG, "重试3次后仍失败", throwable);
                }
            );
    }
    
    // ==================== 示例5: 数据转换 ====================
    public void mapTransformRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        request.getCall()
            .subscribeOn(Schedulers.io())
            .map(translation -> {
                // 转换数据：只提取内容字段
                return translation != null ? translation.getContent() : "无内容";
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                content -> {
                    // 直接收到转换后的String
                    Log.d(TAG, "转换后的内容: " + content);
                    if (resultTextView != null) {
                        resultTextView.setText(content);
                    }
                },
                throwable -> {
                    Log.e(TAG, "请求失败", throwable);
                }
            );
    }
    
    // ==================== 示例6: 过滤数据 ====================
    public void filterRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        request.getCall()
            .subscribeOn(Schedulers.io())
            .filter(translation -> {
                // 过滤：只处理状态码为1的数据
                return translation != null && translation.getStatus() == 1;
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                translation -> {
                    // 只有通过filter的数据才会到这里
                    if (translation != null && resultTextView != null) {
                        resultTextView.setText("有效数据: " + translation.getContent());
                    }
                },
                throwable -> {
                    Log.e(TAG, "请求失败", throwable);
                }
            );
    }
    
    // ==================== 示例7: 合并多个请求 ====================
    public void mergeRequest(final TextView resultTextView) {
        GetRequestRxjava_Interface request = createRxApiService();
        
        Observable<Translation> observable1 = request.translate("fy", "auto", "auto", "hello");
        Observable<Translation> observable2 = request.translate("fy", "auto", "auto", "world");
        
        Observable.merge(observable1, observable2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                translation -> {
                    // 每个请求的结果都会触发
                    if (translation != null && resultTextView != null) {
                        String currentText = resultTextView.getText().toString();
                        resultTextView.setText(currentText + "\n" + translation.getContent());
                    }
                },
                throwable -> {
                    Log.e(TAG, "合并请求失败", throwable);
                }
            );
    }
    
    // ==================== 清理资源 ====================
    /**
     * 取消所有订阅，防止内存泄漏
     * 在Activity/Fragment的onDestroy中调用
     */
    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            Log.d(TAG, "已取消所有订阅");
        }
    }
    
    /**
     * 清理静态示例
     */
    public static void cleanup() {
        // 在应用退出时调用
    }
}

// ==================== 在Activity中使用示例 ====================
/*
public class RxJavaActivity extends AppCompatActivity {
    private RxJavaRequestExample rxJavaExample;
    private TextView resultTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        
        resultTextView = findViewById(R.id.result_text);
        rxJavaExample = new RxJavaRequestExample();
        
        // 执行网络请求
        rxJavaExample.basicRxJavaRequest(resultTextView);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 重要：取消所有订阅，防止内存泄漏
        if (rxJavaExample != null) {
            rxJavaExample.dispose();
        }
    }
}
*/
