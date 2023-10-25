package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

public abstract class BackDialog1 extends Dialog implements View.OnClickListener {
    Activity mContext;
    TextView tv_sure;
    TextView tv_continue;

    public BackDialog1(Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_back1);
        this.mContext = context;
        initView();
    }

    private void initView() {
        tv_sure = findViewById(R.id.tv_sure);
        tv_continue = findViewById(R.id.tv_continue);
        tv_sure.setOnClickListener(this);
        tv_continue.setOnClickListener(this);
    }

    public abstract void Confirm();
    public abstract void sure();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                sure();
                break;

            case R.id.tv_continue:
                Confirm();
                break;
        }
    }
}
