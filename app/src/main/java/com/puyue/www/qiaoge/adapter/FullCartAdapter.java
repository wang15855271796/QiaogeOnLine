package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.CartFullsModel;

import java.util.ArrayList;
import java.util.List;

public class FullCartAdapter extends BaseQuickAdapter<CartFullsModel.DataBean.DeductDetailBean, BaseViewHolder> {
    List<CartFullsModel.DataBean.DeductDetailBean> dataBeans;
    List<CartFullsModel.DataBean.DeductDetailBean.SendProdsBean> sendProd1 = new ArrayList<>();
    List<CartFullsModel.DataBean.DeductDetailBean.SendProdsBean> sendProd2 = new ArrayList<>();
    public FullCartAdapter(int layoutResId, List<CartFullsModel.DataBean.DeductDetailBean> dataBeans) {
        super(layoutResId, dataBeans);
        this.dataBeans = dataBeans;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartFullsModel.DataBean.DeductDetailBean item) {
        TextView tv_desc = helper.getView(R.id.tv_desc);
        TextView tv_given = helper.getView(R.id.tv_given);
        RecyclerView rv_coupon = helper.getView(R.id.rv_coupon);
        RecyclerView recyclerView = helper.getView(R.id.recycleView);
        tv_desc.setText(item.getLimitInfo());
        tv_given.setText(item.getDeductInfo());
        sendProd1 = new ArrayList<>();
        sendProd2 = new ArrayList<>();
        for (int i = 0; i < item.getSendProds().size(); i++) {
            if(item.getSendProds().get(i).getType()==0) {
                sendProd1.add(item.getSendProds().get(i));
            }else {
                sendProd2.add(item.getSendProds().get(i));
            }
        }

        //商品
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FullCartsAdapter fullCartsAdapter = new FullCartsAdapter(R.layout.item_fulls_cart,sendProd1);
        recyclerView.setAdapter(fullCartsAdapter);

        //优惠券
        CartCouponsAdapter couponsAdapter = new CartCouponsAdapter(R.layout.item_cart_coupon,sendProd2);
        rv_coupon.setAdapter(couponsAdapter);
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));

//        if(item.getType()==0) {
//            //商品
//            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//            FullCartsAdapter fullCartsAdapter = new FullCartsAdapter(R.layout.item_fulls_cart,data);
//            recyclerView.setAdapter(fullCartsAdapter);
//        }else {
//            //优惠券
//            CartCouponsAdapter couponsAdapter = new CartCouponsAdapter(R.layout.item_cart_coupon,data);
//            rv_coupon.setAdapter(couponsAdapter);
//            rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));
//        }

    }
}
