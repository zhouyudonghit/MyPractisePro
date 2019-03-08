package com.example.localuser.retrofittest.OKhttpTest;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttpTest {
    private String TAG = LogConfigs.TAG_PREFIX_OKHTTP+"OkHttpTest";
    private OkHttpClient mOkHttpClient;

    public OkHttpTest()
    {
        mOkHttpClient = new OkHttpClient();
    }
    //异步get请求
    public void test1()
    {
        String url = "http://wwww.baidu.com";
        final Request request = new Request.Builder().url(url).get().build();
        Call call = mOkHttpClient.newCall(request);
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
        final Request request = new Request.Builder().url(url).get().build();
        Call call = mOkHttpClient.newCall(request);
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

        Request request = new Request.Builder().url("https://free-api.heweather.com/s6/air/now?location=beijing&key=f464c53cb02240a194640685ee425116")
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

    public void testPostJson()
    {
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonStr = "";
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON,jsonStr);
        Request request = new Request.Builder().url("https://free-api.heweather.com/s6/air/now?location=beijing&key=f464c53cb02240a194640685ee425116")
                .post(requestBody)
                .build();
        request.headers()
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void testPostFile()
    {
        //MultipartBody.FORM;
    }
}
