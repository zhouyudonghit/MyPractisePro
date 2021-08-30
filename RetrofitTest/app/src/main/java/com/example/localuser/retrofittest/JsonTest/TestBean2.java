package com.example.localuser.retrofittest.JsonTest;

import com.google.gson.annotations.SerializedName;

public class TestBean2 extends AbstractTestBean{
    private String str;
    @SerializedName("userAge")
    private Integer age;
    private String telephone;

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
        return "TestBean2{" +
                "str='" + str + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
