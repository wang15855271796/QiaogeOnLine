package com.puyue.www.qiaoge.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CouDanAdapter;
import com.puyue.www.qiaoge.adapter.CouDanUnAdapter;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/12/3
 */
public class CouponsSelfUnUseFragment extends BaseFragment {

    private RecyclerView recyclerView;
    RecyclerView recyclerView_un;
    private CouDanAdapter adapter;
    private LinearLayout data;
    private  LinearLayout noData;
    TextView tv_desc;
    TextView tv_nocoudan;
    TextView tv_coudan;
    private List<queryUserDeductByStateModel.DataBean.ListBean > lists =new ArrayList<>();

    public static CouponsSelfUnUseFragment newInstance(String giftDetailNo,String normalProductBalanceVOStr,String activityBalanceVOStr) {
        Bundle args = new Bundle();
        args.putString("giftDetailNo", giftDetailNo);
        args.putString("activityBalanceVOStr", activityBalanceVOStr);
        args.putString("normalProductBalanceVOStr", normalProductBalanceVOStr);
        CouponsSelfUnUseFragment fragment = new CouponsSelfUnUseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_coupon_unuse;
    }

    @Override
    public void initViews(View view) {

    }

    boolean statModel;
    String giftDetailNo;
    String normalProductBalanceVOStr;
    String activityBalanceVOStr;
    @Override
    public void findViewById(View view) {
        tv_nocoudan = view.findViewById(R.id.tv_nocoudan);
        tv_coudan = view.findViewById(R.id.tv_coudan);
        recyclerView_un = view.findViewById(R.id.recyclerView_un);
        tv_desc = view.findViewById(R.id.tv_desc);
        recyclerView=view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.recyclerView_un);
        data= view .findViewById(R.id.data);
        noData= view.findViewById(R.id.noData);

        statModel = getArguments().getBoolean("statModel");
        giftDetailNo = getArguments().getString("giftDetailNo");
        normalProductBalanceVOStr = getArguments().getString("normalProductBalanceVOStr");
        activityBalanceVOStr = getArguments().getString("activityBalanceVOStr");
        activityBalanceVOStr = getArguments().getString("activityBalanceVOStr");
        userChooseDeduct();

    }
    CouDanUnAdapter adapter1;
    @Override
    public void setViewData() {
        //凑单可用
        adapter = new CouDanAdapter(R.layout.item_my_coupons,dataBean1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        //本身不可用
        adapter1 = new CouDanUnAdapter(R.layout.item_my_coupons,dataBean2);
        recyclerView_un.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_un.setAdapter(adapter1);

    }

    @Override
    public void setClickEvent() {

    }

    UserChooseDeductModel models;
    List<UserChooseDeductModel.DataBean>dataBean1 = new ArrayList<>();
    List<UserChooseDeductModel.DataBean>dataBean2 = new ArrayList<>();
    private List<UserChooseDeductModel.DataBean> list = new ArrayList<>();
    private void userChooseDeduct() {
        userChooseDeductAPI.requestData(getContext(), "0",activityBalanceVOStr, normalProductBalanceVOStr,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserChooseDeductModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserChooseDeductModel model) {
                        if (model.success) {
                            models = model;

                            for (int i = 0; i < model.getData().size(); i++) {
                                if(model.getData().get(i).getGiftFlag().equals("1")) {
                                    //凑单可用
                                    dataBean1.add(model.getData().get(i));
                                    adapter.notifyDataSetChanged();

                                    if(dataBean1.size()>0) {
                                        tv_coudan.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_coudan.setVisibility(View.GONE);
                                    }
                                }else {
                                    //凑单不可用
                                    dataBean2.add(model.getData().get(i));
                                    adapter1.notifyDataSetChanged();

                                    if(dataBean2.size()>0) {
                                        tv_nocoudan.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_nocoudan.setVisibility(View.GONE);
                                    }
                                }

                            }
                        } else {
                            AppHelper.showMsg(getContext(), model.message);
                        }

                    }
                });
    }

}
