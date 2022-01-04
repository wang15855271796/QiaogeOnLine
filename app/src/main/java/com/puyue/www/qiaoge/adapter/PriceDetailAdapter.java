package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.DealPriceModel;

import java.util.List;

public class PriceDetailAdapter extends BaseQuickAdapter<DealPriceModel.DataBean.CalculatePriceInfoBean, BaseViewHolder> {
    public PriceDetailAdapter(int layoutResId, @Nullable List<DealPriceModel.DataBean.CalculatePriceInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DealPriceModel.DataBean.CalculatePriceInfoBean item) {
        helper.setText(R.id.tv_desc,item.getName());
        helper.setText(R.id.tv_price,item.getAmount());
    }
}
