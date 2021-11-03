package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.model.home.CouponModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/9
 */
public class CommonTestAdapter extends BaseQuickAdapter<CouponModel.DataBean.ActivesBean,BaseViewHolder> {

    public CommonTestAdapter(int layoutResId, @Nullable List<CouponModel.DataBean.ActivesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.DataBean.ActivesBean item) {

    }
}
