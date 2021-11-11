package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/19
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.BaseViewHolder> {

    Activity mContext;
    int layoutResId;
    List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives;
    CouponModels.DataBean.TeamBean.ActivesBeanX activesBean;
    public TeamAdapter(Activity context, int layoutResId, List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives) {
        this.mContext = context;
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
        try {
            activesBean = actives.get(position%actives.size());
            Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
            holder.tv_name.setText(activesBean.getActiveName());
            holder.tv_price.setText(activesBean.getPrice());

            if(activesBean.getNotSend()==1) {
                holder.iv_send.setVisibility(View.GONE);
            }else {
                holder.iv_send.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(activesBean.getOldPrice())) {
                holder.tv_old_price.setText(activesBean.getOldPrice());
                holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
                holder.tv_old_price.setVisibility(View.VISIBLE);
            }else {
                holder.tv_old_price.setVisibility(View.GONE);
            }

            if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                    holder.tv_price.setVisibility(View.VISIBLE);
                    holder.tv_desc.setVisibility(View.GONE);
                    holder.tv_old_price.setVisibility(View.VISIBLE);
                    holder.tv_price.setText(activesBean.getPrice());
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

            if(activesBean.getFlag()==1) {
                Glide.with(mContext).load(activesBean.getSoldOutPic()).into(holder.iv_sale_done);
                holder.iv_sale_done.setVisibility(View.VISIBLE);
            }else {
                holder.iv_sale_done.setVisibility(View.GONE);
            }

            holder.rl_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent teamIntent = new Intent(mContext, TeamDetailActivity.class);
                    mContext.startActivity(teamIntent);
                }
            });
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
        private ImageView iv_send;
        public BaseViewHolder(View view) {
            super(view);
            iv_send = (ImageView) view.findViewById(R.id.iv_send);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
        }
    }


}
