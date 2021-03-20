package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class ShopTypeAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    int pos = -1;
    public ShopTypeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        RelativeLayout rl = helper.getView(R.id.rl);
        if(pos==helper.getAdapterPosition()) {
            iv_choose.setVisibility(View.VISIBLE);
            rl.setBackgroundResource(R.drawable.shape_orange_shop);
        }else {
            iv_choose.setVisibility(View.GONE);
            rl.setBackgroundResource(R.drawable.shape_grey_shop);
        }

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item);


    }

    public void setSelectionPosition(int position) {
        this.pos = position;

    }
}
