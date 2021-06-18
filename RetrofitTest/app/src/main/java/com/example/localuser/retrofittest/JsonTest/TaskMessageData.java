package com.example.localuser.retrofittest.JsonTest;

import android.support.annotation.Keep;

/**
 * 成长体系任务弹窗的透传消息里面的数据bean
 */
@Keep
public class TaskMessageData {
    //成长经验值，任务完成弹窗所用
    private int expNum;
    //云钻值，任务弹窗所用
    private int pointNum;
    private int functionId;
    //可以是等级名称、勋章名称、任务完成名称
    private String name;
    private String icon;
    private long time;

    public int getExpNum() {
        return expNum;
    }

    public void setExpNum(int expNum) {
        this.expNum = expNum;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
