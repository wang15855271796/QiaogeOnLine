package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2021/2/23
 */
public class changeEvent {
    boolean b;
    String i;
    public changeEvent(boolean b,String i) {
        this.b = b;
        this.i = i;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
}
