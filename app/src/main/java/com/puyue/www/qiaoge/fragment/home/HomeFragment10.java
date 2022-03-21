package com.puyue.www.qiaoge.fragment.home;

import android.animation.IntEvaluator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.AutoPollRecyclerView;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.BannerActivity;
import com.puyue.www.qiaoge.activity.ChooseCompanyActivity;
import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.FullListActivity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.HuoDetailActivity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.home.ChangeCityActivity;
import com.puyue.www.qiaoge.activity.home.ChooseAddressActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.home.HomeGoodsListActivity;
import com.puyue.www.qiaoge.activity.home.SearchReasultActivity;
import com.puyue.www.qiaoge.activity.home.SearchStartActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.activity.mine.MessageCenterActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.MyCouponsActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.activity.mine.order.MyOrdersActivity;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MinerIntegralActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletNewActivity;
import com.puyue.www.qiaoge.adapter.CommonCouponAdapter;
import com.puyue.www.qiaoge.adapter.CommonsAdapter;
import com.puyue.www.qiaoge.adapter.CommonssAdapter;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.adapter.FullAdapter;
import com.puyue.www.qiaoge.adapter.HotAdapter;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.Skill2Adapter;
import com.puyue.www.qiaoge.adapter.Skill3Adapter;
import com.puyue.www.qiaoge.adapter.Team3Adapter;
import com.puyue.www.qiaoge.adapter.TeamAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonProductActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.adapter.home.ReductionProductActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.UpdateAPI;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.banner.listener.OnBannerListener;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseHomeDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.HuoOrderDialog;
import com.puyue.www.qiaoge.dialog.Privacy4Dialog;
import com.puyue.www.qiaoge.dialog.PrivacysDialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
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
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.ChangeCityModel;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.HomeNewRecommendModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
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
//import io.reactivex.disposables.Disposable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by ${王涛} on 2021/2/22
 */
public class HomeFragment10 extends BaseFragment implements View.OnClickListener,BaseSliderView.OnSliderClickListener{
    Unbinder bind;
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
    @BindView(R.id.rl2)
    RelativeLayout rl2;
//    @BindView(R.id.ll1)
//    LinearLayout ll1;
    @BindView(R.id.indicator)
    HIndicators indicator;
    @BindView(R.id.rv_icon)
    RecyclerView rv_icon;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.iv_location)
    ImageView iv_location;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_fill)
    ImageView iv_fill;
    @BindView(R.id.ll_driver)
    LinearLayout ll_driver;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.homeMessage)
    ImageView homeMessage;
    @BindView(R.id.rl_more)
    RelativeLayout rl_more;
    @BindView(R.id.rl_more3)
    RelativeLayout rl_more3;
    @BindView(R.id.rl_more6)
    RelativeLayout rl_more6;
    @BindView(R.id.rl_more4)
    RelativeLayout rl_more4;
    @BindView(R.id.rb_new)
    RadioButton rb_new;
    @BindView(R.id.rb_must_common)
    RadioButton rb_must_common;
    @BindView(R.id.rb_reduce)
    RadioButton rb_reduce;
    @BindView(R.id.rb_common)
    RadioButton rb_common;

    @BindView(R.id.rb_new_top)
    RadioButton rb_new_top;
    @BindView(R.id.rb_must_common_top)
    RadioButton rb_must_common_top;
    @BindView(R.id.rb_info_top)
    RadioButton rb_info_top;
    @BindView(R.id.rb_common_top)
    RadioButton rb_common_top;
    @BindView(R.id.tv_full_title1)
    TextView tv_full_title1;

    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.rv_team)
    AutoPollRecyclerView rv_team;
    @BindView(R.id.tv_team_title)
    TextView tv_team_title;
    @BindView(R.id.tv_team_title1)
    TextView tv_team_title1;
    @BindView(R.id.rv_given)
    AutoPollRecyclerView rv_given;
    @BindView(R.id.v1s)
    View v1s;
    @BindView(R.id.v2s)
    View v2s;
    @BindView(R.id.v3s)
    View v3s;
    @BindView(R.id.v4s)
    View v4s;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.tv_title2)
    TextView tv_title2;
    @BindView(R.id.tv_title3)
    TextView tv_title3;
    @BindView(R.id.tv_title4)
    TextView tv_title4;
    @BindView(R.id.ll_small_title)
    LinearLayout ll_small_title;
    @BindView(R.id.snap)
    SnapUpCountDownTimerViewss snap;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.tv_change_address)
    TextView tv_change_address;
    @BindView(R.id.tv_skill_title)
    TextView tv_skill_title;
    @BindView(R.id.tv_times)
    TextView tv_times;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.ll_bgc)
    RelativeLayout ll_bgc;
    @BindView(R.id.rv_skill)
    AutoPollRecyclerView rv_skill;
    @BindView(R.id.rg_new)
    RadioGroup rg_new;
    @BindView(R.id.rg_new_top)
    RadioGroup rg_new_top;
    @BindView(R.id.ll_skill)
    LinearLayout ll_skill;
    @BindView(R.id.rv_auto_view)
    RecyclerView rv_auto_view;
    @BindView(R.id.rv_auto_team)
    AutoPollRecyclerView rv_auto_team;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.rv_hot1)
    RecyclerView rv_hot1;
    @BindView(R.id.tv_coupon_more)
    TextView tv_coupon_more;
    @BindView(R.id.iv_full2_right)
    ImageView iv_full2_right;
    @BindView(R.id.iv_full_right)
    ImageView iv_full_right;
    @BindView(R.id.iv_team_right)
    ImageView iv_team_right;
    @BindView(R.id.iv_team2_right)
    ImageView iv_team2_right;
    @BindView(R.id.tv_look_more)
    TextView tv_look_more;
    @BindView(R.id.verticalBanner)
    VerticalBannerView verticalBanner;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.iv_tip)
    ImageView iv_tip;
    @BindView(R.id.rl_grand)
    RelativeLayout rl_grand;
    @BindView(R.id.ll_parent)
    RelativeLayout ll_parent;
    @BindView(R.id.rl_inSide)
    RelativeLayout rl_inSide;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.ll_parent_top)
    RelativeLayout ll_parent_top;
    @BindView(R.id.ll_hot)
    LinearLayout ll_hot;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.tv_full_title)
    TextView tv_full_title;
    @BindView(R.id.tv_coupon_title)
    TextView tv_coupon_title;
    @BindView(R.id.tv_small_coupon_title)
    TextView tv_small_coupon_title;
    @BindView(R.id.tv_small_hot_title)
    TextView tv_small_hot_title;
    @BindView(R.id.tv_hot_title)
    TextView tv_hot_title;
    @BindView(R.id.rl_more5)
    RelativeLayout rl_more5;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.tv_look)
    TextView tv_look;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.rl_huo)
    RelativeLayout rl_huo;
    List<String> list = new ArrayList<>();
    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 72;
    private int evaluatemargin;
    private int evaluatetop;
    private RelativeLayout.LayoutParams layoutParams;
    int scrollLength;
    HotAdapter hotAdapter;
    IndexRecommendAdapter indexRecommendAdapter;
    CouponDialog couponDialog;
    FullAdapter fullAdapter;
    TeamAdapter teamAdapter;
    Team3Adapter team3Adapter;
    private String cell; // 客服电话
    private PrivacysDialog privacyDialog;
    ChooseHomeDialog chooseAddressDialog;
    List<String> recommendData;
    List<ProductNormalModel.DataBean.ListBean> listss = new ArrayList<>();
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
    int PageNum = 1;
    private UpdateModel mModelUpdate;
    private boolean update;
    private boolean forceUpdate;
    private String content;
    private String url;//更新所用的url
    private AlertDialog mTypedialog;
    List<String> list1 = new ArrayList<>();
    private IndexInfoModel.DataBean data;
    //分类列表
    private List<IndexInfoModel.DataBean.ClassifyListBean> classifyList = new ArrayList<>();
    NewFragment newFragment;
    MustFragment mustFragment;
    CommonFragment commonFragment;
    private String questUrl;
    private CouponModels.DataBean data1;
    private int showType;
    private CommonCouponAdapter commonCouponAdapter;
    private CommonAdapter commonAdapter;
    private List<CouponModels.DataBean.SpikeBean.ActivesBean> skillActive3 = new ArrayList<>();
    private List<CouponModels.DataBean.SpikeBean.ActivesBean> skillActive2 = new ArrayList<>();
    private List<CouponModels.DataBean.SpikeBean.ActivesBean> skillActive1 = new ArrayList<>();
    private List<CouponModels.DataBean.SpecialBean.ActivesBeanX> couponActive1 = new ArrayList<>();
    private List<CouponModels.DataBean.SpecialBean.ActivesBeanX> couponActive2 = new ArrayList<>();
    private List<CouponModels.DataBean.SpecialBean.ActivesBeanX> couponActive3 = new ArrayList<>();
    private List<CouponModels.DataBean.TeamBean.ActivesBeanX> teamActive1 = new ArrayList<>();
    private List<CouponModels.DataBean.FullGiftBean.ActivesBeanXX> fullActive1 = new ArrayList<>();

    private long currentTime;
    private long startTime;
    private long endTime;
    private Date currents;
    private Date starts;
    private VerticalBannerAdapter verticalBannerAdapter;
    private CouponListDialog couponListDialog;
    IndexInfoModel.DataBean couponListModels;
    private CouponListAdapter couponListAdapter;
    private List<CouponListModel.DataBean.GiftsBean> lists;
    private List<TurnModel.DataBean> data2;
    private TurnTableDialog turnTableDialog;
    private SkillAdapter skillAdapter;
    private Skill2Adapter skill2Adapter;
    private Skill3Adapter skill3Adapter;
    private HomeActivityDialog homeActivityDialog;
    CommonssAdapter commonssAdapter;
    int topHeight;
    @Override
    public int setLayoutId() {
        return R.layout.test10;
    }

    @Override
    public void initViews(View view) {
    }


    private void getDatas(long end) {
        RecommendApI.getDatas(mActivity,3,end)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);
        setListener();
        EventBus.getDefault().register(this);
        rl_grand.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int bar_height = rl_bar.getHeight();
                int scroll_height = ll_scroll.getHeight();

                scrollLength = Math.abs(scroll_height - bar_height);
                topHeight = DensityUtil.dip2px(scrollLength, mActivity);

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

                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    //企业
                                    iv_location.setImageResource(R.mipmap.icon_company_white);
                                }else {
                                    //城市
                                    iv_location.setImageResource(R.mipmap.icon_address2);
                                }

                                homeMessage.setImageResource(R.mipmap.ic_mine_email);
                                tv_city.setAlpha(percent);
//                                iv_tip.getBackground().setAlpha(1);
                                tv_city.setEnabled(true);
                                rl_search.requestLayout();
                            }
                        } else {
                            rl_bar.setAlpha(1);
                            if(layoutParams!=null){
                                layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,mActivity),85, DensityUtil.dip2px(ENDMARGINLEFT,mActivity), 0);
                                rl_search.requestLayout();
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    iv_location.setImageResource(R.mipmap.icon_company_black);
                                }else {
                                    iv_location.setImageResource(R.mipmap.icon_address1);
                                }

                                tv_city.setAlpha(0);
                                iv_tip.setAlpha(0);
                                tv_city.setEnabled(false);
                                homeMessage.setImageResource(R.mipmap.iv_message1);
                            }
                        }
                    }
                });
            }
        });


        commonssAdapter = new CommonssAdapter(mActivity,fullActive1);
        rv_auto_view1.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_auto_view1.setAdapter(commonssAdapter);
        rv_given.start();

        fullAdapter = new FullAdapter(mActivity, fullActive1);
        rv_given.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_given.setAdapter(fullAdapter);
        rv_given.start();

        team3Adapter = new Team3Adapter(mActivity,R.layout.item_teams_list, teamActive1);
        rv_team.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_team.setAdapter(team3Adapter);
        rv_team.start();

        teamAdapter = new TeamAdapter(mActivity,R.layout.item_team_lists, teamActive1);
        rv_auto_team.setAdapter(teamAdapter);
        rv_auto_team.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_auto_team.start();

        skill2Adapter = new Skill2Adapter(mActivity, R.layout.item_skill_lists, skillActive2, "0");
        rv_skill.setAdapter(skill2Adapter);
        rv_skill.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_skill.start();

        //顶部推荐
        indexRecommendAdapter = new IndexRecommendAdapter(R.layout.item_index_recommend, recommendList);
        rv_recommend.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_recommend.setAdapter(indexRecommendAdapter);

        indexRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,SearchReasultActivity.class);
                intent.putExtra(AppConstant.SEARCHWORD,recommendList.get(position));
                startActivity(intent);
            }
        });
        tv_change.setOnClickListener(this);
        tv_change_address.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        rl_more5.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        rl_more3.setOnClickListener(this);
        rl_more6.setOnClickListener(this);
        rl_more4.setOnClickListener(this);
        tv_look_more.setOnClickListener(this);
        tv_coupon_more.setOnClickListener(this);
        rl_huo.setOnClickListener(this);
        tv_look.setOnClickListener(this);
        rl_address.setOnClickListener(null);
        requestUpdate();
        refreshLayout.autoRefresh();
        lav_activity_loading.show();
        couponListAdapter = new CouponListAdapter(R.layout.item_home_coupon_list, lists);
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
                getBaseLists();
                EventBus.getDefault().post(new BackEvent());
                refreshLayout.finishRefresh();
            }
        });

        rv_icon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                indicator.bindRecyclerView(rv_icon);
            }
        });
        //判断用户是否选择了企业
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            Log.d("vfdfvdf........","123");
            getStyle();
        }


    }

    //判断用户是否选择了企业
    private void getStyle() {
        IndexHomeAPI.chooseCity(mActivity,1)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ChangeCityModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ChangeCityModel changeCityModel) {
                        if(changeCityModel.getCode()==1) {
                           if(!changeCityModel.isData()) {
                               Intent intent = new Intent(mActivity,ChooseCompanyActivity.class);
                               startActivity(intent);
                           }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,changeCityModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }

    private void getSpikeList() {
        IndexHomeAPI.getHomeCouponList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CouponModels>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CouponModels couponModel) {
                        if (couponModel.isSuccess()) {

                            if (couponModel.getData().getSpike()!=null) {
                                data1 = couponModel.getData();
                                tv_skill_title.setText(data1.getSpike().getTitle());
                                currentTime = System.currentTimeMillis();
                                startTime = couponModel.getData().getSpike().getStartTime();
                                    if (data1.getSpike().getActives().size() == 1) {
                                        skillActive1.clear();
                                        skillActive1.addAll(data1.getSpike().getActives());
                                        skillAdapter = new SkillAdapter(mActivity, R.layout.item_skill_lists, skillActive1, "1");
                                        rv_skill.setAdapter(skillAdapter);
                                        rv_skill.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skillAdapter.notifyDataSetChanged();
                                        ll_skill.setVisibility(View.VISIBLE);
                                    } else if (data1.getSpike().getActives().size() == 2||data1.getSpike().getActives().size() >4) {
                                        skillActive2.clear();
                                        skillActive2.addAll(data1.getSpike().getActives());
                                        ll_skill.setVisibility(View.VISIBLE);
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill2Adapter.notifyDataSetChanged();

                                    } else if (data1.getSpike().getActives().size() == 3) {
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getSpike().getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list, skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity, 3));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        ll_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                    } else if (data1.getSpike().getActives().size() == 4) {
                                        skillActive3.clear();
                                        skillActive3.addAll(data1.getSpike().getActives());
                                        skill3Adapter = new Skill3Adapter(R.layout.item_skill_list4, skillActive3);
                                        rv_skill.setAdapter(skill3Adapter);
                                        rv_skill.setLayoutManager(new GridLayoutManager(mActivity, 4));
                                        rv_skill.setVisibility(View.VISIBLE);
                                        skill3Adapter.notifyDataSetChanged();
                                        ll_skill.setVisibility(View.VISIBLE);
                                    }

                                    endTime = couponModel.getData().getSpike().getEndTime();
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
                                        tv_time.setVisibility(View.GONE);
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
                                        tv_time.setText(data1.getSpike().getStartTimeStr()+"开抢");
                                        tv_time.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    ll_skill.setVisibility(View.GONE);
                                }

                                if(couponModel.getData().getSpecial()!=null) {
                                    data1 = couponModel.getData();
                                    if(data1.getSpecial().getActives().size()>0) {
                                        ll_coupon.setVisibility(View.VISIBLE);
                                        tv_coupon_title.setText(data1.getSpecial().getTitle());
                                        tv_small_coupon_title.setText(data1.getSpecial().getDesc());
                                    }else {
                                        ll_coupon.setVisibility(View.GONE);
                                    }

                                    if (data1.getSpecial().getActives().size() == 1) {
                                        couponActive1.clear();
                                        couponActive1.addAll(data1.getSpecial().getActives());
                                        commonAdapter = new CommonAdapter(R.layout.item_common_lists, couponActive1);
                                        rv_coupon.setAdapter(commonAdapter);
                                        rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity));
                                        commonAdapter.notifyDataSetChanged();
                                        rv_coupon1.setVisibility(View.GONE);
                                        rv_coupon.setVisibility(View.VISIBLE);
                                    } else if (data1.getSpecial().getActives().size() == 2) {
                                        couponActive2.clear();
                                        couponActive2.addAll(data1.getSpecial().getActives());
                                        commonCouponAdapter = new CommonCouponAdapter(mActivity, 11 + "", R.layout.item_coupon_lists, couponActive2);
                                        rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                        rv_coupon.setAdapter(commonCouponAdapter);
                                        commonCouponAdapter.notifyDataSetChanged();
                                        rv_coupon1.setVisibility(View.GONE);
                                        rv_coupon.setVisibility(View.VISIBLE);
                                    } else {
                                        couponActive3.clear();
                                        couponActive3.addAll(data1.getSpecial().getActives());
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

                            if (couponModel.getData().getTeam() !=null && couponModel.getData().getFullGift() != null) {
                                rl1.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.GONE);
                                //满赠
                                fullActive1.clear();
                                tv_full_title.setText(couponModel.getData().getFullGift().getTitle());
                                tv_full_title1.setText(couponModel.getData().getFullGift().getDesc());
                                fullActive1.addAll(couponModel.getData().getFullGift().getActives());
                                fullAdapter.notifyDataSetChanged();

                                //组合
                                teamActive1.clear();
                                tv_team_title.setText(couponModel.getData().getTeam().getTitle());
                                tv_team_title1.setText(couponModel.getData().getTeam().getTitle());
                                teamActive1.addAll(couponModel.getData().getTeam().getActives());
                                team3Adapter.notifyDataSetChanged();


                            }else if(couponModel.getData().getTeam() !=null && couponModel.getData().getFullGift() ==null) {
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl_team.setVisibility(View.VISIBLE);
                                rl_full.setVisibility(View.GONE);

                                teamActive1.clear();
                                tv_team_title.setText(couponModel.getData().getTeam().getTitle());
                                tv_team_title1.setText(couponModel.getData().getTeam().getTitle());
                                teamActive1.addAll(couponModel.getData().getTeam().getActives());
                                teamAdapter.notifyDataSetChanged();

                            }else if(couponModel.getData().getFullGift() != null && couponModel.getData().getTeam() == null) {
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl_team.setVisibility(View.GONE);
                                rl_full.setVisibility(View.VISIBLE);

                                fullActive1.clear();
                                tv_full_title.setText(couponModel.getData().getFullGift().getTitle());
                                tv_full_title1.setText(couponModel.getData().getFullGift().getDesc());
                                fullActive1.addAll(couponModel.getData().getFullGift().getActives());
                                commonssAdapter.notifyDataSetChanged();

                            }else  {
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    /**
     * 热卖集合
     *
     * @param pageNum
     * @param pageSize
     * @param type
     */
    List<ProductNormalModel.DataBean.ListBean> listBeans = new ArrayList<>();

    private void getHot(int pageNum, int pageSize, String type) {
        ProductListAPI.requestData(mActivity, pageNum, pageSize, type, null)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
                                hotAdapter = new HotAdapter(R.layout.item_common_lists, listBeans, new HotAdapter.Onclick() {
                                    @Override
                                    public void addDialog() {

                                    }

                                    @Override
                                    public void tipClick() {
                                        AppHelper.ShowAuthDialog(mActivity,cell);
                                    }
                                });
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot.setAdapter(hotAdapter);
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                            } else if (listBeans.size() == 2) {
                                rv_hot1.setVisibility(View.GONE);
                                rv_hot.setVisibility(View.VISIBLE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_lists, listBeans, new HotAdapter.Onclick() {
                                    @Override
                                    public void addDialog() {

                                    }

                                    @Override
                                    public void tipClick() {
                                        AppHelper.ShowAuthDialog(mActivity,cell);
                                    }
                                });
                                rv_hot.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot.setAdapter(hotAdapter);
                            } else {
                                rv_hot1.setVisibility(View.VISIBLE);
                                rv_hot.setVisibility(View.GONE);
                                hotAdapter = new HotAdapter(R.layout.item_coupon_listss, listBeans, new HotAdapter.Onclick() {
                                    @Override
                                    public void addDialog() {

                                    }

                                    @Override
                                    public void tipClick() {
                                        AppHelper.ShowAuthDialog(mActivity,cell);
                                    }
                                });
                                rv_hot1.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                                rv_hot1.setAdapter(hotAdapter);
                            }

                        } else {
                            ToastUtil.showErroMsg(mActivity, getCommonProductModel.getMessage());
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


    private void isShow() {
        CityChangeAPI.isShow(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<IsShowModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsShowModel isShowModel) {
                        if (isShowModel.isSuccess()) {
                            if (isShowModel.data != null) {
                                SharedPreferencesUtil.saveString(mActivity, "priceType", isShowModel.getData().enjoyProduct);
                            }
                        } else {
                            AppHelper.showMsg(mActivity, isShowModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取权限
     */
    IndexInfoModel.DataBean.HomePopup homePropup;
    private void getPrivacy(IndexInfoModel indexInfoModel) {
        IndexHomeAPI.getPrivacy(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
//                            privacyDialog = new PrivacysDialog(mActivity);
                            Privacy4Dialog privacysDialog = new Privacy4Dialog(mActivity);
                            privacysDialog.show();
                            if(privacyModel.getData().getOpen().equals("0")) {
                                privacysDialog.dismiss();
                            }else {
                                privacysDialog.show();
                            }


                        } else {
                            AppHelper.showMsg(mActivity, privacyModel.getMessage());
                        }
                    }
                });
    }

//    private Disposable mAutoTask;
    private LinearSmoothScroller mSmoothScroller;
    private LinearSmoothScroller mSmoothScrollers;
    private int mCurrentPosition;

    long start;
    @Override
    public void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden&&SharedPreferencesUtil.getString(mActivity,"index1").equals("1")&&SharedPreferencesUtil.getString(mActivity,"index2").equals("1")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }else {
            start = System.currentTimeMillis();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        if(SharedPreferencesUtil.getString(mActivity,"index").equals("1")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }
    }

    /**
     * 转盘数据
     */
    private void getTurn() {
        IndexHomeAPI.getTurn(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
        tv_phone.setText("客服热线 (" + cell + ")");

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
                    SharedPreferencesUtil.saveString(mActivity,"mobile",cell);
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
                .observeOn(mainThread())
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
                            if(indexInfoModel.getData()!=null) {
                                if (indexInfoModel.getData().size() > 0) {
                                    driverList.clear();
                                    driverList.addAll(indexInfoModel.getData());
                                    verticalBannerAdapter = new VerticalBannerAdapter(cell, driverList, getContext());
                                    ll_driver.setVisibility(View.VISIBLE);
                                    verticalBanner.setAdapter(verticalBannerAdapter);
                                    verticalBanner.start();

                                } else {
                                    ll_driver.setVisibility(View.GONE);
                                }
                            }else {
                                ll_driver.setVisibility(View.GONE);
                            }

                        }else {
                            ll_driver.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 搜索热词
     */
    List<IndexInfoModel.DataBean.ClassifyListBean> classifyLists;

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
    /**
     * 获取首页信息
     */
    private void getBaseLists() {
        IndexHomeAPI.getIndexInfo(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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

                            UserInfoHelper.saveAreaName(mActivity, data.getAreaName());
                            UserInfoHelper.saveCity(mActivity, data.getCityName());
                            UserInfoHelper.saveProvince(mActivity, data.getProvinceName());

                            iconList.clear();

                            getSpikeList();
                            getHot(1, 10, "hot");
                            getCustomerPhone();
                            isShow();
                            getOrder();

                            if(indexInfoModel.getData().getIcons()!=null) {
                                iconList.addAll(data.getIcons());
                            }
                            couponListModels = indexInfoModel.getData();
                            if(indexInfoModel.getData().getGiftReceiveBtn().equals("0")) {
                                getPrivacy(indexInfoModel);
                                getDialog(indexInfoModel);
                            }else {
                                getTurn();
                            }

                            if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                tv_city.setText(data.getCompanyName());
                            }else {
                                tv_city.setText(data.getAddress());
                            }
                            if(!SharedPreferencesUtil.getString(mActivity,"once").equals("0")) {
                                getDialog(indexInfoModel);
                            }

                            tv_times.setText(indexInfoModel.getData().getReturnAmountTime() + "小时快速退款");
                            tv_amount.setText("满" + indexInfoModel.getData().getSendAmount() + "元免配送费");
                            tv_title3.setText("物美价廉");


                            if (data.isAddressIsInArea()) {
                                rl_address.setVisibility(View.GONE);
                            } else {
                                rl_address.setVisibility(View.VISIBLE);
                            }

                            if(data.getHllOrderCallNum()>0) {
                                rl_huo.setVisibility(View.VISIBLE);
                                tv_order_num.setText("您有"+data.getHllOrderCallNum()+"笔订单待呼叫货拉拉！");
                            }else {
                                rl_huo.setVisibility(View.GONE);
                            }
                            questUrl = indexInfoModel.getData().getQuestUrl();

                            list.clear();
                            list1.clear();

//                            indexInfoModel.getData().getHomePopup()
                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                list.add(data.getBanners().get(i).getDefaultPic());

                            }

                            for (int i = 0; i < indexInfoModel.getData().getBanners().size(); i++) {
                                if (indexInfoModel.getData().getBanners().get(i).getShowType() == 2) {
                                    list1.add(data.getBanners().get(i).getDetailPic());
                                }
                            }

                            if (data.getBanners().size() > 0) {
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
                                iv_fill.setVisibility(View.GONE);
                                ll_bgc.setVisibility(View.VISIBLE);
                                banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int i, float v, int i1) {

                                    }

                                    @Override
                                    public void onPageSelected(int pos) {
                                        if(data.getBanners().size()>0) {
                                            if (!TextUtils.isEmpty(banners.get(pos).getRgbColor())) {
                                                String rgbColor = banners.get(pos).getRgbColor();
                                                ll_bgc.setBackgroundColor(Color.parseColor("#" + rgbColor));
                                            }
                                        }

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int i) {
//                                        Log.d("wwwwwwww........","3333");
                                    }
                                });

                            } else {
                                banner.setVisibility(View.GONE);
                                iv_fill.setVisibility(View.VISIBLE);
                                Glide.with(mActivity).load(data.getHomeBackPic()).into(iv_fill);
                                ll_bgc.setVisibility(View.GONE);
                            }

                            recommendList.clear();
                            recommendData = indexInfoModel.getData().getHotKey();
                            recommendList.addAll(recommendData);
                            indexRecommendAdapter.notifyDataSetChanged();

                            classifyLists = indexInfoModel.getData().getClassifyList();
                            classifyList.clear();
                            classifyList.addAll(classifyLists);
                            rvIconAdapter = new RvIconAdapter(R.layout.item_home_icon,classifyList);
                            if(classifyList.size()==5||classifyList.size()==9||classifyList.size()==10) {
                                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,5);
                                rv_icon.setLayoutManager(gridLayoutManager1);
                                rv_icon.setAdapter(rvIconAdapter);
                                indicator.setVisibility(View.GONE);
                            }else if(classifyList.size()<=4||classifyList.size()<=8 &&classifyList.size()!=5){
                                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mActivity,4);
                                rv_icon.setLayoutManager(gridLayoutManager2);
                                rv_icon.setAdapter(rvIconAdapter);
                                indicator.setVisibility(View.GONE);
                            }else {
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, RecyclerView.HORIZONTAL, false);
                                rv_icon.setLayoutManager(gridLayoutManager);
                                rv_icon.setAdapter(rvIconAdapter);
                                indicator.setVisibility(View.VISIBLE);
                            }

                            if(data.getHllTip()!=null) {
                                HuoOrderDialog huoOrderDialog = new HuoOrderDialog(mActivity,data);
                                huoOrderDialog.show();
                            }

                            rvIconAdapter.notifyDataSetChanged();

                            rvIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
                                    EventBus.getDefault().post(new GoToMarketEvent());
                                    EventBus.getDefault().postSticky(new FromIndexEvent(classifyList.get(position).getId() + ""));
                                }
                            });

                            lav_activity_loading.hide();
                        } else {

                            AppHelper.showMsg(mActivity, indexInfoModel.getMessage());
                            lav_activity_loading.hide();
                        }
                    }
                });
    }


    private void getDialog(IndexInfoModel indexInfoModel) {
        couponListModels = indexInfoModel.getData();
        if(couponListModels.getUserPopup()!=null) {
            if(couponListModels.getUserPopup().getGifts()!=null) {
                lists = couponListModels.getUserPopup().getGifts();
                couponListDialog = new CouponListDialog(mActivity, couponListModels);
                if (lists.size() > 0) {
                    couponListDialog.show();
                } else {
                    couponListDialog.dismiss();
                }
            }
        }


        if(indexInfoModel.getData().getHomePopup()!=null) {
            homePropup = indexInfoModel.getData().getHomePopup();
            homeActivityDialog = new HomeActivityDialog(mActivity,homePropup);
            homeActivityDialog.show();
        }
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
                        Intent intent = new Intent(getActivity(), MyCouponsActivity.class);
                        startActivity(intent);
                    }
                } else if (showType == 4) {

                    //商品
                    if(banners.get(position).getBusinessNum()>1) {
                        Intent intent = new Intent(mActivity, BannerActivity.class);
                        intent.putExtra("title",banners.get(position).getTitle());
                        intent.putExtra("bannerId",banners.get(position).getBannerId());
                        intent.putExtra("defaultPic",banners.get(position).getDefaultPic());
                        startActivity(intent);

                    }else {
                        int businessId = Integer.parseInt(banners.get(position).getBusinessId());
                        Intent intent = new Intent(getActivity(), CommonGoodsDetailActivity.class);
                        intent.putExtra(AppConstant.ACTIVEID, businessId);
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                        startActivity(intent);
                    }

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

    /**
     * 获取更新
     */
    private void requestUpdate() {
        UpdateAPI.requestUpdate(getContext(), AppHelper.getVersion(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
    public void onDestroy() {
        super.onDestroy();
//        verticalBanner.stop();
        EventBus.getDefault().unregister(this);
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
            case R.id.tv_look:
                if(data.getHllOrderCallNum()>1) {
                    Intent intent = MyOrdersActivity.getIntent(getContext(), MyOrdersActivity.class, AppConstant.ALL);
                    intent.putExtra("orderDeliveryType",0);
                    startActivity(intent);
                }else {
                    Intent intentsss = new Intent(mActivity, NewOrderDetailActivity.class);
                    intentsss.putExtra("orderId",data.getOrderId());
                    startActivity(intentsss);
                }
                break;
            case R.id.rl_huo:
                isAuth();
                break;
            case R.id.tv_search:
                Intent intent = new Intent(mActivity, SearchStartActivity.class);
                intent.putExtra(AppConstant.SEARCHTYPE, AppConstant.HOME_SEARCH);
                intent.putExtra("flag", "first");
                intent.putExtra("good_buy", "");
                startActivity(intent);
                break;

            case R.id.iv_location:
                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                    Intent intent1 = new Intent(mActivity, ChooseCompanyActivity.class);
                    startActivity(intent1);
                }else {
                    if(data!=null) {
                        Intent messageIntent = new Intent(getActivity(), ChooseAddressActivity.class);
                        messageIntent.putExtra("cityName", data.getCityName());
                        messageIntent.putExtra("areaName", data.getAreaName());
                        messageIntent.putExtra("fromPage", "0");
                        startActivity(messageIntent);
                    }
                }
                break;

            case R.id.rl_message:
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getActivity()))) {
                    Intent intents = new Intent(getActivity(), MessageCenterActivity.class);
                    startActivityForResult(intents, 101);

                } else {
                    initDialog();
                }
                break;

            case R.id.tv_city:
                //选择城市
                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                    Intent intent1 = new Intent(mActivity, ChooseCompanyActivity.class);
                    startActivity(intent1);
                }else {
                    if(data!=null) {
                        Intent messageIntent = new Intent(getActivity(), ChooseAddressActivity.class);
                        messageIntent.putExtra("cityName", data.getCityName());
                        messageIntent.putExtra("areaName", data.getAreaName());
                        messageIntent.putExtra("fromPage", "0");
                        startActivity(messageIntent);
                    }
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

            case R.id.rl_more3:
                //超值组合
                Intent teamIntent = new Intent(getActivity(), TeamDetailActivity.class);
                startActivity(teamIntent);
                break;

            case R.id.rl_more6:
                //超值组合
                Intent teamIntents = new Intent(getActivity(), TeamDetailActivity.class);
                startActivity(teamIntents);
                break;

            case R.id.rl_more4:
                //满赠
                Intent fullIntent = new Intent(getActivity(), FullListActivity.class);
                startActivity(fullIntent);
                break;

            case R.id.tv_change:
                Intent changeCityIntent = new Intent(getActivity(), ChangeCityActivity.class);
                startActivityForResult(changeCityIntent, 105);
                break;

            case R.id.tv_change_address:
                chooseAddressDialog = new ChooseHomeDialog(mActivity, "");
                chooseAddressDialog.show();
                break;

            case R.id.iv_full2_right:
                Intent intent1 = new Intent(mActivity,FullListActivity.class);
                startActivity(intent1);
                break;

            case R.id.iv_full_right:
                Intent intent7 = new Intent(mActivity,FullListActivity.class);
                startActivity(intent7);
                break;

            case R.id.iv_team_right:
                Intent intent3 = new Intent(mActivity,TeamDetailActivity.class);
                startActivity(intent3);
                break;

            case R.id.iv_team2_right:
                Intent intent4 = new Intent(mActivity,TeamDetailActivity.class);
                startActivity(intent4);
                break;

            case R.id.tv_look_more:
                Intent intent5 = new Intent(mActivity,CouponDetailActivity.class);
                startActivity(intent5);
                break;

            case R.id.tv_coupon_more:
                Intent intent6 = new Intent(mActivity,HotProductActivity.class);
                startActivity(intent6);
                break;

            case R.id.rl_more5:
                Intent intent8 = new Intent(mActivity,FullListActivity.class);
                startActivity(intent8);
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

    /**
     * 判断是否需要授权
     */
    private void isAuth() {
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
                                    startActivity(CommonH6Activity.getIntent(mActivity, CommonH6Activity.class,isAuthModel.getData().getAuthUrl(),""));
                                }else {
                                    Intent intentss = new Intent(mActivity, HuoHomeActivity.class);
                                    intentss.putExtra("orderId","");
                                    startActivity(intentss);
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,isAuthModel.getMessage());
                        }
                    }
                });
    }

    private void stopAuto() {
//        if (mAutoTask != null && !mAutoTask.isDisposed()) {
//            mAutoTask.dispose();
//            mAutoTask = null;
//        }
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
        if(chooseAddressDialog!=null) {
            chooseAddressDialog.dismiss();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getScrolls(TopEvent event) {
        if(event.isB()) {
            appbar.setExpanded(true);
        }
    }

    int position;
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_new:
                    position = 1;
                    rb_reduce.setTextColor(Color.parseColor("#333333"));
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

                    switchNew();
                    break;

                case R.id.rb_must_common:
                    position = 0;
                    rb_reduce.setTextColor(Color.parseColor("#333333"));
                    rb_common.setTextColor(Color.parseColor("#333333"));
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

                    switchMust();
                    break;

                case R.id.rb_reduce:
                    position = 2;
                    rb_reduce.setTextColor(Color.parseColor("#17BD60"));
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

                    switchReduce();
                    break;

                case R.id.rb_common:
                    position = 3;
                    rb_reduce.setTextColor(Color.parseColor("#333333"));
                    rb_common.setTextColor(Color.parseColor("#17BD60"));
                    rb_must_common.setTextColor(Color.parseColor("#333333"));
                    rb_new.setTextColor(Color.parseColor("#333333"));
                    rb_reduce.setTextColor(Color.parseColor("#333333"));

                    tv_title2.setTextColor(Color.parseColor("#999999"));
                    tv_title2.setBackgroundResource(R.drawable.shape_white);

                    tv_title1.setTextColor(Color.parseColor("#999999"));
                    tv_title1.setBackgroundResource(R.drawable.shape_white);

                    tv_title3.setTextColor(Color.parseColor("#999999"));
                    tv_title3.setBackgroundResource(R.drawable.shape_white);

                    tv_title4.setTextColor(Color.parseColor("#ffffff"));
                    tv_title4.setBackgroundResource(R.drawable.shape_greenss);

                    switchCommon();
                    break;
            }
//            //根据位置得到对应的Fragment
//            Fragment to = getFragment();
//            //替换到Fragment
//            switchFrament(mContent,to);
        }
    }

//    private Fragment mContent;
//    private void switchFrament(Fragment from,Fragment to) {
//        if(from != to){ //才切换
//            mContent = to;
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction(); //开启事务
//            //判断to有没有被添加
//            if(!to.isAdded()){//to没有被添加
//                //1.from隐藏
//                if(from != null){
//                    ft.hide(from);
//                }
//                //2.添加to
//                if(to != null){
//                    ft.add(R.id.fl_container,to).commit();
//                }
//            }else{ //to已经被添加
//                //1.from隐藏
//                if(from != null){
//                    ft.hide(from);
//                }
//                //2.显示to
//                if(to != null){
//                    ft.show(to).commit();
//                }
//            }
//        }
//    }
//    private Fragment getFragment() {
//        Fragment fragment = mBaseFragment.get(position);
//        return fragment;
//    }
    private void setListener() {
        rg_new.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_new_top.setOnCheckedChangeListener(new MyTopOnCheckedChangeListener());
        //设置默认选中框架页面
        rg_new.check(R.id.rb_must_common);
    }

//    private List<Fragment> mBaseFragment;
//    private void initFragment() {
//        mBaseFragment = new ArrayList<>();
//        mBaseFragment.add(MustFragment.getInstance());
//        mBaseFragment.add(NewFragment.getInstance());
//        mBaseFragment.add(ReduceFragment.getInstance());
//        mBaseFragment.add(CommonFragment.getInstance());
//    }

    private class MyTopOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_new_top:
                    position = 1;
                    rb_new_top.setChecked(true);

                    rb_new.setChecked(true);
                    rb_reduce.setChecked(false);
                    rb_common.setChecked(false);
                    rb_must_common.setChecked(false);
                    rb_info_top.setTextColor(Color.parseColor("#333333"));
                    rb_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_new_top.setTextColor(Color.parseColor("#17BD60"));
                    v4s.setVisibility(View.INVISIBLE);
                    v1s.setVisibility(View.INVISIBLE);
                    v2s.setVisibility(View.VISIBLE);
                    v3s.setVisibility(View.INVISIBLE);
                    switchNew();
                    break;

                case R.id.rb_must_common_top:
                    position = 0;
                    rb_must_common_top.setChecked(true);
                    rb_new.setChecked(false);
                    rb_reduce.setChecked(false);
                    rb_common.setChecked(false);
                    rb_must_common.setChecked(true);
                    rb_info_top.setTextColor(Color.parseColor("#333333"));
                    rb_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_new_top.setTextColor(Color.parseColor("#17BD60"));

                    v4s.setVisibility(View.INVISIBLE);
                    v1s.setVisibility(View.INVISIBLE);
                    v2s.setVisibility(View.VISIBLE);
                    v3s.setVisibility(View.INVISIBLE);
                    switchMust();
                    break;

                case R.id.rb_info_top:
                    rb_info_top.setChecked(true);

                    rb_new.setChecked(false);
                    rb_reduce.setChecked(true);
                    rb_common.setChecked(false);
                    rb_must_common.setChecked(false);

                    position = 2;
                    rb_info_top.setTextColor(Color.parseColor("#17BD60"));
                    rb_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_new_top.setTextColor(Color.parseColor("#333333"));

                    v4s.setVisibility(View.INVISIBLE);
                    v1s.setVisibility(View.INVISIBLE);
                    v2s.setVisibility(View.INVISIBLE);
                    v3s.setVisibility(View.VISIBLE);
                    switchReduce();
                    Log.d("wdasdassd....","111");
                    break;

                case R.id.rb_common_top:
                    rb_common_top.setChecked(true);

                    rb_new.setChecked(false);
                    rb_reduce.setChecked(false);
                    rb_common.setChecked(true);
                    rb_must_common.setChecked(false);
                    position = 3;
                    rb_info_top.setTextColor(Color.parseColor("#333333"));
                    rb_common_top.setTextColor(Color.parseColor("#17BD60"));
                    rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                    rb_new_top.setTextColor(Color.parseColor("#333333"));

                    v4s.setVisibility(View.VISIBLE);
                    v1s.setVisibility(View.INVISIBLE);
                    v2s.setVisibility(View.INVISIBLE);
                    v3s.setVisibility(View.INVISIBLE);
                    switchCommon();
                    break;
            }
//            //根据位置得到对应的Fragment
//            Fragment to = getFragment();
//            //替换到Fragment
//            switchFrament(mContent,to);

        }


    }

    /**
     * 常用清单
     */
    public void switchCommon() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (commonFragment == null) {
            commonFragment = new CommonFragment();
            fragmentTransaction.add(R.id.fl_container, commonFragment, CommonFragment.class.getCanonicalName());
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
     * 降价
     */
    ReduceFragment reduceFragment;
    public void switchReduce() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (reduceFragment == null) {
            reduceFragment = new ReduceFragment();
            fragmentTransaction.add(R.id.fl_container, reduceFragment, ReduceFragment.class.getCanonicalName());
        }

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
    public void switchMust() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (mustFragment == null) {
            mustFragment = new MustFragment();
            fragmentTransaction.add(R.id.fl_container, mustFragment, MustFragment.class.getCanonicalName());
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
    public void switchNew() {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (newFragment == null) {
            newFragment = new NewFragment();
            fragmentTransaction.add(R.id.fl_container, newFragment, NewFragment.class.getCanonicalName());
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

}
