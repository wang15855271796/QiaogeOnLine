package com.puyue.www.qiaoge.fragment.home;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.mine.ShopDetailActivity;
import com.puyue.www.qiaoge.api.home.InfoListModel;
import com.puyue.www.qiaoge.view.RoundImageView;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/3
 */
public class MarketsAdapter extends BaseQuickAdapter<InfoListModel.DataBean.ListBean,BaseViewHolder> {
    public MarketsAdapter(int layoutResId, @Nullable List<InfoListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoListModel.DataBean.ListBean item) {
        helper.setIsRecyclable(false);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_player = helper.getView(R.id.iv_player);

        if(item.getVideoCoverUrl()!=null) {
            iv_player.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getVideoCoverUrl()).into(iv_pic);
        }else {
            if(item.getPictureList()!=null&&item.getPictureList().size()>0) {
                iv_player.setVisibility(View.GONE);
                Glide.with(mContext).load(item.getPictureList().get(0)).into(iv_pic);
            }
        }

        if(item.getMsgType().equals("1")) {
            helper.setText(R.id.tv_style,"店铺转让");
        }else if(item.getMsgType().equals("2")) {
            helper.setText(R.id.tv_style,"器具转让");
        }else if(item.getMsgType().equals("3")) {
            helper.setText(R.id.tv_style, "厨师招聘");
        }else {
            helper.setText(R.id.tv_style, "其他信息");
        }
        helper.setText(R.id.tv_title,item.getContent());
        helper.setText(R.id.tv_time,item.getCreateTime()+"发布");
        helper.setText(R.id.tv_num,item.getBrowseNum());
        helper.setText(R.id.tv_address,"地址:"+item.getAreaName());
        helper.getView(R.id.rl_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShopDetailActivity.class);
                intent.putExtra("msgId",item.getMsgId());
                mContext.startActivity(intent);
            }
        });
    }
}
