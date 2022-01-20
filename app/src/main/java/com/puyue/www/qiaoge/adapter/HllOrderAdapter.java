package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

public class HllOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int pos = -1;
    public HllOrderAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_desc = helper.getView(R.id.tv_desc);
        tv_desc.setText(item);

        if(helper.getAdapterPosition()==pos) {
            tv_desc.setTextColor(Color.parseColor("#999999"));
        }else {
            tv_desc.setTextColor(Color.parseColor("#FD6601"));
        }
    }

    public void setSelectionPos(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}
