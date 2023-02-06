package com.puyue.www.qiaoge.fragment.mine.order;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.adapter.MyOrdersItemAdapter1;
import com.puyue.www.qiaoge.adapter.mine.MyOrdersItemAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.order.CopyToCartAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderListAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.HuoConnentionDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.HasConnectModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.model.mine.order.CopyToCartModel;
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


/**
 * Created by Administrator on 2018/4/21.
 */
//退货订单
public class ReturnOrderFragment extends BaseFragment {
    private PtrClassicFrameLayout mPtr;
    private RecyclerView mRv;
    private MyOrdersItemAdapter1 mAdapterMyOrders;
    private String mType;
    private int pageNum = 1;
    private ImageView mIvNoData;
    private OrdersModel mModelMyOrders;
    private List<OrdersModel.DataBean.ListBean> mListResult = new ArrayList<>();
    private CopyToCartModel mModelCopyToCart;

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
        mListResult.clear();
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
                requestOrdersList(11);
            }
        });
        if (orderDeliveryType==0){
            mAdapterMyOrders = new MyOrdersItemAdapter1(R.layout.item_my_order1, mListResult, 11,orderDeliveryType, new MyOrdersItemAdapter.OnClick() {
                @Override
                public void callHuo(int deliveryMode, String orderId, String hllOrderId) {
                    HasConnect(orderId,hllOrderId);
                }

                @Override
                public void evaluateNowOnclick(int position,String orderId) {

                }

                @Override
                public void againBayOnclick(int position) {
                    OrdersModel.DataBean.ListBean listBean = mListResult.get(position);
                    requestCopyToCart(listBean.orderId);
                }

                @Override
                public void cancelOnclick(String orderId) {

                }

                @Override
                public void deleteOnclick(String orderId) {

                }

                @Override
                public void imageGo(String orderId, String payAmount) {

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
            mAdapterMyOrders = new MyOrdersItemAdapter1(R.layout.item_my_order1, mListResult, 11, orderDeliveryType,new MyOrdersItemAdapter.OnClick() {


                @Override
                public void callHuo(int deliveryMode, String orderId, String hllOrderId) {
                    HasConnect(orderId,hllOrderId);
                }

                @Override
                public void evaluateNowOnclick(int position,String orderId) {

                }

                @Override
                public void againBayOnclick(int position) {
                    OrdersModel.DataBean.ListBean listBean = mListResult.get(position);
                    requestCopyToCart(listBean.orderId);
                }

                @Override
                public void cancelOnclick(String orderId) {

                }

                @Override
                public void deleteOnclick(String orderId) {

                }

                @Override
                public void imageGo(String orderId, String payAmount) {

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
                requestOrdersList(11);
            }
        });
//        requestOrdersList(11);
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
//                        Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(OrdersModel myOrdersModel) {
                        logoutAndToHome(getContext(), myOrdersModel.code);
                        mPtr.refreshComplete();
                        mModelMyOrders = myOrdersModel;
                        if (mModelMyOrders.success) {
                            updateOrderList();
                        } else {
                            AppHelper.showMsg(getContext(), mModelMyOrders.message);
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
                            requestOrdersList(11);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                            huoConnentionDialog.dismiss();
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }


    // 添加到购物车
    private void requestCopyToCart(String orderId) {
        CopyToCartAPI.requestCopyToCart(mActivity, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CopyToCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CopyToCartModel copyToCartModel) {
                        mModelCopyToCart = copyToCartModel;
                        if (mModelCopyToCart.success) {
                            //将订单内的商品加入购物车
                            AppHelper.showMsg(mActivity, mModelCopyToCart.message);
                        } else {
                            AppHelper.showMsg(mActivity, mModelCopyToCart.message);
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

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            requestOrdersList(11);
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
