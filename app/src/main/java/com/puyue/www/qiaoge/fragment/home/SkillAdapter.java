package com.puyue.www.qiaoge.fragment.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
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
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.CommonAdapter;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.MustDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

import static cn.com.chinatelecom.account.api.CtAuth.mContext;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.BaseViewHolder> implements View.OnClickListener {

    private ImageView iv_pic;
//    private ImageView iv_add;
    private RelativeLayout rl_group;
    String flag;
    ImageView iv_flag;
    private TextView tv_old_price;
    private TextView tv_coupon;
    RelativeLayout rl_coupon;
    TextView tv_desc;
    Context mContext;
    int layoutResId;
    int pos;
//    CouponModel.DataBean.ActivesBean activesBean;
    List<CouponModel.DataBean.ActivesBean> actives;
    public SkillAdapter(Context context,int layoutResId, List<CouponModel.DataBean.ActivesBean> actives, String flag) {
        this.mContext = context;
        this.layoutResId = layoutResId;
        this.actives = actives;
        this.flag = flag;
    }

    @NonNull
    @Override
    public SkillAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkillAdapter.BaseViewHolder holder, int position) {
//        try {
//            pos = position%actives.size();
//            activesBean = actives.get(position%actives.size());
//        }catch (Exception e) {
//
//        }

        CouponModel.DataBean.ActivesBean activesBean = actives.get(position);
        holder.tv_name.setText(activesBean.getActiveName());
        Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
        holder.tv_price.setText(activesBean.getPrice());
//        holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.tv_old_price.setText(activesBean.getOldPrice());
//        holder.tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                holder.tv_desc.setVisibility(View.GONE);
//                holder.tv_old_price.setVisibility(View.VISIBLE);
                holder.tv_price.setVisibility(View.VISIBLE);
            }else {
                holder.tv_desc.setVisibility(View.VISIBLE);
//                holder.tv_old_price.setVisibility(View.GONE);
                holder.tv_price.setVisibility(View.GONE);
            }
        }else {
            holder.tv_desc.setVisibility(View.GONE);
//            holder.tv_old_price.setVisibility(View.VISIBLE);
            holder.tv_price.setVisibility(View.VISIBLE);
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
//                            onClick.shoppingCartOnClick(position);
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

//    @Override
//    protected void convert(BaseViewHolder helper, CouponModel.DataBean.ActivesBean item) {
//
//        iv_pic = helper.getView(R.id.iv_pic);
//        tv_desc = helper.getView(R.id.tv_desc);
//        iv_sale_done = helper.getView(R.id.iv_sale_done);
//        iv_flag = helper.getView(R.id.iv_flag);
//        iv_add = helper.getView(R.id.iv_add);
//        tv_coupon = helper.getView(R.id.tv_coupon);
//        rl_coupon = helper.getView(R.id.rl_coupon);
//        rl_group = helper.getView(R.id.rl_group);
//        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
//        helper.setText(R.id.tv_name,item.getActiveName());
//        TextView tv_price = helper.getView(R.id.tv_price);
//        tv_old_price = helper.getView(R.id.tv_old_price);
//        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        helper.setText(R.id.tv_old_price,item.getOldPrice());
//        tv_price.setText(item.getPrice());
//        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
//        if(item.getDiscount()!=null) {
//            tv_coupon.setText(item.getDiscount());
//            rl_coupon.setVisibility(View.VISIBLE);
//        }else {
//            rl_coupon.setVisibility(View.GONE);
//        }
//
//        if(item.getFlag()==1) {
//            iv_sale_done.setVisibility(View.VISIBLE);
//            Glide.with(mContext).load(item.getSoldOutPic()).into(iv_sale_done);
//        }else {
//            iv_sale_done.setVisibility(View.GONE);
//        }
//
//        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                tv_desc.setVisibility(View.GONE);
//                tv_old_price.setVisibility(View.VISIBLE);
//                tv_price.setVisibility(View.VISIBLE);
//            }else {
//                tv_desc.setVisibility(View.VISIBLE);
//                tv_old_price.setVisibility(View.GONE);
//                tv_price.setVisibility(View.GONE);
//            }
//        }else {
//            tv_desc.setVisibility(View.GONE);
//            tv_old_price.setVisibility(View.VISIBLE);
//            tv_price.setVisibility(View.VISIBLE);
//        }
//
//        rl_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,SeckillGoodActivity.class);
//                intent.putExtra(AppConstant.ACTIVEID,item.getActiveId());
//                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
//                intent.putExtra("num","-1");
//                mContext.startActivity(intent);
//
//            }
//        });
//
//        iv_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onClick!=null) {
//                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//                        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                            onClick.shoppingCartOnClick(helper.getAdapterPosition());
//                        }else {
//                            onClick.tipClick();
//                        }
//                    }else {
//                        onClick.addDialog();
//                    }
//                }
//            }
//        });
//    }

    public interface OnClick {
        void shoppingCartOnClick(int position);
        void tipClick();
        void addDialog();
    }
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_group;
//        private ImageView iv_add;
        private ImageView iv_pic;
        private TextView tv_price;
//        private TextView tv_old_price;
        private TextView tv_desc;
        private TextView tv_name;
        private ImageView iv_sale_done;
        private ImageView iv_flag;
        public BaseViewHolder(View view) {
            super(view);
            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
//            iv_add = (ImageView) view.findViewById(R.id.iv_add);
            iv_flag = (ImageView) view.findViewById(R.id.iv_flag);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
//            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
        }
    }

}
