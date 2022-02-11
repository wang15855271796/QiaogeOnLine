package com.puyue.www.qiaoge.fragment.mine.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.adapter.mine.MyOrdersItemAdapter;
import com.puyue.www.qiaoge.adapter.mine.NewOrderDetailAdapter;
import com.puyue.www.qiaoge.api.cart.CancelOrderAPI;
import com.puyue.www.qiaoge.api.cart.DeleteOrderAPI;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.HuoConnentionDialog;
import com.puyue.www.qiaoge.fragment.mine.coupons.PaymentFragments;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.HasConnectModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.model.cart.CancelOrderModel;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * Created by Administrator on 2018/4/21.
 */
//待付款订单
public class PaymentOrderFragment extends BaseFragment {
    private PtrClassicFrameLayout mPtr;
    private RecyclerView mRv;
    private MyOrdersItemAdapter mAdapterMyOrders;
    private String mType;
    private ImageView mIvNoData;
    private int pageNum = 1;
    private OrdersModel mModelMyOrders;

    private NewOrderDetailAdapter adapter;
    private GetOrderDetailModel.DataBean getOrderDetailModel;
    private List<OrdersModel.DataBean.ListBean> mListResult = new ArrayList<>();
    private String returnProductMainId = "";
    private String orderId;
    private String orderState = "";
    private int orderStatusRequest;

//    private List<GetOrderDetailModel.DataBean.ProductVOListBean> list = new ArrayList<>();


    private int orderDeliveryType;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_orders;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {
        mPtr = ((PtrClassicFrameLayout) view.findViewById(R.id.ptr_my_orders));
        mRv = ((RecyclerView) view.findViewById(R.id.rv_my_orders));
        mIvNoData = ((ImageView) view.findViewById(R.id.iv_my_orders_no_data));
    }

    HuoConnentionDialog huoConnentionDialog;
    @Override
    public void setViewData() {
        Log.d("fesfdsf.....","11");
        mListResult.clear();
//        requestOrdersList(1);
        if (UserInfoHelper.getDeliverType(mActivity) != null && StringHelper.notEmptyAndNull(UserInfoHelper.getDeliverType(mActivity))) {
            orderDeliveryType = Integer.parseInt(UserInfoHelper.getDeliverType(mActivity));
        }
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                requestOrdersList(1);
            }
        });
        if (orderDeliveryType==0){
            mAdapterMyOrders = new MyOrdersItemAdapter(R.layout.item_my_order, mListResult, 1,orderDeliveryType, new MyOrdersItemAdapter.OnClick() {

                @Override
                public void callHuo(int deliveryMode, String orderId, String hllOrderId) {
                    HasConnect(orderId,hllOrderId);
                }

                @Override
                public void evaluateNowOnclick(int position,String orderId) {

                }

                @Override
                public void againBayOnclick(int position) {

                }

                @Override
                public void cancelOnclick(String orderId) {
                    final AlertDialog mDialog = new AlertDialog.Builder(getContext()).create();
                    mDialog.show();
                    mDialog.getWindow().setContentView(R.layout.dailog_cancel);
                    TextView mBtnCancel = (TextView) mDialog.getWindow().findViewById(R.id.btnCancel);
                    TextView mBtnOK = (TextView) mDialog.getWindow().findViewById(R.id.btnOK);

                    mBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });


                    mBtnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            //取消订单的接口
                            cancelOrder(orderId);
                            //   mPtr.refreshComplete();
                            //   mAdapterMyOrders.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void deleteOnclick(String orderId) {


                }

                @Override
                public void imageGo(String orderId, String payAmount) {

                    PaymentFragments paymentFragment = new PaymentFragments();
                    Bundle bundle = new Bundle();
                    bundle.putString("total", payAmount);
                    bundle.putString("remark","");
                    bundle.putString("payAmount",payAmount);
                    bundle.putString("orderId",orderId);
                    bundle.putString("orderDeliveryType",orderDeliveryType+"");
                    paymentFragment.setArguments(bundle);
                    paymentFragment.setCancelable(false);
                    paymentFragment.show(getFragmentManager(),"paymentFragment");
                }

                @Override
                public void requestConfirmGetGoods(String orderId) {

                }

                @Override
                public void confirmSelfOrder(String orderId) {

                }

                @Override
                public void confirmSelfReturnOrder(String orderId, int pos) {

                }


            });
        }else if (orderDeliveryType==1){
            mAdapterMyOrders = new MyOrdersItemAdapter(R.layout.item_my_order_self, mListResult, 1,orderDeliveryType, new MyOrdersItemAdapter.OnClick() {

                @Override
                public void callHuo(int deliveryMode, String orderId, String hllOrderId) {
                    HasConnect(orderId,hllOrderId);
                }

                @Override
                public void evaluateNowOnclick(int position,String orderId) {

                }

                @Override
                public void againBayOnclick(int position) {

                }

                @Override
                public void cancelOnclick(String orderId) {
                    final AlertDialog mDialog = new AlertDialog.Builder(getContext()).create();
                    mDialog.show();
                    mDialog.getWindow().setContentView(R.layout.dailog_cancel);
                    TextView mBtnCancel = (TextView) mDialog.getWindow().findViewById(R.id.btnCancel);
                    TextView mBtnOK = (TextView) mDialog.getWindow().findViewById(R.id.btnOK);

                    mBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });


                    mBtnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            //取消订单的接口
                            cancelOrder(orderId);
                            //   mPtr.refreshComplete();
                            //   mAdapterMyOrders.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void deleteOnclick(String orderId) {
               /* final AlertDialog mDialog = new AlertDialog.Builder(getContext()).create();
                mDialog.show();
                mDialog.getWindow().setContentView(R.layout.dailog_cancel);
                Button mBtnCancel = (Button) mDialog.getWindow().findViewById(R.id.btnCancel);
                Button mBtnOK = (Button) mDialog.getWindow().findViewById(R.id.btnOK);

                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });


                mBtnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        //取消订单的接口
                       deleteOrder(orderId);
                        //   mPtr.refreshComplete();
                        //   mAdapterMyOrders.notifyDataSetChanged();
                    }
                });*/


                }

                @Override
                public void imageGo(String orderId, String payAmount) {
//                    Intent intent = new Intent(getActivity(), MyConfireOrdersActivity.class);
//                    intent.putExtra("orderId", orderId);
//                    intent.putExtra("remark", "");
//
//                    intent.putExtra("payAmount", Double.parseDouble(payAmount));
//                    intent.putExtra("flag", true);
//
//                    startActivity(intent);

                    PaymentFragments paymentFragment = new PaymentFragments();
                    Bundle bundle = new Bundle();
                    bundle.putString("total", payAmount);
                    bundle.putString("remark","");
                    bundle.putString("payAmount",payAmount);
                    bundle.putString("orderId",orderId);
                    bundle.putString("orderDeliveryType",orderDeliveryType+"");
                    paymentFragment.setArguments(bundle);
                    paymentFragment.setCancelable(false);
                    paymentFragment.show(getFragmentManager(),"paymentFragment");

                }

                @Override
                public void requestConfirmGetGoods(String orderId) {

                }

                @Override
                public void confirmSelfOrder(String orderId) {

                }

                @Override
                public void confirmSelfReturnOrder(String orderId, int pos) {

                }


            });
        }

        mAdapterMyOrders.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    mPtr.setEnabled(false);
                } else {
                    mPtr.setEnabled(true);
                }
            }
        });
        mRv.setAdapter(mAdapterMyOrders);
        mAdapterMyOrders.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                requestOrdersList(1);
            }
        });
//        requestOrdersList(1);
    }

    /**
     * 判断是否有关联订单
     * @param orderId
     * @param hllOrderId
     */
    private void HasConnect(String orderId, String hllOrderId) {
        HuolalaAPI.getHasConnection(getContext(),orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HasConnectModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HasConnectModel hasConnectModel) {
                        if(hasConnectModel.getCode()==1) {
                            if(hasConnectModel.getData()!=null) {
                                if(hasConnectModel.getData().getConnectHllOrder()==1) {
                                    huoConnentionDialog = new HuoConnentionDialog(mActivity) {
                                        @Override
                                        public void Connect() {
                                            getConnection(orderId,hllOrderId);
                                        }

                                        @Override
                                        public void Next() {
//                                            Intent intent = new Intent(mActivity, HuoHomeActivity.class);
//                                            intent.putExtra("orderId",orderId);
//                                            mContext.startActivity(intent);
//                                            mActivity.finish();
                                            isAuth(orderId);
                                            dismiss();
                                        }
                                    };
                                    huoConnentionDialog.show();
                                }else {
                                    isAuth(orderId);
//                                    Intent intent = new Intent(mActivity, HuoHomeActivity.class);
//                                    intent.putExtra("orderId",orderId);
//                                    mActivity.startActivity(intent);
                                }
                            }
                        }else {
                            ToastUtil.showErroMsg(mActivity,hasConnectModel.getMessage());
                        }

                    }
                });
    }

    private void isAuth(String orderId) {
        HuolalaAPI.isAuthorize(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsAuthModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsAuthModel isAuthModel) {
                        if(isAuthModel.getCode()==1) {
                            if(isAuthModel.getData()!=null) {
                                if(isAuthModel.getData().isAuthorize()) {
                                    startActivity(CommonH6Activity.getIntent(mActivity, CommonH6Activity.class,isAuthModel.getData().getAuthUrl(),orderId));
                                }else {
                                    Intent intentss = new Intent(mActivity, HuoHomeActivity.class);
                                    intentss.putExtra("orderId",orderId);
                                    startActivity(intentss);
                                    mActivity.finish();
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,isAuthModel.getMessage());
                        }
                    }
                });
    }

    private void getConnection(String orderId, String hllOrderId) {
        HuolalaAPI.getConnection(mActivity, orderId,hllOrderId)
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
                        if(baseModel.code==1) {
                            requestOrdersList(1);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                            huoConnentionDialog.dismiss();
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }

    private void requestOrdersList(int orderStatus) {
        MyOrderListAPI.requestOrderList(getContext(), orderStatus, pageNum, 10, orderDeliveryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrdersModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrdersModel myOrdersModel) {
                        mPtr.refreshComplete();
                        logoutAndToHome(getContext(), myOrdersModel.code);
                        mModelMyOrders = myOrdersModel;
                        if (mModelMyOrders.success) {
                            updateOrderList();
                        } else {
                            AppHelper.showMsg(getContext(), mModelMyOrders.message);
                        }
                    }
                });
    }

    private void updateOrderList() {
        if (pageNum == 1) {
            if (mModelMyOrders.data != null && mModelMyOrders.data.list.size() > 0) {
                mIvNoData.setVisibility(View.GONE);
                mRv.setVisibility(View.VISIBLE);
                mListResult.clear();
                mListResult.addAll(mModelMyOrders.data.list);
                mAdapterMyOrders.notifyDataSetChanged();
            } else {
                mIvNoData.setVisibility(View.VISIBLE);
                mRv.setVisibility(View.GONE);
            }
        } else {
            //加载更多数据
            mListResult.addAll(mModelMyOrders.data.list);
            mAdapterMyOrders.notifyDataSetChanged();
        }
        if (mModelMyOrders.data.hasNextPage) {
            //有下一页数据
            mAdapterMyOrders.loadMoreComplete();
        } else {
            //没有下一页数据了
            mAdapterMyOrders.loadMoreEnd();
        }
    }

    //删除订单
    private void deleteOrder(String orderId) {
        DeleteOrderAPI.requestData(getContext(), orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CancelOrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CancelOrderModel cancelOrderModel) {
                        if (cancelOrderModel.success) {
                            //取消成功
                            AppHelper.showMsg(mContext, "删除订单成功");
                            // getOrderDetail(orderId, orderState, returnProductMainId);
                        } else {
                            AppHelper.showMsg(mContext, cancelOrderModel.message);
                        }
                    }
                });
    }

    //取消订单
    private void cancelOrder(String orderId) {
        CancelOrderAPI.requestData(getContext(), orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CancelOrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CancelOrderModel cancelOrderModel) {
                        if (cancelOrderModel.success) {
                            //取消成功
                            AppHelper.showMsg(mContext, "取消订单成功");
                            // mPtr.autoRefresh();
                            mPtr.autoRefresh();
                            requestOrdersList(1);

                            // getOrderDetail(orderId, orderState, returnProductMainId);
                        } else {
                            AppHelper.showMsg(mContext, cancelOrderModel.message);
                        }
                    }

                });
    }


    @Override
    public void setClickEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            requestOrdersList(1);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }
}
