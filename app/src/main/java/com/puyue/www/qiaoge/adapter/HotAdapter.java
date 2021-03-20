package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.puyue.www.qiaoge.dialog.CommonListDialog;
import com.puyue.www.qiaoge.dialog.HotDialog;
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
    TextView tv_unit;
    TextView tv_price;
    RelativeLayout rl_group;
    LinearLayout ll_more;
    TextView tv_discount;
    public HotAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> activeList) {
        super(layoutResId, activeList);
        this.activesBean = activeList;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        rl_group = helper.getView(R.id.rl_group);
        ll_more = helper.getView(R.id.ll_more);
        tv_price = helper.getView(R.id.tv_price);
        iv_pic = helper.getView(R.id.iv_pic);
        iv_order = helper.getView(R.id.iv_order);
        tv_discount = helper.getView(R.id.tv_discount);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getProductName());
        tv_price.setText(item.getMinMaxPrice());
        if(activesBean.size()-1==helper.getAdapterPosition()) {
            ll_more.setVisibility(View.VISIBLE);
        }else {
            ll_more.setVisibility(View.GONE);
        }

        tv_discount.setVisibility(View.GONE);

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
            iv_order.setVisibility(View.GONE);
        }

        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                intent.putExtra(AppConstant.ACTIVEID,item.getProductMainId());
                mContext.startActivity(intent);
            }
        });

        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HotProductActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


}
