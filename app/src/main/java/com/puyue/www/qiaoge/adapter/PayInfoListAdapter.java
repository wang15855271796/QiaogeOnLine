package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.PayInfoListModel;

import java.util.List;

public class PayInfoListAdapter extends BaseQuickAdapter<PayInfoListModel.DataBean, BaseViewHolder> {
    int pos = -1;
    public PayInfoListAdapter(int layoutResId, @Nullable List<PayInfoListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayInfoListModel.DataBean item) {
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        tv_title.setText(item.getChannelName());
        if(helper.getAdapterPosition()==0) {
            iv_pic.setImageResource(R.mipmap.ic_pay_alipay);
        }else {
            iv_pic.setImageResource(R.mipmap.ic_pay_wei);
        }

        if(pos == helper.getLayoutPosition()) {
            iv_choose.setImageResource(R.mipmap.icon_gou);
        }else {
            iv_choose.setImageResource(R.mipmap.icon_uncheck_oval);
        }
    }

    public void selectionPosition(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}
