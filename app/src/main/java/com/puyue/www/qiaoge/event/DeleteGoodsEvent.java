package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.cart.CartTestModel;

public class DeleteGoodsEvent {
    int adapterPosition;

    CartTestModel.DataBean.ProdsBeanX.ProdsBean item;
    public DeleteGoodsEvent(int adapterPosition, CartTestModel.DataBean.ProdsBeanX.ProdsBean item) {
        this.adapterPosition = adapterPosition;
        this.item = item;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public CartTestModel.DataBean.ProdsBeanX.ProdsBean getItem() {
        return item;
    }

    public void setItem(CartTestModel.DataBean.ProdsBeanX.ProdsBean item) {
        this.item = item;
    }
}
