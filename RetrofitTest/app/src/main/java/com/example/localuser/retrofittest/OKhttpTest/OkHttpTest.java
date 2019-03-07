package com.example.localuser.retrofittest.OKhttpTest;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTest {
    private String TAG = LogConfigs.TAG_PREFIX_OKHTTP+"OkHttpTest";

    //异步get请求
    public void test1()
    {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        //call的回调是在子线程，不能直接操作UI
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"fail,"+Thread.currentThread().getName());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"suc,"+Thread.currentThread().getName()+",body = "+response.body().toString());
            }
        });
    }

    //同步Get请求
    public void test2()
    {
        String url = "http://wwww.baidu.com";
        OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);
        try {
            //该过程会阻塞UI线程，所以下面代码正确的写法应该放在子线程，否则会报异常
            Response response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,e.getLocalizedMessage());
        }
    }

    /**
     * 测试提交key-value对
     */
    public void testPostKeyValue()
    {
        RequestBody requestBody = new FormBody.Builder().add("username","zhouyudong")
                .add("password","123456")
                .build();

        Request request = new Request.Builder().url("http://wwww.baidu.com")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
