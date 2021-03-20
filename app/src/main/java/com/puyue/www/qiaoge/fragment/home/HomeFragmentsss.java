package com.puyue.www.qiaoge.fragment.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.home.ChangeCityActivity;
import com.puyue.www.qiaoge.activity.home.ChooseAddressActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.home.FullGiftActivity;
import com.puyue.www.qiaoge.activity.home.HomeGoodsListActivity;
import com.puyue.www.qiaoge.activity.home.SearchReasultActivity;
import com.puyue.www.qiaoge.activity.home.SearchStartActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;

import com.puyue.www.qiaoge.activity.mine.order.MyOrdersActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MinerIntegralActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletNewActivity;

import com.puyue.www.qiaoge.adapter.CommonCouponAdapter;
import com.puyue.www.qiaoge.adapter.CommonsAdapter;
import com.puyue.www.qiaoge.adapter.CommonssAdapter;
import com.puyue.www.qiaoge.adapter.CommonsssAdapter;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.adapter.FullAdapter;
import com.puyue.www.qiaoge.adapter.HotAdapter;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.MyAdapter;
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
import com.puyue.www.qiaoge.api.cart.AddCartAPI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.home.QueryHomePropupAPI;
import com.puyue.www.qiaoge.api.mine.UpdateAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderNumAPI;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.banner.listener.OnBannerListener;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseHomeDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.PrivacyDialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.IsTurnModel;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.UpDateNumEvent0;
import com.puyue.www.qiaoge.event.UpDateNumEvent1;
import com.puyue.www.qiaoge.event.UpDateNumEvent10;
import com.puyue.www.qiaoge.event.UpDateNumEvent2;
import com.puyue.www.qiaoge.event.UpDateNumEvent3;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.event.UpDateNumEvent8;
import com.puyue.www.qiaoge.event.UpNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.SendModel;
import com.puyue.www.qiaoge.model.cart.AddCartModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.HomeNewRecommendModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;
import com.puyue.www.qiaoge.model.home.RecommendModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.model.mine.order.MyOrderNumModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.LoginUtil;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2020/1/4
 */
public class HomeFragmentsss extends BaseFragment implements View.OnClickListener,BaseSliderView.OnSliderClickListener{
    Unbinder binder;
    @BindView(R.id.rv_auto_view1)
    AutoScrollRecyclerView rv_auto_view1;
    @BindView(R.id.rv_coupon)
    RecyclerView rv_coupon;
    @BindView(R.id.rv_coupon1)
    RecyclerView rv_coupon1;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl_full)
    RelativeLayout rl_full;
    @BindView(R.id.rl_team)
    RelativeLayout rl_team;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.content)
    FrameLayout frameLayout;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.indicator)
    HIndicators indicator;
    @BindView(R.id.rv_icon)
    RecyclerView rv_icon;
    @BindView(R.id.rootview)
    FrameLayout rootview;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_driver)
    LinearLayout ll_driver;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.homeMessage)
    ImageView homeMessage;
    @BindView(R.id.rl_more)
    RelativeLayout rl_more;
    @BindView(R.id.rl_more2)
    RelativeLayout rl_more2;
    @BindView(R.id.rl_more3)
    RelativeLayout rl_more3;
    @BindView(R.id.rl_more4)
    RelativeLayout rl_more4;
    @BindView(R.id.rb_new)
    RadioButton rb_new;
    @BindView(R.id.rb_must_common)
    RadioButton rb_must_common;
    @BindView(R.id.rb_info)
    RadioButton rb_info;
    @BindView(R.id.rb_common)
    RadioButton rb_common;
    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.rv_team)
    AutoScrollRecyclerView rv_team;
    @BindView(R.id.rv_given)
    AutoScrollRecyclerView rv_given;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.tv_title2)
    TextView tv_title2;
    @BindView(R.id.tv_title3)
    TextView tv_title3;
    @BindView(R.id.tv_title4)
    TextView tv_title4;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ll_small_title)
    LinearLayout ll_small_title;
    @BindView(R.id.ll_active)
    LinearLayout ll_active;
    @BindView(R.id.iv_empty)
    RoundImageView iv_empty;
    @BindView(R.id.recyclerViewTest)
    RecyclerView recyclerViewTest;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.snap)
    SnapUpCountDownTimerViewss snap;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_desc2)
    TextView tv_desc2;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_desc3)
    TextView tv_desc3;
    @BindView(R.id.tv_desc4)
    TextView tv_desc4;
    @BindView(R.id.verticalBanner)
    VerticalBannerView verticalBanner;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.tv_change_address)
    TextView tv_change_address;
    @BindView(R.id.toolbar1)
    Toolbar toolbar1;
    @BindView(R.id.tv_skill_title)
    TextView tv_skill_title;
    @BindView(R.id.tv_search1)
    TextView tv_search1;
    @BindView(R.id.tv_times)
    TextView tv_times;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.ll_bgc)
    LinearLayout ll_bgc;
    @BindView(R.id.rv_skill)
    RecyclerView rv_skill;
    @BindView(R.id.rg_new)
    RadioGroup rg_new;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rv_auto_view)
    AutoScrollRecyclerView rv_auto_view;
    @BindView(R.id.rv_auto_team)
    AutoScrollRecyclerView rv_auto_team;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.rv_hot1)
    RecyclerView rv_hot1;
    @BindView(R.id.tv_coupon_more)
    TextView tv_coupon_more;
    @BindView(R.id.rv_test)
    RecyclerView rv_test;
    HotAdapter hotAdapter;
    Skill5Adapter skill5Adapter;
    IndexRecommendAdapter indexRecommendAdapter;
    CouponDialog couponDialog;
    FullAdapter fullAdapter;
    private String cell; // 客服电话
    private PrivacyDialog privacyDialog;
    ChooseHomeDialog chooseAddressDialog;
    CommonsssAdapter commonsssAdapter;
    List<String> recommendData;
    View footer;
//    AnimationDrawable drawable;
    List<String> listss = new ArrayList<>();
    //司机信息
    List<OrderModel.DataBean> driverList = new ArrayList<>();
    //八个icon集合
    List<IndexInfoModel.DataBean.IconsBean> iconList = new ArrayList<>();
    //秒杀集合
    List<HomeBaseModel.DataBean.SecKillListBean.KillsBean> skillList = new ArrayList<>();
    //秒杀预告集合
    List<HomeBaseModel.DataBean.SecKillListBean.KillsBean> skillAdvList = new ArrayList<>();
    //定时器
    Timer timer = new Timer();
    //新品集合
    List<HomeNewRecommendModel.DataBean.ListBean> newList = new ArrayList<>();
    //banner集合
    private List<String> bannerList = new ArrayList<>();
    //首页顶部推荐集合
    private List<String> recommendList = new ArrayList<>();
    private RvIconAdapter rvIconAdapter;
    Context context;
    int PageNum = 1;
    private MyOrderNumModel mModelMyOrderNum;
    private String token;
    private UpdateModel mModelUpdate;
    private boolean update;
    private boolean forceUpdate;
    private String content;
    private String url;//更新所用的url
    private AlertDialog mTypedialog;
    boolean flag;
    //banner集合
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    private IndexInfoModel.DataBean data;
    //分类列表
    private List<IndexInfoModel.DataBean.ClassifyListBean> classifyList = new ArrayList<>();
    NewFragment newFragment;
    MustFragment mustFragment;
    InfoFragment infoFragment;
    CommonFragment commonFragment;
    private String questUrl;
    private CouponModel.DataBean data1;
    private int showType;
    private CommonCouponAdapter commonCouponAdapter;
    private CommonsAdapter commonsAdapter;
    private CommonAdapter commonAdapter;
    private List<CouponModel.DataBean.ActivesBean> actives = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive3 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> skillActive1 = new ArrayList<>();

    private List<CouponModel.DataBean.ActivesBean> couponActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> couponActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> couponActive3 = new ArrayList<>();

    private List<CouponModel.DataBean.ActivesBean> teamActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> teamActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> teamActive3 = new ArrayList<>();

    private List<CouponModel.DataBean.ActivesBean> fullActive1 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> fullActive2 = new ArrayList<>();
    private List<CouponModel.DataBean.ActivesBean> fullActive3 = new ArrayList<>();

    private int spikeNum;
    private int teamNum;
    private int specialNum;
    private int fullGiftNum;
    private long currentTime;
    private long startTime;
    private long endTime;
    private Date currents;
    private Date starts;
    private VerticalBannerAdapter verticalBannerAdapter;
    private CouponListDialog couponListDialog;
    CouponListModel couponListModels;
    private CouponListAdapter couponListAdapter;
    private List<CouponListModel.DataBean.GiftsBean> lists;
    private List<TurnModel.DataBean> data2;
    private TurnTableDialog turnTableDialog;
    private SkillAdapter skillAdapter;
    private Skill2Adapter skill2Adapter;
    private Skill3Adapter skill3Adapter;
    private String deductAmountStr;
    private String offerStr;
    private String currentVersion;
    private boolean isUpdate;
    private HomeActivityDialog homeActivityDialog;
    CommonssAdapter commonssAdapter;
    public static HomeFragmentsss getInstance() {
        HomeFragmentsss fragment = new HomeFragmentsss();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.test1;

    }

    private void requestOrderNumTwo() {
        MyOrderNumAPI.requestOrderNum(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyOrderNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyOrderNumModel myOrderNumModel) {
                        mModelMyOrderNum = myOrderNumModel;
                        if (mModelMyOrderNum.success) {
                            updateOrderNum();

                        } else {
                            AppHelper.showMsg(mActivity, mModelMyOrderNum.message);
                           }
                    }
                });
    }

    private void updateOrderNum() {
        //消息中心
        if (mModelMyOrderNum.getData().getNotice() > 0) {
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("  " + mModelMyOrderNum.getData().getNotice() + "  ");
        } else {
            tv_num.setVisibility(View.GONE);
        }
    }

    private void getSpikeList(int type) {
        IndexHomeAPI.getCouponList(mActivity,type+"")
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponModel couponModel) {
                        if(couponModel.isSuccess()) {
                            actives.clear();
                            if(type==2) {
                                data1 = couponModel.getData();
                                if(data1!=null) {
                                    PagerSnapHelper snapHelper = new PagerSnapHelper();
                                    tv_desc.setText(data1.getDesc());
                                    tv_skill_title.setText(data1.getTitle());
                                    currentTime = couponModel.getData().getCurrentTime();
                                    startTime = couponModel.getData().getStartTime();
                                    if(data1.getActives().size()==1) {
                                        skillActive1.clear();
                                        skillActive1.addAll(data1.getActives());
                                        skillAdapter = new SkillAdapter(mActivity,R.layout.item_skill_lists, skillActive1,"1");
                                        rv_skill.setAdapter(skillAdapter);
                                        rv_skill.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL, false));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skillAdapter.notifyDataSetChanged();
                                    }else if(data1.getActives().size()==2){
                                        skillActive2.clear();
                                        skillActive2.addAll(data1.getActives());
                                        skill2Adapter = new Skill2Adapter(mActivity, R.layout.item_skill_lists, skillActive2, "0");
                                        rv_skill.setAdapter(skill2Adapter);
                                        recyclerView.setVisibility(View.GONE);
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill2Adapter.notifyDataSetChanged();
                                        snapHelper.attachToRecyclerView(recyclerViewTest);
                                        initRecycle();

                                    }else if(data1.getActives().size()==3){
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list,skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity,3));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                    }else if(data1.getActives().size()==4){
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list4,skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity,4));
                                        recyclerViewTest.setVisibility(View.GONE);
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                    }else if(data1.getActives().size()>4){
                                        skillActive3.clear();
                                        rv_skill.setVisibility(View.GONE);
                                        skillActive3.addAll(data1.getActives());
                                        skill5Adapter.notifyDataSetChanged();
                                        rv_auto_view.setVisibility(View.VISIBLE);
                                    }else {

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


                                    if(currentTime>startTime) {
                                        //秒杀开始
                                        if(startTime !=0&& endTime !=0) {
                                            snap.setVisibility(View.VISIBLE);
                                            snap.setTime(true, currentTime, startTime, endTime);
                                            snap.changeBackGround(ContextCompat.getColor(mActivity, R.color.white));
                                            snap.changeTypeColor(ContextCompat.getColor(mActivity, R.color.color_F6551A));
                                            tv_time.setVisibility(View.GONE);
                                            snap.start();
                                        }else {
                                            tv_time.setVisibility(View.GONE);
                                            snap.setVisibility(View.GONE);
                                        }
                                    }else {
                                        //未开始
                                        boolean exceed2 = DateUtils.isExceed2(currents, starts);
                                        if(exceed2) {
                                            //大于2
                                            tv_time.setText(start+"开抢");
                                            tv_time.setVisibility(View.VISIBLE);
                                            snap.setVisibility(View.GONE);
                                        }else {
                                            //小于2
                                            if(startTime !=0&& endTime !=0) {
                                                snap.setVisibility(View.VISIBLE);
                                                snap.setTime(true, currentTime, startTime, endTime);
                                                snap.changeBackGround(ContextCompat.getColor(mActivity, R.color.white));
                                                snap.changeTypeColor(ContextCompat.getColor(mActivity, R.color.color_F6551A));
                                                tv_time.setVisibility(View.GONE);
                                                snap.start();
                                            }else {
                                                tv_time.setVisibility(View.GONE);
                                                snap.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                }

                            } else if(type==11) {
                                data1 = couponModel.getData();
                                if(data1!=null) {
                                    tv_desc2.setText(data1.getDesc());
                                    if(data1.getActives().size()==1) {
                                        couponActive1.clear();
                                        couponActive1.addAll(data1.getActives());
                                        commonAdapter = new CommonAdapter(R.layout.item_common_lists, couponActive1);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL, false));
                                        rv_coupon.setAdapter(commonAdapter);
                                        commonAdapter.notifyDataSetChanged();

                                    }else if(data1.getActives().size()==2){
                                        couponActive2.clear();
                                        couponActive2.addAll(data1.getActives());
                                        commonCouponAdapter = new CommonCouponAdapter(mActivity, 11 + "", R.layout.item_coupon_lists, couponActive2);
                                        rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL, false));
                                        rv_coupon.setAdapter(commonCouponAdapter);
                                        commonCouponAdapter.notifyDataSetChanged();

                                    }else {
                                        couponActive3.clear();
                                        couponActive3.addAll(data1.getActives());
                                        commonAdapter = new CommonAdapter(R.layout.item_coupon_listss, couponActive3);
                                        rv_coupon1.setLayoutManager(new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL, false));
                                        rv_coupon1.setAdapter(commonAdapter);
                                        commonAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else if(type==3) {
                                data1 = couponModel.getData();
                                if(data1!=null) {
                                    tv_desc3.setText(data1.getDesc());
                                    teamActive1.clear();
                                    teamActive1.addAll(data1.getActives());
                                    teamAdapter.notifyDataSetChanged();
                                    team3Adapter.notifyDataSetChanged();
                                }
                            }else if(type==12) {
                                data1 = couponModel.getData();
                                if(data1!=null) {
                                    tv_desc4.setText(data1.getTitle());
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

    /**
     * 热卖集合
     * @param pageNum
     * @param pageSize
     * @param type
     */
    List<ProductNormalModel.DataBean.ListBean>listBeans = new ArrayList<>();
    private void getHot(int pageNum, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNum, pageSize,type,null)
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

                            if(listBeans.size()==1) {
                                hotAdapter = new HotAdapter(R.layout.item_common_lists,listBeans);
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
                                rv_hot.setAdapter(hotAdapter);
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                            }else if(listBeans.size()==2) {
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_lists,listBeans);
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
                                rv_hot.setAdapter(hotAdapter);
                            }else {
                                rv_hot1.setVisibility(View.VISIBLE);
                                rv_hot.setVisibility(View.GONE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_listss,listBeans);
                                rv_hot1.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
                                rv_hot1.setAdapter(hotAdapter);
                            }

                        } else {
                            ToastUtil.showErroMsg(mActivity,getCommonProductModel.getMessage());
                        }
                    }
                });
    }

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
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }

    private void addCar(int businessId, String productCombinationPriceVOList, int businessType, String totalNum) {
        AddCartAPI.requestData(mActivity, businessId, productCombinationPriceVOList, businessType, String.valueOf(totalNum))
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddCartModel addCartModel) {
                        if (addCartModel.success) {
                            AppHelper.showMsg(mActivity, "成功加入购物车");
                            getCartNum();
                        } else {
                            AppHelper.showMsg(mActivity, addCartModel.message);
                        }

                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        verticalBanner.stop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void initViews(View view) {
        binder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        context = getActivity();
        token = UserInfoHelper.getUserId(mActivity);
        isShow();
        getProductsList(1,10,"commonBuy");
        requestOrderNumTwo();
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset)>30) {
                    toolbar1.setVisibility(View.VISIBLE);
                }else {
                    toolbar1.setVisibility(View.GONE);
                }

                int totalScrollRange = appBarLayout.getTotalScrollRange();
                if(totalScrollRange ==Math.abs(verticalOffset)) {
                    flag = true;
                }else {
                    flag = false;
                }

                if(flag) {
                    ll_small_title.setVisibility(View.GONE);
                    ll_line.setVisibility(View.VISIBLE);
                }else {
                    ll_small_title.setVisibility(View.VISIBLE);
                    ll_line.setVisibility(View.GONE);

                }

            }
        });



        ll_small_title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(flag) {
                    ll_small_title.setVisibility(View.GONE);
                    ll_line.setVisibility(View.VISIBLE);
                    if(rb_new.isChecked()) {
                        v1.setVisibility(View.VISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        v3.setVisibility(View.INVISIBLE);
                        v4.setVisibility(View.INVISIBLE);

                    }else if(rb_must_common.isChecked()){
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.VISIBLE);
                        v3.setVisibility(View.INVISIBLE);
                        v4.setVisibility(View.INVISIBLE);

                    }else if(rb_info.isChecked()){
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        v3.setVisibility(View.VISIBLE);
                        v4.setVisibility(View.INVISIBLE);

                    }else if(rb_common.isChecked()){
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        v3.setVisibility(View.INVISIBLE);
                        v4.setVisibility(View.VISIBLE);
                    }
                }else {
                    ll_small_title.setVisibility(View.VISIBLE);
                    ll_line.setVisibility(View.GONE);

                }
            }
        });



        rg_new.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_new:
                        rb_info.setTextColor(Color.parseColor("#333333"));
                        rb_common.setTextColor(Color.parseColor("#333333"));
                        rb_must_common.setTextColor(Color.parseColor("#333333"));
                        rb_new.setTextColor(Color.parseColor("#17BD60"));
                        tv_title1.setTextColor(Color.parseColor("#ffffff"));
                        tv_title1.setBackgroundResource(R.drawable.shape_greenss);

                        tv_title2.setTextColor(Color.parseColor("#999999"));
                        tv_title2.setBackgroundResource(R.drawable.shape_white);

                        tv_title3.setTextColor(Color.parseColor("#999999"));
                        tv_title3.setBackgroundResource(R.drawable.shape_white);

                        tv_title4.setTextColor(Color.parseColor("#999999"));
                        tv_title4.setBackgroundResource(R.drawable.shape_white);
                        switchRb4();

                        break;

                    case R.id.rb_must_common:
                        rb_info.setTextColor(Color.parseColor("#333333"));
                        rb_common.setTextColor(Color.parseColor("#333333"));
                        rb_must_common.setTextColor(Color.parseColor("#333333"));
                        rb_new.setTextColor(Color.parseColor("#333333"));

                        rb_must_common.setTextColor(Color.parseColor("#17BD60"));
                        tv_title2.setTextColor(Color.parseColor("#ffffff"));
                        tv_title2.setBackgroundResource(R.drawable.shape_greenss);

                        tv_title1.setTextColor(Color.parseColor("#999999"));
                        tv_title1.setBackgroundResource(R.drawable.shape_white);

                        tv_title3.setTextColor(Color.parseColor("#999999"));
                        tv_title3.setBackgroundResource(R.drawable.shape_white);

                        tv_title4.setTextColor(Color.parseColor("#999999"));
                        tv_title4.setBackgroundResource(R.drawable.shape_white);
                        switchRb5();
                        break;

                    case R.id.rb_info:
                        rb_info.setTextColor(Color.parseColor("#17BD60"));
                        rb_common.setTextColor(Color.parseColor("#333333"));
                        rb_must_common.setTextColor(Color.parseColor("#333333"));
                        rb_new.setTextColor(Color.parseColor("#333333"));
                        tv_title2.setTextColor(Color.parseColor("#999999"));
                        tv_title2.setBackgroundResource(R.drawable.shape_white);

                        tv_title1.setTextColor(Color.parseColor("#999999"));
                        tv_title1.setBackgroundResource(R.drawable.shape_white);

                        tv_title3.setTextColor(Color.parseColor("#ffffff"));
                        tv_title3.setBackgroundResource(R.drawable.shape_greenss);

                        tv_title4.setTextColor(Color.parseColor("#999999"));
                        tv_title4.setBackgroundResource(R.drawable.shape_white);
                        switchRb6();
                        break;

                    case R.id.rb_common:
                        rb_info.setTextColor(Color.parseColor("#333333"));
                        rb_common.setTextColor(Color.parseColor("#17BD60"));
                        rb_must_common.setTextColor(Color.parseColor("#333333"));
                        rb_new.setTextColor(Color.parseColor("#333333"));
                        rb_info.setTextColor(Color.parseColor("#333333"));

                        tv_title2.setTextColor(Color.parseColor("#999999"));
                        tv_title2.setBackgroundResource(R.drawable.shape_white);

                        tv_title1.setTextColor(Color.parseColor("#999999"));
                        tv_title1.setBackgroundResource(R.drawable.shape_white);

                        tv_title3.setTextColor(Color.parseColor("#999999"));
                        tv_title3.setBackgroundResource(R.drawable.shape_white);

                        tv_title4.setTextColor(Color.parseColor("#ffffff"));
                        tv_title4.setBackgroundResource(R.drawable.shape_greenss);

                        switchRb7();

                        break;
                }
            }
        });


        //顶部推荐
        indexRecommendAdapter = new IndexRecommendAdapter(R.layout.item_index_recommend,recommendList);
        rv_recommend.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        rv_recommend.setAdapter(indexRecommendAdapter);

        indexRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,SearchReasultActivity.class);
                intent.putExtra(AppConstant.SEARCHWORD,recommendList.get(position));
                startActivity(intent);
            }
        });


        tv_search1.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_change_address.setOnClickListener(this);
    }

    private void isShow() {
        CityChangeAPI.isShow(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsShowModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsShowModel isShowModel) {
                        if(isShowModel.isSuccess()) {
                            if(isShowModel.data!=null) {
                                SharedPreferencesUtil.saveString(mActivity,"priceType",isShowModel.getData().enjoyProduct);
                            }
                        }else {
                            AppHelper.showMsg(mActivity,isShowModel.getMessage());
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
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrivacyModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PrivacyModel privacyModel) {

                        if(privacyModel.isSuccess()) {
                            String content = privacyModel.getData().getContent();
                            privacyDialog = new PrivacyDialog(mActivity,content);
                            if(privacyModel.getData().getOpen().equals("1")) {
                                privacyDialog.show();
                            }else {
                                privacyDialog.dismiss();
                                getCouponList();
                            }

                        }else {
                            AppHelper.showMsg(mActivity,privacyModel.getMessage());
                        }
                    }
                });
    }

    private void getProductsList(int pageNums, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNums, pageSize,type,null)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
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
                            if(getCommonProductModel.getData().getList().size()>0) {
                                switchRb7();
                                rb_common.setChecked(true);
                            }else {
                                switchRb4();
                                rb_new.setChecked(true);
                            }
                        }else {
                            AppHelper.showMsg(mActivity,getCommonProductModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 常用清单
     */
    private void switchRb7() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (commonFragment == null) {
            commonFragment = new CommonFragment();
            fragmentTransaction.add(R.id.content, commonFragment, CommonFragment.class.getCanonicalName());
        }

        fragmentTransaction.show(commonFragment);

        if (reduceFragment != null) {
            fragmentTransaction.hide(reduceFragment);
        }

        if (mustFragment != null) {
            fragmentTransaction.hide(mustFragment);
        }

        if (newFragment != null) {
            fragmentTransaction.hide(newFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 咨讯
     */
    ReduceFragment reduceFragment;
    private void switchRb6() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
//        if (infoFragment == null) {
            reduceFragment = new ReduceFragment();
            Bundle bundle = new Bundle();
            bundle.putString("URL",questUrl);
            bundle.putInt("TYPE",2);
            bundle.putString("name","consult");
            reduceFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content, reduceFragment, ReduceFragment.class.getCanonicalName());
//        }

        fragmentTransaction.show(reduceFragment);

        if (mustFragment != null) {
            fragmentTransaction.hide(mustFragment);
        }

        if (newFragment != null) {
            fragmentTransaction.hide(newFragment);
        }

        if (commonFragment != null) {
            fragmentTransaction.hide(commonFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 必买
     */
    private void switchRb5() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (mustFragment == null) {
            mustFragment = new MustFragment();
            fragmentTransaction.add(R.id.content, mustFragment, MustFragment.class.getCanonicalName());
        }

        fragmentTransaction.show(mustFragment);

        if (reduceFragment != null) {
            fragmentTransaction.hide(reduceFragment);
        }

        if (newFragment != null) {
            fragmentTransaction.hide(newFragment);
        }

        if (commonFragment != null) {
            fragmentTransaction.hide(commonFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 新品
     */
    private void switchRb4() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (newFragment == null) {
            newFragment = new NewFragment();
            fragmentTransaction.add(R.id.content, newFragment, NewFragment.class.getCanonicalName());
        }
        fragmentTransaction.show(newFragment);

        if (reduceFragment != null) {
            fragmentTransaction.hide(reduceFragment);
        }

        if (mustFragment != null) {
            fragmentTransaction.hide(mustFragment);
        }

        if (commonFragment != null) {
            fragmentTransaction.hide(commonFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 更新购物车角标
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
                    AppHelper.showMsg(mActivity, getCartNumModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {

            }
        });
    }


    @Override
    public void findViewById(View view) {

    }
    TeamAdapter teamAdapter;
    Team3Adapter team3Adapter;

    @SuppressLint("NewApi")
    @Override
    public void setViewData() {

        for (int i = 0; i < 200; i++) {
            listss.add(""+i);
        }
        frameLayout.setNestedScrollingEnabled(false);
        rv_test.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_test.setAdapter(new MyAdapter(mActivity,listss));
        //秒杀短
        skill5Adapter = new Skill5Adapter(mActivity,skillActive3);
        rv_auto_view.setAdapter(skill5Adapter);
        rv_auto_view.setLayoutManager(new GridLayoutManager(mActivity,4));

        //满赠1
        PagerSnapHelper snapFull = new PagerSnapHelper();
        commonssAdapter = new CommonssAdapter(mActivity, fullActive1);
        rv_auto_view1.setAdapter(commonssAdapter);
        snapFull.attachToRecyclerView(rv_auto_view1);
        initRecycles();

        //满赠2
        fullAdapter = new FullAdapter(mActivity,fullActive1);
        rv_given.setAdapter(fullAdapter);
        rv_given.setLayoutManager(new GridLayoutManager(mActivity,1));

        //组合
        teamAdapter = new TeamAdapter(R.layout.item_team_lists,teamActive1);
        rv_team.setLayoutManager(new GridLayoutManager(mActivity,1));
        rv_team.setAdapter(teamAdapter);

        //组合2
        team3Adapter = new Team3Adapter(R.layout.item_teams_list,teamActive1);
        rv_team.setLayoutManager(new GridLayoutManager(mActivity,1));
        rv_team.setAdapter(team3Adapter);


        rl_more.setOnClickListener(this);
        rl_more2.setOnClickListener(this);
        rl_more3.setOnClickListener(this);
        rl_more4.setOnClickListener(this);
        rl_address.setOnClickListener(null);
        requestUpdate();
        getHot(1,10,"hot");
        refreshLayout.autoRefresh();
        lav_activity_loading.show();
        couponListAdapter = new CouponListAdapter(R.layout.item_home_coupon_list,lists);
        getPrivacys();
        getCustomerPhone();
        isSend();
        hotKey();
        classifyList();
        getOrder();
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);
        mSmoothScroller = new LinearSmoothScroller(mActivity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 3f / (displayMetrics.density);
            }
        };
        mSmoothScrollers = new LinearSmoothScroller(mActivity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 3f / (displayMetrics.density);
            }
        };
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PageNum = 1;
                newList.clear();
                skillList.clear();
                skillAdvList.clear();
                driverList.clear();
                isSend();
                hotKey();
                getBaseLists();
                isTurn();
                getCustomerPhone();
//                getDriveInfo();
                classifyList();
                EventBus.getDefault().post(new BackEvent());
                refreshLayout.finishRefresh();
            }
        });

//        rvIconAdapter = new RvIconAdapter(R.layout.item_home_icon,classifyList);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(context, 2,RecyclerView.HORIZONTAL, false);
//
//        rvIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                context.startActivity(new Intent(context, HomeActivity.class));
//                EventBus.getDefault().post(new GoToMarketEvent());
//                EventBus.getDefault().postSticky(new FromIndexEvent(classifyList.get(position).getId()+""));
//            }
//        });
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
    }

    private void getPrivacys() {
        IndexHomeAPI.getPrivacy(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrivacyModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PrivacyModel privacyModel) {

                        if(privacyModel.isSuccess()) {
                            String content = privacyModel.getData().getContent();
                            privacyDialog = new PrivacyDialog(mActivity,content);
                            if(!SharedPreferencesUtil.getString(mActivity,"once").equals("0")) {
                                privacyDialog.show();
                            }else {
                                privacyDialog.dismiss();
                            }

                        }else {
                            AppHelper.showMsg(mActivity,privacyModel.getMessage());
                        }
                    }
                });
    }

    private Disposable mAutoTask;
    private LinearSmoothScroller mSmoothScroller;
    private LinearSmoothScroller mSmoothScrollers;
    private int mCurrentPosition;

    @Override
    public void onResume() {
        super.onResume();
        startAuto();
        fullAdapter.start();
        teamAdapter.start();
        team3Adapter.start();
//        commonssAdapter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        stopAuto();
        fullAdapter.cancle();
        team3Adapter.cancle();
//        commonssAdapter.cancle();
    }

    private void startAuto() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        mAutoTask = Observable.interval(5, 2, TimeUnit.SECONDS).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {

            @Override
            public void accept(Long aLong) {
                if (mCurrentPosition == 0) {
                    mCurrentPosition = aLong.intValue();
                } else {
                    mCurrentPosition++;
                }
                mSmoothScroller.setTargetPosition(mCurrentPosition);
                RecyclerView.LayoutManager layoutManager = rv_auto_view.getLayoutManager();
                if (layoutManager!=null) {
                    layoutManager.startSmoothScroll(mSmoothScroller);
                }

            }
        });
    }

    private void isSend() {
        IndexHomeAPI.isSend(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendModel sendModel) {
                        if(sendModel.isSuccess()) {
                            if(sendModel.isData()) {
                                rl_address.setVisibility(View.GONE);
                            }else {
                                rl_address.setVisibility(View.VISIBLE);
                            }

                        }else {
                            AppHelper.showMsg(mActivity,sendModel.getMessage());
                        }
                    }
                });
    }

    private void isTurn() {
        IndexHomeAPI.isTurn(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsTurnModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsTurnModel turnModel) {
                        if(turnModel.isSuccess()) {
                            int isShow = turnModel.getData();
                            //1显示 0不显示
                            if(isShow==1) {
                                getTurn();
                            }else {
                                getPrivacy();
                            }
                        }else {
                            AppHelper.showMsg(mActivity,turnModel.getMessage());
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
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponListModel couponListModel) {
                        if(couponListModel.isSuccess()) {
                            if(couponListModel.getData()!=null) {
                                couponListModels = couponListModel;
                                lists = couponListModel.getData().getGifts();
                                couponListAdapter.notifyDataSetChanged();
                                couponListDialog = new CouponListDialog(mActivity,couponListModel,lists);

                                if(lists.size()>0) {
                                    couponListDialog.show();
                                }else {
                                    couponListDialog.dismiss();
                                    QueryHomePropup();
                                }

                            }else {
                               AppHelper.showMsg(context,couponListModel.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     *
     * 首页活动弹窗
     */
    private void QueryHomePropup() {
        QueryHomePropupAPI.requestQueryHomePropup(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
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
                            if(queryHomePropupModel.getData().getHomePropup()!=null) {
                                QueryHomePropupModel.DataBean.HomePropupBean homePropup = queryHomePropupModel.getData().getHomePropup();
                                homeActivityDialog = new HomeActivityDialog(mActivity,homePropup);
                                if (queryHomePropupModel.getData().isPropup()) {
                                    homeActivityDialog.show();
                                }else {
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
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TurnModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TurnModel turnModel) {
                        if(turnModel.isSuccess()) {
                            data2 = turnModel.getData();
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i <data2.size() ; i++) {
                                list.add(data2.get(i).getPoolNo());
                            }

                            turnTableDialog = new TurnTableDialog(mActivity,list);
                            turnTableDialog.show();
                        }else {
                            AppHelper.showMsg(mActivity,turnModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 弹出电话号码
     */
    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_timess;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(getActivity()).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_timess = mDialog.getWindow().findViewById(R.id.tv_time);
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
        tv_timess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(getActivity());
                mDialog.dismiss();
            }
        });
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
     * 订单状态
     */

    private void getOrder() {
        IndexHomeAPI.indexOrder(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderModel indexInfoModel) {
                        if(indexInfoModel.isSuccess()) {
                            if(indexInfoModel.getData().size()!=0) {
                                driverList.clear();
                                driverList.addAll(indexInfoModel.getData());
                                verticalBannerAdapter = new VerticalBannerAdapter(cell,driverList,getContext());
                                verticalBanner.setAdapter(verticalBannerAdapter);
                                verticalBanner.start();
                                ll_driver.setVisibility(View.VISIBLE);
                            }else {
                                ll_driver.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    /**
     * 搜索热词
     */
    List<IndexInfoModel.DataBean.ClassifyListBean> classifyLists;
    private void classifyList() {
        IndexHomeAPI.classifyList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndexInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndexInfoModel indexInfoModel) {
                        if(indexInfoModel.isSuccess()) {
                            classifyLists = indexInfoModel.getData().getClassifyList();
                            classifyList.addAll(classifyLists);
                            rvIconAdapter.notifyDataSetChanged();
                        }else {
                            AppHelper.showMsg(mActivity,indexInfoModel.getMessage());
                        }
                    }
                });
    }

    /**
     *
     */
    /**
     * 搜索热词
     */
    private void hotKey() {
        IndexHomeAPI.recommendList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RecommendModel recommendModel) {
                        if(recommendModel.isSuccess()) {
                            recommendList.clear();
                            recommendData = recommendModel.getData();
                            recommendList.addAll(recommendData);
                            indexRecommendAdapter.notifyDataSetChanged();
                        }else {
                            AppHelper.showMsg(mActivity,recommendModel.getMessage());
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
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
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
                        if(indexInfoModel.isSuccess()) {
                            data = indexInfoModel.getData();

                            iconList.clear();
                            iconList.addAll(data.getIcons());
                            if(data.getDeductAmountStr()!=null) {
                                deductAmountStr = data.getDeductAmountStr();
                            }


                            tv_times.setText("最快"+indexInfoModel.getData().getSendTime()+"小时送达");
                            tv_amount.setText("满"+indexInfoModel.getData().getSendAmount()+"元免配送费");
                            spikeNum = indexInfoModel.getData().getSpikeNum();
                            teamNum = indexInfoModel.getData().getTeamNum();
                            specialNum = indexInfoModel.getData().getSpecialNum();
                            fullGiftNum = indexInfoModel.getData().getFullGiftNum();

                            if(teamNum!=0&&fullGiftNum!=0) {
                                rl1.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.GONE);

                            }

                            if(teamNum==0&&fullGiftNum==0) {
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                            }

                            if(teamNum!=0&&fullGiftNum==0) {
                                rl1.setVisibility(View.GONE);
                                rl_full.setVisibility(View.GONE);
                                rl_team.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.VISIBLE);
                            }

                            if(fullGiftNum!=0&&teamNum==0) {
                                rl_full.setVisibility(View.VISIBLE);
                                rl_team.setVisibility(View.GONE);
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                            }


                            getSpikeList(12);
                            getSpikeList(3);
                            getSpikeList(2);
                            getSpikeList(11);
                            rvIconAdapter.notifyDataSetChanged();
                            questUrl = indexInfoModel.getData().getQuestUrl();

                            //----------------------------
                            tv_city.setText(data.getAddress());
                            Glide.with(mActivity).load(data.getOtherInfo()).into(iv_pic);
                            list.clear();
                            list1.clear();
                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                list.add(data.getBanners().get(i).getDefaultPic());

                            }

                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                if(indexInfoModel.getData().getBanners().get(i).getShowType()==2) {
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
                                        if(!TextUtils.isEmpty(banners.get(pos).getRgbColor())) {
                                            String rgbColor = banners.get(pos).getRgbColor();
                                            ll_bgc.setBackgroundColor(Color.parseColor("#"+rgbColor));
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
                        }else {

                            AppHelper.showMsg(mActivity, indexInfoModel.getMessage());
                            lav_activity_loading.hide();
                        }
                    }
                });
    }

    private void ClickBanner(List<IndexInfoModel.DataBean.BannersBean> banners) {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showType = banners.get(position).getShowType();
                if(showType==1|| banners.get(position).getLinkSrc()!=null) {
                    //链接 banners.get(position).getLinkSrc()
                    Intent intent = new Intent(getActivity(), NewWebViewActivity.class);
                    intent.putExtra("URL", banners.get(position).getLinkSrc());
                    intent.putExtra("TYPE", 2);
                    intent.putExtra("name", "");
//                    Log.d("wsdsssssssss.........", banners.get(position).getLinkSrc());
                    startActivity(intent);
                }
                else if(showType == 2|| banners.get(position).getDetailPic()!=null) {
                    //图片
                    AppHelper.showPhotoDetailDialog(mActivity, list1, position);
                }else if(showType == 3|| banners.get(position).getProdPage()!=null) {
                    //H5页面
                    if(AppConstant.KILL_PROD.equals(banners.get(position).getProdPage())) {
                        Intent intent = new Intent(getActivity(), HomeGoodsListActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.HOT_PROD.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), HotProductActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.COMMON_PROD.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), CommonProductActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.DEDUCT_PROD.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), ReductionProductActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.SPECIAL_PROD.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.TEAM_PROD.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.BALANCE.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), MyWalletNewActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.POINT.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), MinerIntegralActivity.class);
                        startActivity(intent);
                    }else if(AppConstant.GIFT.equals(banners.get(position).getProdPage())){
                        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                        startActivity(intent);
                    }
                }else if(showType ==4 ) {
                    //商品
                    int businessId = Integer.parseInt(banners.get(position).getBusinessId());
                    Intent intent = new Intent(getActivity(), CommonGoodsDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, businessId);
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity,"priceType"));
                    startActivity(intent);
                }else if(showType ==5 ) {
                    //活动
                    String businessType = banners.get(position).getBusinessType();
                    int businessId = Integer.parseInt(banners.get(position).getBusinessId());
                    if(businessType.equals("2")) {
                        Intent intent = new Intent(getActivity(), SeckillGoodActivity.class);
//                        intent.putExtra(AppConstant.NUM,businessId);
                        intent.putExtra("num","-1");
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity,"priceType"));
                        intent.putExtra(AppConstant.ACTIVEID,businessId);
                        startActivity(intent);
                    }else if(businessType.equals("3")) {
                        Intent intent = new Intent(getActivity(), SpecialGoodDetailActivity.class);
                        intent.putExtra(AppConstant.ACTIVEID, businessId);
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity,"priceType"));
                        startActivity(intent);
                    }else if(businessType.equals("11")) {
                        Intent intent = new Intent(getActivity(), SpecialGoodDetailActivity.class);
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity,"priceType"));
                        intent.putExtra(AppConstant.ACTIVEID,businessId);
                        startActivity(intent);
                    }

                }

            }
        });
    }
    /**
     * 获取更新
     */
    private void requestUpdate() {
        UpdateAPI.requestUpdate(getContext(), AppHelper.getVersion(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateModel updateModel) {
                        mModelUpdate = updateModel;
                        if (mModelUpdate.success) {
                            updateUpdate();
                        } else {
                            AppHelper.showMsg(mActivity, mModelUpdate.message);
                        }
                    }
                });

    }

    private void updateUpdate() {
        url = mModelUpdate.data.url;
        update = mModelUpdate.data.update;
        forceUpdate = mModelUpdate.data.forceUpdate;
        content = mModelUpdate.data.msg;
        if (update) {
            //因为服务器上面的是2.0.6，所以才会出现新版本和提示框的字样，只要上架之后重新上传一个2.0.7就可以了。
            //有更新
            UserInfoHelper.saveGuide(mActivity, "");
            showUpdateDialog();
        }
    }

    /**
     * 更新弹窗
     */
    private void showUpdateDialog() {
        final AlertDialog mDialog = new AlertDialog.Builder(getContext()).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.update_dialog);
        Button mBtnForceUpdate = (Button) mDialog.getWindow().findViewById(R.id.btnForceUpdate);
        Button mBtnCancel = (Button) mDialog.getWindow().findViewById(R.id.btnCancel);
        Button mBtnOK = (Button) mDialog.getWindow().findViewById(R.id.btnOK);
        LinearLayout mLlButton = (LinearLayout) mDialog.getWindow().findViewById(R.id.llButton);
        TextView mTvContent = (TextView) mDialog.getWindow().findViewById(R.id.tvContent);

        mTvContent.setText(content);
        if (forceUpdate) {
            mDialog.setCancelable(false);
            mLlButton.setVisibility(View.GONE);
            mBtnForceUpdate.setVisibility(View.VISIBLE);
        } else {
            mDialog.setCancelable(true);
            mLlButton.setVisibility(View.VISIBLE);
            mBtnForceUpdate.setVisibility(View.GONE);
        }
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 下载
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                } catch (Exception e) {

                }
                mDialog.dismiss();
            }
        });
        mBtnForceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 下载
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url.contains("http://") ? ("http://" + url) : url);
                    intent.setData(content_url);
                    startActivity(intent);
                } catch (Exception e) {

                }
                mDialog.dismiss();
            }
        });
    }


    @Override
    public void setClickEvent() {

    }
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(context,SearchStartActivity.class);
                intent.putExtra(AppConstant.SEARCHTYPE, AppConstant.HOME_SEARCH);
                intent.putExtra("flag", "first");
                intent.putExtra("good_buy", "");
                startActivity(intent);
                break;

            case R.id.tv_search1:
                Intent intent1 = new Intent(context,SearchStartActivity.class);
                intent1.putExtra(AppConstant.SEARCHTYPE, AppConstant.HOME_SEARCH);
                intent1.putExtra("flag", "first");
                intent1.putExtra("good_buy", "");
                startActivity(intent1);
                break;

            case R.id.rl_message:
                Intent intent2 = new Intent(context,TestActivity4.class);
                startActivity(intent2);
//                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getActivity()))) {
//                    Intent intents = new Intent(getActivity(), MessageCenterActivity.class);
//                    startActivityForResult(intents, 101);
////                    Intent intent2 = new Intent(getActivity(), Test1Activity.class);
////                    startActivity(intent2);
//
//                } else {
//                    initDialog();
//                }
                break;

            case R.id.tv_city:
                //选择城市
                if(data!=null) {
                    Intent messageIntent = new Intent(getActivity(), ChooseAddressActivity.class);
                    messageIntent.putExtra("cityName",data.getCityName());
                    messageIntent.putExtra("areaName",data.getAreaName());
//                    startActivityForResult(messageIntent, 104);
                    startActivity(messageIntent);
                }

                break;

            case R.id.rl_more:
                //秒杀专区
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getActivity()))) {
                    Intent secIntent = new Intent(getActivity(), HomeGoodsListActivity.class);
                    startActivity(secIntent);
                } else {
                    initDialog();
                }


                break;

            case R.id.rl_more2:
                //精选折扣
                Intent specialIntent = new Intent(getActivity(), CouponDetailActivity.class);
                startActivity(specialIntent);
                break;

            case R.id.rl_more3:
                //超值组合
                Intent teamIntent = new Intent(getActivity(), TeamDetailActivity.class);
                startActivity(teamIntent);
                break;
            case R.id.rl_more4:
                //满赠
                Intent fullIntent = new Intent(getActivity(), FullGiftActivity.class);
                startActivity(fullIntent);
                break;
            case R.id.tv_change:
                Intent changeCityIntent = new Intent(getActivity(), ChangeCityActivity.class);
                startActivityForResult(changeCityIntent, 105);
                break;
            case R.id.tv_change_address:
                chooseAddressDialog = new ChooseHomeDialog(mActivity,"");
                chooseAddressDialog.show();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 102) {
                int newPosition = data.getIntExtra("NewPosition", 5);//NewPosition
                if (newPosition > 0) {
                    tv_num.setVisibility(View.VISIBLE);
                    tv_num.setText("  " + newPosition + "  ");
                } else {
                    tv_num.setVisibility(View.GONE);
                }
            }
        }

        if (requestCode == 104) {
            newList.clear();
            skillList.clear();
            skillAdvList.clear();
            getBaseLists();
            EventBus.getDefault().post(new BackEvent());
        }

        if (requestCode == 105) {
            refreshLayout.autoRefresh();
        }

    }




    private void stopAuto() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rv_skill.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition()+1);
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);
        rv_skill.setLayoutManager(layoutManager);
    }

    //满赠滑动
    private void initRecycles() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rv_auto_view1.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition()+1);
            }
        }, 3000, 2000, TimeUnit.MILLISECONDS);
        rv_auto_view1.setLayoutManager(layoutManager);
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        String banner_url = slider.getBundle().getString("banner_url");
        if (StringHelper.notEmptyAndNull(banner_url)) {
            Intent intent = new Intent(getActivity(), NewWebViewActivity.class);
            intent.putExtra("URL", banner_url);
            intent.putExtra("TYPE", 2);
            intent.putExtra("name", "");
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(LogoutsEvent event) {
        //刷新UI
        refreshLayout.autoRefresh();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginsEvent(AddressEvent event) {
        //刷新UI
        refreshLayout.autoRefresh();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum(UpNumEvent event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum0(UpDateNumEvent0 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum1(UpDateNumEvent1 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum2(UpDateNumEvent2 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum3(UpDateNumEvent3 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum7(UpDateNumEvent7 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum8(UpDateNumEvent8 event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum10(UpDateNumEvent10 event) {
        getCartNum();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityEvent(CityEvent event) {
        refreshLayout.autoRefresh();
        chooseAddressDialog.dismiss();
//        rootview.setTranslationY(0);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void topEvent(TopEvent event) {
        appbar.setExpanded(true);
        nestedScrollView.smoothScrollTo(0,0);
    }

}
