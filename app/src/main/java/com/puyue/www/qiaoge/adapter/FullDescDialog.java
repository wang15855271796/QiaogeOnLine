package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CartFullModel;

import java.util.List;

public class FullDescDialog extends BaseQuickAdapter<CartFullModel.DataBean, BaseViewHolder> {

    public FullDescDialog(int layoutResId, @Nullable List<CartFullModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartFullModel.DataBean item) {
        helper.setText(R.id.tv_desc,item.getDeductInfo());
    }
}
