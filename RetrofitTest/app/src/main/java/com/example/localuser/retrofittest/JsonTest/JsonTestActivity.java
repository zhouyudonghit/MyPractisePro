package com.example.localuser.retrofittest.JsonTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.localuser.retrofittest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "jsontest--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test_main);
        TestBean testBean = new TestBean();
        testBean.date = new Date();
        List<TestBean2> list = new ArrayList<>();
        TestBean2 testBean2 = new TestBean2();
        testBean2.setStr("dsafds");
        list.add(testBean2);
        testBean.setList(list);
        String json = new Gson().toJson(testBean);
        Log.d(TAG,json);
        //Dec 27, 2018 18:33:55
            //json = "{\"date\":\"Dec 27, 2018 18:33:55 am\"}";
        String json2 = "[{\"str\":\"dsafds\"}]";
        try{
            TestBean testBean1 = new Gson().fromJson(json,TestBean.class);
            //Log.d(TAG,testBean1.date.toString());
            List<TestBean2> datas = new Gson().fromJson(json2,new TypeToken<List<TestBean2>>(){}.getType());
            Log.d(TAG,datas.toString());
        }catch (Exception e)
        {
            Log.d(TAG,"",e);
        }
        test4();
    }

    public void test1()
    {
        String json = "{ \"firstName\":1 , \"lastName\":\"Gates\" }";
        TestBean2 testBean2 = new TestBean2();
        testBean2.setStr(json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            Map map = transferJsonToMap(json);
            String json2 = transferMapToJsonString(map);
            Log.d(TAG,"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> transferJsonToMap(String json)
    {
        if(TextUtils.isEmpty(json))
        {
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> jsonKeys = jsonObject.keys();
            if(jsonKeys != null)
            {
                while (jsonKeys.hasNext())
                {
                    String key = jsonKeys.next();
                    map.put(key,jsonObject.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String transferMapToJsonString(Map<String,Object> map)
    {
//        if(DataUtil.isMapEmpty(map))
//        {
//            return null;
//        }
        JSONObject jsonObject = new JSONObject();
        Set<String> set = map.keySet();
        String jsonStr = "";
        if(set != null)
        {
            try {
                for (String key : set) {
                    jsonObject.put(key, map.get(key));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            jsonStr = jsonObject.toString();
        }
        return jsonStr;
    }

    public void test2()
    {
        LocalInternetRealTimeDataBean dataBean = new LocalInternetRealTimeDataBean();
        dataBean.setSn_calorie(0.45f);
        String json = new Gson().toJson(dataBean);
        new Gson().fromJson(json,LocalInternetRealTimeDataBean.class);
        Log.d(TAG,"");
    }

    public void test3()
    {
        TestBean2 testBean2 = new TestBean2();
        testBean2.setStr("test haha");
        new Gson().toJson(testBean2);
    }

    public void test4()
    {
        String json = "{ \"a\":1 , \"b\":\"Gates\" }";
        TestBean2 testBean2 = new Gson().fromJson(json,TestBean2.class);
        Log.d(TAG,"");
    }
}
