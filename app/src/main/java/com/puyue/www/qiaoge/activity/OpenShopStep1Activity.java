package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.BackDialog;
import com.puyue.www.qiaoge.helper.AppHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenShopStep1Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_provider_name)
    EditText et_provider_name;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_contact_phone)
    EditText et_contact_phone;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
    @BindView(R.id.et_style)
    EditText et_style;
    @BindView(R.id.tv_next)
    TextView tv_next;

    String mProvinceCode;
    String mCityCode;
    boolean isLoaded;
    String providerName;
    String address;
    String contactName;
    String contactPhone;
    String province;
    String city;
    String style;
    //  省
    private List<AreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX>> options2Items = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop_step1);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        selectCity();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_province.setOnClickListener(this);
        tv_city_name.setOnClickListener(this);
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mProvinceCode = options1Items.get(options1).getCode();
                mCityCode = options2Items.get(options1).get(options2).getCode();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                providerName = et_provider_name.getText().toString().trim();
                address = et_address.getText().toString().trim();
                contactName = et_contact_name.getText().toString().trim();
                contactPhone = et_contact_phone.getText().toString().trim();
                province = tv_province.getText().toString();
                city = tv_city_name.getText().toString();
                style = et_style.getText().toString().trim();

                if(!TextUtils.isEmpty(providerName)||!TextUtils.isEmpty(address)||!TextUtils.isEmpty(contactName)||!TextUtils.isEmpty(contactPhone)
                        ||!TextUtils.isEmpty(province)||!TextUtils.isEmpty(city)||!TextUtils.isEmpty(style)) {
                    BackDialog backDialog = new BackDialog(mActivity) {
                        @Override
                        public void Confirm() {
                            //确认退出
                            finish();
                        }

                        @Override
                        public void sure() {
                            //继续填写
                            dismiss();
                        }
                    };

                    backDialog.show();
                }else {
                    finish();
                }



                break;

            case R.id.tv_next:
                providerName = et_provider_name.getText().toString().trim();
                address = et_address.getText().toString().trim();
                contactName = et_contact_name.getText().toString().trim();
                contactPhone = et_contact_phone.getText().toString().trim();
                province = tv_province.getText().toString();
                city = tv_city_name.getText().toString();
                style = et_style.getText().toString().trim();

                if(!TextUtils.isEmpty(providerName)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(contactName)&&!TextUtils.isEmpty(contactPhone)
                        &&!TextUtils.isEmpty(province)&&!TextUtils.isEmpty(city)&&!TextUtils.isEmpty(style)) {
                    Intent intent = new Intent(mContext,OpenShopStep2Activity.class);
                    startActivity(intent);
                }

                break;

            case R.id.tv_province:

            case R.id.tv_city_name:
                if(isLoaded) {
                    showPickerView();
                }

                break;
        }
    }
}
