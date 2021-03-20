package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.QueryHomePropupAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
