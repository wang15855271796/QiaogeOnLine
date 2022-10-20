package com.puyue.www.qiaoge.event;

public class DisTribution1Event {
    String desc;
    int type;
    public DisTribution1Event(String desc,int type) {
        this.desc = desc;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
