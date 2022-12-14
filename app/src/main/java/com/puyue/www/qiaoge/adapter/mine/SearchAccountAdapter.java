package com.puyue.www.qiaoge.adapter.mine;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.SearchListModel;

import java.util.List;

/**
 * Created by ${王文博} on 2019/8/16
 */
public class SearchAccountAdapter extends BaseQuickAdapter<SearchListModel.DataBean.List1Bean, BaseViewHolder> {

    private TextView mTvContent;
    private ImageView iv_select;

    private int selectPosition = -1;

    public SearchAccountAdapter(int layoutResId, @Nullable List<SearchListModel.DataBean.List1Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchListModel.DataBean.List1Bean item) {
        mTvContent = helper.getView(R.id.tv_content);
        iv_select = helper.getView(R.id.iv_select);
        mTvContent.setText(item.getValue());

        if (selectPosition == helper.getAdapterPosition()) {
            mTvContent.setBackgroundResource(R.drawable.shape_red10);
            mTvContent.setTextColor(Color.parseColor("#FF5A30"));
        } else {
            mTvContent.setBackgroundResource(R.drawable.shape_grey13);
            mTvContent.setTextColor(Color.parseColor("#414141"));
        }
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
