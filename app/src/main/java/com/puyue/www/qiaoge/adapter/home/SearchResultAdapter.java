package com.puyue.www.qiaoge.adapter.home;


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
import com.puyue.www.qiaoge.adapter.MarketSpecAdapter;
import com.puyue.www.qiaoge.adapter.SearchResultSpecAdapter;
import com.puyue.www.qiaoge.adapter.cart.SearchSpecAdapter;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.fragment.home.MarketsAdapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/16.
 * 搜索推荐Adapter
 */

public class SearchResultAdapter extends BaseQuickAdapter<SearchResultsModel.DataBean.RecommendProdBean,BaseViewHolder>{

    private TextView tv_price_desc;
    private ImageView iv_head;
    private LinearLayout ll_group;
    private ImageView iv_type;
    Onclick onclick;
    RecommendDialog recommendDialog;
    ImageView iv_operate;
    boolean isOpen;
    public SearchResultAdapter(int layoutResId, @Nullable List<SearchResultsModel.DataBean.RecommendProdBean> data, Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultsModel.DataBean.RecommendProdBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        RelativeLayout rl_open =  helper.getView(R.id.rl_open);
        TextView tv_style =  helper.getView(R.id.tv_style);
        RecyclerView rv_spec =  helper.getView(R.id.rv_spec);
        iv_operate = helper.getView(R.id.iv_operate);
        ImageView iv_no_data = helper.getView(R.id.iv_no_data);
        tv_price_desc = helper.getView(R.id.tv_price_desc);
        iv_type = helper.getView(R.id.iv_type);

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

        ImageView iv_send = helper.getView(R.id.iv_send);
        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        RelativeLayout rl_price = helper.getView(R.id.rl_price);
        TextView tv_price = helper.getView(R.id.tv_price);
        helper.setText(R.id.tv_name,item.getProductName());
        helper.setText(R.id.tv_sale,item.getSalesVolume());
        helper.setText(R.id.tv_price,item.getMinMaxPrice());

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
//        TagAdapter unAbleAdapter = new TagAdapter<SearchResultsModel.DataBean.RecommendProdBean.ProdSpecsBean>(item.getProdSpecs()){
//
//            @Override
//            public View getView(FlowLayout parent, int position, SearchResultsModel.DataBean.RecommendProdBean.ProdSpecsBean prodSpecsBean) {
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
                    recommendDialog = new RecommendDialog(mContext,item);
                    recommendDialog.show();
                }
            }
        });
        SearchResultSpecAdapter marketsAdapter = new SearchResultSpecAdapter(R.layout.item_specss,item.getProdSpecs());
        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));
        rv_spec.setAdapter(marketsAdapter);

        if(item.getProdSpecs().size()>3) {
            tv_style.setVisibility(View.VISIBLE);
        }else {
            tv_style.setVisibility(View.GONE);
        }
//        unAbleAdapter.notifyDataChanged();

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
                    recommendDialog = new RecommendDialog(mContext,item);
                    recommendDialog.show();
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