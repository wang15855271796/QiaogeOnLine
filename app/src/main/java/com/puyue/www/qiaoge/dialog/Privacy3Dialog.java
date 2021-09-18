package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy3Dialog extends Dialog {
    Activity mContext;
    String content;
    TextView tv_content;
    TextView tv_look;
    TextView tv_logout;
    public Privacy3Dialog(@NonNull Activity context, String content) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy3);
        mContext = context;
        this.content = content;
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
                PrivacysDialog privacysDialog = new PrivacysDialog(mContext,content);
                privacysDialog.show();
                dismiss();
            }
        });
    }

}
