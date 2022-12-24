package com.puyue.www.qiaoge.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.DrivingRouteOverlay;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.HuoAddressModel;
import com.puyue.www.qiaoge.model.HuoCityIdModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapOptions;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

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
    private FragmentManager fm;
    TencentMap map;
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

    SupportMapFragment supportMapFragment;
    @Override
    public void findViewById() {

    }

//    LocationClient mLocationClient;
    @Override
    public void setViewData() {
        fm = getSupportFragmentManager();
        supportMapFragment =  (SupportMapFragment)fm.findFragmentById(R.id.frag);
        map = supportMapFragment.getMap();

        getAddress(orderDisplayId);

        getWalkingRoute();
    }

    private void getWalkingRoute(){
        LatLng fromPoint = new LatLng(sendAddress.getLatLon().getLat(), sendAddress.getLatLon().getLon());
        LatLng toPoint = new LatLng(receiveAddress.getLatLon().getLat(), receiveAddress.getLatLon().getLon());
        CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(sendAddress.getLatLon().getLat(),sendAddress.getLatLon().getLon()),
                        12,
                        0f,
                        0f));
        //移动地图
        map.moveCamera(cameraSigma);
        MarkerOptions options = new MarkerOptions(fromPoint);
        options.infoWindowEnable(true);//默认为true
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_z));//设置自定义Marker图标
        map.addMarker(options);

        MarkerOptions optionss = new MarkerOptions(toPoint);
        optionss.infoWindowEnable(true);//默认为true
        optionss.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_x));//设置自定义Marker图标
        map.addMarker(optionss);

        LatLng driverPoint = new LatLng(latLon.getLat(), latLon.getLon());
        MarkerOptions optionsss = new MarkerOptions(driverPoint);
        optionsss.infoWindowEnable(true);//默认为true
        optionsss.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_huo_car));//设置自定义Marker图标
        map.addMarker(optionsss);
//
        // 构造折线点串
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(fromPoint);
        latLngs.add(toPoint);
        latLngs.add(driverPoint);

        // 构造 PolylineOpitons
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngs)
//                // 折线设置圆形线头
                .lineCap(true)
                // 折线的颜色为绿色
                .color(0xff00ff00)
                // 折线宽度为25像素
                .width(25)
                // 还可以添加描边颜色
                .borderColor(Color.parseColor("#3F51B5"))
                // 描边颜色的宽度，线宽还是 25 像素，不过填充的部分宽度为 `width` - 2 * `borderWidth`
                .borderWidth(5);

        // 绘制折线
        map.addPolyline(polylineOptions);

    }


    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
    }

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

                                LatLng driverPoint = new LatLng(data.getLat(), data.getLon());
                                MarkerOptions optionsss = new MarkerOptions(driverPoint);
                                optionsss.infoWindowEnable(true);//默认为true
                                optionsss.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_huo_car));//设置自定义Marker图标
                                map.addMarker(optionsss);
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
