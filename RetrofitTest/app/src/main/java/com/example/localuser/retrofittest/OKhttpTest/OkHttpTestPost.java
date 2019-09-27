package com.example.localuser.retrofittest.OKhttpTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class OkHttpTestPost extends BaseOkHttpTest {

    public OkHttpTestPost(boolean https) {
        super(https);
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
        Headers headers = new Headers.Builder().add("test","12")
                .add("test1","456").build();
        Request request = new Request.Builder().url("https://free-api.heweather.com/s6/air/now?location=beijing&key=f464c53cb02240a194640685ee425116")
                .post(requestBody)
                .headers(headers)
                .build();
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

    /**
     * 参考链接https://blog.csdn.net/qq_40543575/article/details/79232311
     * @param url
     * @param map
     * @param file
     */
    public void testPostFile1(String url, Map<String,Object> map,File file)
    {
        //MultipartBody.FORM;
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        /**
         * json : application/json
         xml : application/xml
         png : image/png
         jpg : image/jpeg
         gif : imge/gif
         */
        if(file != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/"), file);
            multipartBody.addFormDataPart("file", file.getName(), requestBody);
        }
        if(map != null)
        {
            for(Map.Entry entry:map.entrySet())
            {
                if(entry != null) {
                    multipartBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        Request request = new Request.Builder().url(url).post(multipartBody.build()).build();
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

    /**
     * 参考链接https://blog.csdn.net/qq_40543575/article/details/79232311
     * @param params
     */
    public void testPostFiles(Map<String,Object> params)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(Map.Entry entry:params.entrySet())
        {
            String key = (String) entry.getKey();
            if(entry.getValue() instanceof File) {
                File file = (File) entry.getValue();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.addFormDataPart(key,file.getName(),requestBody);
            }else{
                builder.addFormDataPart(key,entry.getValue().toString());
            }
            Request request = new Request.Builder().url("").post(builder.build()).build();
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
    }
}
