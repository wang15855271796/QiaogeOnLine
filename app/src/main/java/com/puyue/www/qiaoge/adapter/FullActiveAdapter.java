package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.dialog.FullDetailDialog;
import com.puyue.www.qiaoge.dialog.FullDialog;
import com.puyue.www.qiaoge.event.FullListModel;
import com.puyue.www.qiaoge.model.FullDetailModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullActiveAdapter extends BaseQuickAdapter<FullDetailModel.DataBean.ProdsBean,BaseViewHolder> {

    public FullActiveAdapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.ProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullDetailModel.DataBean.ProdsBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_not_send = helper.getView(R.id.iv_not_send);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_ok = helper.getView(R.id.tv_ok);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_price.setText(item.getMinMaxPrice());
        tv_title.setText(item.getProductName());
        tv_spec.setText(item.getSpec());

        if(item.getNotSend()==1) {
            iv_not_send.setVisibility(View.VISIBLE);
        }else {
            iv_not_send.setVisibility(View.GONE);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullDetailDialog fullDialog = new FullDetailDialog(mContext,item);
                fullDialog.show();
            }
        });
    }
}
