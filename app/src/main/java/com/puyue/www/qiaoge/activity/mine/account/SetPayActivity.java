package com.puyue.www.qiaoge.activity.mine.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.mine.LogoutAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.SecretSuccessDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.EnCodeUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/19
 */
public class SetPayActivity extends BaseSwipeActivity {
    @BindView(R.id.et_login)
    EditText et_login;
    @BindView(R.id.et_login_sure)
    EditText et_login_sure;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.iv_close_sure)
    ImageView iv_close_sure;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    boolean showPassword = true;
    boolean showPassword1 = true;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set_pay);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    String phones;
    @Override
    public void setViewData() {
        phone = getIntent().getStringExtra("phone");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String secret1 = et_login.getText().toString();
                String secret2 = et_login_sure.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(secret1 !=null && secret2 !=null) {
                        if(secret1.equals(secret2)) {
                            if(secret1.length()>=6&& secret2.length()>=6) {
                                if (StringHelper.isLetterDigit(et_login_sure.getText().toString())) {
                                    setSecret(phones,secret2);
                                } else {
                                    AppHelper.showMsg(mContext, "密码由6-16位数字与字母组成");
                                }
                            } else {
                                AppHelper.showMsg(mContext, "密码位数不足!");
                            }
                        }else {
                            AppHelper.showMsg(mContext, "两次密码不一致!");
                        }
                    }else {
                        AppHelper.showMsg(mContext, "密码不能为空");
                    }


                }

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassword) {// 显示密码
                    iv_close.setImageResource(R.mipmap.ic_login_display);
                    et_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_login.setSelection(et_login.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码

                    iv_close.setImageResource(R.mipmap.ic_login_hide);
                    et_login.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_login.setSelection(et_login.getText().toString().length());
                    showPassword = !showPassword;
                }
            }
        });

        iv_close_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassword1) {// 显示密码
                    iv_close_sure.setImageResource(R.mipmap.ic_login_display);
                    et_login_sure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_login_sure.setSelection(et_login_sure.getText().toString().length());
                    showPassword1 = !showPassword1;
                } else {// 隐藏密码

                    iv_close_sure.setImageResource(R.mipmap.ic_login_hide);
                    et_login_sure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_login_sure.setSelection(et_login_sure.getText().toString().length());
                    showPassword1 = !showPassword1;
                }
            }
        });

    }

    @Override
    public void setClickEvent() {

    }

    public static boolean ispsd(String psd) {
        Pattern p = Pattern
                .compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(psd);

        return m.matches();
    }

    //修改支付密码
    private void setSecret(String phones, String secret2) {
        LoginAPI.modifyPay(mContext,phones,secret2)
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
//                            SecretSuccessDialog secretSuccessDialog = new SecretSuccessDialog(mContext) {
//                                @Override
//                                public void Confirm() {
//                                    requestLogout();
//                                    dismiss();
//                                }
//
//                                @Override
//                                public void Close() {
//                                    requestLogout();
//                                    dismiss();
//                                }
//                            };
//                            secretSuccessDialog.show();
                            AppHelper.showMsg(mContext, baseModel.message);
                            finish();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    private void requestLogout() {
        LogoutAPI.requestLogout(mContext)
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
                            logouts(mContext, -10000);
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    private void logouts(Context context, int mStateCode) {
        if (mStateCode == -10000 || mStateCode == -10001) {
            UserInfoHelper.saveUserId(context, "");
            UserInfoHelper.saveUserType(context, "");
            UserInfoHelper.saveUserHomeRefresh(context, "");
            UserInfoHelper.saveUserMarketRefresh(context, "");
            UserInfoHelper.saveChangeFlag(mActivity, "0");
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
