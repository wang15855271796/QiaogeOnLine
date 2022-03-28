package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;

import java.util.List;

public class CouponSpecAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean.ProdSpecsBean, BaseViewHolder> {

    public CouponSpecAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean.ProdSpecsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean.ProdSpecsBean item) {
        helper.setText(R.id.tv_spec,item.getSpec());
    }
}
