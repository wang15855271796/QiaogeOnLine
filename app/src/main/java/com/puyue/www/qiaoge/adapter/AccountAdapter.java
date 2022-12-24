package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.SearchListModel;
import com.puyue.www.qiaoge.view.BaseView;

import java.util.List;

public class AccountAdapter extends BaseQuickAdapter<SearchListModel.DataBean.List2Bean, BaseViewHolder> {
    private TextView tv_account;
    int pos = -1;
    public AccountAdapter(int layoutResId, @Nullable List<SearchListModel.DataBean.List2Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchListModel.DataBean.List2Bean item) {
        tv_account=helper.getView(R.id.tv_account);
        tv_account.setText(item.getValue());

        if(pos == helper.getAdapterPosition()) {
            tv_account.setBackgroundResource(R.drawable.shape_red10);
            tv_account.setTextColor(Color.parseColor("#FF5A30"));
        }else {
            tv_account.setBackgroundResource(R.drawable.shape_grey13);
            tv_account.setTextColor(Color.parseColor("#414141"));
        }
    }


    public void setPos(int position) {
        pos = position;
        notifyDataSetChanged();
    }
}
