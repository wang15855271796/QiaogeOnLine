package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.VipCenterModel;

import java.util.List;

public class VipAdapter extends BaseQuickAdapter<VipCenterModel.DataBean.VipPackagesBean, BaseViewHolder> {

    int pos = 0;
    String state;
    public VipAdapter(int layoutResId, @Nullable List<VipCenterModel.DataBean.VipPackagesBean> data, String state) {
        super(layoutResId, data);
        this.state = state;
    }

    @Override
    protected void convert(BaseViewHolder helper, VipCenterModel.DataBean.VipPackagesBean item) {
        TextView tv_month = helper.getView(R.id.tv_month);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_open = helper.getView(R.id.tv_open);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        tv_month.setText(item.getTitle());
        tv_price.setText(item.getPrice());


        if(state.equals("ENABLED") || state.equals("OVER")) {
            tv_open.setVisibility(View.GONE);
        }else {
//            tv_open.setVisibility(View.VISIBLE);
            if(helper.getAdapterPosition()==0) {
                tv_open.setVisibility(View.VISIBLE);
            }else {
                tv_open.setVisibility(View.GONE);
            }
        }
        if(pos==helper.getAdapterPosition()) {
            ll_root.setBackgroundResource(R.drawable.shape_jianbian22);
        }else {
            ll_root.setBackgroundResource(R.drawable.shape_grey15);
        }


    }

    public void setPos(int position) {
        this.pos = position;
    }
}
