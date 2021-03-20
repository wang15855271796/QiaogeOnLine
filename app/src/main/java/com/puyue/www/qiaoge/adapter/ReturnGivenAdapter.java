package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.mine.order.NewReturnOrderModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/7
 */
public class ReturnGivenAdapter extends BaseQuickAdapter<NewReturnOrderModel.DataBean.ProductsBean.AdditionList,BaseViewHolder> {

    public ReturnGivenAdapter(int layoutResId, @Nullable List<NewReturnOrderModel.DataBean.ProductsBean.AdditionList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewReturnOrderModel.DataBean.ProductsBean.AdditionList item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_icon);
        ImageView iv_flag = helper.getView(R.id.iv_flag);
        Glide.with(mContext).load(item.getTypeImg()).into(iv_flag);
        helper.setText(R.id.tv_title,item.getProductName());
        helper.setText(R.id.tv_spec,"规格:"+item.getSpec());
        TextView tv_given_num = helper.getView(R.id.tv_given_num);
        tv_given_num.setText("赠:"+item.getReturnNum());
        TextView tv_stock = helper.getView(R.id.tv_stock);
        tv_stock.setVisibility(View.GONE);

    }
}
