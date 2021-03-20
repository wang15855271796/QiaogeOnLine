package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/19
 */
public class FindPhoneActivity extends BaseSwipeActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    String phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_find_phone);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)) {
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        checkPhone(phones);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    //验证手机号
    private void checkPhone(String phones) {
        LoginAPI.checkPhone(mContext,phones)
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
                            Intent intent = new Intent(mContext,PointActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            AppHelper.showMsg(mContext, baseModel.message);
                            finish();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }
}
