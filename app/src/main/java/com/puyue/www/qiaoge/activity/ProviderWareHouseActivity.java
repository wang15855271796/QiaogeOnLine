package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;

import butterknife.BindView;

public class ProviderWareHouseActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.rl_yzm)
    RelativeLayout rl_yzm;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_provider_warehouse);
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
        tv_next.setOnClickListener(this);
        tv_yzm.setOnClickListener(this);
    }

    String phone;
    String yzm;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_yzm:
                handleCountDown();
                break;

            case R.id.tv_next:
                yzm = et_yzm.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                Intent intent = new Intent(mContext,OpenShopListActivity.class);
                startActivity(intent);
//                Intent intent = new Intent(mContext,StartProviderActivity.class);
//                startActivity(intent);
                break;
        }
    }

    boolean isSendingCode;
    CountDownTimer countDownTimer;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                rl_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后" + "重新获取");
                tv_yzm.setTextColor(Color.parseColor("#A7A7A7"));
                tv_yzm.setBackgroundResource(R.drawable.shape_grey3);

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                rl_yzm.setEnabled(true);
                tv_yzm.setText("发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#FF3E20"));
            }
        }.start();
    }

}
