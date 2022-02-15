package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.event.InitEvent;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class PrivacysDialog extends Dialog {

    Activity mContext;
    LinearLayout ll_sure;
    LinearLayout ll_cancel;
    public IWXAPI api;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public PrivacysDialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy);
        mContext = context;
        initView();
    }

    private void initView() {
        ll_cancel = findViewById(R.id.ll_cancel);
        ll_sure = findViewById(R.id.ll_sure);
        TextView tv_content1 = findViewById(R.id.tv_content1);
        TextView tv_content2 = findViewById(R.id.tv_content2);
        TextView tv_content3 = findViewById(R.id.tv_content3);

        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveString(mContext,"once","0");
                EventBus.getDefault().post(new InitEvent());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtra("go_home", "goHome");
                        mContext.startActivity(intent);
                        mContext.finish();
                    }
                },1000);
                dismiss();
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy2Dialog privacy2Dialog = new Privacy2Dialog(mContext);
                privacy2Dialog.show();
                dismiss();
            }
        });


        String s = tv_content1.getText().toString();
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 17,
                8, Color.parseColor("#3483FF"));
        tv_content1.setText(spannableStringBuilder);


        tv_content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, content));
            }
        });


        String s1 = tv_content2.getText().toString();
        SpannableStringBuilder spannableStringBuilder1 = StringSpecialHelper.buildSpanColorStyle(s1, 11,
                8, Color.parseColor("#3483FF"));
        tv_content2.setText(spannableStringBuilder1);



        SpannableString spStr = new SpannableString(tv_content3.getText().toString());
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, content));
            }
        }, 9, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, "https://shaokao.qoger.com/apph5/html/third.html"));
            }
        }, 19, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_content3.setText(spStr);
        tv_content3.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, content));
            }
        });
    }

}
