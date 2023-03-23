package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;

public abstract class PayErrorDialog extends Dialog {
    Context mContext;
    TextView tv_continue;
    TextView tv_message;
    TextView tv_cancel;
    String message;
    public PayErrorDialog(@NonNull Context context,String message) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_pay_error);
        mContext = context;
        this.message = message;
        initView();
    }


    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_message = findViewById(R.id.tv_message);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_message.setText(message);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel();
            }
        });
    }

    public abstract void Confirm();
    public abstract void Cancel();
}
