package com.puyue.www.qiaoge.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.view.RoundImageView;

import java.util.List;

/**
 * Created by ${王涛} on 2019/11/5
 */
public class CommonAdapter extends BaseQuickAdapter<CouponModels.DataBean.SpecialBean.ActivesBeanX,BaseViewHolder> {
    List<CouponModels.DataBean.SpecialBean.ActivesBeanX> data;
    public CommonAdapter(int layoutResId, @Nullable List<CouponModels.DataBean.SpecialBean.ActivesBeanX> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModels.DataBean.SpecialBean.ActivesBeanX item) {
        LinearLayout rl_more = helper.getView(R.id.ll_more);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        RelativeLayout rl_group = helper.getView(R.id.rl_group);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        TextView tv_name = helper.getView(R.id.tv_name);
        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);
        TextView tv_discount = helper.getView(R.id.tv_discount);
        tv_name.setText(item.getActiveName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        if(item.getDiscount()==null||item.getDiscount().equals("")) {
            tv_discount.setVisibility(View.GONE);
            tv_discount.setBackground(null);
        }else {
            tv_discount.setText(item.getDiscount());
            tv_discount.setVisibility(View.VISIBLE);
            tv_discount.setBackgroundResource(R.drawable.shape_coupon_reds);
        }

        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        if(!item.getOldPrice().equals("")||item.getOldPrice()!=null) {
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
        }else {
            tv_old_price.setVisibility(View.GONE);
        }

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setVisibility(View.VISIBLE);
                tv_desc.setVisibility(View.GONE);
                tv_old_price.setVisibility(View.VISIBLE);
                tv_price.setText(item.getPrice());
            }else {
                tv_price.setVisibility(View.GONE);
                tv_desc.setVisibility(View.VISIBLE);
                tv_old_price.setVisibility(View.GONE);
            }
        }else {
            tv_price.setText(item.getPrice());
            tv_price.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }


        if(item.getFlag()==1) {
            Glide.with(mContext).load(item.getSoldOutPic()).into(iv_sale_done);
            iv_sale_done.setVisibility(View.VISIBLE);
        }else {
            iv_sale_done.setVisibility(View.GONE);
        }

        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra(AppConstant.ACTIVEID, item.getActiveId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });

        rl_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(mContext,CouponDetailActivity.class);
                mContext.startActivity(intent5);
            }
        });
        if(data.size()>=3) {
            if(helper.getAdapterPosition()==data.size()-1) {
                rl_more.setVisibility(View.VISIBLE);
            }else {
                rl_more.setVisibility(View.GONE);
            }
        }else {
            rl_more.setVisibility(View.GONE);
        }

    }
}
