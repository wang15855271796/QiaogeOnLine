package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/6/5
 */
public abstract class CouponChangeDialog extends Dialog {
    Context mContext;
    String role;
    public CouponChangeDialog(@NonNull Context context,String role) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_coupon_change);
        mContext = context;
        this.role = role;
        initView();
    }

    private void initView() {
        TextView tv_no = findViewById(R.id.tv_no);
        TextView tv_yes = findViewById(R.id.tv_yes);
        TextView tv_content = findViewById(R.id.tv_content);
        tv_content.setText(role);
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
