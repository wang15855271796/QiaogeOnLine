package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/4/1
 */
public abstract class AuthDialog extends Dialog implements View.OnClickListener{

    Context mContext;
    TextView tv_sure;
    TextView iv_cancel;
    TextView tv_get;
    EditText et_authprize;
    public AuthDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_authorize);
        mContext = context;
        initView();
        initAction();
    }

    private void initAction() {
        tv_get = (TextView) findViewById(R.id.tv_get);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        iv_cancel = (TextView) findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(this);
        iv_cancel.setOnClickListener(this);
        tv_get.setOnClickListener(this);

    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                dismiss();
                break;

            case R.id.tv_sure:
                close();
                break;

        }

    }
    public abstract void close();

}
