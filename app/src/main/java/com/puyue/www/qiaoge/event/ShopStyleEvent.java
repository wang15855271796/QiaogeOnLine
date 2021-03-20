package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2021/1/6
 */
public class ShopStyleEvent {
    String datum;
    int position;
    public ShopStyleEvent(String datum,int position) {
        this.datum = datum;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
