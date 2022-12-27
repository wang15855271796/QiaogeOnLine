package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/11/20
 */
public abstract class ContactDialog extends Dialog {

    Context mContext;
    String phoness;
    public ContactDialog(@NonNull Context context, String phoness) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_contact);
        mContext = context;
        this.phoness = phoness;
        initView();
    }

    private void initView() {
        TextView tv_no = findViewById(R.id.tv_no);
        TextView tv_yes = findViewById(R.id.tv_yes);
        TextView tv1 = findViewById(R.id.tv1);
        tv1.setText("若当前帐号"+phoness+"已停用建议您立刻更换手机号。");
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
