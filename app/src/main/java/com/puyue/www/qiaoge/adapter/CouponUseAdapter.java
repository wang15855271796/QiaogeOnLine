package com.puyue.www.qiaoge.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.model.CouponListsModel;

import java.util.List;

public class CouponUseAdapter extends BaseQuickAdapter<CouponListsModel.DataBean.ListBean, BaseViewHolder> {

    public CouponUseAdapter(int layoutResId, @Nullable List<CouponListsModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListsModel.DataBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getProductName());
        helper.setText(R.id.tv_spec,item.getSpec());
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);


    }
}
