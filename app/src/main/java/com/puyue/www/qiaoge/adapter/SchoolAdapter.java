package com.puyue.www.qiaoge.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.SchoolVideoListModel;
import com.puyue.www.qiaoge.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends BaseQuickAdapter<SchoolVideoListModel.DataBean.VideosBean, BaseViewHolder> {

    public SchoolAdapter(int layoutResId, @Nullable List<SchoolVideoListModel.DataBean.VideosBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolVideoListModel.DataBean.VideosBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getPicUrl()).into(iv_pic);
        helper.setText(R.id.tv_title,item.getVideoName());
        helper.setText(R.id.tv_desc,item.getVideoDesc());
        helper.setText(R.id.tv_player_num,item.getViewNum());
        helper.setText(R.id.tv_time,item.getDateTime());
    }
}
