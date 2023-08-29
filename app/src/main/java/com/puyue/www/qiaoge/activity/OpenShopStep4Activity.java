package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.CheckUsedModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenShopStep4Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_public_account)
    TextView tv_public_account;
    @BindView(R.id.tv_personal_account)
    TextView tv_personal_account;
    @BindView(R.id.et_open_name)
    EditText et_open_name;
    @BindView(R.id.et_open_bank)
    EditText et_open_bank;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
    @BindView(R.id.et_bank_name)
    EditText et_bank_name;
    @BindView(R.id.et_bank_account)
    EditText et_bank_account;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_login_account)
    EditText et_login_account;
    @BindView(R.id.et_login_secret)
    EditText et_login_secret;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_account_usable)
    TextView tv_account_usable;

    boolean isLoaded;
    String provinceCode;
    String cityCode;
    String siteNo;
    String bankCardNo;
    String phone;
    String userName;
    String pwd;
    String checkNo;
    String applyPhone;
    int accountType = -1;
    String accountName;
    String accountBank;

    //  省
    private List<AreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX>> options2Items = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        checkNo = getIntent().getStringExtra("checkNo");
        applyPhone = getIntent().getStringExtra("applyPhone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop_step4);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        selectCity();

        et_login_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkUsed(editable.toString());
            }
        });
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_province.setOnClickListener(this);
        tv_city_name.setOnClickListener(this);
        tv_public_account.setOnClickListener(this);
        tv_personal_account.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.tv_submit:
                accountName = et_open_name.getText().toString().trim();
                accountBank = et_open_bank.getText().toString().trim();
                cityCode = tv_city_name.getText().toString();
                siteNo = et_bank_name.getText().toString().trim();
                bankCardNo = et_bank_account.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                userName = et_login_account.getText().toString().trim();
                pwd = et_login_secret.getText().toString().trim();
                applyProviderFour();
                break;

            case R.id.tv_province:

            case R.id.tv_city_name:
                if(isLoaded) {
                    showPickerView();
                }

                break;

            case R.id.tv_public_account:
                accountType = 0;
                tv_public_account.setBackgroundResource(R.drawable.shape_orange27);
                tv_personal_account.setBackgroundResource(R.drawable.shape_grey3);
                tv_public_account.setTextColor(Color.parseColor("#FF3D03"));
                tv_personal_account.setTextColor(Color.parseColor("#414141"));
                break;

            case R.id.tv_personal_account:
                accountType = 1;
                tv_personal_account.setBackgroundResource(R.drawable.shape_orange27);
                tv_public_account.setBackgroundResource(R.drawable.shape_grey3);
                tv_personal_account.setTextColor(Color.parseColor("#FF3D03"));
                tv_public_account.setTextColor(Color.parseColor("#414141"));
                break;

        }
    }

    //申请第四步
    private void applyProviderFour() {
        RecommendApI.getFinalInfo(mContext,checkNo,applyPhone,accountType,accountName,accountBank,provinceCode,cityCode,siteNo,bankCardNo,phone
                ,userName,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetAddressModel getAddressModel) {
                        if (getAddressModel.getCode()==1) {
                            QiaoGeApplication.getInstance().exit();
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, getAddressModel.getMessage());
                        }
                    }
                });
    }

    //验证供应商账号是否可用
    private void checkUsed(String userName) {
        RecommendApI.checkUsed(mContext,checkNo,userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckUsedModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CheckUsedModel checkUsedModel) {
                        if (checkUsedModel.getCode()==1) {
                            if(checkUsedModel.getData()) {
                                tv_account_usable.setText("(此账号可用)");
                            }else {
                                tv_account_usable.setText("(此账号不可用)");
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext, checkUsedModel.getMessage());
                        }
                    }
                });
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                provinceCode = options1Items.get(options1).getCode();
                cityCode = options2Items.get(options1).get(options2).getCode();

                tv_province.setText(options1Items.get(options1).getName());
                tv_city_name.setText(options2Items.get(options1).get(options2).getName()+"");
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, null);//三级选择器
        pvOptions.show();
    }

    //选择城市
    private void selectCity() {
        AddressListAPI.getArea(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AreaModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AreaModel areaModel) {
                        if (areaModel.isSuccess()) {
                            parseData(areaModel.getData());
                        } else {
                            AppHelper.showMsg(mContext, areaModel.getMessage());
                        }
                    }
                });
    }

    private void parseData(List<AreaModel.DataBean> data) {
        options1Items = data;
        //遍历省
        for(int i = 0; i <data.size() ; i++) {
            //存放城市
            ArrayList<AreaModel.DataBean.ChildrenBeanX> cityList = new ArrayList<>();

            List<AreaModel.DataBean.ChildrenBeanX> children1 = data.get(i).getChildren();
            cityList.addAll(children1);
            options2Items.add(cityList);
        }
        isLoaded = true;
    }
}
