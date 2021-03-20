package com.puyue.www.qiaoge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;

import static com.qiyukf.nimlib.sdk.msg.constant.SystemMessageStatus.init;


/**
 * Created by Administrator on 2018/3/21.
 */

public class QiaoGeApplication extends MultiDexApplication {
    public IWXAPI api;
//    public LocationClient mLocationClient = null;
//    private MyLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil.saveString(this,"pays","-1");
//        CrashReport.initCrashReport(getApplicationContext(), "385e5aaa75", false);
        SDKInitializer.initialize(getApplicationContext());
//        mLocationClient = new LocationClient(getApplicationContext());
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
//        友盟
        UMConfigure.init(this, "5facd45320657917050f92a0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "9bde9b69caaff881a14239cb326241b8");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName(R.class.getPackage().getName());
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken
                Log.d("wsassssssss......",deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                Log.d("wsassssssss......",s1);
            }
        });
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
//
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
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
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

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


        Unicorn.init(this, "32e2c3d171b7d70287c22876a5622022", options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
                RequestOptions options = new RequestOptions()
//                        .placeholder(R.drawable.placeholder)
                        .centerCrop();
//                .error(R.drawable.error);
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


//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            //以下只列举部分获取地址相关的结果信息
//            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//
//            UserInfoHelper.saveLocation(getApplicationContext(),location.getAddrStr());
//
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
////            city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息
//            String streetNumber = location.getStreetNumber();
//
//
//
//            Log.i("citysss..............",location.getLocType()+"");
//
//
//        }
//    }

}
