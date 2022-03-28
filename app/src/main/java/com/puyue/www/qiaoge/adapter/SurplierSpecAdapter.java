package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.SurpliListModel;

import java.util.List;

public class SurplierSpecAdapter extends BaseQuickAdapter<SurpliListModel.DataBean.ListBean.ProdSpecsBean, BaseViewHolder> {

    public SurplierSpecAdapter(int layoutResId, @Nullable List<SurpliListModel.DataBean.ListBean.ProdSpecsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SurpliListModel.DataBean.ListBean.ProdSpecsBean item) {
        helper.setText(R.id.tv_spec,item.getSpec());
    }
}
