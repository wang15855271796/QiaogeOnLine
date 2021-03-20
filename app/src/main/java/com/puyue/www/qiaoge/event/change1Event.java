package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2021/2/23
 */
public class change1Event {

    boolean b;
    int i;
    public change1Event(boolean b,int i) {
        this.b = b;
        this.i = i;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}
