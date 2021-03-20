package com.puyue.www.qiaoge.fragment.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.CartActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterMessageActivity;
import com.puyue.www.qiaoge.adapter.ReduceAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.UpDateNumEvent5;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
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
 * Created by ${王涛} on 2021/2/22
 */
public class ReductionFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    ReduceAdapter reduceAdapter;
    int pageNum = 1;
    int pageSize = 10;
    ProductNormalModel productNormalModel;
    String flag = "reduce";
    String enjoyProduct;
    String cell;
    //降价集合
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();

    public static ReductionFragment getInstance() {
        ReductionFragment fragment = new ReductionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)

    @Override
    public int setLayoutId() {
        return R.layout.list;
    }

    @Override
    public void initViews(View view) {

    }



    /**
     * 弹出电话号码
     */
    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;



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
                        productNormalModel = getCommonProductModel;
                        if (getCommonProductModel.isSuccess()) {
                            reduceAdapter.notifyDataSetChanged();
                            if(getCommonProductModel.getData().getList().size()>0) {
                                list.addAll(getCommonProductModel.getData().getList());
                                reduceAdapter.notifyDataSetChanged();

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
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        getCustomerPhone();
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
                showPhoneDialog(cell);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        refreshLayout.setEnableLoadMore(false);
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
    public void getCommon(BackEvent event) {
        refreshLayout.autoRefresh();

    }

    @Override
    public void setViewData() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void setClickEvent() {

    }

    /**
     * 弹出电话号码
     */

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

    /**
     * @param
     */

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

}
