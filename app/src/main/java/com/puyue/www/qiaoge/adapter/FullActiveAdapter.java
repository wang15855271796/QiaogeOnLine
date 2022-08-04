package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.dialog.FullDetailDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullActiveAdapter extends BaseQuickAdapter<FullDetailModel.DataBean.ProdsBean,BaseViewHolder> {

    Onclick onclick;
    public FullActiveAdapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.ProdsBean> data,Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
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

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setText(item.getMinMaxPrice());
            }else {
                tv_price.setText("价格授权后可见");
            }
        }else {
            tv_price.setText(item.getMinMaxPrice());
        }

        tv_title.setText(item.getProductName());
        tv_spec.setText(item.getSpec());


        tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onclick!=null) {
                    onclick.tipClick();
                }
            }
        });



        if(item.getNotSend()==1) {
            iv_not_send.setVisibility(View.VISIBLE);
        }else {
            iv_not_send.setVisibility(View.GONE);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                        FullDetailDialog fullDialog = new FullDetailDialog(mContext,item);
                        fullDialog.show();
                    }else {
                        onclick.tipClick();
                    }
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }


            }
        });
    }


    public interface Onclick {
        void tipClick();
    }
}
