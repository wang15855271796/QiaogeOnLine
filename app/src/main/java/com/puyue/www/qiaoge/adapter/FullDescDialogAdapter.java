package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.CartFullsModel;

import java.util.List;

public class FullDescDialogAdapter extends BaseQuickAdapter<CartFullsModel.DataBean.DeductDetailBean, BaseViewHolder> {


    public FullDescDialogAdapter(int layoutResId, List<CartFullsModel.DataBean.DeductDetailBean> deductDetail) {
        super(layoutResId, deductDetail);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartFullsModel.DataBean.DeductDetailBean item) {
        TextView tv_style = helper.getView(R.id.tv_style);
        tv_style.setText(item.getDeductInfo());
    }
}
