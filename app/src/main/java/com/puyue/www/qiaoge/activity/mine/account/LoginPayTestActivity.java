package com.puyue.www.qiaoge.activity.mine.account;

import android.content.Intent;
import android.media.Image;
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
public class LoginPayTestActivity extends BaseSwipeActivity {
    @BindView(R.id.et_pay)
    EditText et_pay;
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
        setContentView(R.layout.activity_pay_test);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");

        if(!TextUtils.isEmpty(phone)){
            try {
                phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pay = et_pay.getText().toString();
                checkPay(phones,pay);
            }
        });
    }

    @Override
    public void setViewData() {

    }

    /**
     * 修改支付密码
     */
    private void checkPay(String phones,String pay) {
        LoginAPI.checkPay(mContext,phones,pay)
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
                            //跳转到修改支付密码界面
                            Intent intent = new Intent(mContext,SetPayActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }


    @Override
    public void setClickEvent() {

    }


}
