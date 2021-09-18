package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy2Dialog extends Dialog {

    Activity mContext;
    String content;
    TextView tv_content;
    TextView tv_look;
    TextView tv_unAgree;
    public Privacy2Dialog(@NonNull Activity context, String content) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy2);
        mContext = context;
        this.content = content;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_unAgree = findViewById(R.id.tv_unAgree);
        tv_look = findViewById(R.id.tv_look);

        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy3Dialog privacy3Dialog = new Privacy3Dialog(mContext,content);
                privacy3Dialog.show();
                dismiss();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacysDialog privacyDialog = new PrivacysDialog(mContext,content);
                privacyDialog.show();
                dismiss();
            }
        });
    }



}
