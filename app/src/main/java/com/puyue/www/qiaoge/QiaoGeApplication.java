package com.puyue.www.qiaoge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.puyue.www.qiaoge.event.InitEvent;
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
import com.weavey.loading.lib.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2018/3/21.
 */

public class QiaoGeApplication extends MultiDexApplication {
    public IWXAPI api;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        context = getApplicationContext();
//        Glide.with(context);
//        SharedPreferencesUtil.saveString(this,"pays","-1");
//        SDKInitializer.initialize(getApplicationContext());
//        //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setIsNeedAddress(true);
////        友盟
//        UMConfigure.init(this, "5facd45320657917050f92a0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "9bde9b69caaff881a14239cb326241b8");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
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
//                Log.d("wsassssssss......",s1);
//            }
//        });
//
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
//        UserInfoHelper.saveDate(this, 0+"");
//        api = WXAPIFactory.createWXAPI(this, "wxbc18d7b8fee86977");
//        api.registerApp("wxbc18d7b8fee86977");
//        JPushInterface.setDebugMode(false);
//        JPushInterface.init(this);
//
//        {
//
//            PlatformConfig.setWeixin("wxbc18d7b8fee86977", "710d1b08a6fd655ca8b3e4404fd937cd");
//            PlatformConfig.setQQZone("1106452431", "vgywMsj2j66nW35l");
//        }
//        UMConfigure.init(this, "5bcef11ab465f52b9d000094"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        disableAPIDialog();

        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setLoadingPageLayout(R.layout.test)
                .setEmptyImage(R.mipmap.ic_no_datas)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40);





    }
    public static Context getContext() {
        return context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInit(InitEvent initEvent) {
        SharedPreferencesUtil.saveString(this,"pays","-1");

//        SDKInitializer.initialize(getApplicationContext());
//        //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setIsNeedAddress(true);
//        友盟
        UMConfigure.init(this, "5facd45320657917050f92a0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "9bde9b69caaff881a14239cb326241b8");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName(R.class.getPackage().getName());
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken

            }
            @Override
            public void onFailure(String s, String s1) {

            }
        });

//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        UserInfoHelper.saveDate(this, 0+"");
        api = WXAPIFactory.createWXAPI(this, "wxbc18d7b8fee86977");
        api.registerApp("wxbc18d7b8fee86977");
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        {

            PlatformConfig.setWeixin("wxbc18d7b8fee86977", "710d1b08a6fd655ca8b3e4404fd937cd");
            PlatformConfig.setQQZone("1106452431", "vgywMsj2j66nW35l");
        }
        UMConfigure.init(this, "5bcef11ab465f52b9d000094"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        Unicorn.init(this, "32e2c3d171b7d70287c22876a5622022", options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
                RequestOptions options = new RequestOptions()
                        .centerCrop();
                if (width <= 0 || height <= 0) {
                    width = height = Integer.MIN_VALUE;
                }

                Glide.with(QiaoGeApplication.this).asBitmap().load(uri).apply(options)
                        .into(new SimpleTarget<Bitmap>(width, height) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (listener != null) {
                                    listener.onLoadComplete(resource);
                                }
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Throwable t = new Throwable("加载异常");
                                listener.onLoadFailed(t);
                            }
                        });
            }
        });

    }


    public static YSFOptions ysfOptions;
    /**
     //     * 网易七鱼客服
     //     *
     //     * @return
     //     */
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        /**
         * 客服消息通知
         */
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.statusBarNotificationConfig.notificationSmallIconId = R.mipmap.ic_launcher;
        options.onBotEventListener = new OnBotEventListener() {
            @Override
            public boolean onUrlClick(Context context, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
        };

        ysfOptions = options;
        return options;
    }


    private void disableAPIDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
