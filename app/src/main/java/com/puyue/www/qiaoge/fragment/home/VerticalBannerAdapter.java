package com.puyue.www.qiaoge.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.MapOrderMessageActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.api.home.DriverInfo;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViews;
import com.taobao.library.BaseBannerAdapter;
import com.taobao.library.VerticalBannerView;

import java.util.List;

/**
 * Created by ${王涛} on 2020/1/14
 */
class VerticalBannerAdapter extends BaseBannerAdapter<OrderModel.DataBean> {
    private TextView tv_order_state;
    private TextView tv_pay;
    private TextView tv_time;
    private ImageView iv_icon;
    String cell;
    Context context;
    RelativeLayout rl_driver;
    TextView tv_unpay;
    TextView tv_look;
    LinearLayout ll_snap;
    public VerticalBannerAdapter(String cell, List<OrderModel.DataBean> datas, Context context) {
        super(datas);
         this.context = context;
        this.cell = cell;
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver,null);
    }

    @Override
    public void setItem(View view, OrderModel.DataBean data) {
        SnapUpCountDownTimerViews snap = view.findViewById(R.id.snap);
        ll_snap = view.findViewById(R.id.ll_snap);
        tv_order_state = view.findViewById(R.id.tv_order_state);
        tv_unpay = view.findViewById(R.id.tv_unpay);
        tv_look = view.findViewById(R.id.tv_look);
        tv_order_state.setText(data.getOrderStatusStr());
        tv_time = view.findViewById(R.id.tv_time);
        tv_pay = view.findViewById(R.id.tv_pay);
        String s = "需支付￥"+data.getTotalAmount();
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 3,
                String.valueOf(data.getTotalAmount()).length()+1, Color.parseColor("#FF2622"));
        tv_pay.setText(spannableStringBuilder);
        if(data.getOrderStatus()==1) {
            tv_time.setText(data.getGmtCreate()+"|");
            tv_unpay.setVisibility(View.VISIBLE);
            tv_unpay.setText("去支付");
            tv_look.setVisibility(View.GONE);
            tv_pay.setVisibility(View.VISIBLE);
            snap.setTime(true,data.sysCurrentTime,0,data.orderOverTime);
            tv_look.setText("查看");
//            snap.start();
            snap.setVisibility(View.VISIBLE);
            ll_snap.setVisibility(View.VISIBLE);
        }else if(data.getOrderStatus()==3){
            tv_unpay.setVisibility(View.GONE);
            tv_look.setVisibility(View.VISIBLE);
            tv_look.setText("查看物流");
            tv_pay.setVisibility(View.GONE);
            tv_time.setText(data.getGmtCreate()+"下单");
            snap.setVisibility(View.GONE);
            ll_snap.setVisibility(View.GONE);
        }else {
            tv_look.setText("查看");
            tv_unpay.setVisibility(View.GONE);
            tv_look.setVisibility(View.VISIBLE);
            tv_pay.setVisibility(View.GONE);
            tv_time.setText(data.getGmtCreate()+"下单");
            snap.setVisibility(View.GONE);
            ll_snap.setVisibility(View.GONE);
        }

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderDetailActivity.class);
                intent.putExtra("account","0");
                intent.putExtra(AppConstant.ORDERID, data.getOrderId());
                context.startActivity(intent);
            }
        });

        tv_unpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getOrderStatus()==1) {
                    Intent intent = new Intent(context, NewOrderDetailActivity.class);
                    intent.putExtra("account","0");
                    intent.putExtra(AppConstant.ORDERID, data.getOrderId());
                    context.startActivity(intent);
                }else {
                    Intent intent1 = new Intent(context, MapOrderMessageActivity.class);
                    intent1.putExtra("orderId", data.getOrderId());
                    context.startActivity(intent1);
                }
            }
        });
    }

}
