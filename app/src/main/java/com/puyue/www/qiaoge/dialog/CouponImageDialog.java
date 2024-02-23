package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;

public abstract class CouponImageDialog extends Dialog implements View.OnClickListener{
    Context mContext;
    TextView tv_cancel;
    TextView tv_sure;
    public CouponImageDialog(Context mContext) {
        super(mContext,R.style.promptDialog);
        this.mContext = mContext;
        setContentView(R.layout.dialog_coupon_image);
        initAction();
    }

    private void initAction() {
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_sure = (TextView) findViewById(R.id.tv_sure);


        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    public abstract void Confirm();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:

                dismiss();
                break;

            case R.id.tv_cancel:
                Confirm();
                break;
        }
    }

}
