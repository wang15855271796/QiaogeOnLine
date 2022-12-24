package com.puyue.www.qiaoge.fragment.order;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

//import com.amap.api.maps.AMap;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.BeizhuActivity;
import com.puyue.www.qiaoge.activity.flow.FlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagsFlowLayout;
import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponssActivity;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.adapter.FullConfirmAdapter;
import com.puyue.www.qiaoge.adapter.FullGivenConfirmAdapter;
import com.puyue.www.qiaoge.adapter.TagsAdapter;
import com.puyue.www.qiaoge.adapter.UnOperate1Adapter;
import com.puyue.www.qiaoge.adapter.UnOperateAdapter;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ConfirmOrderNewAdapter;
import com.puyue.www.qiaoge.api.cart.CartBalanceAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.mine.order.GenerateOrderAPI;
import com.puyue.www.qiaoge.api.mine.order.GetOrderDeliverTimeAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.DisSelfDialog;
import com.puyue.www.qiaoge.dialog.OperateDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BeizhuEvent;
import com.puyue.www.qiaoge.event.ChooseCoupon2Event;
import com.puyue.www.qiaoge.event.ChooseCouponsEvent;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.DisTributionSelf1Event;
import com.puyue.www.qiaoge.event.DisTributionSelfEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.fragment.mine.coupons.PaymentFragment;
import com.puyue.www.qiaoge.helper.ActivityResultHelper;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.MapHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.ModeModel;
import com.puyue.www.qiaoge.model.StatModel;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.order.GenerateOrderModel;
import com.puyue.www.qiaoge.model.mine.order.GetTimeOrderModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.GCJ02ToWGS84Util;
import com.puyue.www.qiaoge.view.PickCityUtil;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.httpresponse.Poi;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.TransitResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.ItemizedOverlay;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.OverlayItem;
import com.tencent.tencentmap.mapsdk.map.Projection;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderSufficiencyFragment extends BaseFragment {
    private RecyclerView recyclerView;
    RecyclerView recyclerView_un;
    private LinearLayout linearLayoutAddressHead;
    private TextView userName;
    private TextView userPhone;
    private int currentDay;
    private TextView address;
    private TextView firmName; // 店名
    private LinearLayout LinearLayoutAddress;// 没地址的xml
    private TextView textViewNum; // 几件商品
    private TextView commodityAmount; // 商品总额
    private TextView distributionFee; // 配送活动
    private TextView distributionFeePrice; // 配送费
    private TextView textCoupons; //优惠劵
    private EditText messageEditText; // 买家留言
    private TextView totalPrice;  // 总价
    private TextView textViewDiscount; // 已优惠
    private TextView buttonPay; // 去支付
    private TextView commodity;
    private TextView payMoney; // 支付金额
    private LinearLayout LinearLayoutStoreName; // 店名xml

    private LinearLayout linearLayoutCoupons;// 优惠券xml
    private String orderId;
    //自营
    private ConfirmOrderNewAdapter adapter;
    //非自营
    private UnOperateAdapter unOperateAdapter;
    //自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> list = new ArrayList<>();
    //非自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate = new ArrayList<>();
    // 获取想去的参数 和获取订单的参数
    private String normalProductBalanceVOStr;
    private String activityBalanceVOStr;
    private String equipmentBalanceVOStr;
    private String cartListStr;
    private String NewgiftDetailNo = "";
    private String couponId = "";

    private String payAmount = "";
    // 判断是否匹配优惠券，0否1是，默认1
    CartBalanceModel cModel;


    private RelativeLayout relativeLayoutVIP; //
    private LinearLayout vipSubtractionLinearLayout;
    private LinearLayout subtractionActivitiesLinearLayout;
    private TextView textViewVipTitle;
    private ImageView imVipButton;
    private TextView vipSubtraction;
    private TextView subtractionActivities;
    private TextView vipSubtractionPrice;
    private String VipURl;
    //记录图片的点击次数，设置一开始为0.
//    private int j=0;
    //请求次数
    private int requestCount = 0;
    private String deliverTimeStart = "";
    private String deliverTimeEnd = "";

    private String deliverTimeName = "";
    TextView tv_unOperate;
    TextView tv_operate;
    private LinearLayout ll_collect_bills;
    private LinearLayout ll_go_market;
    private TextView tv_amount_spec;
    private TextView tv_vip_content_one;
    private TextView tv_vip_content_two;
    private TextView tv_go;
    RelativeLayout ll_beizhu;
    StatModel statModel;
    private EditText et_name;
    private EditText et_phone;
    MapView mMapView;
    double latitude1;//仓库位置
    double longitude1;

    private TextView tv_address;
    TextView tv_num;
    AVLoadingIndicatorView lav_activity_loading;
    private BottomSheetDialog mDialogMap;
    private String title;
    private String content;
    private ImageView iv_operate_title;
    private RelativeLayout rl_unOperate;
    RelativeLayout rl_unOperate1;
    private LinearLayout iv_time_arrow;
    private TextView tv_year;
    private TextView tv_hour;

    TextView tv_open;
    ImageView iv_open;
    private String mYear;
    private String mHour;
    private TextView et_time;

    RelativeLayout rl_arrow;
    TextView tv_beizhu;
    private TextView tv_phone;
    TextView tv_full_price;
    RelativeLayout rl_distribution;
    private LinearLayout ll_self_sufficiency;
    TextView tv_distribution;
    com.tencent.tencentmap.mapsdk.maps.MapView mapView;
    LinearLayout ll_root;
    RelativeLayout rl_no_Data;
    ImageView iv_self_arrow;
    TextView tv_dis;
    UnOperate1Adapter unOperate1Adapter;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_confirm_sufficiency_order;
    }

    @Override
    public void initViews(View view) {
    }

    SupportMapFragment supportMapFragment;
    com.tencent.tencentmap.mapsdk.maps.TencentMap mapss;
    @Override
    public void findViewById(View view) {
        tv_dis = view.findViewById(R.id.tv_dis);
        iv_self_arrow = view.findViewById(R.id.iv_self_arrow);
        tv_open = view.findViewById(R.id.tv_open);
        iv_open = view.findViewById(R.id.iv_open);
        rl_no_Data = view.findViewById(R.id.rl_no_Data);
        ll_root = view.findViewById(R.id.ll_root);
        mapView = view.findViewById(R.id.mapView);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag);
        rl_distribution = (RelativeLayout) view.findViewById(R.id.rl_distribution);
        tv_distribution = (TextView) view.findViewById(R.id.tv_distribution);
        rl_arrow = (RelativeLayout) view.findViewById(R.id.rl_arrow);
        rl_unOperate1 = (RelativeLayout) view.findViewById(R.id.rl_unOperate1);
        rl_unOperate = (RelativeLayout) view.findViewById(R.id.rl_unOperate);
        iv_operate_title = (ImageView) view.findViewById(R.id.iv_operate_title);
        tv_unOperate = (TextView) view.findViewById(R.id.tv_unOperate);
        tv_operate = (TextView) view.findViewById(R.id.tv_operate);
        tv_beizhu = (TextView) view.findViewById(R.id.tv_beizhu);
        tv_full_price = (TextView) view.findViewById(R.id.tv_full_price);
        lav_activity_loading = (AVLoadingIndicatorView) view.findViewById(R.id.lav_activity_loading);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView_un = (RecyclerView) view.findViewById(R.id.recyclerView_un);
        userName = (TextView) view.findViewById(R.id.userName);
        userPhone = (TextView) view.findViewById(R.id.userPhone);
        address = (TextView) view.findViewById(R.id.address);
        firmName = (TextView) view.findViewById(R.id.firmName);
        linearLayoutAddressHead = (LinearLayout) view.findViewById(R.id.linearLayoutAddressHead);
        LinearLayoutAddress = (LinearLayout) view.findViewById(R.id.LinearLayoutAddress);
        textViewNum = (TextView) view.findViewById(R.id.textViewNum);
        commodityAmount = (TextView) view.findViewById(R.id.commodityAmount);
        distributionFee = (TextView) view.findViewById(R.id.distributionFee);
        distributionFeePrice = (TextView) view.findViewById(R.id.distributionFeePrice);
        textCoupons = (TextView) view.findViewById(R.id.textCoupons);
        messageEditText = (EditText) view.findViewById(R.id.messageEditText);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        textViewDiscount = (TextView) view.findViewById(R.id.textViewDiscount);
        buttonPay = (TextView) view.findViewById(R.id.buttonPay);
        commodity = (TextView) view.findViewById(R.id.commodity);
        payMoney = (TextView) view.findViewById(R.id.payMoney);
        LinearLayoutStoreName = (LinearLayout) view.findViewById(R.id.LinearLayoutStoreName);
        linearLayoutCoupons = (LinearLayout) view.findViewById(R.id.linearLayoutCoupons);
        relativeLayoutVIP = (RelativeLayout) view.findViewById(R.id.relativeLayoutVIP);
        vipSubtractionLinearLayout = (LinearLayout) view.findViewById(R.id.vipSubtractionLinearLayout);
        subtractionActivitiesLinearLayout = (LinearLayout) view.findViewById(R.id.subtractionActivitiesLinearLayout);
        textViewVipTitle = (TextView) view.findViewById(R.id.textViewVipTitle);
        imVipButton = (ImageView) view.findViewById(R.id.imVipButton);
        vipSubtraction = (TextView) view.findViewById(R.id.vipSubtraction);
        subtractionActivities = (TextView) view.findViewById(R.id.subtractionActivities);
        vipSubtractionPrice = (TextView) view.findViewById(R.id.vipSubtractionPrice);
        ll_collect_bills = (LinearLayout) view.findViewById(R.id.ll_collect_bills);
        ll_go_market = (LinearLayout) view.findViewById(R.id.ll_go_market);
        tv_amount_spec = (TextView) view.findViewById(R.id.tv_amount_spec);
        tv_vip_content_one = (TextView) view.findViewById(R.id.tv_vip_content_one);
        tv_vip_content_two = (TextView) view.findViewById(R.id.tv_tv_vip_content_two);
        tv_go = (TextView) view.findViewById(R.id.tv_go);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        iv_time_arrow = (LinearLayout) view.findViewById(R.id.iv_time_arrow);
        tv_year = (TextView) view.findViewById(R.id.tv_year);
        tv_hour = (TextView) view.findViewById(R.id.tv_hour);
        et_time = (TextView) view.findViewById(R.id.et_time);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        ll_self_sufficiency = (LinearLayout) view.findViewById(R.id.ll_self_sufficiency);
        ll_beizhu = (RelativeLayout) view.findViewById(R.id.ll_beizhu);

        mapss = supportMapFragment.getMap();
        mapss.setOnMapClickListener(new com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(com.tencent.tencentmap.mapsdk.maps.model.LatLng latLng) {
                showMapDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    TencentLocationManager instance;
    @Override
    public void setViewData() {

        instance = TencentLocationManager.getInstance(QiaoGeApplication.getContext());

        TencentLocationListener mLocationListener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation location, int i, String s) {

            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        };
        instance.requestSingleFreshLocation(null, mLocationListener, Looper.getMainLooper());

        ll_self_sufficiency.setVisibility(View.GONE);
        final Calendar mCalendar = Calendar.getInstance();
        long time = System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        normalProductBalanceVOStr = mActivity.getIntent().getStringExtra("normalProductBalanceVOStr");
        activityBalanceVOStr = mActivity.getIntent().getStringExtra("activityBalanceVOStr");
        equipmentBalanceVOStr = mActivity.getIntent().getStringExtra("equipmentBalanceVOStr");
        cartListStr = mActivity.getIntent().getStringExtra("cartListStr");

        list.clear();
        adapter = new ConfirmOrderNewAdapter(R.layout.item_confirm_order_new, list);
        unOperateAdapter = new UnOperateAdapter(R.layout.item_confirm_order_new, listUnOperate);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        iv_time_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_time_arrow.setEnabled(false);
                showGetTime();
            }
        });
        requestCartBalance(NewgiftDetailNo, 1,disType);//NewgiftDetailNo
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGetTime();
            }
        });

        getMode();
    }

    int disType = 1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDistribution(DisTributionSelfEvent disTributionEvent) {
        tv_distribution.setText(disTributionEvent.getDesc());
        disType = 1;
        iv_self_arrow.setImageResource(R.mipmap.iv_deliver_arrow);
    }

    com.tencent.tencentmap.mapsdk.maps.model.LatLng latLng1;
    com.tencent.tencentmap.mapsdk.maps.model.LatLng latLng;
    protected void geocoder(String address) {
        TencentSearch tencentSearch = new TencentSearch(mActivity);
        Address2GeoParam address2GeoParam = new Address2GeoParam(address).region(UserInfoHelper.getCity(mActivity));
        tencentSearch.address2geo(address2GeoParam, new HttpResponseListener<BaseObject>() {
            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Address2GeoResultObject obj = (Address2GeoResultObject)arg1;
                StringBuilder sb = new StringBuilder();
                sb.append("地址解析");
                if (obj.result.latLng != null) {
                    sb.append("\n坐标：" + obj.result.latLng.toString());
                } else {
                    sb.append("\n无坐标");
                }
                latLng = obj.result.latLng;
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new com.tencent.tencentmap.mapsdk.maps.model.LatLng(latLng.latitude,latLng.longitude),
                                15,
                                0f,
                                0f));
                //移动地图
                mapss.moveCamera(cameraSigma);
                getWalkingRoute(latLng);

                //用户地址
                Address2GeoParam address2GeoParam1 = new Address2GeoParam(UserInfoHelper.getProvince(mActivity)+UserInfoHelper.getCity(mActivity)
                        +UserInfoHelper.getAreaName(mActivity)).region(UserInfoHelper.getCity(mActivity));
                tencentSearch.address2geo(address2GeoParam1, new HttpResponseListener<BaseObject>() {
                    @Override
                    public void onSuccess(int arg0, BaseObject arg1) {
                        if (arg1 == null) {
                            return;
                        }
                        Address2GeoResultObject obj = (Address2GeoResultObject)arg1;
                        StringBuilder sb = new StringBuilder();
                        sb.append("地址解析");
                        if (obj.result.latLng != null) {
                            sb.append("\n坐标：" + obj.result.latLng.toString());
                        } else {
                            sb.append("\n无坐标");
                        }
                        latLng1 = obj.result.latLng;

                        DrivingParam drivingParam = new DrivingParam(latLng, latLng1); //创建导航参数

                        drivingParam.roadType(DrivingParam.RoadType.ON_MAIN_ROAD_BELOW_BRIDGE);
                        drivingParam.heading(90);
                        drivingParam.accuracy(30);
                        TencentSearch tencentSearch1 = new TencentSearch(mActivity);

                        tencentSearch1.getRoutePlan(drivingParam, new HttpResponseListener<DrivingResultObject>() {

                            @Override
                            public void onSuccess(int i, DrivingResultObject drivingResultObject) {
                                tv_dis.setText("距您"+Math.ceil(drivingResultObject.result.routes.get(0).distance/1000)+"公里");
                            }

                            @Override
                            public void onFailure(int i, String s, Throwable throwable) {
                            }
                        });

                    }

                    @Override
                    public void onFailure(int arg0, String arg1, Throwable arg2) {

                    }
                });

            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });

    }

    private void getWalkingRoute(com.tencent.tencentmap.mapsdk.maps.model.LatLng latLng){
        MarkerOptions options = new MarkerOptions(latLng);
        options.infoWindowEnable(true);//默认为true
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_confirm_map));//设置自定义Marker图标
        mapss.addMarker(options);
    }


    private void getDatas(long end) {
        RecommendApI.getDatas(mActivity,17,end)
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

                    }
                });
    }



    private GetTimeOrderModel dataBean;

    private void showGetTime() {
        GetOrderDeliverTimeAPI.requestOrderSelfTime(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetTimeOrderModel>() {
                    @Override
                    public void onCompleted() {
                        iv_time_arrow.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iv_time_arrow.setEnabled(true);
                    }

                    @Override
                    public void onNext(GetTimeOrderModel getTimeOrderModel) {

                        List<String> listYear = new ArrayList<>();

                        List<List<String>> listTimer = new ArrayList<>();
                        if (getTimeOrderModel.isSuccess()) {
                            iv_time_arrow.setEnabled(true);
                            dataBean = getTimeOrderModel;

                            for (int i = 0; i < getTimeOrderModel.getData().size(); i++) {
                                listYear.add(getTimeOrderModel.getData().get(i).getDateTime());
                            }

                            for (int i = 0; i < getTimeOrderModel.getData().size(); i++) {
                                List<String> listTime = new ArrayList<>();
                                for (int j = 0; j < getTimeOrderModel.getData().get(i).getDetailTime().size(); j++) {
                                    listTime.add(getTimeOrderModel.getData().get(i).getDetailTime().get(j).getName() + "(" + getTimeOrderModel.getData().get(i).getDetailTime().get(j).getStartTime() + "-" + getTimeOrderModel.getData().get(i).getDetailTime().get(j).getEndTime() + ")");

                                }
                                listTimer.add(listTime);
                            }
                        }
                        PickCityUtil.showDoublePickView(mActivity, listYear, listTimer, "请选择自提时间段", new PickCityUtil.ChooseDPositionListener() {
                            @Override
                            public void choosePosition(int position1, int position2, String s) {
                                et_time.setVisibility(View.GONE);
                                tv_hour.setVisibility(View.VISIBLE);
                                tv_year.setVisibility(View.VISIBLE);
                                mYear = listYear.get(position1);

                                tv_year.setText(mYear);
                                deliverTimeStart = dataBean.getData().get(position1).getDetailTime().get(position2).getStartTime();
                                deliverTimeEnd = dataBean.getData().get(position1).getDetailTime().get(position2).getEndTime();
                                deliverTimeName = dataBean.getData().get(position1).getDetailTime().get(position2).getName();
                                tv_hour.setText(dataBean.getData().get(position1).getDetailTime().get(position2).getStartTime() + "-" + dataBean.getData().get(position1).getDetailTime().get(position2).getEndTime());


                            }
                        });
                    }
                });


        List<String> list1 = new ArrayList<>();
        list1.clear();
        list1.add("AAA");
        list1.add("BBB");
        list1.add("CCC");
        list1.add("DDD");

        List<String> list2 = new ArrayList<>();
        list2.clear();
        list2.add("aaa");
        list2.add("bbb");
        list2.add("ccc");
        list2.add("ddd");

        List<List<String>> list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < list1.size(); i++) {
            list.add(list2);
        }


    }

    boolean isOpen = false;
    @Override
    public void setClickEvent() {
        statModel = new StatModel();
        buttonPay.setOnClickListener(noDoubleClickListener);
        linearLayoutCoupons.setOnClickListener(noDoubleClickListener);
        imVipButton.setOnClickListener(noDoubleClickListener);
        ll_go_market.setOnClickListener(noDoubleClickListener);
        ll_beizhu.setOnClickListener(noDoubleClickListener);
        rl_distribution.setOnClickListener(noDoubleClickListener);

        rl_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpen) {
                    tv_open.setText("收起");
                    iv_open.setImageResource(R.mipmap.icon_arrow_up);
                    unOperate1Adapter.setOpenList(openList);
                }else {
                    tv_open.setText("展开");
                    unOperate1Adapter.setHideList(hideList);
                    iv_open.setImageResource(R.mipmap.icon_arrow_down);
                }
                unOperate1Adapter.notifyDataSetChanged();
                isOpen = !isOpen;
            }
        });

    }

    DisSelfDialog disDialog;
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.rl_distribution:
                    if(cModel!=null&&cModel.getData()!=null) {
                        if(disDialog == null) {
                            disDialog = new DisSelfDialog(mActivity,cModel.getData().getSendAmount(),0);
                        }
                        disDialog.show();
                    }

                    break;
                case R.id.ll_beizhu:
                    Intent intents = new Intent(mActivity,BeizhuActivity.class);
                    intents.putExtra("beizhu",tv_beizhu.getText().toString()+"");
                    startActivity(intents);
                    break;
                case R.id.linearLayoutAddressHead: // 地址切换
                   // Intent intent_ = new Intent(mActivity, AddressListActivity.class);
                   // intent_.putExtra("type", 1);
                   // startActivityForResult(intent_, 31);
                    break;
                case R.id.LinearLayoutAddress: // 添加地址
                //    startActivityForResult(AddressListActivity.getIntent(mActivity, AddressListActivity.class), 32);

                    break;
                case R.id.buttonPay:// 去支付
                    getDatas(1);
                    if(list.size()==0) {
                        ToastUtil.showSuccessMsg(mActivity,"无可结算的商品");
                        return;
                    }
                    lav_activity_loading.show();
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    if(tv_distribution.getText().toString().equals("")) {
                        AppHelper.showMsg(mActivity, "请选择配送服务");
                        buttonPay.setEnabled(true);
                        lav_activity_loading.hide();
                        if(cModel!=null&&cModel.getData()!=null) {
                            if(disDialog == null) {
                                disDialog = new DisSelfDialog(mActivity,cModel.getData().getSendAmount(),1);
                            }
                            disDialog.show();
                        }

                        return;
                    }

                    if (LinearLayoutAddress.getVisibility() == View.VISIBLE) { // 没有地址
                        AppHelper.showMsg(mActivity, "请填写地址");
                    } else {
                        buttonPay.setEnabled(false);
                        lav_activity_loading.show();
                        lav_activity_loading.setVisibility(View.VISIBLE);
                        if (et_name.getText().toString() != null) {
                            if (et_phone.getText().toString() != null) {
                                if (mYear != null) {
                                    if(listUnOperate.size()> 0) {
                                        operateDialog = new OperateDialog(mActivity,listUnOperate,info.getTotalAmount()) {
                                            @Override
                                            public void Confirm() {
                                                EventBus.getDefault().post(new RefreshEvent());
                                                operateDialog.dismiss();
                                                lav_activity_loading.hide();
                                                buttonPay.setEnabled(true);
                                            }

                                            @Override
                                            public void Cancle() {
                                                requestOrderNum();
                                            }

                                            @Override
                                            public void Close() {
                                                dismiss();
                                                buttonPay.setEnabled(true);
                                                lav_activity_loading.hide();
                                            }
                                        };

                                        operateDialog.show();
                                    }else {
                                        requestOrderNum();
                                    }

                                } else {
                                    AppHelper.showMsg(mActivity, "请选择取货时间");
                                    lav_activity_loading.hide();
                                    lav_activity_loading.setVisibility(View.GONE);
                                    buttonPay.setEnabled(true);
                                }
                            } else {
                                AppHelper.showMsg(mActivity, "请输入提货人手机号");
                            }

                        } else {
                            AppHelper.showMsg(mActivity, "请输入提货人姓名");
                        }

                    }
                    break;
                case R.id.linearLayoutCoupons: // 优惠券
                    Intent intent2 = new Intent(getContext(), ChooseCouponssActivity.class);
                    intent2.putExtra("statModel",statModel.isSelects());
                    intent2.putExtra("activityBalanceVOStr", activityBalanceVOStr);
                    intent2.putExtra("normalProductBalanceVOStr", normalProductBalanceVOStr);
                    intent2.putExtra("giftDetailNo", NewgiftDetailNo);
                    intent2.putExtra("deliveryModel",disType);
                    startActivityForResult(intent2, ActivityResultHelper.ChOOSE_COUPONS_REQUESR_CODE);

                    break;
                case R.id.ll_go_market:

                    Intent intent = new Intent(mActivity, NewWebViewActivity.class);
                    intent.putExtra("URL", VipURl);
                    intent.putExtra("name", "");

                    startActivity(intent);

                    break;
            }
        }
    };

    /**
     * 是否显示内容
     */
    ModeModel modeModel1;
    public void getMode() {
        IndexHomeAPI.getMode(mActivity,SharedPreferencesUtil.getInt(mActivity,"wad"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ModeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ModeModel modeModel) {
                        if(modeModel.getCode()==1) {
                            if(modeModel.getData()!=null) {
                                modeModel1 = modeModel;
                                if(modeModel.getData().getPickBtn()==0) {
                                    //展示
                                    ll_root.setVisibility(View.VISIBLE);
                                    rl_no_Data.setVisibility(View.GONE);
                                }else {
                                    //不展示
                                    ll_root.setVisibility(View.GONE);
                                    rl_no_Data.setVisibility(View.VISIBLE);
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,modeModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取数据的网络请求
     */
    List<CartBalanceModel.DataBean.ProductVOListBean> openList = new ArrayList<>();
    List<CartBalanceModel.DataBean.ProductVOListBean> hideList = new ArrayList<>();
    private void requestCartBalance(String giftDetailNo, int type,int disType) {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, giftDetailNo, type, 1,disType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartBalanceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CartBalanceModel cartBalanceModel) {
                        if (cartBalanceModel.success) {
                            cModel = cartBalanceModel;
                            title = cartBalanceModel.getData().wareAddress;
                            content = cartBalanceModel.getData().wareAddress;
                            tv_phone.setText( cartBalanceModel.getData().customerPhone);
                            if (cartBalanceModel != null) {
                                setText(cartBalanceModel);
                                if (requestCount == 0) {
                                    requestCount++;
                                }

                                list.clear();
                                listUnOperate.clear();
                                geocoder(cartBalanceModel.getData().wareAddress);
                                if (cartBalanceModel.getData().getProductVOList().size() > 0) {
                                    for (int i = 0; i < cartBalanceModel.getData().getProductVOList().size(); i++) {
                                        if(cartBalanceModel.getData().getProductVOList().get(i).getSelfOrNot()==0) {
                                            list.add(cartBalanceModel.getData().getProductVOList().get(i));
                                            adapter.notifyDataSetChanged();
                                        }else {
                                            listUnOperate.add(cartBalanceModel.getData().getProductVOList().get(i));
                                            unOperateAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }

                            if(list.size()>0) {
                                rl_unOperate.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                iv_operate_title.setVisibility(View.VISIBLE);
                            }else {
                                recyclerView.setVisibility(View.GONE);
                                rl_unOperate.setVisibility(View.GONE);
                                iv_operate_title.setVisibility(View.GONE);
                            }

                            if(listUnOperate.size()>0) {
                                rl_unOperate1.setVisibility(View.VISIBLE);
                                recyclerView_un.setVisibility(View.VISIBLE);
                                if(listUnOperate.size()>3) {
                                    rl_arrow.setVisibility(View.VISIBLE);
                                }else {
                                    rl_arrow.setVisibility(View.GONE);
                                }
                            }else {
                                rl_unOperate1.setVisibility(View.GONE);
                                recyclerView_un.setVisibility(View.GONE);
                            }

                        } else {
                            AppHelper.showMsg(mActivity, cartBalanceModel.message);
                        }

                        openList.clear();
                        hideList.clear();
                        if(listUnOperate.size()>3) {
                            for (int i = 0; i < listUnOperate.size(); i++) {
                                openList.add(listUnOperate.get(i));
                            }


                            for (int i = 0; i < 3; i++) {
                                hideList.add(listUnOperate.get(i));
                            }

                            unOperate1Adapter = new UnOperate1Adapter(mActivity);
                            recyclerView_un.setAdapter(unOperate1Adapter);
                            recyclerView_un.setLayoutManager(new LinearLayoutManager(mActivity));
                            unOperate1Adapter.setHideList(hideList);
                        }else {
                            unOperate1Adapter = new UnOperate1Adapter(mActivity);
                            recyclerView_un.setAdapter(unOperate1Adapter);
                            recyclerView_un.setLayoutManager(new LinearLayoutManager(mActivity));
                            unOperate1Adapter.setRealList(listUnOperate);
                        }
                    }
                });
    }


    /**
     * 接收来自接口的数据进行数据设置。
     *
     * @param cartBalanceModel
     */
    CartBalanceModel.DataBean info;
    private void setText(CartBalanceModel cartBalanceModel) {
        info = cartBalanceModel.getData();
        if(NewgiftDetailNo.equals("")) {
            if(cartBalanceModel.getData().getDeductDesc().equals("无优惠券")) {
                textCoupons.setText("无优惠券");
                textCoupons.setTextColor(Color.parseColor("#999999"));
                linearLayoutCoupons.setEnabled(false);
            }else if(cartBalanceModel.getData().getDeductDesc().equals("暂无可用优惠券")) {
                textCoupons.setText("暂无可用优惠券");
                textCoupons.setTextColor(Color.parseColor("#999999"));
                linearLayoutCoupons.setEnabled(true);
            } else {
                textCoupons.setText(cartBalanceModel.getData().getDeductDesc());
                textCoupons.setTextColor(Color.parseColor("#F25E0E"));
                linearLayoutCoupons.setEnabled(true);
            }
        }else {
            textCoupons.setText(cartBalanceModel.getData().getDeductDetail().getGiftName());
        }




        if (info.pickPhone != null) {
            et_phone.setText(info.pickPhone);
        }
        if (info.pickUserName != null) {
            et_name.setText(info.pickUserName);
        }

        tv_address.setText(info.wareAddress);
        textViewNum.setText("共" + info.getTotalNum() + "" + "件商品");
        distributionFeePrice.setText("¥" + info.getDeliveryFee());
        payMoney.setText("¥" + info.getTotalAmount());
        commodity.setText("共" + info.getTotalNum() + "件商品");
        totalPrice.setText("¥" + info.getTotalAmount());
        payAmount = info.getTotalAmount();
        commodityAmount.setText("¥" + info.getProdAmount() + "");

        distributionFee.setText("满" + info.getSendAmount() + "元免配送费");

        if (info.wareName!=null&&StringHelper.notEmptyAndNull(info.wareName)){
            address.setText(info.wareName);
        }
        if (info.getAddressVO().getContactPhone() != null && info.getAddressVO().getUserName() != null &&
                info.getAddressVO().getAreaName() != null ) {
            userName.setText(info.getAddressVO().getUserName());
            userPhone.setText(info.getAddressVO().getContactPhone());


            if (!TextUtils.isEmpty(info.getAddressVO().getShopName())) {
                firmName.setText(info.getAddressVO().getShopName());
            }
        }
        if (cartBalanceModel.getData().getOfferAmount() != null) {
            textViewDiscount.setText("已优惠¥" + cartBalanceModel.getData().getOfferAmount());
            textViewDiscount.setVisibility(View.VISIBLE);
        } else {
            textViewDiscount.setVisibility(View.GONE);

        }
        if (cartBalanceModel.getData().getDeductDetail() != null) {
            if (!TextUtils.isEmpty(cartBalanceModel.getData().getDeductDetail().getAmountStr())) {
                couponId = cartBalanceModel.getData().getDeductDetail().getGiftDetailNo();
                NewgiftDetailNo = cartBalanceModel.getData().getDeductDetail().getGiftDetailNo();////NewgiftDetailNo
            }
        }

        VipURl = cartBalanceModel.getData().getVipCenterUrl();
        vipSubtractionPrice.setText("¥" + cartBalanceModel.getData().getVipReduct());

        if (!TextUtils.isEmpty(cartBalanceModel.getData().getVipReductDesc())) {

            vipSubtraction.setText(cartBalanceModel.getData().getVipReductDesc());
            vipSubtraction.setVisibility(View.VISIBLE);
        } else {
            vipSubtraction.setVisibility(View.GONE);
        }
        if (cartBalanceModel.getData().isVip()) { // 是否开通vip
            ll_collect_bills.setVisibility(View.GONE);

            vipSubtractionLinearLayout.setVisibility(View.VISIBLE);
            relativeLayoutVIP.setVisibility(View.GONE);
            textViewVipTitle.setText(cartBalanceModel.getData().getNotVipDesc());
        } else {
            vipSubtractionLinearLayout.setVisibility(View.GONE);
            if (!cartBalanceModel.getData().isOpenVip()) {
                if (cartBalanceModel.getData().getVipDesc() > 0) {
                    ll_collect_bills.setVisibility(View.VISIBLE);
                    tv_vip_content_one.setText("续费会员本单立减");
                    tv_vip_content_two.setText("，随后享受单单满减优惠");
                    tv_go.setText("去续费");

                    tv_amount_spec.setText(cartBalanceModel.getData().getVipDesc().toString() + "" + "元");
                } else {
                    ll_collect_bills.setVisibility(View.GONE);
                }


            } else {
                ll_collect_bills.setVisibility(View.VISIBLE);
                tv_amount_spec.setText(cartBalanceModel.getData().getVipDesc().toString() + "" + "元");
                tv_vip_content_one.setText("开通会员本单立减");
                tv_vip_content_two.setText("，随后享受单单满减优惠");
                tv_go.setText("去开通");
            }
            relativeLayoutVIP.setVisibility(View.GONE);

        }

        tv_num.setText(cartBalanceModel.getData().getTotalNum()+"");
        if (cartBalanceModel.getData().isOfferIsOpen()) { // 活动满减
            subtractionActivitiesLinearLayout.setVisibility(View.VISIBLE);
            tv_full_price.setText("¥" + cartBalanceModel.getData().getNormalReduct());
            if (!TextUtils.isEmpty(cartBalanceModel.getData().getNormalReductDesc())) {
                subtractionActivities.setVisibility(View.VISIBLE);
                subtractionActivities.setText(cartBalanceModel.getData().getNormalReductDesc());
            } else {
                subtractionActivities.setVisibility(View.GONE);
            }

        } else {
            subtractionActivitiesLinearLayout.setVisibility(View.GONE);
            subtractionActivities.setVisibility(View.GONE);
        }
    }


    // 去支付
    OperateDialog operateDialog;
    private void requestOrderNum() {
        GenerateOrderAPI.requestGenerateOrder(mActivity, activityBalanceVOStr, normalProductBalanceVOStr, cartListStr, NewgiftDetailNo, messageEditText.getText().toString(),
                deliverTimeStart, deliverTimeEnd, deliverTimeName, 1, et_name.getText().toString(), et_phone.getText().toString(), mYear,0)//NewgiftDetailNo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GenerateOrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GenerateOrderModel generateOrderModel) {
                        if (generateOrderModel.success) {
                            orderId = generateOrderModel.getData();
                            PaymentFragment paymentFragment = new PaymentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("total", payAmount);
                            bundle.putString("payAmount",payAmount);
                            bundle.putString("remark","");
                            bundle.putString("orderId",orderId);
                            bundle.putString("orderDeliveryType","1");
                            paymentFragment.setArguments(bundle);
                            paymentFragment.setCancelable(false);
                            paymentFragment.show(getFragmentManager(),"paymentFragment");
                            lav_activity_loading.hide();
                            lav_activity_loading.setVisibility(View.GONE);

                        } else {
                            AppHelper.showMsg(mActivity, generateOrderModel.message);
                            lav_activity_loading.hide();
                            buttonPay.setEnabled(true);
                            lav_activity_loading.setVisibility(View.GONE);

                        }
                    }
                });
    }
    GoToConfirmDeliver mlisenter;


    public interface GoToConfirmDeliver {
        void jumpConfirmDeliver();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof GoToConfirmDeliver) {
            mlisenter = (GoToConfirmDeliver) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements GoToMarket");
        }

    }

    //把传递进来的activity对象释放掉
    @Override
    public void onDetach() {
        super.onDetach();
        mlisenter = null;
    }


    @Subscribe
    public void onEventMainThread(AddressEvent event) {
        list.clear();
        requestCartBalance(NewgiftDetailNo, 1,disType);
    }


    /**
     * 选中某一个优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCouponss(ChooseCouponsEvent chooseCouponEvent) {
        list.clear();
        NewgiftDetailNo = chooseCouponEvent.getGiftDetailNo();
        requestCartBalance(chooseCouponEvent.getGiftDetailNo(), 1,disType);
        statModel.setSelects(false);
    }

    /**
     * 未选优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupons(ChooseCoupon2Event chooseCouponEvent) {
        list.clear();
        NewgiftDetailNo = "";
        requestCartBalance("",1,disType);
        statModel.setSelects(true);
    }

    /**
     * 获取备注内容
     * @param beizhuEvent
     */
    BeizhuEvent beizhuEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBeizhu(BeizhuEvent beizhuEvent) {
        this.beizhuEvent = beizhuEvent;
        tv_beizhu.setText(beizhuEvent.getBeizhu());
    }

    public void showMapDialog() {
        mDialogMap = new BottomSheetDialog(mActivity);
        mDialogMap.setContentView(R.layout.dialog_map);
        mDialogMap.show();
        mDialogMap.findViewById(R.id.ll_dialog_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //百度
                goBaiduMap();
            }
        });
        mDialogMap.findViewById(R.id.ll_dialog_gaode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //高德
                goGaoDeMap();
            }
        });
        mDialogMap.findViewById(R.id.ll_dialog_tengxun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //腾讯
                goTengXunMap();
            }
        });
        mDialogMap.findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                mDialogMap.dismiss();
            }
        });
    }

    /**
     * 跳转百度地图
     */
    private void goBaiduMap() {
        if (MapHelper.isAvilible(mActivity, "com.baidu.BaiduMap")) {// 传入指定应用包名
            try {
                Intent intent = new Intent();
                intent.setData(Uri.parse("baidumap://map/marker?location=" + latitude1 + "," + longitude1 + "&title=" + title + "&content=" + content + "&traffic=on"));
                startActivity(intent); // 启动调用
                mDialogMap.dismiss();
            } catch (Exception e) {
                Log.e("intent", e.getMessage());
            }
        } else {
            // 未安装
            String mapUri = "http://api.map.baidu.com/marker?location=" + latitude1 + "," + longitude1 + "&title=" + title + "&content=" + content + "&output=html";
            Uri uri = Uri.parse(mapUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            mDialogMap.dismiss();
        }
    }

    /**
     * 跳转到高德地图
     */
    private void goGaoDeMap() {

        Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09togcj02(longitude1, latitude1);
        Double lon = stringDoubleMap.get("lon");
        Double lat = stringDoubleMap.get("lat");



        if (MapHelper.isAvilible(mActivity, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=翘歌烧烤&poiname=" + title + "&lat="
                        + lat
                        + "&lon="
                        + lon + "&dev=0");
                startActivity(intent);
                mDialogMap.dismiss();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            String mapUri = "http://uri.amap.com/marker?position=" + lon + "," + lat + "&name=" + title + "&src=" + content + "&coordinate=gaode&callnative=0";
            Uri uri = Uri.parse(mapUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            mDialogMap.dismiss();
        }

    }
    /**
     * 打开腾讯地图网页版
     */
    private void goTengXunMap() {
        Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09togcj02(longitude1, latitude1);
        Double lon = stringDoubleMap.get("lon");
        Double lat = stringDoubleMap.get("lat");

        String mapUri = "http://apis.map.qq.com/uri/v1/marker?marker=coord:" + lat + "," + lon + ";title:" + title + ";addr:" + content + "&referer=翘歌烧烤";
        Uri uri = Uri.parse(mapUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        mDialogMap.dismiss();
    }

    interface OnTapListener {
        void onTap(OverlayItem itemTap);
        void onEmptyTap(GeoPoint pt);
    }
}
