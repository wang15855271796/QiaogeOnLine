package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CouponUseActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.List;

public class Coupon4Adapter extends BaseQuickAdapter<FullDetailModel.DataBean.SendGiftsBean, BaseViewHolder> {

    public Coupon4Adapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_num,item.getSendNum());
        helper.setText(R.id.tv_role,item.getRole().get(0));
        helper.setText(R.id.tv_spec,item.getUseInfo());
        TextView tv_detail = helper.getView(R.id.tv_detail);

        if(item.getGiftProdUseType().equals("1")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(item.getGiftProdUseType().equals("2")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("1")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else if(item.getGiftProdUseType().equals("2")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else {
                    tv_detail.setVisibility(View.GONE);
                }
            }
        });
    }
}
