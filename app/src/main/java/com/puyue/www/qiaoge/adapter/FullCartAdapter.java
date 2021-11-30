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

import java.util.ArrayList;
import java.util.List;

public class FullCartAdapter extends BaseQuickAdapter<CartFullModel.DataBean, BaseViewHolder> {
    String deductInfo;
    List<CartFullModel.DataBean> dataBeans;
    List<CartFullModel.DataBean.SendProdsBean> sendProd1 = new ArrayList<>();
    List<CartFullModel.DataBean.SendProdsBean> sendProd2 = new ArrayList<>();
    public FullCartAdapter(int layoutResId, String deductInfo, List<CartFullModel.DataBean> dataBeans) {
        super(layoutResId, dataBeans);
        this.deductInfo = deductInfo;
        this.dataBeans = dataBeans;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartFullModel.DataBean item) {
        TextView tv_desc = helper.getView(R.id.tv_desc);
        RecyclerView rv_coupon = helper.getView(R.id.rv_coupon);
        RecyclerView recyclerView = helper.getView(R.id.recycleView);
        tv_desc.setText(item.getDeductInfo());

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
