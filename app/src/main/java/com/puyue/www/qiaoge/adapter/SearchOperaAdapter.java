package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

public class SearchOperaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int pos = 0;
    public SearchOperaAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_goods = helper.getView(R.id.tv_goods);
        tv_goods.setText(item);

        if(pos==helper.getAdapterPosition()) {
            tv_goods.setTextColor(Color.parseColor("#FF5C00"));
        }else {
            tv_goods.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void setOnItemPosition(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}
