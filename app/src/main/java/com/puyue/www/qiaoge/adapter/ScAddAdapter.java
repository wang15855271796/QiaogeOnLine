package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.EditAddressActivity;
import com.puyue.www.qiaoge.adapter.mine.AddressAdapter;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;

import java.util.List;

public class ScAddAdapter extends BaseQuickAdapter<AddressModel.DataBean, BaseViewHolder> {

    int pos = -1;
    public ScAddAdapter(int layoutResId, @Nullable List<AddressModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressModel.DataBean item) {
        helper.setText(R.id.tv_address,item.detailAddress);
        helper.setText(R.id.tv_name,item.userName);
        helper.setText(R.id.tv_phone,item.contactPhone);
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        if(pos == helper.getAdapterPosition()) {
            iv_choose.setImageResource(R.mipmap.iv_sc_choose);
        }else {
            iv_choose.setImageResource(R.mipmap.iv_sc_un_choose);
        }

        TextView tv_edit = helper.getView(R.id.tv_edit);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onScClickListener.onScClick(helper.getAdapterPosition());
            }
        });
    }

    public void setPos(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }

    OnScClickListener onScClickListener;
    public void setOnEditClickListener(OnScClickListener onScClickListener) {
        this.onScClickListener = onScClickListener;
    }

    public interface OnScClickListener {
        void onScClick(int pos);
    }
}
