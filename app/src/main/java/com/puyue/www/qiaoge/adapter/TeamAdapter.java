package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/19
 */
public class TeamAdapter extends BaseQuickAdapter<CouponModel.DataBean.ActivesBean,BaseViewHolder> {
    private CountDownTimer countDownTimer1;
    List<CouponModel.DataBean.ActivesBean> data;
    public TeamAdapter(int layoutResId, @Nullable List<CouponModel.DataBean.ActivesBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.DataBean.ActivesBean item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RelativeLayout rl_group = helper.getView(R.id.rl_group);
        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getActiveId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });
        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(data.get(0).getActiveName());
        Glide.with(mContext).load(data.get(0).getDefaultPic()).into(iv_pic);
        tv_price.setText(item.getPrice());
        if(item.getFlag()==1) {
            iv_sale_done.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getSoldOutPic()).into(iv_sale_done);
        }else {
            iv_sale_done.setVisibility(View.GONE);
        }

        if(countDownTimer1 == null) {
            countDownTimer1 = new CountDownTimer(5000,1000) {
                int i = 0;
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    try {
                        Glide.with(mContext).load(data.get(i).getDefaultPic()).into(iv_pic);
                        tv_name.setText(data.get(i).getActiveName());
                        tv_price.setText(data.get(i).getPrice());
                        i++;
                        if(i==data.size()) {
                            i = 0;
                        }
                    }catch (Exception e) {

                    }
                    start();
                }
            }.start();
        }
    }

    public void cancle() {
        if(countDownTimer1!=null) {
            countDownTimer1.cancel();
        }

//        if(countDownTimer2!=null) {
//            countDownTimer2.cancel();
//        }
//        if(countDownTimer3!=null) {
//            countDownTimer3.cancel();
//        }
//        if(countDownTimer4!=null) {
//            countDownTimer4.cancel();
//        }
//        if(countDownTimer5!=null) {
//            countDownTimer5.cancel();
//        }
    }


    public void start() {
        if(countDownTimer1!=null) {
            countDownTimer1.start();
        }

//        if(countDownTimer2!=null) {
//            countDownTimer2.start();
//        }
//        if(countDownTimer3!=null) {
//            countDownTimer3.start();
//        }
//        if(countDownTimer4!=null) {
//            countDownTimer4.start();
//        }
//        if(countDownTimer5!=null) {
//            countDownTimer5.start();
//        }
    }

//    //根据flag 判断返回集合大小还是最大值 0返回最大值 1，返回集合大小
//    String flag;
//    Context mContext;
//    int layoutResId;
//    List<CouponModel.DataBean.ActivesBean> actives;
//
//    public TeamAdapter(Context context, int layoutResId, List<CouponModel.DataBean.ActivesBean> actives) {
//        this.mContext = context;
//        this.layoutResId = layoutResId;
//        this.actives = actives;
//    }
//
//
//    @NonNull
//    @Override
//    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
//        BaseViewHolder holder = new BaseViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//        CouponModel.DataBean.ActivesBean activesBean = actives.get(position);
//        Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
//        holder.tv_name.setText(activesBean.getActiveName());
//        holder.tv_price.setText(activesBean.getPrice());
//        holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.tv_old_price.setText(activesBean.getOldPrice());
//        holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
//
//        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                holder.tv_desc.setVisibility(View.GONE);
//                holder.tv_old_price.setVisibility(View.VISIBLE);
//                holder.tv_price.setVisibility(View.VISIBLE);
//            }else {
//                holder.tv_desc.setVisibility(View.VISIBLE);
//                holder.tv_old_price.setVisibility(View.GONE);
//                holder.tv_price.setVisibility(View.GONE);
//            }
//        }else {
//            holder.tv_desc.setVisibility(View.GONE);
//            holder.tv_old_price.setVisibility(View.VISIBLE);
//            holder.tv_price.setVisibility(View.VISIBLE);
//        }
//
//        if(activesBean.getDiscount()!=null) {
//            holder.tv_coupon.setText(activesBean.getDiscount());
//            holder.rl_coupon.setVisibility(View.VISIBLE);
//        }else {
//            holder.rl_coupon.setVisibility(View.GONE);
//        }
//
//        if(activesBean.getFlag()==1) {
//            Glide.with(mContext).load(activesBean.getSoldOutPic()).into(holder.iv_sale_done);
//            holder.iv_sale_done.setVisibility(View.VISIBLE);
//        }else {
//            holder.iv_sale_done.setVisibility(View.GONE);
//        }
//
////        holder.rl_group.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(style.equals("2")) {
////                    Intent intent = new Intent(mContext,SeckillGoodActivity.class);
////                    intent.putExtra(AppConstant.ACTIVEID, activesBean.getActiveId());
////                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
////                    intent.putExtra("num","-1");
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
////                    mContext.startActivity(intent);
////                }else {
////                    Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
////                    intent.putExtra(AppConstant.ACTIVEID, activesBean.getActiveId());
////                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
////                    mContext.startActivity(intent);
////                }
////            }
////        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return Integer.MAX_VALUE;
//    }
//
//
//
//
//    public class BaseViewHolder extends RecyclerView.ViewHolder {
//        private RelativeLayout rl_group;
//        private RelativeLayout rl_coupon;
//        private ImageView iv_add;
//        private ImageView iv_pic;
//        private TextView tv_price;
//        private TextView tv_old_price;
//        private TextView tv_desc;
//        private TextView tv_name;
//        private ImageView iv_sale_done;
//        private ImageView iv_flag;
//        private TextView tv_coupon;
//        public BaseViewHolder(View view) {
//            super(view);
//            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
//            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
//            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
//            iv_add = (ImageView) view.findViewById(R.id.iv_add);
//            iv_flag = (ImageView) view.findViewById(R.id.iv_flag);
//            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
//            tv_price = (TextView) view.findViewById(R.id.tv_price);
//            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
//            tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
//            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
//        }
//    }


}
