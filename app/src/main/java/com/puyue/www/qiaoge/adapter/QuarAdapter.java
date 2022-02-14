package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

public class QuarAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public QuarAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item).into(iv_pic);
    }
}
