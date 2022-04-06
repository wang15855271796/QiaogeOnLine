package com.puyue.www.qiaoge.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.AddressAdapters;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;

import java.util.List;

public class OrderAddressAdapter extends BaseQuickAdapter<AddressModel.DataBean, BaseViewHolder> {
    private OnEventClickListener mOnEventClickListener;

    public OrderAddressAdapter(int layoutResId, @Nullable List<AddressModel.DataBean> data) {
        super(layoutResId, data);
    }

    public interface OnEventClickListener {
        void onEventClick(View view, int position, String flag);

        void onEventLongClick(View view, int position, String flag);
    }

    public void setOnItemChangeClickListener(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, AddressModel.DataBean item) {
        helper.setText(R.id.tv_item_address_name, item.userName);
        TextView tv_set = helper.getView(R.id.tv_set);
        helper.setText(R.id.tv_item_address_phone, item.contactPhone);
        LinearLayout ll_item_address_default = helper.getView(R.id.ll_item_address_default);

        helper.setText(R.id.tv_item_address_address, item.provinceName+item.cityName+item.areaName+item.detailAddress);

        //切换默认地址
        ((LinearLayout) helper.getView(R.id.ll_item_address_default)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "default");

            }
        });
        //编辑地址
        ((TextView) helper.getView(R.id.tv_item_address_edit)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "edit");
            }
        });
        //删除地址
        ((TextView) helper.getView(R.id.tv_item_address_delete)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "delete");
            }
        });

        //切换订单地址
        ((LinearLayout) helper.getView(R.id.ll_item_address)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "order_address");
            }
        });
    }
}
