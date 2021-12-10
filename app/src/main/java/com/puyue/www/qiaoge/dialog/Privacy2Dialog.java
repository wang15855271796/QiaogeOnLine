package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy2Dialog extends Dialog {

    Activity mContext;
    TextView tv_content;
    TextView tv_sure;
    TextView tv_unAgree;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
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
        String content = "您可通过阅读完整的<font color='#3483FF'>《翘歌隐私政策》</font>及<font color='#3483FF'>《第三方信息数据共享》</font>来了解详情信息。";
        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
            }
        });

        tv_content.setText(Html.fromHtml(content));
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
