package com.puyue.www.qiaoge.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ReqAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.HuoAddressDialog;
import com.puyue.www.qiaoge.dialog.HuoContactDialog;
import com.puyue.www.qiaoge.model.CarStyleModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class HuoOrderConfirmActivity extends BaseActivity implements View.OnClickListener {
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
    List<String> reqList;
    String zAddr;
    String xAddr;
    String carStyle;
    String price;
    String zName;
    String zPhone;
    String xName;
    String xPhone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        reqList = (List<String>) getIntent().getSerializableExtra("reqList");
        zAddr = getIntent().getStringExtra("zAddr");
        xAddr = getIntent().getStringExtra("xAddr");
        carStyle = getIntent().getStringExtra("carStyle");
        price = getIntent().getStringExtra("price");
        zName = getIntent().getStringExtra("zName");
        zPhone = getIntent().getStringExtra("zPhone");
        xName = getIntent().getStringExtra("xName");
        xPhone = getIntent().getStringExtra("xPhone");
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
        tv_z.setText(zAddr);
        tv_x.setText(xAddr);
        tv_car.setText(carStyle);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ReqAdapter reqAdapter = new ReqAdapter(R.layout.item_req,reqList);
        recyclerView.setAdapter(reqAdapter);
        tv_total.setText(price);
        tv_contact.setText(zName+zName);
    }

    @Override
    public void setClickEvent() {
        rl_address.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address:
                HuoAddressDialog huoAddressDialog = new HuoAddressDialog(mContext,zName,xName,zPhone,xPhone);
                huoAddressDialog.show();
                break;

            case R.id.tv_contact:
                HuoContactDialog huoContactDialog = new HuoContactDialog(mContext);
                huoContactDialog.show();
                break;
        }
    }
}
