package com.puyue.www.qiaoge.activity;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.home.SchoolVideoApi;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FrontWareHouseActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_front_warehouse);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    private void applyWareHouse() {
        SchoolVideoApi.applyWareHouse(mActivity,contactName,contactPhone,cityName,companyType)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<BaseModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            finish();
                        }
                        ToastUtil.showSuccessMsg(mContext,baseModel.message);
                    }
                });
    }

    String contactName;
    String contactPhone;
    String cityName;
    int companyType = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_submit:
                if(TextUtils.isEmpty(et_name.getText().toString().trim()) || TextUtils.isEmpty(et_phone.getText().toString().trim())
                        ||TextUtils.isEmpty(et_city.getText().toString().trim())) {
                    ToastUtil.showSuccessMsg(mContext,"以上信息需必填");
                    return;
                }
                contactName = et_name.getText().toString().trim();
                contactPhone = et_phone.getText().toString().trim();
                cityName = et_city.getText().toString().trim();
                applyWareHouse();
                break;
        }
    }
}
