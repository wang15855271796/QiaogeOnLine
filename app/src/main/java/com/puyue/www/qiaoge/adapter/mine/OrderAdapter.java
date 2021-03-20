package com.puyue.www.qiaoge.adapter.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.OrderPriceAdapter;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.api.home.GetAllCommentListByPageAPI;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.model.home.JumpModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.GlideModel;
import com.puyue.www.qiaoge.view.LineBreakLayout;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/3/16
 */
public class OrderAdapter extends BaseQuickAdapter<GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean,BaseViewHolder> {

    private ImageView imageView;
    private ImageView imageIcon;
    private TextView oldPrice;
    private LineBreakLayout lineBreakLayout;
    private TextView tv_return_status;
    TextView coupon;
    private LinearLayout ll_good;
    String orderId;
    RecyclerView rv2;
    TextView tv_old_price;
    TextView tv_current_price;
    TextView tv_coupon_price;
    TextView tv_spec;
    public OrderAdapter(int layoutResId, @Nullable List<GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean> data, String orderId) {
        super(layoutResId, data);
        this.orderId = orderId;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean item) {
        tv_spec = helper.getView(R.id.tv_spec);
        tv_coupon_price = helper.getView(R.id.tv_coupon_price);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_current_price = helper.getView(R.id.tv_current_price);
        coupon = helper.getView(R.id.coupon);
        imageView = helper.getView(R.id.imageView);
        imageIcon = helper.getView(R.id.imageIcon);
        oldPrice = helper.getView(R.id.oldPrice);
        lineBreakLayout = helper.getView(R.id.lineBreakLayout);
        tv_return_status = helper.getView(R.id.tv_return_status);
        ll_good = helper.getView(R.id.ll_good);
        lineBreakLayout.removeAllViews();
        rv2 = helper.getView(R.id.rv2);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.setText(item.oldPrice);
        tv_current_price.setText("￥"+item.price);
        tv_coupon_price.setText(item.afterPrice);
        tv_spec.setText(item.spec);
        OrderPriceAdapter orderPriceAdapter = new OrderPriceAdapter(R.layout.item_order_desc,item.specPrices);
        rv2.setLayoutManager(new LinearLayoutManager(mContext));
        rv2.setAdapter(orderPriceAdapter);
        ll_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.businessType == 1) {
                    jumpDetail(orderId, item.productMainId, item.businessType,item);
                }else {
                    if(item.businessType==2) {
                        jumpDetails(orderId, item.productId, item.businessType,item);
                    }else {
                        jumpDetailss(orderId, item.productId, item.businessType,item);
                    }

                }
            }
        });

        if (item.businessType == 2 || item.businessType == 11) { // 有原价 有规格
            if (item.oldPrice != null && StringHelper.notEmptyAndNull(item.oldPrice)) {
                oldPrice.setVisibility(View.GONE);
                oldPrice.setText(item.oldPrice + "");
                oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            } else {
                oldPrice.setVisibility(View.GONE);
            }
        } else if (item.businessType == 1) {  // 没有原价 有规格
            oldPrice.setVisibility(View.GONE);


        } else if (item.businessType == 3) {// 没有原价 没有规格
            oldPrice.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.productName)) {
            helper.setText(R.id.textTitle, item.productName);
        }

        if (StringHelper.notEmptyAndNull(item.picUrl)) {
            GlideModel.disPlayError(mContext, item.picUrl, imageView);
        }

//        if (item.businessType != 1) {
//            imageIcon.setVisibility(View.VISIBLE);
//
//        } else {
//            imageIcon.setVisibility(View.GONE);
//        }

        if(item.prodTypeUrl!=null) {
            Glide.with(mContext).load(item.prodTypeUrl).into(imageIcon);
        }
    }

    private void jumpDetails(String orderId, int productId, int businessType,GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean item) {
        GetAllCommentListByPageAPI.jumpDetail(mContext, orderId, productId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JumpModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JumpModel jumpModel) {
                        if(jumpModel.isSuccess()) {
                            if(jumpModel.getData()!=null) {

                                if(jumpModel.getData().equals("-1")) {
                                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,productId);
                                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("num",jumpModel.getData());
                                    mContext.startActivity(intent);
                                }else {
                                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,productId);
                                    intent.putExtra("num",jumpModel.getData());
                                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("city",jumpModel.getMessage());
                                    mContext.startActivity(intent);
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,jumpModel.getMessage());
                        }
                    }
                });
    }
    private void jumpDetailss(String orderId, int productId, int businessType,GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean item) {
        GetAllCommentListByPageAPI.jumpDetail(mContext, orderId, productId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JumpModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JumpModel jumpModel) {
                        if(jumpModel.isSuccess()) {

                            if(jumpModel.getData()!=null) {
                                if(jumpModel.getData().equals("-1")) {
                                    Intent intent = new Intent(mContext, SpecialGoodDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,productId);
                                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("num",jumpModel.getData());
                                    mContext.startActivity(intent);
                                }else {
                                    Intent intent = new Intent(mContext, SpecialGoodDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,productId);
                                    intent.putExtra("num",jumpModel.getData());
                                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("city",jumpModel.getMessage());
                                    mContext.startActivity(intent);
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,jumpModel.getMessage());
                        }
                    }
                });
    }
    /**
     * 跳转详情
     */
    private void jumpDetail(String orderId, int businessId, int businessType, GetOrderDetailModel.DataBean.OrderProdsBean.ProductsBean item) {
        GetAllCommentListByPageAPI.jumpDetail(mContext, orderId, businessId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JumpModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JumpModel jumpModel) {
                        if(jumpModel.isSuccess()) {
                            if(jumpModel.getData()!=null) {
                                if(jumpModel.getData().equals("-1")) {
                                    Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,item.productMainId);
                                    intent.putExtra("num",jumpModel.getData());
                                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                                    mContext.startActivity(intent);
                                }else {
                                    Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,item.productMainId);
                                    intent.putExtra("num",jumpModel.getData());
                                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("city",jumpModel.getMessage());
                                    mContext.startActivity(intent);
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,jumpModel.getMessage());
                        }
                    }
                });
    }

}
