package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.VipListModel;

import java.util.List;

public class VipListAdapter extends BaseQuickAdapter<VipListModel.DataBean, BaseViewHolder> {

    int selectPosition = -1;
    public VipListAdapter(int layoutResId, @Nullable List<VipListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipListModel.DataBean item) {
        helper.setText(R.id.tv_pay,item.getChannelName());
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        if(item.getFlag()==2) {
            iv_pic.setImageResource(R.mipmap.ic_pay_alipay);
        }else {
            iv_pic.setImageResource(R.mipmap.ic_pay_wechar);
        }

        if(selectPosition == helper.getLayoutPosition()) {
            iv_choose.setImageResource(R.mipmap.ic_pay_ok);
        }else {
            iv_choose.setImageResource(R.mipmap.ic_pay_no);
        }
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
