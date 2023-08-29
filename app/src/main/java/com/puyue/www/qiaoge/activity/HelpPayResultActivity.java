package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;

import butterknife.BindView;

public class HelpPayResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_look_order)
    TextView tv_look_order;
    String orderId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay_result);
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
        tv_look_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_look_order:
                Intent intent = new Intent(mContext,LookDeliveryDetailActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                finish();
                break;
        }
    }
}
