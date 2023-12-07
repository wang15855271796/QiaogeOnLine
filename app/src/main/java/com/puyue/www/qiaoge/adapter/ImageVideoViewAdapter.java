package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.helper.AppHelper;

import java.util.Arrays;
import java.util.List;

public class ImageVideoViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private RoundImageView imageView;
    public ImageVideoViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        imageView = helper.getView(R.id.iv_image);
//        ImageView iv_player = helper.getView(R.id.iv_player);
        Glide.with(mContext).load(item).into(imageView);
//        if(item.contains(".mp4")) {
//            iv_player.setVisibility(View.VISIBLE);
//        }else {
//            iv_player.setVisibility(View.GONE);
//        }
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(item.contains(".mp4")) {
//
//                }else {
//                    List<String> result = Arrays.asList(item.split(","));
//                    AppHelper.showPhotoDetailDialog(mContext, result, helper.getAdapterPosition());
//                }
//
//            }
//        });
    }
}
