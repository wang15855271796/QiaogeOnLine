package com.puyue.www.qiaoge.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.HomeActivity;

import com.puyue.www.qiaoge.activity.mine.order.MyOrdersActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.VipPayResultActivity;
import com.puyue.www.qiaoge.adapter.VipListAdapter;
import com.puyue.www.qiaoge.api.cart.GetPayResultAPI;

import com.puyue.www.qiaoge.api.mine.order.VipPayAPI;
import com.puyue.www.qiaoge.api.mine.order.VipPayResultAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.WeChatPayEvent;
import com.puyue.www.qiaoge.event.WeChatUnPayEvent;
import com.puyue.www.qiaoge.helper.AppHelper;

import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.VipListModel;
import com.puyue.www.qiaoge.model.cart.GetPayResultModel;

import com.puyue.www.qiaoge.model.mine.order.VipPayModel;
import com.puyue.www.qiaoge.model.mine.order.VipPayResultModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import com.puyue.www.qiaoge.utils.ToastUtil;

import com.rrtx.tzpaylib.CashierManager;
import com.rrtx.tzpaylib.PaymentCallback;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by ${daff}
 * on 2018/10/25
 * 备注  我的积分支付页面
 */
public class IntegralPayActivity extends BaseSwipeActivity {

    private ImageView imageViewBack;
    private ImageView alipay;
    private ImageView wechat;
    private ImageView okPay;
    private String vipPackageId;
    private int payChannel;  // 支付渠道   2=支付宝   3=微信
    private String outTradeNo;
    RecyclerView recyclerView;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    AVLoadingIndicatorView lav_activity_loading;
    ImageView iv_yun;
    private Handler handler = new Handler();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_integral_pay);

    }

    VipListAdapter vipListAdapter;
    int jumpWx = -1;
    @Override
    public void findViewById() {
        recyclerView = findViewById(R.id.recyclerView);
        lav_activity_loading = (AVLoadingIndicatorView) findViewById(R.id.lav_activity_loading);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        okPay = (ImageView) findViewById(R.id.okPay);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        vipListAdapter = new VipListAdapter(R.layout.item_vip,list);
        recyclerView.setAdapter(vipListAdapter);
        vipListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                payChannel = list.get(position).getFlag();
                jumpWx = list.get(position).getJumpWx();
                vipListAdapter.selectPosition(position);
            }
        });
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        vipPackageId = getIntent().getStringExtra("vipPackageId");
    }

    @Override
    public void setClickEvent() {
        imageViewBack.setOnClickListener(noDoubleClickListener);
        okPay.setOnClickListener(noDoubleClickListener);
    }

    public NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.imageViewBack:
                    finish();
                    break;

                case R.id.okPay:
                    if (payChannel==0){
                        AppHelper.showMsg(mContext, "请选择支付方式");
                        return;
                    }
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    lav_activity_loading.show();
                    requestVipPay(vipPackageId,payChannel);
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        if(jumpWx==1) {
            mActivity.finish();
        }

        if(outTradeNo!=null&&jumpWx==0) {
            Intent intent = new Intent(mContext, VipPayResultActivity.class);
            intent.putExtra("payChannal", payChannel);
            intent.putExtra("outTradeNo", outTradeNo);
            startActivity(intent);
            finish();
        }

        getPayList();
    }

    List<VipListModel.DataBean> list = new ArrayList<>();
    List<VipListModel.DataBean> datas;
    private void getPayList() {
        VipPayAPI.getPayList(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipListModel vipListModel) {
                        if (vipListModel.getCode()==1) {
                            if(vipListModel.getData()!=null) {
                                datas = vipListModel.getData();
                                list.clear();
                                list.addAll(vipListModel.getData());
                                vipListAdapter.notifyDataSetChanged();
                            }

                        } else {
                            AppHelper.showMsg(mContext, vipListModel.getMessage());

                        }
                    }
                });
    }

    private void getPayResult(String outTradeNo) {
        VipPayResultAPI.requestOrderComment(mContext, outTradeNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipPayResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipPayResultModel getPayResultModel) {
                        logoutAndToHome(mContext, getPayResultModel.getCode());
                        if (getPayResultModel.isSuccess()) {
                            if(getPayResultModel.getData()!=null) {
                                Intent intent = new Intent(mContext, VipPayResultActivity.class);
                                intent.putExtra("payChannal", payChannel);
                                intent.putExtra("outTradeNo", outTradeNo);
                                startActivity(intent);
                                finish();


                            }

                        } else {
                            AppHelper.showMsg(mContext, getPayResultModel.getMessage());

                        }
                    }
                });
    }

    private void requestVipPay(String vipPackageId,int payChannel) {
        VipPayAPI.requestVipPayData(mContext, vipPackageId,payChannel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipPayModel vipPayModel) {
                        if (vipPayModel.isSuccess()) {
                            outTradeNo = vipPayModel.getData().getOutTradeNo();
                            String orderNoList = vipPayModel.getData().orderNoList;
                            String businessCstNo = vipPayModel.getData().businessCstNo;
                            String merchantNo = vipPayModel.getData().merchantNo;
                            UserInfoHelper.saveWalletStatus(mContext, outTradeNo);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("orderFlowNo",orderNoList);
                                jsonObject.put("businessCstNo",businessCstNo);
                                jsonObject.put("platMerCstNo",merchantNo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //台州银行
                            if(vipPayModel.getData().getPayType()==16) {
                                CashierManager.getInstance().init(mActivity);
                                CashierManager.getInstance().launchPayment(jsonObject.toString(), new PaymentCallback() {
                                    @Override
                                    public void paymentResult(String s) {
                                        switch (s){
                                            case "10":
                                                //初始状态
                                                break;

                                            case "70":
                                                //失败
                                                getPayResult(outTradeNo);
                                                break;

                                            case "80":
                                                //关闭
                                                ToastUtil.showSuccessMsg(mContext,"支付通道关闭");
                                                break;

                                            case "90":
                                                //成功
                                                getPayResult(outTradeNo);
                                                break;
                                        }
                                    }
                                });
                            }

                            if (payChannel == 2&&vipPayModel.getData().getPayType()==2) {
                                //支付宝支付
                                SharedPreferencesUtil.saveString(mContext,"payKey","2");
                                aliPay(vipPayModel.getData().getPayToken());
                            } else if (payChannel == 3&&jumpWx==1) {
                                //微信支付(小程序)
                                SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                Intent lan = mActivity.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                Intent t2 = new Intent(Intent.ACTION_MAIN);
                                t2.addCategory(Intent.CATEGORY_LAUNCHER);
                                t2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                t2.setComponent(lan.getComponent());
                                startActivity(t2);
                                weChatPay(vipPayModel.getData().getPayToken());

                            }else if(payChannel == 3&&jumpWx==0) {
                                //微信支付
                                SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                weChatPay2(vipPayModel.getData().getPayToken());
                            }else if(vipPayModel.getData().getPayType()==14&&payChannel == 2) {
                                //银联
                                SharedPreferencesUtil.saveString(mContext,"payKey","4");
                                payAliPay(vipPayModel.getData().getPayToken());
                            }
                        } else {
                            AppHelper.showMsg(mContext, vipPayModel.getMessage());
                        }
                    }
                });
    }

    private void weChatPay2(String json) {
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(this, "wxbc18d7b8fee86977");
            JSONObject obj = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = obj.optString("appId");
            request.partnerId = obj.optString("mchID");
            request.prepayId = obj.optString("prepayId");
            request.packageValue = obj.optString("pkg");
            request.nonceStr = obj.optString("nonceStr");
            request.timeStamp = obj.optString("timeStamp");
            request.sign = obj.optString("paySign");
            api.sendReq(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lav_activity_loading.setVisibility(View.GONE);
        lav_activity_loading.hide();
    }

    private void payAliPay(String parms) {
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = parms;
        UnifyPayPlugin.getInstance(mContext).sendPayRequest(msg);
        lav_activity_loading.setVisibility(View.GONE);
        lav_activity_loading.hide();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = MyOrdersActivity.getIntent(getContext(), HomeActivity.class, AppConstant.ALL);
//                intent.putExtra("orderDeliveryType",0);
//                startActivity(intent);
//                finish();
//            }
//        },20000);

//        UnifyPayRequest msg = new UnifyPayRequest();
//        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
//        msg.payData = parms;
//        UnifyPayPlugin.getInstance(getContext()).sendPayRequest(msg);
    }


    //---------------------------支付逻辑------------------------------------------------//
    /**
     * 微信支付(小程序)
     */

    private void weChatPay(String json) {
        SharedPreferencesUtil.saveString(getContext(),"pays","0");
        String appId = "wx24c9fe5477c95b47"; // 填移动应用(App)的 AppId，非小程序的 AppID
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), appId);
        String userId = UserInfoHelper.getUserId(getContext());
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_02750c16f80b"; // 填小程序原始id
        req.path = "/pagesGoods/vippage/appvippay?vipPackageId="+vipPackageId+"&token="+userId;
        ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType =  WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
        lav_activity_loading.hide();
    }

    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(mContext, VipPayResultActivity.class);
        intent.putExtra("payChannal", payChannel);
        intent.putExtra("outTradeNo", outTradeNo);
        startActivity(intent);
        finish();
    }

    /**
     * 微信支付用户已取消支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatUnPayEvent event) {
    }


    /**
     * 支付宝支付
     */
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(IntegralPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        lav_activity_loading.setVisibility(View.GONE);
        lav_activity_loading.hide();
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * 支付宝支付结果
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    if ("9000".equals(result.get("resultStatus"))) {
                        //支付成功
                        Intent intent = new Intent(mContext, VipPayResultActivity.class);
                        intent.putExtra("payChannal", payChannel);
                        intent.putExtra("outTradeNo", outTradeNo);
                        startActivity(intent);
                        finish();
                    } else if ("6001".equals(result.get("resultStatus"))) {
                        //用户取消支付
                        AppHelper.showMsg(mContext, "您已取消支付");
                    } else if ("6002".equals(result.get("resultStatus"))) {
                        //网络连接错误
                        AppHelper.showMsg(mContext, "网络连接错误");
                    } else {
                        //支付失败
                        Intent intent = new Intent(mContext, VipPayResultActivity.class);
                        intent.putExtra("payChannal", payChannel);
                        intent.putExtra("outTradeNo", outTradeNo);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
