package com.puyue.www.qiaoge.fragment.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HuoEditAddressActivity;
import com.puyue.www.qiaoge.activity.HuoOrderConfirmActivity;
import com.puyue.www.qiaoge.activity.PriceDetailActivity;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.adapter.ChooseRequireAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.dialog.AppointDialog;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoOrderFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_zhuang)
    TextView tv_zhuang;
    @BindView(R.id.tv_unload)
    TextView tv_unload;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.tv_appoint)
    TextView tv_appoint;
    @BindView(R.id.tv_now)
    TextView tv_now;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    int position;
    //附加要求选择
    List<Integer> list = new ArrayList<>();
    //附加要求选择描述
    List<String> listDesc = new ArrayList<>();
    String id;
    int type;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_huo_order;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        Bundle bundle=getArguments();
        id=bundle.getString("id");

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getCarStyle("1011");
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        chooseRequireAdapter =  new ChooseRequireAdapter(R.layout.item_choose_req,reqList);
        recyclerView.setAdapter(chooseRequireAdapter);

        chooseRequireAdapter.setOnItemClickListener(new ChooseRequireAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, boolean hasFocus) {
                type = reqList.get(position).getType();
                String name = reqList.get(position).getName();
                if(hasFocus) {
                    list.add(type);
                    listDesc.add(name);
                }else {
                    list.remove(new Integer(type));
                    listDesc.remove(name);
                }
            }

        });
    }

    @Override
    public void setClickEvent() {
        tv_zhuang.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        tv_appoint.setOnClickListener(this);
        tv_now.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_now:
                Intent orderIntent = new Intent(mActivity, HuoOrderConfirmActivity.class);
                orderIntent.putExtra("reqList", (Serializable) listDesc);
                orderIntent.putExtra("carStyle", data.getVehicle_list().get(position).getVehicle_name());
                orderIntent.putExtra("zAddr", tv_zhuang.getText().toString());
                orderIntent.putExtra("xAddr", tv_unload.getText().toString());
                orderIntent.putExtra("price", priceData.getTotal_price());
                orderIntent.putExtra("zName", huoAddressEvent.getName());
                orderIntent.putExtra("zPhone", huoAddressEvent.getPhone());
                orderIntent.putExtra("xName", huoAddressEvent.getName());
                orderIntent.putExtra("xPhone", huoAddressEvent.getPhone());
                startActivity(orderIntent);
                break;
            case R.id.tv_appoint:
                AppointDialog appointDialog = new AppointDialog(mActivity);
                appointDialog.show();
                break;

            case R.id.tv_zhuang:
                Intent intent = new Intent(mActivity, HuoEditAddressActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;

            case R.id.tv_unload:
                Intent intents = new Intent(mActivity,HuoEditAddressActivity.class);
                intents.putExtra("type",2);
                startActivity(intents);
                break;

            case R.id.ll_price:
                if(priceData==null&&data.getVehicle_list()==null) {
                    return;
                }
                Intent intentss = new Intent(mActivity, PriceDetailActivity.class);
                intentss.putExtra("totalPrice", priceData.getTotal_price());
                intentss.putExtra("carStyle", data.getVehicle_list().get(position).getVehicle_name());
                intentss.putExtra("priceList", (Serializable) priceData.getCalculate_price_info());
                intentss.putExtra("id", id);
                startActivity(intentss);
                break;
        }
    }

    ChooseRequireAdapter chooseRequireAdapter;
    CarStyleModel.DataBean data;
    //附加要求集合
    List<CarStyleModel.DataBean.SpecReqItemBean> reqList = new ArrayList<>();
    private void getCarStyle(String cityId) {
        HuolalaAPI.getCarStyle(mActivity,cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarStyleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CarStyleModel carStyleModel) {
                        if(carStyleModel.getCode()==1) {
                            if(carStyleModel.getData()!=null) {
                                reqList.clear();
                                data = carStyleModel.getData();
                                List<CarStyleModel.DataBean.VehicleListBean> vehicle_list = data.getVehicle_list();
                                reqList.addAll(data.getSpec_req_item());
                                MyCarPagerAdapter myCarPagerAdapter = new MyCarPagerAdapter(vehicle_list,mActivity);
                                viewPager.setAdapter(myCarPagerAdapter);
                                tab_layout.setupWithViewPager(viewPager);
                                chooseRequireAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,carStyleModel.getMessage());
                        }
                    }
                });
    }


    AddressListModel.DataBean dataBean;
    JSONArray jsonArray1 = new JSONArray();
    HuoAddressEvent huoAddressEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(HuoAddressEvent huoAddressEvent) {
        this.huoAddressEvent = huoAddressEvent;
        dataBean = huoAddressEvent.getDataBean();
        if(huoAddressEvent.getType()==1) {
            tv_zhuang.setText(dataBean.getAddr());
        }else {
            tv_unload.setText(dataBean.getAddr());
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("district_name",dataBean.getDistrict_name());
            jsonObject.put("place_id",dataBean.getPlace_id());
            jsonObject.put("addr",dataBean.getAddr());
            jsonObject.put("name",dataBean.getName());
            jsonObject.put("house_number","");
            jsonObject.put("city_name",dataBean.getCity_name());
            jsonObject.put("city_id",dataBean.getCity_id());
            jsonObject.put("contacts_name",huoAddressEvent.getName());
            jsonObject.put("contacts_phone_no",huoAddressEvent.getPhone());
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("lat",dataBean.getLat_lon().getLat());
            jsonObject1.put("lon",dataBean.getLat_lon().getLon());
            jsonObject.put("lat_lon",jsonObject1);
            jsonArray1.put(jsonObject);
            Log.d("cdsgfsfe...","sss");
            getPrice(dataBean.getCity_id(),data.getCity_info_revision(),
                    data.getVehicle_list().get(position).getOrder_vehicle_id(),"", StringUtils.join(list, ","),jsonArray1,0,"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    DealPriceModel.DataBean priceData;
    private void getPrice(String city_id, String city_info_revision, String order_vehicle_id, String couponId,
                          String spec_req, JSONArray addInfo, int orderTime, String reserve_time) {
        HuolalaAPI.getPrice(mActivity,city_id,city_info_revision,order_vehicle_id,couponId,spec_req,addInfo,orderTime,reserve_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DealPriceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(DealPriceModel priceModel) {
                        if(priceModel.getCode()==1) {
                            if(priceModel.getData()!=null) {
                                priceData = priceModel.getData();
                                tv_total_price.setText(priceData.getTotal_price());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,priceModel.getMessage());
                        }
                    }
                });
    }
}
