package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.PriceDetailAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PriceDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_car_style)
    TextView tv_car_style;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_rule)
    TextView tv_rule;
    String carStyle;
    String totalPrice;
    String id;
    List<DealPriceModel.DataBean.CalculatePriceInfoBean> priceList = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        priceList = (List<DealPriceModel.DataBean.CalculatePriceInfoBean>) getIntent().getSerializableExtra("priceList");
        carStyle = getIntent().getStringExtra("carStyle");
        totalPrice = getIntent().getStringExtra("totalPrice");
        id = getIntent().getStringExtra("id");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_price_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        tv_car_style.setText(carStyle);
        tv_price.setText(totalPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        PriceDetailAdapter priceDetailAdapter = new PriceDetailAdapter(R.layout.item_price,priceList);
        recyclerView.setAdapter(priceDetailAdapter);

    }

    @Override
    public void setClickEvent() {
        tv_rule.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_rule:
                Intent intent = new Intent(mContext,HuoRuleActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;

        }
    }

}
