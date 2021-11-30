package com.puyue.www.qiaoge.fragment.market;

import android.content.Intent;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.api.market.MarketRightModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2019/11/1
 */
class ProdInnerAdapter extends BaseQuickAdapter<MarketRightModel.DataBean.BrandProdBean.ListBeanX.ProdClassifyBean.ListBean,BaseViewHolder> {

    public ProdInnerAdapter(int layoutResId, @Nullable List<MarketRightModel.DataBean.BrandProdBean.ListBeanX.ProdClassifyBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketRightModel.DataBean.BrandProdBean.ListBeanX.ProdClassifyBean.ListBean item) {
        helper.setText(R.id.tv_name,item.getProductName());
        helper.setText(R.id.tv_sale,item.getSalesVolume());
        ImageView iv_operate = helper.getView(R.id.iv_operate);
        TextView tv_price = helper.getView(R.id.tv_price);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_send = helper.getView(R.id.iv_send);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID, item.getProductMainId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setText(item.getMinMaxPrice());
            }else {
                tv_price.setText("价格授权后可见");
            }
        }else {
            tv_price.setText(item.getMinMaxPrice());
        }

        Glide.with(mContext)
                .load(item.getDefaultPic())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .apply(new RequestOptions().placeholder(iv_pic.getDrawable()).skipMemoryCache(false).dontAnimate())
                .into(iv_pic);

    }
}
