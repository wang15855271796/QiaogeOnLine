package com.puyue.www.qiaoge.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tu.loadingdialog.LoadingDailog;
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

public class LookDeliveryDetailActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.tv_send_desc)
    TextView tv_send_desc;
    @BindView(R.id.tv_send_amount)
    TextView tv_send_amount;
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
    @BindView(R.id.tv_replace_pay)
    TextView tv_replace_pay;
    @BindView(R.id.tv_replace_order)
    TextView tv_replace_order;
    @BindView(R.id.ll_replace_order)
    LinearLayout ll_replace_order;
    @BindView(R.id.ll_replace_pay)
    LinearLayout ll_replace_pay;
    @BindView(R.id.tv_driver_content)
    TextView tv_driver_content;
    @BindView(R.id.iv_fresh_status)
    ImageView iv_fresh_status;
    @BindView(R.id.tv_order_status)
    TextView tv_order_status;
    @BindView(R.id.rl_driver_info)
    RelativeLayout rl_driver_info;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_pay_style)
    TextView tv_pay_style;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.rv_full)
    RecyclerView rv_full;
    String orderId;
    LoadingDailog dialog;
    OrderFullAdapter orderFullAdapter;
    HelpOrderDetailAdapter helpOrderDetailAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_delivery_detail);
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
        iv_fresh_status.setOnClickListener(this);
    }

    //获取订单详情
    GetOrderDetailModel.DataBean dataBean;
    private List<GetOrderDetailModel.DataBean.SendGiftInfo> list_full = new ArrayList<>();
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

                                if(dataBean.sendGiftInfo!=null) {
                                    list_full.addAll(orderDetailModel.data.sendGiftInfo);
                                    rv_full.setVisibility(View.VISIBLE);
                                }else {
                                    rv_full.setVisibility(View.GONE);
                                }

                                orderFullAdapter.notifyDataSetChanged();
                                helpOrderDetailAdapter.notifyDataSetChanged();
                                setContent(dataBean);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, orderDetailModel.message);
                        }

                        dialog.dismiss();
                    }
                });
    }

    private void setContent(GetOrderDetailModel.DataBean data) {
        if(data.getDeliveryModel() == 0) {
            tv_distribution.setText("翘歌配送");
        }else {
            tv_distribution.setText("我自己叫货拉拉");
        }

        tv_apply_person.setText("申请人:"+data.orderPhone);
        tv_pay_time.setText(data.payDate);
        tv_pay_style.setText(data.payChannel);

        if(data.saleSettle==1) {
            ll_replace_order.setVisibility(View.VISIBLE);
            tv_replace_order.setText(data.saleName);
        }else {
            ll_replace_order.setVisibility(View.GONE);
        }

        if (data.orderStatus == 2) {
            tv_order_status.setText("等待接收订单");
            tv_order_status.setTextColor(Color.parseColor("#FE5630"));
        } else if (data.orderStatus == 14) {
            tv_order_status.setText("订单已接收");
            tv_order_status.setTextColor(Color.parseColor("#999999"));
        } else if (data.orderStatus == 3) {
            tv_order_status.setTextColor(Color.parseColor("#999999"));
            tv_order_status.setText("装车完成-已发货");
        }

        rl_driver_info.setVisibility(data.orderStatus == 2 || data.orderStatus == 14 || data.orderStatus == 3 ? View.VISIBLE : View.GONE);

        GetOrderDetailModel.DataBean.AddressVOBean addressVO = data.addressVO;
//        tv_apply_person.setText(addressVO.shopName);
        tv_status.setText(data.orderStatusName);
        tv_address.setText(addressVO.shopName+ " "+addressVO.userName +" "+addressVO.contactPhone);
        tv_detail_address.setText(addressVO.provinceName+addressVO.cityName+addressVO.areaName+addressVO.detailAddress);
        tv_goods_num.setText("共"+data.prodNum+"件");
        tv_driver_content.setText(data.payDate);
        tv_goods_amount.setText("￥"+data.prodAmount);
        tv_send_desc.setText(data.sendAmountStr);
        tv_send_amount.setText("￥"+data.deliveryFee);
        tv_full_desc.setText(data.vipReductStr);
        tv_full_amount.setText("减￥"+data.vipReduct);
        tv_full_active_amount.setText("减￥"+data.normalReduct);
        tv_coupon_desc.setText(data.giftName);
        tv_coupon_amount.setText("减￥"+data.giftAmount);
        tv_should_pay.setText("￥"+data.totalAmount);
        tv_create_time.setText(data.gmtCreate);
        if(TextUtils.isEmpty(data.deliverTimeName)) {
            tv_delivery_time.setText("无");
        }else {
            tv_delivery_time.setText(data.deliverTimeName + "  " + data.deliverTimeStart + " - " + data.deliverTimeEnd);
        }
        if(TextUtils.isEmpty(data.remark)) {
            tv_memo.setText("无");
        }else {
            tv_memo.setText(data.remark);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_fresh_status:
                LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                        .setMessage("获取数据中")
                        .setCancelable(false)

                        .setCancelOutside(true);
                dialog = loadBuilder.create();
                dialog.show();
                orderProdsList.clear();
                getOrderDetail();
                break;

        }
    }
}
