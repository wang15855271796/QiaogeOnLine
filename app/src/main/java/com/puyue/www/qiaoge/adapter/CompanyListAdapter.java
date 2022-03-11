package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CompanyListModel;

import java.util.List;

public class CompanyListAdapter extends BaseQuickAdapter<CompanyListModel.DataBean, BaseViewHolder> {

    int pos = -1;
    public CompanyListAdapter(int layoutResId, @Nullable List<CompanyListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyListModel.DataBean item) {
        helper.setText(R.id.tv_short,item.getShortName());
        helper.setText(R.id.tv_long,item.getCompanyName());
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        if(pos==helper.getAdapterPosition()) {
            iv_choose.setVisibility(View.VISIBLE);
        }else {
            iv_choose.setVisibility(View.GONE);
        }
    }

    public void setSelectionPos(int position) {
        this.pos = position;
    }
}
