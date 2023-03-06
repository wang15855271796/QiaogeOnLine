package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.wallet.MyCountDetailActivity;
import com.puyue.www.qiaoge.model.mine.GetWallertRecordByPageModel;

import java.util.List;

public class RemainAdapter extends BaseQuickAdapter<GetWallertRecordByPageModel.DataBean.RecordsBean, BaseViewHolder> {

    public RemainAdapter(int layoutResId, @Nullable List<GetWallertRecordByPageModel.DataBean.RecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetWallertRecordByPageModel.DataBean.RecordsBean item) {
        helper.setText(R.id.tv_title,item.getFlowRecordTypeName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_price,item.getAmount());
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getIconUrl()).into(iv_pic);


    }
}
