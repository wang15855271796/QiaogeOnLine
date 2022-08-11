package com.puyue.www.qiaoge.activity.home;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;

import java.util.List;

/**
 * Created by ${王涛} on 2019/12/20
 */
public class AddressListAdapter extends BaseQuickAdapter<AddressModel.DataBean,BaseViewHolder> {

    public AddressListAdapter(int layoutResId, @Nullable List<AddressModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressModel.DataBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_choose = helper.getView(R.id.iv_choose);

        TextView tv_default = helper.getView(R.id.tv_default);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_address = helper.getView(R.id.tv_address);
        TextView tv_phone = helper.getView(R.id.tv_phone);

        if(null!= item.shopName && !item.shopName.equals("")) {
            tv_title.setText(item.shopName);
        }else {
            tv_title.setText(item.cityName);
        }

        tv_name.setText(item.userName);
        tv_address.setText(item.provinceName+item.cityName+item.areaName+item.detailAddress);
        tv_phone.setText(item.contactPhone);

        if(item.isDefault==1) {
            tv_default.setVisibility(View.VISIBLE);
            iv_choose.setVisibility(View.VISIBLE);
        }else {
            iv_choose.setVisibility(View.GONE);
            tv_default.setVisibility(View.GONE);
        }
    }
}
