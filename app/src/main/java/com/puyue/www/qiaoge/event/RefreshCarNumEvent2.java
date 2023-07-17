package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

public class RefreshCarNumEvent2 {

    List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX;

    public RefreshCarNumEvent2(List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prodsBeanX = prodsBeanX;
    }

    public List<CartTestModel.DataBean.ProdsBeanX> getProdsBeanX() {
        return prodsBeanX;
    }

    public void setProdsBeanX(List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prodsBeanX = prodsBeanX;
    }
}
