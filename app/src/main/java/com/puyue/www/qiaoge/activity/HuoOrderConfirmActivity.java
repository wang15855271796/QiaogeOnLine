package com.puyue.www.qiaoge.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.puyue.www.qiaoge.event.HuoBeizhuEvent;
import com.puyue.www.qiaoge.event.HuoOrderContactEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.HuoCouponModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
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
    String city_id = "1101";
    String id;
    String lat;
    String lon;
    List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem> vehicleStdItem = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        reqList = (List<String>) getIntent().getSerializableExtra("reqList");
        vehicleStdItem = (List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem>) getIntent().getSerializableExtra("vehicleStdItem");
        zAddr = getIntent().getStringExtra("zAddr");
        xAddr = getIntent().getStringExtra("xAddr");
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ReqAdapter reqAdapter = new ReqAdapter(R.layout.item_req,reqList);
        recyclerView.setAdapter(reqAdapter);
        tv_total.setText(price);
        tv_contact.setText(zName+zPhone);

        getCoupon(orderTime,reserve_time,city_id,id,lat,lon);
    }

    @Override
    public void setClickEvent() {
        rl_address.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_receipt.setOnClickListener(this);
        ll_receipt.setOnClickListener(this);
        rl_beizhu.setOnClickListener(this);
        rl_other.setOnClickListener(this);
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

    boolean isChoose1 = false;
    boolean isChoose2 = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_other:
                HuoOtherDialog huoOtherDialog = new HuoOtherDialog(mContext,vehicleStdItem);
                huoOtherDialog.show();
                break;

            case R.id.rl_beizhu:
                HuoBeizhuDialog huoBeizhuDialog = new HuoBeizhuDialog(mContext);
                huoBeizhuDialog.show();
                break;

            case R.id.tv_receipt:
                if(!isChoose1) {
                    isChoose1 = true;
                    tv_receipt.setBackgroundResource(R.drawable.shape_yellow1);
                    tv_receipt.setTextColor(Color.parseColor("#FD6601"));
                    tv_receipt1.setTextColor(Color.parseColor("#666666"));
                    tv_receipt2.setTextColor(Color.parseColor("#666666"));
                    if(isChoose2) {
                        isChoose2 = false;
                        ll_receipt.setBackgroundResource(R.drawable.shape_grey8);
                    }else {
                    }
                }else {
                    isChoose1 = false;
                    tv_receipt.setBackgroundResource(R.drawable.shape_grey8);
                    tv_receipt.setTextColor(Color.parseColor("#666666"));
                }

                break;

            case R.id.ll_receipt:
                if(!isChoose2) {
                    isChoose2 = true;
                    tv_receipt1.setTextColor(Color.parseColor("#FD6601"));
                    tv_receipt2.setTextColor(Color.parseColor("#FD6601"));
                    ll_receipt.setBackgroundResource(R.drawable.shape_yellow1);
                    if(isChoose1) {
                        isChoose1 = false;
                        tv_receipt.setBackgroundResource(R.drawable.shape_grey8);
                        tv_receipt.setTextColor(Color.parseColor("#666666"));
                    }else {

                    }
                }else {
                    isChoose2 = false;
                    ll_receipt.setBackgroundResource(R.drawable.shape_grey8);
                }

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_address:
                HuoAddressDialog huoAddressDialog = new HuoAddressDialog(mContext,zName,xName,zPhone,xPhone);
                huoAddressDialog.show();
                break;

            case R.id.tv_contact:
                HuoContactDialog huoContactDialog = new HuoContactDialog(mContext);
                huoContactDialog.show();
                break;

            case R.id.rl_coupon:
                HuoCouponDialog huoCouponDialog = new HuoCouponDialog(mContext,dataList);
                huoCouponDialog.show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContact(HuoOrderContactEvent huoOrderContactEvent) {
        tv_contact.setText(huoOrderContactEvent.getEtName()+huoOrderContactEvent.getEtPhone());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContact(HuoBeizhuEvent huoBeizhuEvent) {
        tv_contact.setText(huoBeizhuEvent.getEtDesc());
    }

    List<HuoCouponModel.DataBean> dataList = new ArrayList<>();
    private void getCoupon(String orderTime,String reserve_time,String city_id,String order_vehicle_id,
                           String lat,String lon) {
        HuolalaAPI.getCoupon(mContext,orderTime,reserve_time,city_id,order_vehicle_id,lat,lon)
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
                                dataList.clear();
                                dataList.addAll(huoCouponModel.getData());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoCouponModel.getMessage());
                        }
                    }
                });
    }


}
