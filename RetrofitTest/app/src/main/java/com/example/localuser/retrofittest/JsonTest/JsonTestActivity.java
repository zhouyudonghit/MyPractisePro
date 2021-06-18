package com.example.localuser.retrofittest.JsonTest;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.localuser.retrofittest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class JsonTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "jsontest--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test_main);
//        test8();
        test9();
    }

    public void test1()
    {
//        String json = "{ \"firstName\":1 , \"lastName\":\"Gates\",\"age\":null }";
        String json = "{ \"userAge\":null }";
        TestBean2 testBean2 = new Gson().fromJson(json,TestBean2.class);
        Toast.makeText(this,"age = "+testBean2.getAge(),Toast.LENGTH_LONG).show();
        Integer a = 2;
        int b = 1;
        b = a;
        Toast.makeText(this,"b = "+b,Toast.LENGTH_LONG).show();
//        TestBean2 testBean2 = new TestBean2();
//        testBean2.setStr(json);
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            Map map = transferJsonToMap(json);
//            String json2 = transferMapToJsonString(map);
//            Log.d(TAG,"");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
        testBean2.setId(1);
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

        Log.d(TAG,new Gson().toJson(16));
    }

    public void test7()
    {
        //GsonTest.fieldRenameTest();
        //Time time = TimeUnit.MILLISECONDS.toNanos();
//        LatLng latLng = new LatLng(102.324324324324,145.23433242343);
//        Log.d(TAG,new Gson().toJson(latLng));
//        LatLng latLng1 = new Gson().fromJson("{\"latitude\":90.0,\"longitude\":145.23433242343}",LatLng.class);
//        Log.d(TAG,"latLng1 = "+latLng1);
        int[] a = new int[]{0,1,2,5};
        double[] intList = new double[]{1,2,3.4,5.6};
        Gson gson = new Gson();
        TestBean testBean = new TestBean();
//        List<Double> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
        testBean.setDoubleList(intList);
        String json = "{\"objlist\":[1,2.0,3.4,5.6]}";
//        String json = "{\"doublelist\":[1,2]}";
        Log.d(TAG,gson.fromJson(json,TestBean.class).getObjlist().toString());
    }

    public void test8()
    {
        TestBean2 testBean2 = new TestBean2();
        testBean2.setStr("ha ha");
        String jsonStr1 = new Gson().toJson(testBean2);
        Log.d(TAG,"jsonStr1 = "+jsonStr1);
        String jsonStr2 = "{ \"userAge\":0, \"str\":\"ha  ha\", \"id\":0}";
        try {
            TestBean2 testBean21 = new Gson().fromJson(jsonStr2, TestBean2.class);
        }catch (Exception e)
        {
            Log.e(TAG,"",e);
        }
    }

    //弹窗提醒-等级提示 1014
    //弹窗提醒-经验值提示 1015
    //弹窗提醒-勋章提醒 1016
    private void test9()
    {
        TaskMessageData data = new TaskMessageData();
        data.setExpNum(11);
        data.setName("测试任务");
        data.setFunctionId(1015);
        Log.d(TAG,new Gson().toJson(data));

        TaskMessageData data2 = new TaskMessageData();
        data2.setExpNum(11);
        data2.setName("金牌");
        data2.setFunctionId(1014);
        Log.d(TAG,new Gson().toJson(data));
    }
}
