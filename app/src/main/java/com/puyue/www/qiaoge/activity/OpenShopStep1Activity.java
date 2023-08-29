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
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.BackDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

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
    @BindView(R.id.tv_area)
    TextView tv_area;
    String companyProvinceCode;
    String companyCityCode;
    String companyAreaCode;
    boolean isLoaded;
    String supplierName;
    String address;
    String contactName;
    String contactPhone;
    String province;
    String city;
    String checkNo;
    String applyPhone;
    String supplyProdType;
    //  省
    private List<AreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean>>> options3Items = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        applyPhone = getIntent().getStringExtra("applyPhone");
        checkNo = getIntent().getStringExtra("checkNo");
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
        tv_area.setOnClickListener(this);
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                companyProvinceCode = options1Items.get(options1).getCode();
                companyCityCode = options2Items.get(options1).get(options2).getCode();
                companyAreaCode = options3Items.get(options1).get(options2).get(options3).getCode();
                String tx = options3Items.get(options1).get(options2).get(options3).getName();
                tv_province.setText(options1Items.get(options1).getName());
                tv_city_name.setText(options2Items.get(options1).get(options2).getName()+"");
                tv_area.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
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
    //     遍历省
        for(int i = 0; i <data.size() ; i++) {
//         存放城市
            ArrayList<AreaModel.DataBean.ChildrenBeanX> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean>> province_AreaList = new ArrayList<>();
            List<AreaModel.DataBean.ChildrenBeanX> children1 = data.get(i).getChildren();
            cityList.addAll(children1);
//         遍历市
            for(int c = 0; c <data.get(i).getChildren().size() ; c++) {
                //该城市的所有地区列表
                ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean> city_AreaList = new ArrayList<>();

                if (data.get(i).getChildren().get(c).getChildren() == null || data.get(i).getChildren().get(c).getChildren().size() == 0) {
                    AreaModel.DataBean.ChildrenBeanX.ChildrenBean childrenBean = new AreaModel.DataBean.ChildrenBeanX.ChildrenBean();
                    childrenBean.setName("");
                    city_AreaList.add(childrenBean);
                } else {

                    List<AreaModel.DataBean.ChildrenBeanX.ChildrenBean> children = data.get(i).getChildren().get(c).getChildren();
                    city_AreaList.addAll(children);
                    province_AreaList.add(city_AreaList);

                }
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);

        }

        isLoaded = true;


    }

//    申请第一步
    private void applyProviderOne() {
        RecommendApI.applyProviderOne(mContext,checkNo,applyPhone,supplierName,address,contactName,contactPhone,companyProvinceCode
                ,companyCityCode,companyAreaCode,supplyProdType)
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
                            if(getAddressModel.getData()!=null) {
                                Intent intent = new Intent(mContext,OpenShopStep2Activity.class);
                                intent.putExtra("checkNo",getAddressModel.getData());
                                intent.putExtra("applyPhone",applyPhone);
                                startActivity(intent);

                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, getAddressModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
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
//                supplierName = et_provider_name.getText().toString().trim();
//                address = et_address.getText().toString().trim();
//                contactName = et_contact_name.getText().toString().trim();
//                contactPhone = et_contact_phone.getText().toString().trim();
//                province = tv_province.getText().toString();
//                city = tv_city_name.getText().toString();
//                supplyProdType = et_style.getText().toString().trim();

//                if(!TextUtils.isEmpty(supplierName)||!TextUtils.isEmpty(address)||!TextUtils.isEmpty(contactName)||!TextUtils.isEmpty(contactPhone)
//                        ||!TextUtils.isEmpty(province)||!TextUtils.isEmpty(city)||!TextUtils.isEmpty(supplyProdType)) {
//                    BackDialog backDialog = new BackDialog(mActivity) {
//                        @Override
//                        public void Confirm() {
//                            //确认退出
//                            finish();
//                        }
//
//                        @Override
//                        public void sure() {
//                            //继续填写
//                            dismiss();
//                        }
//                    };
//
//                    backDialog.show();
//                }else {
//                    finish();
//                }



                break;

            case R.id.tv_next:
                supplierName = et_provider_name.getText().toString().trim();
                address = et_address.getText().toString().trim();
                contactName = et_contact_name.getText().toString().trim();
                contactPhone = et_contact_phone.getText().toString().trim();
                province = tv_province.getText().toString();
                city = tv_city_name.getText().toString();
                supplyProdType = et_style.getText().toString().trim();

//                if(!TextUtils.isEmpty(supplierName)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(contactName)&&!TextUtils.isEmpty(contactPhone)
//                        &&!TextUtils.isEmpty(province)&&!TextUtils.isEmpty(city)&&!TextUtils.isEmpty(supplyProdType)) {
//                    Intent intent = new Intent(mContext,OpenShopStep2Activity.class);
//
//                }
                applyProviderOne();

                QiaoGeApplication.getInstance().addActivity(this);
                break;

            case R.id.tv_province:

            case R.id.tv_city_name:

            case R.id.tv_area:
                if(isLoaded) {
                    showPickerView();
                }

                break;
        }
    }
}
