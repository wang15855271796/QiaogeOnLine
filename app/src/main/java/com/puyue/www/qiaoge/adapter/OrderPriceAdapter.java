package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/10
 */
public class OrderPriceAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public OrderPriceAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        tv_price.setText(item);
    }
}
