package com.example.localuser.retrofittest.View.bean;

import android.support.annotation.Keep;
import android.util.Log;

import com.example.localuser.retrofittest.Utils.DateUtil;

import java.util.Date;

@Keep
public class WeightInfo {
    private String createTime;
    private String weight;
    private float weightFloat;
    //从1开始的
    private int indexOfDay;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
        Date date = DateUtil.stringToDate(createTime,DateUtil.DATE_PATTERN_1);
        indexOfDay = DateUtil.getDayOfMonth(date);
        Log.d("WeightInfo","indexOfDay = "+indexOfDay);
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
        try{
            weightFloat = Float.valueOf(weight);
        }catch (Exception e)
        {
        }
    }

    public float getWeightFloat() {
        return weightFloat;
    }

    public int getIndexOfDay() {
        return indexOfDay;
    }
}
