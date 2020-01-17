package com.example.localuser.retrofittest.OKhttpTest.SNNetConfig;

import android.text.TextUtils;
import android.util.Log;

import com.example.localuser.retrofittest.OKhttpTest.BaseOkHttpTest;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SuningNetTest extends BaseOkHttpTest {
    public SuningNetTest(boolean https) {
        super(https);
        UrlConfig.init();
    }

    public void test1()
    {
        String url = UrlConfig.HEALTH_BASE_TEST_URL + UrlConfig.mGetHomeAdList + "?" + buildRequestBody(UrlConfig.getCommonPairList());
        Log.d(TAG,"url = "+url);
        Request request = new Request.Builder().url(url).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"suc,"+response.toString());
            }
        });
    }

    /**
     * @param paramList
     *            参数列表
     * @return 请求体
     * @Description:
     * @Author 12075179
     * @Date 2015-6-9
     */
    protected static String buildRequestBody(List<NameValuePair> paramList) {
        if (paramList == null || paramList.isEmpty()) {
            return "";
        }
        StringBuilder requestBody = new StringBuilder();

        int size = paramList.size();
        NameValuePair param;
        for (int i = 0; i < size; i++) {
            param = paramList.get(i);
            String value = param.getValue();
            if (TextUtils.isEmpty(value)) {
                value = "";
            } else {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    value = param.getValue();
                }
            }
            requestBody.append(param.getName()).append('=').append(value);
            if (i < size - 1) {
                requestBody.append('&');
            }
        }
        return requestBody.toString();
    }
}
