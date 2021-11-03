package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by ${王涛} on 2021/1/20
 */
public class Skill5Adapter extends RecyclerView.Adapter<Skill5Adapter.BaseViewHolder> {
    Context context;
    List<CouponModels.DataBean.SpikeBean.ActivesBean> skillActive3;
    CouponModels.DataBean.SpikeBean.ActivesBean activesBean;
    public Skill5Adapter(FragmentActivity mActivity, List<CouponModels.DataBean.SpikeBean.ActivesBean> skillActive3) {
        this.context = mActivity;
        this.skillActive3 = skillActive3;
    }

    //item_skill_list_4
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_skill_lists, viewGroup, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        try {
            int pos = position % skillActive3.size();
            activesBean = skillActive3.get(position % skillActive3.size());
            viewHolder.tv_name.setText(activesBean.getActiveName());
            Glide.with(context).load(activesBean.getDefaultPic()).into(viewHolder.iv_pic);
            if(!TextUtils.isEmpty(activesBean.getOldPrice())) {
                viewHolder.tv_old_price.setText(activesBean.getOldPrice());
                viewHolder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
                viewHolder.tv_old_price.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_old_price.setVisibility(View.GONE);
            }

            if(activesBean.getFlag()==1) {
                viewHolder.iv_sale_done.setVisibility(View.VISIBLE);
                Glide.with(context).load(activesBean.getSoldOutPic()).into(viewHolder.iv_sale_done);
            }else {
                viewHolder.iv_sale_done.setVisibility(View.GONE);
            }

            if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(context))) {
                if(SharedPreferencesUtil.getString(context,"priceType").equals("1")) {
                    viewHolder.tv_price.setVisibility(View.VISIBLE);
                    viewHolder.tv_desc.setVisibility(View.GONE);
                    viewHolder.tv_old_price.setVisibility(View.VISIBLE);
                    viewHolder.tv_price.setText(activesBean.getPrice());

                }else {
                    viewHolder.tv_price.setVisibility(View.GONE);
                    viewHolder.tv_old_price.setVisibility(View.GONE);
                    viewHolder.tv_desc.setVisibility(View.VISIBLE);
                }
            }else {
                viewHolder.tv_price.setText(activesBean.getPrice());
                viewHolder.tv_price.setVisibility(View.VISIBLE);
                viewHolder.tv_desc.setVisibility(View.GONE);
            }

            viewHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,SeckillGoodActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID,skillActive3.get(pos).getActiveId());
                    intent.putExtra("priceType",SharedPreferencesUtil.getString(context,"priceType"));
                    intent.putExtra("num","-1");
                    context.startActivity(intent);
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
        ImageView iv_pic;
        TextView tv_name;
        TextView tv_price;
        LinearLayout ll_root;
        ImageView iv_sale_done;
        TextView tv_desc;
        TextView tv_old_price;
        public BaseViewHolder(View view) {
            super(view);
            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        }
    }
}
