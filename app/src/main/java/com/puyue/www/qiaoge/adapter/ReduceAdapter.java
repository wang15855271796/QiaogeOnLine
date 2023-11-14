package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ReduceDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.RoundImageView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/6/2
 */
public class ReduceAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {
    private RoundImageView iv_pic;
    List<ProductNormalModel.DataBean.ListBean> activesBean;
    private ImageView iv_add;
    Onclick onclick;
    private ReduceDialog reduceDialog;
    private RelativeLayout rl_group;
    private TextView tv_sale;
    ImageView iv_flag;
    String enjoyProduct;
    private TextView tv_desc;
    TextView tv_price;
    ImageView iv_operate;
    ImageView iv_next;
    View v_champion;
    TextView tv_champion;
    LinearLayout ll_champion;
    ImageView iv_fresh_price;
    ImageView iv_reduce;
    ImageView iv_coupon;
    ImageView iv_new;
    public ReduceAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> activeList, Onclick onclick) {
        super(layoutResId, activeList);
        this.activesBean = activeList;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        iv_next = helper.getView(R.id.iv_next);
        iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        ll_champion = helper.getView(R.id.ll_champion);
        v_champion = helper.getView(R.id.v_champion);
        iv_operate = helper.getView(R.id.iv_operate);
        tv_desc = helper.getView(R.id.tv_desc);
        tv_price = helper.getView(R.id.tv_price);
        iv_pic = helper.getView(R.id.iv_pic);
        iv_flag = helper.getView(R.id.iv_flag);
        iv_add = helper.getView(R.id.iv_add);
        rl_group = helper.getView(R.id.rl_group);
        tv_sale = helper.getView(R.id.tv_sale);
        tv_champion = helper.getView(R.id.tv_champion);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        Glide.with(mContext).load(item.getSendTimeTpl()).into(iv_next);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getProductName());
        helper.setText(R.id.tv_price,item.getMinMaxPrice());
        ImageView iv_send = helper.getView(R.id.iv_send);
        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }
        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);

        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }
        if(item.getHotProdFlag()==1) {
            //1热销
            ll_champion.setVisibility(View.VISIBLE);
            v_champion.setVisibility(View.GONE);
        }else {
            ll_champion.setVisibility(View.GONE);
            v_champion.setVisibility(View.GONE);
        }

        iv_reduce = helper.getView(R.id.iv_reduce);
        iv_coupon = helper.getView(R.id.iv_coupon);
        iv_new = helper.getView(R.id.iv_new);
        Glide.with(mContext).load(item.getProdDeductUrl()).into(iv_reduce);
        Glide.with(mContext).load(item.getProdSpecialUrl()).into(iv_coupon);
        Glide.with(mContext).load(item.getProdNewUrl()).into(iv_new);


        ll_champion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotProductActivity.class);
                mContext.startActivity(intent);
            }
        });

        if(SharedPreferencesUtil.getString(mContext, "priceType").equals("1")) {
            tv_price.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
            tv_price.setText(item.getMinMaxPrice());
        }else {
            tv_price.setVisibility(View.GONE);
            tv_desc.setVisibility(View.VISIBLE);
        }

        tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.tipClick();
                }
            }
        });


        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getProductMainId());
                intent.putExtra("priceType",enjoyProduct);
                mContext.startActivity(intent);
            }
        });


        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                        onclick.addDialog();
                        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                            exchangeList(item.getProductId());
                        }
                    }else {
                        onclick.tipClick();
                    }

                }
            }
        });
    }

    /**
     * 切换商品规格列表
     * @param productId
     */
    private void exchangeList(int productId) {
        GetProductDetailAPI.getExchangeList(mContext,productId,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExchangeProductModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ExchangeProductModel exchangeProductModel) {
                        if(exchangeProductModel.getCode()==1) {
                            if(exchangeProductModel.getData()!=null) {
                                reduceDialog = new ReduceDialog(mContext,exchangeProductModel.getData());
                                reduceDialog.show();
                            }
                        }else {
                            ToastUtil.showErroMsg(mContext,exchangeProductModel.getMessage());
                        }

                    }
                });
    }

    public interface Onclick {
        void addDialog();
        void tipClick();
    }

}
