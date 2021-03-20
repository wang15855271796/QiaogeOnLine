package com.puyue.www.qiaoge.activity.mine.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.EditAccountOrPasswordResultActivity;
import com.puyue.www.qiaoge.api.mine.login.ChangeLoginPasswordAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.MessageEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.utils.EnCodeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/12/11(由修改登录密码进来的)
 */
public class SetLoginSecret1Activity extends BaseSwipeActivity {
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
    String verifyCode;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {


        setContentView(R.layout.activity_set_login_secret);
    }

    @Override
    public void findViewById() {
        phone = getIntent().getStringExtra("phone");
        verifyCode = getIntent().getStringExtra("verifyCode");
        ButterKnife.bind(this);
    }

    String phones;
    @Override
    public void setViewData() {
        phone = getIntent().getStringExtra("phone");

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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                                    requestChangeLoginPassword(phones,verifyCode,secret2);
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
    }

    @Override
    public void setClickEvent() {

    }


    private void requestChangeLoginPassword(String phones, String verifyCode, String secret2) {
        //在这里获取到用户手机号,用户的验证码,以及在这个界面获取到的新的登录密码,调接口将用户的登录密码修改过来
        ChangeLoginPasswordAPI.requestChangeLoginPassword(mContext, phones, verifyCode, secret2)
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
                            finish();
                            AppHelper.showMsg(mContext, baseModel.message);
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }
}
