package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

public class VpTeamAdapter extends RecyclerView.Adapter<VpTeamAdapter.BaseViewHolder> {
    Context mContext;
    int layoutResId;
    List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives;
    Onclick onclick;
    public VpTeamAdapter(Context context, int layoutResId, List<CouponModels.DataBean.TeamBean.ActivesBeanX> actives,Onclick onclick) {
        this.mContext = context;
        this.layoutResId = layoutResId;
        this.actives = actives;
        this.onclick = onclick;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        CouponModels.DataBean.TeamBean.ActivesBeanX activesBeanX = actives.get(position % actives.size());
        if(!TextUtils.isEmpty(activesBeanX.getSpread()) && !activesBeanX.getSpread().equals("")) {
            holder.tv_save_price.setText(activesBeanX.getSpread());
            holder.tv_save_price.setBackgroundResource(R.drawable.shape_yellow2);
        }else {
            holder.tv_save_price.setBackgroundResource(R.drawable.shape_white);
        }

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                holder.tv_price.setText(activesBeanX.getPrice());
            }else {
                holder.tv_price.setText("价格授权后可见");
            }
        }else {
            holder.tv_price.setText(activesBeanX.getMinMaxPrice());
        }

        holder.tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.tipClick();
                }
            }
        });

        holder.tv_title.setText(activesBeanX.getActiveName());
        Glide.with(mContext).load(activesBeanX.getDefaultPic()).into(holder.iv_pic);

        if(activesBeanX.getNotSend()==1) {
            holder.iv_not_send.setVisibility(View.VISIBLE);
        }else {
            holder.iv_not_send.setVisibility(View.GONE);
        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TeamDetailActivity.class);
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

    public interface Onclick {
        void tipClick();
    }
}
