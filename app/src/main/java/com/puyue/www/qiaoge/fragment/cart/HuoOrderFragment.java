package com.puyue.www.qiaoge.fragment.cart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HuoEditAddressActivity;
import com.puyue.www.qiaoge.activity.HuoEditxActivity;
import com.puyue.www.qiaoge.activity.HuoOrderConfirmActivity;
import com.puyue.www.qiaoge.activity.PriceDetailActivity;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.adapter.ChooseRequireAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.dialog.AppointDialog;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.event.HuoCityEvent;
import com.puyue.www.qiaoge.event.JSONEvent;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.AppointModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.WrapContentHeightViewPager;
import com.wang.avi.AVLoadingIndicatorView;

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
    WrapContentHeightViewPager viewPager;
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
    @BindView(R.id.lav_loading)
    AVLoadingIndicatorView lav_loading;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.iv_pre)
    ImageView iv_pre;
    @BindView(R.id.iv_next)
    ImageView iv_next;
    int positions = 0;
    //附加要求选择
    List<Integer> list = new ArrayList<>();
    //附加要求选择描述
    List<String> listDesc = new ArrayList<>();
    //车型id
    String id;
    String cityInfoRevision;
    String orderId;
    String address;
    String cityId;
    int orderTime = 0;
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
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setViewData() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle bundle=getArguments();
        ll_price.setVisibility(View.GONE);
        orderId = bundle.getString("orderId");
        address = bundle.getString("address");
        cityId = bundle.getString("cityId");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                positions = position;
                reqList.clear();
                listDesc.clear();
                reqList.addAll(data.getVehicle_list().get(positions).getVehicle_std_item());
                id = data.getVehicle_list().get(positions).getOrder_vehicle_id();
                if(reqList.size()>0) {
                    tv_desc.setVisibility(View.VISIBLE);
                }else {
                    tv_desc.setVisibility(View.GONE);
                }

                if(jsonArray1.length()>1) {
                    getPrice(data.getCity_id(),data.getCity_info_revision(),
                            data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                            "",jsonArray1,orderTime,"",StringUtils.join(listDesc, ","),invoiceType);
                }
                chooseRequireAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        lav_loading.show();
        getTime();
        getCarStyle(cityId,orderId);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        chooseRequireAdapter =  new ChooseRequireAdapter(R.layout.item_choose_req,reqList);
        recyclerView.setAdapter(chooseRequireAdapter);
        chooseRequireAdapter.setOnItemClickListener(new ChooseRequireAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, boolean hasFocus) {
                String name = reqList.get(position).getName();
                if(hasFocus) {
                    listDesc.add(name);
                    if(data!=null&&jsonArray1.length()==2) {
                        getPrice(data.getCity_id(),data.getCity_info_revision(),
                                data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                                "",jsonArray1,orderTime,"", StringUtils.join(listDesc, ","),invoiceType);
                    }

                }else {
                    listDesc.remove(name);
                    if(data!=null&&jsonArray1.length()==2) {
                        getPrice(data.getCity_id(),data.getCity_info_revision(),
                                data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                                "",jsonArray1,orderTime,"", StringUtils.join(listDesc, ","),invoiceType);
                    }
                }
            }

        });

        if(orderId!=null&&!TextUtils.isEmpty(orderId)) {
            tv_zhuang.setEnabled(false);
            tv_unload.setEnabled(false);
        }else {
            tv_zhuang.setEnabled(true);
            tv_unload.setEnabled(true);
        }
    }

    @Override
    public void setClickEvent() {
        tv_zhuang.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        tv_appoint.setOnClickListener(this);
        tv_now.setOnClickListener(this);
        iv_pre.setOnClickListener(this);
        iv_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_pre:
                if(positions==0) {
                    return;
                }
                positions--;
                viewPager.setCurrentItem(positions);
                break;

            case R.id.iv_next:
                if(vehicle_list.size()== positions) {
                    return;
                }
                positions++;
                viewPager.setCurrentItem(positions);
                break;

            case R.id.tv_now:
                if(priceData!=null&&data!=null) {
                    Intent orderIntent = new Intent(mActivity, HuoOrderConfirmActivity.class);
                    orderIntent.putExtra("reqList", (Serializable) listDesc);
//                    orderIntent.putExtra("list", (Serializable) list);
                    orderIntent.putExtra("carStyle", data.getVehicle_list().get(positions).getVehicle_name());
                    orderIntent.putExtra("zAddr", tv_zhuang.getText().toString());
                    orderIntent.putExtra("xAddr", tv_unload.getText().toString());
                    orderIntent.putExtra("price", priceData.getTotal_price());
                    orderIntent.putExtra("zName", zName);
                    orderIntent.putExtra("zPhone", zPhone);
                    orderIntent.putExtra("xName", xName);
                    orderIntent.putExtra("xPhone", xPhone);
                    orderIntent.putExtra("orderTime", "0");
                    orderIntent.putExtra("time","现在用车");
                    orderIntent.putExtra("id", id);
                    orderIntent.putExtra("lat", lat);
                    orderIntent.putExtra("lon", lon);
                    orderIntent.putExtra("cityId", data.getCity_id());
                    orderIntent.putExtra("orderId", orderId);
                    orderIntent.putExtra("cityInfoRevision",cityInfoRevision);
                    EventBus.getDefault().postSticky(new JSONEvent(jsonArray1));
                    orderIntent.putExtra("vehicleStdItem", (Serializable) specList);
                    startActivity(orderIntent);
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请填写装货/卸货信息");
                }

                break;

            case R.id.tv_appoint:
                if(priceData!=null) {
                    showPickerView();
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请填写装货/卸货信息");
                }
                break;

            case R.id.tv_zhuang:
                Intent intent = new Intent(mActivity, HuoEditAddressActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("cityId",cityId);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                break;

            case R.id.tv_unload:
                if(tv_zhuang.getText().toString()!=null&&!tv_zhuang.getText().toString().equals("")) {
                    Intent intents = new Intent(mActivity, HuoEditxActivity.class);
                    intents.putExtra("type",2);
                    intents.putExtra("cityId",cityId);
                    intents.putExtra("orderId",orderId);
                    startActivity(intents);
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请先填写装货地址");
                }

                break;

            case R.id.ll_price:
                if(priceData==null&&data.getVehicle_list()==null) {
                    return;
                }
                Intent intentss = new Intent(mActivity, PriceDetailActivity.class);
                intentss.putExtra("totalPrice", priceData.getTotal_price());
                intentss.putExtra("carStyle", data.getVehicle_list().get(positions).getVehicle_name());
                intentss.putExtra("priceList", (Serializable) priceData.getCalculate_price_info());
                intentss.putExtra("id", id);
                intentss.putExtra("cityId",cityId);
                startActivity(intentss);
                break;
        }
    }

    //  天
    private List<AppointModel.DataBean> dayList = new ArrayList<>();
    //  时
    private ArrayList<ArrayList<AppointModel.DataBean.HoursBean>> hoursList = new ArrayList<>();
    //  分
    private ArrayList<ArrayList<ArrayList<AppointModel.DataBean.HoursBean.MinutesBean>>> minList = new ArrayList<>();
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Intent intent = new Intent(mActivity,HuoOrderConfirmActivity.class);
                intent.putExtra("time",minList.get(options1).get(options2).get(options3).getDateTime());
                intent.putExtra("reqList", (Serializable) listDesc);
//                intent.putExtra("list", (Serializable) list);
                intent.putExtra("carStyle", data.getVehicle_list().get(positions).getVehicle_name());
                intent.putExtra("zAddr", tv_zhuang.getText().toString());
                intent.putExtra("xAddr", tv_unload.getText().toString());
                intent.putExtra("price", priceData.getTotal_price());
                intent.putExtra("zName", zName);
                intent.putExtra("zPhone", zPhone);
                intent.putExtra("xName", xName);
                intent.putExtra("xPhone", xPhone);
                intent.putExtra("orderTime", "1");
                intent.putExtra("id", id);
                intent.putExtra("cityId", data.getCity_id());
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("cityInfoRevision",cityInfoRevision);
                EventBus.getDefault().postSticky(new JSONEvent(jsonArray1));
                intent.putExtra("vehicleStdItem", (Serializable) specList);
                startActivity(intent);
            }
        })

                .setTitleText("预计时间")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(dayList, hoursList, minList);//三级选择器
        pvOptions.show();


    }

    ChooseRequireAdapter chooseRequireAdapter;
    CarStyleModel.DataBean data;
    List<CarStyleModel.DataBean.VehicleListBean> vehicle_list = new ArrayList<>();
    //附加要求集合
    List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem> reqList = new ArrayList<>();
    //额外服务集合
    List<CarStyleModel.DataBean.SpecReqItemBean> specList = new ArrayList<>();
    MyCarPagerAdapter myCarPagerAdapter;
    private void getCarStyle(String cityId,String orderId) {
        HuolalaAPI.getCarStyle(mActivity,cityId,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarStyleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_loading.hide();
                    }

                    @Override
                    public void onNext(CarStyleModel carStyleModel) {

                        if(carStyleModel.getCode()==1) {
                            if(carStyleModel.getData()!=null) {
                                reqList.clear();
                                data = carStyleModel.getData();
                                vehicle_list = data.getVehicle_list();
                                //额外服务
                                specList.clear();
                                specList.addAll(data.getSpec_req_item());
                                //附加要求集合
                                reqList.addAll(data.getVehicle_list().get(positions).getVehicle_std_item());
                                cityInfoRevision = data.getCity_info_revision();
                                id = data.getVehicle_list().get(positions).getOrder_vehicle_id();
                                myCarPagerAdapter = new MyCarPagerAdapter(vehicle_list,mActivity);
                                viewPager.setAdapter(myCarPagerAdapter);
                                tab_layout.setupWithViewPager(viewPager);
                                chooseRequireAdapter.notifyDataSetChanged();
                                myCarPagerAdapter.notifyDataSetChanged();
                                lav_loading.hide();
                                tv_unload.setText(data.getReceiveAddr().getAddr());

                                if(carStyleModel.getData().getReceiveAddr()!=null) {
                                    tv_unload.setText(data.getReceiveAddr().getAddr());
                                    CarStyleModel.DataBean.ReceiveAddrBean receiveAddr = data.getReceiveAddr();
                                    JSONObject jsonObject;
                                    JSONObject jsonObject1;
                                    try {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("district_name",receiveAddr.getDistrict_name());
                                        jsonObject.put("addr",receiveAddr.getAddr());
                                        jsonObject.put("name",receiveAddr.getName());
                                        jsonObject.put("house_number",receiveAddr.getHouse_number());
                                        jsonObject.put("city_name",receiveAddr.getCity_name());
                                        jsonObject.put("city_id",receiveAddr.getCity_id());
                                        jsonObject.put("contacts_name",receiveAddr.getContacts_name());
                                        jsonObject.put("contacts_phone_no",receiveAddr.getContacts_phone_no());
                                        jsonObject1 = new JSONObject();
                                        jsonObject1.put("lat",receiveAddr.getLat_lon().getLat());
                                        jsonObject1.put("lon",receiveAddr.getLat_lon().getLon());
                                        jsonObject.put("lat_lon",jsonObject1);
                                        jsonArray1.put(jsonObject);
                                        xName = receiveAddr.getContacts_name();
                                        xPhone = receiveAddr.getContacts_phone_no();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                tv_zhuang.setText(data.getSendAddr().getAddr());
                                if(carStyleModel.getData().getSendAddr()!=null) {
                                    tv_zhuang.setText(data.getSendAddr().getAddr());
                                    CarStyleModel.DataBean.SendAddrBean sendAddr = data.getSendAddr();
                                    JSONObject jsonObject;
                                    JSONObject jsonObject1;
                                    try {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("district_name",sendAddr.getDistrict_name());
                                        jsonObject.put("addr",sendAddr.getAddr());
                                        jsonObject.put("name",sendAddr.getName());
                                        jsonObject.put("house_number",sendAddr.getHouse_number());
                                        jsonObject.put("city_name",sendAddr.getCity_name());
                                        jsonObject.put("city_id",sendAddr.getCity_id());
                                        jsonObject.put("contacts_name",sendAddr.getContacts_name());
                                        jsonObject.put("contacts_phone_no",sendAddr.getContacts_phone_no());
                                        jsonObject1 = new JSONObject();
                                        jsonObject1.put("lat",sendAddr.getLat_lon().getLat());
                                        jsonObject1.put("lon",sendAddr.getLat_lon().getLon());
                                        jsonObject.put("lat_lon",jsonObject1);
                                        jsonArray1.put(jsonObject);
                                        zName = sendAddr.getContacts_name();
                                        zPhone = sendAddr.getContacts_phone_no();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(orderId!=null&&!TextUtils.isEmpty(orderId)) {
                                    getPrice(data.getCity_id(),data.getCity_info_revision(),
                                            data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                                            "",jsonArray1,orderTime,"",StringUtils.join(listDesc, ","),invoiceType);
                                }
                            }
                        }else {
                            lav_loading.hide();
                            ToastUtil.showSuccessMsg(mActivity,carStyleModel.getMessage());
                        }
                    }
                });
    }

    private void getTime() {
        HuolalaAPI.getAppoint(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppointModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppointModel appointModel) {
                        if(appointModel.getCode()==1) {
                            if(appointModel.getData()!=null) {
                                parseData(appointModel.getData());
                            }
                        }
                    }
                });
    }

    private void parseData(List<AppointModel.DataBean> data) {
        dayList = data;
//      遍历天
        for(int i = 0; i <data.size() ; i++) {
//         存放时
            ArrayList<AppointModel.DataBean.HoursBean> cityList = new ArrayList<>();
//         存放分
            ArrayList<ArrayList<AppointModel.DataBean.HoursBean.MinutesBean>> province_AreaList = new ArrayList<>();
            List<AppointModel.DataBean.HoursBean> children1 = data.get(i).getHours();
            cityList.addAll(children1);
//         遍历市
            for(int c = 0; c <data.get(i).getHours().size() ; c++) {
                //该城市的所有地区列表
                ArrayList<AppointModel.DataBean.HoursBean.MinutesBean> city_AreaList = new ArrayList<>();
                List<AppointModel.DataBean.HoursBean.MinutesBean> children = data.get(i).getHours().get(c).getMinutes();
                city_AreaList.addAll(children);
                province_AreaList.add(city_AreaList);
            }

            hoursList.add(cityList);

            minList.add(province_AreaList);

        }
    }
    AddressListModel.DataBean dataBean;
    JSONArray jsonArray1 = new JSONArray();
    HuoAddressEvent huoAddressEvent;
    String lat;
    String lon;
    String zPhone;
    String zName;
    String xPhone;
    String xName;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(HuoAddressEvent huoAddressEvent) {
        this.huoAddressEvent = huoAddressEvent;

        dataBean = huoAddressEvent.getDataBean();
        if(huoAddressEvent.getType()==1) {
            tv_zhuang.setText(dataBean.getAddr());
            zPhone = huoAddressEvent.getPhone();
            zName = huoAddressEvent.getName();
        }else {

            xPhone = huoAddressEvent.getPhone();
            xName = huoAddressEvent.getName();
            tv_unload.setText(dataBean.getAddr());
        }
        AddressListModel.DataBean.LatLonBean lat_lon = dataBean.getLat_lon();
        lat = lat_lon.getLat();
        lon = lat_lon.getLon();

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
            if(huoAddressEvent.getType()==1) {
                jsonArray1.put(0,jsonObject);
            }else {
                jsonArray1.put(1,jsonObject);
            }

            if(jsonArray1.length()==2) {
                getPrice(dataBean.getCity_id(),data.getCity_info_revision(),
                        data.getVehicle_list().get(positions).getOrder_vehicle_id(),"", "",
                        jsonArray1,orderTime,"",StringUtils.join(listDesc, ","),invoiceType);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    int invoiceType = 0;
    DealPriceModel.DataBean priceData;
    private void getPrice(String city_id, String city_info_revision, String order_vehicle_id, String couponId,
                          String spec_req, JSONArray addInfo, int orderTime, String reserve_time,String vehicle_std,int invoiceType) {
        HuolalaAPI.getPrice(mActivity,city_id,city_info_revision,order_vehicle_id,couponId,spec_req,addInfo,orderTime,reserve_time,vehicle_std,invoiceType)
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
                                ll_price.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,priceModel.getMessage());
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCity(HuoCityEvent huoCityEvent) {
        cityId = huoCityEvent.getCityId();
        SharedPreferencesUtil.saveString(mActivity,"huoCityName",huoCityEvent.getName());
        getCarStyle(cityId,orderId);
        tv_unload.setText("");
        tv_zhuang.setText("");
        ll_price.setVisibility(View.GONE);

        try {
            jsonArray1 = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dataBean = null;
    }
}
