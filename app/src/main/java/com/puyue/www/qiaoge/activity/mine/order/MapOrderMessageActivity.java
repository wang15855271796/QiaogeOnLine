package com.puyue.www.qiaoge.activity.mine.order;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.core.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.adapter.mine.AddressApi;
import com.puyue.www.qiaoge.api.mine.order.ConfirmGetGoodsAPI;
import com.puyue.www.qiaoge.api.mine.order.GetOrderMapMessageAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;


import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.mine.order.ConfirmGetGoodsModel;
import com.puyue.www.qiaoge.model.mine.order.GetOrderDriverModel;
import com.puyue.www.qiaoge.model.mine.wallet.AddressModel;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用的帧布局，SlideNestedPanelLayout的滑动时间会和地图冲突，造成地图不能位移，缩小变大
 * Created by ${王文博} on 2019/5/28
 */
public class MapOrderMessageActivity extends BaseSwipeActivity {
    private String orderId;
    //司机地址
    private String addressDetail;
    private ConfirmGetGoodsModel mModelConfirmGetGoods;

    private TextView mTvOk;

    private List<GetOrderDriverModel.DataBean.UserLocationVOListBean> listBeans = new ArrayList<>();

    private String payTime;
    private String receiveTime;
    private String sendTime;
    private String finishTime;

    private TextView tv_wait_order;
    private TextView tv_wait_send_date;
    private TextView tv_already_send_date;
    private TextView tv_send_date;
    private TextView tv_confirm_date;
    private TextView tv_wait_month;
    private TextView tv_wait_send_month;
    private TextView tv_already_send_month;
    private TextView tv_send_month;
    private TextView tv_confirm_month;
    private RelativeLayout rl_confirm_order;//确认收货
    private RelativeLayout rl_send_order;//发货完毕
    private TextView tv_driver_name;//司机名字
    private TextView tv_driver_phone;//司机电话
    private TextView tv_circle_one;
    private TextView tv_circle_four;
    private TextView tv_circle_five;
    private TextView tv_send_order;
    private ImageView iv_back;


    private LoadingDailog dialog;


//    private LatLng cenpt;
//    private MapStatusUpdate mMapStatusUpdate;


//    private GeoCoder mCoder;
    double latitude1;
    double longitude1;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.map_order_detail);
    }

    SupportMapFragment supportMapFragment;
    com.tencent.tencentmap.mapsdk.maps.TencentMap mapss;
    @Override
    public void findViewById() {
        //获取地图控件引用
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag);
        mTvOk = findViewById(R.id.tv_ok);
        tv_wait_order = findViewById(R.id.tv_wait_order);
        tv_wait_send_date = findViewById(R.id.tv_wait_send_date);
        tv_already_send_date = findViewById(R.id.tv_already_send_date);
        rl_confirm_order = findViewById(R.id.rl_confirm_order);
        rl_send_order = findViewById(R.id.rl_send_order);
        tv_send_date = findViewById(R.id.tv_send_date);
        tv_confirm_date = findViewById(R.id.tv_confirm_date);
        tv_wait_month = findViewById(R.id.tv_wait_month);
        tv_wait_send_month = findViewById(R.id.tv_wait_send_month);
        tv_already_send_month = findViewById(R.id.tv_already_send_month);
        tv_send_month = findViewById(R.id.tv_send_month);
        tv_confirm_month = findViewById(R.id.tv_confirm_month);
        tv_driver_name = findViewById(R.id.tv_driver_name);
        tv_driver_phone = findViewById(R.id.tv_driver_phone);
        tv_circle_five = findViewById(R.id.tv_circle_five);
        tv_circle_one = findViewById(R.id.tv_circle_one);
        tv_circle_four = findViewById(R.id.tv_circle_four);
        tv_send_order = findViewById(R.id.tv_send_order);
        iv_back = findViewById(R.id.iv_back);
        mapss = supportMapFragment.getMap();
        
    }



    @Override
    public void setViewData() {

        orderId = getIntent().getStringExtra("orderId");
        timer.schedule(task, 0, 60 * 1000);
        //获取物流信息
        getOrderMessage(orderId);


    }


    @Override
    public void setClickEvent() {
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestConfirmGetGoods();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private final Timer timer = new Timer();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 要做的事情


            super.handleMessage(msg);
            if (msg.what == 1) {
                //定时获取地址

            }
        }
    };


    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


    private void getOrderMessage(String orderId) {
        GetOrderMapMessageAPI.requestDriverMe(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetOrderDriverModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetOrderDriverModel getOrderDriverModel) {

                        if (getOrderDriverModel.isSuccess()) {

                            payTime = getOrderDriverModel.getData().getPayTime();
                            receiveTime = getOrderDriverModel.getData().getReceiveTime();
                            sendTime = getOrderDriverModel.getData().getSendTime();
                            finishTime = getOrderDriverModel.getData().getFinishTime();
                            if(null!=receiveTime) {
                                tv_wait_send_date.setText(returnDate(receiveTime));
                                tv_wait_send_month.setText(returnMonth(receiveTime));
                            }

                            if(null!=sendTime) {
                                tv_already_send_date.setText(returnDate(sendTime));
                                tv_already_send_month.setText(returnMonth(sendTime));
                                tv_send_month.setText(returnMonth(sendTime));
                                tv_send_date.setText(returnDate(sendTime));
                            }
                            if(null!=payTime) {
                                tv_wait_order.setText(returnDate(payTime));
                                tv_wait_month.setText(returnMonth(payTime));
                            }

                            if(null!=getOrderDriverModel.getData().getDriverName()) {
                                tv_driver_name.setText("配送员：" + getOrderDriverModel.getData().getDriverName());
                            }


                            if(null!=getOrderDriverModel.getData().getDriverPhone()) {
                                tv_driver_phone.setText(getOrderDriverModel.getData().getDriverPhone());
                            }


                            if (null!= finishTime) {
                                //  tv_send_date.setText(returnDate(finishTime));
                                tv_confirm_date.setText(returnDate(finishTime));
                                // tv_send_month.setText(returnMonth(finishTime));
                                tv_confirm_month.setText(returnMonth(finishTime));

                            }

                            if (getOrderDriverModel.getData().getOrderStatus() == 5) {
                                rl_confirm_order.setVisibility(View.VISIBLE);
                                tv_send_order.setText("派送完毕");
                                tv_circle_five.setBackgroundResource(R.drawable.circle_bg);
                                tv_circle_four.setBackgroundResource(R.drawable.circle_bg_two);
                            } else {
                                rl_confirm_order.setVisibility(View.GONE);
                                tv_send_order.setText("正在派送中");
                                tv_circle_four.setBackgroundResource(R.drawable.circle_bg);
                            }

                            listBeans.clear();
                            addressDetail = getOrderDriverModel.getData().getAddressDetail();
                            listBeans.addAll(getOrderDriverModel.getData().getUserLocationVOList());
                            showPoint();
                            if(getOrderDriverModel.getData().getUserLocationVOList()!=null&&getOrderDriverModel.getData().getAddressDetail()!=null) {
                                geocoder(getOrderDriverModel.getData().getUserLocationVOList(),getOrderDriverModel.getData().getAddressDetail());
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void getWalkingRoute(List<GetOrderDriverModel.DataBean.UserLocationVOListBean> userLocationVOList, LatLng latLng){
        LatLng fromPoint = new LatLng(userLocationVOList.get(0).getLatitude(), userLocationVOList.get(0).getLongitude());
        LatLng toPoint = new LatLng(latLng.latitude,latLng.longitude);
        MarkerOptions options = new MarkerOptions(fromPoint);
        options.infoWindowEnable(true);//默认为true
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_z));//设置自定义Marker图标
        mapss.addMarker(options);

        DrivingParam drivingParam = new DrivingParam(fromPoint, toPoint); //创建导航参数

        drivingParam.roadType(DrivingParam.RoadType.ON_MAIN_ROAD_BELOW_BRIDGE);
        drivingParam.heading(90);
        drivingParam.accuracy(30);
        TencentSearch tencentSearch = new TencentSearch(this);

        tencentSearch.getRoutePlan(drivingParam, new HttpResponseListener<DrivingResultObject>() {

            @Override
            public void onSuccess(int i, DrivingResultObject drivingResultObject) {
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(userLocationVOList.get(0).getLatitude(), userLocationVOList.get(0).getLongitude()),
                                6,
                                0f,
                                0f));
                //移动地图

                mapss.moveCamera(cameraSigma);
                if (drivingResultObject == null){
                    return;
                }

                for (DrivingResultObject.Route route : drivingResultObject.result.routes){
                    List<LatLng> lines = route.polyline;
                    mapss.addPolyline(new PolylineOptions().addAll(lines)
                            .color(Color.parseColor("#66FF99"))
                            .width(12)
                    );
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
            }
        });

        MarkerOptions optionss = new MarkerOptions(toPoint);
        optionss.infoWindowEnable(true);//默认为true
        optionss.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_x));//设置自定义Marker图标
        mapss.addMarker(optionss);

        LatLng driverPoint = new LatLng(userLocationVOList.get(userLocationVOList.size()-1).getLatitude(), userLocationVOList.get(userLocationVOList.size()-1).getLongitude());
        MarkerOptions optionsss = new MarkerOptions(driverPoint);
        optionsss.infoWindowEnable(true);//默认为true
        optionsss.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_huo_car));//设置自定义Marker图标
        mapss.addMarker(optionsss);

//        // 构造折线点串
//        List<LatLng> latLngs = new ArrayList<>();
//        latLngs.add(fromPoint);
//        latLngs.add(toPoint);
//        latLngs.add(driverPoint);
//
//// 构造 PolylineOpitons
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .addAll(latLngs)
//                // 折线设置圆形线头
//                .lineCap(true)
//                // 折线的颜色为绿色
//                .color(0xff00ff00)
//                // 折线宽度为25像素
//                .width(25)
//                // 还可以添加描边颜色
//                .borderColor(Color.parseColor("#3F51B5"))
//                // 描边颜色的宽度，线宽还是 25 像素，不过填充的部分宽度为 `width` - 2 * `borderWidth`
//                .borderWidth(5);
//
//        // 绘制折线
//        mapss.addPolyline(polylineOptions);

    }

    protected void geocoder(List<GetOrderDriverModel.DataBean.UserLocationVOListBean> userLocationVOList, String address) {
        TencentSearch tencentSearch = new TencentSearch(mActivity);

        Address2GeoParam address2GeoParam =
                new Address2GeoParam(address).region(UserInfoHelper.getCity(mActivity));
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
                com.tencent.tencentmap.mapsdk.maps.model.LatLng latLng = obj.result.latLng;
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(userLocationVOList.get(userLocationVOList.size()-1).getLatitude(), userLocationVOList.get(userLocationVOList.size()-1).getLongitude()),
                                6,
                                0f,
                                0f));
                //移动地图

                mapss.moveCamera(cameraSigma);
//                CameraUpdate cameraSigma =

//                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                                new com.tencent.tencentmap.mapsdk.maps.model.LatLng(latLng.latitude,latLng.longitude),
//                                15,
//                                0f,
//                                0f));
//                //移动地图
//                mapss.moveCamera(cameraSigma);
                getWalkingRoute(userLocationVOList,latLng);
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
//                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }


    private String returnDate(String s) {


        String[] split = s.split(" ");
        String s1 = split[1];
        String s2;
        if (s1.length() > 3) {

            s2=s1.substring(0, s1.length()-3);
            return s2;
        }


        return s1;
    }

    private String returnMonth(String s) {

        String[] split = s.split(" ");
        String s1 = split[1];//这个得到的是18:20:20
        String s2 = split[0];//2018-01-02
        String[] split1 = s2.split("-");
        String s3 = split1[1];//01
        String s4 = split1[2];//02


        return s3 + "-" + s4;
    }


    private void showPoint() {

        List<Integer> colors = new ArrayList<>();

        for (int i = 0; i < listBeans.size(); i++) {

            if (listBeans.get(i).getType().equals("1")) {
                colors.add(Integer.valueOf(Color.GREEN));
            } else if (listBeans.get(i).getType().equals("2")) {
                colors.add(Integer.valueOf(Color.RED));
            }
        }

    }

    public void getAddress(String addressDetail) {
        AddressApi.requestAddress(mContext, "杭州市万达广场", "json", "KqnEcjvFD0DdacMhQxUtxtSYm2Fj9Lia", "C3:FD:C2:A1:A5:77:B8:27:81:BB:89:98:95:59:66:C0:E0:C1:2B:6B;com.barbecue.app")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("lyy", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(AddressModel addressModel) {


                        Log.i("wwb", "onNext: " + addressModel.status);
                        if (addressModel.status == 0) {
                            double lat = addressModel.result.location.lat;//纬度
                            double lng = addressModel.result.location.lng;//经度
//                            LatLng cenpt = new LatLng(lat, lng);
                            //定义地图状态
//                            MapStatus mMapStatus = new MapStatus.Builder()
//                                    .target(cenpt)
//                                    .zoom(18)
//                                    .build();
//                            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//
//
//                            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//                            //改变地图状态
//                            //定义Maker坐标点
//
//                            //构建Marker图标
//                            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                    .fromResource(R.mipmap.ic_driver_location_end);
//                            //构建MarkerOption，用于在地图上添加Marker
//                            OverlayOptions option = new MarkerOptions()
//                                    .position(cenpt)
//                                    .icon(bitmap);
//                            //在地图上添加Marker，并显示
//                            mBaiduMap.addOverlay(option);
//                            mBaiduMap.setMapStatus(mMapStatusUpdate);
                        } else {

                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//        mMapView.onDestroy();
        timer.cancel();
//        mCoder.destroy();

    }


    // 确认收货
    private void requestConfirmGetGoods() {
        ConfirmGetGoodsAPI.reuqestConfirmGetGoods(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConfirmGetGoodsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ConfirmGetGoodsModel confirmGetGoodsModel) {
                        mModelConfirmGetGoods = confirmGetGoodsModel;
                        if (mModelConfirmGetGoods.success) {

                            //确认收货成功
                            AppHelper.showMsg(mContext, "确认收货成功");
                            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                                    .setMessage("确认收货成功")
                                    .setCancelable(false)

                                    .setCancelOutside(true);
                            dialog = loadBuilder.create();
                            dialog.show();
                            mTvOk.setClickable(false);
                            mTvOk.setText("收货成功");
                            getOrderMessageTwo(orderId);
                        } else {
                            AppHelper.showMsg(mContext, mModelConfirmGetGoods.message);
                        }
                    }
                });
    }

    private void getOrderMessageTwo(String orderId) {

        GetOrderMapMessageAPI.requestDriverMe(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetOrderDriverModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetOrderDriverModel getOrderDriverModel) {

                        if (getOrderDriverModel.isSuccess()) {

                            dialog.dismiss();
                            payTime = getOrderDriverModel.getData().getPayTime();
                            receiveTime = getOrderDriverModel.getData().getReceiveTime();
                            sendTime = getOrderDriverModel.getData().getSendTime();
                            finishTime = getOrderDriverModel.getData().getFinishTime();

                            tv_wait_order.setText(returnDate(payTime));
                            tv_wait_send_date.setText(returnDate(receiveTime));
                            tv_already_send_date.setText(returnDate(sendTime));
                            tv_wait_month.setText(returnMonth(payTime));
                            tv_wait_send_month.setText(returnMonth(receiveTime));
                            tv_already_send_month.setText(returnMonth(sendTime));

                            tv_send_date.setText(returnDate(sendTime));
                            tv_send_month.setText(returnMonth(sendTime));
                            tv_driver_name.setText("配送员：" + getOrderDriverModel.getData().getDriverName());
                            tv_driver_phone.setText(getOrderDriverModel.getData().getDriverPhone()+"s");
                            if (finishTime != null) {
                                tv_send_date.setText(returnDate(finishTime));
                                tv_confirm_date.setText(returnDate(finishTime));
                                tv_send_month.setText(returnMonth(finishTime));
                                tv_confirm_month.setText(returnMonth(finishTime));
                            }

                            if (getOrderDriverModel.getData().getOrderStatus() == 5) {
                                rl_confirm_order.setVisibility(View.VISIBLE);

                                 tv_driver_name.setText(getOrderDriverModel.getData().getDriverName());
                                 tv_driver_phone.setText(getOrderDriverModel.getData().getDriverPhone());
                                tv_circle_five.setBackgroundResource(R.drawable.circle_bg);
                                tv_circle_four.setBackgroundResource(R.drawable.circle_bg_two);

                            } else {
                                rl_confirm_order.setVisibility(View.GONE);

                                tv_circle_four.setBackgroundResource(R.drawable.circle_bg);
                            }

                            Log.i("wwbbb", "onNext: " + listBeans.size() + "/" + getOrderDriverModel.getData().getUserLocationVOList().size());
                            listBeans.clear();
                            //    addressDetail = getOrderDriverModel.getData().getAddressDetail();
                            // getAddress(addressDetail);
                            listBeans.addAll(getOrderDriverModel.getData().getUserLocationVOList());

                            showPoint();


                        }


                    }
                });
    }
}
