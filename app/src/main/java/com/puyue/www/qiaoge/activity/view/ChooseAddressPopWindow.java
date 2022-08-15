package com.puyue.www.qiaoge.activity.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.AddAddressAdapter;
import com.puyue.www.qiaoge.adapter.SearchOperaAdapter;
import com.puyue.www.qiaoge.api.mine.address.DefaultAddressAPI;
import com.puyue.www.qiaoge.event.UpdateAddressEvent;
import com.puyue.www.qiaoge.listener.PopWindowListener;
import com.puyue.www.qiaoge.model.AreasModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseAddressPopWindow extends PopupWindow {
    Activity mActivity;
    PopWindowListener popWindowListener;
    String cityName;
    String keyWords;
    public ChooseAddressPopWindow(Activity mActivity,String cityName, String keyWords) {
        super(mActivity);
        this.mActivity=mActivity;
        this.cityName = cityName;
        this.keyWords = keyWords;
        init();
    }


    public void setPopWindowListener(PopWindowListener popWindowListener) {
        this.popWindowListener = popWindowListener;
    }


    RecyclerView recyclerView;
    View view;
    private void init() {
        if(view == null) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.item_address_popup, null, false);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        setContentView(view);
    }

    AddAddressAdapter addAddressAdapter;
    private void getArea(String cityName, String keyWords) {
        DefaultAddressAPI.getArea(mActivity, cityName, keyWords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AreasModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AreasModel areasModel) {
                        if(areasModel.getCode()==1) {
                            if(areasModel.getData()!=null && areasModel.getData().size()>0) {
                                recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                                addAddressAdapter = new AddAddressAdapter(R.layout.item_address_area,areasModel.getData());
                                recyclerView.setAdapter(addAddressAdapter);
                                addAddressAdapter.notifyDataSetChanged();
                                addAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        EventBus.getDefault().post(new UpdateAddressEvent(areasModel.getData().get(position).getDetailAddress()));
                                        dismiss();
//                                        keyWorldsView.setText(areasModel.getData().get(position).getDetailAddress());
//                                        recyclerView.setVisibility(View.GONE);
//                                        keyWorldsView.setSelection(areasModel.getData().get(position).getDetailAddress().length());
//                                        hintKbTwo();
                                    }
                                });
                            }else {
                                recyclerView.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.showErroMsg(mActivity,areasModel.getMessage());
                        }
                    }
                });
    }

    public void setAddressData(String cityName, String keyWords) {
        this.cityName = cityName;
        this.keyWords = keyWords;
        getArea(cityName,keyWords);
    }

//    public void setAddressData(String cityName, String keyWords) {
//        this.cityName = cityName;
//        this.keyWords = keyWords;
//        addAddressAdapter.notifyDataSetChanged();
//    }
}
