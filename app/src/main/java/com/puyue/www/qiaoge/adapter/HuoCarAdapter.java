package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CarPriceModel;

import java.util.List;

public class HuoCarAdapter extends BaseQuickAdapter<CarPriceModel.DataBean.CarSpecBean, BaseViewHolder> {

    public HuoCarAdapter(int layoutResId, @Nullable List<CarPriceModel.DataBean.CarSpecBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarPriceModel.DataBean.CarSpecBean item) {
        helper.setText(R.id.tv_desc,item.getName());
        helper.setText(R.id.tv_name,item.getDesc());
    }
}
