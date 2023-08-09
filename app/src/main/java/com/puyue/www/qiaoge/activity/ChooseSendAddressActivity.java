package com.puyue.www.qiaoge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.ChangeCityActivity;
import com.puyue.www.qiaoge.activity.mine.account.EditAndAddActivity;
import com.puyue.www.qiaoge.adapter.ChooseAddresssAdapter;
import com.puyue.www.qiaoge.adapter.ChooseAddressssAdapter;
import com.puyue.www.qiaoge.adapter.ChooseSendAddresssAdapter;
import com.puyue.www.qiaoge.adapter.ChooseSendAddressssAdapter;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.DefaultAddressAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.ChangeCityDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseSendAddressActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_add)
    TextView tv_add;
    List<AddressModel.DataBean> data0 = new ArrayList<>();
    List<AddressModel.DataBean> data1 = new ArrayList<>();
    private List<AddressModel.DataBean> mListData = new ArrayList<>();
    public static final String TYPE = "type";//是来编辑地址,还是来新建地址的
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";
    public static final String STORE_NAME = "store_name";
    public static final String AREA = "area";//三级城市
    public static final String ADDRESS = "address";//详细地址
    public static final String DEFAULT = "default";//是否是默认地址
    public static final String ADDRESS_ID = "address_id";//地址id
    public static final String PROVINCE_CODE = "province_code";//省ID
    public static final String CITY_CODE = "city_code";//市ID
    public static final String AREA_CODE = "area_code";
    public static final String ORDERID = "orderId";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_choose_send_address);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        getAddress();
    }

    String searchKey;
    @Override
    public void setClickEvent() {

        iv_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);

    }

    AddressModel mModelAddress;
    AddressModel.DataBean dataBean;
    private int defaultId = -1;
    public String changeAddress;
    int pos = 0;
    ChooseSendAddressssAdapter addressAdapterss;
    ChooseSendAddresssAdapter addressAdapters;
    private void getAddress() {
        AddressListAPI.requestAddressModel(mContext,searchKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressModel addressModel) {
                        mModelAddress = addressModel;
                        if (mModelAddress.success) {
                            mListData.clear();
                            data0.clear();
                            data1.clear();
                            if (mModelAddress.data.size() > 0 && mModelAddress.data != null) {
                                mListData.addAll(mModelAddress.data);

                                for (int i = 0; i <mListData.size() ; i++) {
                                    if(mListData.get(i).sendType==1) {
                                        data0.add(mListData.get(i));
                                    }else {
                                        data1.add(mListData.get(i));
                                    }
                                }

                                addressAdapters = new ChooseSendAddresssAdapter(R.layout.item_dialog_send_address,data0,"", new ChooseAddresssAdapter.Onclick() {
                                    @Override
                                    public void jump(int position) {
                                        dataBean = data0.get(position);
                                        Intent intent = new Intent(mContext, EditAndAddActivity.class);
                                        intent.putExtra(TYPE, "edit");
                                        intent.putExtra(USER_NAME,dataBean.userName);
                                        intent.putExtra(USER_PHONE,dataBean.contactPhone);
                                        intent.putExtra(STORE_NAME, dataBean.shopName);
                                        intent.putExtra(AREA, dataBean.cityName);
                                        intent.putExtra(ADDRESS, dataBean.detailAddress);
                                        intent.putExtra(DEFAULT, "true");
                                        intent.putExtra(ADDRESS_ID,  String.valueOf(dataBean.id));
                                        intent.putExtra(PROVINCE_CODE, dataBean.provinceCode);
                                        intent.putExtra(CITY_CODE, dataBean.cityCode);
                                        intent.putExtra(AREA_CODE, dataBean.areaCode);
                                        intent.putExtra(ORDERID, "");
                                        intent.putExtra("allAddress", (mListData.get(position).provinceName + " " + mListData.get(position).cityName + " " + mListData.get(position).areaName));
                                        startActivityForResult(intent,22);
                                    }
                                });
                                addressAdapters.setOnItemChangeClickListener(new ChooseAddresssAdapter.OnEventClickListener() {
                                    @Override
                                    public void onEventClick(View view, int position, String flag) {
                                        if (flag.equals("default")) {
                                            //这里只是表面上地显示成这个地址为默认地址,要退出这个界面的时候调接口告知后台哪个变成默认地址了
                                            for (int i = 0; i < mListData.size(); i++) {
                                                if (i == position) {
                                                    if (mListData.get(i).isDefault == 1) {
                                                        //原来就是默认地址,这里点击没有效果,原来也设置了原来是默认地址这里就没有效果
                                                        //点击原来的默认地址,不会有操作,跳出界面的时候也不会有调接口操作

                                                    } else if (mListData.get(i).isDefault == 0) {
                                                        //原来不是默认地址,可以点击
                                                        //一旦点击,这个即变成默认地址了
                                                        //关键是,不能让用户点击一次就调一次接口重新刷新列表,需要在用户准备跳出这个界面的时候调接口
                                                        mListData.get(i).isDefault = 1;
                                                        //这里代表着切换了默认地址
                                                        defaultId = mListData.get(i).id;
                                                        pos = i;
                                                        changeAddress = mListData.get(i).provinceName + mListData.get(i).cityName + mListData.get(i).areaName + mListData.get(i).detailAddress;
                                                        requestEditDefaultAddress(defaultId, "");
                                                    }
                                                } else {
                                                    mListData.get(i).isDefault = 0;
                                                }
                                            }
                                            addressAdapters.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onEventLongClick(View view, int position, String flag) {

                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                recyclerView.setAdapter(addressAdapters);
                                addressAdapters.notifyDataSetChanged();
                                addressAdapterss = new ChooseSendAddressssAdapter(R.layout.item_dialog_send_address,data1);
                                if(data1.size()==0) {
                                    tv1.setVisibility(View.GONE);
                                }else {
                                    tv1.setVisibility(View.VISIBLE);
                                }
                                recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
                                recyclerView1.setAdapter(addressAdapterss);
                                addressAdapterss.notifyDataSetChanged();
                                addressAdapterss.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                        ChangeCityDialog changeCityDialog = new ChangeCityDialog(mContext) {
                                            @Override
                                            public void close() {
                                                Intent intent = new Intent(mContext, ChangeCityActivity.class);
                                                intent.putExtra("flag","1");
                                                mContext.startActivity(intent);
                                                ((Activity)mContext).finish();
                                                dismiss();
                                            }
                                        };
                                        changeCityDialog.show();

                                    }
                                });

                            }
                        } else {
                            AppHelper.showMsg(mContext, mModelAddress.message);
                        }
                    }
                });
    }

    /**
     * 设置默认地址
     * @param defaultId
     * @param orderId
     */
    private void requestEditDefaultAddress(int defaultId, String orderId) {
        DefaultAddressAPI.requestEditDefaultAddress(mContext, defaultId, orderId)
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
                            UserInfoHelper.saveProvince(mActivity,mListData.get(pos).getProvinceName());
                            UserInfoHelper.saveCity(mActivity,mListData.get(pos).getCityName());
                            UserInfoHelper.saveAreaName(mActivity,mListData.get(pos).getAreaName());
                            UserInfoHelper.saveChangeFlag(mActivity,"0");
                            EventBus.getDefault().post(new AddressEvent());
                            finish();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
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


            case R.id.tv_add:
                Intent intent = new Intent(mContext,EditAndAddActivity.class);
                intent.putExtra(TYPE, "add");
                intent.putExtra(USER_NAME,"");
                intent.putExtra(USER_PHONE, "");
                intent.putExtra(STORE_NAME, "");
                intent.putExtra(AREA, "");
                intent.putExtra(ADDRESS, "");
                intent.putExtra(DEFAULT, "false");
                intent.putExtra(ADDRESS_ID, "");
                intent.putExtra(PROVINCE_CODE, "");
                intent.putExtra(CITY_CODE,"");
                intent.putExtra(AREA_CODE, "");
                intent.putExtra(ORDERID, "");
                intent.putExtra("allAddress", "");
                startActivityForResult(intent,11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        data0.clear();
        data1.clear();
        getAddress();
    }
}
