package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/11
 */
public class CartGoodsEvent {
    List<CartTestModel.DataBean.ProdsBeanX> data;
    boolean isSelect;
    public CartGoodsEvent(List<CartTestModel.DataBean.ProdsBeanX> data, boolean isSelect) {
        this.data = data;
        this.isSelect = isSelect;
    }

    public List<CartTestModel.DataBean.ProdsBeanX> getData() {
        return data;
    }

    public void setData(List<CartTestModel.DataBean.ProdsBeanX> data) {
        this.data = data;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
