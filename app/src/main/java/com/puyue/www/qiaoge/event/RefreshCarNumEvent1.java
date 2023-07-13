package com.puyue.www.qiaoge.event;

import android.widget.TextView;

import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

public class RefreshCarNumEvent1 {
    CartTestModel.DataBean.ProdsBeanX.ProdsBean prods;
    int num;
    int productCombinationPriceId;
    TextView tv_num;
    List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX;
    public RefreshCarNumEvent1(CartTestModel.DataBean.ProdsBeanX.ProdsBean prods, int num, int productCombinationPriceId
            , TextView tv_num, List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prods = prods;
        this.num = num;
        this.tv_num = tv_num;
        this.prodsBeanX = prodsBeanX;
        this.productCombinationPriceId = productCombinationPriceId;

    }

    public RefreshCarNumEvent1( List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prodsBeanX = prodsBeanX;

    }


    public List<CartTestModel.DataBean.ProdsBeanX> getProdsBeanX() {
        return prodsBeanX;
    }

    public void setProdsBeanX(List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prodsBeanX = prodsBeanX;
    }

    public TextView getTv_num() {
        return tv_num;
    }

    public void setTv_num(TextView tv_num) {
        this.tv_num = tv_num;
    }

    public CartTestModel.DataBean.ProdsBeanX.ProdsBean getProds() {
        return prods;
    }

    public void setProds(CartTestModel.DataBean.ProdsBeanX.ProdsBean prods) {
        this.prods = prods;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProductCombinationPriceId() {
        return productCombinationPriceId;
    }

    public void setProductCombinationPriceId(int productCombinationPriceId) {
        this.productCombinationPriceId = productCombinationPriceId;
    }
}
