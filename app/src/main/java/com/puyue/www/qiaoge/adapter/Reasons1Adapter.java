package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CancelReasonModel;

import java.util.List;

public class Reasons1Adapter extends BaseQuickAdapter<CancelReasonModel.DataBean.SubReasonBean, BaseViewHolder> {
    int pos = -1;
    public Reasons1Adapter(int layoutResId, @Nullable List<CancelReasonModel.DataBean.SubReasonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CancelReasonModel.DataBean.SubReasonBean item) {
        helper.setText(R.id.tv_reason,item.getName());
        TextView tv_reason = helper.getView(R.id.tv_reason);
        if(pos == helper.getAdapterPosition()) {
            tv_reason.setTextColor(Color.parseColor("#FD6601"));
        }else {
            tv_reason.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void setSelectPosition(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}
