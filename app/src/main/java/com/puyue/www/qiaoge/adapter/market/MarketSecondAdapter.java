package com.puyue.www.qiaoge.adapter.market;

import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.market.ClassIfyModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
//
public class MarketSecondAdapter extends BaseQuickAdapter<ClassIfyModel.DataBean.SecondClassifyBean,BaseViewHolder> {
    private int selectPosition;
    OnEventClickListener mOnEventClickListener;
    com.puyue.www.qiaoge.listener.OnItemClickListener onItemClickListener;
    private ImageView iv_icon;

    public MarketSecondAdapter(int layoutResId, @Nullable List<ClassIfyModel.DataBean.SecondClassifyBean> data) {
        super(layoutResId, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, ClassIfyModel.DataBean.SecondClassifyBean item) {
        iv_icon = helper.getView(R.id.iv_icon);
        LinearLayout rl_bg = helper.getView(R.id.rl_bg);
        View iv_point = helper.getView(R.id.iv_point);
        if(helper.getAdapterPosition()==1) {
//            iv_icon.setImageResource(R.mipmap.icon_hot);
            Glide.with(mContext).load(item.getImgUrl()).into(iv_icon);
            iv_icon.setVisibility(View.VISIBLE);

        }else if(helper.getAdapterPosition()==2){
//            iv_icon.setImageResource(R.mipmap.icon_tip);
            iv_icon.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getImgUrl()).into(iv_icon);
        }else if(helper.getAdapterPosition()==3){
//            iv_icon.setImageResource(R.mipmap.icon_new);
            iv_icon.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getImgUrl()).into(iv_icon);
        }else if(helper.getAdapterPosition()==4){
//            iv_icon.setImageResource(R.mipmap.icon_coupon);
            iv_icon.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getImgUrl()).into(iv_icon);
        }else {
            iv_icon.setVisibility(View.GONE);
        }

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());
        if(selectPosition == helper.getLayoutPosition()) {
            iv_point.setBackgroundColor(Color.parseColor("#FF2925"));
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_name.setTextColor(Color.parseColor("#333333"));
            rl_bg.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            tv_name.setBackground(null);
            iv_point.setBackgroundColor(Color.parseColor("#f3f3f3"));
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_name.setTextColor(Color.parseColor("#333333"));
            rl_bg.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

        if (onItemClickListener != null) {
            rl_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,helper.getAdapterPosition());

                }
            });
        }
    }


    public interface OnPositionListener {
        void getPos(int position,String firstId,int secondId);

    }
    public interface OnEventClickListener {
        void onEventClick(int position, int secondId);

    }

    public void setOnItemClickListeners(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }


    public void setOnItemClickListener(com.puyue.www.qiaoge.listener.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
