package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2020/4/13
 */
public abstract class CouponDialog extends Dialog {
    Context mContext;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    public View view;
    public Unbinder binder;
    public CouponDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        initView();
    }

    private void initView() {


        view = View.inflate(mContext, R.layout.dialog_coupon, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);

        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(mContext);
        getWindow().setAttributes(attributes);


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }


    public abstract void Login();
    public abstract void Register();
}
