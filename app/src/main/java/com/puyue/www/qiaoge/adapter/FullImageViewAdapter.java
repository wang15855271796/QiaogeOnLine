package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.FullActiveActivity;
import com.puyue.www.qiaoge.event.FullListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullImageViewAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    FullListModel.DataBean items;
    public FullImageViewAdapter(int layoutResId, FullListModel.DataBean item, @Nullable List<String> data) {
        super(layoutResId, data);
        this.items = item;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item).into(iv_pic);
        LinearLayout ll_root = helper.getView(R.id.ll_root);

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FullActiveActivity.class);
                intent.putExtra("fullId",items.getFullId());
                mContext.startActivity(intent);
            }
        });

    }
}
