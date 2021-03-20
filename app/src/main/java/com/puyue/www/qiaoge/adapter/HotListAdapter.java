package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.dialog.HotDialog;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/26
 */
public class HotListAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {

    ImageView iv_style;
    HotDialog hotDialog;
    List<ProductNormalModel.DataBean.ListBean> data;
    public HotListAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);
        ImageView iv_type = helper.getView(R.id.iv_type);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_add = helper.getView(R.id.iv_add);
        iv_style = helper.getView(R.id.iv_style);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_stock_total = helper.getView(R.id.tv_stock_total);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_sale = helper.getView(R.id.tv_sale);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        tv_price.setText(item.getMinMaxPrice());
        tv_stock_total.setText(item.getInventory());
        tv_name.setText(item.getProductName());
        tv_sale.setText(item.getSalesVolume());
        tv_spec.setText("规格:"+item.getSpec());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_style);

//        if(data.getFlag()==1) {
//            Glide.with(mContext).load(data.getSoldOutPic()).into(iv_sale_done);
//            iv_sale_done.setVisibility(View.VISIBLE);
//        }else {
//            iv_sale_done.setVisibility(View.GONE);
//        }


        if(data.size()>=3) {
            if(helper.getAdapterPosition()==0) {
                iv_type.setVisibility(View.VISIBLE);
                iv_type.setImageResource(R.mipmap.icon_one);
            }else if(helper.getAdapterPosition()==1) {
                iv_type.setVisibility(View.VISIBLE);
                iv_type.setImageResource(R.mipmap.icon_two);
            }else if(helper.getAdapterPosition()==2){
                iv_type.setVisibility(View.VISIBLE);
                iv_type.setImageResource(R.mipmap.icon_three);
            }else {
                iv_type.setVisibility(View.GONE);
            }

        }else {
            iv_type.setVisibility(View.GONE);
        }


        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotDialog = new HotDialog(mContext,item.getProductId(),item);
                hotDialog.show();
            }
        });
    }
}
