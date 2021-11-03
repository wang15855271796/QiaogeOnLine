package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/9/5
 */
public abstract class CouponProdDialog extends Dialog {

    Context mContext;
    TextView tv_account;
    public TextView tv_sure,hint;
    public TextView title;
    WebView webView;
    String datas;
    public CouponProdDialog(@NonNull Context context,String datas) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.coupon_dialog);
        mContext = context;
        this.datas = datas;
        initView();
        initAction();
    }

    private void initView() {
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        webView = (WebView) findViewById(R.id.webView);
        title = findViewById(R.id.title);
        tv_account = findViewById(R.id.tv_account);

        webView.loadDataWithBaseURL(null,datas, "text/html", "UTF-8", null);
    }

    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public abstract void Confirm();
}
