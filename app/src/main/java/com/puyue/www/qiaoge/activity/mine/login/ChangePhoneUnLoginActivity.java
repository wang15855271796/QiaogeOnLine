package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.ChangePhoneActivity;
import com.puyue.www.qiaoge.activity.mine.account.SetLoginSecretActivity;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.CheckFinishDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/30
 */
public class ChangePhoneUnLoginActivity extends BaseSwipeActivity implements View.OnClickListener {
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String phoness;
    String oldPhone;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_phone);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        tv_yzm.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        oldPhone = getIntent().getStringExtra("oldPhone");
    }

    @Override
    public void setClickEvent() {

    }
    String phone;
    String phones;
    String et_yzms;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_yzm:
                phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        sendCode(phones);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    AppHelper.showMsg(mContext,"请填写正确手机号码");
                }
                break;

            case R.id.tv_next:
                //校验验证码
                phone = et_phone.getText().toString();
                et_yzms = et_yzm.getText().toString();
                if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(oldPhone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        phoness = EnCodeUtil.encryptByPublicKey(oldPhone, publicKeyStr);
                        checkYzm(phones,phoness,et_yzms);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }

    private void checkYzm(String phones, String oldPhone,String et_yzms) {
        LoginAPI.setChnagePhone(mContext,phones,oldPhone,et_yzms)
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
                        if (baseModel.success) {
                            CheckFinishDialog checkFinishDialog = new CheckFinishDialog(mContext) {
                                @Override
                                public void Confirm() {
                                    //设置登录密码
                                    Intent intent =  new Intent(ChangePhoneUnLoginActivity.this,SetUnLoginSecret1Activity.class);
                                    intent.putExtra("phone",phone);
                                    startActivity(intent);
                                    finish();
                                }
                            };

                            checkFinishDialog.show();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 发送验证码
     * @param phones
     */
    private void sendCode(String phones) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phones,4)
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
                            if (baseModel.success) {
                                ToastUtil.showSuccessMsg(mContext, "发送验证码成功!");
                                handleCountDown();

                            } else {
                                ToastUtil.showSuccessMsg(mContext, baseModel.message);

                            }
                        }
                    });
        }
    }
    CountDownTimer countDownTimer;
    boolean isSendingCode;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
//                ll_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText("重新获取"+"("+millisUntilFinished / 1000+")" + "s");
                tv_yzm.setTextColor(Color.parseColor("#A7A7A7"));

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
//                ll_yzm.setEnabled(true);
                tv_yzm.setText("点击发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#232131"));
            }
        }.start();
    }
}
