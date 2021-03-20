package com.puyue.www.qiaoge.activity.mine.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.SecretSuccessDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/18
 */
public class LoginTestActivity extends BaseSwipeActivity {
    @BindView(R.id.et_secret)
    EditText et_secret;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String phone;
    String phones;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login_test);
    }

    @Override
    public void findViewById() {

        ButterKnife.bind(this);
    }
    String secret;
    @Override
    public void setViewData() {
        phone = getIntent().getStringExtra("phone");
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secret = et_secret.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        checkSecret();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void checkSecret() {
        LoginAPI.checkSecret(mContext,phones,secret)
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
                            Intent intent = new Intent(mContext,ChangePhoneActivity.class);
                            intent.putExtra("oldPhone",phone);
                            startActivity(intent);
                            finish();
                            ToastUtil.showSuccessMsg(mContext,"验证成功");
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    @Override
    public void setClickEvent() {

    }
}
