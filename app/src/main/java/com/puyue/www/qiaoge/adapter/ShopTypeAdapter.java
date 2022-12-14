package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        TextView tv_name = helper.getView(R.id.tv_name);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        if(helper.getAdapterPosition()==0) {
            iv_pic.setImageResource(R.mipmap.info_shop);
        }else if(helper.getAdapterPosition()==1) {
            iv_pic.setImageResource(R.mipmap.info_tool);
        }else if(helper.getAdapterPosition()==2) {
            iv_pic.setImageResource(R.mipmap.info_chef);
        }else {
            iv_pic.setImageResource(R.mipmap.info_other);
        }

        tv_name.setText(item);


    }

    public void setSelectionPosition(int position) {
        this.pos = position;

    }
}
