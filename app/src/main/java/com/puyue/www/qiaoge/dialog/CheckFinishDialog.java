package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/11/17
 */
public abstract class CheckFinishDialog extends Dialog {
    Context mContext;
    TextView tv_next;
    public CheckFinishDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_check_finish);
        mContext = context;
        initView();
    }


    private void initView() {
        tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }

    public abstract void Confirm();
}
