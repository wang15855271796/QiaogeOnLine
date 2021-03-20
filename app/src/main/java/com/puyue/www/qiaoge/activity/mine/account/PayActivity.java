package com.puyue.www.qiaoge.activity.mine.account;

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
import com.puyue.www.qiaoge.activity.mine.login.SetLoginSecret1Activity;
import com.puyue.www.qiaoge.api.mine.login.CheckCommonCodeAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/18
 */
public class PayActivity extends BaseSwipeActivity {

    private ImageView mIvBack;
    private TextView mBtnNext;
    private TextView mTvTitle;
    private int mCode;
    TextView tv_yzm;
    String phone;
    EditText et_yzm;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    String et_yzms;
    TextView tv_stop;
    TextView tv_phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay);
    }

    @Override
    public void findViewById() {
        tv_stop = (TextView) findViewById(R.id.tv_stop);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        tv_yzm = (TextView) findViewById(R.id.tv_yzm);
        mIvBack = (ImageView) findViewById(R.id.iv_pp_input_phone_back);
        mBtnNext = (TextView) findViewById(R.id.tv_next);
        mTvTitle = (TextView) findViewById(R.id.tv_pp_input_phone_title);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
    }

    @Override
    public void setViewData() {
        phone = getIntent().getStringExtra("phone");
        mIvBack.setOnClickListener(noDoubleClickListener);
        mBtnNext.setOnClickListener(noDoubleClickListener);
        tv_yzm.setOnClickListener(noDoubleClickListener);
        tv_stop.setOnClickListener(noDoubleClickListener);

        tv_phone.setText(phone);
    }

    @Override
    public void setClickEvent() {

    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == mIvBack) {
                finish();
            }  else if (view == mBtnNext) {
                //校验验证码
                et_yzms = et_yzm.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    try {
//                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        checkYzm(phone,et_yzms);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }else if(view == tv_yzm) {
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
            }else if(view == tv_stop) {
                Intent intent = new Intent(mContext,LoginPayTestActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        }
    };


    private void checkYzm(String phones, String et_yzms) {
        CheckCommonCodeAPI.requestCodeRight(mContext,phones,et_yzms,6)
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
                            Intent intent = new Intent(mContext,SetPayActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

//    private void checkYzm(String phones, String et_yzms) {
//        LoginAPI.checkYzm(mContext,phones,et_yzms)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseModel baseModel) {
//                        if (baseModel.success) {
//                            Intent intent = new Intent(mContext,SetPayActivity.class);
//                            intent.putExtra("phone",phone);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
//                        }
//                    }
//                });
//    }

    /**
     * 发送验证码
     * @param phone
     */
    private void sendCode(String phone) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phone,6)
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
