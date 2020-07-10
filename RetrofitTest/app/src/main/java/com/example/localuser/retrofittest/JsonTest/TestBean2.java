package com.example.localuser.retrofittest.JsonTest;

import com.google.gson.annotations.SerializedName;

public class TestBean2 extends AbstractTestBean{
    private String str;
    @SerializedName("userAge")
    private Integer age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        String s = "str = "+str+",age = "+age;
        return s;
    }
}
