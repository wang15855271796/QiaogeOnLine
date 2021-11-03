package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy2Dialog extends Dialog {

    Activity mContext;
    TextView tv_content;
    TextView tv_look;
    TextView tv_unAgree;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public Privacy2Dialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy2);
        mContext = context;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_unAgree = findViewById(R.id.tv_unAgree);
        tv_look = findViewById(R.id.tv_look);

        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy3Dialog privacy3Dialog = new Privacy3Dialog(mContext);
                privacy3Dialog.show();
                dismiss();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacysDialog privacyDialog = new PrivacysDialog(mContext);
                privacyDialog.show();
                dismiss();
            }
        });
    }
}
