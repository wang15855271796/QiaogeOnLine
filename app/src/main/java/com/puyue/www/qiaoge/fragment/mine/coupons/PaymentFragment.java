package com.puyue.www.qiaoge.fragment.mine.coupons;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.cart.PayResultActivity;
import com.puyue.www.qiaoge.activity.mine.account.HisActivity;
import com.puyue.www.qiaoge.activity.mine.account.PayActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.PayListAdapter;
import com.puyue.www.qiaoge.api.cart.CheckPayPwdAPI;
import com.puyue.www.qiaoge.api.cart.GetPayResultAPI;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.AccountCenterAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.PayErrorDialog;
import com.puyue.www.qiaoge.dialog.YueDialog;
import com.puyue.www.qiaoge.event.WeChatPayEvent;
import com.puyue.www.qiaoge.event.WeChatUnPayEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.PayListModel;
import com.puyue.www.qiaoge.model.cart.CheckPayPwdModel;
import com.puyue.www.qiaoge.model.cart.GetPayResultModel;
import com.puyue.www.qiaoge.model.cart.OrderPayModel;
import com.puyue.www.qiaoge.model.mine.AccountCenterModel;
import com.puyue.www.qiaoge.utils.DateUtils;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/8/14
 */
public class PaymentFragment extends DialogFragment {

    private RelativeLayout rePayWay, rePayDetail;
    private LinearLayout LinPayWay;
    private Button btnPay;
    TextView tv_balance;
    TextView tv_amount;
    List<PayListModel.DataBean> list = new ArrayList<>();
    RecyclerView recyclerView;
    PayListAdapter payListAdapter;
    ImageView iv_close;
    int selectionPosition = 0;
    String payAmount;
    String orderId;
    private byte payChannel;
    String orderDeliveryType;
    ImageView iv_closes;
    AVLoadingIndicatorView lav_activity_loading;
    String remark;
    int jumpWx = -1;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String total = bundle.getString("total");
        payAmount = bundle.getString("payAmount");
        remark = bundle.getString("remark");
        orderId = bundle.getString("orderId");
        orderDeliveryType = bundle.getString("orderDeliveryType");

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_detail);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        orderPay();
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.setAttributes(lp);
        lav_activity_loading = (AVLoadingIndicatorView) dialog.findViewById(R.id.lav_activity_loading);
        iv_closes = (ImageView) dialog.findViewById(R.id.iv_closes);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        tv_amount = (TextView) dialog.findViewById(R.id.tv_amount);
        tv_balance = (TextView) dialog.findViewById(R.id.tv_balance);
        rePayWay = (RelativeLayout) dialog.findViewById(R.id.re_pay_way);
        rePayDetail = (RelativeLayout) dialog.findViewById(R.id.re_pay_detail);//付款详情
        LinPayWay = (LinearLayout) dialog.findViewById(R.id.lin_pay_way);//付款方式
        btnPay = (Button) dialog.findViewById(R.id.btn_confirm_pay);
        rePayWay.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        tv_amount.setText(total);
        iv_close.setOnClickListener(listener);
        rePayWay.setEnabled(false);
        iv_closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderDeliveryType.equals("0")){
                    Intent intent = new Intent(getContext(), NewOrderDetailActivity.class);
                    intent.putExtra("orderDeliveryType", orderDeliveryType);
                    intent.setClass(getContext(), NewOrderDetailActivity.class);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("account","2");
                    intent.putExtra(AppConstant.ORDERSTATE, "1");
                    startActivity(intent);
                    SharedPreferencesUtil.saveString(getContext(),"account","2");
                }else if (orderDeliveryType.equals("1")){
                    Intent intent = new Intent(getContext(), SelfSufficiencyOrderDetailActivity.class);
                    intent.putExtra("orderDeliveryType", orderDeliveryType);
                    intent.putExtra("account","2");
                    intent.setClass(getContext(), SelfSufficiencyOrderDetailActivity.class);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra(AppConstant.ORDERSTATE, "1");
                    startActivity(intent);
                    SharedPreferencesUtil.saveString(getContext(),"account","2");
                }
                dismiss();
                getActivity().finish();

            }
        });
        return dialog;
    }
    Animation slide_left_to_right;
    YueDialog yueDialog;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_to_left);
            slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
            switch (view.getId()) {
                case R.id.iv_close:
                    rePayDetail.startAnimation(slide_left_to_right);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    break;
                case R.id.re_pay_way:
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    LinPayWay.startAnimation(slide_right_to_left);
                    LinPayWay.setVisibility(View.VISIBLE);
                    break;

                case R.id.btn_confirm_pay:
                    if(data.get(selectionPosition).getFlag().equals("0")) {
                        String userWalletAccount = UserInfoHelper.getUserWalletAccount(getContext());
                        if (Double.parseDouble(payAmount) > Double.parseDouble(userWalletAccount)) {
                            yueDialog = new YueDialog(getContext(), userWalletAccount) {
                                @Override
                                public void close() {
                                    dismiss();
                                }
                            };
                            yueDialog.show();

                        }

                    }
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    lav_activity_loading.show();
                    //调支付接口
                    orderPays(orderId, payChannel, Double.parseDouble(payAmount), remark);
                    getDatas(1);
                    break;
                default:
                    break;
            }
        }
    };


    private void getDatas(long end) {
        RecommendApI.getDatas(getActivity(),18,end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {

                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(outTradeNo!=null&&SharedPreferencesUtil.getString(getContext(),"payKey").equals("5")) {
//            getPayResult(outTradeNo);
//        }
//
//        if(outTradeNo!=null&&SharedPreferencesUtil.getString(getContext(),"payKey").equals("4")) {
//            getPayResult(outTradeNo);
//        }

        if(jumpWx==1) {
            Intent intent = new Intent(getActivity(),NewOrderDetailActivity.class);
            intent.putExtra(AppConstant.ORDERID,orderId);
            startActivity(intent);
            getActivity().finish();
        }

        if(outTradeNo!=null&&jumpWx==0) {
            getPayResult(outTradeNo);
        }
    }

    // 支付
    String outTradeNo;
    int errorFlag = 0;
    private void orderPays(final String orderId, final byte payChannel, double payAmount, String remark) {
        OrderPayAPI.requestData(getContext(), orderId, payChannel, payAmount, remark,errorFlag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderPayModel orderPayModel) {
                        if (orderPayModel.success) {
                            outTradeNo = orderPayModel.data.outTradeNo;
                            String orderNoList = orderPayModel.data.orderNoList;
                            String businessCstNo = orderPayModel.data.businessCstNo;
                            String merchantNo = orderPayModel.data.merchantNo;
                            UserInfoHelper.saveWalletStatus(getContext(), outTradeNo);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("orderFlowNo",orderNoList);
                                jsonObject.put("businessCstNo",businessCstNo);
                                jsonObject.put("platMerCstNo",merchantNo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //台州银行
                            if(orderPayModel.data.payType==16) {
                                CashierManager.getInstance().init(getActivity());
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
                                                ToastUtil.showSuccessMsg(getContext(),"支付通道关闭");
                                                break;

                                            case "90":
                                                //成功
                                                getPayResult(outTradeNo);
                                                break;
                                        }
                                    }
                                });
                            }
                            if (payChannel == 1) {
                                //余额支付
                                //ok
                                SharedPreferencesUtil.saveString(getContext(),"payKey","1");
                                accountCenter();

                            } else if (payChannel == 2&&orderPayModel.data.payType==2) {
                                //支付宝支付 已经改好了
                                if(DateUtils.isZhiFuBao(getActivity())) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","2");
                                    aliPay(orderPayModel.data.payToken);
                                }

                            } else if (payChannel == 3&&jumpWx==1) {
                                //微信支付(小程序)1
                                if(DateUtils.isWeixin(getActivity())) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                    Intent lan = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                    Intent t2 = new Intent(Intent.ACTION_MAIN);
                                    t2.addCategory(Intent.CATEGORY_LAUNCHER);
                                    t2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    t2.setComponent(lan.getComponent());
                                    startActivity(t2);
                                    weChatPay(orderPayModel.data.payToken);
                                }

                            }else if(payChannel == 3&&jumpWx==0) {
                                //微信支付
                                if(DateUtils.isWeixin(getActivity())) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                    weChatPay2(orderPayModel.data.payToken);
                                }
                            }else if(orderPayModel.data.payType==14&&payChannel == 2) {
                                //银联
                                if(DateUtils.isZhiFuBao(getActivity())) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","4");
                                    payAliPay(orderPayModel.data.payToken);
                                }
                            } else if(orderPayModel.data.payType==22) {
                                //支付宝跳转小程序
                                SharedPreferencesUtil.saveString(getContext(),"payKey","5");
                                zhiFuBaoPay(orderPayModel.data.payToken);

                            }

                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                        } else if(orderPayModel.code ==100006) {
                            //ok
                            PayErrorDialog payErrorDialog = new PayErrorDialog(getContext(), orderPayModel.message) {
                                @Override
                                public void Confirm() {
                                    errorFlag = 1;
                                    orderPays(orderId, payChannel, payAmount, remark);
                                }

                                @Override
                                public void Cancel() {
                                    dismiss();
                                }
                            };
                            payErrorDialog.show();
                        } else {
                            //ok
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            ToastUtil.showSuccessMsg(getContext(),orderPayModel.message);
                        }
                    }
                });
    }

    /**
     * 支付宝
     * @param parms
     */
    private void payAliPay(String parms){
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = parms;
        UnifyPayPlugin.getInstance(getContext()).sendPayRequest(msg);
    }


    private void weChatPay2(String json) {
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(getContext(), "wxbc18d7b8fee86977");
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
    }

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
        req.path = "/pagesGoods/toplay/apptoplay?token="+userId+"&oderNo="+orderId;
        ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType =  WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }

    /**
     * 支付宝支付（小程序）
     */
    private void zhiFuBaoPay(String json) {
        try {
            String uri = json;
            Intent intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(getContext(), PayResultActivity.class);
        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
        intent.putExtra(AppConstant.ORDERID, orderId);
        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
        startActivity(intent);
    }

    /**
     * 微信支付用户已取消支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatUnPayEvent event) {
        Intent intent = new Intent(getContext(), PayResultActivity.class);
        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
        intent.putExtra(AppConstant.ORDERID, orderId);
        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
        startActivity(intent);

    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
                //设置支付调用
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付结果
     */
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    Log.e("TGA", result.get("resultStatus") + "");
                    if ("9000".equals(result.get("resultStatus"))) {
                        //okpay
                        //支付成功
                        Intent intent = new Intent(getContext(), PayResultActivity.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");

                        intent.putExtra(AppConstant.ORDERID, orderId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if ("6001".equals(result.get("resultStatus"))) {
                        //用户取消支付
                        AppHelper.showMsg(getContext(), "您已取消支付");
                    } else if ("6002".equals(result.get("resultStatus"))) {
                        //网络连接错误
                        AppHelper.showMsg(getContext(), "网络连接错误");
                    } else {
                        //okpay
                        //支付失败
                        Intent intent = new Intent(getContext(), PayResultActivity.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERID, orderId);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");


                        startActivity(intent);
                        getActivity().finish();
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


    };

    /**
     * 获取用户支付密码的状态
     */
    private String mUserCell;
    private void accountCenter() {
        AccountCenterAPI.requestAccountCenter(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccountCenterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AccountCenterModel accountCenterModel) {

                        if (accountCenterModel.success) {
                            mUserCell = accountCenterModel.data.phone;
                            if (accountCenterModel.data.hasSetPayPwd) {
                                showInputPwdDialog();
                            } else {
                                showGoSetDialog();
                            }
                        } else {
                            AppHelper.showMsg(getContext(), accountCenterModel.message);

                        }
                    }
                });
    }

    /**
     * 显示设置支付密码弹窗
     */
    private Handler handler = new Handler();
    private void showGoSetDialog() {
        mDialog = new AlertDialog.Builder(getContext()).create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_not_set_paypwd);
        mDialog.getWindow().findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
//                mLavLoading.setVisibility(View.GONE);
                //   mIvError.setVisibility(View.VISIBLE);
                //  mTvState.setText("取消支付");
            }
        });
        mDialog.getWindow().findViewById(R.id.tv_dialog_goset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoHelper.saveDeliverType(getContext(),1+"");
                UserInfoHelper.saveForgetPas(getContext(), "wwwe");
                checkFirstChange();
//                startActivity(EditPasswordInputCodeActivity.getIntent(getContext(), EditPasswordInputCodeActivity.class, "0", mUserCell, "pay","forgetPsw",1, Double.parseDouble(payAmount)));

                mDialog.dismiss();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //     mIvError.setVisibility(View.VISIBLE);
                        //    mTvState.setText("取消支付");
                    }
                }, 1000);
            }
        });
    }

    /**
     * 显示输入支付密码弹窗
     */
    private AlertDialog mDialog;
    private void showInputPwdDialog() {
        mDialog = new AlertDialog.Builder(getContext()).create();
        mDialog.setView(new EditText(getContext()));
        mDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_input_pwd);
        final EditText mEtPwd = mDialog.getWindow().findViewById(R.id.et_dialog_paypwd);
        mDialog.getWindow().findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), PayActivity.class);
//                intent.putExtra("phone",mUserCell);
//                startActivity(intent);
                UserInfoHelper.saveForgetPas(getContext(), "wwwe");
                UserInfoHelper.saveDeliverType(getContext(),1+"");
//
                checkFirstChange();

            }
        });
        mDialog.getWindow().findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().findViewById(R.id.tv_dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtPwd.getText().toString())) {
                    AppHelper.showMsg(getContext(), "请输入交易密码");
                } else {
                    mDialog.dismiss();
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    lav_activity_loading.show();
                    checkPayPwd(outTradeNo, mEtPwd.getText().toString());

                }
            }
        });
    }

    private void checkFirstChange() {
        LoginAPI.checkFirst(getActivity(),mUserCell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        //没余额
                        if(baseModel.code==-1) {
                            Intent intent = new Intent(getActivity(),PayActivity.class);
                            intent.putExtra("phone",mUserCell);
                            startActivity(intent);
                            mDialog.dismiss();
                        }else if(baseModel.code ==1){
                            Intent intent = new Intent(getActivity(),HisActivity.class);
                            intent.putExtra("phone",mUserCell);
                            startActivity(intent);
                            mDialog.dismiss();
                        }else {
                            ToastUtil.showSuccessMsg(getActivity(),baseModel.message);
                            mDialog.dismiss();
                        }

                    }

                });
    }

    /**
     * 校验支付密码
     */
    private void checkPayPwd(final String outTradeNo, String passWord) {
        CheckPayPwdAPI.requestData(getContext(), outTradeNo, passWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckPayPwdModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CheckPayPwdModel checkPayPwdModel) {
                        if (checkPayPwdModel.code==1) {
                            lav_activity_loading.hide();
                            lav_activity_loading.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    getPayResult(outTradeNo);
                                }
                            }, 500);
                        } else {
                            lav_activity_loading.hide();
                            lav_activity_loading.setVisibility(View.GONE);
                            ToastUtil.showSuccessMsg(getContext(),checkPayPwdModel.message);
                        }
                    }
                });
    }


    private void getPayResult(String outTradeNo) {
        GetPayResultAPI.requestData(getContext(), outTradeNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetPayResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.setVisibility(View.GONE);
                        lav_activity_loading.hide();
                    }

                    @Override
                    public void onNext(GetPayResultModel getPayResultModel) {
                        if (getPayResultModel.isSuccess()) {
                            if(getPayResultModel.getData()!=null) {
                                Intent intent = new Intent(getContext(), PayResultActivity.class);
                                intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                                intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                                intent.putExtra(AppConstant.ORDERID, orderId);
                                intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
                                startActivity(intent);
                                lav_activity_loading.setVisibility(View.GONE);
                                lav_activity_loading.hide();
                                getActivity().finish();
                            }

                        } else {
                            AppHelper.showMsg(getContext(), getPayResultModel.getMessage());
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();

                        }
                    }
                });
    }

    /**
     * 提交订单
     */
    List<PayListModel.DataBean> data;
    private void orderPay() {
        OrderPayAPI.requestsData(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        rePayWay.setEnabled(true);
                    }

                    @Override
                    public void onNext(PayListModel payListModel) {
                        if (payListModel.success) {
                            rePayWay.setEnabled(true);
                            data = payListModel.getData();
                            list.clear();
                            list.addAll(data);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            payListAdapter = new PayListAdapter(R.layout.item_pay_list,list);
                            recyclerView.setAdapter(payListAdapter);


                            if(payListModel.getData().get(0).getFlag().equals("0")) {
                                tv_balance.setText(payListModel.getData().get(0).getChannelName()+"("+UserInfoHelper.getUserWalletAccount(getActivity())+")");
                                payChannel = 1;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("1")){
                                tv_balance.setText(payListModel.getData().get(0).getChannelName());
                                payChannel = 2;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("2")){
                                tv_balance.setText(payListModel.getData().get(0).getChannelName());
                                payChannel = 3;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("3")){
                                tv_balance.setText(payListModel.getData().get(0).getChannelName());
                                payChannel = 16;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }

                            payListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    selectionPosition = position;
                                    payListAdapter.selectionPosition(position);
                                    payListAdapter.notifyDataSetChanged();

                                    if(payListModel.getData().get(position).getFlag().equals("0")) {
                                        payChannel = 1;
                                        jumpWx = payListModel.getData().get(position).getJumpWx();
                                    }else if(payListModel.getData().get(position).getFlag().equals("1")){
                                        payChannel = 2;
                                        jumpWx = payListModel.getData().get(position).getJumpWx();
                                    }else if(payListModel.getData().get(position).getFlag().equals("2")){
                                        payChannel = 3;
                                        jumpWx = payListModel.getData().get(position).getJumpWx();
                                    }else if(payListModel.getData().get(position).getFlag().equals("3")){
                                        payChannel = 16;
                                        jumpWx = payListModel.getData().get(position).getJumpWx();
                                    }
                                    if(payListModel.getData().get(position).getFlag().equals("0")) {
                                        tv_balance.setText("余额"+"("+UserInfoHelper.getUserWalletAccount(getActivity())+")");
                                    }else {
                                        tv_balance.setText(data.get(position).getChannelName());
                                    }
                                    rePayDetail.startAnimation(slide_left_to_right);
                                    rePayDetail.setVisibility(View.VISIBLE);
                                    LinPayWay.startAnimation(slide_left_to_right);
                                    LinPayWay.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            rePayWay.setEnabled(true);
                            AppHelper.showMsg(getContext(), payListModel.message);
                        }
                    }
                });
    }
}
