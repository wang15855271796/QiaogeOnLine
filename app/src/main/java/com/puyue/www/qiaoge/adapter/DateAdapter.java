package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

public class DateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int pos = 0;
    public DateAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_date = helper.getView(R.id.tv_date);
        tv_date.setText(item);
        if(pos == helper.getLayoutPosition()) {
            tv_date.setTextColor(Color.parseColor("#FF5C00"));
            tv_date.setBackgroundResource(R.drawable.shape_orange5);
        }else {
            tv_date.setTextColor(Color.parseColor("#FFFFFF"));
            tv_date.setBackgroundResource(R.drawable.shape_orange4);
        }
    }

    public void setSelectionPosition(int position) {
        this.pos = position;

    }
}
