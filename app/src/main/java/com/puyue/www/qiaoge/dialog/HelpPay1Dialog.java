package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;

import java.util.List;

public abstract class HelpPay1Dialog extends Dialog implements View.OnClickListener {

    Activity mContext;
    TextView tv_look;
    TextView tv_phone;
    TextView tv_amount;
    ImageView iv_close;
    int pos;
    List<IndexInfoModel.DataBean.SendOrderBean> sendOrder;
    public HelpPay1Dialog(Activity context, List<IndexInfoModel.DataBean.SendOrderBean> sendOrder, int pos) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_help_pay1);
        this.mContext = context;
        this.pos = pos;
        this.sendOrder = sendOrder;
        initView();
    }

    private void initView() {
        tv_look = findViewById(R.id.tv_look);
        iv_close = findViewById(R.id.iv_close);
        tv_phone = findViewById(R.id.tv_phone);
        tv_amount = findViewById(R.id.tv_amount);
        tv_look.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tv_phone.setText("来自用户:"+sendOrder.get(pos).getSendPhone());
        tv_amount.setText("￥"+sendOrder.get(pos).getOrderAmt());
    }

    public abstract void sure(int pos, int orderDeliveryType);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look:
                sure(pos,sendOrder.get(pos).getOrderDeliveryType());
                dismiss();
                break;

            case R.id.iv_close:
                dismiss();
                break;
        }
    }

}
