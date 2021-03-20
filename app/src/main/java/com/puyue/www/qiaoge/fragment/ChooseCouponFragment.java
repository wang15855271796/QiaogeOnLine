package com.puyue.www.qiaoge.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ChooseCouonsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.event.ChooseCoupon1Event;
import com.puyue.www.qiaoge.event.ChooseCouponEvent;
import com.puyue.www.qiaoge.fragment.mine.order.AllOrderFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/24
 */
public class ChooseCouponFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_select_all)
    ImageView iv_select_all;
    public static ChooseCouponFragment newInstance(String giftDetailNo,String normalProductBalanceVOStr,String activityBalanceVOStr,boolean statModel) {
        Bundle args = new Bundle();
        args.putBoolean("statModel", statModel);
        args.putString("giftDetailNo", giftDetailNo);
        args.putString("activityBalanceVOStr", activityBalanceVOStr);
        args.putString("normalProductBalanceVOStr", normalProductBalanceVOStr);
        ChooseCouponFragment fragment = new ChooseCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_choose_coupons;
    }

    @Override
    public void initViews(View view) {
        bind = ButterKnife.bind(this,view);
    }

    boolean statModel;
    String giftDetailNo;
    String normalProductBalanceVOStr;
    String activityBalanceVOStr;

    @Override
    public void findViewById(View view) {
        statModel = getArguments().getBoolean("statModel");
        giftDetailNo = getArguments().getString("giftDetailNo");
        normalProductBalanceVOStr = getArguments().getString("normalProductBalanceVOStr");
        activityBalanceVOStr = getArguments().getString("activityBalanceVOStr");
        activityBalanceVOStr = getArguments().getString("activityBalanceVOStr");
        userChooseDeduct();
    }

    ChooseCouponsAdapter adapter;
    @Override
    public void setViewData() {

        adapter = new ChooseCouponsAdapter(R.layout.item_choose_copons, list, new ChooseCouponsAdapter.ImageOnclick() {
            @Override
            public void Onclick(int position, String giftDetailNo) {
                UserChooseDeductModel.DataBean info = list.get(position);
                statModel = false;
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setFlags(!list.get(i).isFlags());
                        if (list.get(i).isFlags()) {
                            EventBus.getDefault().post(new ChooseCouponEvent(info.getGiftDetailNo()));
                            getActivity().finish();
                        } else {
                            getActivity().finish();
                        }
                    } else {
                        list.get(i).setFlags(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        iv_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list!=null) {
                    //未选
                    adapter.setStat();
                    EventBus.getDefault().post(new ChooseCoupon1Event());
                    getActivity().finish();

                }
                statModel = true;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    UserChooseDeductModel models;
    private List<UserChooseDeductModel.DataBean> list = new ArrayList<>();
    private void userChooseDeduct() {
        userChooseDeductAPI.requestData(getContext(), "0",activityBalanceVOStr, normalProductBalanceVOStr,"0")
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

                            if (model.getData().size()> 0) {

                                list.addAll(model.getData());
                                adapter.notifyDataSetChanged();
                                for (int i = 0; i < list.size(); i++) {
                                    if (model.getData().get(i).getGiftDetailNo().equals(giftDetailNo)) {
                                        model.getData().get(i).setFlags(true);
                                        if(statModel) {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_ok);
                                        }else {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_no);
                                        }
                                    } else {
                                        model.getData().get(i).setFlags(false);
                                        if(statModel) {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_ok);
                                        }else {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_no);
                                        }


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
