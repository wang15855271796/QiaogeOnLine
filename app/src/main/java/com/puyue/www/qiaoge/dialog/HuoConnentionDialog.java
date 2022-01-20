package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;

public abstract class HuoConnentionDialog extends Dialog {
    Context mContext;
    public TextView tv_connect,tv_next;
    public HuoConnentionDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_huo_connection);
        mContext = context;
        initView();
        initAction();
    }

    private void initView() {
        tv_connect= (TextView) findViewById(R.id.tv_connect);
        tv_next = (TextView) findViewById(R.id.tv_next);
    }


    private void initAction() {
        tv_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });
    }

    public abstract void Connect();
    public abstract void Next();
}
