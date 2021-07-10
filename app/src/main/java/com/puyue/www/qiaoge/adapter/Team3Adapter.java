package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.fragment.home.TestsAdapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/24
 */
public class Team3Adapter extends RecyclerView.Adapter<Team3Adapter.BaseViewHolder> {
//    private CountDownTimer countDownTimer1;
//    List<CouponModels.DataBean.TeamBean.ActivesBeanX> data;
//    public Team3Adapter(int layoutResId, @Nullable List<CouponModels.DataBean.TeamBean.ActivesBeanX> data) {
//        super(layoutResId, data);
//        this.data = data;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, CouponModels.DataBean.TeamBean.ActivesBeanX item) {
//        TextView tv_old_price = helper.getView(R.id.tv_old_price);
//        ImageView iv_pic = helper.getView(R.id.iv_pic);
//        Glide.with(mContext).load(data.get(0).getDefaultPic()).into(iv_pic);
//        TextView tv_desc = helper.getView(R.id.tv_desc);
//        RelativeLayout rl_group = helper.getView(R.id.rl_group);
//        rl_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent teamIntent = new Intent(mContext, TeamDetailActivity.class);
//                mContext.startActivity(teamIntent);
//            }
//        });
//
//        if(!TextUtils.isEmpty(item.getOldPrice())) {
//            tv_old_price.setText("原价:"+item.getOldPrice());
//            tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
//            tv_old_price.setVisibility(View.VISIBLE);
//        }else {
//            tv_old_price.setVisibility(View.GONE);
//        }
//
//        TextView tv_price = helper.getView(R.id.tv_price);
//        TextView tv_name = helper.getView(R.id.tv_name);
//        tv_name.setText(data.get(0).getActiveName());
//        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);
////        if(item.getFlag()==1) {
////            iv_sale_done.setVisibility(View.VISIBLE);
////            Glide.with(mContext).load(item.getSoldOutPic()).into(iv_sale_done);
////        }else {
////            iv_sale_done.setVisibility(View.GONE);
////        }
//
//        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                tv_price.setVisibility(View.VISIBLE);
//                tv_desc.setVisibility(View.GONE);
//                tv_price.setText(data.get(0).getPrice());
//                tv_old_price.setVisibility(View.VISIBLE);
//            }else {
//                tv_price.setVisibility(View.GONE);
//                tv_desc.setVisibility(View.VISIBLE);
//                tv_old_price.setVisibility(View.GONE);
//            }
//        }else {
//            tv_price.setText(item.getPrice());
//            tv_price.setVisibility(View.VISIBLE);
//            tv_desc.setVisibility(View.GONE);
//        }


//    }
//
//    public void cancle() {
////        if(countDownTimer1!=null) {
////            countDownTimer1.cancel();
////        }
//
////        if(countDownTimer2!=null) {
////            countDownTimer2.cancel();
////        }
////        if(countDownTimer3!=null) {
////            countDownTimer3.cancel();
////        }
////        if(countDownTimer4!=null) {
////            countDownTimer4.cancel();
////        }
////        if(countDownTimer5!=null) {
////            countDownTimer5.cancel();
////        }
//    }
//
//
//    public void start() {
////        if(countDownTimer1!=null) {
////            countDownTimer1.start();
////        }
//    }
//    //根据flag 判断返回集合大小还是最大值 0返回最大值 1，返回集合大小
    Activity mActivity;
    int layoutResId;
    List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives;
    CouponModels.DataBean.TeamBean.ActivesBeanX activesBean;
    public Team3Adapter(Activity mActivity, int layoutResId, List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives) {
        this.mActivity = mActivity;
        this.layoutResId = layoutResId;
        this.actives = actives;

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(layoutResId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        try {
            activesBean = actives.get(position%actives.size());
            holder.tv_name.setText(activesBean.getActiveName());
            Glide.with(mActivity).load(activesBean.getDefaultPic()).into(holder.iv_pic);
            holder.tv_price.setText(activesBean.getPrice());

            if(!TextUtils.isEmpty(activesBean.getOldPrice())) {
                holder.tv_old_price.setText("原价:"+activesBean.getOldPrice());
                holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
                holder.tv_old_price.setVisibility(View.VISIBLE);
            }else {
                holder.tv_old_price.setVisibility(View.GONE);
            }

            if(activesBean.getFlag()==1) {
                holder.iv_sale_done.setVisibility(View.VISIBLE);
                Glide.with(mActivity).load(activesBean.getSoldOutPic()).into(holder.iv_sale_done);
            }else {
                holder.iv_sale_done.setVisibility(View.GONE);
            }

            holder.rl_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent teamIntent = new Intent(mActivity, TeamDetailActivity.class);
                    mActivity.startActivity(teamIntent);
                }
            });

            if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                if(SharedPreferencesUtil.getString(mActivity,"priceType").equals("1")) {
                    holder.tv_price.setVisibility(View.VISIBLE);
                    holder.tv_desc.setVisibility(View.GONE);
                    holder.tv_price.setText(activesBean.getPrice());
                    holder.tv_old_price.setVisibility(View.VISIBLE);
                }else {
                    holder.tv_price.setVisibility(View.GONE);
                    holder.tv_desc.setVisibility(View.VISIBLE);
                    holder.tv_old_price.setVisibility(View.GONE);

                }
            }else {
                holder.tv_price.setText(activesBean.getPrice());
                holder.tv_price.setVisibility(View.VISIBLE);
                holder.tv_desc.setVisibility(View.GONE);
            }

        }catch (Exception e) {

        }



    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;

    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_group;
        private ImageView iv_pic;
        private TextView tv_price;
        private TextView tv_old_price;
        private TextView tv_desc;
        private TextView tv_name;
        private ImageView iv_sale_done;
        public BaseViewHolder(View view) {
            super(view);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
            iv_pic = (RoundImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
        }
    }


}
