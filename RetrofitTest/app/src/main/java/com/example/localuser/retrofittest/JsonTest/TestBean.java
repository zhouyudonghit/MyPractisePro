package com.example.localuser.retrofittest.JsonTest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class TestBean implements Parcelable {
    public transient Date date;
    private List<TestBean2> list;
    private double[] doubleList;
    private List<Integer> intlist;
    private List<Double> doublelist;
    private List<String> strlist;
    private List<Object> objlist;

    public List<Object> getObjlist() {
        return objlist;
    }

    public void setObjlist(List<Object> objlist) {
        this.objlist = objlist;
    }

    public List<String> getStrlist() {
        return strlist;
    }

    public void setStrlist(List<String> strlist) {
        this.strlist = strlist;
    }

    public List<Double> getDoublelist() {
        return doublelist;
    }

    public void setDoublelist(List<Double> doublelist) {
        this.doublelist = doublelist;
    }

    public double[] getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(double[] doubleList) {
        this.doubleList = doubleList;
    }

    public List<Integer> getIntlist() {
        return intlist;
    }

    public void setIntlist(List<Integer> intlist) {
        this.intlist = intlist;
    }

    public TestBean()
    {

    }

    protected TestBean(Parcel in) {
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
    }

    public List<TestBean2> getList() {
        return list;
    }

    public void setList(List<TestBean2> list) {
        this.list = list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
