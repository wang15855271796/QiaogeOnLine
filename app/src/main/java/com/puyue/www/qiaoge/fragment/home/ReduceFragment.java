package com.puyue.www.qiaoge.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.ReduceAdapter;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/27
 */
public class ReduceFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.rv_reduce)
    RecyclerView rv_reduce;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;
    String enjoyProduct;
    int pageNum = 1;
    int pageSize = 10;
    View emptyView;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_reduce;
    }
    public static ReduceFragment getInstance() {
        ReduceFragment fragment = new ReduceFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void initViews(View view) {
        if(!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void setViewData() {
        refreshLayout.setEnableLoadMore(false);
        rv_reduce.setLayoutManager(new MyGrideLayoutManager(mActivity,2));
        enjoyProduct = SharedPreferencesUtil.getString(mActivity, "priceType");
        reduceAdapter = new ReduceAdapter(enjoyProduct,R.layout.item_team_list, list, new ReduceAdapter.Onclick() {
            @Override
            public void addDialog() {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                }else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    mActivity.startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }

            }

            @Override
            public void tipClick() {
//                showPhoneDialog(cell);
            }
        });
        rv_reduce.setAdapter(reduceAdapter);
        rv_reduce.setLayoutManager(new GridLayoutManager(mActivity,2));
        getProductsList(1,10,"reduct");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getProductsList(1,pageSize,"reduct");
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (productNormalModel.getData()!=null) {
                    if(productNormalModel.getData().isHasNextPage()) {
                        pageNum++;
                        getProductsList(pageNum, 10,"reduct");
                        refreshLayout.finishLoadMore();


                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();

                    }
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getScrolls(TopEvent event) {
        rv_reduce.smoothScrollToPosition(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(BackEvent event) {
        getCustomerPhone();
        refreshLayout.autoRefresh();
    }
    String cell;
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


    @Override
    public void setClickEvent() {

    }

    /**
     * 降价(王涛)
     * @param pageNum
     * @param pageSize
     * @param
     */
    ProductNormalModel productNormalModel;
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();

    ReduceAdapter reduceAdapter;
    private void getProductsList(int pageNum, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNum, pageSize,type,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductNormalModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductNormalModel getCommonProductModel) {
                        if (getCommonProductModel.isSuccess()) {
                            productNormalModel = getCommonProductModel;
                            if (getCommonProductModel.getData().getList()!=null) {
                                list.addAll(getCommonProductModel.getData().getList());
                                reduceAdapter.notifyDataSetChanged();
                                List<ProductNormalModel.DataBean.ListBean> list = getCommonProductModel.getData().getList();
                                if (pageNum == 1) {
                                    reduceAdapter.setNewData(list);
                                } else {
                                    reduceAdapter.addData(list);
                                }
                            }

                            if(getCommonProductModel.getData().getList().size()>0) {
                                rl_empty.setVisibility(View.GONE);
                            }else {
                                rl_empty.setVisibility(View.VISIBLE);
                            }
                            //判断是否有下一页
                            if (!getCommonProductModel.getData().isHasNextPage()) {
                                reduceAdapter.loadMoreEnd(false);
                            } else {
                                reduceAdapter.loadMoreComplete();
                            }
                            refreshLayout.setEnableLoadMore(true);
                        } else {
                            AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());

                        }
                    }
                });
    }

}
