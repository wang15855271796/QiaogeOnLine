package com.puyue.www.qiaoge.adapter;

import android.transition.Visibility;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoListModel;

import java.util.List;

public class MyHuoAdapter extends BaseQuickAdapter<HuoListModel.DataBean.ListBean, BaseViewHolder> {

    String type;
    public MyHuoAdapter(int layoutResId, @Nullable List<HuoListModel.DataBean.ListBean> data, String type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoListModel.DataBean.ListBean item) {

        helper.setText(R.id.tv_date,item.getOrder_time());
        helper.setText(R.id.tv_state,item.getOrder_status_name());
        helper.setText(R.id.tv_zhuang,item.getSend_address());
        helper.setText(R.id.tv_xie,item.getReceive_address());
        helper.setText(R.id.tv_price,item.getTotalPrice());

        if(type.equals("1")) {
            //进行中
            helper.setVisible(R.id.tv_state, true);
            helper.setVisible(R.id.tv_price, false);
        }else {
            helper.setVisible(R.id.tv_state, false);
            helper.setVisible(R.id.tv_price, true);
        }
    }
}
