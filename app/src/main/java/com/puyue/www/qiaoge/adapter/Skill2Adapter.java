package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/3
 */
public class Skill2Adapter extends RecyclerView.Adapter<Skill2Adapter.BaseViewHolder> implements View.OnClickListener {
    private ImageView iv_pic;
//    private ImageView iv_add;
    private RelativeLayout rl_group;
    String flag;
    ImageView iv_flag;
//    private TextView tv_old_price;
    private TextView tv_coupon;
//    RelativeLayout rl_coupon;
    public OnClick onClick;
    TextView tv_desc;
    Context mContext;
    int layoutResId;
    int pos;
    CouponModels.DataBean.SpikeBean.ActivesBean activesBean;
    List<CouponModels.DataBean.SpikeBean.ActivesBean> actives;
    public Skill2Adapter(Context context, int layoutResId, List<CouponModels.DataBean.SpikeBean.ActivesBean> actives, String flag) {
        this.mContext = context;
        this.layoutResId = layoutResId;
        this.actives = actives;
        this.flag = flag;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        try {
            pos = position%actives.size();
            activesBean = actives.get(position%actives.size());
        }catch (Exception e) {

        }

        holder.tv_name.setText(activesBean.getActiveName());
        Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
        holder.tv_price.setText(activesBean.getPrice());
        holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_old_price.setText(activesBean.getOldPrice());
        holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                holder.tv_desc.setVisibility(View.GONE);
                holder.tv_old_price.setVisibility(View.VISIBLE);
                holder.tv_price.setVisibility(View.VISIBLE);
            }else {
                holder.tv_desc.setVisibility(View.VISIBLE);
                holder.tv_old_price.setVisibility(View.GONE);
                holder.tv_price.setVisibility(View.GONE);
            }
        }else {
            holder.tv_desc.setVisibility(View.GONE);
//            holder.tv_old_price.setVisibility(View.VISIBLE);
            holder.tv_price.setVisibility(View.VISIBLE);
        }

        if(activesBean.getDiscount()!=null) {
            holder.tv_coupon.setText(activesBean.getDiscount());
//            holder.rl_coupon.setVisibility(View.VISIBLE);
        }else {
//            holder.rl_coupon.setVisibility(View.GONE);
        }

        if(activesBean.getNotSend()==1) {
            holder.iv_not_send.setVisibility(View.VISIBLE);
        }else {
            holder.iv_not_send.setVisibility(View.GONE);
        }

        if(activesBean.getFlag()==1) {
            holder.iv_sale_done.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(activesBean.getSoldOutPic()).into(holder.iv_sale_done);
        }else {
            holder.iv_sale_done.setVisibility(View.GONE);
        }

        holder.rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,SeckillGoodActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,activesBean.getActiveId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                intent.putExtra("num","-1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);

//                    Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//                    intent.putExtra(AppConstant.ACTIVEID,activesBean.getActiveId());
//                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
//                    mContext.startActivity(intent);

            }
        });

//        holder.iv_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onClick!=null) {
//                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//                        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                            onClick.shoppingCartOnClick(pos);
//                        }else {
//                            onClick.tipClick();
//                        }
//                    }else {
//                        onClick.addDialog();
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(flag.equals("0")) {
            return Integer.MAX_VALUE;
        }else {
            return actives.size();
        }
    }

    @Override
    public void onClick(View v) {

    }


    public interface OnClick {
        void shoppingCartOnClick(int position);
        void tipClick();
        void addDialog();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_group;
        private ImageView iv_not_send;
        private ImageView iv_pic;
        private TextView tv_price;
        private TextView tv_old_price;
        private TextView tv_desc;
        private TextView tv_name;
        private ImageView iv_sale_done;
        private ImageView iv_flag;
        private TextView tv_coupon;
        public BaseViewHolder(View view) {
            super(view);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
//            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
            iv_not_send = (ImageView) view.findViewById(R.id.iv_not_send);
            iv_flag = (ImageView) view.findViewById(R.id.iv_flag);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
        }
    }
}
