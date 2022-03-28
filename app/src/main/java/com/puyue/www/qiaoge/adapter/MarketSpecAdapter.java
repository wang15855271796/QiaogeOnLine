package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.market.MarketRightModel;

import java.util.List;

public class MarketSpecAdapter extends BaseQuickAdapter<MarketRightModel.DataBean.ProdClassifyBean.ListBean.ProdSpecsBean, BaseViewHolder> {

    public MarketSpecAdapter(int layoutResId, @Nullable List<MarketRightModel.DataBean.ProdClassifyBean.ListBean.ProdSpecsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketRightModel.DataBean.ProdClassifyBean.ListBean.ProdSpecsBean item) {
        helper.setText(R.id.tv_spec,item.getSpec());
    }
}
