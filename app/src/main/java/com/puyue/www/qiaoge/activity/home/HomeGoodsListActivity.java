package com.puyue.www.qiaoge.activity.home;

import android.animation.IntEvaluator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.DensityUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveNewAdapter;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveQueryAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.home.SecKillMoreListAPI;
import com.puyue.www.qiaoge.api.home.SpikeActiveQueryAPI;
import com.puyue.www.qiaoge.api.home.SpikeNewActiveQueryAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.calendar.utils.SelectBean;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.fragment.cart.ChangeStatEvent;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.fragment.home.NewAdapter;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.FVHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.SeckillListModel;
import com.puyue.www.qiaoge.model.home.SpikeNewQueryModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.view.CustomAppbarLayout;
import com.puyue.www.qiaoge.view.MyScrollView2;
import com.puyue.www.qiaoge.view.OutScollerview;
import com.puyue.www.qiaoge.view.ScrollViewListeners;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView1;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViewss;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/17.
 */
//列表
public class HomeGoodsListActivity extends BaseActivity {

    Unbinder bind;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_num1)
    TextView tv_num1;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_team)
    com.puyue.www.qiaoge.view.MarqueeView rv_team;
    @BindView(R.id.rv_discount)
    com.puyue.www.qiaoge.view.MarqueeView rv_discount;
    @BindView(R.id.tv_skill_time1)
    TextView tv_skill_time1;
    @BindView(R.id.rv_full)
    com.puyue.www.qiaoge.view.MarqueeView rv_full;
    @BindView(R.id.appbar)
    CustomAppbarLayout appbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;

    @BindView(R.id.rl_grand)
    RelativeLayout rl_grand;
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;
    @BindView(R.id.mRvSpikeData1)
    RecyclerView mRvSpikeData1;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.ll_parent_top)
    RelativeLayout ll_parent_top;

    @BindView(R.id.rv_skill)
    com.puyue.www.qiaoge.view.MarqueeView rv_skill;
    @BindView(R.id.rv_team1)
    com.puyue.www.qiaoge.view.MarqueeView rv_team1;
    @BindView(R.id.ll_discount1)
    LinearLayout ll_discount1;
    @BindView(R.id.ll_full1)
    LinearLayout ll_full1;
    @BindView(R.id.rv_full1)
    com.puyue.www.qiaoge.view.MarqueeView rv_full1;
    @BindView(R.id.rv_discount1)
    com.puyue.www.qiaoge.view.MarqueeView rv_discount1;
    @BindView(R.id.ll_full)
    LinearLayout ll_full;
    @BindView(R.id.ll_discount)
    LinearLayout ll_discount;
    @BindView(R.id.ll_team)
    LinearLayout ll_team;
    @BindView(R.id.ll_team1)
    LinearLayout ll_team1;

    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.tv_full_desc1)
    TextView tv_full_desc1;
    @BindView(R.id.tv_team_desc)
    TextView tv_team_desc;
    @BindView(R.id.tv_team_desc1)
    TextView tv_team_desc1;
    @BindView(R.id.tv_discount_desc)
    TextView tv_discount_desc;
    @BindView(R.id.tv_discount_desc1)
    TextView tv_discount_desc1;
    @BindView(R.id.ll_two)
    LinearLayout ll_two;
    @BindView(R.id.ll_one)
    LinearLayout ll_one;
    @BindView(R.id.iv_huo_company)
    ImageView iv_huo_company;

    @BindView(R.id.ll_hot)
    LinearLayout ll_hot;
    @BindView(R.id.my_scroll)
    MyScrollView2 my_scroll;

    @BindView(R.id.ll_city)
    LinearLayout ll_city;

    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.mRvSpikeData)
    RecyclerView mRvSpikeData;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.snap)
    SnapUpCountDownTimerViewss snap;
    @BindView(R.id.snap10)
    SnapUpCountDownTimerViewss snap10;
    @BindView(R.id.tv_desc1)
    TextView tv_desc1;
    @BindView(R.id.tv_start_time1)
    TextView tv_start_time1;
    @BindView(R.id.rv_activity_goods_list)
    RecyclerView mRvData;
    @BindView(R.id.iv_activity_back)
    ImageView iv_activity_back;
    @BindView(R.id.iv_back_title)
    ImageView iv_back_title;
    @BindView(R.id.iv_back_title1)
    ImageView iv_back_title1;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.rl_good_cart1)
    RelativeLayout rl_good_cart1;
    @BindView(R.id.rootview)
    FrameLayout rootview;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 72;
    private int evaluatemargin;
    private int evaluatetop;
    private RelativeLayout.LayoutParams layoutParams;
    int scrollLength;

    IndexRecommendAdapter indexRecommendAdapter;

    private String cell; // 客服电话


    //首页顶部推荐集合
    private List<String> recommendList = new ArrayList<>();
    int PageNum = 1;
    private AlertDialog mTypedialog;
    int topHeight;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test5);
    }

    int pageNum = 1;
    int pageSize = 10;
    @Override
    public void findViewById() {
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        rl_grand.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_grand.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int bar_height = rl_bar.getHeight();
                int scroll_height = ll_scroll.getHeight();
                scrollLength = Math.abs(scroll_height - bar_height);
                topHeight = DensityUtil.dip2px(scrollLength, mActivity);
                appbar.post(new Runnable() {
                    @Override public void run() {
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
                        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();

                        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                            @Override
                            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                                return true;
                            }
                        });
                    }
                });

                appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int y) {
//                        long currentTime1 = System.currentTimeMillis();
                        int abs = Math.abs(y);
                        int totalScrollRange = appBarLayout.getTotalScrollRange()-262;

                        if (abs>=totalScrollRange) {
                            ll_parent_top.setVisibility(View.VISIBLE);

                        } else {
                            ll_parent_top.setVisibility(View.INVISIBLE);

                        }


                        //滑动距离小于顶部栏从透明到不透明所需的距离
                        if ((scrollLength - abs) > 0) {
                            //估值器
                            IntEvaluator evaluator = new IntEvaluator();
                            float percent = (float) (scrollLength - abs) / scrollLength;

                            if (percent <= 1) {
                                //透明度

                                rl_bar.setAlpha(1-percent);

                                //搜索栏左右margin值
                                evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(ENDMARGINLEFT,mActivity), DensityUtil.dip2px(STARTMARGINLEFT,mActivity));
                                //搜索栏顶部margin值
                                evaluatetop = evaluator.evaluate(percent,  DensityUtil.dip2px(ENDMARGINTOP,mActivity), DensityUtil.dip2px(STARTMARGINTOP,mActivity));

                                layoutParams = (RelativeLayout.LayoutParams) rl_search.getLayoutParams();
                                layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                                if(evaluatetop<100) {
                                    layoutParams.setMargins(evaluatemargin, 85, evaluatemargin, 0);
                                    rl_search.requestLayout();
                                }else {
                                    layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                                    rl_search.requestLayout();
                                }



                                rl_search.requestLayout();
                            }
                        } else {
                            rl_bar.setAlpha(1);
                            if(layoutParams!=null){
                                layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,mActivity),85, DensityUtil.dip2px(ENDMARGINLEFT,mActivity), 0);
                                rl_search.requestLayout();

                            }
                        }

                    }
                });
            }
        });

        indexRecommendAdapter = new IndexRecommendAdapter(R.layout.item_index_recommend, recommendList);

        indexRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, SearchReasultActivity.class);
                intent.putExtra(AppConstant.SEARCHWORD,recommendList.get(position));
                startActivity(intent);
            }
        });

        refreshLayout.autoRefresh();
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PageNum = 1;
                num = 0;
//                getNewSpikeTool();
                refreshLayout.finishRefresh();
            }
        });

        iv_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_back_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_good_cart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }
            }
        });

        iv_back_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }
            }
        });

//        getProductsList1(pageNum,pageSize,"new");
//        newAdapter = new NewAdapter(R.layout.item_team_list, list, new NewAdapter.Onclick() {
//            @Override
//            public void addDialog() {
//
//            }
//
//            @Override
//            public void tipClick() {
//
//            }
//        });

//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(newAdapter);


//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                if (productNormalModel.getData()!=null) {
//                    if(productNormalModel.getData().isHasNextPage()) {
//                        pageNum++;
//                        getProductsList1(pageNum, 10,"new");
//                        refreshLayout.finishLoadMore();
//
//                    }else {
//                        refreshLayout.finishLoadMoreWithNoMoreData();
//                    }
//                }
//                refreshLayout.finishLoadMore();
//            }
//        });
    }

    ProductNormalModel productNormalModel;
    private NewAdapter newAdapter;
    //新品集合
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
//    private void getProductsList1(int pageNums, int pageSize, String type) {
//
//        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
//
//        }else {
//
//            ProductListAPI.requestData(mActivity, pageNums, pageSize,type,null)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<ProductNormalModel>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(ProductNormalModel getCommonProductModel) {
//                            productNormalModel = getCommonProductModel;
//                            if (getCommonProductModel.isSuccess()) {
//                                newAdapter.notifyDataSetChanged();
//                                if(getCommonProductModel.getData().getList().size()>0) {
//                                    list.addAll(getCommonProductModel.getData().getList());
//                                    newAdapter.notifyDataSetChanged();
//
//                                    recyclerView.setVisibility(View.VISIBLE);
//                                }else {
//
//                                    recyclerView.setVisibility(View.GONE);
//                                }
//                                refreshLayout.setEnableLoadMore(true);
//                            }
//                            else {
//                                AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());
//                            }
//                        }
//                    });
//        }
//    }

    @Override
    public void setViewData() {
        getNewSpikeTool();
        getCartNum();
        getCustomerPhone();
    }

    @Override
    public void setClickEvent() {

    }



    int num = 0;
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
     * 秒杀专区更多-顶部
     */
    private List<SpikeNewQueryModel.DataBean> mListSpikeNew = new ArrayList<>();
    SpikeActiveNewAdapter mAdapterNewSpike;
    private int currentPosition = 0;
    long startTime1;
    long endTime1;
    long currentTime1;
    private void getNewSpikeTool() {
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {

        }else {
            SpikeNewActiveQueryAPI.requestData(mContext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SpikeNewQueryModel>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
                            if (spikeNewQueryModel.isSuccess()) {
                                mListSpikeNew.clear();
                                if (spikeNewQueryModel.getData() != null) {
                                    mListSpikeNew.addAll(spikeNewQueryModel.getData());
                                    //秒杀专区-顶部
                                    mAdapterNewSpike = new SpikeActiveNewAdapter(mContext, mListSpikeNew);
                                    mRvSpikeData.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                                    mRvSpikeData.setAdapter(mAdapterNewSpike);

                                    mRvSpikeData1.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                                    mRvSpikeData1.setAdapter(mAdapterNewSpike);


                                    List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
                                    if(data.get(currentPosition).getFlag()==1) {
                                        tv_desc.setText("距离本场活动结束");
                                        tv_desc1.setText("距离本场活动结束");
                                    }else {
                                        tv_desc.setText("距离本场活动开始");
                                        tv_desc1.setText("距离本场活动开始");
                                    }
                                    tv_start_time.setText("活动于"+data.get(currentPosition).getDateTime()+"开始");
                                    tv_start_time1.setText("活动于"+data.get(currentPosition).getDateTime()+"开始");
                                    long startTime = data.get(currentPosition).getStartTime();
                                    long endTime = data.get(currentPosition).getEndTime();
                                    long currentTime = data.get(currentPosition).getCurrentTime();
                                    snap.setTime(false, currentTime, startTime,endTime);
                                    snap.start();

                                    startTime1 = data.get(currentPosition).getStartTime();
                                    endTime1 = data.get(currentPosition).getEndTime();
                                    currentTime1 = data.get(currentPosition).getCurrentTime();

                                    snap10.setTime(false, currentTime1, startTime1,endTime1);
                                    snap10.start();
                                    mAdapterNewSpike.setOnItemClickListener(new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            getNewSpikeTwo(position);
                                            tv_start_time.setText("活动于"+data.get(position).getDateTime()+"开始");
                                            tv_start_time1.setText("活动于"+data.get(position).getDateTime()+"开始");
                                        }

                                        @Override
                                        public void onItemLongClick(View view, int position) {

                                        }
                                    });


                                    mAdapterNewSpike.setOnItemClickListener(new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            getNewSpikeTwo(position);
                                            tv_start_time.setText("活动于"+data.get(position).getDateTime()+"开始");
                                            tv_start_time1.setText("活动于"+data.get(position).getDateTime()+"开始");
                                        }

                                        @Override
                                        public void onItemLongClick(View view, int position) {

                                        }
                                    });

                                    //请求列表数据
                                    spikeActiveQuery(spikeNewQueryModel.getData().get(currentPosition).getActiveId());
                                }


                            } else {
                                AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
                            }

                        }
                    });
        }

    }

    private void getNewSpikeTwo(int pos) {
        SpikeNewActiveQueryAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SpikeNewQueryModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
                        if (spikeNewQueryModel.isSuccess()) {
                            if (spikeNewQueryModel.getData() != null) {
                                List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
                                mAdapterNewSpike.selectPosition(pos);
                                spikeActiveQuery(mListSpikeNew.get(pos).getActiveId());
                                currentPosition = pos;
                                if(data.get(currentPosition).getFlag()==1) {
                                    tv_desc.setText("距离本场活动结束");
                                    tv_desc1.setText("距离本场活动结束");
                                }else {
                                    tv_desc.setText("距离本场活动开始");
                                    tv_desc1.setText("距离本场活动开始");
                                }
                                long startTime = data.get(currentPosition).getStartTime();
                                long endTime = data.get(currentPosition).getEndTime();
                                long currentTime = data.get(currentPosition).getCurrentTime();
                                snap.setTime(false, currentTime, startTime,endTime);
                                snap.start();

                                long startTime1 = data.get(currentPosition).getStartTime();
                                long endTime1 = data.get(currentPosition).getEndTime();
                                long currentTime1 = data.get(currentPosition).getCurrentTime();
                                snap10.setTime(false, currentTime1, startTime1,endTime1);
                                snap10.start();

                                mAdapterNewSpike.notifyDataSetChanged();
                            }
                        } else {
                            AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
                        }

                    }
                });
    }
    /**
     * 秒杀-更多-列表
     */
    SpikeActiveQueryAdapter mAdapterSpikeQuery;
    //秒杀列表
    private List<SeckillListModel.DataBean.KillsBean> mListSeckill = new ArrayList<>();
    private void spikeActiveQuery(int activeId) {
        SecKillMoreListAPI.requestMoreListData(mContext, activeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SeckillListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SeckillListModel seckillListModel) {
                        if (seckillListModel.success) {
                            mAdapterSpikeQuery = new SpikeActiveQueryAdapter(R.layout.spike_new_active_product, seckillListModel.data.kills, activeId, new SpikeActiveQueryAdapter.Onclick() {
                                @Override
                                public void tipClick() {
                                    AppHelper.ShowAuthDialog(mActivity,cell);
                                }
                            });

                            mAdapterSpikeQuery.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID, mListSeckill.get(position).activeId);
                                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("num","-1");
                                    startActivity(intent);
                                }
                            });

                            mRvData.setAdapter(mAdapterSpikeQuery);
                            mRvData.setLayoutManager(new LinearLayoutManager(mContext));
                            mAdapterSpikeQuery.notifyDataSetChanged();
                            mListSeckill.clear();
                            if (seckillListModel.data.kills != null) {
                                mListSeckill.addAll(seckillListModel.data.kills);
                            }

                            int flag = seckillListModel.data.flag;
                            UserInfoHelper.saveSpikePosition(mContext, String.valueOf(flag));

                        } else {

                            AppHelper.showMsg(mActivity, seckillListModel.message);

                        }
                    }
                });
    }

    int couponNum = 0;


    /**
     * 购物车数量
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mContext)
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
                            if(getCartNumModel.getData().getNum().equals("0")) {
                                tv_num.setVisibility(View.GONE);
                                tv_num1.setVisibility(View.GONE);
                            }else {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num1.setVisibility(View.VISIBLE);
                                tv_num.setText(getCartNumModel.getData().getNum());
                                tv_num1.setText(getCartNumModel.getData().getNum());
                            }
                        } else {
                            AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                        }
                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(ReduceNumEvent event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(NumEvent event) {
        getCartNum();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

//    public TextView tv_num;
//    TextView tv_num1;
//    private ImageView mIvBack;
//    private RecyclerView mRvData;
//    private RecyclerView mRvSpikeData;
//    //秒杀预告，特惠
//    private RelativeLayout linearLayoutSpike;
//    //秒杀活动
//    private SpikeActiveNewAdapter mAdapterNewSpike;
//    private List<SpikeNewQueryModel.DataBean> mListSpikeNew = new ArrayList<>();
//
//    private SpikeActiveQueryAdapter mAdapterSpikeQuery;
//    //秒杀列表
//    private List<SeckillListModel.DataBean.KillsBean> mListSeckill = new ArrayList<>();
//    private int currentPosition = 0;
//    private RelativeLayout rl_good_cart;
//    RelativeLayout rl_good_cart1;
//    SnapUpCountDownTimerViewss snap;
//    TextView tv_desc;
//    TextView tv_start_time;
//    NestedScrollView scoller;
////    ImageView iv_back;
//    ImageView iv_back_title;
//    ImageView iv_back_title1;
//    ImageView iv_back1;
//    ImageView iv_title1;
//    ImageView iv_cart;
//    RelativeLayout ll_header1;
//    ImageView iv_404;
//    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        handleExtra(savedInstanceState);
//        super.onCreate(savedInstanceState);
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        long end = (System.currentTimeMillis()-currentTime)/1000;
//        long time = Time.getTime(end);
//        getDatas(time);
//
//    }
//
//    private void getDatas(long end) {
//        RecommendApI.getDatas(mContext,4,end)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseModel baseModel) {
//
//                    }
//                });
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.activity_home_goods_list);
//    }
//
//
//    @Override
//    public void findViewById() {
//        iv_404 =  FVHelper.fv(this, R.id.iv_404);
////        iv_back = FVHelper.fv(this, R.id.iv_back);
//        rl_good_cart1 = FVHelper.fv(this, R.id.rl_good_cart1);
//        iv_back1 = FVHelper.fv(this, R.id.iv_back1);
//        iv_back_title = FVHelper.fv(this, R.id.iv_back_title);
//        iv_back_title1 = FVHelper.fv(this, R.id.iv_back_title1);
//        scoller = FVHelper.fv(this, R.id.scoller);
//        tv_start_time = FVHelper.fv(this, R.id.tv_start_time);
//        tv_desc = FVHelper.fv(this, R.id.tv_desc);
//        snap = FVHelper.fv(this, R.id.snap);
//        mIvBack = FVHelper.fv(this, R.id.iv_activity_back);
//        tv_num = FVHelper.fv(this, R.id.tv_num);
//        tv_num1 = FVHelper.fv(this, R.id.tv_num1);
//
//        mRvData = FVHelper.fv(this, R.id.rv_activity_goods_list);
//        linearLayoutSpike = FVHelper.fv(this, R.id.linearLayout_spike);
//        mRvSpikeData = FVHelper.fv(this, R.id.recyclerview_spike_content);
//        rl_good_cart = FVHelper.fv(this, R.id.rl_good_cart);
//        ll_header1 = FVHelper.fv(this, R.id.ll_header1);
//        iv_cart = FVHelper.fv(this,R.id.iv_cart);
//        EventBus.getDefault().register(this);
//        mIvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        rl_good_cart1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
//                    startActivity(new Intent(mContext, HomeActivity.class));
//                    EventBus.getDefault().post(new GoToCartFragmentEvent());
//                } else {
//                    AppHelper.showMsg(mActivity, "请先登录");
//                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
//                }
//            }
//        });
//
//        iv_back_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        iv_back1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        iv_back_title1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        iv_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
//                    startActivity(new Intent(mContext, HomeActivity.class));
//                    EventBus.getDefault().post(new GoToCartFragmentEvent());
//                } else {
//                    AppHelper.showMsg(mActivity, "请先登录");
//                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
//                }
//            }
//        });
//
//        scoller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY!=0) {
//                    ll_header1.setVisibility(View.VISIBLE);
//                }else {
//                    ll_header1.setVisibility(View.GONE);
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    public void setViewData() {
//        getCustomerPhone();
//        getCartNum();
//        judgePageType();//进行差异性的设置。
//        getNewSpikeTool();
//
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//    }
//
//    /**
//     * 购物车数量
//     */
//    private void getCartNum() {
//        GetCartNumAPI.requestData(mContext)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GetCartNumModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(GetCartNumModel getCartNumModel) {
//                        if (getCartNumModel.isSuccess()) {
//                            if(getCartNumModel.getData().getNum().equals("0")) {
//                                tv_num.setVisibility(View.GONE);
//                                tv_num1.setVisibility(View.GONE);
//                            }else {
//                                tv_num.setVisibility(View.VISIBLE);
//                                tv_num1.setVisibility(View.VISIBLE);
//                                tv_num.setText(getCartNumModel.getData().getNum());
//                                tv_num1.setText(getCartNumModel.getData().getNum());
//                            }
//                        } else {
//                            AppHelper.showMsg(mContext, getCartNumModel.getMessage());
//                        }
//                    }
//                });
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getCartNum(ReduceNumEvent event) {
//        getCartNum();
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getCartNum(NumEvent event) {
//        getCartNum();
//    }
//
//    @Override
//    public void setClickEvent() {
//        rl_good_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
//                    startActivity(new Intent(mContext, HomeActivity.class));
//                    EventBus.getDefault().post(new GoToCartFragmentEvent());
//                } else {
//                    AppHelper.showMsg(mActivity, "请先登录");
//                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
//                }
//
//
//            }
//        });
//    }
//
//    /**
//     * 判断是哪种类型的页面
//     **/
//    private void judgePageType() {
//        linearLayoutSpike.setVisibility(View.VISIBLE);
//        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
//
//    }
//
//    /**
//     * 秒杀专区更多-顶部
//     */
//    private void getNewSpikeTool() {
//        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
//            iv_404.setImageResource(R.mipmap.ic_404);
//            iv_404.setVisibility(View.VISIBLE);
//            scoller.setVisibility(View.GONE);
//        }else {
//            scoller.setVisibility(View.VISIBLE);
//            iv_404.setImageResource(R.mipmap.ic_no_data);
//            SpikeNewActiveQueryAPI.requestData(mContext)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<SpikeNewQueryModel>() {
//                        @Override
//                        public void onCompleted() {
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                        }
//
//                        @Override
//                        public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
//                            if (spikeNewQueryModel.isSuccess()) {
//                                mListSpikeNew.clear();
//                                if (spikeNewQueryModel.getData() != null) {
//                                    mListSpikeNew.addAll(spikeNewQueryModel.getData());
//                                    //秒杀专区-顶部
//                                    mAdapterNewSpike = new SpikeActiveNewAdapter(mContext, mListSpikeNew);
//                                    mRvSpikeData.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
//                                    mRvSpikeData.setAdapter(mAdapterNewSpike);
//                                    List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
//                                    if(data.get(currentPosition).getFlag()==1) {
//                                        tv_desc.setText("距离本场活动结束");
//                                    }else {
//                                        tv_desc.setText("距离本场活动开始");
//                                    }
//                                    tv_start_time.setText("活动于"+data.get(currentPosition).getDateTime()+"开始");
//                                    long startTime = data.get(currentPosition).getStartTime();
//                                    long endTime = data.get(currentPosition).getEndTime();
//                                    long currentTime = data.get(currentPosition).getCurrentTime();
//                                    snap.setTime(false, currentTime, startTime,endTime);
//                                    snap.start();
//                                    mAdapterNewSpike.setOnItemClickListener(new OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(View view, int position) {
//                                            getNewSpikeTwo(position);
//                                            tv_start_time.setText("活动于"+data.get(position).getDateTime()+"开始");
//                                        }
//
//                                        @Override
//                                        public void onItemLongClick(View view, int position) {
//
//                                        }
//                                    });
//
//                                    //请求列表数据
//                                    spikeActiveQuery(spikeNewQueryModel.getData().get(currentPosition).getActiveId());
//                                }
//
//
//                            } else {
//                                AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
//                            }
//
//                        }
//                    });
//        }
//
//    }
//
//    private void getNewSpikeTwo(int pos) {
//        SpikeNewActiveQueryAPI.requestData(mContext)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<SpikeNewQueryModel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
//                        if (spikeNewQueryModel.isSuccess()) {
//                            if (spikeNewQueryModel.getData() != null) {
//                                List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
//                                mAdapterNewSpike.selectPosition(pos);
//                                spikeActiveQuery(mListSpikeNew.get(pos).getActiveId());
//                                currentPosition = pos;
//                                if(data.get(currentPosition).getFlag()==1) {
//                                    tv_desc.setText("距离本场活动结束");
//                                }else {
//                                    tv_desc.setText("距离本场活动开始");
//                                }
//                                long startTime = data.get(currentPosition).getStartTime();
//                                long endTime = data.get(currentPosition).getEndTime();
//                                long currentTime = data.get(currentPosition).getCurrentTime();
//                                snap.setTime(false, currentTime, startTime,endTime);
//                                snap.start();
//                                mAdapterNewSpike.notifyDataSetChanged();
//                            }
//                        } else {
//                            AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
//                        }
//
//                    }
//                });
//    }
//    /**
//     * 秒杀-更多-列表
//     */
//    private void spikeActiveQuery(int activeId) {
//        SecKillMoreListAPI.requestMoreListData(mContext, activeId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<SeckillListModel>() {
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(SeckillListModel seckillListModel) {
//                        if (seckillListModel.success) {
//                            mAdapterSpikeQuery = new SpikeActiveQueryAdapter(R.layout.spike_new_active_product, seckillListModel.data.kills, activeId, new SpikeActiveQueryAdapter.Onclick() {
//                                @Override
//                                public void tipClick() {
//                                    AppHelper.ShowAuthDialog(mActivity,cell);
//                                }
//                            });
//                            mRvData.setAdapter(mAdapterSpikeQuery);
//
//                            mListSeckill.clear();
//                            if (seckillListModel.data.kills != null) {
//                                mListSeckill.addAll(seckillListModel.data.kills);
//                            }
//
//                            mAdapterSpikeQuery.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
//                                    intent.putExtra(AppConstant.ACTIVEID, mListSeckill.get(position).activeId);
//                                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
//                                    intent.putExtra("num","-1");
//                                    startActivity(intent);
//
//                                }
//                            });
//
//                            int flag = seckillListModel.data.flag;
//                            UserInfoHelper.saveSpikePosition(mContext, String.valueOf(flag));
//
//                        } else {
//
//                            AppHelper.showMsg(mActivity, seckillListModel.message);
//
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 弹出电话号码
//     */
//    private AlertDialog mDialog;
//    TextView tv_phone;
//    TextView tv_time;
//    public void showPhoneDialog(final String cell) {
//        mDialog = new AlertDialog.Builder(mContext).create();
//        mDialog.show();
//        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
//        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
//        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
//        tv_phone.setText("客服热线 ("+cell+")");
//
//        tv_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                mDialog.dismiss();
//            }
//        });
//        tv_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UnicornManager.inToUnicorn(mContext);
//                mDialog.dismiss();
//            }
//        });
//    }
//
//    /**
//     * @param
//     */
//    String cell;
//    private void getCustomerPhone() {
//        PublicRequestHelper.getCustomerPhone(mActivity, new OnHttpCallBack<GetCustomerPhoneModel>() {
//            @Override
//            public void onSuccessful(GetCustomerPhoneModel getCustomerPhoneModel) {
//                if (getCustomerPhoneModel.isSuccess()) {
//                    cell = getCustomerPhoneModel.getData();
//                } else {
//                    AppHelper.showMsg(mActivity, getCustomerPhoneModel.getMessage());
//                }
//            }
//
//            @Override
//            public void onFaild(String errorMsg) {
//            }
//        });
//    }
//
//    /**
//     * 获取提醒状态  SpikeActiveQueryAPI  int activeId,SeckillListModel seckillListModel
//     */
//    private void getStat(int activeId) {
//        SpikeActiveQueryAPI.requestData(mContext, activeId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(BaseModel baseModel) {
//                        int warnMe = SharedPreferencesUtil.getInt(mContext, "warnMe");
//                        if(baseModel.success) {
//                            if(warnMe == 0) {
//                                SharedPreferencesUtil.saveInt(mContext,"warnMe",1);
//                            }else {
//                                SharedPreferencesUtil.saveInt(mContext,"warnMe",0);
//                            }
//
//                        }else {
//                            AppHelper.showMsg(mActivity, baseModel.message);
//                        }
//
//                    }
//                });
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SelectBean.cleanDate();
//        EventBus.getDefault().unregister(this);
//
//    }
//    long currentTime;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        currentTime = System.currentTimeMillis();
//        getCustomerPhone();
//    }
//


}

