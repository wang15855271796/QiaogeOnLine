package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.MyCouponsActivity;
import com.puyue.www.qiaoge.event.GoToMarketEvent;

import org.greenrobot.eventbus.EventBus;

public class ExchangeSuccessDialog  extends Dialog {
    TextView tv_continue;
    TextView tv_cancel;
    TextView tv_look;
    Context mContext;
    public ExchangeSuccessDialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        setContentView(R.layout.dialog_exchange_success);
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_look = findViewById(R.id.tv_look);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
                EventBus.getDefault().post(new GoToMarketEvent());
                dismiss();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(mContext, MyCouponsActivity.class);
                mContext.startActivity(intent3);
                dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
