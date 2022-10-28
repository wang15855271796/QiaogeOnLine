package com.puyue.www.qiaoge.fragment.cart;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.frankfancode.marqueeview.MarqueeView;
import com.puyue.www.qiaoge.AutoPollRecyclerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.flow.FlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagAdapter;
import com.puyue.www.qiaoge.activity.flow.TagFlowLayout;
import com.puyue.www.qiaoge.activity.home.SearchReasultActivity;
import com.puyue.www.qiaoge.activity.mine.order.ConfirmNewOrderActivity;
import com.puyue.www.qiaoge.adapter.Must2Adapter;
import com.puyue.www.qiaoge.adapter.cart.CartAdapter;
import com.puyue.www.qiaoge.api.cart.CartBalanceAPI;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.api.cart.DeleteCartAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.mine.order.CartGetReductDescAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.FullDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.CartGoodsEvent;
import com.puyue.www.qiaoge.event.DeleteGoodsEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.BigDecimalUtils;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.cart.CartActivityGoodsModel;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.cart.CartCommonGoodsModel;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.Arith;
import com.puyue.www.qiaoge.view.AutosRecycleView;
import com.puyue.www.qiaoge.view.ScrollRecycleView;
import com.puyue.www.qiaoge.view.SlideRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/9
 */
public class CartFragments extends BaseFragment implements View.OnClickListener {
    Unbinder bind;
    @BindView(R.id.rv_cart)
    RecyclerView rv_cart;
    @BindView(R.id.cb_select_all)
    CheckBox cb_select_all;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.ll_NoData)
    LinearLayout ll_NoData;
    @BindView(R.id.iv_buy)
    ImageView iv_buy;
    @BindView(R.id.ll_go_market)
    LinearLayout ll_go_market;
    @BindView(R.id.tv_price_desc)
    TextView tv_price_desc;
    @BindView(R.id.ll_service)
    LinearLayout ll_service;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_invalid)
    TagFlowLayout rv_invalid;
    @BindView(R.id.tv_arrow)
    TextView tv_arrow;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.ll_sure)
    LinearLayout ll_sure;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.ll_unList)
    LinearLayout ll_unList;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.iv_recommend)
    ImageView iv_recommend;

    @BindView(R.id.tv_reduce)
    TextView tv_reduce;
    @BindView(R.id.tv_given)
    TextView tv_given;
    @BindView(R.id.rl_reduce)
    RelativeLayout rl_reduce;
    @BindView(R.id.rl_given)
    RelativeLayout rl_given;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    CartAdapter cartAdapter;
    private double sendAmount;
    boolean mSelect;
    private BigDecimal allPrice;
    List<Integer> cartIdList = new ArrayList<>();
    List<Integer> cartIdInList = new ArrayList<>();
    private CartActivityGoodsModel mModelCartActivityGoods = new CartActivityGoodsModel();
    private String activityBalanceVOStr = "";
    private String normalProductBalanceVOStr = "";
    CartCommonGoodsModel mModelCartCommonGoods = new CartCommonGoodsModel();
    String cartListStr;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_carts;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getCartLists();

    }

    @Override
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        iv_back.setVisibility(View.GONE);

        smart.autoRefresh();
        cb_select_all.setOnClickListener(this);
        iv_buy.setOnClickListener(this);
        ll_go_market.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getCartLists();
                getProductsList();
                getScrollData();
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (productModels.getData()!=null) {
                    if(productModels.getData().isHasNextPage()) {
                        pageNum++;
                        getProductsList();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                refreshLayout.finishLoadMore();
            }
        });

        //获取滚动数据
        getScrollData();
    }

    long start;
    @Override
    public void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
        getCartLists();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(SharedPreferencesUtil.getString(mActivity,"index").equals("3")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }
    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mActivity,10,end)
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

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }


    List<CartTestModel.DataBean.ProdsBeanX> prods = new ArrayList<>();
    List<CartTestModel.DataBean.InValidProdBean> inProds = new ArrayList<>();
    List<CartTestModel.DataBean.InValidProdBean.ProdsBean> inProdss = new ArrayList<>();
    ImageView iv_head;
    TextView tv_title;
    TextView tv_search;
    CartTestModel.DataBean data;
    List<Integer> cartIds = new ArrayList<>();
    TagAdapter unAbleAdapter;
    private void getCartLists() {
        CartListAPI.getCartsList(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartTestModel>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CartTestModel cartTestModel) {
                        if(cartTestModel.getCode()==1) {
                            if(cartTestModel.getData()!=null) {
                                data = cartTestModel.getData();
                                prods.clear();
                                inProds.clear();
                                inProdss.clear();
                                prods.addAll(data.getProds());
                                rv_cart.setLayoutManager(new LinearLayoutManager(mActivity));
                                rv_cart.setActivated(false);
                                cartAdapter = new CartAdapter(R.layout.item_cart, prods, new CartAdapter.Onclick() {
                                    @Override
                                    public void deleteItem(int pos, CartTestModel.DataBean.ProdsBeanX prodsBeanX) {
                                        int cartId = prodsBeanX.getProds().get(0).getCartId();
                                        cartIds.clear();
                                        cartIds.add(cartId);
                                        showDeleteCartDialog(0,cartIds);
                                    }
                                });

                                rv_cart.setAdapter(cartAdapter);
                                cartAdapter.notifyDataSetChanged();
                                if(data.getInValidProdBean()!=null) {
                                    if(data.getInValidProdBean().getProds()!=null) {
                                        ll_unList.setVisibility(View.VISIBLE);
                                        inProdss.addAll(data.getInValidProdBean().getProds());
                                    }else {
                                        ll_unList.setVisibility(View.GONE);
                                    }

                                    rv_invalid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {
                                            boolean isOverFlow = rv_invalid.isOverFlow();
                                            boolean isLimit = rv_invalid.isLimit();
                                            if (isLimit && isOverFlow) {
                                                tv_arrow.setVisibility(View.VISIBLE);
                                            } else {
                                                tv_arrow.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                                    tv_arrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            rv_invalid.setLimit(false);
                                            unAbleAdapter.notifyDataChanged();
                                        }
                                    });

                                }else {
                                    ll_unList.setVisibility(View.GONE);
                                }

                                unAbleAdapter = new TagAdapter<CartTestModel.DataBean.InValidProdBean.ProdsBean>(inProdss){
                                    @Override
                                    public View getView(FlowLayout parent, int position, CartTestModel.DataBean.InValidProdBean.ProdsBean inValidListBean) {
                                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_uncarts,rv_invalid, false);
                                        iv_head = view.findViewById(R.id.iv_head);
                                        tv_title = view.findViewById(R.id.tv_title);
                                        tv_search = view.findViewById(R.id.tv_search);
                                        tv_title.setText(inValidListBean.getProductName());

                                        tv_search.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(mActivity,SearchReasultActivity.class);
                                                intent.putExtra(AppConstant.SEARCHWORD,inValidListBean.getProductName());
                                                startActivity(intent);
                                            }
                                        });

                                        return view;
                                    }
                                };
                                rv_invalid.setAdapter(unAbleAdapter);
                                unAbleAdapter.notifyDataChanged();
                                //判断是否展示空数据界面
                                if(prods.size()==0&&inProdss.size()==0) {
                                    ll_NoData.setVisibility(View.VISIBLE);
                                    tv_delete.setVisibility(View.GONE);
                                    ll_sure.setVisibility(View.GONE);
                                    ll_service.setVisibility(View.GONE);
                                }else {
                                    tv_delete.setVisibility(View.VISIBLE);
                                    ll_NoData.setVisibility(View.GONE);
                                    ll_sure.setVisibility(View.VISIBLE);

                                    //计算总金额
                                    getAllPrice(prods);
                                }

                                if(prods.size()==0) {
                                    ll_sure.setVisibility(View.GONE);
                                    tv_delete.setVisibility(View.GONE);
                                }else {
                                    tv_delete.setVisibility(View.VISIBLE);
                                    ll_sure.setVisibility(View.VISIBLE);
                                }
                                //配送金额
                                sendAmount = data.getSendAmount();

                                //初始化状态
                                cartAdapter.setRefreshListener(new CartAdapter.OnRefreshListener() {
                                    @Override
                                    public void onRefresh(boolean isSelect) {
                                        mSelect = isSelect;
                                        cb_select_all.setChecked(mSelect);
                                    }
                                });
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,cartTestModel.getMessage());
                        }

                    }
                });
    }

    //计算总金额
    private void getAllPrice(List<CartTestModel.DataBean.ProdsBeanX> prods) {
        allPrice = new BigDecimal("0");
        for (int i = 0; i < prods.size(); i++) {
            List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prods1 = prods.get(i).getProds();
            for (int j = 0; j < prods1.size(); j++) {
                if(prods1.get(j).isSelected()) {
                    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> productDescVOList = prods1.get(j).getProductDescVOList();
                    for (int k = 0; k < productDescVOList.size(); k++) {
                        BigDecimal cartNum = new BigDecimal(productDescVOList.get(k).getProductNum());
                        double totals = Arith.mul(Double.parseDouble(productDescVOList.get(k).getPrice()),cartNum);
                        allPrice = allPrice.add(BigDecimal.valueOf(totals));
                    }
                }
            }
        }

        double allPrices = allPrice.doubleValue();

        //去凑单显示与否
        if(allPrices>sendAmount) {
            ll_service.setVisibility(View.GONE);
        }else {
            double diff = sendAmount - allPrices;
            double result = Double.parseDouble(String.format("%.2f", diff));
            tv_price_desc.setText(""+result);
            ll_service.setVisibility(View.VISIBLE);
        }

        tv_total_price.setText(allPrice+"");
    }

    /**
     * 删除商品
     * @param deleteGoodsEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartList(DeleteGoodsEvent deleteGoodsEvent) {
        int cartId = deleteGoodsEvent.getItem().getCartId();
        cartIds.clear();
        cartIds.add(cartId);
        showDeleteCartDialog(0,cartIds);
    }

    /**
     * 必买列表(王涛)
     * @param
     */
    int pageNum = 1;
    int pageSize = 10;
    ProductNormalModel productModels;
    Must2Adapter mustAdapter;
    List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
    private void getProductsList() {
        IndexHomeAPI.getMust2(mActivity,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductNormalModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductNormalModel getCommonProductModel) {
                        if (getCommonProductModel.isSuccess()) {
                            //为您推荐列表
                            productModels = getCommonProductModel;
                            if(getCommonProductModel.getData()!=null) {
                                iv_recommend.setVisibility(View.VISIBLE);
                            }else {
                                iv_recommend.setVisibility(View.GONE);
                            }

                            if(getCommonProductModel.getData().getList().size()>0) {
                                List<ProductNormalModel.DataBean.ListBean> lists = getCommonProductModel.getData().getList();
                                list.addAll(lists);
                                mustAdapter = new Must2Adapter(R.layout.item_team_list, list, new Must2Adapter.Onclick() {
                                    @Override
                                    public void addDialog() {
                                        getCartLists();
                                    }

                                    @Override
                                    public void tipClick() {
                                        AppHelper.ShowAuthDialog(mActivity,SharedPreferencesUtil.getString(mActivity,"mobile"));
                                    }
                                });

                                mustAdapter.notifyDataSetChanged();
                                rv_recommend.setLayoutManager(new GridLayoutManager(mActivity,2));
                                rv_recommend.setAdapter(mustAdapter);
                            }

                        } else {
                            AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取滚动数据
     *
     * @param
     */
    private void getScrollData() {
        CartGetReductDescAPI.requestCartGetReductDesc(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartGetReductModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CartGetReductModel cartGetReductModel) {
                        if(cartGetReductModel.getCode()==1) {
                            if(cartGetReductModel.getData()!=null&&cartGetReductModel.getData().size()>0) {
                                ll_scroll.setVisibility(View.VISIBLE);
                                List<CartGetReductModel.DataBean> data = cartGetReductModel.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if(data.get(i).getType()==0) {
                                        tv_reduce.setText(data.get(i).getDeductInfo());
                                    }else {
                                        tv_given.setText(data.get(i).getDeductInfo());
                                    }

                                    if(data.size()==2) {
                                        rl_reduce.setVisibility(View.VISIBLE);
                                        rl_given.setVisibility(View.VISIBLE);
                                    }else {
                                        if(data.get(i).getType()==0) {
                                            rl_reduce.setVisibility(View.VISIBLE);
                                            rl_given.setVisibility(View.GONE);
                                        }

                                        if(data.get(i).getType()==1) {
                                            rl_given.setVisibility(View.VISIBLE);
                                            rl_reduce.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                rl_reduce.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AppHelper.showFullDialog(mActivity);
                                    }
                                });

                                rl_given.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FullDialog fullDialog = new FullDialog(mActivity);
                                        fullDialog.show();
                                    }
                                });
                            }else {
                                ll_scroll.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_select_all:
                if (mSelect) {
                    mSelect = false;
                    cartAdapter.setSelectAll(false);
                } else {
                    mSelect = true;
                    cartAdapter.setSelectAll(true);
                }
                break;

            case R.id.iv_buy:
                EventBus.getDefault().post(new GoToMarketEvent());
                break;

            case R.id.ll_go_market:
                EventBus.getDefault().post(new GoToMarketEvent());
                break;


            case R.id.tv_delete:
                cartIdList.clear();
                if(prods!=null) {
                    for (int i = 0; i < prods.size(); i++) {
                        List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prod = prods.get(i).getProds();
                        for (int j = 0; j < prod.size(); j++) {
                            if(prod.get(j).isSelected()) {
                                int cartId = prod.get(j).getCartId();
                                cartIdList.add(cartId);
                            }
                        }
                    }

                    if(cartIdList.size()==0) {
                        ToastUtil.showSuccessMsg(getActivity(),"请选择要删除的商品");
                    }else {
                        showDeleteCartDialog(0,cartIdList);
                    }
                }



                break;

            case R.id.tv_clear:
                cartIdInList.clear();
                for (int i = 0; i < inProdss.size(); i++) {
                    CartTestModel.DataBean.InValidProdBean.ProdsBean prodsBean = inProdss.get(i);
                    int cartId = prodsBean.getCartId();
                    cartIdInList.add(cartId);
                }

                showClearDialog(cartIdInList);

                break;

            case R.id.btn_sure:
                mModelCartCommonGoods.amount.clear();
                mModelCartCommonGoods.productIdList.clear();
                mModelCartCommonGoods.detailList.clear();
                mModelCartActivityGoods.totalNumList.clear();
                mModelCartActivityGoods.amount.clear();
                mModelCartActivityGoods.activityIdList.clear();
                normalProductBalanceVOStr = "";
                double priceCommonGoods = 0.00;
                List<Integer> cartIds = new ArrayList<>();
                for (int i = 0; i < prods.size(); i++) {
                    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prods1 = prods.get(i).getProds();
                    for (int j = 0; j < prods1.size(); j++) {
                        if(prods1.get(j).isSelected()) {
                            List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> productDescVOList = prods1.get(j).getProductDescVOList();
                            if(prods1.get(j).getBusinessType()==1) {
                                List<CartCommonGoodsModel.DetailListBean> commonGoodsDetailList = new ArrayList<>();
                                for (int k = 0; k < productDescVOList.size(); k++) {
                                    CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean productDescVOListBean = productDescVOList.get(k);
                                    priceCommonGoods = Double.parseDouble(BigDecimalUtils.add(Double.toString(priceCommonGoods), BigDecimalUtils.mul(productDescVOListBean.getPrice(),
                                            String.valueOf(productDescVOListBean.getProductNum()), 2), 2));

                                    commonGoodsDetailList.add(new CartCommonGoodsModel.DetailListBean(productDescVOListBean.getProductCombinationPriceId(), productDescVOListBean.getProductNum()));
                                    mModelCartCommonGoods.amount.add((Double.parseDouble(BigDecimalUtils.mul(productDescVOList.get(k).getPrice(), String.valueOf(productDescVOList.get(k).getProductNum()), 2))));
                                }
                                int cartId = prods1.get(j).getCartId();
                                cartIds.add(cartId);
                                cartListStr = cartIds.toString();
                                mModelCartCommonGoods.detailList.add(commonGoodsDetailList);
                                mModelCartCommonGoods.productIdList.add(prods1.get(j).getBusinessId());

                            }else {
                                for (int k = 0; k < productDescVOList.size(); k++) {
                                    mModelCartActivityGoods.totalNumList.add(productDescVOList.get(k).getProductNum());
                                    mModelCartActivityGoods.amount.add((Double.parseDouble(BigDecimalUtils.mul(productDescVOList.get(k).getPrice(), String.valueOf(productDescVOList.get(k).getProductNum()), 2))));
                                }

                                int cartId = prods1.get(j).getCartId();
                                cartIds.add(cartId);
                                cartListStr = cartIds.toString();
                                mModelCartActivityGoods.activityIdList.add(prods1.get(j).getBusinessId());
                            }

                        }

                    }
                }

                normalProductBalanceVOStr = "";
                if (mModelCartCommonGoods.amount != null && mModelCartCommonGoods.amount.size() > 0) {
                    //统计完成,有普通商品需要结算
                    normalProductBalanceVOStr = mModelCartCommonGoods.toString();
                }

                activityBalanceVOStr = "";
                if (mModelCartActivityGoods.amount != null && mModelCartActivityGoods.amount.size() > 0) {
                    //统计完成,有活动商品需要结算
                    activityBalanceVOStr = mModelCartActivityGoods.toString();
                }

                requestCartBalance();
                break;
        }
    }

    //清空失效商品弹窗
    private void showClearDialog(List<Integer> cartIdInList) {
        //确认要删除选中的商品吗
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_cart);
        TextView mTvTitle = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_title);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_confirm);
        mTvTitle.setText("确定清空失效的商品吗?");

        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteCart(cartIdInList.toString());
                alertDialog.dismiss();
            }
        });
    }

    //删除购物车弹窗
    private void showDeleteCartDialog(int type,List<Integer> cartIdList) {
        //确认要删除选中的商品吗
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_cart);
        TextView mTvTitle = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_title);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_confirm);
        if (type == 0) {
            mTvTitle.setText("确定删除选中的商品吗?");
        }
        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (type == 0) {
                    requestDeleteCart(cartIdList.toString());
                }
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 结算
     */
    private void requestCartBalance() {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, "", 0,0,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartBalanceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        btn_sure.setEnabled(true);
                    }

                    @Override
                    public void onNext(CartBalanceModel cartBalanceModel) {
                        if (cartBalanceModel.code==1) {
                            Intent intent = new Intent(getActivity(), ConfirmNewOrderActivity.class);
                            intent.putExtra("normalProductBalanceVOStr", normalProductBalanceVOStr);
                            intent.putExtra("activityBalanceVOStr", activityBalanceVOStr);
                            intent.putExtra("cartListStr", cartListStr);
                            startActivity(intent);
                            loading.hide();
                            loading.setVisibility(View.GONE);
                            btn_sure.setEnabled(true);

                        } else {
                            loading.hide();
                            loading.setVisibility(View.GONE);
                            btn_sure.setEnabled(true);
                            ToastUtil.showSuccessMsg(mActivity, cartBalanceModel.message);
                        }
                    }
                });
    }

    /**
     * 删除购物车
     * @param cardIds
     */
    private void requestDeleteCart(String cardIds) {
        DeleteCartAPI.requestDeleteCart(getActivity(), cardIds)
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
                        if (baseModel.success) {
                            //删除成功,重新请求列表数据
                            ToastUtil.showSuccessMsg(mActivity, "删除商品成功");
                            getCartLists();
                            getCartNum();
                        } else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 更新购物车角标数量
     */
    private void getCartNum() {

        PublicRequestHelper.getCartNum(mActivity, new OnHttpCallBack<GetCartNumModel>() {
            @Override
            public void onSuccessful(GetCartNumModel getCartNumModel) {
                if (getCartNumModel.isSuccess()) {
                    if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                        ((TextView) getActivity().findViewById(R.id.tv_home_car_number)).setText(getCartNumModel.getData().getNum());
                        getActivity().findViewById(R.id.tv_home_car_number).setVisibility(View.VISIBLE);

                    } else {
                        getActivity().findViewById(R.id.tv_home_car_number).setVisibility(View.GONE);
                    }
                } else {
                    ToastUtil.showSuccessMsg(mActivity, getCartNumModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {

            }
        });
    }
    //总金额
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAllPrice(UpdateEvent updateEvent) {
        tv_total_price.setText(updateEvent.getDiscribe());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upPrice(AddressEvent event) {
        smart.autoRefresh();
    }

    //刷新列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartList(ReduceNumEvent reduceNumEvent) {
        getCartLists();
    }

    //新改
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changes(CityEvent cityEvent) {
        list.clear();
        getProductsList();
        getScrollData();
        getCartLists();
    }
    //按钮状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartState(CartGoodsEvent cartGoodsEvent) {
        cartAdapter.notifyDataSetChanged();
        List<CartTestModel.DataBean.ProdsBeanX> data = cartGoodsEvent.getData();
        for (int i = 0; i < data.size(); i++) {
            if(!data.get(i).isSelect()) {
                mSelect = false;
                return;
            }else {
                mSelect = true;
            }
        }
        cb_select_all.setChecked(mSelect);
    }
}
