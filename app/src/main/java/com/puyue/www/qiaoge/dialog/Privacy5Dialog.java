package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/10/15
 */
public class Privacy5Dialog extends Dialog {
    Activity mContext;
    TextView tv_content;
    TextView tv_look;
    TextView tv_unAgree;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public Privacy5Dialog(@NonNull Activity context) {
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
                Privacy6Dialog privacy6Dialog = new Privacy6Dialog(mContext);
                privacy6Dialog.show();
                dismiss();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy4Dialog privacyDialog = new Privacy4Dialog(mContext);
                privacyDialog.show();
                dismiss();
            }
        });
    }
}
