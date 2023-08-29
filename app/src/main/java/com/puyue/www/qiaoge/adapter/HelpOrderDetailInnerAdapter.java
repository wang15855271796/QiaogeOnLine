package com.puyue.www.qiaoge.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;

import java.util.List;

public class HelpOrderDetailInnerAdapter extends BaseQuickAdapter<GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean, BaseViewHolder> {

    public HelpOrderDetailInnerAdapter(int layoutResId, @Nullable List<GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean item) {
        RoundImageView imageView = helper.getView(R.id.imageView);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        RecyclerView rv_price = helper.getView(R.id.rv_price);
        Glide.with(mContext).load(item.picUrl).into(imageView);
        if(!TextUtils.isEmpty(item.prodTypeUrl)) {
            iv_icon.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.prodTypeUrl).into(iv_icon);
        }else {
            iv_icon.setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_spec,item.spec);
        helper.setText(R.id.tv_title,item.productName);
        helper.setText(R.id.tv_coupon_price,item.afterPrice);
        helper.setText(R.id.tv_old_price,item.oldPrice);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_current_price,item.price);
        OrderPriceAdapter orderPriceAdapter = new OrderPriceAdapter(R.layout.item_order_desc,item.specPrices);
        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        rv_price.setAdapter(orderPriceAdapter);
    }
}
