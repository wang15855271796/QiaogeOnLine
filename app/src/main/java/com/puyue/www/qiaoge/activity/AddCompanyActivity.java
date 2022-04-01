package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.CompanyEvent;
import com.puyue.www.qiaoge.model.GetCompanyModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddCompanyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.et_sms)
    EditText et_sms;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.add_company_activity);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        tv_title.setText("添加企业");
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        et_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==6) {
                    getCompanyName(editable.toString());
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_sure:
                String etSms = et_sms.getText().toString();
                addCompany(etSms);
                break;
        }
    }

    /**
     * 添加公司
     * @param smsCode
     */
    private void addCompany(String smsCode) {
        IndexHomeAPI.addCompany(mActivity,smsCode)
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
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            //刷新公司列表
                            EventBus.getDefault().post(new CompanyEvent());
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 获取公司简称
     */
    private void getCompanyName(String invitationCode) {
        IndexHomeAPI.getCompanyName(mActivity,invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCompanyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCompanyModel getCompanyModel) {
                        if(getCompanyModel.getCode()==1) {
                            if(getCompanyModel.getData()!=null) {
                                tv_company.setText(getCompanyModel.getData().getCompanyName());
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,getCompanyModel.getMessage());
                        }
                    }
                });
    }


}
