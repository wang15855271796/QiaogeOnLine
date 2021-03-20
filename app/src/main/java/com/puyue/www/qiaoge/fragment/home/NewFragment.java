package com.puyue.www.qiaoge.fragment.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterMessageActivity;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
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
 * Created by ${王涛} on 2020/1/5
 */
public class NewFragment extends BaseFragment {

    private Unbinder bind;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    public int pageNum = 1;
    ProductNormalModel productNormalModel;
    View emptyView;
    CouponDialog couponDialog;
    String cell;
    //新品集合
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
    private NewAdapter newAdapter;
    public static NewFragment getInstance() {
        NewFragment fragment = new NewFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int setLayoutId() {
        return R.layout.list;
    }


    @Override
    public void initViews(View view) {

        if(!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        bind = ButterKnife.bind(this, view);
        refreshLayout.setEnableLoadMore(false);
        emptyView = View.inflate(mActivity, R.layout.layout_empty, null);
        getProductsList(pageNum,11,"new");
        newAdapter = new NewAdapter(R.layout.item_team_list, list, new NewAdapter.Onclick() {
            @Override
            public void addDialog() {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                }else {
                    initDialog();
                }
            }

            @Override
            public void tipClick() {
                showPhoneDialog(cell);
            }
        });
        newAdapter.setEmptyView(emptyView);
        recyclerView.setLayoutManager(new MyGrideLayoutManager(mActivity,2));
        recyclerView.setAdapter(newAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getProductsList(1,10,"new");
                newAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    if (productNormalModel.getData()!=null) {
                        if(productNormalModel.getData().isHasNextPage()) {
                            pageNum++;
                            getProductsList(pageNum, 10,"new");
                            refreshLayout.finishLoadMore();

                        }else {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                    }
                refreshLayout.finishLoadMore();
                }
        });
    }

    /**
     * 弹出电话号码
     */
    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(getActivity()).create();
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
                UnicornManager.inToUnicorn(getActivity());
                mDialog.dismiss();
            }
        });
    }
    /**
     * 新品列表(王涛)
     * @param
     * @param pageSize
     * @param
     */

    private void getProductsList(int pageNums, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNums, pageSize,type,null)
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
                        productNormalModel = getCommonProductModel;
                        if (getCommonProductModel.isSuccess()) {
                            newAdapter.notifyDataSetChanged();
                            if(getCommonProductModel.getData().getList().size()>0) {
                                list.addAll(getCommonProductModel.getData().getList());
                                newAdapter.notifyDataSetChanged();
                            }

                            refreshLayout.setEnableLoadMore(true);
                        }
                        else {
                            AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void findViewById(View view) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBusss(BackEvent event) {
        getCustomerPhone();
        refreshLayout.autoRefresh();
    }


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

    /**
     * 接收地址切换时的授权处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPriceType(CityEvent event) {
        refreshLayout.autoRefresh();
        getCustomerPhone();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getScrolls(TopEvent event) {
        recyclerView.smoothScrollToPosition(0);
    }
    @Override
    public void setViewData() {
        getCustomerPhone();
    }

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 提示用户去登录还是注册的弹窗
     */
    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
                startActivity(RegisterActivity.getIntent(mActivity, RegisterMessageActivity.class));
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }
}
