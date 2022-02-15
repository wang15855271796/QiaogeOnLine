package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.DrivingRouteOverlay;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.HuoAddressModel;
import com.puyue.www.qiaoge.model.HuoCityIdModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoDriverActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
//    @BindView(R.id.map)
//    MapView mMapView;
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    String orderDisplayId;
//    RoutePlanSearch mSearch;
    HuoDetailModel.DataBean.DriverInfoBean.LatLonBean latLon;
    HuoDetailModel.DataBean.SendAddrBean sendAddress;
    HuoDetailModel.DataBean.ReceiveAddrBean receiveAddress;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderDisplayId = getIntent().getStringExtra("orderDisplayId");
        latLon = (HuoDetailModel.DataBean.DriverInfoBean.LatLonBean) getIntent().getSerializableExtra("latLon");
        sendAddress = (HuoDetailModel.DataBean.SendAddrBean) getIntent().getSerializableExtra("sendAddress");
        receiveAddress = (HuoDetailModel.DataBean.ReceiveAddrBean) getIntent().getSerializableExtra("receiveAddress");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_driver);
    }

    @Override
    public void findViewById() {

    }

//    LocationClient mLocationClient;
    @Override
    public void setViewData() {

//        mBaidumap = mMapView.getMap();
//        mSearch = RoutePlanSearch.newInstance();
//        mSearch.setOnGetRoutePlanResultListener(listener);
//
//        mLocationClient = new LocationClient(getApplicationContext());
//
//        //通过LocationClientOption设置LocationClient相关参数
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(0);
//
//        //设置locationClientOption
//        mLocationClient.setLocOption(option);
//
//        //注册LocationListener监听器
//        MyLocationListener myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);
////        //开启地图定位图层
//        mLocationClient.start();
//
//        LatLng fromLl = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
//        LatLng endLl = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
//        PlanNode stNode = PlanNode.withLocation(fromLl);
//        PlanNode enNode = PlanNode.withLocation(endLl);
//        mSearch.drivingSearch((new DrivingRoutePlanOption())
//                .from(stNode)
//                .to(enNode));
        getAddress(orderDisplayId);

    }

//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null) {
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection())
//                    .latitude(location.getLatitude())
//                    .longitude(location.getLongitude())
//                    .build();
//
//            BaiduMap maps = mMapView.getMap();
//            maps.setMyLocationData(locData);
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(ll).zoom(18.0f);
//            maps.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mSearch.destroy();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
    }
//    BaiduMap mBaidumap = null;
//    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
//        @Override
//        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
//
//        }
//
//        @Override
//        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
//        }
//
//        @Override
//        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
//        }
//
//        @Override
//        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
//            //创建DrivingRouteOverlay实例
//            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaidumap);
//            if(drivingRouteResult.getRouteLines()!=null) {
//                if (drivingRouteResult.getRouteLines().size() > 0) {
////                overlay.setData(drivingRouteResult.getRouteLines().get(0));
//                    LatLng p1 = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
//                    LatLng p2 = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
//                    LatLng p3 = new LatLng(latLon.getLat(), latLon.getLon());
//                    List<LatLng> point = new ArrayList<>();
//                    point.add(p1);
//                    point.add(p2);
//                    point.add(p3);
//                    BitmapDescriptor start = BitmapDescriptorFactory.fromResource(R.mipmap.icon_z);
//                    BitmapDescriptor end = BitmapDescriptorFactory.fromResource(R.mipmap.icon_x);
//                    BitmapDescriptor driver = BitmapDescriptorFactory.fromResource(R.mipmap.icon_huo_cars);
//                    List<OverlayOptions> options = new ArrayList<>();
//                    OverlayOptions option1 =  new MarkerOptions()
//                            .position(p1)
//                            .icon(start);
//                    OverlayOptions option2 =  new MarkerOptions()
//                            .position(p2)
//                            .icon(end);
//                    OverlayOptions option3 =  new MarkerOptions()
//                            .position(p3)
//                            .icon(driver);
//                    options.add(option1);
//                    options.add(option2);
//                    options.add(option3);
//                    mBaidumap.addOverlays(options);
//                    //设置折线的属性
//                    OverlayOptions mOverlayOptions = new PolylineOptions()
//                            .width(10)
//                            .color(0xAAFF0000)
//                            .points(point);
//                    //在地图上绘制折线
//                    //mPloyline 折线对象
//                    mBaidumap.addOverlay(mOverlayOptions);
//                    mBaidumap.addOverlays(options);
//                    overlay.addToMap();
//                }
//            }
//        }
//
//        @Override
//        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
//
//        }
//
//        @Override
//        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
//
//        }
//    };


    private void getAddress(String orderDisplayId) {
        HuolalaAPI.getDriverAddress(mActivity,orderDisplayId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoAddressModel huoAddressModel) {
                        if(huoAddressModel.getCode()==1) {
                            if(huoAddressModel.getData()!=null) {
                                HuoAddressModel.DataBean data = huoAddressModel.getData();
//                                LatLng fromLl = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
//                                LatLng endLl = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
//                                PlanNode stNode = PlanNode.withLocation(fromLl);
//                                PlanNode enNode = PlanNode.withLocation(endLl);
//                                mSearch.drivingSearch((new DrivingRoutePlanOption())
//                                        .from(stNode)
//                                        .to(enNode));
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoAddressModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_refresh:
                getAddress(orderDisplayId);
                break;
        }

    }
}
