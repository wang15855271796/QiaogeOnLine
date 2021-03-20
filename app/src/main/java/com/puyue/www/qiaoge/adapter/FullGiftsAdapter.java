package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/8/29
 */
public class FullGiftsAdapter extends BaseQuickAdapter<CouponModel.DataBean.ActivesBean,BaseViewHolder> {

    public FullGiftsAdapter(int layoutResId, @Nullable List<CouponModel.DataBean.ActivesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.DataBean.ActivesBean item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        helper.setText(R.id.tv_full_desc,item.getSendGiftInfo());
        helper.setText(R.id.tv_name,item.getProductName());
        helper.setText(R.id.tv_price,item.getMinMaxPrice());
        helper.setOnClickListener(R.id.tv_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
//                intent.putExtra(AppConstant.ACTIVEID, item.getProductMainId());
//                intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
//                mContext.startActivity(intent);
            }
        });
    }
}
