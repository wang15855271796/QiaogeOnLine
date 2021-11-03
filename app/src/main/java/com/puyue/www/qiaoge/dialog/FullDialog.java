package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/8/26
 */
public class FullDialog extends Dialog {
    Context mContext;
    public TextView tv_ok;
    String fullGiftSendInfo;
    WebView webView;
    public FullDialog(@NonNull Context context,String fullGiftSendInfo) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_full);
        mContext = context;
        this.fullGiftSendInfo = fullGiftSendInfo;
        initView();
        initAction();
    }

    private void initView() {
        tv_ok = findViewById(R.id.tv_ok);
        webView = findViewById(R.id.webView);

        webView.loadDataWithBaseURL(null,fullGiftSendInfo, "text/html", "UTF-8", null);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    private void initAction() {
    }



}
