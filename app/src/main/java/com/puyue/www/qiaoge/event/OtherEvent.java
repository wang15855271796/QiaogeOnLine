package com.puyue.www.qiaoge.event;

import java.util.List;

public class OtherEvent {
    List<String> list;
    public OtherEvent(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
