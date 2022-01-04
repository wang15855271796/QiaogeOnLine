package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CarPriceModel;

import java.util.List;

public class HuoBaseAdapter extends BaseQuickAdapter<CarPriceModel.DataBean.BasicFeeBean, BaseViewHolder> {

    public HuoBaseAdapter(int layoutResId, @Nullable List<CarPriceModel.DataBean.BasicFeeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarPriceModel.DataBean.BasicFeeBean item) {
        helper.setText(R.id.tv_desc,item.getName());
        helper.setText(R.id.tv_deal,item.getDesc());
    }
}
