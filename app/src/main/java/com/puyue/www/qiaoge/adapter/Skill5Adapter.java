package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/20
 */
public class Skill5Adapter extends RecyclerView.Adapter<Skill5Adapter.BaseViewHolder> {
    Context context;
    List<CouponModel.DataBean.ActivesBean> skillActive3;
    CouponModel.DataBean.ActivesBean activesBean;
    public Skill5Adapter(FragmentActivity mActivity, List<CouponModel.DataBean.ActivesBean> skillActive3) {
        this.context = mActivity;
        this.skillActive3 = skillActive3;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_skill_list4, viewGroup, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        try {
            int pos = position % skillActive3.size();
            activesBean = skillActive3.get(position % skillActive3.size());
            viewHolder.tv_name.setText(activesBean.getActiveName());
            viewHolder.tv_price.setText(activesBean.getPrice()+"00000");
            Glide.with(context).load(activesBean.getDefaultPic()).into(viewHolder.iv_pic);

            if(activesBean.getFlag()==1) {
                viewHolder.iv_sale_done.setVisibility(View.VISIBLE);
                Glide.with(context).load(activesBean.getSoldOutPic()).into(viewHolder.iv_sale_done);
            }else {
                viewHolder.iv_sale_done.setVisibility(View.GONE);
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
        public BaseViewHolder(View view) {
            super(view);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        }
    }
}
