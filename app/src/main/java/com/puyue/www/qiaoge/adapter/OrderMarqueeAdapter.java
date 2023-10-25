package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HelpPayDeliveryDetailActivity;
import com.puyue.www.qiaoge.activity.HelpPaySelfDetailActivity;
import com.puyue.www.qiaoge.activity.mine.MessageDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.MapOrderMessageActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView3;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView4;

import java.util.List;

public class OrderMarqueeAdapter extends RecyclerView.Adapter<OrderMarqueeAdapter.ViewHolder>{
    List<OrderModel.DataBean> data;
    Activity activity;
    OrderModel.DataBean dataBean;


    public void setData(List<OrderModel.DataBean> data, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_marquee, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dataBean = data.get(position % data.size());
        holder.tv_order_state.setText(dataBean.getOrderStatusStr());
        holder.tv_position.setText(position % data.size()+1+"/"+data.size());
        if(dataBean.getOrderStatus()==1) {
            //支付剩余时间
            holder.snap.setTime(true,dataBean.sysCurrentTime,0,dataBean.orderOverTime);
            holder.snap.setVisibility(View.VISIBLE);
            holder.tv_time.setVisibility(View.GONE);
        }else {
            holder.snap.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(dataBean.getPayDate());
        }
        String orderDeliveryType = dataBean.getOrderDeliveryType();
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataBean.getFriendPay() == 1) {
                    //帮付
                    Intent intent;
                    if(orderDeliveryType.equals("0")) {
                        //配送
                        intent = new Intent(activity, HelpPayDeliveryDetailActivity.class);
                    }else {
                        intent = new Intent(activity, HelpPaySelfDetailActivity.class);
                    }
                    intent.putExtra(AppConstant.ORDERID, data.get(position % data.size()).getOrderId());
                    activity.startActivity(intent);
                }else {
                    Intent intent;
                    if(orderDeliveryType.equals("0")) {
                        //配送
                        intent = new Intent(activity, NewOrderDetailActivity.class);
                        intent.putExtra("account","0");

                    }else {
                        intent = new Intent(activity, SelfSufficiencyOrderDetailActivity.class);
                        intent.putExtra("account","0");
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, "");

                    }
                    intent.putExtra(AppConstant.ORDERID, data.get(position % data.size()).getOrderId());
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_state;
        TextView tv_time;
        TextView tv_pay;
        ImageView iv_pic;
        TextView tv_position;
        LinearLayout ll_root;
        SnapUpCountDownTimerView4 snap;
        public ViewHolder(View view) {
            super(view);
            ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
            snap = (SnapUpCountDownTimerView4) view.findViewById(R.id.snap);
            tv_order_state = (TextView) view.findViewById(R.id.tv_order_state);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_pay = (TextView) view.findViewById(R.id.tv_pay);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_position = (TextView) view.findViewById(R.id.tv_position);
        }
    }


}
