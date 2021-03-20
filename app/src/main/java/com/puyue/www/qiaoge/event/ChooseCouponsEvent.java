package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2020/9/8
 */
public class ChooseCouponsEvent {

    String GiftDetailNo;

    public ChooseCouponsEvent(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }

    public String getGiftDetailNo() {
        return GiftDetailNo;
    }

    public void setGiftDetailNo(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }


}
