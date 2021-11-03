package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/10/15
 */
public class Privacy6Dialog extends Dialog {
    Activity mContext;
    TextView tv_content;
    TextView tv_look;
    TextView tv_logout;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public Privacy6Dialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy3);
        mContext = context;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_logout = findViewById(R.id.tv_logout);
        tv_look = findViewById(R.id.tv_look);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy4Dialog privacysDialog = new Privacy4Dialog(mContext);
                privacysDialog.show();
                dismiss();
            }
        });
    }
}
