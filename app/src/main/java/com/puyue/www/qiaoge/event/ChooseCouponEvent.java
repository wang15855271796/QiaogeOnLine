package com.puyue.www.qiaoge.event;

/**
 * Created by ${王涛} on 2020/8/10
 */
public class ChooseCouponEvent {
    String GiftDetailNo;

    public ChooseCouponEvent(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }

    public String getGiftDetailNo() {
        return GiftDetailNo;
    }

    public void setGiftDetailNo(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }
}
