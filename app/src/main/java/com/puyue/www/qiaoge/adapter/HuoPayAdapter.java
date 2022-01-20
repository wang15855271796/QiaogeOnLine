package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoPriceModel;

import java.util.List;

public class HuoPayAdapter extends BaseQuickAdapter<HuoPriceModel, BaseViewHolder> {

    public HuoPayAdapter(int layoutResId, @Nullable List<HuoPriceModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoPriceModel item) {
        helper.setText(R.id.tv_price,item.getAmount()+"");
        helper.setText(R.id.tv_desc,item.getBillTypeName());
    }
}
