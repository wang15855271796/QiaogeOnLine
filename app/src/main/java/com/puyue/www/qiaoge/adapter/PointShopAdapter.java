package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.PointShopModel;

import java.util.List;

public class PointShopAdapter extends BaseQuickAdapter<PointShopModel.DataBean.DeductsBean, BaseViewHolder> {

    public PointShopAdapter(int layoutResId, @Nullable List<PointShopModel.DataBean.DeductsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PointShopModel.DataBean.DeductsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_condition,item.getAppRoleDesc());
        helper.setText(R.id.tv_role,item.getAppActiveRole());
        helper.setText(R.id.tv_exchange,"兑换价:"+item.getPoint()+"积分");
        helper.setText(R.id.tv_time,"有效期:"+item.getEffectTime());
    }
}
