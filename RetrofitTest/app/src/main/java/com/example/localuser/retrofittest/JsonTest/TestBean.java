package com.example.localuser.retrofittest.JsonTest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class TestBean implements Parcelable {
    public transient Date date;
    private List<TestBean2> list;

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
}
