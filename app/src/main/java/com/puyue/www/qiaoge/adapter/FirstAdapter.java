package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.api.market.ClassIfyModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/3/30
 */
public class FirstAdapter extends BaseQuickAdapter<ClassIfyModel.DataBean,BaseViewHolder> {
    int selectPosition = 0;

    public FirstAdapter(int layoutResId, @Nullable List<ClassIfyModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassIfyModel.DataBean item) {
        RoundImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        tv_desc.setText(item.getName());
        Glide.with(mContext).load(item.getImgUrl()).into(iv_icon);

        if(selectPosition == helper.getLayoutPosition()) {
            tv_desc.setBackgroundResource(R.drawable.shape_markets);
            tv_desc.setTextColor(Color.parseColor("#ffffff"));
        }else {
            tv_desc.setBackground(null);
            tv_desc.setTextColor(Color.parseColor("#5a5a5a"));
        }
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

}
