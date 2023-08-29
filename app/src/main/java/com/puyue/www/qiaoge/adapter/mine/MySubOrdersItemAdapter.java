package com.puyue.www.qiaoge.adapter.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HelpPayActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.ReturnGoodDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.HuoConnentionDialog;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.view.GlideModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/8/6  （子账户订单列表）
 */
public class MySubOrdersItemAdapter extends BaseQuickAdapter<OrdersModel.DataBean.ListBean, BaseViewHolder> {

    private int orderState;
    private ImageView commodityOne;
    private ImageView commodityTwo;
    private ImageView commodityThree;
    private ImageView commodityFour;
    private ImageView commodityMore;
    private TextView tv_status;
    private TextView imageGo;//立即付款
    private TextView evaluateNow; // 立即评价
    private TextView againBay;  // 再次购买
    private MySubOrdersItemAdapter.OnClick onClick;
    private LinearLayout linearLayoutItem;
    private TextView cancelOrder;//取消订单
    private TextView deleteOrder;//删除订单
    private TextView confirmOrder;//确认收货
    TextView tv_product_name;
    TextView tv_time;
    TextView tv_sub_account;
    TextView tv_call;
    TextView tv_status_name;
    int orderDeliveryType;
    TextView tv_total;
    LinearLayout ll_info;
    TextView tv_friend_pay;
    public MySubOrdersItemAdapter(int layoutResId, @Nullable List<OrdersModel.DataBean.ListBean> data, int orderState, int orderDeliveryType, MySubOrdersItemAdapter.OnClick onClick) {
        super(layoutResId, data);
        this.orderState = orderState;
        this.onClick = onClick;
        this.orderDeliveryType = orderDeliveryType;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrdersModel.DataBean.ListBean item) {
        helper.setIsRecyclable(false);
        tv_call = helper.getView(R.id.tv_call);
        tv_friend_pay = helper.getView(R.id.tv_friend_pay);
        tv_total = helper.getView(R.id.tv_total);
        ImageView iv_style = helper.getView(R.id.iv_style);
        TextView tv_order = helper.getView(R.id.tv_order);
        TextView tv_pay = helper.getView(R.id.tv_pay);
        tv_sub_account = helper.getView(R.id.tv_sub_account);
        tv_time = helper.getView(R.id.tv_time);
        tv_product_name = helper.getView(R.id.tv_product_name);
        againBay = helper.getView(R.id.againBay);
        tv_status_name = helper.getView(R.id.tv_status_name);
        commodityOne = helper.getView(R.id.commodityOne);
        commodityTwo = helper.getView(R.id.commodityTwo);
        commodityThree = helper.getView(R.id.commodityThree);
        commodityFour = helper.getView(R.id.commodityFour);
        commodityMore = helper.getView(R.id.commodityMore);
        tv_status = helper.getView(R.id.tv_status);
        imageGo = helper.getView(R.id.imageGo);
        deleteOrder = helper.getView(R.id.tv_delete_order);
        cancelOrder = helper.getView(R.id.tv_cancel_order);
        evaluateNow = helper.getView(R.id.evaluateNow);
        linearLayoutItem = helper.getView(R.id.linearLayoutItem);
        confirmOrder = helper.getView(R.id.tv_confirm_order);
        tv_product_name.setText(item.prodName);
        ll_info = helper.getView(R.id.ll_info);
        tv_time.setText(item.orderTime);
        if(item.subBuyPhone!=null && !item.subBuyPhone.equals("")) {
            tv_sub_account.setText(item.subBuyPhone+"订单");
            ll_info.setVisibility(View.VISIBLE);
        }else {
            ll_info.setVisibility(View.GONE);
        }
        if(item.pics!=null && item.pics.size()>0) {
            tv_total.setText("共"+item.pics.size()+"件商品");
            tv_total.setVisibility(View.VISIBLE);
        }else {
            tv_total.setVisibility(View.GONE);
        }

        if(item.friendPayBtn ==0) {
            tv_friend_pay.setVisibility(View.GONE);
        }else {
            tv_friend_pay.setVisibility(View.VISIBLE);
        }

        tv_friend_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HelpPayActivity.class);
                intent.putExtra("orderId",item.orderId);
                mContext.startActivity(intent);
            }
        });

        if(orderDeliveryType==0) {
            //配送
            iv_style.setVisibility(View.VISIBLE);
            if(item.deliverModel==0) {
                iv_style.setImageResource(R.mipmap.ic_qiaoge);
                tv_status_name.setVisibility(View.GONE);
                tv_status_name.setBackgroundResource(R.drawable.shape_white1);
            }else {
                if(item.hllOrderStatusName!=null) {
                    iv_style.setImageResource(R.mipmap.ic_huolala);
                }else {
                    iv_style.setImageResource(R.mipmap.ic_huolala);
                }

                if(item.hllOrderStatusName!=null&&item.hllOrderStatusName.equals("")) {
                    tv_status_name.setText(item.hllOrderStatusName);
                    tv_status_name.setVisibility(View.VISIBLE);
                    tv_status_name.setBackgroundResource(R.drawable.shape_orange12);
                }else {
                    tv_status_name.setVisibility(View.GONE);
                    tv_status_name.setBackgroundResource(R.drawable.shape_white1);
                }
            }
        }else {
            //自提
            tv_status_name.setVisibility(View.GONE);
            iv_style.setVisibility(View.GONE);
        }

        if(item.saleSettle==1) {
            tv_order.setVisibility(View.VISIBLE);
        }else {
            tv_order.setVisibility(View.GONE);
        }

        if(item.salePay==1) {
            tv_pay.setVisibility(View.VISIBLE);
        }else {
            tv_pay.setVisibility(View.GONE);
        }


        helper.setText(R.id.tv_item_my_order_all_price, "￥"+item.totalAmount);//总价

        // orderState 0全部 orderState 2 待发货订单 orderState 5 待评价订单
        // 1待付款订单 3待收货订单 11退货订单 7 已取消

        if(item.orderStatus==2) {
            //待发货-待接收
            againBay.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.GONE);
            imageGo.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            tv_time.setVisibility(View.VISIBLE);
            getState(item);
        }else if(item.orderStatus==1) {
            //代付款
            tv_time.setVisibility(View.VISIBLE);
            imageGo.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.VISIBLE);
            againBay.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.GONE);
            getState(item);
        }else if(item.orderStatus==3) {
            //待收货
            confirmOrder.setVisibility(View.VISIBLE);
            againBay.setVisibility(View.VISIBLE);
            imageGo.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.GONE);
            cancelOrder.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.GONE);
            tv_time.setVisibility(View.GONE);
            getState(item);
        }else if(item.orderStatus==14) {
            //待发货-已接收
            againBay.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.GONE);
            imageGo.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            tv_time.setVisibility(View.VISIBLE);
            getState(item);
        }else if(item.orderStatus==5) {
            //待评价
            tv_time.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.VISIBLE);
            againBay.setVisibility(View.VISIBLE);
            deleteOrder.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            imageGo.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            getState(item);
        }else if(item.orderStatus==6) {
            //已评价
            imageGo.setVisibility(View.GONE);
            againBay.setVisibility(View.VISIBLE);
            evaluateNow.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            tv_call.setVisibility(View.GONE);
        }else if(item.orderStatus==7) {
            //已取消
            tv_time.setVisibility(View.GONE);
            imageGo.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.VISIBLE);
            againBay.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            evaluateNow.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            getState(item);
        }else {
            //退货
            imageGo.setVisibility(View.GONE);
            againBay.setVisibility(View.VISIBLE);
            evaluateNow.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.VISIBLE);
            cancelOrder.setVisibility(View.GONE);
            tv_call.setVisibility(View.GONE);
        }

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
                        Intent intent =new Intent(mContext,ReturnGoodDetailActivity.class);
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
                onClick.deleteOnclick(item.orderId,item.orderStatus);
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

    private void getState(OrdersModel.DataBean.ListBean item) {
        if(item.deliverModel==1) {
            if(item.orderStatus!=1&&item.orderStatus!=7&&item.orderStatus!=11) {
                if(!TextUtils.isEmpty(item.hllOrderId)&&!item.hllOrderId.equals("")) {
                    tv_call.setVisibility(View.GONE);

                }else {
                    tv_call.setVisibility(View.VISIBLE);
                }
            }
        }else {
            tv_call.setVisibility(View.GONE);
        }
    }


    public interface OnClick {

        void callHuo(int deliveryMode,String orderId,String hllOrderId);

        void evaluateNowOnclick(int position,String orderId);

        void againBayOnclick(int position);

        void cancelOnclick(String orderId);

        void deleteOnclick(String orderId,int orderStatus);

        void imageGo(String orderId, String totalAmount);

        void requestConfirmGetGoods(String orderId);

        void confirmSelfOrder(String orderId);

        void confirmSelfReturnOrder(String orderId, int pos);

    }


}
