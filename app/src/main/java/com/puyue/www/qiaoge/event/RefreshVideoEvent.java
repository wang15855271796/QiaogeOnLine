package com.puyue.www.qiaoge.event;

public class RefreshVideoEvent {
    int productId;
    int pos;
    public RefreshVideoEvent(int productId,int pos) {
        this.productId = productId;
        this.pos = pos;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
