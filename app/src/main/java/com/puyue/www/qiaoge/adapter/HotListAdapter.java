package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.dialog.HotDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/26
 */
public class HotListAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {

    ImageView iv_style;
    HotDialog hotDialog;
    List<ProductNormalModel.DataBean.ListBean> data;
    Onclick onclick;
    public HotListAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> data,Onclick onclick) {
        super(layoutResId, data);
        this.data = data;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        ImageView iv_send = helper.getView(R.id.iv_send);
        ImageView iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        ImageView iv_type = helper.getView(R.id.iv_type);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_add = helper.getView(R.id.tv_add);
        iv_style = helper.getView(R.id.iv_style);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_stock_total = helper.getView(R.id.tv_stock_total);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_sale = helper.getView(R.id.tv_sale);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_desc = helper.getView(R.id.tv_desc);
//        tv_stock_total.setText(item.getInventory());
        tv_name.setText(item.getProductName());
//        tv_sale.setText(item.getSalesVolume());
        tv_spec.setText("规格:"+item.getSpec());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_style);

        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.ic_refresh_price);
        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setVisibility(View.VISIBLE);
                tv_desc.setVisibility(View.GONE);
                tv_price.setText(item.getMinMaxPrice());
            }else {
                tv_price.setVisibility(View.GONE);
                tv_desc.setVisibility(View.VISIBLE);
            }
        }else {
            tv_price.setText(item.getMinMaxPrice());
            tv_price.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }

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


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                        hotDialog = new HotDialog(mContext,item.getProductId(),item);
                        hotDialog.show();
                    }else {
                        onclick.addDialog();
                    }
                }else {
                    //未登录
                    onclick.login();
                }
            }
        });
    }
    public interface Onclick {
        void addDialog();
        void login();
    }
}
