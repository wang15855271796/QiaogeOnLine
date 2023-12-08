package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.PicVideoModel;

import java.util.List;

public class GoodsDetailBannerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GoodsDetailBannerAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_player = helper.getView(R.id.iv_player);
        if(item.endsWith(".mp4")) {
            iv_player.setVisibility(View.VISIBLE);
        }else {
            iv_player.setVisibility(View.GONE);
        }

        ImageView iv_image = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item).into(iv_image);
    }
}
