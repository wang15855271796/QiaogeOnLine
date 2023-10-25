package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HelpOrderDetailAdapter;
import com.puyue.www.qiaoge.adapter.OrderFullAdapter;
import com.puyue.www.qiaoge.api.home.GetOrderDetailAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.HelpPayOrderDialog;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HelpPaySelfDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_distribution)
    TextView tv_distribution;
    @BindView(R.id.tv_apply_person)
    TextView tv_apply_person;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_detail_address)
    TextView tv_detail_address;
    @BindView(R.id.tv_goods_num)
    TextView tv_goods_num;
    @BindView(R.id.tv_goods_amount)
    TextView tv_goods_amount;
    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.tv_full_amount)
    TextView tv_full_amount;
    @BindView(R.id.tv_coupon_desc)
    TextView tv_coupon_desc;
    @BindView(R.id.tv_coupon_amount)
    TextView tv_coupon_amount;
    @BindView(R.id.tv_should_pay)
    TextView tv_should_pay;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_delivery_time)
    TextView tv_delivery_time;
    @BindView(R.id.tv_memo)
    TextView tv_memo;
    @BindView(R.id.tv_full_active_amount)
    TextView tv_full_active_amount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_take_name)
    TextView tv_take_name;
    @BindView(R.id.tv_take_phone)
    TextView tv_take_phone;
    @BindView(R.id.tv_take_time)
    TextView tv_take_time;
    @BindView(R.id.tv_replace_pay)
    TextView tv_replace_pay;
    @BindView(R.id.tv_replace_order)
    TextView tv_replace_order;
    @BindView(R.id.ll_replace_order)
    LinearLayout ll_replace_order;
    @BindView(R.id.ll_replace_pay)
    LinearLayout ll_replace_pay;
    @BindView(R.id.rv_full)
    RecyclerView rv_full;
    String orderId;
    HelpOrderDetailAdapter helpOrderDetailAdapter;
    OrderFullAdapter orderFullAdapter;
    private List<GetOrderDetailModel.DataBean.SendGiftInfo> list_full = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay_self_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        getOrderDetail();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        helpOrderDetailAdapter = new HelpOrderDetailAdapter(R.layout.item_help_order_detail, orderProdsList);
        recyclerView.setAdapter(helpOrderDetailAdapter);

        rv_full.setLayoutManager(new LinearLayoutManager(mContext));
        orderFullAdapter = new OrderFullAdapter(R.layout.item_full_order,list_full);
        rv_full.setAdapter(orderFullAdapter);

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
    }

    //获取订单详情
    GetOrderDetailModel.DataBean dataBean;
    List<GetOrderDetailModel.DataBean.OrderProdsBean> orderProdsList = new ArrayList<>();
    private void getOrderDetail() {
        GetOrderDetailAPI.requestData(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetOrderDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(GetOrderDetailModel orderDetailModel) {
                        if (orderDetailModel.code == 1) {
                            if (orderDetailModel.data!= null) {
                                dataBean = orderDetailModel.data;
                                orderProdsList.addAll(dataBean.orderProds);
                                helpOrderDetailAdapter.notifyDataSetChanged();

                                if(orderDetailModel.data.sendGiftInfo!=null) {
                                    list_full.addAll(orderDetailModel.data.sendGiftInfo);
                                    rv_full.setVisibility(View.VISIBLE);
                                }else {
                                    rv_full.setVisibility(View.GONE);
                                }
                                orderFullAdapter.notifyDataSetChanged();
                                setContent(dataBean);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, orderDetailModel.message);
                        }
                    }
                });
    }

    private void setContent(GetOrderDetailModel.DataBean data) {
//        if(data.sendFriendFlag==1) {
//            ll_replace_order.setVisibility(View.VISIBLE);
//            ll_replace_pay.setVisibility(View.VISIBLE);
//            tv_replace_order.setText(data.saleName);
//            tv_replace_pay.setText(data.payAccount);
//        }else {
//            ll_replace_order.setVisibility(View.GONE);
//            ll_replace_pay.setVisibility(View.GONE);
//        }
        if(data.saleSettle==1) {
            ll_replace_order.setVisibility(View.VISIBLE);
            tv_replace_order.setText(data.saleName);
        }else {
            ll_replace_order.setVisibility(View.GONE);
        }

        if(data.salePay==1) {
            ll_replace_pay.setVisibility(View.VISIBLE);
            tv_replace_pay.setText(data.payAccount);
        }else {
            ll_replace_pay.setVisibility(View.GONE);
        }

        tv_apply_person.setText("申请人:"+data.orderPhone);
        GetOrderDetailModel.DataBean.AddressVOBean addressVO = data.addressVO;
        tv_address.setText(data.wareName);
        tv_detail_address.setText(addressVO.provinceName+addressVO.cityName+addressVO.areaName+addressVO.detailAddress);
        tv_take_name.setText(data.pickUserName);
        tv_take_phone.setText(data.pickPhone);
        tv_take_time.setText(data.sendStartTime + data.sendTimeStr);
        tv_goods_num.setText("共"+data.prodNum+"件");
        tv_goods_amount.setText("￥"+data.prodAmount);
        tv_full_desc.setText(data.vipReductStr);
        tv_full_amount.setText("减￥"+data.vipReduct);
        tv_full_active_amount.setText("减￥"+data.normalReduct);
        tv_coupon_desc.setText(data.giftName);
        tv_coupon_amount.setText("减￥"+data.giftAmount);
        tv_should_pay.setText(data.totalAmount);
        tv_create_time.setText(data.gmtCreate);
        tv_delivery_time.setText(data.deliverTimeName + "  " + data.deliverTimeStart + " - " + data.deliverTimeEnd);
        tv_memo.setText(data.remark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_pay:
                HelpPayOrderDialog payDialog = new HelpPayOrderDialog(mActivity,orderId,dataBean.totalAmount,dataBean.remark, "1");
                payDialog.show();
                break;
        }
    }
}
