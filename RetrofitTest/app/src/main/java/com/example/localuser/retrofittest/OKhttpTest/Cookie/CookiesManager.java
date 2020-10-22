package com.example.localuser.retrofittest.OKhttpTest.Cookie;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MyApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {
    private final PersistentCookieStore1 cookieStore = new PersistentCookieStore1(MyApplication.getInstance());
    private String TAG = LogConfigs.TAG_PREFIX_OKHTTP + getClass().getSimpleName();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        Log.d(TAG,"loadForRequest,url = "+url);
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
