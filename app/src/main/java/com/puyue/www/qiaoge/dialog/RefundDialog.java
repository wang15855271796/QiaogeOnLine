package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ${王涛} on 2020/8/14
 */
public abstract class RefundDialog extends Dialog {

    Context mContext;
    public TextView tv_sure,hint;
    TextView tv_cancle;

    public RefundDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_refund);
        mContext = context;
        initView();
        initAction();
    }

    private void initView() {
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_cancle = findViewById(R.id.tv_cancle);
    }


    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }



    public abstract void Confirm();
    public abstract void Cancle();

}
