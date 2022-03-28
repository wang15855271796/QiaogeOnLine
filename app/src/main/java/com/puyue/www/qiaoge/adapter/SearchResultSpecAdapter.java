package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;

import java.util.List;

public class SearchResultSpecAdapter extends BaseQuickAdapter<SearchResultsModel.DataBean.RecommendProdBean.ProdSpecsBean, BaseViewHolder> {

    public SearchResultSpecAdapter(int layoutResId, @Nullable List<SearchResultsModel.DataBean.RecommendProdBean.ProdSpecsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultsModel.DataBean.RecommendProdBean.ProdSpecsBean item) {
        helper.setText(R.id.tv_spec,item.getSpec());
    }
}
