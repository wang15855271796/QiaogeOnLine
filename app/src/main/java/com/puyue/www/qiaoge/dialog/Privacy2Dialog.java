package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy2Dialog extends Dialog {

    Activity mContext;
    TextView tv_content;
    TextView tv_sure;
    TextView tv_unAgree;
    String contents = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public Privacy2Dialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy2);
        mContext = context;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_unAgree = findViewById(R.id.tv_unAgree);
        tv_sure = findViewById(R.id.tv_sure);
        TextView tv_content = findViewById(R.id.tv_content);
        String content = "您可通过阅读完整的《翘歌隐私政策》及《第三方信息数据共享》来了解详情信息。";
        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
            }
        });


        SpannableString spStr = new SpannableString(tv_content.getText().toString());
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, contents));
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

        tv_content.setText(spStr);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());


        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("go_home", "goHome");
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
    }
}
