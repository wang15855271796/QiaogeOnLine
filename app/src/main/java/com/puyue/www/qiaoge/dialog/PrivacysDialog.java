package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class PrivacysDialog extends Dialog {

    Activity mContext;
    String content;
    LinearLayout ll_sure;
    LinearLayout ll_cancel;
    TextView tv_content;
    public PrivacysDialog(@NonNull Activity context, String content) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy);
        mContext = context;
        this.content = content;
        initView();
        initAction();
    }

    private void initView() {
        ll_cancel = findViewById(R.id.ll_cancel);
        ll_sure = findViewById(R.id.ll_sure);
        tv_content = findViewById(R.id.tv_content);
        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveString(mContext,"once","0");
                dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtra("go_home", "goHome");
                        mContext.startActivity(intent);
                        mContext.finish();
                    }
                },1000);
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy2Dialog privacy2Dialog = new Privacy2Dialog(mContext,content);
                privacy2Dialog.show();
                dismiss();
            }
        });
        String s = tv_content.getText().toString();
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 14,
                9, Color.parseColor("#ff5000"));
        tv_content.setText(spannableStringBuilder);


        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, content));
            }
        });

    }


    private void initAction() {

    }


    CouponListDialog couponListDialog;

//    private void getCouponList() {
//        IndexHomeAPI.getCouponLists(mContext)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<CouponListModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(CouponListModel couponListModel) {
//                        if(couponListModel.isSuccess()) {
//                            lists = couponListModel.getData().getGifts();
//                            if(lists.size()>0) {
//                                couponListDialog = new CouponListDialog(mContext,couponListModel, lists);
//                                couponListDialog.show();
//                            }else {
//                                QueryHomePropup();
//                            }
//
//                        }else {
//                            AppHelper.showMsg(mContext,couponListModel.getMessage());
//                        }
//                    }
//                });
//    }

    /**
     *
     * 首页活动弹窗
     */
    HomeActivityDialog homeActivityDialog;
//    private void QueryHomePropup() {
//        QueryHomePropupAPI.requestQueryHomePropup(mContext)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<QueryHomePropupModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(QueryHomePropupModel queryHomePropupModel) {
//                        if (queryHomePropupModel.isSuccess()) {
//                            if(queryHomePropupModel.getData().getHomePropup()!=null) {
//                                QueryHomePropupModel.DataBean.HomePropupBean homePropup = queryHomePropupModel.getData().getHomePropup();
//                                homeActivityDialog = new HomeActivityDialog(mContext,homePropup);
//                                if (queryHomePropupModel.getData().isPropup()) {
//                                    homeActivityDialog.show();
//                                }else {
//                                    homeActivityDialog.dismiss();
//                                }
//                            }
//
//                        } else {
//                            AppHelper.showMsg(mContext, queryHomePropupModel.getMessage());
//                        }
//                    }
//                });
//    }


}
