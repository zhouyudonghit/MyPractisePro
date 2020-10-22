package com.example.localuser.retrofittest.SerializeTest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class D implements Parcelable {
    private List<String> str;
    private List<B> blist;
    private List<Integer> intList;
    private B b;

    protected D(Parcel in) {
        str = in.createStringArrayList();
        blist = in.createTypedArrayList(B.CREATOR);
        b = in.readParcelable(B.class.getClassLoader());
    }

    public static final Creator<D> CREATOR = new Creator<D>() {
        @Override
        public D createFromParcel(Parcel in) {
            return new D(in);
        }

        @Override
        public D[] newArray(int size) {
            return new D[size];
        }
    };

    public void setBlist(List<B> blist) {
        this.blist = blist;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }


    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }


    public List<B> getBlist() {
        return blist;
    }

    public void setBlist(ArrayList<B> blist) {
        this.blist = blist;
    }

    public List<String> getStr() {
        return str;
    }

    public void setStr(List<String> str) {
        this.str = str;
    }

    public D()
    {

    }


    @Override
    public String toString() {
        return "str = "+str + ",blist = "+blist +",b="+b;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(str);
        dest.writeTypedList(blist);
        dest.writeParcelable(b, flags);
    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeStringList(str);
//        dest.writeTypedList(blist);
////        dest.writeSerializable(blist);不行
////        dest.writeList(blist);不行
////        dest.writeValue(blist);不行
//        dest.writeParcelable(b,0);
//    }
}
