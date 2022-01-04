package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoDetailModel;

import java.util.List;

public class HuoPayAdapter extends BaseQuickAdapter<HuoDetailModel.DataBean.PriceInfoBean, BaseViewHolder> {

    public HuoPayAdapter(int layoutResId, @Nullable List<HuoDetailModel.DataBean.PriceInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoDetailModel.DataBean.PriceInfoBean item) {
        helper.setText(R.id.tv_price,item.getAmount()+"");
        helper.setText(R.id.tv_desc,item.getBillTypeName());
    }
}
