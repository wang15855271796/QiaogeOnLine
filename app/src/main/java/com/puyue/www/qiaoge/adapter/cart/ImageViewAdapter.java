package com.puyue.www.qiaoge.adapter.cart;

import androidx.annotation.Nullable;

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.helper.AppHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ${王涛} on 2019/10/10
 */
public class ImageViewAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private RoundImageView imageView;
    public ImageViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        imageView = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> result = Arrays.asList(item.split(","));
                AppHelper.showPhotoDetailDialog(mContext, result, helper.getAdapterPosition());
            }
        });
    }
}
