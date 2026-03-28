package com.example.localuser.retrofittest.AddressPickerTest;

import java.util.List;

public class ProvinceJsonBean {
    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    public String getPickerViewText() {
        return name;
    }
}
