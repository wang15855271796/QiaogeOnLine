package com.puyue.www.qiaoge.fragment.home;

import android.animation.IntEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.home.HomeGoodsListActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.MyOrdersActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MinerIntegralActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletNewActivity;
import com.puyue.www.qiaoge.adapter.CommonCouponAdapter;
import com.puyue.www.qiaoge.adapter.CommonssAdapter;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.adapter.FullAdapter;
import com.puyue.www.qiaoge.adapter.HotAdapter;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.Skill2Adapter;
import com.puyue.www.qiaoge.adapter.Skill3Adapter;
import com.puyue.www.qiaoge.adapter.Skill5Adapter;
import com.puyue.www.qiaoge.adapter.Team3Adapter;
import com.puyue.www.qiaoge.adapter.TeamAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonProductActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.adapter.home.ReductionProductActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.home.QueryHomePropupAPI;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.banner.listener.OnBannerListener;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.PrivacyDialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.IsTurnModel;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.SendModel;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;
import com.puyue.www.qiaoge.model.home.RecommendModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.puyue.www.qiaoge.view.AutoScrollRecyclerView;
import com.puyue.www.qiaoge.view.HIndicators;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViewss;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.taobao.library.VerticalBannerView;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/1/4
 */
public class HomeFragmentss extends BaseFragment {
    Unbinder binder;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.rg_new)
    RadioGroup rg_new;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rl_grand)
    RelativeLayout rl_grand;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.ll_parent_top)
    RelativeLayout ll_parent_top;
    @BindView(R.id.ll_small_title)
    LinearLayout ll_small_title;
    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.rb_new)
    RadioButton rb_new;
    @BindView(R.id.rb_must_common)
    RadioButton rb_must_common;
    @BindView(R.id.rb_reduce)
    RadioButton rb_reduce;
    @BindView(R.id.rb_common)
    RadioButton rb_common;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.rb_new_top)
    RadioButton rb_new_top;
    @BindView(R.id.rb_must_common_top)
    RadioButton rb_must_common_top;
    @BindView(R.id.rb_info_top)
    RadioButton rb_info_top;
    @BindView(R.id.rb_common_top)
    RadioButton rb_common_top;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.iv_location)
    ImageView iv_location;
    @BindView(R.id.v1s)
    View v1s;
    @BindView(R.id.v2s)
    View v2s;
    @BindView(R.id.v3s)
    View v3s;
    @BindView(R.id.v4s)
    View v4s;
    @BindView(R.id.iv_tip)
    ImageView iv_tip;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_times)
    TextView tv_times;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl_full)
    RelativeLayout rl_full;
    @BindView(R.id.rl_team)
    RelativeLayout rl_team;
    @BindView(R.id.iv_empty)
    RoundImageView iv_empty;
    @BindView(R.id.ll_bgc)
    RelativeLayout ll_bgc;
    @BindView(R.id.rv_auto_view)
    AutoScrollRecyclerView rv_auto_view;
    @BindView(R.id.rv_skill)
    RecyclerView rv_skill;
    @BindView(R.id.snap)
    SnapUpCountDownTimerViewss snap;
    @BindView(R.id.ll_skill)
    LinearLayout ll_skill;
    @BindView(R.id.rv_coupon)
    RecyclerView rv_coupon;
    @BindView(R.id.rv_coupon1)
    RecyclerView rv_coupon1;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.tv_team_title)
    TextView tv_team_title;
    @BindView(R.id.tv_skill_title)
    TextView tv_skill_title;
    @BindView(R.id.rv_icon)
    RecyclerView rv_icon;
    @BindView(R.id.indicator)
    HIndicators indicator;
    @BindView(R.id.verticalBanner)
    VerticalBannerView verticalBanner;
    @BindView(R.id.ll_driver)
    LinearLayout ll_driver;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.rv_hot1)
    RecyclerView rv_hot1;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.ll_hot)
    LinearLayout ll_hot;
    @BindView(R.id.rv_given)
    AutoScrollRecyclerView rv_given;
    @BindView(R.id.rv_auto_view1)
    AutoScrollRecyclerView rv_auto_view1;
    @BindView(R.id.rv_auto_team)
    AutoScrollRecyclerView rv_auto_team;
    @BindView(R.id.rv_team)
    AutoScrollRecyclerView rv_team;
    private SkillAdapter skillAdapter;
    Skill5Adapter skill5Adapter;
    HotAdapter hotAdapter;
    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 72;
    private int evaluatemargin;
    private int evaluatetop;
    private RelativeLayout.LayoutParams layoutParams;
    int scrollLength;
    int bar_height;
    int scroll_height;
    private IndexInfoModel.DataBean data;
    private CouponModel.DataBean data1;
    private long currentTime;
    private long startTime;
    private long endTime;
    private Date currents;
    private Date starts;
    private PrivacyDialog privacyDialog;
    List<IndexInfoModel.DataBean.IconsBean> iconList = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> list = new ArrayList<>();
    private List<String> bannerList = new ArrayList<>();

    private List<CouponModel.DataBean.ActivesBean> actives = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive3 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> couponActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> couponActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> couponActive3 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> teamActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> fullActive1 = new ArrayList<>();
    private List<IndexInfoModel.DataBean.ClassifyListBean> classifyList = new ArrayList<>();
    private List<TurnModel.DataBean> data2;
    List<IndexInfoModel.DataBean.ClassifyListBean> classifyLists;
    private List<CouponListModel.DataBean.GiftsBean> lists;
    List<ProductNormalModel.DataBean.ListBean> listBeans = new ArrayList<>();
    private List<String> recommendList = new ArrayList<>();
    List<String> recommendData;
    private CouponListAdapter couponListAdapter;
    List<OrderModel.DataBean> driverList = new ArrayList<>();
    private HomeActivityDialog homeActivityDialog;
    private int teamNum;
    private int fullGiftNum;
    private String cell; // 客服电话
    Team3Adapter team3Adapter;
    TeamAdapter teamAdapter;
    private CommonAdapter commonAdapter;
    CommonssAdapter commonssAdapter;
    CouponListModel couponListModels;
    private CommonCouponAdapter commonCouponAdapter;
    private VerticalBannerAdapter verticalBannerAdapter;
    private RvIconAdapter rvIconAdapter;
    private Skill2Adapter skill2Adapter;
    private Skill3Adapter skill3Adapter;
    FullAdapter fullAdapter;
    IndexRecommendAdapter indexRecommendAdapter;
    private CouponListDialog couponListDialog;
    int PageNum = 1;
    private int showType;
    private TurnTableDialog turnTableDialog;

    @Override
    public int setLayoutId() {
        return R.layout.testss;
    }

    @Override
    public void initViews(View view) {
        binder = ButterKnife.bind(this,view);
        initFragment();
        setListener();
        refreshLayout.autoRefresh();

//        rvIconAdapter = new RvIconAdapter(R.layout.item_home_icon,classifyList);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, RecyclerView.HORIZONTAL, false);
//
//        rv_icon.setLayoutManager(gridLayoutManager);
//        rv_icon.setAdapter(rvIconAdapter);
//
//        rv_icon.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                indicator.bindRecyclerView(rv_icon);
//            }
//        });
//
//        rvIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
//                EventBus.getDefault().post(new GoToMarketEvent());
//                EventBus.getDefault().postSticky(new FromIndexEvent(classifyList.get(position).getId() + ""));
//            }
//        });

        //顶部推荐
        indexRecommendAdapter = new IndexRecommendAdapter(R.layout.item_index_recommend, recommendList);
        rv_recommend.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_recommend.setAdapter(indexRecommendAdapter);

        skill5Adapter = new Skill5Adapter(mActivity, skillActive3);
        rv_auto_view.setAdapter(skill5Adapter);
        rv_auto_view.setLayoutManager(new GridLayoutManager(mActivity, 4));

        //满赠1
        PagerSnapHelper snapFull = new PagerSnapHelper();
        commonssAdapter = new CommonssAdapter(mActivity, fullActive1);
        rv_auto_view1.setAdapter(commonssAdapter);
        snapFull.attachToRecyclerView(rv_auto_view1);
        initRecycles();

        //满赠2
        fullAdapter = new FullAdapter(mActivity, fullActive1);
        rv_given.setAdapter(fullAdapter);
        rv_given.setLayoutManager(new GridLayoutManager(mActivity, 1));

        //组合
        teamAdapter = new TeamAdapter(R.layout.item_team_lists, teamActive1);
        rv_auto_team.setLayoutManager(new GridLayoutManager(mActivity, 1));
        rv_auto_team.setAdapter(teamAdapter);

        //组合2
        team3Adapter = new Team3Adapter(R.layout.item_teams_list, teamActive1);
        rv_team.setLayoutManager(new GridLayoutManager(mActivity, 1));
        rv_team.setAdapter(team3Adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBaseLists();
                PageNum = 1;
                driverList.clear();
                isSend();
                hotKey();
                isTurn();
                getHot(1, 10, "hot");
                getOrder();
                classifyList();
                EventBus.getDefault().post(new BackEvent());
                refreshLayout.finishRefresh();
            }
        });

        rl_grand.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_grand.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                bar_height = rl_bar.getHeight();
                scroll_height = ll_scroll.getHeight();
                scrollLength = Math.abs(scroll_height - bar_height);
            }
        });

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int y) {
                int abs_y = Math.abs(y);
                int totalScrollRange = appBarLayout.getTotalScrollRange()-bar_height;
                if (Math.abs(abs_y)>=totalScrollRange) {
                    ll_parent_top.setVisibility(View.VISIBLE);
                    ll_small_title.setVisibility(View.GONE);
                    ll_line.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new changeEvent(true,"1"));
                    if(rb_must_common.isChecked()) {
                        rb_must_common_top.setChecked(true);
                        rb_new_top.setChecked(false);
                        rb_info_top.setChecked(false);
                        rb_common_top.setChecked(false);
                        v1s.setVisibility(View.INVISIBLE);
                        v2s.setVisibility(View.VISIBLE);
                        v3s.setVisibility(View.INVISIBLE);
                        v4s.setVisibility(View.INVISIBLE);
                        rb_new_top.setTextColor(Color.parseColor("#333333"));
                        rb_must_common_top.setTextColor(Color.parseColor("#17BD60"));
                        rb_info_top.setTextColor(Color.parseColor("#333333"));
                        rb_common_top.setTextColor(Color.parseColor("#333333"));
                        Log.d("wdadddwdq.........","333");
                    }else if(rb_new.isChecked()) {
                        rb_new_top.setChecked(true);
                        rb_must_common_top.setChecked(false);
                        rb_info_top.setChecked(false);
                        rb_common_top.setChecked(false);
                        v1s.setVisibility(View.VISIBLE);
                        v2s.setVisibility(View.INVISIBLE);
                        v3s.setVisibility(View.INVISIBLE);
                        v4s.setVisibility(View.INVISIBLE);
                        Log.d("wdadddwdq.........","222");
                        rb_new_top.setTextColor(Color.parseColor("#17BD60"));
                        rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                        rb_info_top.setTextColor(Color.parseColor("#333333"));
                        rb_common_top.setTextColor(Color.parseColor("#333333"));
                    }else if(rb_reduce.isChecked()) {
                        rb_info_top.setChecked(true);
                        rb_new_top.setChecked(false);
                        rb_must_common_top.setChecked(false);
                        rb_common_top.setChecked(false);
                        v1s.setVisibility(View.INVISIBLE);
                        v2s.setVisibility(View.INVISIBLE);
                        v3s.setVisibility(View.VISIBLE);
                        v4s.setVisibility(View.INVISIBLE);
                        rb_info_top.setTextColor(Color.parseColor("#17BD60"));
                        rb_new_top.setTextColor(Color.parseColor("#333333"));
                        rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                        rb_common_top.setTextColor(Color.parseColor("#333333"));
                    }else if(rb_common.isChecked()){
                        rb_common_top.setChecked(true);
                        rb_info_top.setChecked(false);
                        rb_new_top.setChecked(false);
                        rb_must_common_top.setChecked(false);
                        v1s.setVisibility(View.INVISIBLE);
                        v2s.setVisibility(View.INVISIBLE);
                        v3s.setVisibility(View.INVISIBLE);
                        v4s.setVisibility(View.VISIBLE);
                        rb_common_top.setTextColor(Color.parseColor("#17BD60"));
                        rb_info_top.setTextColor(Color.parseColor("#333333"));
                        rb_new_top.setTextColor(Color.parseColor("#333333"));
                        rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                    }

                } else {
                    EventBus.getDefault().post(new changeEvent(false,"1"));
                    ll_parent_top.setVisibility(View.GONE);
                    ll_small_title.setVisibility(View.VISIBLE);
                    ll_line.setVisibility(View.GONE);
                }


                //滑动距离小于顶部栏从透明到不透明所需的距离
                if ((scrollLength - abs_y) > 0) {
                    //估值器
                    IntEvaluator evaluator = new IntEvaluator();
                    float percent = (float) (scrollLength - abs_y) / scrollLength;

                    if (percent <= 1) {
                        //透明度
                        int evaluate = evaluator.evaluate(percent, 255, 0);
                        rl_bar.setAlpha(evaluate);
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
                        iv_location.setImageResource(R.mipmap.icon_address2);
                        tv_city.setAlpha(percent);
//                                iv_tip.getBackground().setAlpha(1);
                        rl_search.requestLayout();
                    }
                } else {
                    rl_bar.getBackground().setAlpha(255);
                    if(layoutParams!=null){
                        layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,mActivity),85, DensityUtil.dip2px(ENDMARGINLEFT,mActivity), 0);
                        rl_search.requestLayout();
                        iv_location.setImageResource(R.mipmap.icon_address1);
                        tv_city.setAlpha(0);
                        iv_tip.setAlpha(0);
                    }

                }
            }
        });
    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }

    private void initRecycles() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rv_auto_view1.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
            }
        }, 5000, 4000, TimeUnit.MILLISECONDS);
        rv_auto_view1.setLayoutManager(layoutManager);
    }


    private void isTurn() {
        IndexHomeAPI.isTurn(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsTurnModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsTurnModel turnModel) {
                        if (turnModel.isSuccess()) {
                            int isShow = turnModel.getData();
                            //1显示 0不显示
                            if (isShow == 1) {
                                getTurn();
                            } else {
                                getPrivacy();
                            }
                        } else {
                            AppHelper.showMsg(mActivity, turnModel.getMessage());
                        }
                    }
                });
    }


    /**
     * 获取权限
     */
    private void getPrivacy() {
        IndexHomeAPI.getPrivacy(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrivacyModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PrivacyModel privacyModel) {

                        if (privacyModel.isSuccess()) {
                            String content = privacyModel.getData().getContent();
                            privacyDialog = new PrivacyDialog(mActivity, content);
                            if (privacyModel.getData().getOpen().equals("1")) {
                                privacyDialog.show();
                            } else {
                                privacyDialog.dismiss();
                                getCouponList();
                            }

                        } else {
                            AppHelper.showMsg(mActivity, privacyModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 优惠券列表弹窗
     */
    private void getCouponList() {
        IndexHomeAPI.getCouponLists(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponListModel couponListModel) {
                        if (couponListModel.isSuccess()) {
                            if (couponListModel.getData() != null) {
                                couponListModels = couponListModel;
                                lists = couponListModel.getData().getGifts();
                                couponListAdapter.notifyDataSetChanged();
                                couponListDialog = new CouponListDialog(mActivity, couponListModel, lists);

                                if (lists.size() > 0) {
                                    couponListDialog.show();
                                } else {
                                    couponListDialog.dismiss();
                                    QueryHomePropup();
                                }

                            } else {
                                AppHelper.showMsg(mActivity, couponListModel.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 首页活动弹窗
     */
    private void QueryHomePropup() {
        QueryHomePropupAPI.requestQueryHomePropup(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryHomePropupModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QueryHomePropupModel queryHomePropupModel) {
                        if (queryHomePropupModel.isSuccess()) {
                            if (queryHomePropupModel.getData().getHomePropup() != null) {
                                QueryHomePropupModel.DataBean.HomePropupBean homePropup = queryHomePropupModel.getData().getHomePropup();
                                homeActivityDialog = new HomeActivityDialog(mActivity, homePropup);
                                if (queryHomePropupModel.getData().isPropup()) {
                                    homeActivityDialog.show();
                                } else {
                                    homeActivityDialog.dismiss();
                                }
                            }

                        } else {
                            AppHelper.showMsg(mActivity, queryHomePropupModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 转盘数据
     */
    private void getTurn() {
        IndexHomeAPI.getTurn(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TurnModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TurnModel turnModel) {
                        if (turnModel.isSuccess()) {
                            data2 = turnModel.getData();
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < data2.size(); i++) {
                                list.add(data2.get(i).getPoolNo());
                            }

                            turnTableDialog = new TurnTableDialog(mActivity, list);
                            turnTableDialog.show();
                        } else {
                            AppHelper.showMsg(mActivity, turnModel.getMessage());
                        }
                    }
                });
    }

    private void isSend() {
        IndexHomeAPI.isSend(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendModel sendModel) {
                        if (sendModel.isSuccess()) {
                            if (sendModel.isData()) {
                                rl_address.setVisibility(View.GONE);
                            } else {
                                rl_address.setVisibility(View.VISIBLE);
                            }

                        } else {
                            AppHelper.showMsg(mActivity, sendModel.getMessage());
                        }
                    }
                });
    }

    private void getHot(int pageNum, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNum, pageSize, type, null)
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
                            listBeans.clear();
                            List<ProductNormalModel.DataBean.ListBean> list = getCommonProductModel.getData().getList();
                            listBeans.addAll(list);
                            if(listBeans.size()>0) {
                                ll_hot.setVisibility(View.VISIBLE);
                            }else {
                                ll_hot.setVisibility(View.GONE);
                            }
                            if (listBeans.size() == 1) {
                                hotAdapter = new HotAdapter(R.layout.item_common_lists, listBeans);
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot.setAdapter(hotAdapter);
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                            } else if (listBeans.size() == 2) {
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_lists, listBeans);
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot.setAdapter(hotAdapter);
                            } else {
                                rv_hot1.setVisibility(View.VISIBLE);
                                rv_hot.setVisibility(View.GONE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_listss, listBeans);
                                rv_hot1.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot1.setAdapter(hotAdapter);
                            }

                        } else {
                            ToastUtil.showErroMsg(mActivity, getCommonProductModel.getMessage());
                        }
                    }
                });
    }

    private void classifyList() {
        IndexHomeAPI.classifyList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndexInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndexInfoModel indexInfoModel) {
                        if (indexInfoModel.isSuccess()) {
                            if(indexInfoModel.getData()!=null) {
                                classifyLists = indexInfoModel.getData().getClassifyList();
                                classifyList.clear();
                                classifyList.addAll(classifyLists);

                                if(classifyList.size()>0) {
                                    indicator.setVisibility(View.VISIBLE);
                                }else {
                                    indicator.setVisibility(View.GONE);
                                }
                                rvIconAdapter.notifyDataSetChanged();
                            }

                        } else {
                            AppHelper.showMsg(mActivity, indexInfoModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 搜索热词
     */
    private void hotKey() {
        IndexHomeAPI.recommendList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RecommendModel recommendModel) {
                        if (recommendModel.isSuccess()) {

                            if(recommendModel.getData()!=null) {
                                recommendList.clear();
                                recommendData = recommendModel.getData();
                                recommendList.addAll(recommendData);
                                indexRecommendAdapter.notifyDataSetChanged();
                            }else {
//
                            }

                        } else {
                            AppHelper.showMsg(mActivity, recommendModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 订单状态
     */

    private void getOrder() {
        IndexHomeAPI.indexOrder(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderModel indexInfoModel) {
                        if (indexInfoModel.isSuccess()) {

                            if (indexInfoModel.getData().size() > 0) {
                                driverList.clear();

                                driverList.addAll(indexInfoModel.getData());

                                verticalBannerAdapter = new VerticalBannerAdapter(cell, driverList, getContext());

                                ll_driver.setVisibility(View.VISIBLE);
                                verticalBanner.setAdapter(verticalBannerAdapter);
                                verticalBanner.start();


                            } else {
                                ll_driver.setVisibility(View.GONE);
                                Log.d("wdasddsdfsdf....","222");
                            }
                        }
                    }
                });
    }
    /**
     * 获取首页信息
     */
    private void getBaseLists() {
        IndexHomeAPI.getIndexInfo(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndexInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();
                    }

                    @Override
                    public void onNext(IndexInfoModel indexInfoModel) {
                        if (indexInfoModel.isSuccess()) {
                            data = indexInfoModel.getData();

                            iconList.clear();
                            iconList.addAll(data.getIcons());



                            tv_times.setText(indexInfoModel.getData().getReturnAmountTime() + "小时快速退款");
                            tv_amount.setText("满" + indexInfoModel.getData().getSendAmount() + "元免配送费");
                            teamNum = indexInfoModel.getData().getTeamNum();
                            fullGiftNum = indexInfoModel.getData().getFullGiftNum();

                            if (teamNum != 0 && fullGiftNum != 0) {
                                rl1.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.GONE);

                            }

                            if (teamNum == 0 && fullGiftNum == 0) {
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                            }

                            if (teamNum != 0 && fullGiftNum == 0) {
                                rl1.setVisibility(View.GONE);
                                rl_full.setVisibility(View.GONE);
                                rl_team.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.VISIBLE);
                            }

                            if (fullGiftNum != 0 && teamNum == 0) {
                                rl_full.setVisibility(View.VISIBLE);
                                rl_team.setVisibility(View.GONE);
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                            }
//
                            getSpikeList(12);
                            getSpikeList(3);
                            getSpikeList(2);
                            getSpikeList(11);
                            rvIconAdapter.notifyDataSetChanged();

                            //----------------------------
                            tv_city.setText(data.getAddress());
                            list.clear();
                            list1.clear();
                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                list.add(data.getBanners().get(i).getDefaultPic());

                            }

                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                if (indexInfoModel.getData().getBanners().get(i).getShowType() == 2) {
                                    list1.add(data.getBanners().get(i).getDetailPic());
                                }
                            }
                            if (data.getBanners().size() > 0) {
                                iv_empty.setVisibility(View.GONE);
                                banner.setVisibility(View.VISIBLE);
                                banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                                banner.setImageLoader(new GlideImageLoader());
                                bannerList.clear();
                                bannerList.addAll(list);
                                banner.setImages(bannerList);
                                banner.setBannerAnimation(Transformer.DepthPage);
                                banner.isAutoPlay(true);
                                banner.setDelayTime(3000);
                                banner.setIndicatorGravity(BannerConfig.RIGHT);
                                ClickBanner(data.getBanners());
                                banner.start();

                                List<IndexInfoModel.DataBean.BannersBean> banners = data.getBanners();


                                banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int i, float v, int i1) {
//                                        Log.d("wwwwwwww........","1111");
                                    }

                                    @Override
                                    public void onPageSelected(int pos) {
                                        if (!TextUtils.isEmpty(banners.get(pos).getRgbColor())) {
                                            String rgbColor = banners.get(pos).getRgbColor();
                                            ll_bgc.setBackgroundColor(Color.parseColor("#" + rgbColor));
//                                            StatusBarUtil.setColor(mActivity,Color.parseColor("#" + rgbColor),0);
                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int i) {
//                                        Log.d("wwwwwwww........","3333");
                                    }
                                });

                            } else {
                                banner.setVisibility(View.GONE);
                                iv_empty.setVisibility(View.VISIBLE);

                            }
                            lav_activity_loading.hide();
                        } else {

                            AppHelper.showMsg(mActivity, indexInfoModel.getMessage());
                            lav_activity_loading.hide();
                        }
                    }
                });
    }

    private void getSpikeList(int type) {
        IndexHomeAPI.getCouponList(mActivity, type + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponModel couponModel) {
                        if (couponModel.isSuccess()) {
                            actives.clear();
                            if (type == 2) {
                                data1 = couponModel.getData();
                                if (data1 != null) {
                                    tv_skill_title.setText(data1.getTitle());
                                    currentTime = couponModel.getData().getCurrentTime();
                                    startTime = couponModel.getData().getStartTime();

                                    if (data1.getActives().size() == 1) {
                                        skillActive1.clear();
                                        skillActive1.addAll(data1.getActives());
                                        skillAdapter = new SkillAdapter(mActivity, R.layout.item_skill_lists, skillActive1, "1");
                                        rv_skill.setAdapter(skillAdapter);
                                        rv_skill.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skillAdapter.notifyDataSetChanged();
                                        ll_skill.setVisibility(View.VISIBLE);
                                        rv_auto_view.setVisibility(View.GONE);
                                    } else if (data1.getActives().size() == 2) {
                                        skillActive2.clear();
                                        skillActive2.addAll(data1.getActives());
                                        skill2Adapter = new Skill2Adapter(mActivity, R.layout.item_skill_lists, skillActive2, "0");
                                        rv_skill.setAdapter(skill2Adapter);
                                        ll_skill.setVisibility(View.VISIBLE);
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill2Adapter.notifyDataSetChanged();
                                        rv_auto_view.setVisibility(View.GONE);
                                        initRecycle();

                                    } else if (data1.getActives().size() == 3) {
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list, skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity, 3));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        ll_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                        rv_auto_view.setVisibility(View.GONE);
                                    } else if (data1.getActives().size() == 4) {
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list4, skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity, 4));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                        ll_skill.setVisibility(View.VISIBLE);
                                        rv_auto_view.setVisibility(View.GONE);
                                    } else if(data1.getActives().size() > 4) {
                                        skillActive3.clear();
                                        rv_skill.setVisibility(View.GONE);
                                        skillActive3.addAll(data1.getActives());
                                        skill5Adapter.notifyDataSetChanged();
                                        rv_auto_view.setVisibility(View.VISIBLE);
                                        ll_skill.setVisibility(View.VISIBLE);
                                    }


                                    endTime = couponModel.getData().getEndTime();
                                    String current = DateUtils.formatDate(currentTime, "MM月dd日HH时mm分ss秒");
                                    String start = DateUtils.formatDate(startTime, "MM月dd日HH时mm分ss秒");
                                    try {
                                        currents = Utils.stringToDate(current, "MM月dd日HH时mm分ss秒");
                                        starts = Utils.stringToDate(start, "MM月dd日HH时mm分ss秒");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    if (currentTime > startTime) {
                                        //秒杀开始
                                        if (startTime != 0 && endTime != 0) {
                                            snap.setVisibility(View.VISIBLE);
                                            snap.setTime(true, currentTime, startTime, endTime);
                                            snap.changeBackGround(ContextCompat.getColor(mActivity, R.color.white));
                                            snap.changeTypeColor(ContextCompat.getColor(mActivity, R.color.color_F6551A));

                                            snap.start();
                                        } else {

                                            snap.setVisibility(View.GONE);
                                        }
                                    } else {
                                        //未开始
                                        boolean exceed2 = DateUtils.isExceed2(currents, starts);
                                        if (exceed2) {
                                            //大于2

                                            snap.setVisibility(View.GONE);
                                        } else {
                                            //小于2
                                            if (startTime != 0 && endTime != 0) {
                                                snap.setVisibility(View.VISIBLE);
                                                snap.setTime(true, currentTime, startTime, endTime);
                                                snap.changeBackGround(ContextCompat.getColor(mActivity, R.color.white));
                                                snap.changeTypeColor(ContextCompat.getColor(mActivity, R.color.color_F6551A));

                                                snap.start();
                                            } else {

                                                snap.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                }else {
                                    ll_skill.setVisibility(View.GONE);
                                }

                            } else if (type == 11) {
                                data1 = couponModel.getData();
                                if (data1 != null) {
                                    ll_coupon.setVisibility(View.VISIBLE);
                                    if (data1.getActives().size() == 1) {
                                        couponActive1.clear();
                                        couponActive1.addAll(data1.getActives());
                                        commonAdapter = new CommonAdapter(R.layout.item_common_lists, couponActive1);
                                        rv_coupon.setAdapter(commonAdapter);
                                        rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity));
                                        commonAdapter.notifyDataSetChanged();
                                        rv_coupon1.setVisibility(View.GONE);
                                        rv_coupon.setVisibility(View.VISIBLE);
                                    } else if (data1.getActives().size() == 2) {
                                        couponActive2.clear();
                                        couponActive2.addAll(data1.getActives());
                                        commonCouponAdapter = new CommonCouponAdapter(mActivity, 11 + "", R.layout.item_coupon_lists, couponActive2);
                                        rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                        rv_coupon.setAdapter(commonCouponAdapter);
                                        commonCouponAdapter.notifyDataSetChanged();
                                        rv_coupon1.setVisibility(View.GONE);
                                        rv_coupon.setVisibility(View.VISIBLE);
                                    } else {
                                        couponActive3.clear();
                                        couponActive3.addAll(data1.getActives());
                                        commonAdapter = new CommonAdapter(R.layout.item_coupon_listss, couponActive3);
                                        rv_coupon1.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
                                        rv_coupon1.setAdapter(commonAdapter);
                                        commonAdapter.notifyDataSetChanged();
                                        rv_coupon1.setVisibility(View.VISIBLE);
                                        rv_coupon.setVisibility(View.GONE);
                                    }
                                }else {
                                    ll_coupon.setVisibility(View.GONE);
                                }
                            } else if (type == 3) {
                                data1 = couponModel.getData();
                                if (data1 != null) {
                                    tv_team_title.setText(data1.getTitle());
                                    teamActive1.clear();
                                    teamActive1.addAll(data1.getActives());
                                    teamAdapter.notifyDataSetChanged();
                                    team3Adapter.notifyDataSetChanged();
                                }
                            } else if (type == 12) {
                                data1 = couponModel.getData();
                                if (data1 != null) {
                                    fullActive1.clear();

                                    fullActive1.addAll(data1.getActives());
                                    commonssAdapter.notifyDataSetChanged();
                                    fullAdapter.notifyDataSetChanged();
                                }
                            }
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void ClickBanner(List<IndexInfoModel.DataBean.BannersBean> banners) {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showType = banners.get(position).getShowType();
                if (showType == 1 || banners.get(position).getLinkSrc() != null) {
                    //链接 banners.get(position).getLinkSrc()
                    Intent intent = new Intent(getActivity(), NewWebViewActivity.class);
                    intent.putExtra("URL", banners.get(position).getLinkSrc());
                    intent.putExtra("TYPE", 2);
                    intent.putExtra("name", "");
//                    Log.d("wsdsssssssss.........", banners.get(position).getLinkSrc());
                    startActivity(intent);
                } else if (showType == 2 || banners.get(position).getDetailPic() != null) {
                    //图片
                    AppHelper.showPhotoDetailDialog(mActivity, list1, position);
                } else if (showType == 3 || banners.get(position).getProdPage() != null) {
                    //H5页面
                    if (AppConstant.KILL_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), HomeGoodsListActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.HOT_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), HotProductActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.COMMON_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), CommonProductActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.DEDUCT_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), ReductionProductActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.SPECIAL_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.TEAM_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.BALANCE.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), MyWalletNewActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.POINT.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), MinerIntegralActivity.class);
                        startActivity(intent);
                    } else if (AppConstant.GIFT.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                        startActivity(intent);
                    }
                } else if (showType == 4) {
                    //商品
                    int businessId = Integer.parseInt(banners.get(position).getBusinessId());
                    Intent intent = new Intent(getActivity(), CommonGoodsDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, businessId);
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                    startActivity(intent);
                } else if (showType == 5) {
                    //活动
                    String businessType = banners.get(position).getBusinessType();
                    int businessId = Integer.parseInt(banners.get(position).getBusinessId());
                    if (businessType.equals("2")) {
                        Intent intent = new Intent(getActivity(), SeckillGoodActivity.class);
//                        intent.putExtra(AppConstant.NUM,businessId);
                        intent.putExtra("num", "-1");
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                        intent.putExtra(AppConstant.ACTIVEID, businessId);
                        startActivity(intent);
                    } else if (businessType.equals("3")) {
                        Intent intent = new Intent(getActivity(), SpecialGoodDetailActivity.class);
                        intent.putExtra(AppConstant.ACTIVEID, businessId);
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                        startActivity(intent);
                    } else if (businessType.equals("11")) {
                        Intent intent = new Intent(getActivity(), SpecialGoodDetailActivity.class);
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                        intent.putExtra(AppConstant.ACTIVEID, businessId);
                        startActivity(intent);
                    }

                }

            }
        });
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rv_skill.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);
        rv_skill.setLayoutManager(layoutManager);
    }

    int position;
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //根据位置得到对应的Fragment
            Fragment to = getFragment();
            //替换到Fragment
            switchFrament(mContent,to);
        }
    }
    private Fragment mContent;
    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){ //才切换
            mContent = to;
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction(); //开启事务
            //判断to有没有被添加
            if(!to.isAdded()){//to没有被添加
                //1.from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //2.添加to
                if(to != null){
                    ft.add(R.id.fl_container,to).commit();
                }
            }else{ //to已经被添加
                //1.from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //2.显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }
    }
    private Fragment getFragment() {
        Fragment fragment = mBaseFragment.get(position);
        return fragment;
    }
    private void setListener() {
        rg_new.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中框架页面
        rg_new.check(R.id.rb_common);
    }

    private List<Fragment> mBaseFragment;
    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(MustFragment.getInstance());
        mBaseFragment.add(NewFragment.getInstance());
        mBaseFragment.add(ReduceFragment.getInstance());
        mBaseFragment.add(CommonFragment.getInstance());
    }

}
