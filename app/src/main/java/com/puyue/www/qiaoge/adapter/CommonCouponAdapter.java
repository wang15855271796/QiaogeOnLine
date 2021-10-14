package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
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
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.CommonAdapter;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/3
 */
public class CommonCouponAdapter extends RecyclerView.Adapter<CommonCouponAdapter.BaseViewHolder> implements View.OnClickListener {
    private ImageView iv_pic;
    private ImageView iv_add;
    private RelativeLayout rl_group;
    //根据flag 判断返回集合大小还是最大值 0返回最大值 1，返回集合大小
    String flag;
    ImageView iv_flag;
    RelativeLayout rl_coupon;
    String style;
    Context mContext;
    public OnClick onClick;
    ImageView iv_sale_done;
    TextView tv_price;
    TextView tv_desc;
    int layoutResId;
    List<CouponModels.DataBean.SpecialBean.ActivesBeanX> actives;
    private int pos;
    private CouponModels.DataBean.SpecialBean.ActivesBeanX activesBean;

    public CommonCouponAdapter(Context context,String style, int layoutResId, List<CouponModels.DataBean.SpecialBean.ActivesBeanX> actives) {
        this.mContext = context;
        this.style = style;
        this.layoutResId = layoutResId;
        this.actives = actives;

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        try{
            pos = position%actives.size();
            activesBean = actives.get(position%actives.size());

            Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
            holder.tv_name.setText(activesBean.getActiveName());
            holder.tv_price.setText(activesBean.getPrice());


            holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
            if(!activesBean.getOldPrice().equals("")||activesBean.getOldPrice()!=null) {
                holder.tv_old_price.setVisibility(View.VISIBLE);
                holder.tv_old_price.setText(activesBean.getOldPrice());
            }else {
                holder.tv_old_price.setVisibility(View.VISIBLE);
            }

            if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                    holder.tv_desc.setVisibility(View.GONE);
                    holder.tv_price.setVisibility(View.VISIBLE);
                    holder.tv_old_price.setVisibility(View.VISIBLE);
                }else {
                    holder.tv_desc.setVisibility(View.VISIBLE);
                    holder.tv_price.setVisibility(View.GONE);
                    holder.tv_old_price.setVisibility(View.GONE);
                }
            }else {
                holder.tv_desc.setVisibility(View.GONE);
                holder.tv_price.setVisibility(View.VISIBLE);
            }

            if(activesBean.getDiscount()!=null||!activesBean.getDiscount().equals("")) {
                holder.tv_discount.setText(activesBean.getDiscount());
                holder.tv_discount.setVisibility(View.VISIBLE);
                holder.tv_discount.setBackgroundResource(R.drawable.shape_coupon_reds);

            }else {
                holder.tv_discount.setVisibility(View.GONE);
                holder.tv_discount.setBackground(null);
            }


            if(activesBean.getFlag()==1) {
                Glide.with(mContext).load(activesBean.getSoldOutPic()).into(holder.iv_sale_done);
                holder.iv_sale_done.setVisibility(View.VISIBLE);
            }else {
                holder.iv_sale_done.setVisibility(View.GONE);
            }

            if(activesBean.getNotSend()==1) {
                holder.iv_send.setVisibility(View.VISIBLE);
            }else {
                holder.iv_send.setVisibility(View.GONE);
            }


            holder.rl_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(style.equals("2")) {
                        Intent intent = new Intent(mContext,SeckillGoodActivity.class);
                        intent.putExtra(AppConstant.ACTIVEID, activesBean.getActiveId());
                        intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                        intent.putExtra("num","-1");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext.startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        intent.putExtra(AppConstant.ACTIVEID, activesBean.getActiveId());
                        intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                        mContext.startActivity(intent);
                    }
                }
            });

            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(onClick!=null) {
                        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                                onClick.shoppingCartOnClick(position%actives.size());
                            }else {
                                onClick.tipClick();
                            }
                        }else {
                            onClick.addDialog();
                        }
                    }
                }
            });

        }catch (Exception e) {

        }




    }

    @Override
    public int getItemCount() {
        return actives.size();
    }

    public void setOnclick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public void onClick(View v) {

    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_group;
        private RelativeLayout rl_coupon;
        private ImageView iv_add;
        private ImageView iv_pic;
        private TextView tv_price;
        private TextView tv_desc;
        private TextView tv_name;
        private ImageView iv_sale_done;
        private ImageView iv_flag;
        private ImageView iv_send;
        TextView tv_old_price;
        TextView tv_discount;
        private TextView tv_coupon;
        public BaseViewHolder(View view) {
            super(view);
            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            tv_discount = (TextView) view.findViewById(R.id.tv_discount);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
            iv_add = (ImageView) view.findViewById(R.id.iv_add);
            iv_send = (ImageView) view.findViewById(R.id.iv_send);
            iv_flag = (ImageView) view.findViewById(R.id.iv_flag);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
        }
    }

    public interface OnClick {
        void shoppingCartOnClick(int position);
        void tipClick();
        void addDialog();
    }

}
