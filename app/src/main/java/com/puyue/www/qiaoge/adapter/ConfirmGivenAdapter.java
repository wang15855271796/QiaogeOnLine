package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;

import java.util.List;

public class ConfirmGivenAdapter extends BaseQuickAdapter<CartBalanceModel.DataBean.SystemAdditionBean, BaseViewHolder> {

    public ConfirmGivenAdapter(int layoutResId, @Nullable List<CartBalanceModel.DataBean.SystemAdditionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBalanceModel.DataBean.SystemAdditionBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getPicUrl()).into(iv_icon);
        ImageView iv_finish = helper.getView(R.id.iv_finish);
        ImageView iv_flag =  helper.getView(R.id.iv_flag);

        if(item.getFlagUrl()!=""||item.getFlagUrl()!=null) {
            Glide.with(mContext).load(item.getFlagUrl()).into(iv_flag);
            iv_flag.setVisibility(View.VISIBLE);
        }else {
            iv_flag.setVisibility(View.GONE);
        }

        if(item.getFinishUrl()!=""||item.getFinishUrl()!=null) {
            Glide.with(mContext).load(item.getFinishUrl()).into(iv_finish);
            iv_finish.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
        }

        TextView tv_bg = helper.getView(R.id.tv_bg);
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_spec,"规格:"+item.getSpec());
        helper.setVisible(R.id.tv_stock,false);
        helper.setText(R.id.tv_given_num,"赠:"+item.getSendNumDesc());

        if(item.getAdditionFlag()==2) {
            iv_finish.setVisibility(View.VISIBLE);
            tv_bg.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
            tv_bg.setVisibility(View.GONE);
        }
    }
}
