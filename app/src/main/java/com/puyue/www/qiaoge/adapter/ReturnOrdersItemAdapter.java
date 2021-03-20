package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.ReturnGoodDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.mine.MyOrdersItemAdapter;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.view.GlideModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/13
 */
public class ReturnOrdersItemAdapter  extends BaseQuickAdapter<OrdersModel.DataBean.ListBean, BaseViewHolder> {
    private int orderState;
    private ImageView commodityOne;
    private ImageView commodityTwo;
    private ImageView commodityThree;
    private ImageView commodityFour;
    private ImageView commodityMore;
    private TextView tv_status;
    private ImageView imageGo;//立即付款
    private ImageView evaluateNow; // 立即评价
    private ImageView againBay;  // 再次购买
    private OnClick onClick;
    private LinearLayout linearLayoutItem;
    private ImageView cancelOrder;//取消订单
    private ImageView deleteOrder;//删除订单
    private ImageView confirmOrder;//确认收货
    //    private ImageView iv_order_self_return;//待提货售后
//    private ImageView iv_confirm_order_self;//待提货确认收货
    private int orderDeliveryType;
    TextView tv_title;
    TextView tv_product_name;
    TextView tv_time;
    TextView tv_subUserBuy;
    RelativeLayout rl;
    public ReturnOrdersItemAdapter(int layoutResId, @Nullable List<OrdersModel.DataBean.ListBean> data, int orderState, int orderDeliveryType, OnClick onClick) {
        super(layoutResId, data);
        this.orderState = orderState;
        this.onClick = onClick;
        this.orderDeliveryType = orderDeliveryType;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrdersModel.DataBean.ListBean item) {
        helper.setIsRecyclable(false);
        rl = helper.getView(R.id.rl);
        tv_title = helper.getView(R.id.tv_title);
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
        imageGo = helper.getView(R.id.imageGo);
        deleteOrder = helper.getView(R.id.iv_delete_order);
        cancelOrder = helper.getView(R.id.iv_cancel_order);
        evaluateNow = helper.getView(R.id.evaluateNow);
        linearLayoutItem = helper.getView(R.id.linearLayoutItem);
        confirmOrder = helper.getView(R.id.iv_confirm_order);
        tv_product_name.setText(item.prodName);
        tv_time.setText(item.orderTime);
        tv_subUserBuy.setText(item.subBuyPhone);
        //判断自营还是非自营
        if(item.selfOrNot.equals("0")) {
            tv_title.setText("自营商品");
        }else {
            tv_title.setText("非自营商品");
        }

        if (orderDeliveryType == 0) {

        } else if (orderDeliveryType == 1) {
//            iv_order_self_return = helper.getView(R.id.iv_order_self_return);
//            iv_confirm_order_self = helper.getView(R.id.iv_confirm_order_self);
        }


        helper.setText(R.id.tv_item_my_order_all_price, "合计￥"+item.totalAmount);//总价

        // orderState 0全部 orderState 2 待发货订单 orderState 5 待评价订单
        // 1待付款订单 3待收货订单 11退货订单 7 已取消

        if (orderState == 11) { //退货订单 不显示按钮
            Log.d("weeeesss..............","00000");
            rl.setVisibility(View.VISIBLE);
            imageGo.setVisibility(View.GONE);
            againBay.setVisibility(View.VISIBLE);
            evaluateNow.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            deleteOrder.setVisibility(View.GONE);
            cancelOrder.setVisibility(View.GONE);
//            orderType.setText(item.returnOrderState);
        } else {
            if (item.orderStatusName.equals("待付款")) {
                // 待付款不显示 再次购买 其他的显示，取消的显示
                Log.d("weeeesss..............","11111");
                rl.setVisibility(View.GONE);
                imageGo.setVisibility(View.VISIBLE);
                cancelOrder.setVisibility(View.VISIBLE);
                againBay.setVisibility(View.GONE);
                evaluateNow.setVisibility(View.GONE);
                confirmOrder.setVisibility(View.GONE);
                deleteOrder.setVisibility(View.GONE);
            } else if (item.orderStatusName.equals("已取消")) {
                Log.d("weeeesss..............","22222");
                rl.setVisibility(View.VISIBLE);
                imageGo.setVisibility(View.GONE);
                deleteOrder.setVisibility(View.VISIBLE);
                againBay.setVisibility(View.VISIBLE);
                cancelOrder.setVisibility(View.GONE);
                evaluateNow.setVisibility(View.GONE);
                confirmOrder.setVisibility(View.GONE);
            } else if (item.orderStatusName.equals("待发货")) {
                Log.d("weeeesss..............","33333");
                rl.setVisibility(View.VISIBLE);
                againBay.setVisibility(View.VISIBLE);
                cancelOrder.setVisibility(View.GONE);
                evaluateNow.setVisibility(View.GONE);
                imageGo.setVisibility(View.GONE);
                deleteOrder.setVisibility(View.GONE);
                confirmOrder.setVisibility(View.GONE);
            } else if (item.orderStatusName.equals("待收货")) {
                Log.d("weeeesss..............","44444");
                rl.setVisibility(View.VISIBLE);
                confirmOrder.setVisibility(View.VISIBLE);
                againBay.setVisibility(View.VISIBLE);
                imageGo.setVisibility(View.GONE);
                deleteOrder.setVisibility(View.GONE);
                cancelOrder.setVisibility(View.GONE);
                evaluateNow.setVisibility(View.GONE);
            }


            if (item.orderStatusName.equals("待评价")) {   // 待评价显示 立即评价 和再次购买
                rl.setVisibility(View.VISIBLE);
                evaluateNow.setVisibility(View.VISIBLE);
                againBay.setVisibility(View.VISIBLE);
                deleteOrder.setVisibility(View.GONE);
                cancelOrder.setVisibility(View.GONE);
                imageGo.setVisibility(View.GONE);
                confirmOrder.setVisibility(View.GONE);
            } else {
                evaluateNow.setVisibility(View.GONE);

            }
            tv_status.setText(item.orderStatusName);
        }

//        helper.setText(R.id.orderTime, item.gmtCreate);
        // 文字渐变色
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, tv_status.getPaint().getTextSize(), Color.parseColor("#CEA6FF")
                , Color.parseColor("#6F81FF"), Shader.TileMode.CLAMP);
        tv_status.getPaint().setShader(mLinearGradient);


        /**显示4张图*/
        if (item.pics.size() >= 1) {
            if (item.pics.get(0)!= null) {
                GlideModel.disPlayError(mContext, item.pics.get(0), commodityOne);
                //  Glide.with(mContext).load(item.productVOList.get(0).picUrl).transform(new GlideRoundTransform(mContext, 3)).into(commodityOne);
            }

            commodityOne.setVisibility(View.VISIBLE);
        } else {

            commodityOne.setVisibility(View.GONE);
            commodityOne.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.pics.size() >= 2) {
            if (item.pics.get(1) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(1), commodityTwo);
                //  Glide.with(mContext).load(item.productVOList.get(1).picUrl).transform(new GlideRoundTransform(mContext, 3)).into(commodityTwo);
            }

            commodityTwo.setVisibility(View.VISIBLE);
        } else {
            commodityTwo.setVisibility(View.GONE);
            commodityTwo.setImageResource(R.mipmap.ic_launcher_round);
        }
        if ((item.pics.size() >= 3)) {
            if (item.pics.get(2) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(2), commodityThree);
                //Glide.with(mContext).load(item.productVOList.get(2).picUrl).transform(new GlideRoundTransform(mContext, 3)).into(commodityThree);
            }

            commodityThree.setVisibility(View.VISIBLE);

        } else {
            commodityThree.setVisibility(View.GONE);
            commodityThree.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.pics.size() >= 4) {
            if (item.pics.get(3) != null) {
                GlideModel.disPlayError(mContext, item.pics.get(3), commodityFour);
                //  Glide.with(mContext).load(item.productVOList.get(3).picUrl).transform(new GlideRoundTransform(mContext, 3)).into(commodityFour);
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
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, item.orderId);
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
                        intent.putExtra(AppConstant.RETURNPRODUCTMAINID, item.orderId);
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


        if (orderDeliveryType == 1) {
            if (item.orderStatus == 2) {
//                iv_order_self_return.setVisibility(View.VISIBLE);
//                iv_confirm_order_self.setVisibility(View.VISIBLE);
//                iv_order_self_return.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onClick.confirmSelfReturnOrder(item.orderId,helper.getLayoutPosition());
//                    }
//                });
//                iv_confirm_order_self.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onClick.confirmSelfOrder(item.orderId);
//                    }
//                });

            } else {
//                iv_order_self_return.setVisibility(View.GONE);
//                iv_confirm_order_self.setVisibility(View.GONE);
            }
        }


    }


    public interface OnClick {


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
