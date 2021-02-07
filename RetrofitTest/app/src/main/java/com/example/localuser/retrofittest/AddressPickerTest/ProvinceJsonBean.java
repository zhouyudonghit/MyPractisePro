package com.example.localuser.retrofittest.AddressPickerTest;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class ProvinceJsonBean implements IPickerViewData {
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


    @Override
    public String getPickerViewText() {
        return name;
    }
}
