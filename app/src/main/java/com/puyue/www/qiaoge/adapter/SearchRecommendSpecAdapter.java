package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;

import java.util.List;

public class SearchRecommendSpecAdapter extends BaseQuickAdapter<SearchResultsModel.DataBean.SearchProdBean.ListBean.ProdSpecsBean, BaseViewHolder> {

    public SearchRecommendSpecAdapter(int layoutResId, @Nullable List<SearchResultsModel.DataBean.SearchProdBean.ListBean.ProdSpecsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultsModel.DataBean.SearchProdBean.ListBean.ProdSpecsBean item) {
        helper.setText(R.id.tv_spec,item.getSpec());
    }
}
