package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

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
    HuoAddressAdapter huoAddressAdapter;
    int type;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        huoAddressAdapter = new HuoAddressAdapter(R.layout.item_address_huo,list);
        recyclerView.setAdapter(huoAddressAdapter);
//        et_search.addTextChangedListener(new EditChangedListener());
        huoAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressListModel.DataBean dataBean = list.get(position);
                EventBus.getDefault().post(new HuoAddress1Event(dataBean));
                finish();
            }
        });
    }

    @Override
    public void setClickEvent() {
        tv_cancel.setOnClickListener(this);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            recyclerView.setVisibility(View.VISIBLE);
            getAddressList(charSequence.toString(),"杭州",type);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

//    private TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            recyclerView.setVisibility(View.VISIBLE);
//            getAddressList(s.toString(),"杭州",type);
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    //地址检索
    List<AddressListModel.DataBean> list = new ArrayList<>();
    private void getAddressList(String address,String city,int placeType) {
        HuolalaAPI.getAddressList(mActivity,address,city,placeType)
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
                getAddressList(et_search.getText().toString(),"杭州",type);
//                finish();
                break;
        }
    }
}
