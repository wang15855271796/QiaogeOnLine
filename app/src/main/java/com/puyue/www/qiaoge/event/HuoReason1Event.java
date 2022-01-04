package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.CancelReasonModel;

import java.util.List;

public class HuoReason1Event {
    String name;
    public HuoReason1Event( String name) {

        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
