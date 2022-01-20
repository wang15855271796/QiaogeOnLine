package com.puyue.www.qiaoge.fragment.cart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    int positions;
    //附加要求选择
    List<Integer> list = new ArrayList<>();
    //附加要求选择描述
    List<String> listDesc = new ArrayList<>();
    //车型id
    String id;
    List<CarStyleModel.DataBean.SpecReqItemBean> vehicleStdItem;
    int type;
    String cityInfoRevision;
    String orderId;
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
        ll_price.setVisibility(View.GONE);
        orderId = bundle.getString("orderId");
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positions = tab.getPosition();
                reqList.clear();
                reqList.addAll(data.getVehicle_list().get(positions).getVehicle_std_item());
                if(reqList.size()>0) {
                    tv_desc.setVisibility(View.VISIBLE);
                }else {
                    tv_desc.setVisibility(View.GONE);
                }
                if(dataBean!=null) {
                    getPrice(dataBean.getCity_id(),data.getCity_info_revision(),
                            data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                            StringUtils.join(list, ","),jsonArray1,0,"");
                }

                chooseRequireAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        lav_loading.show();
        getTime();
        String huoCityId = SharedPreferencesUtil.getString(mActivity, "huoCityId");
        getCarStyle(huoCityId,orderId);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        chooseRequireAdapter =  new ChooseRequireAdapter(R.layout.item_choose_req,reqList);
        recyclerView.setAdapter(chooseRequireAdapter);

        chooseRequireAdapter.setOnItemClickListener(new ChooseRequireAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, boolean hasFocus) {
                String name = reqList.get(position).getName();
                if(hasFocus) {
                    listDesc.add(name);
                }else {
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
                if(priceData!=null) {
                    Intent orderIntent = new Intent(mActivity, HuoOrderConfirmActivity.class);
                    orderIntent.putExtra("reqList", (Serializable) listDesc);
                    orderIntent.putExtra("list", (Serializable) list);
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
                    orderIntent.putExtra("orderId", orderId);
                    orderIntent.putExtra("dataBean", (Serializable) dataBean);
                    orderIntent.putExtra("cityInfoRevision",cityInfoRevision);
                    EventBus.getDefault().postSticky(new JSONEvent(jsonArray1));
                    orderIntent.putExtra("vehicleStdItem", (Serializable) reqList);
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
                startActivity(intent);
                break;

            case R.id.tv_unload:
                Intent intents = new Intent(mActivity, HuoEditxActivity.class);
                intents.putExtra("type",2);
                startActivity(intents);
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
                intent.putExtra("list", (Serializable) list);
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
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("dataBean", (Serializable) dataBean);
                intent.putExtra("cityInfoRevision",cityInfoRevision);
                EventBus.getDefault().postSticky(new JSONEvent(jsonArray1));
                intent.putExtra("vehicleStdItem", (Serializable) reqList);
                startActivity(intent);
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
    //附加要求集合
    List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem> reqList = new ArrayList<>();
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
                                List<CarStyleModel.DataBean.VehicleListBean> vehicle_list = data.getVehicle_list();
                                reqList.addAll(data.getVehicle_list().get(positions).getVehicle_std_item());
                                cityInfoRevision = data.getCity_info_revision();
                                MyCarPagerAdapter myCarPagerAdapter = new MyCarPagerAdapter(vehicle_list,mActivity);
                                viewPager.setAdapter(myCarPagerAdapter);
                                tab_layout.setupWithViewPager(viewPager);
                                chooseRequireAdapter.notifyDataSetChanged();

                                if(dataBean!=null) {
                                    getPrice(data.getCity_id(),data.getCity_info_revision(),
                                            data.getVehicle_list().get(positions).getOrder_vehicle_id(),"",
                                            StringUtils.join(list, ","),jsonArray1,0,"");
                                }
                                myCarPagerAdapter.notifyDataSetChanged();
                                lav_loading.hide();


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
            jsonArray1.put(jsonObject);
            getPrice(dataBean.getCity_id(),data.getCity_info_revision(),
                    data.getVehicle_list().get(positions).getOrder_vehicle_id(),"", StringUtils.join(list, ","),jsonArray1,0,"");

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
        positions = 0;
        getCarStyle(huoCityEvent.getCityId(),orderId);


    }

}
