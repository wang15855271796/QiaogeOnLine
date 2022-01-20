package com.puyue.www.qiaoge.event;

public class HuoCityEvent {
    String name;
    String cityId;
    public HuoCityEvent(String name, String cityId) {
        this.name = name;
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
