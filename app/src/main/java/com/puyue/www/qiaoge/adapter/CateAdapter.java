package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class CateAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    int pos = 0;
    Onclick onclick;
    public CateAdapter(int layoutResId, @Nullable List<String> data,Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,item);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RelativeLayout rl_root = helper.getView(R.id.rl_root);
        if(pos==helper.getAdapterPosition()) {
            iv_pic.setVisibility(View.VISIBLE);
        }else {
            iv_pic.setVisibility(View.GONE);
        }
    }


    public void setPosition(int position) {
        this.pos = position;
    }

    public interface Onclick {
        void getCate(String item);
    }
}
