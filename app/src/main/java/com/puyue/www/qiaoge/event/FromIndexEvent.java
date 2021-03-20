package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2021/1/18
 */
public class FromIndexEvent {
    String id;
    public FromIndexEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
