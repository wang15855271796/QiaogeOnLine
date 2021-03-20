package com.puyue.www.qiaoge.fragment.market;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartsListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2019/11/7
 */
public class GivenAdapter extends BaseQuickAdapter<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList,BaseViewHolder> {

    public GivenAdapter(int layoutResId, @Nullable List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList> data) {
        super(layoutResId, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getPicUrl()).into(iv_icon);
        ImageView iv_finish = helper.getView(R.id.iv_finish);
        ImageView iv_flag = helper.getView(R.id.iv_flag);

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
//        Glide.with(mContext).load(item.getFlagUrl()).into(iv_flag);
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_spec,"规格:"+item.getSpec());
//        helper.setText(R.id.tv_stock,"库存"+item.getInventory());
        helper.setVisible(R.id.tv_stock,false);
        helper.setText(R.id.tv_given_num,"赠:"+item.getSendNumDesc());

        if(item.getAdditionFlag().equals("2")) {
            iv_finish.setVisibility(View.VISIBLE);
            tv_bg.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
            tv_bg.setVisibility(View.GONE);
        }
    }
}
