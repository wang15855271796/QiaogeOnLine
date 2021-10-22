package com.puyue.www.qiaoge.activity.home;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.TeamActiveQueryModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/1/17
 */
public class Coupon1InnerAdapter extends BaseQuickAdapter<TeamActiveQueryModel.DataBean.ActivesBean,BaseViewHolder> {

    Coupon1Adapter.Onclick onclick;
    private ImageView iv_pic;
    TextView tv_old_price;
    private TeamActiveQueryModel.DataBean.ActivesBean activesBean;
    private TextView tv_total;
    ProgressBar pb;
    private TextView tv_add;
    private RelativeLayout rl_root;
    private RelativeLayout rl_coupon;
    private int activeId;
    List<TeamActiveQueryModel.DataBean>  data;
    private TeamActiveQueryModel.DataBean dataBean;
    private List<TeamActiveQueryModel.DataBean.ActivesBean> actives;
    private int activeId1;
    private TextView tv_coupon;
    private TextView tv_price;
    RelativeLayout rl_price;
    public Coupon1InnerAdapter(int layoutResId, @Nullable List<TeamActiveQueryModel.DataBean.ActivesBean> data, Coupon1Adapter.Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamActiveQueryModel.DataBean.ActivesBean item) {
        ImageView iv_send = helper.getView(R.id.iv_send);
        rl_price = helper.getView(R.id.rl_price);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_coupon = helper.getView(R.id.tv_coupon);
        iv_pic = helper.getView(R.id.iv_pic);
        rl_root = helper.getView(R.id.rl_root);
        tv_add = helper.getView(R.id.tv_add);
        tv_total = helper.getView(R.id.tv_total);
        pb = helper.getView(R.id.pb);
        rl_coupon = helper.getView(R.id.rl_coupon);

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getActiveName());
        helper.setText(R.id.tv_spec,item.getSpec());
//        helper.setText(R.id.tv_price,item.getPrice());
        tv_price = helper.getView(R.id.tv_price);
//        helper.setText(R.id.tv_old_price,item.getOldPrice());
        pb.setProgress(Integer.parseInt(item.getProgress()));
        tv_total.setText(item.getRemainNum());
        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if (SharedPreferencesUtil.getString(mContext, "priceType").equals("1")) {
                rl_price.setVisibility(View.GONE);
                tv_add.setVisibility(View.VISIBLE);
                tv_add.setText("  未开始  ");
                tv_price.setVisibility(View.VISIBLE);
                tv_old_price.setVisibility(View.VISIBLE);
                tv_old_price.setText(item.getOldPrice());
                tv_price.setText(item.getPrice());
            } else {
                rl_price.setVisibility(View.VISIBLE);
                tv_add.setVisibility(View.GONE);
                tv_old_price.setVisibility(View.INVISIBLE);
                tv_price.setText("价格授权后可见");
                tv_add.setEnabled(false);
            }

            tv_add.setEnabled(true);
        }else {
            tv_add.setVisibility(View.VISIBLE);
            tv_price.setText(item.getPrice());
            tv_add.setText("未开始");
            tv_add.setEnabled(false);
            tv_add.setBackgroundResource(R.drawable.shape_orange);
        }

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(onclick!=null) {
                        onclick.addDialog();
                    }
                }else {

                }
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    onclick.addDialog();
                } else {
                    AppHelper.showMsg(mContext, "请先登录");
                    mContext.startActivity(LoginActivity.getIntent(mContext, LoginActivity.class));
                }
            }
        });


        rl_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.addDialog();
                }
            }
        });

        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                intent.putExtra(AppConstant.ACTIVEID,item.getActiveId());
                mContext.startActivity(intent);
            }
        });

        if(!item.getDiscount().equals("")) {
            helper.setText(R.id.tv_coupon,item.getDiscount());
            rl_coupon.setVisibility(View.VISIBLE);
        }else {
            rl_coupon.setVisibility(View.GONE);
        }


        tv_add.setBackgroundResource(R.drawable.shape_detail_grey);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

    }
}
