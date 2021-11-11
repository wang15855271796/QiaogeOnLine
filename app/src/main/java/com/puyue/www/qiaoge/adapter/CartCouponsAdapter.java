package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

public class CartCouponsAdapter extends BaseQuickAdapter<CartFullModel.DataBean.SendProdsBean, BaseViewHolder> {

    public CartCouponsAdapter(int layoutResId, @Nullable List<CartFullModel.DataBean.SendProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartFullModel.DataBean.SendProdsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_num,item.getSendNum());
        helper.setText(R.id.tv_role,item.getRoles());
        helper.setText(R.id.tv_spec,item.getSpec());

    }
}
