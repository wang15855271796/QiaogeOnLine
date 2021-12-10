package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.InitEvent;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/15
 */
public class Privacy4Dialog extends Dialog {
    Activity mContext;
    LinearLayout ll_sure;
    LinearLayout ll_cancel;
    public IWXAPI api;
    String content = "https://shaokao.qoger.com/apph5/html/yszc2.html";
    String register = "http://staff.qoger.com/h5/enter.html";
    public Privacy4Dialog(@NonNull Activity context) {
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
        String content1 = "您可通过阅读完整的<font color='#3483FF'>《翘歌隐私政策》</font>及<font color='#3483FF'>《第三方信息数据共享》</font>来了解详情信息。";
        String content = "感谢您信任并使用翘歌。\n" +
                "\n" +
                "我们将依据<font color='#3483FF'>《翘歌隐私政策》</font>来帮助您了解我们在收集、使用、存储和共享您个人信息的情况以及您享有的相关权利。\n" +
                "\n" +
                "\n" +
                "\n" +
                "我们将通过<font color='#3483FF'>《翘歌隐私政策》</font>向您说明：\n" +
                "\n" +
                "\n" +
                "\n" +
                "为了您可以更好地享用周边的商品服务，我们会根据您的授权内容，收集和使用对应的必要信息（例如您的联系电话、位置信息、配送地址等）。\n" +
                "\n" +
                "\n" +
                "\n" +
                "在您使用行业信息进行信息发布、在线客服等服务时，我们需要获取您设备的相机权限、相册权限、位置权限、通讯录权限、录音权限等信息。\n" +
                "\n" +
                "\n" +
                "\n" +
                "您可以对上述信息进行访问、更正、删除您的个人信息或管理您的授权以及注销账户，我们也将提供专门的个人信息保护联系方式。\n" +
                "\n" +
                "\n" +
                "\n" +
                "未经您的授权同意，我们不会将上述信息共享给第三方或用于您未授权的其他用途。\n" +
                "\n" +
                "\n" +
                "\n" +
                "我们会采用行业内领先的安全技术来保护您的个人信息。";
        tv_content1.setText(Html.fromHtml(content));
        tv_content2.setText(Html.fromHtml(content1));
        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveString(mContext, "once", "0");
                EventBus.getDefault().post(new InitEvent());
                dismiss();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy5Dialog privacy5Dialog = new Privacy5Dialog(mContext);
                privacy5Dialog.show();
                dismiss();
            }
        });
    }
}
