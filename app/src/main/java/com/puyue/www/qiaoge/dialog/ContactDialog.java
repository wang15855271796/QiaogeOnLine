package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/11/20
 */
public abstract class ContactDialog extends Dialog {

    Context mContext;
    public ContactDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_contact);
        mContext = context;
        initView();
    }

    private void initView() {
        TextView tv_no = findViewById(R.id.tv_no);
        TextView tv_yes = findViewById(R.id.tv_yes);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmNo();
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmYes();
            }
        });
    }

    public abstract void confirmNo();
    public abstract void confirmYes();
}
