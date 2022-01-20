package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.DrivingRouteOverlay;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.HuoAddressModel;
import com.puyue.www.qiaoge.model.HuoCityIdModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

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
    @BindView(R.id.map)
    MapView mMapView;
    String orderDisplayId;
    RoutePlanSearch mSearch;

    HuoDetailModel.DataBean.SendAddrBean sendAddress;
    HuoDetailModel.DataBean.ReceiveAddrBean receiveAddress;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderDisplayId = getIntent().getStringExtra("orderDisplayId");
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

    @Override
    public void setViewData() {
        mBaidumap = mMapView.getMap();
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
//        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
//        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
//        mSearch.drivingSearch((new DrivingRoutePlanOption())
//                .from(stNode)
//                .to(enNode));

        LatLng fromLl = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
        LatLng endLl = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
        PlanNode stNode = PlanNode.withLocation(fromLl);
        PlanNode enNode = PlanNode.withLocation(endLl);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        getCityId(orderDisplayId);
        Log.d("fesfsrfe..........",sendAddress.getLatLon().getLat()+"---"+sendAddress.getLatLon().getLon()+"a");
        Log.d("fesfsrfe..........",receiveAddress.getLatLon().getLat()+"---"+receiveAddress.getLatLon().getLon()+"b");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
    }
    BaiduMap mBaidumap = null;
    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            //创建DrivingRouteOverlay实例
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaidumap);

            if (drivingRouteResult.getRouteLines().size() > 0) {
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();

                LatLng p1 = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
                LatLng p2 = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
                List<LatLng> points = new ArrayList<>();
                points.add(p1);
                points.add(p2);

//设置折线的属性
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(0xAAFF0000)
                        .points(points);
//在地图上绘制折线
//mPloyline 折线对象
                Overlay mPolyline = mBaidumap.addOverlay(mOverlayOptions);
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };


    private void getCityId(String orderDisplayId) {
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
        }

    }
}
