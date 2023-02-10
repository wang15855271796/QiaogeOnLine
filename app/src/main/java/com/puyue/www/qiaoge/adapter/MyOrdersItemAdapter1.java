package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.ReturnGoodDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.mine.MyOrdersItemAdapter;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.HuoConnentionDialog;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.view.GlideModel;

import java.util.List;

public class MyOrdersItemAdapter1 extends BaseQuickAdapter<OrdersModel.DataBean.ListBean, BaseViewHolder> {

    private int orderState;
    private ImageView commodityOne;
    private ImageView commodityTwo;
    private ImageView commodityThree;
    private ImageView commodityFour;
    private ImageView commodityMore;
    private TextView tv_status;
    private ImageView imageGo;//立即付款
    private ImageView evaluateNow; // 立即评价
    private TextView againBay;  // 再次购买
    private MyOrdersItemAdapter.OnClick onClick;
    private LinearLayout linearLayoutItem;
    private ImageView cancelOrder;//取消订单
    private ImageView deleteOrder;//删除订单
    private ImageView confirmOrder;//确认收货
    private int orderDeliveryType;
    LinearLayout ll_return;
    TextView tv_product_name;
    TextView tv_time;
    TextView tv_subUserBuy;
    TextView tv_call;
    TextView tv_state;
    public MyOrdersItemAdapter1(int layoutResId, @Nullable List<OrdersModel.DataBean.ListBean> data, int orderState, int orderDeliveryType, MyOrdersItemAdapter.OnClick onClick) {
        super(layoutResId, data);
        this.orderState = orderState;
        this.onClick = onClick;
        this.orderDeliveryType = orderDeliveryType;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrdersModel.DataBean.ListBean item) {
        helper.setIsRecyclable(false);
        tv_call = helper.getView(R.id.tv_call);
        ImageView iv_style = helper.getView(R.id.iv_style);
        tv_subUserBuy = helper.getView(R.id.tv_subUserBuy);
        tv_time = helper.getView(R.id.tv_time);
        tv_product_name = helper.getView(R.id.tv_product_name);
        againBay = helper.getView(R.id.againBay);
        commodityOne = helper.getView(R.id.commodityOne);
        commodityTwo = helper.getView(R.id.commodityTwo);
        commodityThree = helper.getView(R.id.commodityThree);
        commodityFour = helper.getView(R.id.commodityFour);
        commodityMore = helper.getView(R.id.commodityMore);
        tv_status = helper.getView(R.id.tv_status);
        tv_state = helper.getView(R.id.tv_state);
        imageGo = helper.getView(R.id.imageGo);
        deleteOrder = helper.getView(R.id.iv_delete_order);
        cancelOrder = helper.getView(R.id.iv_cancel_order);
        evaluateNow = helper.getView(R.id.evaluateNow);
        linearLayoutItem = helper.getView(R.id.linearLayoutItem);
        confirmOrder = helper.getView(R.id.iv_confirm_order);
        tv_product_name.setText(item.prodName);
        ll_return = helper.getView(R.id.ll_return);
        TextView tv_return_money = helper.getView(R.id.tv_return_money);
        tv_time.setText(item.orderTime);
        tv_subUserBuy.setText(item.subBuyPhone);
//        if(item.deliverModel==0) {
//            iv_style.setImageResource(R.mipmap.ic_qiaoge);
//        }else {
//            iv_style.setImageResource(R.mipmap.ic_huolala);
//        }

        if(item.deliverModel==0) {
            iv_style.setImageResource(R.mipmap.ic_qiaoge);
        }else {
            if(item.hllOrderStatusName!=null) {
                iv_style.setImageResource(R.mipmap.ic_huolala);
            }else {
                iv_style.setImageResource(R.mipmap.ic_huolala);
            }
        }

        if(item.returnOrderStatus== 1) {
            ll_return.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_item_my_order_all_price, "退款成功￥"+item.totalAmount);//总价
//            if(item.bankReturnFlag) {
//
//            }else {

//
//            }
        }else {
            ll_return.setVisibility(View.GONE);
        }

        tv_return_money.setText("退款:￥"+item.totalAmount);
        // orderState 0全部 orderState 2 待发货订单 orderState 5 待评价订单
        // 1待付款订单 3待收货订单 11退货订单 7 已取消

        //退货
        imageGo.setVisibility(View.GONE);
        evaluateNow.setVisibility(View.GONE);
        confirmOrder.setVisibility(View.GONE);
        deleteOrder.setVisibility(View.GONE);
        cancelOrder.setVisibility(View.GONE);
        tv_call.setVisibility(View.GONE);

        if(orderState==11) {
            tv_status.setText(item.returnOrderStatusStr);
        }else {
            tv_status.setText(item.orderStatusName);
        }


        /**显示4张图*/
        if (item.pics.size() >= 1) {
            if (item.pics.get(0)!= null) {
                GlideModel.disPlayError(mContext, item.pics.get(0), commodityOne);
            }

            commodityOne.setVisibility(View.VISIBLE);
        } else {

            commodityOne.setVisibility(View.GONE);
            commodityOne.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.pics.size() >= 2) {
            if (item.pics.get(1) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(1), commodityTwo);
            }

            commodityTwo.setVisibility(View.VISIBLE);
        } else {
            commodityTwo.setVisibility(View.GONE);
            commodityTwo.setImageResource(R.mipmap.ic_launcher_round);
        }
        if ((item.pics.size() >= 3)) {
            if (item.pics.get(2) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(2), commodityThree);
            }

            commodityThree.setVisibility(View.VISIBLE);

        } else {
            commodityThree.setVisibility(View.GONE);
            commodityThree.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.pics.size() >= 4) {
            if (item.pics.get(3) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(3), commodityFour);
            }

            commodityFour.setVisibility(View.VISIBLE);
        } else {
            commodityFour.setVisibility(View.GONE);
            commodityFour.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.pics.size() >= 4) {
            commodityMore.setVisibility(View.VISIBLE);
        } else {
            commodityMore.setVisibility(View.GONE);
        }

        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.callHuo(item.deliverModel,item.orderId,item.hllOrderId);
            }
        });

        linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deliverType = UserInfoHelper.getDeliverType(mContext);
                //0配送 1自提
                if (deliverType.equals("0")) {
                    if (orderState == 11){
                        Intent intent =new Intent(mContext, ReturnGoodDetailActivity.class);
                        intent.putExtra("orderType" ,0);
                        intent.putExtra("account" ,"0");
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, item.returnProductMainId);
                        mContext.startActivity(intent);

                    }else {
                        Intent intent = new Intent(mContext, NewOrderDetailActivity.class);
                        intent.putExtra(AppConstant.ORDERID, item.orderId);
                        intent.putExtra(AppConstant.ORDERSTATE, "");
                        intent.putExtra("account" ,"0");
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, "");
                        mContext.startActivity(intent);
                    }

                } else if (deliverType.equals("1")) {
                    if (orderState == 11){
                        Intent intent =new Intent(mContext,ReturnGoodDetailActivity.class);
                        intent.putExtra("orderType" ,1);
                        intent.putExtra("account" ,"0");
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, item.returnProductMainId);
                        mContext.startActivity(intent);

                    }else {
                        Intent intent = new Intent(mContext, SelfSufficiencyOrderDetailActivity.class);
                        intent.putExtra(AppConstant.ORDERID, item.orderId);
                        intent.putExtra(AppConstant.ORDERSTATE, "");
                        intent.putExtra("account" ,"0");
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, "");
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        evaluateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.evaluateNowOnclick(helper.getLayoutPosition(),item.orderId);
            }
        });
        againBay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.againBayOnclick(helper.getLayoutPosition());
            }
        });

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.cancelOnclick(item.orderId);

            }
        });
        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.deleteOnclick(item.orderId);
            }
        });
        imageGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.imageGo(item.orderId, item.totalAmount);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.requestConfirmGetGoods(item.orderId);
            }
        });
    }

    public interface OnClick {

        void callHuo(int deliveryMode,String orderId,String hllOrderId);

        void evaluateNowOnclick(int position,String orderId);

        void againBayOnclick(int position);

        void cancelOnclick(String orderId);

        void deleteOnclick(String orderId);

        void imageGo(String orderId, String totalAmount);

        void requestConfirmGetGoods(String orderId);
        void confirmSelfOrder(String orderId);
        void confirmSelfReturnOrder(String orderId, int pos);

    }

}
