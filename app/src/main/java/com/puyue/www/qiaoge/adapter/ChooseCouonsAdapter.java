package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/24
 */
public class ChooseCouonsAdapter extends BaseQuickAdapter<UserChooseDeductModel.DataBean,BaseViewHolder> {

    public ChooseCouonsAdapter(int layoutResId, @Nullable List<UserChooseDeductModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserChooseDeductModel.DataBean item) {

    }
}
