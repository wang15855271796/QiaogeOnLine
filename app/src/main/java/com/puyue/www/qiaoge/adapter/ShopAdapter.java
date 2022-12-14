package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.GetRegisterShopModel;

import java.util.List;

public class ShopAdapter extends BaseQuickAdapter<GetRegisterShopModel.DataBean, BaseViewHolder> {
    int pos = -1;
    public ShopAdapter(int layoutResId, @Nullable List<GetRegisterShopModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetRegisterShopModel.DataBean item) {
        TextView tv_shop = helper.getView(R.id.tv_shop);
        tv_shop.setText(item.getName());

        if(pos == helper.getAdapterPosition()) {
            tv_shop.setBackgroundResource(R.drawable.shape_red6);
        }else {
            tv_shop.setBackgroundResource(R.drawable.shape_red5);
        }
    }

    public void setPos(int position) {
        pos = position;
        notifyDataSetChanged();
    }
}
