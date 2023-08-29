package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.CheckUsedModel;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.Serializable;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LookOpenShopStep4Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_public_account)
    TextView tv_public_account;
    @BindView(R.id.tv_personal_account)
    TextView tv_personal_account;
    @BindView(R.id.tv_open_name)
    TextView tv_open_name;
    @BindView(R.id.tv_open_bank)
    TextView tv_open_bank;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_bank_account)
    TextView tv_bank_account;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_login_account)
    TextView tv_login_account;
    @BindView(R.id.tv_login_secret)
    TextView tv_login_secret;
    ApplyInfoModel.DataBean detailInfo;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        detailInfo = (ApplyInfoModel.DataBean)getIntent().getSerializableExtra("detailInfo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_open_shop_step4);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        QiaoGeApplication.getInstance().addActivity(this);

        if(detailInfo!=null) {
            if(detailInfo.getAccountType()==0) {
                //对公
                tv_public_account.setBackgroundResource(R.drawable.shape_orange27);
                tv_personal_account.setBackgroundResource(R.drawable.shape_grey3);
                tv_public_account.setTextColor(Color.parseColor("#FF3D03"));
                tv_personal_account.setTextColor(Color.parseColor("#414141"));
            }else {
                //个人
                tv_personal_account.setBackgroundResource(R.drawable.shape_orange27);
                tv_public_account.setBackgroundResource(R.drawable.shape_grey3);
                tv_personal_account.setTextColor(Color.parseColor("#FF3D03"));
                tv_public_account.setTextColor(Color.parseColor("#414141"));
            }

            tv_open_name.setText(detailInfo.getAccountName());
            tv_open_bank.setText(detailInfo.getAccountBank());
            tv_province.setText(detailInfo.getProvinceName());
            tv_city.setText(detailInfo.getCityName());
            tv_bank_name.setText(detailInfo.getSiteNo());
            tv_bank_account.setText(detailInfo.getBankCardNo());
            tv_phone.setText(detailInfo.getPhone());
            tv_login_account.setText(detailInfo.getUserName());
            tv_login_secret.setText(detailInfo.getPwd());
        }

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.tv_back:
                QiaoGeApplication.getInstance().exit();
                break;
        }
    }
}
