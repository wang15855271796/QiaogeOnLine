package com.puyue.www.qiaoge.adapter.coupon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.CouponSearchActivity;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.dialog.CouponProdDialog;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;
import com.puyue.www.qiaoge.utils.ToastUtil;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${daff} on 2018/9/20
 */
public class MyCouponsAdapter  extends  BaseQuickAdapter <queryUserDeductByStateModel.DataBean.ListBean,BaseViewHolder> {
    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    private ImageView iv_status;
    private  Context context;
    TextView tv_desc;
    List<queryUserDeductByStateModel.DataBean.ListBean> list;
    RelativeLayout rl_grey;
    TextView tv_tip;
    TextView tv_use;
    TextView tv_coupon_style;

    public MyCouponsAdapter(int layoutResId, @Nullable List<queryUserDeductByStateModel.DataBean.ListBean> data,Context context) {
        super(layoutResId, data);
        list=data;
        this.context=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, queryUserDeductByStateModel.DataBean.ListBean item) {
        tv_use = helper.getView(R.id.tv_use);
        tv_coupon_style = helper.getView(R.id.tv_coupon_style);
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_status=helper.getView(R.id.iv_status);
        rl_grey = helper.getView(R.id.rl_grey);

        if(!TextUtils.isEmpty(item.getGiftFlag())) {
            tv_coupon_style.setText(item.getGiftFlag());
            tv_coupon_style.setVisibility(View.VISIBLE);
        }else {
            tv_coupon_style.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(item.getLimitAmtStr())) {
            tv_user_factor.setText(item.getLimitAmtStr());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());
        tv_use.setVisibility(View.VISIBLE);
        tv_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("0")) {
                    //分类
                    context.startActivity(new Intent(context, HomeActivity.class));
                    EventBus.getDefault().post(new GoToMarketEvent());
                    EventBus.getDefault().unregister(this);
                }else {
                    //可用列表

                    if(item.getJumpFlag().equals("0")) {
                        //不可跳
                        ToastUtil.showSuccessMsg(mContext,"当前区域不可查看");
                    }else {
                        //可跳
                        Intent intent = new Intent(mContext,CouponSearchActivity.class);
                        intent.putExtra("giftDetailNo",item.getGiftDetailNo());
                        intent.putExtra("giftName",item.getGiftName());
                        mContext.startActivity(intent);
                    }

                }

            }
        });

        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());

        if (item.getRole().size()>0){
            tv_role.setText(item.getRole().get(0));
            tv_role.setVisibility(View.VISIBLE);

        }else {
            tv_role.setText("");
            tv_role.setVisibility(View.INVISIBLE);
        }

        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            iv_status.setVisibility(View.GONE);
            rl_grey.setVisibility(View.GONE);

            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));

        }else  if (item.getState().equals("USED")){//USED 已使用
            tv_desc.setText(item.getUseInfo());
            rl_grey.setVisibility(View.VISIBLE);
            iv_status.setVisibility(View.VISIBLE);
            iv_status.setImageResource(R.mipmap.ic_user);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));

        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            iv_status.setVisibility(View.VISIBLE);
            rl_grey.setVisibility(View.GONE);
            iv_status.setImageResource(R.mipmap.ic_overdue);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
        }
    }

}
