package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/6/2
 */
public class HotAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {

    private ImageView iv_pic;
    private ImageView iv_order;
    List<ProductNormalModel.DataBean.ListBean> activesBean;
    TextView tv_price;
    Onclick onclick;
    ImageView iv_not_send;
    public HotAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> activeList,Onclick onclick) {
        super(layoutResId, activeList);
        this.activesBean = activeList;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        iv_pic = helper.getView(R.id.iv_pic);
        iv_order = helper.getView(R.id.iv_order);
        tv_price = helper.getView(R.id.tv_price);
        tv_price.setText(item.getMinMaxPrice());
        iv_not_send = helper.getView(R.id.iv_not_send);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        if(activesBean.size()>=3) {
            if(helper.getAdapterPosition()==0) {
                iv_order.setVisibility(View.VISIBLE);
                iv_order.setImageResource(R.mipmap.icon_one);
            }else if(helper.getAdapterPosition()==1) {
                iv_order.setVisibility(View.VISIBLE);
                iv_order.setImageResource(R.mipmap.icon_two);
            }else if(helper.getAdapterPosition()==2){
                iv_order.setVisibility(View.VISIBLE);
                iv_order.setImageResource(R.mipmap.icon_three);
            }else {
                iv_order.setVisibility(View.GONE);
            }
        }else {
            if(activesBean.size()==1) {
                iv_order.setVisibility(View.VISIBLE);
                iv_order.setImageResource(R.mipmap.icon_one);
            }else if(activesBean.size()==2) {
                if(helper.getAdapterPosition()==0) {
                    iv_order.setVisibility(View.VISIBLE);
                    iv_order.setImageResource(R.mipmap.icon_one);
                }else if(helper.getAdapterPosition()==1) {
                    iv_order.setVisibility(View.VISIBLE);
                    iv_order.setImageResource(R.mipmap.icon_two);
                }
            }
        }

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_not_send.setImageResource(R.mipmap.icon_not_send2);
                iv_not_send.setVisibility(View.VISIBLE);
            }else {
                iv_not_send.setVisibility(View.GONE);
            }
        }

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setText(item.getMinMaxPrice());
            }else {
                tv_price.setText("价格授权后可见");
            }
        }else {
            tv_price.setText(item.getMinMaxPrice());
        }

        tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.tipClick();
                }
            }
        });

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotProductActivity.class);
                mContext.startActivity(intent);
            }
        });

//        iv_send = helper.getView(R.id.iv_send);
//        tv_desc = helper.getView(R.id.tv_desc);
//        rl_group = helper.getView(R.id.rl_group);
//        ll_more = helper.getView(R.id.ll_more);
//        tv_price = helper.getView(R.id.tv_price);
//        iv_pic = helper.getView(R.id.iv_pic);
//        iv_order = helper.getView(R.id.iv_order);
//        tv_discount = helper.getView(R.id.tv_discount);
//        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
//        helper.setText(R.id.tv_name,item.getProductName());
//        tv_price.setText(item.getMinMaxPrice());
//        if(activesBean.size()-1==helper.getAdapterPosition()) {
//            ll_more.setVisibility(View.VISIBLE);
//        }else {
//            ll_more.setVisibility(View.GONE);
//        }
//
//        if(item.getNotSend()!=null) {
//            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
//                iv_send.setImageResource(R.mipmap.icon_not_send2);
//                iv_send.setVisibility(View.VISIBLE);
//            }else {
//                iv_send.setVisibility(View.GONE);
//            }
//        }
//
//        tv_discount.setVisibility(View.GONE);
//
//

//        tv_desc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onclick!=null) {
//                    onclick.tipClick();
//                }
//            }
//        });
//

//
//        rl_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
//                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
//                intent.putExtra(AppConstant.ACTIVEID,item.getProductMainId());
//                mContext.startActivity(intent);
//            }
//        });
//
//        ll_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,HotProductActivity.class);
//                mContext.startActivity(intent);
//            }
//        });

    }

    public interface Onclick {
        void tipClick();
    }
}
