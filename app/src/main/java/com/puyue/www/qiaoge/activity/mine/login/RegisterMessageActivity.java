package com.puyue.www.qiaoge.activity.mine.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.api.mine.login.RegisterAgreementAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.CouponImageDialog;
import com.puyue.www.qiaoge.dialog.PricacyDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.model.mine.login.RegisterAgreementModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.StatusUtils;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2019/11/23
 */
public class RegisterMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.ll_yzm)
    RelativeLayout ll_yzm;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.cb_register)
    CheckBox cb_register;
    @BindView(R.id.tv_register_agreement)
    TextView tv_register_agreement;
    @BindView(R.id.tv_register_secret)
    TextView tv_register_secret;
    private BaseModel mModelSendCode;
    private CountDownTimer countDownTimer;
    private boolean isSendingCode = true;
    private String phone;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    private String phones;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_message_register);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        ll_yzm.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        tv_register_secret.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_secret.getPaint().setAntiAlias(true);//抗锯齿
        tv_register_agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_agreement.getPaint().setAntiAlias(true);//抗锯齿
        tv_register_agreement.setOnClickListener(this);
        tv_register_secret.setOnClickListener(this);
        requestRegisterAgreement();
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
    /**
     * 注册协议接口
     */
    RegisterAgreementModel mModelRegisterAgreement;
    private void requestRegisterAgreement() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            RegisterAgreementAPI.requestRegisterAgreement(mContext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RegisterAgreementModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RegisterAgreementModel registerAgreementModel) {
                            mModelRegisterAgreement = registerAgreementModel;
                            if (mModelRegisterAgreement.success) {
                                mUrlAgreement = mModelRegisterAgreement.data;
                            } else {
                                AppHelper.showMsg(mContext, mModelRegisterAgreement.message);
                            }
                        }
                    });
        }
    }
    /**
     * 验证手机号码
     * @param username
     * @return
     */
    private int checkPhoneNum(String username){
        if (TextUtils.isEmpty(username)){
            return 2;
        }else if (!username.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }

    private String mUrlAgreement;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_register_secret:
                String url = "https://shaokao.qoger.com/apph5/html/yszc.html";
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", 1);
                intent.putExtra("name", "协议");
                startActivity(intent);
                break;


            case R.id.tv_register_agreement:
                startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, mUrlAgreement));
                break;

            case R.id.iv_back:
                CouponImageDialog couponImageDialog = new CouponImageDialog(mContext) {
                    @Override
                    public void Confirm() {
                        dismiss();
                        finish();
                    }
                };
                couponImageDialog.show();

                break;

            case R.id.ll_yzm:
                phone = et_phone.getText().toString();
                try {
                    phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int result = checkPhoneNum(phone);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    requestSendCode(phones);
                }

                break;

            case R.id.tv_next:
                String yzms = et_yzm.getText().toString();
                String phones = et_phone.getText().toString();
                hintKbTwo();
//                if(!cb_register.isChecked()) {
//                    ToastUtil.showSuccessMsg(mContext,"请勾选协议");
//                    return;
//                }
                if(yzms.isEmpty()) {
                    ToastUtil.showSuccessMsg(mContext,"请填写验证码");
                    return;
                }

                if(phones.isEmpty()) {
                    ToastUtil.showSuccessMsg(mContext,"请填写号码");
                    return;
                }

                if(!cb_register.isChecked()) {
                    PricacyDialog pricacyDialog = new PricacyDialog(mContext) {
                        @Override
                        public void Confirm() {
                            cb_register.setChecked(true);
                            dismiss();
                        }
                    };
                    pricacyDialog.show();
                    return;
                }

                getCode(phones,yzms);
                break;
        }
    }

    public void getCode(String phones,String yzms) {
        SendCodeAPI.requestSendCodes(mContext,phones,yzms)
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
                            Intent intent = new Intent(mContext,RegisterStep1Activity.class);
                            intent.putExtra("isDirect",0);
                            intent.putExtra("phone",phones);
                            intent.putExtra("yzm",yzms);
                            startActivity(intent);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, mModelSendCode.message);
                        }
                    }
                });
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    /**
     * 发送验证码
     */
    private void requestSendCode(String phone) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phone,2)
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
                                ToastUtil.showSuccessMsg(mContext, "发送验证码成功!");
                                handleCountDown();

                            } else {
                                ToastUtil.showSuccessMsg(mContext, mModelSendCode.message);

                            }
                        }
                    });
        }
    }

    /**
     * 倒计时
     */
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                ll_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后重新发送");
                tv_yzm.setTextColor(Color.parseColor("#949494"));

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                ll_yzm.setEnabled(true);
                tv_yzm.setText("点击发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#FF3E20"));
            }
        }.start();
    }

}
