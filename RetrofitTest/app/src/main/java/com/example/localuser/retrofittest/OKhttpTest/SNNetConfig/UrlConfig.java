package com.example.localuser.retrofittest.OKhttpTest.SNNetConfig;

import com.example.localuser.retrofittest.Utils.AppUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class UrlConfig {
    private final static Env env = Env.SIT;

    public static String HEALTH_BASE_TEST_URL;
    public static String mGetHomeAdList = "api/ad/getAdList";

    public static void init()
    {
        if(env == Env.SIT)
        {
            HEALTH_BASE_TEST_URL = "https://shmsapisit2.cnsuning.com/";
        }else if(env == Env.PRE)
        {
            HEALTH_BASE_TEST_URL = "https://shmsapipre.cnsuning.com/";
        }else if(env == Env.PRD)
        {
            HEALTH_BASE_TEST_URL = "https://shmsapi.suning.com/";
        }
    }

    public static final String INTERFACE_VERSION = "version";
    public static final String QUERY_RESULT_FORMAT = "format";
    public static final String APPPLT = "appplt";
    public static final String APP_VERSION = "appversion";
    public static final String APP_ID = "appid";

    public static final String QUERY_RESULT_FORMAT_VALUE = "json";
    public static final String APPPLT_VALUE = "android";

    public static List<NameValuePair> getCommonPairList()
    {
        String APP_ID_VALUE = AppUtils.getPackageName();
        String APP_VERSION_VALUE = AppUtils.getVersionName();
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair(APPPLT,APPPLT_VALUE));
        list.add(new BasicNameValuePair(QUERY_RESULT_FORMAT,QUERY_RESULT_FORMAT_VALUE));
        list.add(new BasicNameValuePair(APP_ID,"com.suning.health"));
        list.add(new BasicNameValuePair(APP_VERSION,"2.1.0"));
        list.add(new BasicNameValuePair(INTERFACE_VERSION,"1.1"));
        return list;
    }
}
