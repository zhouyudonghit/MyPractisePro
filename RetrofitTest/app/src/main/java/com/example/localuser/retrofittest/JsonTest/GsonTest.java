package com.example.localuser.retrofittest.JsonTest;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GsonTest {
    private static String TAG = LogConfigs.TAG_PREFIX_JSON_TEST+"GsonTest";
    private static Gson mGson = new Gson();

    /**
     * 将json字符串转化成实体对象
     * @param json
     * @param classOfT
     * @return
     */
    public static Object stringToObject( String json , Class classOfT){
        return  mGson.fromJson( json , classOfT ) ;
    }

    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }

    /**
     * 把json 字符串转化成list
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T>  List<T> stringToList(String json ,Class<T> cls  ){
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list ;
    }

    //该方法和上面的方法的区别是写法简单，但是有一条脏数据的话，什么也得不到，上面的方法如果在for循环里面捕获异常的话，可以剔除掉脏数据
    public static <T> List<T> stringToList2(String json,Class<T> cls)
    {
        List<T> list = mGson.fromJson(json,new TypeToken<ArrayList<T>>() {
        }.getType());
        return list;
    }

    public static void jsonObjectTest()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("number_integer",2);
        jsonObject.addProperty("number_float",1.0);
        jsonObject.addProperty("number_string","hahha");
        jsonObject.addProperty("number_boolean",true);
        jsonObject.addProperty("number_char",'r');
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("number_integer",2);
        jsonObject1.addProperty("number_float",1.0);
        jsonObject1.addProperty("number_string","hahha");
        jsonObject1.addProperty("number_boolean",true);
        jsonObject1.addProperty("number_char",'r');
        jsonObject.add("obj",jsonObject1);
        Log.d(TAG,"jsonObject.toString = " + jsonObject.toString());
    }

    public static void jsonArrayTest()
    {
        //Json数组 转为 字符串数组
        Gson gson = new Gson();
        String jsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        System.out.println("Json数组 转为 字符串数组: ");
        for (String string : strings) {
            System.out.println(string);
        }
        //字符串数组 转为 Json数组
//        jsonArray = gson.toJson(strings, String[].class);
        jsonArray = gson.toJson(strings);
        System.out.println("\n字符串数组 转为 Json数组: ");
        System.out.println(jsonArray);
    }

    public static void fieldRenameTest()
    {
//        TestBean2 testBean = new TestBean2();
//        testBean.setAge(20);
//        testBean.setStr("test");
//        Log.d(TAG,mGson.toJson(testBean));
        String json = "{\"userAge\":20,\"str\":\"test\",\"id\":0}";
        TestBean2 testBean2 = mGson.fromJson(json,TestBean2.class);
        Log.d(TAG,testBean2.toString());
    }
}
