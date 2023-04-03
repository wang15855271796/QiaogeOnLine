package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.PointShopModel;

import java.util.List;

public abstract class PointExchangeDialog extends Dialog {
    Context mContext;
    TextView tv_continue;
    TextView tv_cancel;
    TextView tv_message;
    List<PointShopModel.DataBean.DeductsBean> deducts;
    int position;
    public PointExchangeDialog(Context mContext, List<PointShopModel.DataBean.DeductsBean> deducts, int position) {
        super(mContext, R.style.promptDialog);
        setContentView(R.layout.dialog_point_exchange);
        this.mContext = mContext;
        this.deducts = deducts;
        this.position = position;
        initView();
    }

    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_message = findViewById(R.id.tv_message);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

        tv_message.setText("确定使用"+deducts.get(position).getPoint()+"积分,兑换"+deducts.get(position).getAmount()+"元优惠券吗");
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel();
            }
        });

    }

    public abstract void Confirm();
    public abstract void Cancel();
}
