package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.ChangePhoneActivity;
import com.puyue.www.qiaoge.activity.mine.account.SetLoginSecretActivity;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.ContactDialog;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.VerifyCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/20
 */
public class MessageCheckActivity extends BaseSwipeActivity {
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.verify_code_view)
    VerifyCodeView verify_code_view;
    String phone;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_message_check);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");

        if(!TextUtils.isEmpty(getIntent().getStringExtra("phone")) ) {
            try {
                tv_desc.setText("验证码已发送至"+  phone);
                phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                requestSendCode(phones);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editContent = verify_code_view.getEditContent();
                checkMessage(editContent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSendCode(phones);
            }
        });
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }

    //验证验证码
    private void checkMessage(String password) {
        LoginAPI.checkMessage(mContext, phones,password)
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
                            Intent intent = new Intent(mContext,SetUnLoginSecretActivity.class);
                            intent.putExtra("phone",phone);
                            intent.putExtra("verifyCode",password);
                            startActivity(intent);
                            finish();
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 发送验证码
     */
    private void requestSendCode(String phone) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phone,3)
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

    boolean isSendingCode;
    CountDownTimer countDownTimer;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                tv_send.setEnabled(false);
                tv_send.setText(millisUntilFinished / 1000 + "秒后" + "重新发送验证码");

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                tv_send.setText("重新获取验证码>");
                tv_send.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
