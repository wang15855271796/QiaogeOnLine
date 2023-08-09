package com.puyue.www.qiaoge.fragment.cart;

/**
 * Created by ${王涛} on 2019/10/25
 */
public class UpdateEvent {
    private String discribe;
    boolean isChecked;
    String productMainId;
    int cartId;
    int checkFlag;
    public UpdateEvent(String discribe) {
        this.discribe = discribe;
    }

    public UpdateEvent(String discribe, boolean isChecked, String productMainId, int cartId, int checkFlag) {
        this.discribe = discribe;
        this.isChecked = isChecked;
        this.productMainId = productMainId;
        this.cartId = cartId;
        this.checkFlag = checkFlag;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getProductMainId() {
        return productMainId;
    }

    public void setProductName(String productMainId) {
        this.productMainId = productMainId;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }
}
