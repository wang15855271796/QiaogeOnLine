package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.puyue.www.qiaoge.R;

import java.util.List;

public abstract class ShowKeFuDialog extends Dialog implements View.OnClickListener {

    Context mContext;
    TextView tv_phone;
    TextView tv_time;
    String mCustomerPhone;
    String onlineTime;
    public ShowKeFuDialog(@NonNull Context context, String mCustomerPhone, String onlineTime) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_call_phone);
        mContext = context;
        this.onlineTime = onlineTime;
        this.mCustomerPhone = mCustomerPhone;
        initAction();
    }
//    final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//    params.width = 600;
//    params.height = 800;
//dialog.getWindow().setAttributes(params);
//dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    private void initAction() {
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        tv_phone.setOnClickListener(this);

        tv_time.setText("在线客服"+ "("+onlineTime+")");
        tv_phone.setText("客服热线 ("+mCustomerPhone+")");
    }


    public abstract void Confirm1(String cell);
    public abstract void Confirm2();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_phone:
                Confirm1(mCustomerPhone);
                break;

            case R.id.tv_time:
                Confirm2();
                break;
        }
    }

}
