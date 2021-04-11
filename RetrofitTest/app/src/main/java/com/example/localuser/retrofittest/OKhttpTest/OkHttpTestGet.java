package com.example.localuser.retrofittest.OKhttpTest;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttpTestGet extends BaseOkHttpTest{
    public OkHttpTestGet(boolean https)
    {
        super(https);
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

    //test https of ca
    public void test3()
    {
        //该网址不安证书可以访问
        String url = "https://kyfw.12306.cn";
//        String url = "https://www.sina.com.cn/";
//        String url = "https://free-api.heweather.com/s6/air/now?location=beijing&key=f464c53cb02240a194640685ee425116";
//        String url = "https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_8fee767bce184aaa93e1e2b232663f6e";
        final Request request = new Request.Builder().get().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"fail,"+Thread.currentThread().getName()+","+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"suc,"+Thread.currentThread().getName()+",body = "+response.body().toString());
            }
        });
    }
}
