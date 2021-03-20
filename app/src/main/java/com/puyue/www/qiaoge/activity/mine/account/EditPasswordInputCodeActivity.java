package com.puyue.www.qiaoge.activity.mine.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.SetLoginSecret1Activity;
import com.puyue.www.qiaoge.activity.mine.order.MyConfireOrdersActivity;
import com.puyue.www.qiaoge.api.mine.login.ChangeLoginPhoneAPI;
import com.puyue.www.qiaoge.api.mine.login.CheckCommonCodeAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.mine.login.ChangeLoginPhoneModel;
import com.puyue.www.qiaoge.model.mine.login.CheckPasswordCodeModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/8.
 */

public class EditPasswordInputCodeActivity extends BaseSwipeActivity implements View.OnClickListener {

    public static final String TEL = "tel";
    public static final String SOURCE = "Source";
    private ImageView mIvBack;
    private TextView mEdit;
    private TextView mBtnNext;
    private TextView mTvTitle;
    private int mCode;
    TextView tv_yzm;
    String phone;
    EditText et_yzm;
    String et_yzms;
    TextView tv_stop;
    String mTel;
    String mSource;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";

    public static Intent getIntent(Context context, Class<?> cls, String type, String tel, String source,String forgetPsw,int orderDeliveryType,double payAmount) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(TEL, tel);
        intent.putExtra(SOURCE, source);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mTel = getIntent().getStringExtra(TEL);
        mSource = getIntent().getStringExtra(SOURCE);
        return false;
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_pay_password_input_code);
    }

    @Override
    public void findViewById() {
        tv_stop = (TextView) findViewById(R.id.tv_stop);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        tv_yzm = (TextView) findViewById(R.id.tv_yzm);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEdit = (TextView) findViewById(R.id.tv_phone);
        mBtnNext = (TextView) findViewById(R.id.tv_next);
        mTvTitle = (TextView) findViewById(R.id.tv_pp_input_phone_title);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        tv_yzm.setOnClickListener(this);
        tv_stop.setOnClickListener(this);

        mEdit.setText(mTel);
    }

    String phones;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_stop:
                //直接修改登录密码界面
                Intent intent = new Intent(mContext,LoginTest1Activity.class);
                intent.putExtra("phone",mTel);
                startActivity(intent);
                finish();
                break;

            case R.id.tv_next:
                et_yzms = et_yzm.getText().toString();
                if(!TextUtils.isEmpty(mTel)){
                    try {
                        checkYzm(mTel,et_yzms);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                break;

            case R.id.tv_yzm:
                if(!TextUtils.isEmpty(mTel)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(mTel, publicKeyStr);
                        sendCode(phones);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    AppHelper.showMsg(mContext,"请填写正确手机号码");
                }
                break;
        }
    }

    private void checkYzm(String phones, String et_yzms) {
        CheckCommonCodeAPI.requestCodeRight(mContext,phones,et_yzms,3)
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
                            Intent intent = new Intent(mContext,SetLoginSecret1Activity.class);
                            intent.putExtra("phone",mTel);
                            intent.putExtra("verifyCode",et_yzms);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    private void sendCode(String phones) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phones,3)
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
