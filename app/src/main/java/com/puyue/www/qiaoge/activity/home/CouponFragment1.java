package com.puyue.www.qiaoge.activity.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.api.home.TeamActiveQueryAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.TeamActiveQueryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2019/12/25
 */
public class CouponFragment1 extends BaseFragment {
    private Unbinder bind;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    TeamActiveQueryModel teamActiveQueryModels;
    //折扣集合
    List<TeamActiveQueryModel.DataBean> couponList = new ArrayList<>();
    private Coupon1Adapter coupon1Adapter;
    String cell;
    public static CouponFragment1 getInstance() {
        CouponFragment1 fragment = new CouponFragment1();
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_team;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCustomerPhone();
    }

    @Override
    public void initViews(View view) {

        bind = ButterKnife.bind(this, view);
        getCustomerPhone();
        coupon1Adapter = new Coupon1Adapter(R.layout.item_coupons_list, couponList, new Coupon1Adapter.Onclick() {
            @Override
            public void addDialog() {
                AppHelper.ShowAuthDialog(mActivity,cell);
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        recycleView.setAdapter(coupon1Adapter);
        getCouponList();

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
                UnicornManager.inToUnicorn(getContext());
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

    /**
     * 获取折扣列表
     */
    private void getCouponList() {
        TeamActiveQueryAPI.requestData(mActivity,11+"",1+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TeamActiveQueryModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TeamActiveQueryModel teamActiveQueryModel) {
                        teamActiveQueryModels = teamActiveQueryModel;
                        if (teamActiveQueryModel.isSuccess()) {
                            couponList.clear();
                            if (teamActiveQueryModel.getData() != null) {
                                couponList.addAll(teamActiveQueryModel.getData());
                                coupon1Adapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
