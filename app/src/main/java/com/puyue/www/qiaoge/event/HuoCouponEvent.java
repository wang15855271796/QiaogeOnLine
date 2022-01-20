package com.puyue.www.qiaoge.event;

public class HuoCouponEvent {
    String amount;
    String coupon_id;
    public HuoCouponEvent(String amount, String coupon_id) {
        this.amount = amount;
        this.coupon_id = coupon_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }
}
