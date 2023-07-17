package com.puyue.www.qiaoge.event;

import android.widget.TextView;

import com.puyue.www.qiaoge.adapter.cart.CartGoodsAdapter;
import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

public class RefreshCarNumEvent1 {
    CartTestModel.DataBean.ProdsBeanX.ProdsBean prods;
    int num;
    int productCombinationPriceId;
    TextView tv_num;
    List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX;
    int adapterPosition;
    CartGoodsAdapter cartGoodsAdapter;
    int size;
    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prodsBeans;
    public RefreshCarNumEvent1(CartTestModel.DataBean.ProdsBeanX.ProdsBean prods, int num, int productCombinationPriceId
            , TextView tv_num, List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        this.prods = prods;
        this.num = num;
        this.tv_num = tv_num;
        this.prodsBeanX = prodsBeanX;
        this.productCombinationPriceId = productCombinationPriceId;

    }

    public RefreshCarNumEvent1(List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX, int adapterPosition
            , List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prodsBeans, CartGoodsAdapter cartGoodsAdapter, int size) {
        this.prodsBeanX = prodsBeanX;
        this.adapterPosition = adapterPosition;
        this.prodsBeans = prodsBeans;
        this.size = size;
        this.cartGoodsAdapter = cartGoodsAdapter;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> getProdsBeans() {
        return prodsBeans;
    }

    public void setProdsBeans(List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prodsBeans) {
        this.prodsBeans = prodsBeans;
    }

    public CartGoodsAdapter getCartGoodsAdapter() {
        return cartGoodsAdapter;
    }

    public void setCartGoodsAdapter(CartGoodsAdapter cartGoodsAdapter) {
        this.cartGoodsAdapter = cartGoodsAdapter;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
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
