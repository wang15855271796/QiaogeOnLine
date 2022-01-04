package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CarPriceModel;

import java.util.List;

public class HuoAskAdapter extends BaseQuickAdapter<CarPriceModel.DataBean.OtherReqBean, BaseViewHolder> {

    public HuoAskAdapter(int layoutResId, @Nullable List<CarPriceModel.DataBean.OtherReqBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarPriceModel.DataBean.OtherReqBean item) {
        helper.setText(R.id.tv_desc,item.getName());
        helper.setText(R.id.tv_name,item.getDesc());
    }
}
