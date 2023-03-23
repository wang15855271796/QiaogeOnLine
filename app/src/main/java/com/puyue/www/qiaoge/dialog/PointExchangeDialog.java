package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

public abstract class PointExchangeDialog extends Dialog {
    Context mContext;
    TextView tv_continue;
    TextView tv_cancel;
    public PointExchangeDialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        setContentView(R.layout.dialog_point_exchange);
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

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
