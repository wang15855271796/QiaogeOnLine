package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;

public class OpenShopActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_city_warehouse)
    TextView tv_city_warehouse;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tv_front)
    TextView tv_front;
    @BindView(R.id.tv_provider)
    TextView tv_provider;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        iv_back.setOnClickListener(this);
        tv_city_warehouse.setOnClickListener(this);
        iv_back1.setOnClickListener(this);
        tv_front.setOnClickListener(this);
        tv_provider.setOnClickListener(this);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0) {
                    rl_title.setVisibility(View.GONE);
                }else {
                    rl_title.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.iv_back1:
                finish();
                break;

            case R.id.tv_city_warehouse:
                Intent cityIntent = new Intent(mContext,CityWareHouseActivity.class);
                startActivity(cityIntent);
                break;

            case R.id.tv_front:
                Intent frontIntent = new Intent(mContext,FrontWareHouseActivity.class);
                startActivity(frontIntent);
                break;

            case R.id.tv_provider:
                Intent providerIntent = new Intent(mContext,ProviderWareHouseActivity.class);
                startActivity(providerIntent);
                break;
        }
    }
}
