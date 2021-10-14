package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.FullGiftActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/24
 */
public class FullAdapter extends RecyclerView.Adapter<FullAdapter.BaseViewHolder> {

    List<CouponModels.DataBean.FullGiftBean.ActivesBeanXX> fullActive;
    Context mActivity;
    CouponModels.DataBean.FullGiftBean.ActivesBeanXX activesBean;
    int pos = 0;
    public FullAdapter(FragmentActivity mActivity, List<CouponModels.DataBean.FullGiftBean.ActivesBeanXX> fullActive) {
        this.mActivity = mActivity;
        this.fullActive = fullActive;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_full_lists, viewGroup, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        try {
            pos = position%fullActive.size();
            activesBean = fullActive.get(position % fullActive.size());
            viewHolder.tv_name.setText(activesBean.getProductName());
            Glide.with(mActivity).load(activesBean.getDefaultPic()).into(viewHolder.iv_pic);
            viewHolder.tv_price.setText(activesBean.getMinMaxPrice());


            if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                if(SharedPreferencesUtil.getString(mActivity,"priceType").equals("1")) {
                    viewHolder.tv_price.setVisibility(View.VISIBLE);
                    viewHolder.tv_desc.setVisibility(View.GONE);
                    viewHolder.tv_price.setText(activesBean.getMinMaxPrice());

                }else {
                    viewHolder.tv_price.setVisibility(View.GONE);
                    viewHolder.tv_desc.setVisibility(View.VISIBLE);
                }
            }else {
                viewHolder.tv_price.setText(activesBean.getMinMaxPrice());
                viewHolder.tv_price.setVisibility(View.VISIBLE);
                viewHolder.tv_desc.setVisibility(View.GONE);
            }

            viewHolder.rl_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mActivity,FullGiftActivity.class);
                    mActivity.startActivity(intent1);

                }
            });


            if(activesBean.getNotSend() ==1) {
                viewHolder.iv_send.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_send.setVisibility(View.GONE);
            }

            if(activesBean.getSendGiftType().equals("赠品")) {
                viewHolder.tv_coupon.setVisibility(View.GONE);
                viewHolder.tv_shop.setText("赠送商品");
                viewHolder.tv_shop.setVisibility(View.VISIBLE);
            }else if(activesBean.getSendGiftType().equals("送券")){
                viewHolder.tv_coupon.setVisibility(View.VISIBLE);
                viewHolder.tv_coupon.setText("赠优惠券");
                viewHolder.tv_shop.setVisibility(View.GONE);
            }else {
                viewHolder.tv_coupon.setVisibility(View.VISIBLE);
                viewHolder.tv_shop.setVisibility(View.VISIBLE);
                viewHolder.tv_shop.setText("赠送商品");
                viewHolder.tv_coupon.setText("赠优惠券");
            }
        }catch (Exception e) {
//
        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        RoundImageView iv_pic;
        TextView tv_name;
        TextView tv_price;
        TextView tv_coupon;
        RelativeLayout rl_group;
        TextView tv_desc;
        RelativeLayout rl_coupon;
        TextView tv_shop;
        ImageView iv_send;
        public BaseViewHolder(View view) {
            super(view);
            iv_send = view.findViewById(R.id.iv_send);
            tv_shop = view.findViewById(R.id.tv_shop);
            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
            tv_desc = view.findViewById(R.id.tv_desc);
            rl_group = view.findViewById(R.id.rl_group);
            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
            iv_pic = (RoundImageView) view.findViewById(R.id.iv_pic);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
        }
    }


}
