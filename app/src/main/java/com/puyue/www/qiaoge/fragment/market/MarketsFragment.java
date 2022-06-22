package com.puyue.www.qiaoge.fragment.market;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.XRecyclerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.CustomPopWindow;
import com.puyue.www.qiaoge.activity.home.SearchStartActivity;

import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginEvent;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;

import com.puyue.www.qiaoge.adapter.FirstAdapter;
import com.puyue.www.qiaoge.adapter.market.MarketAlreadyGoodAdapter;
import com.puyue.www.qiaoge.adapter.market.MarketGoodBrandAdapter;
import com.puyue.www.qiaoge.adapter.market.MarketGoodsAdapter;
import com.puyue.www.qiaoge.adapter.market.MarketSecondAdapter;
import com.puyue.www.qiaoge.api.cart.ProdRecommendModel;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.BannerModel;
import com.puyue.www.qiaoge.api.market.ClassIfyModel;
import com.puyue.www.qiaoge.api.market.MarketAlreadyGoodAPI;
import com.puyue.www.qiaoge.api.market.MarketGoodNameAPI;
import com.puyue.www.qiaoge.api.market.MarketGoodSelcetAPI;
import com.puyue.www.qiaoge.api.market.MarketGoodsClassifyAPI;
import com.puyue.www.qiaoge.api.market.MarketRightModel;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.TwoDeviceHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.cart.MarketBeanModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.market.MarketAlreadyGoodModel;
import com.puyue.www.qiaoge.model.market.MarketSelectGoodModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.view.FlowLayout;
import com.puyue.www.qiaoge.view.selectmenu.MyListView;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.com.chinatelecom.account.api.CtAuth.mContext;

/**
 * Created by ${王涛} on 2019/10/30
 */
public class MarketsFragment extends BaseFragment {
    private RelativeLayout mLlSearch;
    CouponDialog couponDialog;
    //左侧列表
    private RecyclerView mRvSecond;
    //右侧列表
    private XRecyclerView mRvDetail;
    //一级分类
    private List<ClassIfyModel.DataBean> mList = new ArrayList<>();
    //左侧集合
    private List<ClassIfyModel.DataBean.SecondClassifyBean> mListSecondNow = new ArrayList<>();
    //右侧数据集合
    private List<MarketRightModel.DataBean.ProdClassifyBean.ListBean> mListGoods = new ArrayList<>();
    //品牌数据集合
    private List<MarketRightModel.DataBean.BrandProdBean.ListBeanX> mListProd = new ArrayList<>();

    //品牌推荐数据集合
    private List<String> mListRecommendProd = new ArrayList<>();

    //左侧分类Adapter
    private MarketSecondAdapter mAdapterMarketSecond;
    //右侧adapter
    private MarketGoodsAdapter mAdapterMarketDetail;
    //左侧分类model
    private ClassIfyModel mModelMarketGoodsClassify;
    //右侧model
    private MarketRightModel mModelMarketGoods;
    private boolean select = false;
    private String mFirstCode;
    private int mSecondCode;
    private int pageNum = 1;//切换一级分类和二级分类的时候都要将这个pageNum置为1
//    private LoadingDailog dialog;
    private String cell; // 客服电话
    private ImageView mIvNoData;
    private LinearLayout llDialog;
    private RecyclerView tvRvSelect;
    private LinearLayout ll_up;
    private TextView tv_blank;
    TextView tv_select_good;
    TextView tv_price;
    ImageView iv_tip;
//    private SliderLayout mViewBanner;
    private List<BannerModel.DataBean> mListBanner = new ArrayList<>();
    private LinearLayout mllMarket;
//    private RelativeLayout mRlSelectGood;
    private RecyclerView mRyGetGoodName;
    private RecyclerView mRyBuyName;
    private EditText mEtLowPrice;
    private EditText mEtHighPrice;
    private EditText mEtSearchGood;
    private TextView mTvReresh;
    AVLoadingIndicatorView lav_activity_loading;
    Banner banner;
    private TextView mTvOk;
    private ImageView ivSearch;
    private LoadingDailog dialog;
    TextView tv_search;
    TextView tv_sale;
    View v_shadow;
    private List<MarketBeanModel> mListBrand = new ArrayList<>();
    //商品名
    private MarketGoodBrandAdapter mAdapterBrand;
    private String brandName = "";
    private String selectBrandName = "";
    private String minPrice;
    private String maxPrice;
    private List<MarketAlreadyGoodModel.DataBean> mListAlreadyGood = new ArrayList<>();
    private MarketAlreadyGoodAdapter mAlreadyAdapter;
    private PopupWindow popupWindow;
    private ArrayList<View> viewList = new ArrayList<>();
    RecyclerView rv_cate;
    private String saleVolume = "";
    private String priceUp = "";
    private String newProduct = "";
    private int Imposition = 0;
    private boolean isCheck = false;
    private boolean hasPage = true;
    EditText et_goods;
    View mask;
    LinearLayout ll_select;
    LinearLayout ll_prod;
    private ProdAdapter prodAdapter;
    XRecyclerView rv_prod_detail;
    Context context;
    private PopupWindow dialog1;
    private SearchProdAdapter searchProdAdapter;
    private AlertDialog mTypedialog;
    boolean flag = false;
    LinearLayout ll_price;
    LinearLayout ll_all;
    public static MarketsFragment getInstance() {
        MarketsFragment fragment = new MarketsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void initViews(View view) {

    }


    @Override
    public void onStop() {
        super.onStop();
        if(SharedPreferencesUtil.getString(mActivity,"index").equals("2")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }

    }



    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,9,end)
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

    FirstAdapter firstAdapter;
    String priceDown;
    int styles = 0;
    int saleNum = 0;
    @Override
    public void findViewById(View view) {
        context = getActivity();

        EventBus.getDefault().register(this);
        ll_all = view.findViewById(R.id.ll_all);
        tv_select_good = view.findViewById(R.id.tv_select_good);
        iv_tip = view.findViewById(R.id.iv_tip);
        tv_price = view.findViewById(R.id.tv_price);
        ll_price = view.findViewById(R.id.ll_price);
        tv_sale = view.findViewById(R.id.tv_sale);
        rv_cate = view.findViewById(R.id.rv_cate);
        banner = view.findViewById(R.id.banner);
        tv_search = view.findViewById(R.id.tv_search);
        et_goods = view.findViewById(R.id.et_goods);
        v_shadow = view.findViewById(R.id.v_shadow);
        rv_prod_detail = view.findViewById(R.id.rv_prod_detail);
        ll_select = view.findViewById(R.id.ll_select);
        ll_prod = view.findViewById(R.id.ll_prod);
        mLlSearch = view.findViewById(R.id.ll_market_search);//搜索
        mRvSecond = ((RecyclerView) view.findViewById(R.id.rv_market_second));
        mRvDetail = ((XRecyclerView) view.findViewById(R.id.rv_market_detail));
        mIvNoData = ((ImageView) view.findViewById(R.id.iv_market_no_data));
        lav_activity_loading = view.findViewById(R.id.lav_activity_loading);
        llDialog = ((LinearLayout) view.findViewById(R.id.dialog));
        tvRvSelect = view.findViewById(R.id.recyclerView_select);
        ll_up = view.findViewById(R.id.ll_up);
        tv_blank = view.findViewById(R.id.tv_blank);
        mllMarket = view.findViewById(R.id.ll_market);
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListView();
            }
        });
        ll_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                styles++;
                pageNum =1;
                tv_price.setTextColor(Color.parseColor("#F6391A"));
                tv_sale.setTextColor(Color.parseColor("#333333"));
                if(styles%3==0) {
                    //综合
                    priceUp = "";
                    priceDown = "";
                    saleVolume = "";
                    newProduct = "";
                    selectBrandName = "";
                    minPrice = "";
                    maxPrice = "";
                    Imposition = 1;
                    iv_tip.setImageResource(R.mipmap.icon_grey);
                    sendSelectGood(saleVolume, priceUp,priceDown, newProduct, selectBrandName, minPrice, maxPrice);
                }else if(styles%3==1) {
                    //升
                    priceUp = "1";
                    priceDown = "";
                    saleVolume = "";
                    newProduct = "";
                    selectBrandName = "";
                    minPrice = "";
                    maxPrice = "";
                    Imposition = 1;
                    iv_tip.setImageResource(R.mipmap.icon_top);
                    sendSelectGood(saleVolume, priceUp,priceDown, newProduct, selectBrandName, minPrice, maxPrice);
                }else {
                    //降
                    priceUp = "";
                    priceDown = "1";
                    saleVolume = "";
                    newProduct = "";
                    selectBrandName = "";
                    minPrice = "";
                    maxPrice = "";
                    Imposition = 1;
                    iv_tip.setImageResource(R.mipmap.icon_bt);
                    sendSelectGood(saleVolume, priceUp,priceDown, newProduct, selectBrandName, minPrice, maxPrice);
                }
            }
        });
        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(saleNum%2==0) {
                    tv_sale.setTextColor(Color.parseColor("#F6391A"));
                    saleVolume = "1";
                }else {
                    tv_sale.setTextColor(Color.parseColor("#333333"));
                    saleVolume = "";
                }
                saleNum++;
                tv_price.setTextColor(Color.parseColor("#333333"));
                iv_tip.setImageResource(R.mipmap.icon_grey);
                Imposition = 2;
                priceUp = "";
                pageNum =1;
                priceDown = "";
                newProduct = "";
                minPrice = "";
                maxPrice = "";
                selectBrandName = "";
                dialog.show();
                sendSelectGood(saleVolume, priceUp,priceDown, newProduct, selectBrandName, minPrice, maxPrice);
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBrandName = et_goods.getText().toString();
                pageNum = 1;
                if(mModelMarketGoods.getData().getBrandProd().isHasNextPage()) {
                    hasPage = true;
                    getDataThree();
                }else {
                    hasPage = false;
                    getDataThree();
                }
            }
        });

        firstAdapter = new FirstAdapter(R.layout.item_icons, mList);
        rv_cate.setAdapter(firstAdapter);
        rv_cate.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

        firstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                firstAdapter.selectPosition(position);
                clicks = 1;
                selectBrandName = "";
                mAdapterMarketSecond.selectPosition(0);
                dialog.show();
                pageNum = 1;
                requestGoodsList(mList.get(position).getFirstId());
                //点击一级分类时候隐藏品牌界面
                ll_prod.setVisibility(View.GONE);
                ll_select.setVisibility(View.VISIBLE);
                if(mCustomPopWindow!=null) {
                    mCustomPopWindow.dissmiss();
                }

            }
        });
        v_shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKbTwo();
            }
        });

        et_goods.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    selectBrandName = et_goods.getText().toString();
                    pageNum = 1;
                    if(mModelMarketGoods.getData().getBrandProd().isHasNextPage()) {
                        hasPage = true;
                        getDataThree();
                    }else {
                        hasPage = false;
                        getDataThree();
                    }
                    return true;
                }
                return false;
            }
        });

        et_goods.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ProdDialog();
                    v_shadow.setVisibility(View.VISIBLE);
                }
            }
        });

        tv_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select) {
                    llDialog.setVisibility(View.GONE);

                }
                select = false;
            }
        });
        tv_blank.setBackgroundColor(Color.BLACK);
        tv_blank.getBackground().setAlpha(100);

        tv_select_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一个自定义的布局，作为显示的内容
                View contentView = LayoutInflater.from(mActivity).inflate(
                        R.layout.market_select_draw, null);
                mEtSearchGood = contentView.findViewById(R.id.et_activity_search_word);//输入商品名称
                mEtLowPrice = contentView.findViewById(R.id.et_low_price);//输入最低价
                mEtHighPrice = contentView.findViewById(R.id.rt_high_price);//输入最高价
                mRyBuyName = contentView.findViewById(R.id.ry_already_buy_good);//购买过的商品
                mRyGetGoodName = contentView.findViewById(R.id.recyclerView_search_good);//获取到的商品名
                mTvReresh = contentView.findViewById(R.id.tv_refresh_good);//重置
                mTvOk = contentView.findViewById(R.id.tv_ok);//确定
                ivSearch = contentView.findViewById(R.id.iv_search);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int width1 = mActivity.getWindowManager().getDefaultDisplay().getWidth();


                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                popupWindow = new PopupWindow(contentView, width, LinearLayout.LayoutParams.MATCH_PARENT, true);
                popupWindow.setWidth(width1 * 3 / 4);
                popupWindow.setAnimationStyle(R.style.AnimationRightFade);
//全屏
                popupWindow.setClippingEnabled(false);
                backgroundAlpha(0.3f);
//关闭事件
                popupWindow.setOnDismissListener(new popupDismissListener());

                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(mllMarket, Gravity.RIGHT, 0, 0);


                //  mEtLowPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                //  mEtHighPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                //获取商品名字
                brandName = "";
                selectBrandName = "";
                getGoodName();
                //获取购买过的商品
                getAlreadyGood();
                ivSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (StringHelper.notEmptyAndNull(mEtSearchGood.getText().toString())) {

                            brandName = mEtSearchGood.getText().toString();
                            getGoodName();
                        } else {
                            brandName = "";
                            getGoodName();
                        }
                    }
                });


                mTvReresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtSearchGood.setText("");
                        mEtLowPrice.setText("");
                        mEtHighPrice.setText("");
                        mAdapterBrand.setStat();

                    }
                });
                mTvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtSearchGood.setText("");
                        // if (isChecked && StringHelper.notEmptyAndNull(mEtLowPrice.getText().toString()) && StringHelper.notEmptyAndNull(mEtHighPrice.getText().toString())) {
                        minPrice = mEtLowPrice.getText().toString();
                        maxPrice = mEtHighPrice.getText().toString();
                        pageNum = 1;
                        getDataTwo();

                        popupWindow.dismiss();

                    }

                });
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(hidden&&SharedPreferencesUtil.getString(mActivity,"index1").equals("1")) {
            if(mCustomPopWindow!=null) {
                mCustomPopWindow.dissmiss();
            }
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }else {
            start = System.currentTimeMillis();
        }
    }



    CustomPopWindow mCustomPopWindow;
    private void shopListView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.cate_list,null);
        ImageView iv_close = contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,5));
        recyclerView.setAdapter(firstAdapter);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomPopWindow.dissmiss();
            }
        });
        //当前界面没关闭，不是售罄产品才显示
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setFocusable(false)
                .setOutsideTouchable(true)
                .setTouchable(true)
                .setView(contentView)
                .create()
                .showAsDropDown(mLlSearch);
//        mCustomPopWindow = new PopupWindow(getActivity());
//        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        View searchView = LayoutInflater.from(getActivity()).inflate(R.layout.cate_list, null);
//        ImageView iv_close = searchView.findViewById(R.id.iv_close);
//        RecyclerView recyclerView = searchView.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(mContext,5));
//        recyclerView.setAdapter(firstAdapter);
//        iv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mCustomPopWindow.dissmiss();
//                mask.setVisibility(View.GONE);
//            }
//        });
//        mCustomPopWindow.setContentView(searchView);
//        mCustomPopWindow.setBackgroundDrawable(new BitmapDrawable());
////关闭事件
//        mCustomPopWindow.setOnDismissListener(new popupDismissListener());
//        mCustomPopWindow.getBackground().setAlpha(100);
//        mCustomPopWindow.setOutsideTouchable(true);
//        mCustomPopWindow.setTouchable(true);
//        mCustomPopWindow.showAsDropDown(mLlSearch,0,0);
//
//        mCustomPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mCustomPopWindow.setOnDismissListener(new popupDismissListener());
    }

    /**
     * 推荐商品弹框列表
     */
    private void ProdDialog() {
        dialog1 = new PopupWindow(getActivity());
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        View searchView = LayoutInflater.from(getActivity()).inflate(R.layout.prod, null);
        FlowLayout fl_container = searchView.findViewById(R.id.fl_container);
        dialog1.setContentView(searchView);
        dialog1.setBackgroundDrawable(new BitmapDrawable());
        dialog1.setAnimationStyle(R.style.AnimationRightFade);
//全屏
        dialog1.setClippingEnabled(false);
//关闭事件
        dialog1.setOnDismissListener(new popupDismissListener());
        dialog1.getBackground().setAlpha(100);
        dialog1.setOutsideTouchable(true);
        dialog1.setTouchable(true);
        dialog1.showAsDropDown(et_goods,0,0);

        fl_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prodName = mListRecommendProd.get(position);
                selectBrandName = prodName;
                getDataThree();
                et_goods.setText(selectBrandName);
                dialog1.dismiss();
            }
        });
        searchProdAdapter = new SearchProdAdapter(context,mListRecommendProd);
        fl_container.setAdapter(searchProdAdapter);

    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && mActivity.getCurrentFocus() != null) {
            if (mActivity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
//            et_goods.clearFocus();
            v_shadow.setVisibility(View.GONE);
        }

    }



    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    //筛选确定
    private void sendSelectGoodTwo(String saleVolume, String priceUp,String priceDown, String newProduct, String brandName, String minPrices, String maxPrices) {
        MarketGoodSelcetAPI.getClassifyRight(mActivity, pageNum, 12, mFirstCode, mSecondCode, saleVolume, priceUp,priceDown, newProduct, brandName, minPrices, maxPrices)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarketRightModel>() {
                    @Override
                    public void onCompleted() {
                        mRvDetail.refreshComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();

                    }

                    @Override
                    public void onNext(MarketRightModel marketGoodSelectModel) {
                        if (marketGoodSelectModel.isSuccess()) {

                            flag = true;
                            mModelMarketGoods = marketGoodSelectModel;
                            dialog.dismiss();
                            updateMarketGoods();
                            lav_activity_loading.hide();

                        } else {
                            lav_activity_loading.hide();
                            AppHelper.showMsg(mActivity, marketGoodSelectModel.getMessage());
                        }
                    }
                });
    }

    //筛选确定
    private void sendSelectGood(String saleVolume, String priceUp,String priceDown, String newProduct, String brandName, String minPrices, String maxPrices) {
        MarketGoodSelcetAPI.getClassifyRight(mActivity, pageNum, 12, mFirstCode, mSecondCode, saleVolume, priceUp,priceDown, newProduct, brandName, minPrices, maxPrices)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarketRightModel>() {
                    @Override
                    public void onCompleted() {
                        mRvDetail.refreshComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();
                    }

                    @Override
                    public void onNext(MarketRightModel marketGoodSelectModel) {

                        if (marketGoodSelectModel.isSuccess()) {
                            selectBrandName = "";
                            minPrice = "";
                            maxPrice = "";
                            mModelMarketGoods = marketGoodSelectModel;
                            dialog.dismiss();
                            updateMarketGoods();
                            lav_activity_loading.hide();
                            flag = true;
                        } else {
                            AppHelper.showMsg(mActivity, marketGoodSelectModel.getMessage());
                            lav_activity_loading.hide();

                        }
                    }
                });
    }

    //获取购买过商品
    private void getAlreadyGood() {
        MarketAlreadyGoodAPI.requestMarketAlready(mActivity, mFirstCode, mSecondCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarketAlreadyGoodModel>() {
                    @Override
                    public void onCompleted() {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onNext(MarketAlreadyGoodModel marketAlreadyGoodModel) {

                        if (marketAlreadyGoodModel.isSuccess()) {
                            mListAlreadyGood.clear();
                            mListAlreadyGood.addAll(marketAlreadyGoodModel.getData());
                            showAlreadyGood(mListAlreadyGood);
                        } else {
                            AppHelper.showMsg(mActivity, marketAlreadyGoodModel.getMessage());
                        }

                    }
                });

    }

    private void showAlreadyGood(List<MarketAlreadyGoodModel.DataBean> listBeans) {

        mAlreadyAdapter = new MarketAlreadyGoodAdapter(R.layout.already_buy_good, listBeans);
        //1、RecyclerView 有自己默认的动画，去除默认动画
        mRyBuyName.setLayoutManager(new GridLayoutManager(mActivity, 2));
        //并且设置对应的adapter，设置
        ((SimpleItemAnimator) mRyBuyName.getItemAnimator()).setSupportsChangeAnimations(false);

        mAlreadyAdapter.setHasStableIds(true);
        mAlreadyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, CommonGoodsDetailActivity.class);
                int productId = listBeans.get(position).getProductId();
                intent.putExtra(AppConstant.ACTIVEID, productId);
                startActivity(intent);
            }
        });


        mRyBuyName.setAdapter(mAlreadyAdapter);

    }

    //获取商品名
    private void getGoodName() {
        MarketGoodNameAPI.requestMarketName(mActivity, mFirstCode, mSecondCode, brandName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarketSelectGoodModel>() {
                    @Override
                    public void onCompleted() {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onNext(MarketSelectGoodModel marketSelectGoodModel) {

                        if (marketSelectGoodModel.isSuccess()) {
                            mListBrand.clear();
                            for (int i = 0;i<marketSelectGoodModel.getData().size();i++){
                                MarketBeanModel bean = new MarketBeanModel(marketSelectGoodModel.getData().get(i));
                                mListBrand.add(bean);
                            }
                            if(mListBrand!=null && mListBrand.size()>0) {
                                mTvReresh.setVisibility(View.VISIBLE);
                                mTvOk.setVisibility(View.VISIBLE);
                            }else {
                                mTvReresh.setVisibility(View.GONE);
                                mTvOk.setVisibility(View.GONE);
                            }

                            showGoodBrand();

                        } else {
                            AppHelper.showMsg(mActivity, marketSelectGoodModel.getMessage());
                        }
                    }
                });

    }

    private void showGoodBrand() {
        mAdapterBrand = new MarketGoodBrandAdapter(mActivity, mListBrand);
        mRyGetGoodName.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mAdapterBrand.setOnItemClickListener(new MarketGoodBrandAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<MarketBeanModel> list) {
                selectBrandName = "";
                if (list.size() > 0) {
                    if(list.size()>1) {
                        for (int i = 0; i < list.size(); i++) {
                            selectBrandName += list.get(i).getS()+ ",";
                        }
                        selectBrandName = selectBrandName.substring(0, selectBrandName.length()-1);
                    }else {
                        selectBrandName =  list.get(0).getS();
                    }
                }
            }
        });



        mRyGetGoodName.setAdapter(mAdapterBrand);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessages(GoToMarketEvent goToMarketEvent) {

//        sendSelectGood("", "", "", "", "", "");
    }

    //新改
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityEvent(CityEvent event) {
        //刷新UI
        lav_activity_loading.show();

        getSearchProd();
//        getDataThree();
//        getDataTwo();
        getData();
        requestGoodsList("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void update(AddressEvent event) {
        //刷新数据
        lav_activity_loading.show();
        getSearchProd();
        getDataThree();
        getDataTwo();
        requestGoodsList("");

    }


    int choosePosition;
    @Override
    public void setViewData() {
        lav_activity_loading.show();

        ArrayList<String> titles = new ArrayList<>();
        titles.add("综合排序");
        ArrayList<String> contentThree = new ArrayList<>();
        contentThree.add("综合排序");
        contentThree.add("价格排序");
        contentThree.add("销量排序");
        contentThree.add("新品");
        MyListView myListView2 = new MyListView(mActivity, contentThree);
        viewList.add(0, myListView2);

        myListView2.setOnSelectListener(new MyListView.OnSelectListener() {
            @Override
            public void getValue(String value, int position) {

                pageNum = 1;
                Log.i("dda", "getValue: " + mRvSecond.getWidth() + mActivity.getWindowManager().getDefaultDisplay().getWidth());
                if (value.equals("价格排序")) {
                    priceUp = "1";
                    saleVolume = "";
                    newProduct = "";
                    selectBrandName = "";
                    minPrice = "";
                    maxPrice = "";
                    Imposition = 1;

//                    sendSelectGood(saleVolume, priceUp, newProduct, selectBrandName, minPrice, maxPrice);
                } else if (value.equals("销量排序")) {
                    saleVolume = "1";
                    Imposition = 2;
                    priceUp = "";
                    newProduct = "";
                    minPrice = "";
                    maxPrice = "";
                    selectBrandName = "";

//                    sendSelectGood(saleVolume, priceUp, newProduct, selectBrandName, minPrice, maxPrice);
                } else if (value.equals("新品")) {
                    newProduct = "1";
                    priceUp = "";
                    saleVolume = "";
                    selectBrandName = "";
                    minPrice = "";
                    Imposition = 3;
                    maxPrice = "";

//                    sendSelectGood(saleVolume, priceUp, newProduct, selectBrandName, minPrice, maxPrice);
                } else {
                    Imposition = 0;
                    newProduct = "";
                    priceUp = "";
                    saleVolume = "";
                    selectBrandName = "";
                    minPrice = "";
                    maxPrice = "";
//                    sendSelectGood("", "", "", "", "", "");
                }


            }
        });

        //获取banner
        getSearchProd();

        //切换左边导航时的加载数据弹窗
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(getContext())
                .setMessage("获取数据中")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();


        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);

        mRvSecond.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDetail.setLayoutManager(new LinearLayoutManager(getContext()));

        mRvDetail.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新
                if (isCheck) {
                    pageNum = 1;
                    hasPage  = true;
                    getDataTwo();
                } else {
                    pageNum = 1;
                    hasPage  = true;
                    getData();
                }
            }

            @Override
            public void onLoadMore() {
                if (isCheck) {
//筛选
                    if (hasPage) {
                        pageNum++;
                        getDataTwo();

                    } else {
                        hasPage = false;
                        mRvDetail.noMoreLoading();
                    }
                } else {


                    if (hasPage) {
                        pageNum++;
                        getData();

                    } else {
                        hasPage = false;
                        mRvDetail.noMoreLoading();
                    }
                }
            }
        });


        ll_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select) {
                    llDialog.setVisibility(View.GONE);

                }
                select = false;
            }
        });

        mAdapterMarketSecond = new MarketSecondAdapter(R.layout.item_left_classify, mListSecondNow);

        mAdapterMarketSecond.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //在点击二级列表的时候,需要将样式修改过来,然后刷新三级详情列表数据
                    dialog.show();
                    minPrice = "";
                    maxPrice = "";
                    selectBrandName = "";
                    mSecondCode = mListSecondNow.get(position).getSecondId();
                    getData();
                    mAdapterMarketSecond.selectPosition(position);
                    dialog.show();
                        if (flag) {
                            flag = false;
                            hintKbTwo();
                            dialog.show();
//                            selectionPositon = position;
//                            mAdapterMarketSecond.selectPosition(position);
//                            mAdapterMarketSecond.selectId("");
//                            mAdapterMarketSecond.notifyDataSetChanged();
//                            if (mList.get(position).getSecondClassify() == null) {
//                                mSecondCode = 0;
//                            } else {
//                                mSecondCode = mList.get(position).getSecondClassify().get(0).getSecondId();
//                            }
//                            mFirstCode = mList.get(position).getFirstId();
                            if(position == 2) {
                                mSecondCode = -5;
                            }
                            if (mSecondCode == -5) {
                                ll_select.setVisibility(View.GONE);
                                ll_prod.setVisibility(View.VISIBLE);
                                getDataThree();
                            } else {
                                ll_select.setVisibility(View.VISIBLE);
                                ll_prod.setVisibility(View.GONE);
                                pageNum = 1;
                                hasPage = true;
                                getData();
//                                if (isCheck) {
//                                    pageNum = 1;
//                                    hasPage = true;
//                                    getDataTwo();
//                                } else {
//                                    pageNum = 1;
//                                    hasPage = true;
//                                    getData();
//
//                                }
                            }
                        }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });


        mAdapterMarketSecond.setOnItemClickListeners(new MarketSecondAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(int position, int secondId) {
                dialog.show();
                pageNum = 1;
                minPrice = "";
                maxPrice = "";
                mSecondCode = secondId;
                selectBrandName = "";
                if (isCheck) {
                    getDataTwo();
                } else {
                    getData();
                }
            }
        });
        mAdapterMarketDetail = new MarketGoodsAdapter(R.layout.item_noresult_recommends, mListGoods, new MarketGoodsAdapter.Onclick() {
            @Override
            public void addDialog() {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(context))) {
                    if(UserInfoHelper.getUserType(getActivity()).equals(AppConstant.USER_TYPE_RETAIL)) {

                    }
                }else {
                    initDialog();
                }
            }

            @Override
            public void getPrice() {
//                showPhoneDialog(cell);
                AppHelper.ShowAuthDialog(mActivity,SharedPreferencesUtil.getString(mActivity,"mobile"));
            }
        });

        prodAdapter = new ProdAdapter(R.layout.item_prod,mListProd);
        rv_prod_detail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_prod_detail.setAdapter(prodAdapter);
        rv_prod_detail.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新
                if (isCheck) {
                    pageNum = 1;
                    getDataThree();
                    hasPage  = true;
                } else {
                    pageNum = 1;
                    getDataThree();
                    hasPage  = true;

                }
            }

            @Override
            public void onLoadMore() {
                if (isCheck) {

                    if (hasPage) {

                        pageNum++;
                        getDataThree();
                    } else {
                        hasPage = false;
                        rv_prod_detail.noMoreLoading();

                    }
                } else {

                    if (hasPage) {

                        pageNum++;

                        getDataThree();
                    } else {
                        hasPage = false;
                        rv_prod_detail.noMoreLoading();
                    }
                }
            }
        });
        mRvSecond.setAdapter(mAdapterMarketSecond);
        mRvDetail.setAdapter(mAdapterMarketDetail);

    }

    /**
     * 获取品牌名称
     */
    private void getSearchProd() {
        RecommendApI.getSearchProd(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProdRecommendModel>() {
                    @Override
                    public void onCompleted() {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onNext(ProdRecommendModel recommendModel) {
                        if (recommendModel.isSuccess()) {
                            mListRecommendProd.clear();
                            mListRecommendProd.addAll(recommendModel.getData());
                            searchProdAdapter.notifyDataSetChanged();

                        } else {
                            AppHelper.showMsg(context, recommendModel.getMessage());
                        }
                    }
                });
    }

    private void getDataThree() {
        if (Imposition == 0) {
            sendSelectGoodThree("", "", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 1) {
            sendSelectGoodThree("", "1", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 2) {
            sendSelectGoodThree("1", "", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 3) {
            sendSelectGoodThree("", "", "1","", selectBrandName, minPrice, maxPrice);
        } else {
            sendSelectGoodThree("", "", "","", selectBrandName, minPrice, maxPrice);
        }
    }

    /**
     * 品牌
     * @param saleVolume
     * @param priceUp
     * @param newProduct
     * @param brandName
     * @param minPrices
     * @param maxPrices
     */
    private void sendSelectGoodThree(String saleVolume, String priceUp, String priceDown,String newProduct, String brandName, String minPrices, String maxPrices) {
        MarketGoodSelcetAPI.getClassifyRight(mActivity, pageNum, 4, mFirstCode, mSecondCode,
                saleVolume, priceUp,priceDown, newProduct, brandName, minPrices, maxPrices)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarketRightModel>() {
                    @Override
                    public void onCompleted() {
                        rv_prod_detail.refreshComplete();
//                        ptr.refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();
                    }

                    @Override
                    public void onNext(MarketRightModel marketGoodSelectModel) {
                        mModelMarketGoods = marketGoodSelectModel;
                        if (marketGoodSelectModel.isSuccess()) {

                            flag = true;
                            dialog.dismiss();
                            upProdDate(marketGoodSelectModel);
                            lav_activity_loading.hide();
                        } else {
                            AppHelper.showMsg(mActivity, marketGoodSelectModel.getMessage());
                            lav_activity_loading.hide();
                        }
                    }
                });
    }

    /**
     * 更新品牌
     * @param marketGoodSelectModel
     */
    private void upProdDate(MarketRightModel marketGoodSelectModel) {
        if (pageNum == 1) {
            if (marketGoodSelectModel.getData().getBrandProd() != null && marketGoodSelectModel.getData().getBrandProd().getList().size()>0) {
                rv_prod_detail.setVisibility(View.VISIBLE);
                mIvNoData.setVisibility(View.GONE);
                mListProd.clear();
                mListProd.addAll(marketGoodSelectModel.getData().getBrandProd().getList());
                prodAdapter.notifyDataSetChanged();
            } else {
                rv_prod_detail.setVisibility(View.GONE);
                mIvNoData.setVisibility(View.VISIBLE);

            }
        } else {

            mListProd.addAll(marketGoodSelectModel.getData().getBrandProd().getList());
            prodAdapter.notifyDataSetChanged();

        }


        if (marketGoodSelectModel.getData().getBrandProd().isHasNextPage()) {
            hasPage = true;
        } else {
            hasPage = false;
        }


    }



    private void getDataTwo() {
        isCheck = true;
        if (Imposition == 0) {
            sendSelectGoodTwo("", "", "", "",selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 1) {
            sendSelectGoodTwo("", "1", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 2) {
            sendSelectGoodTwo("1", "", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 3) {
            sendSelectGoodTwo("", "", "1","", selectBrandName, minPrice, maxPrice);
        } else {
            sendSelectGoodTwo("", "", "","", selectBrandName, minPrice, maxPrice);
        }
    }

    private void getData() {
        isCheck = false;
        if (Imposition == 0) {
            sendSelectGood("", "", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 1) {
            sendSelectGood("", "1", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 2) {
            sendSelectGood("1", "", "","", selectBrandName, minPrice, maxPrice);
        } else if (Imposition == 3) {
            sendSelectGood("", "", "1","", selectBrandName, minPrice, maxPrice);
        } else {
            sendSelectGood("", "", "", "",selectBrandName, minPrice, maxPrice);
        }
    }


    /**
     * 请求左侧数据集合
     */
    private void requestGoodsList(String fromId) {
        MarketGoodsClassifyAPI.getClassify(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ClassIfyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ClassIfyModel marketGoodsModel) {
                        mModelMarketGoodsClassify = marketGoodsModel;
                        if (mModelMarketGoodsClassify.isSuccess()) {
                            updateGoodsList(fromId);

                        } else {
                            if (marketGoodsModel.getCode() == AppConstant.ANOTHER_PLACE_LOGIN) {
                                TwoDeviceHelper.logoutAndToHome(getActivity());
                            } else {
                                AppHelper.showMsg(mActivity, mModelMarketGoodsClassify.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化列表数据
     */
    int clicks = 0;
    private void updateGoodsList(String fromId) {
        dialog.show();
        if(clicks == 0) {
            if(fromId.equals("")) {
                mList.clear();
                mList.addAll(mModelMarketGoodsClassify.getData());
                mListSecondNow.clear();
                mListSecondNow.addAll(mModelMarketGoodsClassify.getData().get(0).getSecondClassify());
                mAdapterMarketSecond.notifyDataSetChanged();
                mAdapterMarketSecond.selectPosition(0);
                firstAdapter.notifyDataSetChanged();
                mFirstCode = mModelMarketGoodsClassify.getData().get(0).getFirstId();
                mSecondCode  = -1;
                getData();

            }else {
                mList.clear();
                mList.addAll(mModelMarketGoodsClassify.getData());
                mListSecondNow.clear();

                for (int i = 0; i < mModelMarketGoodsClassify.getData().size(); i++) {
                    if(mModelMarketGoodsClassify.getData().get(i).getFirstId().equals(fromId)) {
                        mListSecondNow.addAll(mModelMarketGoodsClassify.getData().get(i).getSecondClassify());
                    }
                }
                mAdapterMarketSecond.selectPosition(0);
                mAdapterMarketSecond.notifyDataSetChanged();
                mFirstCode = fromId;
                mSecondCode = -1;
                for (int i = 0; i < mList.size(); i++) {
                    if(fromId.equals(mList.get(i).getFirstId())) {
                        firstAdapter.selectPosition(i);
                        rv_cate.scrollToPosition(i);
//                        rv_cate.smoothScrollToPosition(i);
                    }
                }
                getData();

            }
        }else {

            if(!fromId.equals("")) {
                mList.clear();
                mList.addAll(mModelMarketGoodsClassify.getData());
                mListSecondNow.clear();
                for (int i = 0; i < mModelMarketGoodsClassify.getData().size(); i++) {
                    if(mModelMarketGoodsClassify.getData().get(i).getFirstId().equals(fromId)) {
                        mListSecondNow.addAll(mModelMarketGoodsClassify.getData().get(i).getSecondClassify());
                    }
                }
                mAdapterMarketSecond.selectPosition(0);
                mAdapterMarketSecond.notifyDataSetChanged();
                firstAdapter.notifyDataSetChanged();
                mFirstCode = fromId;
                mSecondCode  = -1;
                for (int i = 0; i < mList.size(); i++) {
                    if(fromId.equals(mList.get(i).getFirstId())) {
                        rv_cate.smoothScrollToPosition(i);
                        firstAdapter.selectPosition(i);
                    }
                }
                getData();
            }else {

                mList.clear();
                mList.addAll(mModelMarketGoodsClassify.getData());
                mListSecondNow.clear();
                mListSecondNow.addAll(mModelMarketGoodsClassify.getData().get(0).getSecondClassify());
                mAdapterMarketSecond.selectPosition(0);
                mAdapterMarketSecond.notifyDataSetChanged();
                firstAdapter.notifyDataSetChanged();
                mFirstCode = mModelMarketGoodsClassify.getData().get(0).getFirstId();
                mSecondCode  = -1;
                firstAdapter.selectPosition(0);
                getData();
            }

        }

    }


    /**
     * 更新数据
     */
    private void updateMarketGoods() {

        if (pageNum == 1) {
            if (mModelMarketGoods.getData().getProdClassify() != null && mModelMarketGoods.getData().getProdClassify().getList().size() > 0) {
                mRvDetail.setVisibility(View.VISIBLE);
                mIvNoData.setVisibility(View.GONE);
                mListGoods.clear();
                mListGoods.addAll(mModelMarketGoods.getData().getProdClassify().getList());
                mAdapterMarketDetail.notifyDataSetChanged();

            } else {
                mRvDetail.setVisibility(View.GONE);
                mIvNoData.setVisibility(View.VISIBLE);

            }
        } else {
            mListGoods.addAll(mModelMarketGoods.getData().getProdClassify().getList());
            mAdapterMarketDetail.notifyDataSetChanged();

        }

        if (mModelMarketGoods.getData().getProdClassify().isHasNextPage()) {
            hasPage = true;
        } else {
            hasPage = false;
        }

    }

    @Override
    public void setClickEvent() {

        mLlSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                Intent intent = new Intent(getActivity(), SearchStartActivity.class);
                intent.putExtra(AppConstant.SEARCHTYPE, AppConstant.HOME_SEARCH);
                intent.putExtra("flag", "first");
                intent.putExtra("good_buy","");
                getActivity().startActivity(intent);
            }
        });

    }

    long start;
    @Override
    public void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
        String userMarketRefresh = UserInfoHelper.getUserMarketRefresh(getContext());
//        dialog.show();
        if (StringHelper.notEmptyAndNull(userMarketRefresh)) {

        } else {
            pageNum = 1;

            UserInfoHelper.saveUserMarketRefresh(getContext(), "market_has_refresh");
        }


//        mViewBanner.startAutoCycle(3000, 8000, true);

    }

    /**
     * 弹出电话号码
     */
    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
        tv_phone.setText("客服热线 ("+cell+")");

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(mContext);
                mDialog.dismiss();
            }
        });
    }


    /**
     * 获取客服电话
     */
    private void getCustomerPhone() {
        PublicRequestHelper.getCustomerPhone(mActivity, new OnHttpCallBack<GetCustomerPhoneModel>() {
            @Override
            public void onSuccessful(GetCustomerPhoneModel getCustomerPhoneModel) {
                if (getCustomerPhoneModel.isSuccess()) {
                    cell = getCustomerPhoneModel.getData();
                } else {
                    AppHelper.showMsg(mActivity, getCustomerPhoneModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {
            }
        });
    }


    List<String> list = new ArrayList<>();

    List<String> list1 = new ArrayList<>();
    public interface onClick {
        void refreshCartNum(int pos);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(LoginEvent event) {
        //刷新UI
        requestGoodsList("");
        getData();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(LogoutEvent event) {
        //刷新UI
        requestGoodsList("");
        getData();


    }

//    登录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginsEvent(LogoutsEvent event) {
        //刷新UI
        requestGoodsList("");
        getData();


    }

    String fromId;
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void change(FromIndexEvent fromIndexEvent) {
        fromId = fromIndexEvent.getId();
        requestGoodsList(fromId);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void change(BackEvent fromIndexEvent) {
//        requestGoodsList(fromId);
//        getData();
//        getCustomerPhone();
//
//    }
    /**
     * 提示用户去登录还是注册的弹窗
     */
    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
//                startActivity(RegisterActivity.getIntent(mActivity, RegisterMessageActivity.class));
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }
}
