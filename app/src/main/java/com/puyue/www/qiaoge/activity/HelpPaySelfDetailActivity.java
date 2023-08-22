package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;

import butterknife.BindView;

public class HelpPaySelfDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_apply_person)
    TextView tv_apply_person;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_detail_address)
    TextView tv_detail_address;
    @BindView(R.id.tv_goods_num)
    TextView tv_goods_num;
    @BindView(R.id.tv_goods_amount)
    TextView tv_goods_amount;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.tv_send_time)
    TextView tv_send_time;
    @BindView(R.id.tv_copy_order)
    TextView tv_copy_order;
    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.tv_full_amount)
    TextView tv_full_amount;
    @BindView(R.id.tv_coupon_desc)
    TextView tv_coupon_desc;
    @BindView(R.id.tv_coupon_amount)
    TextView tv_coupon_amount;
    @BindView(R.id.tv_should_pay)
    TextView tv_should_pay;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_delivery_time)
    TextView tv_delivery_time;
    @BindView(R.id.tv_memo)
    TextView tv_memo;
    @BindView(R.id.tv_full_active_amount)
    TextView tv_full_active_amount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_given)
    RecyclerView rv_given;
    @BindView(R.id.rv_coupon)
    RecyclerView rv_coupon;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_take_name)
    TextView tv_take_name;
    @BindView(R.id.tv_take_phone)
    TextView tv_take_phone;
    @BindView(R.id.tv_take_time)
    TextView tv_take_time;
    @BindView(R.id.tv_replace_pay)
    TextView tv_replace_pay;
    @BindView(R.id.tv_replace_order)
    TextView tv_replace_order;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay_self_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
