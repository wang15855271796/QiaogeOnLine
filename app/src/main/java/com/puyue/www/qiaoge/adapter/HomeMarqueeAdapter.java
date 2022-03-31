package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.MapOrderMessageActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.FullDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViews;

import java.util.List;

public class HomeMarqueeAdapter extends RecyclerView.Adapter<HomeMarqueeAdapter.ViewHolder> {
    List<OrderModel.DataBean> dataBean;
    Activity activity;
    OrderModel.DataBean data;
    public void setData(List<OrderModel.DataBean> data, int itemCount, FragmentActivity activity) {
        this.dataBean = data;
        this.activity = activity;
        int remainder = dataBean.size() % itemCount;
        if (remainder > 0) {
            for (int i = 0; i >= itemCount; i++) {
                this.dataBean.remove(dataBean.size()-1);
            }
        }
        if (dataBean.size() > itemCount) {
            for (int i = 0; i < itemCount; i++) {
                this.dataBean.add(dataBean.size(), dataBean.get(i));
            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        data = dataBean.get(position);
        String s = "需支付￥"+data.getTotalAmount();
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 3,
                String.valueOf(data.getTotalAmount()).length()+1, Color.parseColor("#FF2622"));
        holder.tv_pay.setText(spannableStringBuilder);

        if(data.getOrderStatus()==1) {
//            tv_unpay.setText("去付款"+data.);
            holder.tv_time.setText(data.getGmtCreate()+"下单 |");
            holder.tv_unpay.setVisibility(View.VISIBLE);
            holder.tv_look.setVisibility(View.GONE);
            holder.tv_pay.setVisibility(View.VISIBLE);

//            snap.SnapUpCountDownTimerViewType(context, 1);
//            snap.setBackTheme(true);
            holder.snap.setTime(true,data.sysCurrentTime,data.orderOverTime,0);
//            snap.changeTypeColor(Color.WHITE);
            holder.snap.start();
            holder.snap.setVisibility(View.VISIBLE);

        }else if(data.getOrderStatus()==3){
            holder.tv_unpay.setVisibility(View.VISIBLE);
            holder.tv_look.setVisibility(View.GONE);
            holder.tv_pay.setVisibility(View.GONE);
            holder.tv_time.setText(data.getGmtCreate()+"下单");
            holder.snap.setVisibility(View.GONE);
        }else {
            holder.tv_unpay.setVisibility(View.GONE);
            holder.tv_look.setVisibility(View.VISIBLE);
            holder.tv_pay.setVisibility(View.GONE);
            holder.tv_time.setText(data.getGmtCreate()+"下单");
            holder.snap.setVisibility(View.GONE);
        }

        holder.tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewOrderDetailActivity.class);
                intent.putExtra("account","0");
                intent.putExtra(AppConstant.ORDERID, data.getOrderId());
                activity.startActivity(intent);
            }
        });

        holder.tv_unpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getOrderStatus()==1) {
                    Intent intent = new Intent(activity, NewOrderDetailActivity.class);
                    intent.putExtra("account","0");
                    intent.putExtra(AppConstant.ORDERID, data.getOrderId());
                    activity.startActivity(intent);
                }else {
                    Intent intent1 = new Intent(activity, MapOrderMessageActivity.class);
                    intent1.putExtra("orderId", data.getOrderId());
                    activity.startActivity(intent1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataBean == null || dataBean.size() == 0) {
            return 0;
        } else {
            return dataBean.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_no;
        TextView tv_phone;
        TextView tv_date;
        TextView tv_order_state;
        ImageView iv_icon;
        TextView tv_time;
        TextView tv_pay;
        TextView tv_unpay;
        TextView tv_look;
        SnapUpCountDownTimerViews snap;
        RelativeLayout rl_driver;
        public ViewHolder(View view) {
            super(view);
            snap = view.findViewById(R.id.snap);
            tv_look = view.findViewById(R.id.tv_look);
            tv_unpay = view.findViewById(R.id.tv_unpay);
            tv_pay = view.findViewById(R.id.tv_pay);
            tv_time = view.findViewById(R.id.tv_time);
            tv_order_state = view.findViewById(R.id.tv_order_state);
            iv_icon = view.findViewById(R.id.iv_icon);
            rl_driver = view.findViewById(R.id.rl_driver);
            tv_date = view.findViewById(R.id.tv_date);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_no = (TextView) view.findViewById(R.id.tv_no);
        }
    }

}
