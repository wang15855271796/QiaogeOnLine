package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.fragment.home.TestsAdapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/24
 */
public class Team3Adapter extends BaseQuickAdapter<CouponModel.DataBean.ActivesBean,BaseViewHolder> {
    private CountDownTimer countDownTimer1;
    List<CouponModel.DataBean.ActivesBean> data;
    public Team3Adapter(int layoutResId, @Nullable List<CouponModel.DataBean.ActivesBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.DataBean.ActivesBean item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(data.get(0).getDefaultPic()).into(iv_pic);

        RelativeLayout rl_group = helper.getView(R.id.rl_group);
        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getActiveId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(data.get(0).getActiveName());
        tv_price.setText(data.get(0).getPrice());
        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);
        if(item.getFlag()==1) {
            iv_sale_done.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getSoldOutPic()).into(iv_sale_done);
        }else {
            iv_sale_done.setVisibility(View.GONE);
        }

        if(countDownTimer1 == null) {
            countDownTimer1 = new CountDownTimer(5000,1000) {
                int i = 0;
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    try {
                        Glide.with(mContext).load(data.get(i).getDefaultPic()).into(iv_pic);
                        tv_price.setText(data.get(i).getPrice());
                        tv_name.setText(data.get(i).getActiveName());
                        tv_price.setText(data.get(i).getPrice());
                        i++;
                        if(i==data.size()) {
                            i = 0;
                        }
                    }catch (Exception e) {

                    }
                    start();
                }
            }.start();
        }
    }

    public void cancle() {
        if(countDownTimer1!=null) {
            countDownTimer1.cancel();
        }

//        if(countDownTimer2!=null) {
//            countDownTimer2.cancel();
//        }
//        if(countDownTimer3!=null) {
//            countDownTimer3.cancel();
//        }
//        if(countDownTimer4!=null) {
//            countDownTimer4.cancel();
//        }
//        if(countDownTimer5!=null) {
//            countDownTimer5.cancel();
//        }
    }


    public void start() {
        if(countDownTimer1!=null) {
            countDownTimer1.start();
        }
    }
//    //根据flag 判断返回集合大小还是最大值 0返回最大值 1，返回集合大小
//    Context mContext;
//    int layoutResId;
//    List<CouponModel.DataBean.ActivesBean> actives;
//    CouponModel.DataBean.ActivesBean activesBean;
//    public Team3Adapter(Context context,int layoutResId, List<CouponModel.DataBean.ActivesBean> actives) {
//        this.mContext = context;
//        this.layoutResId = layoutResId;
//        this.actives = actives;
//
//    }
//
//
//    @NonNull
//    @Override
//    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
//        BaseViewHolder holder = new BaseViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//        try {
//            activesBean = actives.get(position%actives.size());
//            holder.tv_name.setText(activesBean.getActiveName());
//            Glide.with(mContext).load(activesBean.getDefaultPic()).into(holder.iv_pic);
//            holder.tv_price.setText(activesBean.getPrice());
//        }catch (Exception e) {
//
//        }
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return Integer.MAX_VALUE;
//
//    }
//
//
//    public class BaseViewHolder extends RecyclerView.ViewHolder {
//        private RelativeLayout rl_group;
//        private RelativeLayout rl_coupon;
//        private ImageView iv_add;
//        private ImageView iv_pic;
//        private TextView tv_price;
//        private TextView tv_old_price;
//        private TextView tv_desc;
//        private TextView tv_name;
//        private ImageView iv_sale_done;
//        private ImageView iv_flag;
//        private TextView tv_coupon;
//        public BaseViewHolder(View view) {
//            super(view);
//            rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
//            rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);
//            tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
//            iv_add = (ImageView) view.findViewById(R.id.iv_add);
//            iv_flag = (ImageView) view.findViewById(R.id.iv_flag);
//            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
//            tv_price = (TextView) view.findViewById(R.id.tv_price);
//            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
//            tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
//            iv_sale_done = (ImageView) view.findViewById(R.id.iv_sale_done);
//        }
//    }
//
//    public interface OnClick {
//        void shoppingCartOnClick(int position);
//        void tipClick();
//        void addDialog();
//    }

}
