package com.puyue.www.qiaoge.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.FullActiveActivity;
import com.puyue.www.qiaoge.activity.FullListActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

public class VpFullAdapter extends RecyclerView.Adapter<VpFullAdapter.BaseViewHolder> {

    Context mContext;
    int layoutResId;
    List<CouponModels.DataBean.FullGiftBean.ActivesBeanXX> actives;
    public VpFullAdapter(Context context, int layoutResId, List<CouponModels.DataBean.FullGiftBean.ActivesBeanXX> actives) {
        this.mContext = context;
        this.layoutResId = layoutResId;
        this.actives = actives;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        CouponModels.DataBean.FullGiftBean.ActivesBeanXX activesBeanXX = actives.get(position % actives.size());
        if(!TextUtils.isEmpty(activesBeanXX.getSendInfo()) && !activesBeanXX.getSendInfo().equals("")) {
            holder.tv_save_price.setText(activesBeanXX.getSendInfo());
            holder.tv_save_price.setBackgroundResource(R.drawable.shape_yellow2);
        }else {
            holder.tv_save_price.setBackgroundResource(R.drawable.shape_white);
        }

        holder.tv_price.setText(activesBeanXX.getMinMaxPrice());
        holder.tv_title.setText(activesBeanXX.getProductName());
        Glide.with(mContext).load(activesBeanXX.getDefaultPic()).into(holder.iv_pic);

        if(activesBeanXX.getNotSend()==1) {
            holder.iv_not_send.setVisibility(View.VISIBLE);
        }else {
            holder.iv_not_send.setVisibility(View.GONE);
        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FullListActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;

    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_save_price;
        TextView tv_price;
        RoundImageView iv_pic;
        ImageView iv_not_send;
        LinearLayout ll_root;
        public BaseViewHolder(View view) {
            super(view);
            ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
            iv_not_send = (ImageView) view.findViewById(R.id.iv_not_send);
            iv_pic = (RoundImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_save_price = (TextView) view.findViewById(R.id.tv_save_price);

        }
    }

}
