package com.puyue.www.qiaoge.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.VipPayResultActivity;
import com.puyue.www.qiaoge.api.mine.order.VipPayResultAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.FVHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.VipPayInfoResultModel;
import com.puyue.www.qiaoge.model.mine.order.VipPayResultModel;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoPayResultActivity extends BaseActivity {
    private ImageView mIvBack;
    private TextView mTvOrderDetail;
    private int payChannal;
    private String outTradeNo;
    private Handler handler = new Handler();
    private  TextView textViewSuccess; //支付成功文案


    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay_info_result);
    }

    @Override
    public void findViewById() {
        mIvBack = FVHelper.fv(this, R.id.iv_activity_back);
        mTvOrderDetail = FVHelper.fv(this, R.id.tv_activity_order_look);
        textViewSuccess=FVHelper.fv(this, R.id.textViewSuccess);
    }

    Timer timer;
    LoadingDailog dialog;
    @Override
    public void setViewData() {
        payChannal = getIntent().getIntExtra("payChannel",0);
        outTradeNo = getIntent().getStringExtra("outTradeNo");

        timer = new Timer();
        timer.schedule(task,0,2000);
        getPayResult(outTradeNo);
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("获取支付结果中")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(payChannal == 14) {
                getPayResult(outTradeNo);
            }
        }
    };


    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mTvOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getPayResult(String outTradeNo) {
        VipPayResultAPI.getResult(mContext, outTradeNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipPayInfoResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipPayInfoResultModel vipPayResultModel) {
                        if (vipPayResultModel.getCode()==1) {
                            //支付成功
                            if (vipPayResultModel.getData()!=null) {

                                VipPayInfoResultModel.DataBean data = vipPayResultModel.getData();
                                //支付成功
                                if(data.getStatus().equals("TRADE_SUCCESS")) {
                                    textViewSuccess.setText(data.getMessage());
                                    dialog.dismiss();
                                }else if(data.getStatus().equals("FAILED")) {
                                    textViewSuccess.setText(data.getMessage());
                                    dialog.dismiss();
                                }else {
                                    //支付中
                                    dialog.show();
                                    textViewSuccess.setText(data.getMessage());
                                }

                                if(data.getStatus().equals("TRADE_SUCCESS")) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(timer!=null) {
                                                timer.cancel();
                                                timer = null;
                                            }
                                            dialog.dismiss();
                                        }
                                    });

                                }else if(data.getStatus().equals("FAILED")) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(timer!=null) {
                                                timer.cancel();
                                                timer = null;
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                                }else {
                                    Log.d("swefaswedas.....",payChannal+"--");
                                    if(payChannal == 14) {
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(timer!=null) {
                                                    timer.cancel();
                                                    timer = null;
                                                }
                                                dialog.dismiss();
                                            }
                                        },6000);
                                    }else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(timer!=null) {
                                                    timer.cancel();
                                                    timer = null;
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                    }

                                }
                            }
                        } else {
                            AppHelper.showMsg(mContext, vipPayResultModel.getMessage());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && !this.isFinishing()) {
            dialog.dismiss();
        }
        handler.removeCallbacksAndMessages(null);
        task.cancel();
    }
}
