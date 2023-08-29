package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.SetPayActivity;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.login.CheckCommonCodeAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.home.ResetPwdModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProviderWareHouseActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.rl_yzm)
    RelativeLayout rl_yzm;
    @BindView(R.id.tv_next)
    TextView tv_next;

    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";


    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_provider_warehouse);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_yzm.setOnClickListener(this);
    }

    String phone;
    String yzm;
    String phones;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_yzm:
                phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        sendMsg(phones);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    AppHelper.showMsg(mContext,"请填写正确手机号码");
                }
                break;

            case R.id.tv_next:
                yzm = et_yzm.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                checkYzm(phone,yzm);
                break;
        }
    }

    //检验验证码
    private void checkYzm(String phone, String et_yzms) {
        CheckCommonCodeAPI.requestCodeRight(mContext,phone,et_yzms,11)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResetPwdModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResetPwdModel baseModel) {
                        if(baseModel.getCode()==1) {
                            isApplyProvider();
                        }else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.getMessage());
                        }
                    }
                });
    }

    //是否申请过
    private void isApplyProvider() {
        RecommendApI.isApplyProvider(mContext,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsApplyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsApplyModel isApplyModel) {
                        if (isApplyModel.getCode()==1) {
                            Intent intent;
                            if(isApplyModel.getData()!=null) {
                                //申请过
                                intent = new Intent(mContext, OpenShopListActivity.class);
                                intent.putExtra("applyPhone",phone);
                                intent.putExtra("applyData", (Serializable) isApplyModel.getData());
                            }else {
                                //未申请过
                                intent = new Intent(mContext, StartProviderActivity.class);
                                intent.putExtra("applyPhone",phone);
                                finish();
                            }
                            startActivity(intent);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, isApplyModel.getMessage());
                        }
                    }
                });
    }

    //发送验证码
    public void sendMsg(String phones) {
        SendCodeAPI.requestSendCode(mContext,phones,11)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code == 1) {
                            ToastUtil.showSuccessMsg(mContext, "发送验证码成功!");
                            handleCountDown();

                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }
    boolean isSendingCode;
    CountDownTimer countDownTimer;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                rl_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后" + "重新获取");
                tv_yzm.setTextColor(Color.parseColor("#A7A7A7"));
                tv_yzm.setBackgroundResource(R.drawable.shape_grey3);

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                rl_yzm.setEnabled(true);
                tv_yzm.setText("发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#FF3E20"));
            }
        }.start();
    }

}
