package com.puyue.www.qiaoge.event;

public class HllOrderEvent {
    String hllOrderId;
    public HllOrderEvent(String hllOrderId) {
        this.hllOrderId = hllOrderId;
    }

    public String getHllOrderId() {
        return hllOrderId;
    }

    public void setHllOrderId(String hllOrderId) {
        this.hllOrderId = hllOrderId;
    }
}
