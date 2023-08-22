package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.base.BaseActivity;


import butterknife.BindView;


public class LookOpenShopStep2Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_up_business)
    ImageView iv_up_business;
    @BindView(R.id.iv_up_allow)
    ImageView iv_up_allow;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;



    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_open_shop_step2);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        QiaoGeApplication.getInstance().addActivity(this);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.tv_next:
                Intent intent = new Intent(mContext, LookOpenShopStep3Activity.class);
                startActivity(intent);
                break;
        }
    }





}
