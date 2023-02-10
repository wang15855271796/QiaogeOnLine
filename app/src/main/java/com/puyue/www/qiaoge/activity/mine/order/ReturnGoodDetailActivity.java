package com.puyue.www.qiaoge.activity.mine.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.mine.ReturnDetailOrderAdapter;
import com.puyue.www.qiaoge.api.mine.order.CancelReturnAPI;
import com.puyue.www.qiaoge.api.mine.order.NewReturnOderAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.mine.order.NewReturnOrderModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王文博} on 2019/8/27
 * 只显示退货商品详情
 */
public class ReturnGoodDetailActivity extends BaseActivity {
    private TextView tv_return_success;//退货成功
    private TextView tvOrderContent1;//状态内容
    private TextView tvOrderContent2;//状态内容
    private TextView tvOrderContent3;//状态内容
    private TextView tv_cancel_return;//撤销退货
    private String returnProductMainId;
    private TextView tv_return_amount;//退货金额
    private TextView tv_return_way;//退货去向
    private TextView tv_return_reason;//退货原因
    private TextView tv_return_order;//订单编号
    private TextView tv_return_name;//订单申请人
    private ImageView imageViewBreak;
    private RecyclerView recycler_good;
    private List<NewReturnOrderModel.DataBean.ProductsBean> mProductList = new ArrayList<>();
    private ReturnDetailOrderAdapter returnDetailOrderAdapter;
    //0,审核 1，成功, 2，失败 4，已撤销
    private int orderDeliveryType;
    private RelativeLayout rl_order;
    private String orderId;
    private RelativeLayout rl_return_content;
    private TextView tv_return_content;//退款说明

    private TextView tv_amount_content;
    private TextView tv_amount_spec;
    private String returnContent;
    RecyclerView rv_full;
    TextView tv_tip;
    LinearLayout ll_tip;
    TextView tv_apply_time;
    TextView tv_check_time;
    RelativeLayout rl_return_time;
    RelativeLayout rl_check_time;
    RelativeLayout rl_apply_time;
    RelativeLayout rl_return_success1;
    LinearLayout ll_progress2;
    LinearLayout ll_progress3;
    RelativeLayout rl_return_success2;
    RelativeLayout rl_return_success3;
    RelativeLayout rl_check_time2;
    TextView tv_check_time2;
    RelativeLayout rl_apply_time2;
    TextView tv_apply_time2;
    RelativeLayout rl_check_time3;
    TextView tv_check_time3;
    RelativeLayout rl_apply_time3;
    TextView tv_apply_time3;
    RelativeLayout rl_apply_time4;
    TextView tv_apply_time4;
    LinearLayout ll_progress;
    LinearLayout ll_progress4;
    RelativeLayout rl_return_success4;
    TextView tv_cancel_time;
    TextView tv_apply_time5;
    TextView tv_return_time;
    LinearLayout ll_root;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_return_good_detail);
    }

    @Override
    public void findViewById() {
        ll_root = findViewById(R.id.ll_root);
        ll_tip = findViewById(R.id.ll_tip);
        tv_tip = findViewById(R.id.tv_tip);
        rv_full = findViewById(R.id.rv_full);
        tv_apply_time5 = findViewById(R.id.tv_apply_time5);
        tv_cancel_time = findViewById(R.id.tv_cancel_time);
        rl_return_success4 = findViewById(R.id.rl_return_success4);
        ll_progress4 = findViewById(R.id.ll_progress4);
        tv_apply_time4 = findViewById(R.id.tv_apply_time4);
        rl_apply_time4 = findViewById(R.id.rl_apply_time4);
        tv_apply_time2 = findViewById(R.id.tv_apply_time2);
        rl_apply_time2 = findViewById(R.id.rl_apply_time2);
        tv_check_time2 = findViewById(R.id.tv_check_time2);
        rl_check_time2 = findViewById(R.id.rl_check_time2);
        tv_apply_time3 = findViewById(R.id.tv_apply_time3);
        rl_apply_time3 = findViewById(R.id.rl_apply_time3);
        tv_check_time3 = findViewById(R.id.tv_check_time3);
        rl_check_time3 = findViewById(R.id.rl_check_time3);
        rl_return_success2 = findViewById(R.id.rl_return_success2);
        rl_return_success3 = findViewById(R.id.rl_return_success3);
        ll_progress = findViewById(R.id.ll_progress);
        ll_progress2 = findViewById(R.id.ll_progress2);
        ll_progress3 = findViewById(R.id.ll_progress3);
        rl_return_success1 = findViewById(R.id.rl_return_success1);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        tv_check_time = findViewById(R.id.tv_check_time);
        rl_return_time = findViewById(R.id.rl_return_time);
        rl_check_time = findViewById(R.id.rl_check_time);
        rl_apply_time = findViewById(R.id.rl_apply_time);
        tv_return_success = findViewById(R.id.tv_return_success);
        tvOrderContent1 = findViewById(R.id.tvOrderContent1);
        tvOrderContent2 = findViewById(R.id.tvOrderContent2);
        tvOrderContent3 = findViewById(R.id.tvOrderContent3);
        tv_cancel_return = findViewById(R.id.tv_cancel_return);
        tv_return_amount = findViewById(R.id.tv_return_amount);
        tv_return_way = findViewById(R.id.tv_return_way);
        tv_return_reason = findViewById(R.id.tv_return_reason);
        tv_return_order = findViewById(R.id.tv_return_order);
        tv_return_name = findViewById(R.id.tv_return_name);
        tv_return_time = findViewById(R.id.tv_return_time);
        imageViewBreak = findViewById(R.id.imageViewBreak);
        recycler_good = findViewById(R.id.recycler_good);
        rl_order = findViewById(R.id.rl_order);
        rl_return_content = findViewById(R.id.rl_return_content);
        tv_return_content = findViewById(R.id.tv_return_content);
        tv_amount_content = findViewById(R.id.tv_amount_content);
        tv_amount_spec = findViewById(R.id.tv_amount_spec);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new BackEvent());
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void setViewData() {
        returnProductMainId = getIntent().getStringExtra(AppConstant.RETURNPRODUCTMAINID);
        orderDeliveryType = getIntent().getIntExtra("orderType", 0);
        mProductList.clear();
        getData();
    }

    @Override
    public void setClickEvent() {
        imageViewBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = MyOrdersActivity.getIntent(mContext, MyOrdersActivity.class, AppConstant.DELIVERY);
//                intent.putExtra("orderDeliveryType",orderDeliveryType);
//                startActivity(intent);
                finish();
            }
        });
        tv_cancel_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //撤销申请
                cancelReturnOrder();
            }
        });

        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0配送 1自提
                if (orderDeliveryType == 0) {
                    Intent intent = new Intent(mContext, NewOrderDetailActivity.class);
                    intent.putExtra(AppConstant.ORDERID, orderId);
                    intent.putExtra(AppConstant.ORDERSTATE, "11");
                    intent.putExtra("account","0");
                   // intent.putExtra(AppConstant.RETURNPRODUCTMAINID, returnProductMainId);
                    intent.putExtra("goAccount", "goAccount");
                    startActivity(intent);
                } else if (orderDeliveryType == 1) {
                    Intent intent = new Intent(mContext, SelfSufficiencyOrderDetailActivity.class);
                    intent.putExtra(AppConstant.ORDERID, orderId);
                    intent.putExtra("account","0");
                    intent.putExtra(AppConstant.ORDERSTATE, "11");
                //    intent.putExtra(AppConstant.RETURNPRODUCTMAINID, returnProductMainId);
                    intent.putExtra("goAccount", "goAccount");
                    startActivity(intent);

                }
            }
        });

        rl_return_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnContent != null && StringHelper.notEmptyAndNull(returnContent)) {
                    AlertDialog dialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
                    dialog.show();
                    dialog.setContentView(R.layout.return_order_content);
                    dialog.setCanceledOnTouchOutside(true);

                    TextView tv_ok = dialog.getWindow().findViewById(R.id.tv_ok);
                    TextView tv_order_content = dialog.getWindow().findViewById(R.id.tv_order_content);

                    tv_order_content.setText(returnContent);

                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }


            }
        });
    }


    private void cancelReturnOrder() {
        CancelReturnAPI.requestCancelOrder(mContext, returnProductMainId)
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
                        if (baseModel.success) {
//                            tvOrderContent.setVisibility(View.GONE);
                            tv_cancel_return.setVisibility(View.GONE);
                            tv_return_success.setVisibility(View.VISIBLE);
                            tv_return_success.setText("申请已撤销");
                            AppHelper.showMsg(mContext, "撤销成功");

                            getData();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });


    }
    private int returnState;
    private void getData() {
        NewReturnOderAPI.requestNewReturnDetail(mContext, returnProductMainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewReturnOrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewReturnOrderModel newReturnOrderModel) {
                        if (newReturnOrderModel.isSuccess()) {
                            orderId = newReturnOrderModel.getData().getOrderId();
                            returnState=newReturnOrderModel.getData().getReturnState();
                            NewReturnOrderModel.DataBean data = newReturnOrderModel.getData();
                            if (newReturnOrderModel.getData().getReturnState() == 1) {
                                tv_amount_spec.setVisibility(View.GONE);
                                rl_return_content.setVisibility(View.VISIBLE);
                                returnContent = newReturnOrderModel.getData().getReturnMemo();
                                if (newReturnOrderModel.getData().getReturnMemo().equals("无")) {
                                    tv_return_content.setText(newReturnOrderModel.getData().getReturnMemo());
                                    rl_return_content.setEnabled(false);
                                } else {
                                    rl_return_content.setEnabled(true);
                                    tv_return_content.setText("");
                                }

                            } else {
                                rl_return_content.setVisibility(View.GONE);
                                tv_amount_spec.setVisibility(View.VISIBLE);
                            }

                            if(newReturnOrderModel.getData().getTips()!=null&&!newReturnOrderModel.getData().getTips().equals("")) {
                                ll_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText(newReturnOrderModel.getData().getTips());
                            }else {
                                ll_tip.setVisibility(View.GONE);
                            }
                            if (newReturnOrderModel.getData().getReturnState() == 0) {
                                //待审核
                                tvOrderContent3.setVisibility(View.VISIBLE);
                                tv_cancel_return.setVisibility(View.VISIBLE);
                                tvOrderContent1.setVisibility(View.GONE);
                                tvOrderContent3.setVisibility(View.VISIBLE);
                                tvOrderContent2.setVisibility(View.GONE);
                                if(newReturnOrderModel.getData().titleText.equals("") || TextUtils.isEmpty(newReturnOrderModel.getData().titleText)) {
                                    tvOrderContent3.setVisibility(View.GONE);
                                }else {
                                    tvOrderContent3.setVisibility(View.VISIBLE);
                                    tvOrderContent3.setText(newReturnOrderModel.getData().titleText);
                                }
                                tv_apply_time4.setText(data.getApplyDate());
                                rl_return_success1.setVisibility(View.GONE);
                                rl_return_success2.setVisibility(View.GONE);
                                rl_return_success3.setVisibility(View.GONE);
                                rl_apply_time4.setVisibility(View.VISIBLE);

                                ll_progress.setVisibility(View.GONE);
                                ll_progress2.setVisibility(View.GONE);
                                ll_progress3.setVisibility(View.GONE);
                                ll_root.setBackgroundResource(R.mipmap.bg_return_check);
                            } else if (newReturnOrderModel.getData().getReturnState() == 1) {
                                //成功
                                tv_return_success.setVisibility(View.VISIBLE);
                                tvOrderContent1.setVisibility(View.VISIBLE);
                                tvOrderContent3.setVisibility(View.GONE);
                                tvOrderContent2.setVisibility(View.GONE);
                                if(newReturnOrderModel.getData().titleText.equals("") || TextUtils.isEmpty(newReturnOrderModel.getData().titleText)) {
                                    tvOrderContent1.setVisibility(View.GONE);
                                }else {
                                    tvOrderContent1.setVisibility(View.VISIBLE);
                                    tvOrderContent1.setText(newReturnOrderModel.getData().titleText);
                                }
                                tv_cancel_return.setVisibility(View.GONE);

                                if(data.isBankReturnFlag()) {
                                    tv_return_time.setText(data.getBankReturnDate());
                                    tv_apply_time.setText(data.getApplyDate());
                                    tv_check_time.setText(data.getCheckDate());
                                    rl_return_success1.setVisibility(View.VISIBLE);
                                    rl_return_success2.setVisibility(View.GONE);
                                    rl_return_success3.setVisibility(View.GONE);
                                    rl_apply_time4.setVisibility(View.GONE);

                                    ll_progress.setVisibility(View.VISIBLE);
                                    ll_progress2.setVisibility(View.GONE);
                                    ll_progress3.setVisibility(View.GONE);

                                }else {
                                    rl_return_success1.setVisibility(View.GONE);
                                    rl_return_success2.setVisibility(View.VISIBLE);
                                    rl_return_success3.setVisibility(View.GONE);
                                    rl_apply_time4.setVisibility(View.GONE);

                                    ll_progress.setVisibility(View.GONE);
                                    ll_progress2.setVisibility(View.VISIBLE);
                                    ll_progress3.setVisibility(View.GONE);

                                    tv_apply_time2.setText(data.getApplyDate());
                                    tv_check_time2.setText(data.getCheckDate());
                                }
                                ll_root.setBackgroundResource(R.mipmap.bg_return_success);
                            } else if (newReturnOrderModel.getData().getReturnState() == 2) {
                                //失败
                                tv_return_success.setVisibility(View.VISIBLE);
                                tvOrderContent2.setVisibility(View.VISIBLE);
                                tv_cancel_return.setVisibility(View.GONE);
                                tvOrderContent1.setVisibility(View.GONE);
                                tvOrderContent3.setVisibility(View.GONE);
                                tvOrderContent2.setVisibility(View.VISIBLE);
                                if(newReturnOrderModel.getData().titleText.equals("") || TextUtils.isEmpty(newReturnOrderModel.getData().titleText)) {
                                    tvOrderContent2.setVisibility(View.GONE);
                                }else {
                                    tvOrderContent2.setVisibility(View.VISIBLE);
                                    tvOrderContent2.setText(newReturnOrderModel.getData().titleText);
                                }

                                tv_check_time3.setText(data.getCheckDate());
                                tv_apply_time3.setText(data.getApplyDate());
                                rl_return_success1.setVisibility(View.GONE);
                                rl_return_success2.setVisibility(View.GONE);
                                rl_return_success3.setVisibility(View.VISIBLE);
                                rl_apply_time4.setVisibility(View.GONE);

                                ll_progress.setVisibility(View.GONE);
                                ll_progress2.setVisibility(View.GONE);
                                ll_progress3.setVisibility(View.VISIBLE);
                                ll_root.setBackgroundResource(R.mipmap.bg_return_fail);
                            } else if (newReturnOrderModel.getData().getReturnState() == 4) {
                                //已撤销
                                ll_progress4.setVisibility(View.VISIBLE);
                                rl_return_success4.setVisibility(View.VISIBLE);

                                ll_progress.setVisibility(View.GONE);
                                ll_progress2.setVisibility(View.GONE);
                                ll_progress3.setVisibility(View.GONE);

                                tvOrderContent1.setVisibility(View.GONE);
                                tvOrderContent2.setVisibility(View.GONE);

                                rl_return_success1.setVisibility(View.GONE);
                                rl_return_success2.setVisibility(View.GONE);
                                rl_return_success3.setVisibility(View.GONE);

                                rl_apply_time4.setVisibility(View.GONE);

                                if(data.titleText.equals("") || TextUtils.isEmpty(data.titleText)) {
                                    tvOrderContent3.setVisibility(View.GONE);
                                }else {
                                    tvOrderContent3.setVisibility(View.VISIBLE);
                                    tvOrderContent3.setText(data.titleText);
                                }
                                tv_cancel_time.setText(data.getCancelDate());
                                tv_apply_time5.setText(data.getApplyDate());
                                tv_cancel_return.setVisibility(View.GONE);
                                ll_root.setBackgroundResource(R.mipmap.bg_return_cancel);
                            }


                            tv_return_success.setText(newReturnOrderModel.getData().getReturnStateStr());
                            if (newReturnOrderModel.getData().getReturnState() == 1) {
                                tv_return_amount.setText("￥" + newReturnOrderModel.getData().getActualAmount());
                                tv_amount_content.setText("退款金额");
                            } else {
                                tv_return_amount.setText("￥" + newReturnOrderModel.getData().getReturnAmount());
                                tv_amount_content.setText("预计退款金额");
                            }

                            tv_return_way.setText(newReturnOrderModel.getData().getReturnChannel());
                            tv_return_reason.setText(newReturnOrderModel.getData().getReturnReason());
                            tv_return_order.setText(newReturnOrderModel.getData().getOrderId());
                            tv_return_name.setText(newReturnOrderModel.getData().getApplier());
                            if (newReturnOrderModel.getData().getProducts().size() > 0) {
                                mProductList.clear();
                                mProductList.addAll(newReturnOrderModel.getData().getProducts());
                                recycler_good.setLayoutManager(new LinearLayoutManager(mContext));
                                returnDetailOrderAdapter = new ReturnDetailOrderAdapter(R.layout.return_order_detail_item, mProductList,returnState,orderId);
                                recycler_good.setAdapter(returnDetailOrderAdapter);
                            }

                        } else {
                            AppHelper.showMsg(mContext, newReturnOrderModel.getMessage());
                        }
                    }
                });

    }


}
