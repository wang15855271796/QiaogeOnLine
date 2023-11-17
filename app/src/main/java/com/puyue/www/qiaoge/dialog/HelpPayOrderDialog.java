package com.puyue.www.qiaoge.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HelpPayResultActivity;
import com.puyue.www.qiaoge.activity.mine.account.HisActivity;
import com.puyue.www.qiaoge.activity.mine.account.PayActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.PayListAdapter;
import com.puyue.www.qiaoge.api.cart.CheckPayPwdAPI;
import com.puyue.www.qiaoge.api.cart.GetPayResultAPI;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.mine.AccountCenterAPI;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
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
import com.puyue.www.qiaoge.utils.Utils;
import com.rrtx.tzpaylib.CashierManager;
import com.rrtx.tzpaylib.PaymentCallback;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HelpPayOrderDialog extends Dialog {

    Activity context;
    View view;
    Unbinder binder;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    int selectionPosition;
    YueDialog yueDialog;
    String orderId;
    String payAmount;
    String remark;
    String orderDeliveryType;
    public HelpPayOrderDialog(Activity context, String orderId, String payAmount, String remark, String orderDeliveryType) {
        super(context, R.style.dialog);
        this.context = context;
        this.orderId = orderId;
        this.payAmount = payAmount;
        this.remark = remark;
        this.orderDeliveryType = orderDeliveryType;
        init();

    }

//    @Override
//    public void show() {
//        super.show();
//
//        if(jumpWx==1) {
//            Intent intent = new Intent(context,NewOrderDetailActivity.class);
//            intent.putExtra(AppConstant.ORDERID,orderId);
//            context.startActivity(intent);
//            context.finish();
//        }
//
//        if(outTradeNo!=null&&jumpWx==0) {
//            getPayResult(outTradeNo);
//        }
//    }

    private void init() {
        view = View.inflate(context, R.layout.dialog_order_pay, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        getPayList();
        tv_tips.setText("温馨提醒：帮我付订单请谨慎操作！");
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(selectionPosition).getFlag().equals("0")) {
                    if (data.get(selectionPosition).getWalletEnabled() == 0) {
                        yueDialog = new YueDialog(getContext(), data.get(selectionPosition).getWalletAmt()) {
                            @Override
                            public void close() {
                                dismiss();
                            }
                        };
                        yueDialog.show();
                    }else {
                        lav_activity_loading.setVisibility(View.VISIBLE);
                        lav_activity_loading.show();
                        //调支付接口
                        orderPays(orderId, payChannel, Double.parseDouble(payAmount), remark);
                    }
                }else {
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    lav_activity_loading.show();
                    //调支付接口
                    orderPays(orderId, payChannel, Double.parseDouble(payAmount), remark);
                }

            }
        });

    }
    PayErrorDialog payErrorDialog;
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
                        if (orderPayModel.code==1) {
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
                                CashierManager.getInstance().init(context);
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
                                if(DateUtils.isZhiFuBao(context)) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","2");
                                    aliPay(orderPayModel.data.payToken);
                                }

                            } else if (payChannel == 3&&jumpWx==1) {
                                //微信支付(小程序)1
                                if(DateUtils.isWeixin(context)) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                    Intent lan = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                    Intent t2 = new Intent(Intent.ACTION_MAIN);
                                    t2.addCategory(Intent.CATEGORY_LAUNCHER);
                                    t2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    t2.setComponent(lan.getComponent());
                                    context.startActivity(t2);
                                    weChatPay(orderPayModel.data.payToken);
                                }

                            }else if(payChannel == 3&&jumpWx==0) {
                                //微信支付
                                if(DateUtils.isWeixin(context)) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                    weChatPay2(orderPayModel.data.payToken);
                                }
                            }else if(orderPayModel.data.payType==14&&payChannel == 2) {
                                //银联
                                if(DateUtils.isZhiFuBao(context)) {
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","4");
                                    payAliPay(orderPayModel.data.payToken);
                                }
                            } else if(orderPayModel.data.payType==22) {
                                //支付宝跳转小程序
                                SharedPreferencesUtil.saveString(getContext(),"payKey","5");
                                zhiFuBaoPay(orderPayModel.data.payToken);

                            }

                            if(payErrorDialog!=null) {
                                payErrorDialog.dismiss();
                            }

                        } else if(orderPayModel.code ==100006) {
                            //ok
                            payErrorDialog = new PayErrorDialog(getContext(), orderPayModel.message) {
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
                            ToastUtil.showSuccessMsg(getContext(),orderPayModel.message);
                        }
                        lav_activity_loading.setVisibility(View.GONE);
                        lav_activity_loading.hide();
                    }
                });
    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
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
                        Intent intent = new Intent(getContext(), HelpPayResultActivity.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");
                        intent.putExtra(AppConstant.ORDERID, orderId);
                        context.startActivity(intent);
                        context.finish();
                    } else if ("6001".equals(result.get("resultStatus"))) {
                        //用户取消支付
                        AppHelper.showMsg(getContext(), "您已取消支付");
                    } else if ("6002".equals(result.get("resultStatus"))) {
                        //网络连接错误
                        AppHelper.showMsg(getContext(), "网络连接错误");
                    } else {
                        //okpay
                        //支付失败
                        Intent intent = new Intent(getContext(), HelpPayResultActivity.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERID, orderId);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");
                        context.startActivity(intent);
                        context.finish();
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
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
    private void showGoSetDialog() {
        mDialog = new AlertDialog.Builder(getContext()).create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_not_set_paypwd);
        mDialog.getWindow().findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().findViewById(R.id.tv_dialog_goset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoHelper.saveDeliverType(getContext(),1+"");
                UserInfoHelper.saveForgetPas(getContext(), "wwwe");
                checkFirstChange();
                mDialog.dismiss();
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
                UserInfoHelper.saveForgetPas(getContext(), "wwwe");
                UserInfoHelper.saveDeliverType(getContext(),1+"");
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
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    getPayResult(outTradeNo);
                                }
                            }, 500);
                        } else {
                            ToastUtil.showSuccessMsg(getContext(),checkPayPwdModel.message);
                        }

                        lav_activity_loading.hide();
                        lav_activity_loading.setVisibility(View.GONE);
                    }
                });
    }

    private void checkFirstChange() {
        LoginAPI.checkFirst(context,mUserCell)
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
                            Intent intent = new Intent(context, PayActivity.class);
                            intent.putExtra("phone",mUserCell);
                            context.startActivity(intent);
                            mDialog.dismiss();
                        }else if(baseModel.code ==1){
                            Intent intent = new Intent(context, HisActivity.class);
                            intent.putExtra("phone",mUserCell);
                            context.startActivity(intent);
                            mDialog.dismiss();
                        }else {
                            ToastUtil.showSuccessMsg(context,baseModel.message);
                            mDialog.dismiss();
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
                        if (getPayResultModel.getCode()==1) {
                            if(getPayResultModel.getData()!=null) {
                                Intent intent = new Intent(getContext(), HelpPayResultActivity.class);
                                intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                                intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                                intent.putExtra(AppConstant.ORDERID, orderId);
                                intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
                                context.startActivity(intent);
                                context.finish();
                            }

                        } else {
                            ToastUtil.showSuccessMsg(getContext(), getPayResultModel.getMessage());

                        }

                        lav_activity_loading.setVisibility(View.GONE);
                        lav_activity_loading.hide();
                    }
                });
    }


    List<PayListModel.DataBean> data;
    List<PayListModel.DataBean> list = new ArrayList<>();
    PayListAdapter payListAdapter;
    byte payChannel = -1;
    int jumpWx;
    private void getPayList() {
        OrderPayAPI.requestsData(getContext(),orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PayListModel payListModel) {
                        if (payListModel.success) {
                            data = payListModel.getData();
                            list.clear();
                            list.addAll(data);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            payListAdapter = new PayListAdapter(R.layout.item_pay_list,list,payAmount);
                            recyclerView.setAdapter(payListAdapter);

                            if(payListModel.getData().get(0).getFlag().equals("0")) {
                                payChannel = 1;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("1")){

                                payChannel = 2;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("2")){

                                payChannel = 3;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }else if(payListModel.getData().get(0).getFlag().equals("3")){
                                payChannel = 16;
                                jumpWx = payListModel.getData().get(0).getJumpWx();
                            }



                            payListAdapter.setOnCheckItemListener(new PayListAdapter.OnCheckItemListener() {
                                @Override
                                public void onItemClick(int position) {
                                    payListAdapter.selectionPosition(position);
                                    payListAdapter.notifyDataSetChanged();
                                    selectionPosition = position;
                                    if(data.get(position).getFlag().equals("0")) {
                                        payChannel = 1;
                                        jumpWx = data.get(position).getJumpWx();
                                    }else if(data.get(position).getFlag().equals("1")){
                                        payChannel = 2;
                                        jumpWx = data.get(position).getJumpWx();
                                    }else if(data.get(position).getFlag().equals("2")){
                                        payChannel = 3;
                                        jumpWx = data.get(position).getJumpWx();
                                    }else if(data.get(position).getFlag().equals("3")){
                                        payChannel = 16;
                                        jumpWx = data.get(position).getJumpWx();
                                    }
                                }
                            });

                        } else {
                            AppHelper.showMsg(getContext(), payListModel.message);
                        }
                    }
                });
    }
}
