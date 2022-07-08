package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.AreasModel;

import java.util.List;

public class AddAddressAdapter extends BaseQuickAdapter<AreasModel.DataBean, BaseViewHolder> {

    public AddAddressAdapter(int layoutResId, @Nullable List<AreasModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreasModel.DataBean item) {
        TextView tv_area = helper.getView(R.id.tv_area);
        tv_area.setText(item.getDetailAddress());
    }
}
