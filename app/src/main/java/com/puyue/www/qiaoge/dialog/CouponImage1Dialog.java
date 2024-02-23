package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.puyue.www.qiaoge.R;

public abstract class CouponImage1Dialog extends Dialog implements View.OnClickListener {
    Context mContext;
    ImageView iv_close;
    TextView tv_sure;
    EditText et_content;
    public CouponImage1Dialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        this.mContext = mContext;
        setContentView(R.layout.dialog_coupon_image1);
        initAction();
    }

    private void initAction() {
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        et_content = (EditText) findViewById(R.id.et_content);
        iv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    public abstract void Confirm(String content);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                Confirm(et_content.getText().toString());
                break;

            case R.id.iv_close:
                dismiss();
                break;

        }
    }
}
