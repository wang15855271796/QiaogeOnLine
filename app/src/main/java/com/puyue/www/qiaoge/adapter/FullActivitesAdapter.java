package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/8
 */
public class FullActivitesAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public FullActivitesAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_given = helper.getView(R.id.tv_given);
        tv_given.setText(item);
    }
}
