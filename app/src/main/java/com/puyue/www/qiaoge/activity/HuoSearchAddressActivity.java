package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HuoAddressAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.HuoAddress1Event;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.event.HuoCityEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.zaaach.citypicker.CityPickerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoSearchAddressActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.tv_city)
    TextView tv_city;
    HuoAddressAdapter huoAddressAdapter;
    int type;
    String cityId;
    String name;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search_address);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        huoAddressAdapter = new HuoAddressAdapter(R.layout.item_address_huo,list);
        recyclerView.setAdapter(huoAddressAdapter);
        et_search.addTextChangedListener(new EditChangedListener());
        huoAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressListModel.DataBean dataBean = list.get(position);
                EventBus.getDefault().post(new HuoAddress1Event(dataBean));
                finish();
            }
        });

        //默认城市名称
        name = SharedPreferencesUtil.getString(mContext, "huoCityName");
        cityId = SharedPreferencesUtil.getString(mContext, "huoCityId");
        tv_city.setText(name);
    }

    @Override
    public void setClickEvent() {
        tv_cancel.setOnClickListener(this);
        ll_address.setOnClickListener(this);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            recyclerView.setVisibility(View.VISIBLE);
            getAddressList(charSequence.toString(),name,type,cityId);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    //地址检索
    List<AddressListModel.DataBean> list = new ArrayList<>();
    private void getAddressList(String address,String city,int placeType,String cityId) {
        HuolalaAPI.getAddressList(mActivity,address,city,placeType,cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressListModel addressListModel) {
                        if(addressListModel.getCode()==1) {
                            if(addressListModel.getData()!=null) {
                                list.clear();
                                list.addAll(addressListModel.getData());
                                huoAddressAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,addressListModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.ll_address:
                Intent intent = new Intent(mContext,HuoCityActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCity(HuoCityEvent huoCityEvent) {
        name = huoCityEvent.getName();
        cityId = huoCityEvent.getCityId();
        list.clear();
        tv_city.setText(huoCityEvent.getName());
        et_search.setText("");
    }
}
