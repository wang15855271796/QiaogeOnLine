package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.EditAddressActivity;
import com.puyue.www.qiaoge.adapter.ScAddAdapter;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.ScAddressEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChSchoolAddActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.et_search)
    EditText et_search;
    ScAddAdapter scAddAdapter;

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
        setContentView(R.layout.ch_school_activity);
    }

    @Override
    public void findViewById() {

    }

    AddressModel.DataBean dataBean;
    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        scAddAdapter = new ScAddAdapter(R.layout.item_sc_address,list);
        recyclerView.setAdapter(scAddAdapter);
        scAddAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                scAddAdapter.setPos(position);
                dataBean = list.get(position);
            }
        });

        scAddAdapter.setOnEditClickListener(new ScAddAdapter.OnScClickListener() {
            @Override
            public void onScClick(int pos) {
                Intent intent = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra(TYPE, "edit");
                intent.putExtra(USER_NAME, list.get(pos).userName);
                intent.putExtra(USER_PHONE,list.get(pos).contactPhone);
                intent.putExtra(STORE_NAME,list.get(pos).shopName);
                intent.putExtra(AREA, list.get(pos).cityName);
                intent.putExtra(ADDRESS, list.get(pos).detailAddress);
                intent.putExtra(DEFAULT, "false");
                intent.putExtra(ADDRESS_ID,  String.valueOf(list.get(pos).id));
                intent.putExtra(PROVINCE_CODE,  list.get(pos).provinceCode);
                intent.putExtra(CITY_CODE, list.get(pos).cityCode);
                intent.putExtra(AREA_CODE, list.get(pos).areaCode);
                intent.putExtra(ORDERID,"");
                intent.putExtra("allAddress", (list.get(pos).provinceName + " " + list.get(pos).cityName + " " + list.get(pos).areaName));
                mActivity.startActivityForResult(intent,22);
            }
        });

        requestAddressList();

        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
    }

    @Override
    public void setClickEvent() {

    }

    List<AddressModel.DataBean> list = new ArrayList<>();
    private void requestAddressList() {
        AddressListAPI.requestAddressModel(mContext)
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
                        if(addressModel.code == 1) {
                            if(addressModel.data!=null && addressModel.data.size()>0) {
                                list.addAll(addressModel.data);
                                scAddAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showErroMsg(mActivity,addressModel.message);
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

            case R.id.iv_clear:
                et_search.getText().clear();
                break;

            case R.id.tv_ok:
                if(dataBean!=null) {
                    EventBus.getDefault().post(new ScAddressEvent(dataBean));
                }
                finish();
                break;

            case R.id.tv_add:
                Intent intent = new Intent(mContext,EditAddressActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list.clear();
        //重新请求一次地址列表数据
        requestAddressList();
    }
}
