package com.puyue.www.qiaoge.fragment.cart;

/**
 * Created by ${王涛} on 2019/11/16
 */
public class ReduceNumEvent {

    int checkFlag;
    int cartId;
    public ReduceNumEvent() {

    }

    public ReduceNumEvent(int cartId, int checkFlag) {

        this.cartId = cartId;
        this.checkFlag = checkFlag;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }


}
