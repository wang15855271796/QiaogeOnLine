package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

public class FullGoodsAdapter extends BaseQuickAdapter<FullDetailModel.DataBean.SendGiftsBean, BaseViewHolder> {

    public FullGoodsAdapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_spec,item.getSpec());
        helper.setText(R.id.tv_num,item.getSendNum());
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getPicture()).into(iv_pic);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                intent.putExtra("activeId",item.getProductMainId());
                intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext, "priceType"));
                mContext.startActivity(intent);
            }
        });
    }
}
