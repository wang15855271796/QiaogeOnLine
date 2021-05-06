package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.SurplierAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.SurpNumEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.SurpliModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GuessModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/4/23
 */
public class ShopsActivity extends BaseActivity {
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_goods_num)
    TextView tv_goods_num;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SurplierAdapter surplierAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.shops_activity);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    int pageNum = 1;
    int pageSize = 10;
    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        surplierAdapter = new SurplierAdapter(R.layout.item_noresult_recommend,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(surplierAdapter);
        getCartNum();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(UpDateNumEvent7 event) {
        getCartNum();
    }

    @Override
    public void setClickEvent() {
        String surplieId = getIntent().getStringExtra("surplieId");
        getSupplierInfo(surplieId);
        getSupplierList(surplieId);

        ll_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,IntelliGencyInfoActivity.class);
                intent.putExtra("id",surplieId);
                startActivity(intent);
            }
        });
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,CartActivity.class);
                startActivity(intent);
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SearchSurpStartActivity.class);
                intent.putExtra("surplieId",surplieId);
                startActivity(intent);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                refreshLayout.setEnableLoadMore(false);
                getSupplierList(surplieId);
                refreshLayout.finishRefresh();

            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if(surpliListModels != null) {
                    if(surpliListModels.getData().isHasNextPage()) {
                        pageNum++;
                        getSupplierList(surplieId);
                        refreshLayout.finishLoadMore();      //加载完成
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }


            }
        });

    }

    private void getSupplierInfo(String surplieId) {

        RecommendApI.getSupplier(mContext,surplieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SurpliModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SurpliModel recommendModel) {
                        if (recommendModel.isSuccess()) {
                           if(recommendModel.getData()!=null) {
                               tv_title.setText(recommendModel.getData().getSupplierName());
                               tv_goods_num.setText("商品数量:"+recommendModel.getData().getProdNum()+"");
                               tv_sale.setText("月销量:"+recommendModel.getData().getSaleVolume()+"");
                           }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, recommendModel.getMessage());
                        }
                    }
                });
    }

    List<SurpliListModel.DataBean.ListBean> list = new ArrayList<>();
    SurpliListModel surpliListModels;
    private void getSupplierList(String surplieId) {
        RecommendApI.getSupplierList(mContext,surplieId,"",pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SurpliListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SurpliListModel surpliListModel) {
                        if (surpliListModel.isSuccess()) {
                            if(surpliListModel.getData()!=null) {
                                surpliListModels = surpliListModel;
                                List<SurpliListModel.DataBean.ListBean> lists = surpliListModel.getData().getList();
                                list.addAll(lists);
                                surplierAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, surpliListModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 购物车数量
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {
                        if (getCartNumModel.isSuccess()) {
                            if(getCartNumModel.getData().getNum().equals("0")) {
                                tv_num.setVisibility(View.GONE);
                            }else {
                                tv_num.setVisibility(View.VISIBLE);

                                tv_num.setText(getCartNumModel.getData().getNum());
                            }
                        } else {
                            AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                        }
                    }
                });
    }
}
