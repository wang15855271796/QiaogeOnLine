package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CityWareHouseActivity;
import com.puyue.www.qiaoge.activity.FrontWareHouseActivity;
import com.puyue.www.qiaoge.activity.ProviderWareHouseActivity;
import com.puyue.www.qiaoge.model.OpenShopInfoModel;

import java.util.List;

public class OpenShopAdapter extends BaseQuickAdapter<OpenShopInfoModel.DataBean.ShopsBean, BaseViewHolder> {

    public OpenShopAdapter(int layoutResId, @Nullable List<OpenShopInfoModel.DataBean.ShopsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OpenShopInfoModel.DataBean.ShopsBean item) {
        helper.setText(R.id.tv_desc,item.getDesc());
        helper.setText(R.id.tv_title,item.getTitle());
        TextView tv_provider = helper.getView(R.id.tv_provider);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        if(!TextUtils.isEmpty(item.getIcon())) {
            Glide.with(mContext).load(item.getIcon()).into(iv_pic);
        }

        if(item.getCompanyType() ==0) {
            //城市合伙人
            tv_provider.setText("城市仓入驻");
        }

        if(item.getCompanyType() == 1) {
            //前置仓
            tv_provider.setText("前置仓入驻");
        }

        if(item.getCompanyType() == 2) {
            //成为供应商
            tv_provider.setText("供应商入驻");
        }
    }
}
