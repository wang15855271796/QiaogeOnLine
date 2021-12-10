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
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
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
                SharedPreferencesUtil.saveString(mContext,"once","0");
                EventBus.getDefault().post(new InitEvent());
//                initSdk();
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

//        String s = tv_content1.getText().toString();
//        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 0,
//                9, Color.parseColor("#ff5000"));
//        tv_content1.setText(spannableStringBuilder);


//        tv_content1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, content));
//            }
//        });


//        String s1 = tv_content2.getText().toString();
//        SpannableStringBuilder spannableStringBuilder1 = StringSpecialHelper.buildSpanColorStyle(s1, 0,
//                6, Color.parseColor("#ff5000"));
//        tv_content2.setText(spannableStringBuilder1);
//
//
//        tv_content2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, register));
//            }
//        });


    }

//    private void initSdk() {
//        SharedPreferencesUtil.saveString(QiaoGeApplication.getContext(),"pays","-1");
//        SDKInitializer.initialize(QiaoGeApplication.getContext());
//        //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setIsNeedAddress(true);
////        友盟
//        UMConfigure.init(QiaoGeApplication.getContext(), "5facd45320657917050f92a0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "9bde9b69caaff881a14239cb326241b8");
//        PushAgent mPushAgent = PushAgent.getInstance(QiaoGeApplication.getContext());
//        mPushAgent.setResourcePackageName(R.class.getPackage().getName());
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken
//
//            }
//            @Override
//            public void onFailure(String s, String s1) {
//            }
//        });
//
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
//        UserInfoHelper.saveDate(QiaoGeApplication.getContext(), 0+"");
//        api = WXAPIFactory.createWXAPI(QiaoGeApplication.getContext(), "wxbc18d7b8fee86977");
//        api.registerApp("wxbc18d7b8fee86977");
//        JPushInterface.setDebugMode(false);
//        JPushInterface.init(QiaoGeApplication.getContext());
//
//        {
//
//            PlatformConfig.setWeixin("wxbc18d7b8fee86977", "710d1b08a6fd655ca8b3e4404fd937cd");
//            PlatformConfig.setQQZone("1106452431", "vgywMsj2j66nW35l");
//        }
//        UMConfigure.init(QiaoGeApplication.getContext(), "5bcef11ab465f52b9d000094"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//
//        Unicorn.init(QiaoGeApplication.getContext(), "32e2c3d171b7d70287c22876a5622022", options(), new UnicornImageLoader() {
//            @Nullable
//            @Override
//            public Bitmap loadImageSync(String uri, int width, int height) {
//                return null;
//            }
//
//            @Override
//            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
//                RequestOptions options = new RequestOptions()
//                        .centerCrop();
//                if (width <= 0 || height <= 0) {
//                    width = height = Integer.MIN_VALUE;
//                }
//
//                Glide.with(QiaoGeApplication.getContext()).asBitmap().load(uri).apply(options)
//                        .into(new SimpleTarget<Bitmap>(width, height) {
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                if (listener != null) {
//                                    listener.onLoadComplete(resource);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                                super.onLoadFailed(errorDrawable);
//                                Throwable t = new Throwable("加载异常");
//                                listener.onLoadFailed(t);
//                            }
//                        });
//            }
//        });
//    }

//    public static YSFOptions ysfOptions;
    /**
     //     * 网易七鱼客服
     //     *
     //     * @return
     //     */
//    private YSFOptions options() {
//        YSFOptions options = new YSFOptions();
//        /**
//         * 客服消息通知
//         */
//        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
//        options.statusBarNotificationConfig.notificationSmallIconId = R.mipmap.ic_launcher;
//        options.onBotEventListener = new OnBotEventListener() {
//            @Override
//            public boolean onUrlClick(Context context, String url) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                context.startActivity(intent);
//                return true;
//            }
//        };
//
//        ysfOptions = options;
//        return options;
//    }
}
