package com.puyue.www.qiaoge.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.puyue.www.qiaoge.model.mine.order.VipPayResultModel;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoPayResultActivity extends BaseActivity {
    private ImageView mIvBack;
    private ImageView mIvSuccess;
    private ImageView mIvError;
    private TextView mTvOrderDetail;

    private int payChannal;
    private String outTradeNo;
    private String mUserCell;
    private String orderId;
    private AlertDialog mDialog;
    private Handler handler = new Handler();
    private  TextView textViewSuccess; //支付成功文案
    private  ImageView imageViewRecommend;
    private String imageUrl;
    private TextView otherMessage;


    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vippayresult);
    }

    @Override
    public void findViewById() {
        mIvBack = FVHelper.fv(this, R.id.iv_activity_back);
        mIvSuccess = FVHelper.fv(this, R.id.iv_activity_order_success);
        mIvError = FVHelper.fv(this, R.id.iv_activity_order_error);
        mTvOrderDetail = FVHelper.fv(this, R.id.tv_activity_order_look);
        textViewSuccess=FVHelper.fv(this, R.id.textViewSuccess);
        imageViewRecommend=FVHelper.fv(this,R.id.imageViewRecommend);
        otherMessage =FVHelper.fv(this,R.id.otherMessage);
    }

    Timer timer;
    LoadingDailog dialog;
    @Override
    public void setViewData() {
        payChannal = getIntent().getIntExtra("payChannel",0);
        outTradeNo = getIntent().getStringExtra("outTradeNo");

        timer = new Timer();
        timer.schedule(task,0,2000);

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
            getPayResult(outTradeNo);
        }
    };


    @Override
    public void setClickEvent() {
        imageViewRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", imageUrl);
                intent.putExtra("name","");
                startActivity(intent);
            }
        });
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
                .subscribe(new Subscriber<VipPayResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipPayResultModel vipPayResultModel) {
                        if (vipPayResultModel.getCode()==1) {
                            //支付成功
                            if (vipPayResultModel.getData()!=null) {
                                mIvSuccess.setVisibility(View.VISIBLE);
                                mIvError.setVisibility(View.GONE);
                                //支付成功
                                if(vipPayResultModel.getData().getPayMsg().equals("支付成功")) {
                                    textViewSuccess.setText(vipPayResultModel.getData().getPayMsg());
                                    dialog.dismiss();
                                }else if(vipPayResultModel.getData().getPayMsg().equals("支付失败")) {
                                    textViewSuccess.setText(vipPayResultModel.getData().getPayMsg());
                                    dialog.dismiss();
                                }else {
                                    //支付中
                                    dialog.show();
                                    textViewSuccess.setText(vipPayResultModel.getData().getPayMsg());
                                }

                                if(vipPayResultModel.getData().getPayMsg().equals("支付成功")) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(timer!=null) {
                                                timer.cancel();
                                                timer = null;
                                            }
                                        }
                                    });

                                }else if(vipPayResultModel.getData().getPayMsg().equals("支付失败")) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(timer!=null) {
                                                timer.cancel();
                                                timer = null;
                                            }
                                        }
                                    });
                                }else {
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(timer!=null) {
                                                timer.cancel();
                                                timer = null;
                                            }
                                            dialog.dismiss();
                                        }
                                    },20000);
                                }

                                if (!TextUtils.isEmpty(vipPayResultModel.getData().getErrorMsg())) {
                                    otherMessage.setText(vipPayResultModel.getData().getErrorMsg());
                                    otherMessage.setVisibility(View.GONE);
                                } else {
                                    otherMessage.setVisibility(View.GONE);
                                }

                                Glide.with(mActivity).load(vipPayResultModel.getData().getVo().getBannerUrl()).into(imageViewRecommend);
                                imageUrl = vipPayResultModel.getData().getVo().getBannerDetailUrl();
                                textViewSuccess.setVisibility(View.VISIBLE);
                            } else {
                                mIvSuccess.setVisibility(View.GONE);
                                mIvError.setVisibility(View.VISIBLE);
                                textViewSuccess.setVisibility(View.GONE);
                            }
                        } else {
                            AppHelper.showMsg(mContext, vipPayResultModel.getData().getPayMsg());
                            mIvError.setVisibility(View.VISIBLE);
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
    }
}
