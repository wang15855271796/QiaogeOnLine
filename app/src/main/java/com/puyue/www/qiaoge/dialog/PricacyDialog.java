package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.api.mine.login.RegisterAgreementAPI;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.mine.login.RegisterAgreementModel;
import com.puyue.www.qiaoge.view.flowtaglayout.StringUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class PricacyDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView tv_desc;
    TextView tv_cancel;
    TextView tv_sure;
    TextView tv_desc1;
    ImageView iv_close;
    public PricacyDialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        this.mContext = mContext;
        setContentView(R.layout.dialog_pricacy);
        initView();
        requestRegisterAgreement();
    }

    private void initView() {
        iv_close = findViewById(R.id.iv_close);
        tv_desc = findViewById(R.id.tv_desc);
        tv_desc1 = findViewById(R.id.tv_desc1);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        String desc = "为了更好地保障您的合法权益，请您阅读并同意以下协议《翘歌平台用户协议》及";
        SpannableStringBuilder spannableStringBuilder =
                StringSpecialHelper.buildSpanColorStyle(desc, 25, 10, Color.parseColor("#FF2A00"));
        tv_desc.setText(spannableStringBuilder);
        tv_desc.setOnClickListener(this);
        tv_desc1.setOnClickListener(this);
    }

    public abstract void Confirm();

    String mUrlAgreement;
    String url = "https://shaokao.qoger.com/apph5/html/yszc.html";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                Confirm();
                break;

            case R.id.tv_desc:
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, mUrlAgreement));
                break;

            case R.id.tv_desc1:
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", 1);
                intent.putExtra("name", "协议");
                mContext.startActivity(intent);
                break;

            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 注册协议接口
     */
    RegisterAgreementModel mModelRegisterAgreement;
    private void requestRegisterAgreement() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            RegisterAgreementAPI.requestRegisterAgreement(mContext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RegisterAgreementModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RegisterAgreementModel registerAgreementModel) {
                            mModelRegisterAgreement = registerAgreementModel;
                            if (mModelRegisterAgreement.success) {
                                mUrlAgreement = mModelRegisterAgreement.data;
                            } else {
                                AppHelper.showMsg(mContext, mModelRegisterAgreement.message);
                            }
                        }
                    });
        }
    }

}
