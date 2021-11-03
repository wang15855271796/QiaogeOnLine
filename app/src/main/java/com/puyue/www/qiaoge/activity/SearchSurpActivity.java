package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.SurplierAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

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
 * Created by ${王涛} on 2021/4/24
 */
public class SearchSurpActivity extends BaseActivity {
    int pageNum = 1;
    int pageSize = 10;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.search_btn_back)
    LinearLayout search_btn_back;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search_surp);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        String search = getIntent().getStringExtra("search");
        tv_search.setText(search);
        String surplieId = getIntent().getStringExtra("surplieId");
        getSupplierList(surplieId,search);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HomeActivity.class));
                EventBus.getDefault().post(new GoToCartFragmentEvent());
//                Intent intent = new Intent(mActivity,CartActivity.class);
//                startActivity(intent);
            }
        });

        search_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EventBus.getDefault().register(this);
        surplierAdapter = new SurplierAdapter(R.layout.item_noresult_recommend,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(surplierAdapter);
        getCartNum();

    }

    @Override
    public void setViewData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(UpDateNumEvent7 event) {
        getCartNum();
    }

    @Override
    public void setClickEvent() {

    }
    List<SurpliListModel.DataBean.ListBean> list = new ArrayList<>();
    SurpliListModel surpliListModels;
    SurplierAdapter surplierAdapter;
    private void getSupplierList(String surplieId,String search) {
        RecommendApI.getSupplierList(mContext,surplieId,search,pageNum,pageSize)
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
                                if(surpliListModel.getData().getList().size()>0) {
                                    Log.d("wfsdfdsf....","0");
                                    iv_no_data.setVisibility(View.GONE);
                                    ll.setVisibility(View.VISIBLE);
                                }else {
                                    Log.d("wfsdfdsf....","1");
                                    iv_no_data.setVisibility(View.VISIBLE);
                                    ll.setVisibility(View.GONE);
                                }

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
