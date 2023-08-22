package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

public abstract class HelpPay1Dialog extends Dialog implements View.OnClickListener {

    Activity mContext;
    TextView tv_look;
    ImageView iv_close;
    TextView tv_address;

    public HelpPay1Dialog(Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_help_pay1);
        this.mContext = context;
        initView();
    }

    private void initView() {

        tv_look = findViewById(R.id.tv_look);
        iv_close = findViewById(R.id.iv_close);
        tv_address = findViewById(R.id.tv_address);
        tv_look.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    public abstract void sure();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look:
                sure();
                break;

            case R.id.iv_close:
                dismiss();
                break;
        }
    }

}
