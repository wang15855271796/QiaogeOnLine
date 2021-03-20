package com.puyue.www.qiaoge.fragment.order;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.BeizhuActivity;

import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponsActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponssActivity;
import com.puyue.www.qiaoge.activity.mine.order.ConfirmNewOrderActivity;
import com.puyue.www.qiaoge.activity.mine.order.MyConfireOrdersActivity;
import com.puyue.www.qiaoge.adapter.UnOperateAdapter;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ConfirmOrderNewAdapter;
import com.puyue.www.qiaoge.api.cart.CartBalanceAPI;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.api.mine.order.GenerateOrderAPI;
import com.puyue.www.qiaoge.api.mine.order.GetOrderDeliverTimeAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.OperateDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BeizhuEvent;
import com.puyue.www.qiaoge.event.ChooseCoupon1Event;
import com.puyue.www.qiaoge.event.ChooseCoupon2Event;
import com.puyue.www.qiaoge.event.ChooseCouponEvent;
import com.puyue.www.qiaoge.event.ChooseCouponsEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.fragment.cart.CartFragment;
import com.puyue.www.qiaoge.fragment.mine.coupons.PaymentFragment;
import com.puyue.www.qiaoge.helper.ActivityResultHelper;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.MapHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.StatModel;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.order.GenerateOrderModel;
import com.puyue.www.qiaoge.model.mine.order.GetTimeOrderModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.GCJ02ToWGS84Util;
import com.puyue.www.qiaoge.view.PickCityUtil;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderSufficiencyFragment extends BaseFragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    RecyclerView recyclerView_un;
    private LinearLayout linearLayoutAddressHead;
    private TextView userName;
    private TextView userPhone;
    private int currentDay;
    private TextView address;
    private Double totalAmount;
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
    private String NewgiftDetailNo = "";//NewgiftDetailNo
    // 获取选择优惠券的参数
    private String proActAmount = "";
    private String teamAmount = "";
    private String killAmount = "";
    private String prodAmount = "";
    private String couponId = "";

    private String payAmount = "";
    // 判断是否匹配优惠券，0否1是，默认1
    CartBalanceModel cModel;
    //  优惠卷
    private RecyclerView couponsRecyclerView;
    private ChooseCouponsAdapter couponsAdapter;
    private List<UserChooseDeductModel.DataBean> couponsList = new ArrayList<>();
    private TextView noData;


    private RelativeLayout relativeLayoutVIP; //
    private LinearLayout vipSubtractionLinearLayout;
    private LinearLayout subtractionActivitiesLinearLayout;
    private TextView textViewVipTitle;
    private ImageView imVipButton;
    private TextView vipSubtraction;
    private TextView subtractionActivities;
    private TextView vipSubtractionPrice;
    private String VipURl;
    private String deductDetail = "";
    //记录图片的点击次数，设置一开始为0.
//    private int j=0;
    //请求次数
    private int requestCount = 0;
    private Double toRechargeAmount;
    private boolean toRecharge;
    private String areaContent;
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
    List<String> mlist = new ArrayList<>();
    RelativeLayout ll_beizhu;
    StatModel statModel;
    private EditText et_name;
    private EditText et_phone;

    BaiduMap mBaiduMap;
    private TextureMapView mMapView = null;
    private GeoCoder mCoder;
    double latitude1;//仓库位置
    double longitude1;

    double latitude2;//用户位置
    double longitude2;
    private TextView tv_address;
    TextView tv_num;
    AVLoadingIndicatorView lav_activity_loading;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private UiSettings mUiSettings;


    private BottomSheetDialog mDialogMap;
    private String title;
    private String content;
    private TextView tv_operate_title;
    private RelativeLayout rl_unOperate;

    private LinearLayout iv_time_arrow;
    private TextView tv_year;
    private TextView tv_hour;


    private String mYear;
    private String mHour;
    private TextView et_time;


    TextView tv_beizhu;
    private TextView tv_phone;
    TextView tv_full_price;
    private LinearLayout ll_self_sufficiency;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_confirm_sufficiency_order;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {
        //  toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rl_unOperate = (RelativeLayout) view.findViewById(R.id.rl_unOperate);
        tv_operate_title = (TextView) view.findViewById(R.id.tv_operate_title);
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
        couponsRecyclerView = (RecyclerView) view.findViewById(R.id.couponsRecyclerView);

        relativeLayoutVIP = (RelativeLayout) view.findViewById(R.id.relativeLayoutVIP);
        vipSubtractionLinearLayout = (LinearLayout) view.findViewById(R.id.vipSubtractionLinearLayout);
        subtractionActivitiesLinearLayout = (LinearLayout) view.findViewById(R.id.subtractionActivitiesLinearLayout);
        textViewVipTitle = (TextView) view.findViewById(R.id.textViewVipTitle);
        imVipButton = (ImageView) view.findViewById(R.id.imVipButton);
        vipSubtraction = (TextView) view.findViewById(R.id.vipSubtraction);
        subtractionActivities = (TextView) view.findViewById(R.id.subtractionActivities);
        vipSubtractionPrice = (TextView) view.findViewById(R.id.vipSubtractionPrice);
        noData = (TextView) view.findViewById(R.id.noData);
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
        mMapView = (TextureMapView) view.findViewById(R.id.bmapView);
        iv_time_arrow = (LinearLayout) view.findViewById(R.id.iv_time_arrow);
        tv_year = (TextView) view.findViewById(R.id.tv_year);
        tv_hour = (TextView) view.findViewById(R.id.tv_hour);
        et_time = (TextView) view.findViewById(R.id.et_time);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        ll_self_sufficiency = (LinearLayout) view.findViewById(R.id.ll_self_sufficiency);
        ll_beizhu = (RelativeLayout) view.findViewById(R.id.ll_beizhu);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
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
      //  requestCartBalance(NewgiftDetailNo, 1);//NewgiftDetailNo
        mBaiduMap = mMapView.getMap();
//普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        //默认显示地图标注
        //  mBaiduMap.showMapPoi(false);

        //通过设置enable为true或false 选择是否显示比例尺
        mMapView.showScaleControl(false);

        //通过设置enable为true或false 选择是否显示缩放按钮
        mMapView.showZoomControls(false);

        mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否禁用所有手势
        mUiSettings.setAllGesturesEnabled(false);

        adapter = new ConfirmOrderNewAdapter(R.layout.item_confirm_order_new, list);
        unOperateAdapter = new UnOperateAdapter(R.layout.item_confirm_order_new, listUnOperate);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        //
        recyclerView_un.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView_un.setAdapter(unOperateAdapter);


        iv_time_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_time_arrow.setEnabled(false);
                showGetTime();
            }
        });

        requestCartBalance(NewgiftDetailNo, 1);//NewgiftDetailNo
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGetTime();
            }
        });
    }




    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    return;
                } else {
                    latitude1 = geoCodeResult.getLocation().latitude;
                    longitude1 = geoCodeResult.getLocation().longitude;
                    getAddressLocation();

                }
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

        }


    };



    BaiduMap.OnMapClickListener listenerClick = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            Log.i("dwqrqr", "onMapClick: " + "我点击了");
            showMapDialog();
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };



    private void getAddressLocation() {
        LatLng cenpt = new LatLng(latitude1, longitude1);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        //定义Maker坐标点

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_confirm_map);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(cenpt)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        LatLng cenpt1 = new LatLng(120.08947, 30.397751);

        OverlayOptions position = new MarkerOptions().position(cenpt1);

        LatLng cenpt2 = new LatLng(120.126731, 30.336927);

        //用来构造InfoWindow的Button
        TextView button = new TextView(mActivity.getApplicationContext());

        if (latitude2>0||longitude2>0){
            if (latitude2 != 4.9E-324 || longitude2 != 4.9E-324) {
                button.setVisibility(View.VISIBLE);
                button.setBackgroundResource(R.drawable.popup_map);
                button.setTextColor(Color.parseColor("#FF666666"));
                button.setGravity(Gravity.CENTER_HORIZONTAL);

                if (getDistance(longitude1, latitude1, longitude2, latitude2) > 1000) {
                    long round = Math.round(getDistance(longitude1, latitude1, longitude2, latitude2) / 1000);
                    button.setText("距您" + String.valueOf(round) + "公里");
                } else {
                    button.setText("距您不到1公里");
                }

                //构造InfoWindow
//point 描述的位置点
//-100 InfoWindow相对于point在y轴的偏移量

                InfoWindow mInfoWindow = new InfoWindow(button, ((MarkerOptions) option).getPosition(), -50);

//使InfoWindow生效
                mBaiduMap.showInfoWindow(mInfoWindow);
            } else {
                button.setVisibility(View.GONE);
            }
        }else {
            button.setVisibility(View.GONE);
        }



 /*       //用来构造InfoWindow
        BitmapDescriptor mBitmap = BitmapDescriptorFactory.fromResource(R.drawable.circle_bg);

//响应点击的OnInfoWindowClickListener
        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                Toast.makeText(mActivity, "Click on InfoWindow", Toast.LENGTH_LONG).show();
            }
        };

//构造InfoWindow
//point 描述的位置点
//-100 InfoWindow相对于point在y轴的偏移量
      InfoWindow  mInfoWindow = new InfoWindow(mBitmap, cenpt1, -100, listener);

//使InfoWindow生效
        mBaiduMap.showInfoWindow(mInfoWindow);*/
    }


    //地球半径
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 根据经纬度查询距离
     *
     * @param lng1 经度
     * @param lat1 纬度
     * @param lng2 经度
     * @param lat2 纬度
     * @return
     */
    private static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS * 1000;
//	   s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
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


                         /*   if (getTimeOrderModel.getData().size() == 1) {
                                for (int i = 0; i < getTimeOrderModel.getData().get(0).getDetailTime().size(); i++) {
                                    
                                }
                            }*/


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


    @Override
    public void setClickEvent() {

       // linearLayoutAddressHead.setOnClickListener(noDoubleClickListener);
     //   LinearLayoutAddress.setOnClickListener(noDoubleClickListener);
        statModel = new StatModel();
        buttonPay.setOnClickListener(noDoubleClickListener);
        linearLayoutCoupons.setOnClickListener(noDoubleClickListener);
        imVipButton.setOnClickListener(noDoubleClickListener);
        ll_go_market.setOnClickListener(noDoubleClickListener);
        ll_beizhu.setOnClickListener(noDoubleClickListener);
    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
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

                    if (LinearLayoutAddress.getVisibility() == View.VISIBLE) { // 没有地址
                        AppHelper.showMsg(mActivity, "请填写地址");
                    } else {
                        buttonPay.setEnabled(false);
                        lav_activity_loading.show();
                        lav_activity_loading.setVisibility(View.VISIBLE);
                        if (et_name.getText().toString() != null) {
                            if (et_phone.getText().toString() != null) {
                                if (mYear != null) {
                                    requestOrderNum();
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
                    if(giftNum>0) {
                        Intent intent2 = new Intent(getContext(), ChooseCouponssActivity.class);
                        intent2.putExtra("statModel",statModel.isSelects());
                        intent2.putExtra("activityBalanceVOStr", activityBalanceVOStr);
                        intent2.putExtra("normalProductBalanceVOStr", normalProductBalanceVOStr);
                        intent2.putExtra("giftDetailNo", NewgiftDetailNo);
                        startActivityForResult(intent2, ActivityResultHelper.ChOOSE_COUPONS_REQUESR_CODE);
                    }

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
     * 获取数据的网络请求
     */
    private void requestCartBalance(String giftDetailNo, int type) {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, giftDetailNo, type, 1)
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
                            toRechargeAmount = cModel.getData().getToRechargeAmount();
                            toRecharge = cModel.getData().isToRecharge();
                            totalAmount = Double.valueOf(cartBalanceModel.getData().getTotalAmount());
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
                                tv_operate_title.setVisibility(View.VISIBLE);
                            }else {
                                tv_operate_title.setVisibility(View.GONE);
                            }

                            if(listUnOperate.size()>0) {
                                rl_unOperate.setVisibility(View.VISIBLE);
                            }else {
                                rl_unOperate.setVisibility(View.GONE);
                            }

                            mCoder.geocode(new GeoCodeOption()
                                    .city("杭州")
                                    .address(cartBalanceModel.getData().wareAddress));
                        } else {
                            AppHelper.showMsg(mActivity, cartBalanceModel.message);
                        }
                    }
                });
    }

    CartBalanceModel.DataBean info;
    int giftNum;


    /**
     * 接收来自接口的数据进行数据设置。
     *
     * @param cartBalanceModel
     */
    private void setText(CartBalanceModel cartBalanceModel) {
        CartBalanceModel.DataBean info = cartBalanceModel.getData();
        proActAmount = info.getProActAmount();
        teamAmount = info.getTeamAmount();
        killAmount = info.getKillAmount();
        prodAmount = info.getNormalAmount();

        areaContent =
                info.getAddressVO().getAreaCode();

        if (info.getDeductDetail() != null) {
            deductDetail = info.getDeductDetail().getGiftDetailNo();
        } else {
            deductDetail = "";
        }


        giftNum = cartBalanceModel.getData().getGiftNum();

        if(giftNum>0) {
            textCoupons.setText(cartBalanceModel.getData().getDeductDesc());
            textCoupons.setTextColor(Color.parseColor("#F25E0E"));
            linearLayoutCoupons.setEnabled(true);
        }else {
            textCoupons.setText("暂无优惠券可使用");
            textCoupons.setTextColor(Color.parseColor("#999999"));
            linearLayoutCoupons.setEnabled(false);

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
          //  LinearLayoutAddress.setVisibility(View.GONE);
           // linearLayoutAddressHead.setVisibility(View.VISIBLE);
            userName.setText(info.getAddressVO().getUserName());
            userPhone.setText(info.getAddressVO().getContactPhone());


            if (!TextUtils.isEmpty(info.getAddressVO().getShopName())) {
                firmName.setText(info.getAddressVO().getShopName());
                // LinearLayoutStoreName.setVisibility(View.VISIBLE);
            } else {
                // LinearLayoutStoreName.setVisibility(View.GONE);
            }
        } else {
          //  linearLayoutAddressHead.setVisibility(View.GONE);
          //  LinearLayoutAddress.setVisibility(View.VISIBLE);
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
//        textCoupons.setText(cartBalanceModel.getData().getDeductDesc());

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
            //  vipSubtractionLinearLayout.setVisibility(View.GONE);
            textViewVipTitle.setText(cartBalanceModel.getData().getNotVipDesc());
        } else {
            vipSubtractionLinearLayout.setVisibility(View.GONE);
            //ll_collect_bills.setVisibility(View.VISIBLE);
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


//        userChooseDeduct();
    }


    // 去支付
    OperateDialog operateDialog;
    private void requestOrderNum() {
        GenerateOrderAPI.requestGenerateOrder(mActivity, activityBalanceVOStr, normalProductBalanceVOStr, cartListStr, NewgiftDetailNo, messageEditText.getText().toString(),
                deliverTimeStart, deliverTimeEnd, deliverTimeName, 1, et_name.getText().toString(), et_phone.getText().toString(), mYear)//NewgiftDetailNo
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
                            if(listUnOperate.size()>0) {
                                operateDialog = new OperateDialog(mActivity,listUnOperate) {
                                    @Override
                                    public void Confirm() {
                                        EventBus.getDefault().post(new RefreshEvent());
                                        operateDialog.dismiss();
                                        lav_activity_loading.hide();
                                        buttonPay.setEnabled(true);
                                    }

                                    @Override
                                    public void Cancle() {
                                        lav_activity_loading.hide();
                                        operateDialog.dismiss();
                                        buttonPay.setEnabled(true);

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

                                    }
                                };
                                operateDialog.show();
                            }else {
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
                            }


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
        requestCartBalance(NewgiftDetailNo, 1);
//        userChooseDeduct();
    }


    /**
     * 选中某一个优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCouponss(ChooseCouponsEvent chooseCouponEvent) {
        list.clear();
        requestCartBalance(chooseCouponEvent.getGiftDetailNo(), 1);
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
        requestCartBalance("",1);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mMapView.onDestroy();

        mCoder.destroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        mMapView.onResume();


        mLocationClient = new LocationClient(mActivity.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
//设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(listenerClick);

        mLocationClient.setLocOption(option);
        mLocationClient.start();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mCoder = GeoCoder.newInstance();
//        requestCartBalance(NewgiftDetailNo, 1);//NewgiftDetailNo
        mCoder.setOnGetGeoCodeResultListener(listener);

    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明


            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String streetNumber = location.getStreetNumber();
            latitude2 = location.getLatitude();
            longitude2 = location.getLongitude();

        }
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
}
