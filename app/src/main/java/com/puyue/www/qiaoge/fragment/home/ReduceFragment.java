package com.puyue.www.qiaoge.fragment.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.adapter.ReduceAdapter;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
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
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.iv_empty_data)
    ImageView iv_empty_data;
    int pageNum = 1;
    int pageSize = 10;
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
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            type = "commonBuy";
            getProductsList(pageNum,11,type);
        }else {
            type = "reduct";
            getProductsList(pageNum,11,type);
        }
        reduceAdapter = new ReduceAdapter(R.layout.item_team_list1, list, new ReduceAdapter.Onclick() {
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
                AppHelper.ShowAuthDialog(mActivity,SharedPreferencesUtil.getString(mActivity,"mobile"));
            }
        });
        rv_reduce.setAdapter(reduceAdapter);
        rv_reduce.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getProductsList(1,pageSize,type);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (productNormalModel.getData()!=null) {
                    if(productNormalModel.getData().isHasNextPage()) {
                        pageNum++;
                        getProductsList(pageNum, 10,type);
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
//        getCustomerPhone();
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
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
            iv_empty_data.setImageResource(R.mipmap.ic_404);
            ll_no_data.setVisibility(View.VISIBLE);
            rv_reduce.setVisibility(View.GONE);
        }else {
            iv_empty_data.setImageResource(R.mipmap.ic_no_data);
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
                                    ll_no_data.setVisibility(View.GONE);
                                    rv_reduce.setVisibility(View.VISIBLE);
                                }else {
                                    ll_no_data.setVisibility(View.VISIBLE);
                                    rv_reduce.setVisibility(View.GONE);
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

    String type = "reduct";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutsEvent event) {
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            type = "commonBuy";
        }else {
            type = "reduct";
        }

        refreshLayout.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(AddressEvent event) {
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            type = "commonBuy";
        }else {
            type = "reduct";
        }

        refreshLayout.autoRefresh();
    }

}
