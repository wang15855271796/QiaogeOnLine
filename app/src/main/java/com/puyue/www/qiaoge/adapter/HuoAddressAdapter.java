package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.AddressListModel;

import java.util.List;

public class HuoAddressAdapter extends BaseQuickAdapter<AddressListModel.DataBean, BaseViewHolder> {

    public HuoAddressAdapter(int layoutResId, @Nullable List<AddressListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressListModel.DataBean item) {
        helper.setText(R.id.tv_location,item.getName());
        helper.setText(R.id.tv_address,item.getAddr());
        helper.setText(R.id.tv_distance,item.getDistance());
    }
}
