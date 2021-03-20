package com.puyue.www.qiaoge.activity.mine.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/8.
 */
//根据传过来的参数来判断用户是在修改支付密码还是设置支付密码
//现在还需要传一个参数来判断是来修改登录密码还是修改支付密码的
public class EditAccountInputPhoneActivity extends BaseSwipeActivity {
    public static final String TYPE = "type";
    public static final String SOURCE = "source";
    private String mType;
    private String mSource;
    private ImageView mIvBack;
    private TextView mEdit;
    private TextView mBtnNext;
    private TextView mTvTitle;
    private int mCode;
    TextView tv_yzm;
    private BaseModel mModelSendCode;
    String phone;
    EditText et_yzm;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    String et_yzms;
    TextView tv_stop;
    public static Intent getIntent(Context context, Class<?> cls, String type, String source,String phone) {
        Intent intent = new Intent();
        intent.putExtra("phone",phone);
        intent.setClass(context, cls);
        intent.putExtra(TYPE, type);
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
        phone = getIntent().getStringExtra("phone");
        mType = getIntent().getStringExtra(TYPE);
        mSource = getIntent().getStringExtra(SOURCE);
        if (savedInstanceState != null) {
            mType = savedInstanceState.getString(TYPE);
            mSource = savedInstanceState.getString(SOURCE);
        }
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_pay_password_input_phone);
    }

    @Override
    public void findViewById() {
        tv_stop = (TextView) findViewById(R.id.tv_stop);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        tv_yzm = (TextView) findViewById(R.id.tv_yzm);
        mIvBack = (ImageView) findViewById(R.id.iv_pp_input_phone_back);
        mEdit = (TextView) findViewById(R.id.edit_pp_input_phone);
        mBtnNext = (TextView) findViewById(R.id.tv_next);
        mTvTitle = (TextView) findViewById(R.id.tv_pp_input_phone_title);
    }

    @Override
    public void setViewData() {
//        mBtnNext.setEnabled(false);
        if (StringHelper.notEmptyAndNull(mSource)) {
            if (mSource.equals("account")) {
//                mTvTitle.setText("修改登录账号");
                mEdit.setHint("请输入手机号");
                mCode = 4;
            } else if (mSource.equals("forget")) {
                mTvTitle.setText("忘记密码");
                mEdit.setHint("请输入手机号");
                mCode = 3;
            }
        }
    }

    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(noDoubleClickListener);
        mBtnNext.setOnClickListener(noDoubleClickListener);
        tv_yzm.setOnClickListener(noDoubleClickListener);
        tv_stop.setOnClickListener(noDoubleClickListener);

        mEdit.setText(phone);

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
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    checkYzm(phones,et_yzms);

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
                Intent intent = new Intent(mContext,LoginTestActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        }
    };

    private void checkYzm(String phones, String et_yzms) {
        LoginAPI.checkYzm(mContext,phones,et_yzms)
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
                        mModelSendCode = baseModel;
                        if (mModelSendCode.success) {
                            Intent intent = new Intent(EditAccountInputPhoneActivity.this,ChangePhoneActivity.class);
                            intent.putExtra("oldPhone",phone);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, mModelSendCode.message);
                        }
                    }
                });
    }

    /**
     * 发送验证码
     * @param phone
     */
    private void sendCode(String phone) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.changePhone(mContext,phone)
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
                            mModelSendCode = baseModel;
                            if (mModelSendCode.success) {
                                handleCountDown();
                                ToastUtil.showSuccessMsg(mContext, baseModel.message);
                            } else {
                                ToastUtil.showSuccessMsg(mContext, mModelSendCode.message);
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
