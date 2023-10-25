package com.puyue.www.qiaoge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LookOpenShopStep1Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_provider_name)
    TextView tv_provider_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_contact_name)
    TextView tv_contact_name;
    @BindView(R.id.tv_contact_phone)
    TextView tv_contact_phone;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_style)
    TextView tv_style;
    IsApplyModel.DataBean applyData;
    ApplyInfoModel.DataBean detailInfo;
    String applyPhone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        applyData = (IsApplyModel.DataBean) getIntent().getSerializableExtra("applyData");
        applyPhone = getIntent().getStringExtra("applyPhone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_open_shop_step1);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        getDetailInfo();
        QiaoGeApplication.getInstance().addActivity(this);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    private void getDetailInfo() {
        RecommendApI.getApplyInfo(mContext,applyData.getCheckNo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApplyInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApplyInfoModel applyInfoModel) {
                        if (applyInfoModel.getCode()==1) {
                            if(applyInfoModel.getData()!=null) {

                                setContent(applyInfoModel.getData());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, applyInfoModel.getMessage());
                        }
                    }
                });
    }

    private void setContent(ApplyInfoModel.DataBean data) {
        detailInfo = data;
        tv_provider_name.setText(data.getSupplierName());
        tv_address.setText(data.getAddress());
        tv_contact_name.setText(data.getContactName());
        tv_contact_phone.setText(data.getContactPhone());
        tv_province.setText(data.getCompanyProvinceName());
        tv_city.setText(data.getCompanyCityName());
        tv_area.setText(data.getCompanyAreaName());
        tv_style.setText(data.getSupplyProdType());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_next:
                Intent intent = new Intent(mContext,LookOpenShopStep2Activity.class);
                intent.putExtra("detailInfo", (Serializable) detailInfo);
                intent.putExtra("applyPhone",applyPhone);
                startActivity(intent);
                break;
        }
    }
}
