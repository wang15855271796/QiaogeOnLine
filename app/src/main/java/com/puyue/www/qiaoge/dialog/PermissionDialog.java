package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;


public abstract class PermissionDialog extends Dialog {
    Context mContext;

    public TextView tv_sure,tv_content;
    public PermissionDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_persmission);

        mContext = context;

        initView();
        initAction();
    }

    private void initView() {
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_content = (TextView) findViewById(R.id.tv_content);

    }


    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }

    public abstract void Confirm();

}
