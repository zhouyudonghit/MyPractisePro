package com.example.localuser.retrofittest.JsonTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.localuser.retrofittest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
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
        test6();
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

    public void test5()
    {
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        Gson gson = new Gson();
        Log.d(TAG,"json = "+gson.toJson(list));

        List list2 = gson.fromJson(gson.toJson(list),new TypeToken<List<String>>(){}.getType());
        Log.d(TAG,"list2 = "+list2);
    }

    public void test6()
    {
        String json = "[{\"applyId\":39,\"custNum\":\"7001977774\",\"nickName\":\"\",\"headImg\":\"http:\\/\\/uimgpre.cnsuning.com\\/uimg\\/cmf\\/cust_headpic\\/5fdf0a15ea2a1ce4cfc0b2a3572c8f2b_00_120x120.jpg?v= 08194559\",\"uSex\":\"\",\"applyTime\":\"2019-08-20 11:00:04\"}]";
        try {
            JSONArray jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
