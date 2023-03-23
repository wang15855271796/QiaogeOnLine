package com.puyue.www.qiaoge.activity;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.home.SchoolVideoApi;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.ScAddressEvent;
import com.puyue.www.qiaoge.event.UpdateAddressEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AskActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_choose_address)
    RelativeLayout rl_choose_address;
    @BindView(R.id.tv_edit_address)
    TextView tv_edit_address;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.edit_message)
    EditText edit_message;
    @BindView(R.id.edit_address)
    EditText edit_address;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.ask_activity);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        rl_choose_address.setOnClickListener(this);
        tv_edit_address.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);

        if(UserInfoHelper.getUserId(mActivity)!=null && !UserInfoHelper.getUserId(mActivity).equals("")) {
            rl_choose_address.setVisibility(View.VISIBLE);
        }else {
            rl_choose_address.setVisibility(View.GONE);
        }
        selectCity();
    }

    public String mProvinceCode;
    public String mCityCode;
    public String mAreaCode;
    String provinceName = "";
    String cityName = "";
    String areaName = "";
    //  省
    private List<AreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean>>> options3Items = new ArrayList<>();
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mProvinceCode = options1Items.get(options1).getCode();
                mCityCode = options2Items.get(options1).get(options2).getCode();
                mAreaCode = options3Items.get(options1).get(options2).get(options3).getCode();
                provinceName = options1Items.get(options1).getName();
                cityName = options2Items.get(options1).get(options2).getName();
                areaName = options3Items.get(options1).get(options2).get(options3).getName();
                String tx = provinceName + cityName+areaName;
                tv_edit_address.setText(tx);
                tv_edit_address.setTextColor(Color.parseColor("#333333"));
//                clickFlag = false;
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

    private void getSchoolVideoList() {
        SchoolVideoApi.getVideoAsk(mActivity,editName,editPhone,provinceName,cityName,areaName,detailAddress,memo)
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
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    private void parseData(List<AreaModel.DataBean> data) {
        options1Items = data;
////     遍历省
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

//        isLoaded = true;
    }

    //选择地址
    AddressModel.DataBean dataBean;
    String detailAddress = "";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAddress(ScAddressEvent event) {
        dataBean = event.getDataBean();
        mAreaCode = dataBean.getAreaCode();
        mProvinceCode = dataBean.getProvinceCode();
        mCityCode = dataBean.getCityCode();
        areaName = dataBean.getAreaName();
        cityName = dataBean.getCityName();
        provinceName = dataBean.getProvinceName();
        detailAddress = dataBean.getDetailAddress();
        tv_edit_address.setText(provinceName+cityName+areaName);
        edit_address.setText(detailAddress);
        edit_name.setText(dataBean.getUserName());
        edit_phone.setText(dataBean.getContactPhone());

        tv_address.setText(provinceName+cityName+areaName+detailAddress);
    }


    String editName ="";
    String editPhone ="";
    String memo ="";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_address:
                Intent intent = new Intent(mActivity,ChSchoolAddActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_edit_address:
                hintKbTwo();
                if(UserInfoHelper.getUserId(mActivity)!=null && !UserInfoHelper.getUserId(mActivity).equals("")) {
                    showPickerView();
                }else {
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                }

                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_ok:
                editName = edit_name.getText().toString();
                editPhone = edit_phone.getText().toString();
                memo = edit_message.getText().toString();
                if(editName!= null && editPhone!= null &&editName.equals("")&&editPhone.equals("")) {
                    ToastUtil.showSuccessMsg(mContext,"请填写收货人和联系电话");
                    return;
                }
                getSchoolVideoList();
                break;


        }
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setClickEvent() {

    }

}
