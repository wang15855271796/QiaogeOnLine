package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2020/7/8
 */
public class ExchangeAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public ExchangeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_amount,"优惠券"+item+"元");
        helper.setText(R.id.tv_num,"*1");
        helper.setText(R.id.tv_expend,item+"元");

    }
}
