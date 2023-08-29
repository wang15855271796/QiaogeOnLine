package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.HelpPayReturnAdapter;
import com.puyue.www.qiaoge.adapter.mine.ReturnDetailOrderAdapter;
import com.puyue.www.qiaoge.api.mine.order.NewReturnOderAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.order.NewReturnOrderModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HelpPayReturnActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_return_amount)
    TextView tv_return_amount;
    @BindView(R.id.tv_apply_person)
    TextView tv_apply_person;
    @BindView(R.id.tv_order_status)
    TextView tv_order_status;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_return_way)
    TextView tv_return_way;
    @BindView(R.id.tv_return_content)
    TextView tv_return_content;
    @BindView(R.id.tv_return_reason)
    TextView tv_return_reason;
    @BindView(R.id.tv_return_order)
    TextView tv_return_order;
    @BindView(R.id.tv_apply_name)
    TextView tv_apply_name;
    @BindView(R.id.rl_return_content)
    RelativeLayout rl_return_content;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.tv_amount_spec)
    TextView tv_amount_spec;
    @BindView(R.id.rl_return_success1)
    RelativeLayout rl_return_success1;
    @BindView(R.id.rl_return_success2)
    RelativeLayout rl_return_success2;
    @BindView(R.id.rl_return_success3)
    RelativeLayout rl_return_success3;
    @BindView(R.id.rl_return_success4)
    RelativeLayout rl_return_success4;
    @BindView(R.id.ll_progress)
    LinearLayout ll_progress;
    @BindView(R.id.ll_progress2)
    LinearLayout ll_progress2;
    @BindView(R.id.ll_progress3)
    LinearLayout ll_progress3;
    @BindView(R.id.ll_progress4)
    LinearLayout ll_progress4;
    @BindView(R.id.tv_return_time)
    TextView tv_return_time;
    @BindView(R.id.tv_check_time)
    TextView tv_check_time;
    @BindView(R.id.tv_apply_time)
    TextView tv_apply_time;
    @BindView(R.id.tv_check_time2)
    TextView tv_check_time2;
    @BindView(R.id.tv_apply_time2)
    TextView tv_apply_time2;
    @BindView(R.id.tv_check_time3)
    TextView tv_check_time3;
    @BindView(R.id.tv_cancel_time)
    TextView tv_cancel_time;
    @BindView(R.id.tv_apply_time5)
    TextView tv_apply_time5;
    @BindView(R.id.tv_apply_time4)
    TextView tv_apply_time4;
    @BindView(R.id.tv_apply_time3)
    TextView tv_apply_time3;
    @BindView(R.id.rl_apply_time4)
    RelativeLayout rl_apply_time4;
    @BindView(R.id.ll_tip)
    LinearLayout ll_tip;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    HelpPayReturnAdapter helpPayReturnAdapter;
    String returnProductMainId;
    int orderDeliveryType;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        returnProductMainId = getIntent().getStringExtra("returnProductMainId");
        orderDeliveryType = getIntent().getIntExtra("orderType", 0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay_return);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        helpPayReturnAdapter = new HelpPayReturnAdapter(R.layout.item_help_pay_return_goods,productList,dataBean.getOrderId());
        recyclerView.setAdapter(helpPayReturnAdapter);
        getOrderReturn();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        rl_order.setOnClickListener(this);
    }

    NewReturnOrderModel.DataBean dataBean;
    List<NewReturnOrderModel.DataBean.ProductsBean> productList = new ArrayList<>();
    private void getOrderReturn() {
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
                        if (newReturnOrderModel.getCode() == 1) {
                            if(newReturnOrderModel.getData()!=null) {
                                dataBean = newReturnOrderModel.getData();
                                productList.addAll(newReturnOrderModel.getData().getProducts());
                                helpPayReturnAdapter.notifyDataSetChanged();
                                setContent(newReturnOrderModel.getData());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, newReturnOrderModel.getMessage());
                        }
                    }
                });

    }

    private void setContent(NewReturnOrderModel.DataBean data) {
        tv_return_amount.setText(data.getReturnAmount());
        tv_apply_person.setText(data.getApplier());
        tv_order_status.setText(data.getReturnStateStr());
        tv_return_way.setText(data.getReturnChannel());
        tv_return_reason.setText(data.getReturnReason());
        tv_return_order.setText(data.getOrderId());
        tv_apply_name.setText(data.getApplier());

        if(data.getTips()!=null&&!data.getTips().equals("")) {
            ll_tip.setVisibility(View.VISIBLE);
            tv_tip.setText(data.getTips());
        }else {
            ll_tip.setVisibility(View.GONE);
        }

        if (data.getReturnState() == 1) {
            tv_amount_spec.setVisibility(View.GONE);
            rl_return_content.setVisibility(View.VISIBLE);
            if (data.getReturnMemo().equals("无")) {
                tv_return_content.setText(data.getReturnMemo());
                rl_return_content.setEnabled(false);
            } else {
                rl_return_content.setEnabled(true);
                tv_return_content.setText("");
            }

        } else {
            rl_return_content.setVisibility(View.GONE);
            tv_amount_spec.setVisibility(View.VISIBLE);
        }

        if (data.getReturnState() == 0) {
            //待审核
            tv_apply_time4.setText(data.getApplyDate());
            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.GONE);
            rl_apply_time4.setVisibility(View.VISIBLE);
            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.GONE);

        } else if (data.getReturnState() == 1) {
            //成功
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

        } else if (data.getReturnState() == 2) {
            tv_check_time3.setText(data.getCheckDate());
            tv_apply_time3.setText(data.getApplyDate());
            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.VISIBLE);
            rl_apply_time4.setVisibility(View.GONE);

            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.VISIBLE);
        } else if (data.getReturnState() == 4) {
            //已撤销
            ll_progress4.setVisibility(View.VISIBLE);
            rl_return_success4.setVisibility(View.VISIBLE);
            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.GONE);
            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.GONE);
            rl_apply_time4.setVisibility(View.GONE);
            tv_cancel_time.setText(data.getCancelDate());
            tv_apply_time5.setText(data.getApplyDate());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_order:
                //0配送 1自提
                if (orderDeliveryType == 0) {
                    Intent intent = new Intent(mContext, LookDeliveryDetailActivity.class);
                    intent.putExtra(AppConstant.ORDERID, dataBean.getOrderId());
                    startActivity(intent);
                } else if (orderDeliveryType == 1) {
                    Intent intent = new Intent(mContext, LookSelfDetailActivity.class);
                    intent.putExtra(AppConstant.ORDERID, dataBean.getOrderId());
                    startActivity(intent);

                }
                break;
        }
    }
}
