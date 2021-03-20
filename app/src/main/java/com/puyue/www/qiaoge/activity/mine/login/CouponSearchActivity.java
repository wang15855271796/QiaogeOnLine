package com.puyue.www.qiaoge.activity.mine.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.CartActivity;
import com.puyue.www.qiaoge.adapter.CouponSearchAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/23
 */
public class CouponSearchActivity extends BaseSwipeActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_coupon_title)
    TextView tv_coupon_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    @BindView(R.id.tv_num)
    TextView tv_num;
    public int pageNum = 1;
    ProductNormalModel productNormalModel;
    String cell;
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
    CouponSearchAdapter couponSearchAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coupon_search);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);

        couponSearchAdapter = new CouponSearchAdapter(R.layout.item_noresult_recommend, list, new CouponSearchAdapter.Onclick() {
            @Override
            public void addDialog() {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                }else {
                    initDialog();
                }
            }

            @Override
            public void getPrice() {
                showPhoneDialog(cell);
            }

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(couponSearchAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getLists(1,10,giftDetailNo);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (productNormalModel.getData()!=null) {
                    if(productNormalModel.getData().isHasNextPage()) {
                        pageNum++;
                        getLists(pageNum, 10,giftDetailNo);
                        refreshLayout.finishLoadMore();

                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                refreshLayout.finishLoadMore();
            }
        });
    }
    String giftDetailNo;
    String giftName;
    @Override
    public void setViewData() {
        giftDetailNo = getIntent().getStringExtra("giftDetailNo");
        giftName = getIntent().getStringExtra("giftName");
        tv_coupon_title.setText("限时促销：以下商品可使用"+giftName);
        getLists(pageNum,10,giftDetailNo);
        getCartNum();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CartActivity.class));
            }
        });
    }

    @Override
    public void setClickEvent() {

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


    private void getLists(int pageNum, int pageSize, String giftDetailNo) {

        ProductListAPI.getLists(mActivity, pageNum, pageSize,giftDetailNo)
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
                            couponSearchAdapter.notifyDataSetChanged();
                            if(getCommonProductModel.getData().getList().size()>0) {
                                list.addAll(getCommonProductModel.getData().getList());
                                couponSearchAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());
                        }
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
        mDialog = new AlertDialog.Builder(mContext).create();
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
                UnicornManager.inToUnicorn(mContext);
                mDialog.dismiss();
            }
        });
    }

    /**
     * 提示用户去登录还是注册的弹窗
     */
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
                startActivity(RegisterActivity.getIntent(mActivity, RegisterMessageActivity.class));
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }
}
