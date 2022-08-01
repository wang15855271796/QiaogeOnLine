package com.puyue.www.qiaoge.activity.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.view.ChoosePopWindow;
import com.puyue.www.qiaoge.adapter.SearchOperaAdapter;
import com.puyue.www.qiaoge.adapter.home.SearchReasultAdapter;
import com.puyue.www.qiaoge.adapter.home.SearchResultAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CatePopWindow;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.event.UpDateNumEvent8;
import com.puyue.www.qiaoge.fragment.home.AllGoodsFragment;
import com.puyue.www.qiaoge.fragment.home.OperateFragment;
import com.puyue.www.qiaoge.fragment.home.UnOperateFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.PopWindowListener;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/13.
 */

public class SearchReasultActivity extends BaseSwipeActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_activity_result)
    TextView tv_activity_result;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    @BindView(R.id.rb_all)
    RadioButton rb_all;
    @BindView(R.id.rb_operate)
    RadioButton rb_operate;
    @BindView(R.id.rb_unOperate)
    RadioButton rb_unOperate;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.ll_recommend)
    LinearLayout ll_recommend;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.ll_style)
    LinearLayout ll_style;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.iv_direction)
    ImageView iv_direction;
    @BindView(R.id.ll_all_data)
    LinearLayout ll_all_data;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.ll_sale)
    LinearLayout ll_sale;
    @BindView(R.id.tv_all_data)
    TextView tv_all_data;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;
    @BindView(R.id.ll_all_choose)
    LinearLayout ll_all_choose;
    String searchWord;
    int pageNum = 1;
    int pageSize = 10;
    public View view;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    SearchResultsModel searchResultsModel;
    private String cell; // 客服电话
    private List<Fragment> mBaseFragment;
    SearchResultsModel recommendModels;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_seach_reasult);
    }

    boolean isClickOpen = false;
    int num = 1;
    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }

            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_activity_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SearchStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ll_all_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopWindow();
            }
        });
        ll_all_data.setOnClickListener(this);
        ll_sale.setOnClickListener(this);
        ll_price.setOnClickListener(this);

        smart.autoRefresh();
        smart.setEnableLoadMore(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                searchList.clear();
                pageNum = 1;
                getRecommendList();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if(recommendModels!=null && recommendModels.getData()!=null && recommendModels.getData().getSearchProd()!=null) {
                    if(recommendModels.getData().getSearchProd().isHasNextPage()) {
                        pageNum++;
                        getRecommendList();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });
    }

    List<String> list = Arrays.asList("全部", "自营", "非自营");
    ChoosePopWindow choosePopWindow;
    private void showPopWindow() {
        if(choosePopWindow==null) {
            choosePopWindow = new ChoosePopWindow(mActivity,list);
        }
        choosePopWindow.setTouchable(true);
        choosePopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        choosePopWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画
        choosePopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        choosePopWindow.setPopWindowListener(new PopWindowListener() {
            @Override
            public void getCateStyle(String cate,int position) {
//                saleDownFlag = 0;
//                priceFlag = 0;
//                tv_all_data.setTextColor(Color.parseColor("#333333"));
//                tv_sale.setTextColor(Color.parseColor("#333333"));
//                tv_price.setTextColor(Color.parseColor("#333333"));
//                iv_direction.setImageResource(R.mipmap.icon_default);
                isClickOpen = true;
                num++;
                if(isClickOpen) {
                    iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                }else {
                    iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                }

                if(position==0) {
                    isSelf = 0;
                }else if(position==1) {
                    isSelf = 1;
                }else {
                    isSelf = 2;
                }
                dialog.show();
                pageNum = 1;
                searchList.clear();
                recommendList.clear();
                if(null!=searchReasultAdapter) {
                    searchReasultAdapter.notifyDataSetChanged();
                }

                if(null!=searchResultAdapter) {
                    searchResultAdapter.notifyDataSetChanged();
                }
                getRecommendList();
                choosePopWindow.dismiss();
                tv_all.setText(list.get(position));
                tv_all.setTextColor(Color.parseColor("#FF5C00"));
            }
        });

        if(!choosePopWindow.isShowing()) {
            if(!isClickOpen) {
                iv_arrow.setImageResource(R.mipmap.icon_arrow_up);
            }else {
                iv_arrow.setImageResource(R.mipmap.ic_arrow_up);
            }
            choosePopWindow.showAsDropDown(ll_style,1300,0);
        }else {
            if(!isClickOpen) {
                iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
            }else {
                iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
            }
            choosePopWindow.dismiss();
        }
    }

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



    LinearLayout ll_no_search;
    private LoadingDailog dialog;
    @Override
    public void setViewData() {
        view = View.inflate(mContext, R.layout.item_head, null);
        ll_no_search = view.findViewById(R.id.ll_no_search);
        searchWord = getIntent().getStringExtra(AppConstant.SEARCHWORD);
        tv_activity_result.setText(searchWord);
        getCartNum();
        getCustomerPhone();
        lav_activity_loading.setVisibility(View.VISIBLE);
        lav_activity_loading.show();

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("获取数据中")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog = loadBuilder.create();

        searchReasultAdapter = new SearchReasultAdapter(R.layout.item_noresult_recommend, searchList, new SearchReasultAdapter.Onclick() {
            @Override
            public void addDialog() {

            }

            @Override
            public void getPrice() {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(searchReasultAdapter);

    }

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




    /**
     * 获取角标数据
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {
                        if (getCartNumModel.isSuccess()) {
                            if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText(getCartNumModel.getData().getNum());
                            } else {
                                tv_num.setVisibility(View.GONE);
                            }
                        } else {
                            AppHelper.showMsg(mActivity, getCartNumModel.getMessage());
                        }
                    }
                });
    }
    /**
     * 获取推荐列表
     */
    int isSelf = 0;
    int saleDownFlag = 0;
    int priceFlag = 0;
    List<SearchResultsModel.DataBean.SearchProdBean.ListBean> searchList = new ArrayList<>();
    List<SearchResultsModel.DataBean.RecommendProdBean> recommendList = new ArrayList<>();
    SearchReasultAdapter searchReasultAdapter;
    SearchResultAdapter searchResultAdapter;
    private void getRecommendList() {
        RecommendApI.requestData(mContext,searchWord,pageNum,pageSize,isSelf,saleDownFlag,priceFlag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(SearchResultsModel recommendModel) {
                        if (recommendModel.getCode()==1) {
                            smart.setEnableLoadMore(true);
                            if(recommendModel.getData()!=null) {
                                recommendModels = recommendModel;
                                lav_activity_loading.setVisibility(View.GONE);
                                dialog.dismiss();
                                if(recommendModel.getData().getSearchProd()!=null && recommendModel.getData().getSearchProd().getList()!=null && recommendModel.getData().getSearchProd().getList().size()> 0) {
                                    smart.setEnableLoadMore(true);
                                    smart.setEnableRefresh(true);
                                    searchList.addAll(recommendModel.getData().getSearchProd().getList());
                                    ll_no_search.setVisibility(View.GONE);
                                    ll_style.setVisibility(View.VISIBLE);
                                    searchReasultAdapter.notifyDataSetChanged();


                                }

                                if(recommendModel.getData().getRecommendProd()!=null && recommendModel.getData().getRecommendProd().size()>0) {
                                    smart.setEnableLoadMore(false);
                                    smart.setEnableRefresh(false);
                                    recommendList.addAll(recommendModel.getData().getRecommendProd());
                                    searchResultAdapter = new SearchResultAdapter(R.layout.item_noresult_recommend, recommendList, new SearchResultAdapter.Onclick() {
                                        @Override
                                        public void addDialog() {

                                        }

                                        @Override
                                        public void getPrice() {

                                        }
                                    });
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                    recyclerView.setAdapter(searchResultAdapter);
                                    ll_no_search.setVisibility(View.VISIBLE);
                                    searchResultAdapter.addHeaderView(view);
                                    searchResultAdapter.notifyDataSetChanged();
                                    ll_style.setVisibility(View.VISIBLE);
                                }
                            }else {
                                lav_activity_loading.setVisibility(View.GONE);
                            }
                        } else {
                            dialog.dismiss();
                            AppHelper.showMsg(mContext, recommendModel.getMessage());
                            lav_activity_loading.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void setClickEvent() {

    }

    boolean isSale;
    int isPrice = 0;
    boolean isAll;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_all_data:
                pageNum = 1;
                saleDownFlag = 0;
                priceFlag = 0;
                searchList.clear();
                recommendList.clear();
                if(null!=searchReasultAdapter) {
                    searchReasultAdapter.notifyDataSetChanged();
                }

                if(null!=searchResultAdapter) {
                    searchResultAdapter.notifyDataSetChanged();
                }

                tv_sale.setTextColor(Color.parseColor("#333333"));
                tv_price.setTextColor(Color.parseColor("#333333"));
                if(isClickOpen) {
                    iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                    tv_all.setTextColor(Color.parseColor("#FF5C00"));
                }else {
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                    tv_all.setTextColor(Color.parseColor("#333333"));
                }

                if(!isAll) {
                    tv_all_data.setTextColor(Color.parseColor("#FF5C00"));
                }else {
                    tv_all_data.setTextColor(Color.parseColor("#333333"));
                }
                isAll = !isAll;
                isSale = false;
                dialog.show();
                getRecommendList();
                if(choosePopWindow!=null) {
                    choosePopWindow.dismiss();
                }
                break;

            case R.id.ll_sale:
                pageNum = 1;
                dialog.show();
                if(choosePopWindow!=null) {
                    choosePopWindow.dismiss();
                }

                if(isClickOpen) {
                    tv_all.setTextColor(Color.parseColor("#FF5C00"));
                    iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                }else {
                    tv_all.setTextColor(Color.parseColor("#333333"));
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                }
                tv_all_data.setTextColor(Color.parseColor("#333333"));
                tv_price.setTextColor(Color.parseColor("#333333"));
                searchList.clear();
                recommendList.clear();
                if(null!=searchReasultAdapter) {
                    searchReasultAdapter.notifyDataSetChanged();
                }

                if(null!=searchResultAdapter) {
                    searchResultAdapter.notifyDataSetChanged();
                }
                if(!isSale) {
                    tv_sale.setTextColor(Color.parseColor("#FF5C00"));
                    saleDownFlag = 1;
                }else {
                    saleDownFlag = 0;
                    tv_sale.setTextColor(Color.parseColor("#333333"));
                }
                isSale = !isSale;
                isAll = false;
                getRecommendList();
                break;

            case R.id.ll_price:
                pageNum = 1;
                dialog.show();
                isPrice++;
                if(choosePopWindow!=null) {
                    choosePopWindow.dismiss();
                }

                if(isClickOpen) {
                    tv_all.setTextColor(Color.parseColor("#FF5C00"));
                    iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                }else {
                    tv_all.setTextColor(Color.parseColor("#333333"));
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                }

                tv_sale.setTextColor(Color.parseColor("#333333"));
                tv_all_data.setTextColor(Color.parseColor("#333333"));
                searchList.clear();
                recommendList.clear();
                if(null!=searchReasultAdapter) {
                    searchReasultAdapter.notifyDataSetChanged();
                }

                if(null!=searchResultAdapter) {
                    searchResultAdapter.notifyDataSetChanged();
                }

                if(isPrice%3==0) {
                    priceFlag = 0;
                    tv_price.setTextColor(Color.parseColor("#333333"));
                    iv_direction.setImageResource(R.mipmap.icon_default);
                }else if(isPrice%3==1){
                    priceFlag = 2;
                    tv_price.setTextColor(Color.parseColor("#FF5C00"));
                    iv_direction.setImageResource(R.mipmap.icon_search_up);
                }else {
                    priceFlag = 1;
                    tv_price.setTextColor(Color.parseColor("#FF5C00"));
                    iv_direction.setImageResource(R.mipmap.icon_search_down);
                }

                isAll = false;
                isSale = false;
                getRecommendList();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(choosePopWindow!=null) {
            choosePopWindow.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(UpDateNumEvent8 upDateNumEvent) {
        getCartNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotals(UpDateNumEvent7 upDateNumEvent) {
        getCartNum();
    }
}
