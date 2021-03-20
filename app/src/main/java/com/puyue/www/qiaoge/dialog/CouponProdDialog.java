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
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
