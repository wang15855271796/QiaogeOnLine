package com.puyue.www.qiaoge.fragment.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.home.SearchReasultAdapter;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/13
 */
public class UnOperateFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int pageNum = 1;
    int PageSize = 10;
    String searchWord;
    SearchReasultAdapter searchReasultAdapter;
    SearchResultsModel.DataBean.SearchProdBean searchProd;
    List<SearchResultsModel.DataBean.SearchProdBean.ListBean> list;
    List<SearchResultsModel.DataBean.SearchProdBean.ListBean> lists = new ArrayList<>();
    public static Fragment getInstance(String searchWord) {
        UnOperateFragment fragment = new UnOperateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchWord", searchWord);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle getargs = getArguments();
        if (getargs != null) {
            searchWord = getargs.getString("searchWord");

        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_unoperate;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void setViewData() {
        getCustomerPhone();
        smart.autoRefresh();
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getRecommendList(pageNum,PageSize);
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if(searchProd != null) {
                    if(searchProd.isHasNextPage()) {
                        pageNum++;
                        getRecommendList(pageNum,PageSize);
                        refreshLayout.finishLoadMore();      //加载完成
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        searchReasultAdapter = new SearchReasultAdapter(R.layout.item_noresult_recommend,lists, new SearchReasultAdapter.Onclick() {
            @Override
            public void addDialog() {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                }else {
                    initDialog();
                }
            }

            @Override
            public void getPrice() {
                showPhoneDialog(cell);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(searchReasultAdapter);
    }


    private String cell; // 客服电话
    private void getCustomerPhone() {
        PublicRequestHelper.getCustomerPhone(mActivity, new OnHttpCallBack<GetCustomerPhoneModel>() {
            @Override
            public void onSuccessful(GetCustomerPhoneModel getCustomerPhoneModel) {
                if (getCustomerPhoneModel.isSuccess()) {
                    cell = getCustomerPhoneModel.getData();
                } else {
                    AppHelper.showMsg(mActivity, getCustomerPhoneModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {
            }
        });
    }


    private void getRecommendList(int pageNum,int pageSize) {
        RecommendApI.requestData(mActivity,searchWord,pageNum,pageSize,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultsModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchResultsModel recommendModel) {
                        if (recommendModel.isSuccess()) {
                            searchProd = recommendModel.getData().getSearchProd();
                            list = recommendModel.getData().getSearchProd().getList();
                            if(pageNum==1) {
                                lists.clear();
                                lists.addAll(list);
                            }else {
                                lists.addAll(list);
                            }
                            searchReasultAdapter.notifyDataSetChanged();

                        } else {
                            AppHelper.showMsg(mActivity, recommendModel.getMessage());
                        }
                    }
                });
    }
    @Override
    public void setClickEvent() {

    }

    CouponDialog couponDialog;
    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
                LoginUtil.initRegister(getContext());
                dismiss();
            }
        };
        couponDialog.show();
    }
    AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(mActivity).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
        tv_phone.setText("客服热线 ("+cell+")");

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(mActivity);
                mDialog.dismiss();
            }
        });
    }
}
