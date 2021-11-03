package com.puyue.www.qiaoge.adapter.cart;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/12
 */
public class InvalidAdapter extends BaseQuickAdapter<CartTestModel.DataBean.InValidProdBean,BaseViewHolder> {

    public InvalidAdapter(int layoutResId, @Nullable List<CartTestModel.DataBean.InValidProdBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartTestModel.DataBean.InValidProdBean item) {
        RecyclerView rv_invalid = helper.getView(R.id.rv_invalid);
        rv_invalid.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
