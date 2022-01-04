package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HuoAskAdapter;
import com.puyue.www.qiaoge.adapter.HuoBaseAdapter;
import com.puyue.www.qiaoge.adapter.HuoCarAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoRuleActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_car_style)
    TextView tv_car_style;
    @BindView(R.id.tv_length)
    TextView tv_length;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_tiji)
    TextView tv_tiji;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.rv_base)
    RecyclerView rv_base;
    @BindView(R.id.rv_ask)
    RecyclerView rv_ask;
    @BindView(R.id.rv_car_style)
    RecyclerView rv_car_style;
    String id;
    HuoBaseAdapter huoBaseAdapter;
    HuoCarAdapter huoCarAdapter;
    HuoAskAdapter huoAskAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rule);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

        rv_base.setLayoutManager(new LinearLayoutManager(mContext));
        huoBaseAdapter = new HuoBaseAdapter(R.layout.item_base,basicFee);
        rv_base.setAdapter(huoBaseAdapter);

        rv_car_style.setLayoutManager(new LinearLayoutManager(mContext));
        huoCarAdapter = new HuoCarAdapter(R.layout.item_huo_car,carSpec);
        rv_car_style.setAdapter(huoCarAdapter);

        rv_ask.setLayoutManager(new LinearLayoutManager(mContext));
        huoAskAdapter = new HuoAskAdapter(R.layout.item_huo_car,otherReq);
        rv_ask.setAdapter(huoAskAdapter);

        getCarPrice("1011",id);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
    }

    /**
     * 车型费用明细
     * @param cityId
     * @param orderVehicleId
     */
    List<CarPriceModel.DataBean.BasicFeeBean> basicFee = new ArrayList<>();
    List<CarPriceModel.DataBean.CarSpecBean> carSpec = new ArrayList<>();
    List<CarPriceModel.DataBean.OtherReqBean> otherReq = new ArrayList<>();
    private void getCarPrice(String cityId,String orderVehicleId) {
        HuolalaAPI.getCarPrice(mActivity,cityId,orderVehicleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarPriceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CarPriceModel carPriceModel) {
                        if(carPriceModel.getCode()==1) {
                            if(carPriceModel.getData()!=null) {
                                CarPriceModel.DataBean data = carPriceModel.getData();
                                tv_city.setText(data.getName());
                                Glide.with(mContext).load(data.getImage_url_high_light()).into(iv_pic);
                                tv_length.setText("车厢长："+data.getVehicle_size());
                                tv_tiji.setText("体积："+data.getVehicle_volume_text());
                                tv_weight.setText("重量："+data.getVehicle_weight_text());

                                basicFee.clear();
                                basicFee.addAll(data.getBasicFee());

                                if(data.getCarSpec()!=null) {
                                    carSpec.clear();
                                    carSpec.addAll(data.getCarSpec());
                                }

                                otherReq.clear();
                                otherReq.addAll(data.getOtherReq());
                                huoBaseAdapter.notifyDataSetChanged();
                                huoCarAdapter.notifyDataSetChanged();
                                huoAskAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,carPriceModel.getMessage());
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
