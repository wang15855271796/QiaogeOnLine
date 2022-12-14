package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HisModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/20
 */
public class HisAddressAdapter extends BaseQuickAdapter<HisModel.DataBean,BaseViewHolder> {
    ImageView iv_choose;
    int selectionPosition = -1;
    public HisAddressAdapter(int layoutResId, @Nullable List<HisModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HisModel.DataBean item) {
        TextView tv_address = helper.getView(R.id.tv_address);
        RelativeLayout rl_root = helper.getView(R.id.rl_root);
        iv_choose = helper.getView(R.id.iv_choose);
        tv_address.setText(item.getDetailAddress());
        if(helper.getAdapterPosition()==selectionPosition) {
            rl_root.setBackgroundResource(R.drawable.shape_orange22);
            tv_address.setTextColor(Color.parseColor("#FF2A00"));
        }else {
            rl_root.setBackgroundResource(R.drawable.shape_white1);
            tv_address.setTextColor(Color.parseColor("#414141"));
        }
    }

    public void setNotify(int selectionPosition) {
       this.selectionPosition = selectionPosition;
    }
}
