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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;


import java.util.List;

/**
 * Created by ${王涛} on 2020/8/29(满赠)
 */
public class CommonssAdapter extends  RecyclerView.Adapter<CommonssAdapter.BaseViewHolder> {
    private CountDownTimer countDownTimer1;

    List<CouponModel.DataBean.ActivesBean> fullActive;
    Context mActivity;
    CouponModel.DataBean.ActivesBean activesBean;
    public CommonssAdapter(FragmentActivity mActivity, List<CouponModel.DataBean.ActivesBean> fullActive) {
        this.mActivity = mActivity;
        this.fullActive = fullActive;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_full_list, viewGroup, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        try {
            activesBean = fullActive.get(position % fullActive.size());
            viewHolder.tv_name.setText(activesBean.getProductName());
            viewHolder.tv_price.setText(activesBean.getMinMaxPrice());
            Glide.with(mActivity).load(activesBean.getDefaultPic()).into(viewHolder.iv_pic);


            viewHolder.rl_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity,CommonGoodsDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, activesBean.getProductMainId());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity,"priceType"));
                    mActivity.startActivity(intent);

                }
            });
            if(activesBean.getSendGiftType().equals("赠礼")) {
                viewHolder.rl_given.setVisibility(View.VISIBLE);
                viewHolder.rl_coupon.setVisibility(View.GONE);
                Glide.with(mActivity).load(activesBean.getSendGiftPic()).into(viewHolder.iv_given);
                viewHolder.tv_descs.setText("满赠商品");
            }else {
                viewHolder.rl_given.setVisibility(View.GONE);
                viewHolder.tv_descs.setText("满赠优惠券");
                viewHolder.rl_coupon.setVisibility(View.VISIBLE);
                viewHolder.tv_fit.setText(activesBean.getRoleAmount());
                viewHolder.tv_coupon.setText(activesBean.getSendGiftInfo());
            }


//            if(countDownTimer1 == null) {
//                countDownTimer1 = new CountDownTimer(5000,4000) {
//                    int i = 0;
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        try {
//                            Glide.with(mActivity).load(fullActive.get(i).getDefaultPic()).into(viewHolder.iv_pic);
//                            viewHolder.tv_price.setText(fullActive.get(i).getPrice());
//                            viewHolder.tv_name.setText(fullActive.get(i).getProductName());
//                            if(fullActive.get(i).getSendGiftType().equals("赠礼")) {
//                                viewHolder.tv_descs.setText("满赠商品");
//                                viewHolder.rl_given.setVisibility(View.VISIBLE);
//                                viewHolder.rl_coupon.setVisibility(View.GONE);
//                                Glide.with(mActivity).load(fullActive.get(i).getDefaultPic()).into(viewHolder.iv_given);
//
//                            }else {
//                                viewHolder.rl_given.setVisibility(View.GONE);
//                                viewHolder.tv_descs.setText("满赠优惠券");
//                                viewHolder.rl_coupon.setVisibility(View.VISIBLE);
//                                viewHolder.tv_fit.setText(fullActive.get(i).getRoleAmount());
//                                viewHolder.tv_coupon.setText(fullActive.get(i).getSendGiftInfo());
//                            }
//                            i++;
//                            if(i==fullActive.size()) {
//                                i = 0;
//                            }
//                        }catch (Exception e) {
//
//                        }
//                        start();
//                    }
//                }.start();
//            }
        }catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
//
//    public void cancle() {
//        if (countDownTimer1 != null) {
//            countDownTimer1.cancel();
//        }
//    }
//
//    public void start() {
//        if(countDownTimer1!=null) {
//            countDownTimer1.start();
//        }
//    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        RoundImageView iv_pic;
        TextView tv_name;
        TextView tv_price;
        TextView tv_coupon;
        TextView tv_fit;
        RoundImageView iv_given;
        RelativeLayout rl_given;
        RelativeLayout rl_coupon;
        TextView tv_descs;
        RelativeLayout rl_group;
        public BaseViewHolder(View view) {
            super(view);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
            tv_descs = (TextView) view.findViewById(R.id.tv_descs);
            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
            rl_given = (RelativeLayout) view.findViewById(R.id.rl_given);
            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
            tv_fit = (TextView) view.findViewById(R.id.tv_fit);
            iv_given = (RoundImageView) view.findViewById(R.id.iv_given);
            iv_pic = (RoundImageView) view.findViewById(R.id.iv_pic);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
        }
    }
}
