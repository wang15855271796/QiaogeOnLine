package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

import java.util.logging.Handler;

/**
 * Created by ${王涛} on 2020/11/17
 */
public abstract class SecretSuccessDialog extends Dialog {
    private android.os.Handler handler = new android.os.Handler();
    Context mContext;
    TextView tv_login;

    public SecretSuccessDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_secret_success);
        mContext = context;
        initView();
    }


    private void initView() {
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Close();
            }
        },3000);
    }

    public abstract void Confirm();
    public abstract void Close();
}
