package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.flow.FlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagAdapter;
import com.puyue.www.qiaoge.activity.flow.TagFlowLayout;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.api.market.MarketRightModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponSearchDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/23
 */
public class CouponSearchAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {

    private ImageView iv_head;
    private TextView tv_stock;
    private LinearLayout ll_group;
    private ImageView iv_type;
    Onclick onclick;
    CouponSearchDialog searchDialog;
    private TextView tv_price_desc;
    ImageView iv_operate;
    boolean isOpen;
    public CouponSearchAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> data,Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        iv_operate = helper.getView(R.id.iv_operate);
        RelativeLayout rl_open =  helper.getView(R.id.rl_open);
        ImageView iv_no_data = helper.getView(R.id.iv_no_data);
        TextView tv_style =  helper.getView(R.id.tv_style);
        RecyclerView rv_spec =  helper.getView(R.id.rv_spec);
        tv_price_desc = helper.getView(R.id.tv_price_desc);
        iv_type = helper.getView(R.id.iv_type);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        if(item.getFlag()==0) {
            Glide.with(mContext).load(item.getTypeUrl()).into(iv_no_data);
            iv_no_data.setVisibility(View.VISIBLE);
            iv_type.setVisibility(View.GONE);
        }else {
            Glide.with(mContext).load(item.getTypeUrl()).into(iv_type);
            iv_type.setVisibility(View.VISIBLE);
            iv_no_data.setVisibility(View.GONE);
        }
        RelativeLayout rl_spec = helper.getView(R.id.rl_spec);
        ll_group = helper.getView(R.id.ll_group);
        ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID, item.getProductMainId());
                intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });
        RelativeLayout rl_price = helper.getView(R.id.rl_price);
        TextView tv_price = helper.getView(R.id.tv_price);
        helper.setText(R.id.tv_name,item.getProductName());
        helper.setText(R.id.tv_sale,item.getSalesVolume());

        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
            rl_price.setVisibility(View.GONE);
            rl_spec.setVisibility(View.VISIBLE);
            tv_price.setText(item.getMinMaxPrice());
            tv_price.setVisibility(View.VISIBLE);
            tv_price_desc.setVisibility(View.GONE);
        }else {
            rl_spec.setVisibility(View.GONE);
            rl_price.setVisibility(View.VISIBLE);
            tv_price.setText(item.getMinMaxPrice());
            tv_price.setVisibility(View.GONE);
            tv_price_desc.setVisibility(View.VISIBLE);
        }

//        TagAdapter unAbleAdapter = new TagAdapter<ProductNormalModel.DataBean.ListBean.ProdSpecsBean>(item.getProdSpecs()){
//
//            @Override
//            public View getView(FlowLayout parent, int position, ProductNormalModel.DataBean.ListBean.ProdSpecsBean prodSpecsBean) {
//                View view = LayoutInflater.from(mContext).inflate(R.layout.item_specss,rv_spec, false);
//                TextView tv_spec = view.findViewById(R.id.tv_spec);
//                tv_spec.setText(prodSpecsBean.getSpec());
//                return view;
//            }
//        };

        tv_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.addDialog();
                }

                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    searchDialog = new CouponSearchDialog(mContext,item);
                    searchDialog.show();
                }
            }
        });
        CouponSpecAdapter couponSpecAdapter = new CouponSpecAdapter(R.layout.item_specss,item.getProdSpecs());
        rv_spec.setAdapter(couponSpecAdapter);
        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));

        if(item.getProdSpecs().size()>3) {
            tv_style.setVisibility(View.VISIBLE);
        }else {
            tv_style.setVisibility(View.GONE);
        }

//        rl_open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isOpen) {
//                    isOpen = false;
//                    tv_style.setText("展开全部规则");
//                    iv_icon.setImageResource(R.mipmap.icon_arrow_light_down);
//                    rv_spec.setLimit(true);
//                }else {
//                    tv_style.setText("收起全部规则");
//                    isOpen = true;
//                    iv_icon.setImageResource(R.mipmap.icon_arrow_light_up);
//                    rv_spec.setLimit(false);
//                }
//                unAbleAdapter.notifyDataChanged();
//            }
//        });

        rl_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.getPrice();
                }
            }
        });
        rl_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.addDialog();
                }

                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    searchDialog = new CouponSearchDialog(mContext,item);
                    searchDialog.show();
                }
            }
        });

        iv_head = helper.getView(R.id.iv_head);
        Glide.with(mContext)
                .load(item.getDefaultPic())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .apply(new RequestOptions().placeholder(iv_head.getDrawable()).skipMemoryCache(false).dontAnimate())
                .into(iv_head);
    }

    public interface Onclick {
        void addDialog();
        void getPrice();
    }

}
