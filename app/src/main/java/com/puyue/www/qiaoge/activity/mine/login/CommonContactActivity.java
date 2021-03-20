package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.ChangePhoneActivity;
import com.puyue.www.qiaoge.activity.mine.account.SetLoginSecretActivity;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.ContactDialog;
import com.puyue.www.qiaoge.model.AddressMessageModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/20
 */
public class CommonContactActivity extends BaseSwipeActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String id;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    String phone;
    String phoness;
    String name;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_common_contact);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    String phonesss;
    @Override
    public void setViewData() {
        id = getIntent().getStringExtra("id");
        phoness = getIntent().getStringExtra("phone");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(phoness)) {
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                        phonesss = EnCodeUtil.encryptByPublicKey(phoness, publicKeyStr);
                        checkAdddress(phones,phonesss,name,id);
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

    //验证收货人
    private void checkAdddress(String phones,String phonesss, String name, String id) {
        LoginAPI.checkContact(mContext,name,id,phones,phonesss)
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
//                            Intent intent = new Intent(mContext,CommonContactActivity.class);
//                            startActivity(intent);
                            ContactDialog contactDialog = new ContactDialog(mContext) {
                                @Override
                                public void confirmNo() {
                                    Intent intent = new Intent(mContext,SetUnLoginSecret1Activity.class);
                                    intent.putExtra("phone",phoness);
                                    startActivity(intent);
                                    finish();
                                    dismiss();
                                }

                                @Override
                                public void confirmYes() {
                                    Intent intent = new Intent(mContext,ChangePhoneUnLoginActivity.class);
                                    intent.putExtra("oldPhone",phoness);
                                    startActivity(intent);
                                    finish();
                                    dismiss();
                                }
                            };
                            contactDialog.show();
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }
}
