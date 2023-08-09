package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.dialog.FullDetailDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                        exchangeList(item.getProductId());
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

    /**
     * 切换商品规格列表
     * @param productId
     */
    int pos = 0;
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
                        if(exchangeProductModel.isSuccess()) {
                            if(exchangeProductModel.getData()!=null) {

                                List<ExchangeProductModel.DataBean.ProdSpecsBean> prodSpecs = exchangeProductModel.getData().getProdSpecs();
                                for (int i = 0; i < prodSpecs.size(); i++) {
                                    if(prodSpecs.get(i).getProductId() == productId) {
                                        pos = i;
                                    }
                                }

                                FullDetailDialog fullDialog = new FullDetailDialog(mContext,exchangeProductModel.getData(),pos);
                                fullDialog.show();
                            }
                        }else {
                            ToastUtil.showErroMsg(mContext,exchangeProductModel.getMessage());
                        }

                    }
                });
    }


    public interface Onclick {
        void tipClick();
    }
}
