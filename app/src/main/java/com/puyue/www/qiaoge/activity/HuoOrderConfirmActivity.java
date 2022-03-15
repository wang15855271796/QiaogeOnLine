package com.puyue.www.qiaoge.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ReqAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.HuoAddressDialog;
import com.puyue.www.qiaoge.dialog.HuoBeizhuDialog;
import com.puyue.www.qiaoge.dialog.HuoContactDialog;
import com.puyue.www.qiaoge.dialog.HuoCouponDialog;
import com.puyue.www.qiaoge.dialog.HuoOtherDialog;
import com.puyue.www.qiaoge.event.HuoAddress1Event;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.event.HuoBeizhuEvent;
import com.puyue.www.qiaoge.event.HuoCouponEvent;
import com.puyue.www.qiaoge.event.HuoOrderContactEvent;
import com.puyue.www.qiaoge.event.JSONEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.event.OtherEvent;
import com.puyue.www.qiaoge.event.OtherSureEvent;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.AppointModel;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.model.HuoCouponModel;
import com.puyue.www.qiaoge.model.HuoPayModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoOrderConfirmActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_z)
    TextView tv_z;
    @BindView(R.id.tv_x)
    TextView tv_x;
    @BindView(R.id.tv_car)
    TextView tv_car;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.tv_receipt)
    TextView tv_receipt;
    @BindView(R.id.tv_receipt1)
    TextView tv_receipt1;
    @BindView(R.id.tv_receipt2)
    TextView tv_receipt2;
    @BindView(R.id.ll_receipt)
    LinearLayout ll_receipt;
    @BindView(R.id.rl_beizhu)
    RelativeLayout rl_beizhu;
    @BindView(R.id.rl_other)
    RelativeLayout rl_other;
    @BindView(R.id.tv_other)
    TextView tv_other;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.tv_contact_phone)
    TextView tv_contact_phone;
    @BindView(R.id.tv_use_car)
    TextView tv_use_car;
    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    @BindView(R.id.lav_loading)
    AVLoadingIndicatorView lav_loading;
    //附加要求
    List<String> reqList;
    String zAddr;
    String xAddr;
    String carStyle;
    String price;
    String zName;
    String zPhone;
    String xName;
    String xPhone;
    String orderTime;
    String reserve_time = "";
//    String city_id;
    String id;
    String lat;
    String lon;
    String cityInfoRevision;
//    List<Integer>reqIntegerList;
    List<CarStyleModel.DataBean.SpecReqItemBean> vehicleStdItem;
    String orderId;
    String orderAmt;
    String cityId;
    List<Integer> listTypes = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
//        reqIntegerList = (List<Integer>) getIntent().getSerializableExtra("list");
        reqList = (List<String>) getIntent().getSerializableExtra("reqList");
        vehicleStdItem = (List<CarStyleModel.DataBean.SpecReqItemBean>) getIntent().getSerializableExtra("vehicleStdItem");
        orderId = getIntent().getStringExtra("orderId");
        zAddr = getIntent().getStringExtra("zAddr");
        xAddr = getIntent().getStringExtra("xAddr");
        cityId = getIntent().getStringExtra("cityId");
        reserve_time = getIntent().getStringExtra("time");
        carStyle = getIntent().getStringExtra("carStyle");
        price = getIntent().getStringExtra("price");
        zName = getIntent().getStringExtra("zName");
        zPhone = getIntent().getStringExtra("zPhone");
        xName = getIntent().getStringExtra("xName");
        xPhone = getIntent().getStringExtra("xPhone");
        orderTime = getIntent().getStringExtra("orderTime");
        id = getIntent().getStringExtra("id");
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        orderAmt = getIntent().getStringExtra("orderAmt");
        cityInfoRevision = getIntent().getStringExtra("cityInfoRevision");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.huo_activity_order_confirm);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        setTranslucentStatus();
        EventBus.getDefault().register(this);
        tv_z.setText(zAddr);
        tv_x.setText(xAddr);
        tv_car.setText(carStyle);
        tv_use_car.setText(reserve_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ReqAdapter reqAdapter = new ReqAdapter(R.layout.item_req,reqList);
        recyclerView.setAdapter(reqAdapter);
        tv_total.setText(price);
        getCoupon(orderTime,reserve_time,cityId,id,lat,lon,price);
        getTime();
    }

    @Override
    public void setClickEvent() {
        rl_address.setOnClickListener(this);
        ll_user.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_receipt.setOnClickListener(this);
        ll_receipt.setOnClickListener(this);
        rl_beizhu.setOnClickListener(this);
        rl_other.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        tv_use_car.setOnClickListener(this);
    }

    boolean isChoose1 = false;
    boolean isChoose2 = false;
    HuoCouponDialog huoCouponDialog;
    HuoOtherDialog huoOtherDialog;
    HuoBeizhuDialog huoBeizhuDialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_use_car:
                showPickerView();
                break;

            case R.id.tv_pay:
                lav_loading.show();
                if(tv_contact.equals("")|| TextUtils.isEmpty(tv_contact.getText().toString())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写联系人姓名");
                    lav_loading.hide();
                    return;
                }

                if(tv_contact_phone.equals("")|| TextUtils.isEmpty(tv_contact_phone.getText().toString())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写联系人电话");
                    lav_loading.hide();
                    return;
                }
                getOrderAddress(cityId,cityInfoRevision,id,coupon_id,StringUtils.join(listTypes, ","),jsonArray1,orderTime,reserve_time
                        ,zName,zPhone,remark,orderId,invoiceType, StringUtils.join(reqList, ","));
                break;

            case R.id.rl_other:
                if(huoOtherDialog==null) {
                    huoOtherDialog = new HuoOtherDialog(mContext, vehicleStdItem) {
                        @Override
                        public void Confirm(List<String> list, List<Integer> listType) {
                            listTypes = listType;
                            String join = StringUtils.join(list, ",");
                            tv_other.setText(join);
                            getPrice(cityId,cityInfoRevision,
                                    id,coupon_id, StringUtils.join(listTypes, ","),
                                    jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
                            dismiss();
                        }
                    };
                }
                huoOtherDialog.setCanceledOnTouchOutside(false);
                huoOtherDialog.show();
                break;

            case R.id.rl_beizhu:
                if(huoBeizhuDialog==null) {
                    huoBeizhuDialog = new HuoBeizhuDialog(mContext);
                }

                huoBeizhuDialog.show();
                break;

            case R.id.tv_receipt:
                if(!isChoose1) {
                    isChoose1 = true;
                    invoiceType = 1;
                    tv_receipt.setBackgroundResource(R.drawable.shape_yellow1);
                    tv_receipt.setTextColor(Color.parseColor("#FD6601"));
                    tv_receipt1.setTextColor(Color.parseColor("#666666"));
                    tv_receipt2.setTextColor(Color.parseColor("#666666"));
                    if(isChoose2) {
                        isChoose2 = false;
                        ll_receipt.setBackgroundResource(R.drawable.shape_grey8);
                    }else {
                    }
                    getPrice(cityId,cityInfoRevision,
                            id,coupon_id, StringUtils.join(listTypes, ","),
                            jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
                }else {
                    invoiceType = 0;
                    isChoose1 = false;
                    tv_receipt.setBackgroundResource(R.drawable.shape_grey8);
                    tv_receipt.setTextColor(Color.parseColor("#666666"));
                    getPrice(cityId,cityInfoRevision,
                            id,coupon_id, StringUtils.join(listTypes, ","),
                            jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
                }

                break;

            case R.id.ll_receipt:
                if(!isChoose2) {
                    isChoose2 = true;
                    invoiceType = 2;
                    tv_receipt1.setTextColor(Color.parseColor("#FD6601"));
                    tv_receipt2.setTextColor(Color.parseColor("#FD6601"));
                    ll_receipt.setBackgroundResource(R.drawable.shape_yellow1);
                    if(isChoose1) {
                        isChoose1 = false;
                        tv_receipt.setBackgroundResource(R.drawable.shape_grey8);
                        tv_receipt.setTextColor(Color.parseColor("#666666"));
                    }

                    getPrice(cityId,cityInfoRevision,
                            id,coupon_id, StringUtils.join(listTypes, ","),
                            jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
                }else {
                    invoiceType = 0;
                    isChoose2 = false;
                    tv_receipt1.setTextColor(Color.parseColor("#666666"));
                    tv_receipt2.setTextColor(Color.parseColor("#666666"));
                    ll_receipt.setBackgroundResource(R.drawable.shape_grey8);
                    getPrice(cityId,cityInfoRevision,
                            id,coupon_id, StringUtils.join(listTypes, ","),
                            jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
                }

                break;
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_address:
                HuoAddressDialog huoAddressDialog = new HuoAddressDialog(mContext,zName,xName,zPhone,xPhone,zAddr,xAddr);
                huoAddressDialog.show();
                break;

            case R.id.ll_user:
                HuoContactDialog huoContactDialog = new HuoContactDialog(mContext);
                huoContactDialog.show();
                break;

            case R.id.rl_coupon:
                if(dataList.size()>0) {
                    if(huoCouponDialog==null) {
                        huoCouponDialog = new HuoCouponDialog(mContext,dataList);
                    }
                    huoCouponDialog.setCanceledOnTouchOutside(false);
                    huoCouponDialog.show();
                }

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
                String dateStr = dayList.get(options1).getDateStr();
                String hourStr = hoursList.get(options1).get(options2).getHourStr();
                String minute = minList.get(options1).get(options2).get(options3).getDateTime();
                tv_use_car.setText(minute);
                reserve_time = minute;
            }
        })

                .setTitleText("预计时间")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(dayList, hoursList, minList);//三级选择器
        orderTime = "1";

        pvOptions.show();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContact(HuoCouponEvent huoCouponEvent) {
        tv_coupon.setText(huoCouponEvent.getAmount());
        coupon_id = huoCouponEvent.getCoupon_id();
        getPrice(cityId,cityInfoRevision,
                id,coupon_id, StringUtils.join(listTypes, ","),
                jsonArray1, Integer.parseInt(orderTime),reserve_time,StringUtils.join(reqList, ","),invoiceType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContact(HuoOrderContactEvent huoOrderContactEvent) {
        tv_contact.setText(huoOrderContactEvent.getEtName());
        tv_contact_phone.setText(huoOrderContactEvent.getEtPhone());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContacts(HuoBeizhuEvent huoBeizhuEvent) {
        tv_beizhu.setText(huoBeizhuEvent.getEtDesc());
        remark = huoBeizhuEvent.getEtDesc();
    }




    JSONArray jsonArray1;
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getAddress(JSONEvent jsonEvent) {
        jsonArray1 = jsonEvent.getJsonArray1();
        Log.d("xasfswdefew.....",jsonEvent.getJsonArray1().toString()+"b");
    }


    List<HuoCouponModel.DataBean> dataList = new ArrayList<>();
    List<HuoCouponModel.DataBean> data;
    private void getCoupon(String orderTime,String reserve_time,String city_id,String order_vehicle_id,
                           String lat,String lon,String price) {
        HuolalaAPI.getCoupon(mContext,orderTime,reserve_time,city_id,order_vehicle_id,lat,lon,price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoCouponModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoCouponModel huoCouponModel) {
                        if(huoCouponModel.getCode()==1) {
                            if(huoCouponModel.getData()!=null) {
                                data = huoCouponModel.getData();
                                dataList.clear();
                                dataList.addAll(huoCouponModel.getData());
                                if(data.size()>0) {
                                    tv_coupon.setText("您有"+data.size()+"张优惠券可用");
                                }else {
                                    tv_coupon.setText("暂无可用优惠券");
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoCouponModel.getMessage());
                        }
                    }
                });
    }


    String coupon_id;
    String remark;
    int invoiceType;
    private void getOrderAddress(String city_id,String cityInfoRevision,String id,String coupon_id,
                           String spec_req,JSONArray jsonArray1,String orderTime,String reserve_time,String contact_name,String contact_phone_no,
                                 String remark,String orderId,int invoiceType,String vehicle_std) {
        HuolalaAPI.getOrderAddress(mContext,city_id,cityInfoRevision,id,coupon_id,spec_req,jsonArray1,
                orderTime,reserve_time,contact_name,contact_phone_no,remark,orderId,invoiceType,vehicle_std)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_loading.hide();
                    }

                    @Override
                    public void onNext(HuoPayModel huoPayModel) {
                        if(huoPayModel.getCode()==1) {
                            if(huoPayModel.getData()!=null) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(huoPayModel.getData().getCashier_url()));
                                if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                                    intent.resolveActivity(mActivity.getPackageManager());
                                    mActivity.startActivity(Intent.createChooser(intent, "请选择浏览器"));
                                }
//                                mContext.startActivity(CommonH7Activity.getIntent(mContext, CommonH7Activity.class, huoPayModel.getData().getCashier_url()));
                                finish();
                            }
                            lav_loading.hide();
                        }else {
                            lav_loading.hide();
                            ToastUtil.showSuccessMsg(mActivity,huoPayModel.getMessage());
                        }
                    }
                });
    }

    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

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
                                tv_total.setText(priceModel.getData().getTotal_price());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,priceModel.getMessage());
                        }
                    }
                });
    }
}
