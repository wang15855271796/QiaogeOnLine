package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoCouponModel;

import java.util.List;

public class HuoCouponAdapter extends BaseQuickAdapter<HuoCouponModel.DataBean, BaseViewHolder> {

    int pos = -1;
    public HuoCouponAdapter(int layoutResId, @Nullable List<HuoCouponModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoCouponModel.DataBean item) {
        if(item.getDiscount_type()==1) {
            helper.setText(R.id.tv_style,"立减券");
        }else {
            helper.setText(R.id.tv_style,"折扣券");
        }
        helper.setText(R.id.tv_title,item.getPacket_name());
        helper.setText(R.id.tv_price,item.getDiscount_str());

        if(pos == helper.getLayoutPosition()) {
            helper.setVisible(R.id.iv_pic,true);
            helper.setBackgroundRes(R.id.rl_root,R.mipmap.huo_coupon_en);
        }else {
            helper.setVisible(R.id.iv_pic,false);
            helper.setBackgroundRes(R.id.rl_root,R.mipmap.huo_coupon_un);
        }


    }

    public void setSelectionPosition(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}
