package com.puyue.www.qiaoge.fragment.home;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.Manifest;
import android.animation.IntEvaluator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.frankfancode.marqueeview.MarqueeView;
import com.google.android.exoplayer2.Player;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.Size;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import io.reactivex.Observable;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.BannerActivity;
import com.puyue.www.qiaoge.activity.ChooseCompanyActivity;
import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.HelpPayDeliveryDetailActivity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.activity.PlayerActivity;
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.cart.TestActivity;
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

import com.puyue.www.qiaoge.activity.view.SchoolActivity;
import com.puyue.www.qiaoge.adapter.HotAdapter;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.MarqueeAdapter;
import com.puyue.www.qiaoge.adapter.OrderMarqueeAdapter;
import com.puyue.www.qiaoge.adapter.VpDiscountAdapter;
import com.puyue.www.qiaoge.adapter.VpFullAdapter;
import com.puyue.www.qiaoge.adapter.VpSkillAdapter;
import com.puyue.www.qiaoge.adapter.VpTeamAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonProductActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.UpdateAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderNumAPI;
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
import com.puyue.www.qiaoge.dialog.HelpPay1Dialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.HuoOrderDialog;
import com.puyue.www.qiaoge.dialog.Privacy4Dialog;
import com.puyue.www.qiaoge.dialog.SchoolDialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.ChangeCityModel;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.HomeBannerModel;
import com.puyue.www.qiaoge.model.HomeCouponModel;
import com.puyue.www.qiaoge.model.HomeStyleModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.model.mine.order.MyOrderNumModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.utils.ToastUtil;

import com.puyue.www.qiaoge.view.CustomAppbarLayout;
import com.puyue.www.qiaoge.view.HIndicators;
import com.puyue.www.qiaoge.view.MyCompanyScrollView;
import com.puyue.www.qiaoge.view.MyScrollView1;
import com.puyue.www.qiaoge.view.MyScrollView2;
import com.puyue.www.qiaoge.view.ScrollSpeedLinearLayoutManger;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView3;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViewss;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2020/1/4
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,BaseSliderView.OnSliderClickListener {
    Unbinder bind;
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
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.homeMessage)
    ImageView homeMessage;
    @BindView(R.id.rb_new)
    RadioButton rb_new;
    @BindView(R.id.rb_must_common)
    RadioButton rb_must_common;
    @BindView(R.id.rb_reduce)
    RadioButton rb_reduce;
    @BindView(R.id.rb_common)
    RadioButton rb_common;
    @BindView(R.id.marquee)
    MarqueeView marqueeView;
    @BindView(R.id.rb_new_top)
    RadioButton rb_new_top;
    @BindView(R.id.rb_must_common_top)
    RadioButton rb_must_common_top;
    @BindView(R.id.rb_info_top)
    RadioButton rb_info_top;
    @BindView(R.id.rb_common_top)
    RadioButton rb_common_top;
    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.rv_team)
    com.puyue.www.qiaoge.view.MarqueeView rv_team;
    @BindView(R.id.rv_discount)
    com.puyue.www.qiaoge.view.MarqueeView rv_discount;
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
    @BindView(R.id.ll_must)
    LinearLayout ll_must;
    @BindView(R.id.ll_new)
    LinearLayout ll_new;
    @BindView(R.id.ll_reduce)
    LinearLayout ll_reduce;
    @BindView(R.id.ll_common)
    LinearLayout ll_common;
    @BindView(R.id.ll_must_top)
    LinearLayout ll_must_top;
    @BindView(R.id.ll_new_top)
    LinearLayout ll_new_top;
    @BindView(R.id.ll_reduce_top)
    LinearLayout ll_reduce_top;
    @BindView(R.id.ll_common_top)
    LinearLayout ll_common_top;
//    @BindView(R.id.ll_small_title)
//    LinearLayout ll_small_title;
    @BindView(R.id.snap1)
    SnapUpCountDownTimerViewss snap1;
    @BindView(R.id.tv_skill_time1)
    TextView tv_skill_time1;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.tv_change_address)
    TextView tv_change_address;
    @BindView(R.id.tv_times)
    TextView tv_times;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.ll_bgc)
    RelativeLayout ll_bgc;
    @BindView(R.id.rv_full)
    com.puyue.www.qiaoge.view.MarqueeView rv_full;
    @BindView(R.id.rg_new)
    RadioGroup rg_new;
    @BindView(R.id.rg_new_top)
    RadioGroup rg_new_top;
    @BindView(R.id.ll_skill)
    LinearLayout ll_skill;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.appbar)
    CustomAppbarLayout appbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.iv_tip)
    ImageView iv_tip;
    @BindView(R.id.rl_grand)
    RelativeLayout rl_grand;
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.ll_parent)
    RelativeLayout ll_parent;
    @BindView(R.id.rl_inSide)
    LinearLayout rl_inSide;
    @BindView(R.id.rl_bar)
    RelativeLayout rl_bar;
    @BindView(R.id.ll_parent_top)
    RelativeLayout ll_parent_top;
    @BindView(R.id.tv_look)
    TextView tv_look;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.rl_huo)
    RelativeLayout rl_huo;
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
    @BindView(R.id.order_marquee)
    MarqueeView order_marquee;
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
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.iv_coupon)
    ImageView iv_coupon;
    @BindView(R.id.ll_hot)
    LinearLayout ll_hot;
    @BindView(R.id.my_scroll)
    MyScrollView2 my_scroll;
    @BindView(R.id.tv_coupon_num)
    TextView tv_coupon_num;
    @BindView(R.id.view_common)
    View view_common;
    @BindView(R.id.view_must)
    View view_must;
    @BindView(R.id.view_reduce)
    View view_reduce;
    @BindView(R.id.view_new)
    View view_new;
    @BindView(R.id.ll_city)
    LinearLayout ll_city;
    @BindView(R.id.ll_school)
    LinearLayout ll_school;
    @BindView(R.id.iv_school)
    ImageView iv_school;
    @BindView(R.id.iv_enter)
    ImageView iv_enter;
    @BindView(R.id.iv_half_school)
    ImageView iv_half_school;
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
    private String cell; // 客服电话
    ChooseHomeDialog chooseAddressDialog;
    List<String> recommendData;
    //司机信息
    List<OrderModel.DataBean> driverList = new ArrayList<>();
    //八个icon集合
    List<IndexInfoModel.DataBean.IconsBean> iconList = new ArrayList<>();
    //秒杀集合
    List<HomeBaseModel.DataBean.SecKillListBean.KillsBean> skillList = new ArrayList<>();
    //秒杀预告集合
    List<HomeBaseModel.DataBean.SecKillListBean.KillsBean> skillAdvList = new ArrayList<>();
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
    private CouponModels.DataBean dataActive;
    private int showType;
    private long currentTime;
    private long startTime;
    private long endTime;
    private CouponListDialog couponListDialog;
    IndexInfoModel.DataBean couponListModels;
    private List<CouponListModel.DataBean.GiftsBean> lists;
    private List<TurnModel.DataBean> data2;
    private TurnTableDialog turnTableDialog;
    private HomeActivityDialog homeActivityDialog;
    int topHeight;

    //静止状态
    private final static int SCROLL_STATE_IDLE = 1;
    //拖动或者惯性滑动状态
    private final static int SCROLL_STATE_SCROLL = 2;

    //判断是否是拖动状态
    boolean isDragState = false;

    int currentState = SCROLL_STATE_IDLE;

    CountDownTimer animTimer;

    private CountDownTimer scrollCountTimer = new CountDownTimer(2000, 2000) {
        @Override
        public void onTick(long millisUntilFinished) {
            setScrollState(SCROLL_STATE_SCROLL);
        }

        @Override
        public void onFinish() {
            setScrollState(SCROLL_STATE_IDLE);
        }
    };


    @Override
    public int setLayoutId() {
        return R.layout.test10;
    }

    private void setScrollState(int scrollStateScroll) {
        if(couponNum>0) {
            if(scrollStateScroll == 1) {
                ll_coupon.setVisibility(View.VISIBLE);
                iv_coupon.setVisibility(View.GONE);
            }else {
                ll_coupon.setVisibility(View.GONE);
                iv_coupon.setVisibility(View.VISIBLE);
            }
        }else {
            ll_coupon.setVisibility(View.GONE);
            iv_coupon.setVisibility(View.GONE);
        }

    }

    int scroll = 0;
    private AnimationDrawable anim;
    private int TIME = 3000;
    Handler handler = new Handler();
    @Override
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);
        anim = (AnimationDrawable)ll_coupon.getBackground();
        setListener();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        my_scroll.setOnScrollStatusListener(new MyScrollView2.OnScrollStatusListener() {
            @Override
            public void onScrollStop() {
            }

            @Override
            public void onScrolling(int length) {
                if(Math.abs(length)>25) {
                    scrollCountTimer.start();
                }
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, TIME);
                    animTimer = new CountDownTimer(TIME, TIME) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            anim.start();
                        }

                        @Override
                        public void onFinish() {
                            anim.stop();
                        }
                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, TIME); // 在初始化方法里.
        setScrollState(SCROLL_STATE_IDLE);

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
                        int abs = Math.abs(y);
                        int totalScrollRange = appBarLayout.getTotalScrollRange()-bar_height;
                        if (abs>=totalScrollRange) {
                            ll_parent_top.setVisibility(View.VISIBLE);
//                            ll_small_title.setVisibility(View.GONE);
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
                            }else if(rb_new.isChecked()) {
                                rb_new_top.setChecked(true);
                                rb_must_common_top.setChecked(false);
                                rb_info_top.setChecked(false);
                                rb_common_top.setChecked(false);
                                v1s.setVisibility(View.VISIBLE);
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    v2s.setVisibility(View.GONE);
                                    v4s.setVisibility(View.GONE);
                                }else {
                                    v2s.setVisibility(View.INVISIBLE);
                                    v4s.setVisibility(View.INVISIBLE);
                                }
                                v3s.setVisibility(View.INVISIBLE);
                            }else if(rb_reduce.isChecked()) {
                                rb_info_top.setChecked(true);
                                rb_new_top.setChecked(false);
                                rb_must_common_top.setChecked(false);
                                rb_common_top.setChecked(false);
                                v1s.setVisibility(View.INVISIBLE);
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    v2s.setVisibility(View.GONE);
                                    v4s.setVisibility(View.GONE);
                                }else {
                                    v2s.setVisibility(View.INVISIBLE);
                                    v4s.setVisibility(View.INVISIBLE);
                                }
                                v3s.setVisibility(View.VISIBLE);
                            }else if(rb_common.isChecked()){
                                rb_common_top.setChecked(true);
                                rb_info_top.setChecked(false);
                                rb_new_top.setChecked(false);
                                rb_must_common_top.setChecked(false);
                                v1s.setVisibility(View.INVISIBLE);
                                v2s.setVisibility(View.INVISIBLE);
                                v3s.setVisibility(View.INVISIBLE);
                                v4s.setVisibility(View.VISIBLE);
                            }

                        } else {
                            EventBus.getDefault().post(new changeEvent(false,"1"));
                            ll_parent_top.setVisibility(View.GONE);
//                            ll_small_title.setVisibility(View.VISIBLE);
                            ll_line.setVisibility(View.GONE);
                        }


                        //滑动距离小于顶部栏从透明到不透明所需的距离
                        if ((scrollLength - abs) > 0) {
                            //估值器
                            IntEvaluator evaluator = new IntEvaluator();
                            float percent = (float) (scrollLength - abs) / scrollLength;

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
                            }
                        }

                        if(scroll != abs) {
                            if (isDragState) {//拖动状态单独处理不再进行滚动状态监测
                                return;
                            }
                            //滑动时先取消倒计时，设置滑动状态
                            scrollCountTimer.cancel();
                            if(currentState != SCROLL_STATE_SCROLL) {
                                setScrollState(SCROLL_STATE_SCROLL);
                            }
                            scrollCountTimer.start();
                        }
                        scroll = abs;
                    }
                });

            }
        });

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
        tv_city.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        rl_address.setOnClickListener(null);
        rl_huo.setOnClickListener(this);
        iv_huo_company.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        lav_activity_loading.show();
        iv_school.setOnClickListener(this);
        iv_enter.setOnClickListener(this);
        iv_half_school.setOnClickListener(this);
        requestUpdate();
        refreshLayout.autoRefresh();
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PageNum = 1;
                skillList.clear();
                skillAdvList.clear();
                driverList.clear();
                num = 0;
                isShow();
                getOrder();
                getBaseLists();
                getSpikeList();
                getHomeStyle();
                getCustomerPhone();
                getMessage();
                hintKbTwo();
                getHomeCoupon();
                getHot(1, 10, "hot");
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
                                Intent intent = new Intent(mActivity, ChooseCompanyActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,changeCityModel.getMessage());
                        }
                    }
                });
    }

    VpFullAdapter.Onclick onclick;
    VpDiscountAdapter.Onclick onclickDis;
    VpTeamAdapter.Onclick onclickTeam;
    VpSkillAdapter.Onclick onclickSkill;
//    String[] params = { Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    public void setViewData() {

        //判断用户是否选择了企业
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            getStyle();
            ll_hot.setVisibility(View.GONE);
            rb_new.setText("热销商品");
            tv_title1.setText("超值人气");
            tv_title3.setText("专宠好物");
            rb_reduce.setText("常购清单");
            rb_new_top.setText("热销商品");
            rb_info_top.setText("常购清单");
            rb_must_common_top.setVisibility(View.GONE);
            rb_must_common.setVisibility(View.GONE);
            ll_must.setVisibility(View.GONE);
            ll_common.setVisibility(View.GONE);
//            tv_title2.setVisibility(View.GONE);
//            tv_title4.setVisibility(View.GONE);
//            v2s.setVisibility(View.GONE);
//            v4s.setVisibility(View.GONE);
            ll_must_top.setVisibility(View.GONE);
            ll_common_top.setVisibility(View.GONE);
            rb_common.setVisibility(View.GONE);
            rb_common_top.setVisibility(View.GONE);
            rg_new.check(R.id.rb_new);
        }else {
            ll_hot.setVisibility(View.VISIBLE);
            rb_reduce.setText("降价商品");
            rb_new.setText("新品上市");
            tv_title1.setText("上新立荐");
            tv_title3.setText("物美价廉");
            rb_new_top.setText("新品上市");
            rb_info_top.setText("降价商品");
//            v2s.setVisibility(View.VISIBLE);
//            v4s.setVisibility(View.VISIBLE);
//            tv_title2.setVisibility(View.VISIBLE);
//            tv_title4.setVisibility(View.VISIBLE);
            ll_must.setVisibility(View.VISIBLE);
            ll_common.setVisibility(View.VISIBLE);
            ll_must_top.setVisibility(View.VISIBLE);
            ll_common_top.setVisibility(View.VISIBLE);
            rb_must_common_top.setVisibility(View.VISIBLE);
            rb_must_common.setVisibility(View.VISIBLE);
            rb_common.setVisibility(View.VISIBLE);
            rb_common_top.setVisibility(View.VISIBLE);
            rg_new.check(R.id.rb_must_common);
        }

        onclick = new VpFullAdapter.Onclick() {
            @Override
            public void tipClick() {
                getPriceDialog();
            }
        };

        onclickDis = new VpDiscountAdapter.Onclick() {
            @Override
            public void tipClick() {
                getPriceDialog();
            }
        };

        onclickTeam = new VpTeamAdapter.Onclick() {
            @Override
            public void tipClick() {
                getPriceDialog();
            }
        };

        onclickSkill = new VpSkillAdapter.Onclick() {
            @Override
            public void tipClick() {
                getPriceDialog();
            }
        };

//        if (EasyPermissions.hasPermissions(mActivity,params)) {//检查是否获取该权限
//            logoutAndToHomes(mContext, -10000);
            //全部允许
//        } else {//第二次请求
            //存在不允许的权限  对话框为什么一会出来一会不出来
//            EasyPermissions.requestPermissions(this, "需要定位您当前的位置信息", 1, params);

//        }
//        requestPermissions(mActivity,"haha",1,params);
//        if(!EasyPermissions.hasPermissions(mActivity,params)) {
//            ActivityCompat.requestPermissions(mActivity, params, 1);
//        }

    }

    public static void requestPermissions(@NonNull Activity host, String rationale,
                                          int requestCode, @Size(min = 1) @NonNull String... perms) {
        // 需要原因说明弹窗的依然交给EasyPermission处理
//        if (!TextUtils.isEmpty(rationale)) {
//            EasyPermissions.requestPermissions(host, rationale, requestCode, perms);
//        } else {
//            // rational的值为空时，直接调用权限申请，绕过EasyPermission的判断
//            PermissionRequest request = new PermissionRequest.Builder(host, requestCode, perms).build();
//            request.getHelper().directRequestPermissions(requestCode, perms);
//        }
    }


    private void getPriceDialog() {
        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
            if(!SharedPreferencesUtil.getString(mActivity,"priceType").equals("1")) {
                AppHelper.ShowAuthDialog(mActivity,cell);
            }
        }else {
            initDialog();
        }
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
                        if (couponModel.getCode()==1) {
                           if(couponModel.getData()!=null) {
                               scrollTip = 1;
                               dataActive = couponModel.getData();
                               getActive(dataActive);
                               if (couponModel.getData().getSpike()!=null) {
                                   startTime = dataActive.getSpike().getStartTime();
                                   endTime = dataActive.getSpike().getEndTime();
                                   currentTime = dataActive.getSpike().getCurrentTime();

                                   String start = DateUtils.formatDate(startTime, "MM.dd HH:mm:ss");
                                   //未开始
                                   if(startTime > currentTime) {
                                       snap1.setVisibility(View.GONE);
                                       tv_skill_time1.setText(start+"开始");
                                       tv_skill_time1.setVisibility(View.VISIBLE);
                                   }else {
                                       //是否超出100个小时
                                       if(DateUtils.isExceed100(currentTime, endTime)) {
                                           String day = DateUtils.getDay(endTime - currentTime);
                                           tv_skill_time1.setText(day);
                                           tv_skill_time1.setVisibility(View.VISIBLE);
                                           snap1.setVisibility(View.GONE);
                                       }else {
                                           snap1.setTime(true, currentTime, startTime, endTime);
                                           snap1.start();
                                           snap1.setVisibility(View.VISIBLE);
                                           tv_skill_time1.setVisibility(View.GONE);
                                       }
                                   }
                               }
                           }
                        }
                    }
                });
    }

    int num = 0;
    //活动区域
    private void getActive(CouponModels.DataBean dataActive) {
        if(dataActive.getTeam()!=null) {
            tv_team_desc.setText(dataActive.getTeam().getDesc());
            tv_team_desc1.setText(dataActive.getTeam().getDesc());
            num++;
        }

        if(dataActive.getSpecial()!=null) {
            tv_discount_desc.setText(dataActive.getSpecial().getDesc());
            tv_discount_desc1.setText(dataActive.getSpecial().getDesc());
            num++;

        }

        if(dataActive.getFullGift()!=null) {
            tv_full_desc.setText(dataActive.getFullGift().getDesc());
            tv_full_desc1.setText(dataActive.getFullGift().getDesc());
            num++;
        }

        if(dataActive.getSpike()!=null) {
            num++;
        }
        switch (num) {
            case 0:
                ll_two.setVisibility(View.GONE);
                ll_one.setVisibility(View.GONE);
                break;
            case 1:
                ll_two.setVisibility(View.VISIBLE);
                ll_one.setVisibility(View.VISIBLE);
                getOne(dataActive);
                break;

            case 2:
                ll_two.setVisibility(View.VISIBLE);
                ll_one.setVisibility(View.VISIBLE);
                getTwo(dataActive);
                break;

            case 3:
                ll_two.setVisibility(View.VISIBLE);
                ll_one.setVisibility(View.VISIBLE);
                getThree(dataActive);
                break;

            case 4:
                ll_two.setVisibility(View.VISIBLE);
                ll_one.setVisibility(View.VISIBLE);
                getFour(dataActive);
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();

    }

    private void getFour(CouponModels.DataBean dataActive) {
        ll_team.setVisibility(View.VISIBLE);
        ll_discount.setVisibility(View.VISIBLE);
        ll_full.setVisibility(View.VISIBLE);
        ll_skill.setVisibility(View.VISIBLE);

        ll_discount1.setVisibility(View.GONE);
        ll_team1.setVisibility(View.GONE);
        ll_full1.setVisibility(View.GONE);


        ll_skill.setBackgroundResource(R.mipmap.bg_home_skill);
        ll_discount.setBackgroundResource(R.mipmap.bg_home_coupon);
        ll_team.setBackgroundResource(R.mipmap.bg_home_team);
        ll_full.setBackgroundResource(R.mipmap.bg_home_full);
        VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger = new ScrollSpeedLinearLayoutManger(mActivity);
        scrollSpeedLinearLayoutManger.setOrientation(RecyclerView.VERTICAL);
        rv_skill.setAdapter(vpSkillAdapter);
        if(dataActive.getSpike().getActives().size()>1) {
            rv_skill.startScroll();
        }else {
            rv_skill.stopScroll();
        }

        VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity, R.layout.item_active_short2, dataActive.getFullGift().getActives(), onclick);
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger1 = new ScrollSpeedLinearLayoutManger(mActivity);
        scrollSpeedLinearLayoutManger1.setOrientation(RecyclerView.VERTICAL);
        rv_full.setAdapter(vpFullAdapter);
        if(dataActive.getFullGift().getActives().size()>1) {
            rv_full.startScroll();
        }else {
            rv_full.stopScroll();
        }


        VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger2 = new ScrollSpeedLinearLayoutManger(mActivity);
        scrollSpeedLinearLayoutManger2.setOrientation(RecyclerView.VERTICAL);
        rv_team.setAdapter(vpTeamAdapter);

        if(dataActive.getTeam().getActives().size()>1) {
            rv_team.startScroll();
        }else {
            rv_team.stopScroll();
        }

        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger3 = new ScrollSpeedLinearLayoutManger(mActivity);
        scrollSpeedLinearLayoutManger3.setOrientation(RecyclerView.VERTICAL);
        VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
        rv_discount.setAdapter(vpDiscountAdapter);
        if(dataActive.getSpecial().getActives().size()>1) {
            rv_discount.startScroll();
        }else {
            rv_discount.stopScroll();
        }
    }

    private void getOne(CouponModels.DataBean dataActive) {
        if(dataActive.getSpecial()!=null) {
            ll_discount.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_discount.setBackgroundResource(R.mipmap.bg_home_coupon_l);

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger4 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger4.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_special,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount.startScroll();
            }else {
                rv_discount.stopScroll();
            }
        }

        if(dataActive.getTeam()!=null) {
            ll_team.setVisibility(View.VISIBLE);
            ll_discount.setVisibility(View.GONE);
            ll_skill.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team.setBackgroundResource(R.mipmap.bg_home_team_l);

            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_special,dataActive.getTeam().getActives(),onclickTeam);

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger5 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger5.setOrientation(scrollSpeedLinearLayoutManger5.VERTICAL);
            rv_team.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team.startScroll();
            }else {
                rv_team.stopScroll();
            }
        }

        if(dataActive.getFullGift()!=null) {
            ll_full.setVisibility(View.VISIBLE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
            ll_skill.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);

            ll_full.setBackgroundResource(R.mipmap.bg_home_full_l);
            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_special,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger6 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger6.setOrientation(RecyclerView.VERTICAL);
            rv_full.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full.startScroll();
            }else {
                rv_full.stopScroll();
            }
        }

        if(dataActive.getSpike()!=null) {
            ll_skill.setVisibility(View.VISIBLE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_skill.setBackgroundResource(R.mipmap.bg_home_skill_l);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_special,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger7 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger7.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }
        }
    }

    private void getTwo(CouponModels.DataBean dataActive) {
        //秒杀和满赠
        if(dataActive.getSpike()!=null && dataActive.getFullGift()!=null) {
            ll_skill.setVisibility(View.VISIBLE);
            ll_full.setVisibility(View.VISIBLE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);

            ll_skill.setBackgroundResource(R.mipmap.bg_home_skill);
            ll_full.setBackgroundResource(R.mipmap.bg_home_full);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger8 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger8.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short2,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger9 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger9.setOrientation(RecyclerView.VERTICAL);
            rv_full.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full.startScroll();
            }else {
                rv_full.stopScroll();
            }
        }

        //秒杀和组合
        if(dataActive.getSpike()!=null && dataActive.getTeam()!=null) {
            ll_skill.setVisibility(View.VISIBLE);
            ll_team1.setVisibility(View.VISIBLE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);

            ll_skill.setBackgroundResource(R.mipmap.bg_home_skill);
            ll_team1.setBackgroundResource(R.mipmap.bg_home_team);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger10 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger10.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger11 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger11.setOrientation(RecyclerView.VERTICAL);
            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
            rv_team1.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team1.startScroll();
            }else {
                rv_team1.stopScroll();
            }
        }

        //秒杀和折扣
        if(dataActive.getSpike()!=null && dataActive.getSpecial()!=null) {
            ll_skill.setVisibility(View.VISIBLE);
            ll_discount1.setVisibility(View.VISIBLE);
            ll_team1.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);

            ll_skill.setBackgroundResource(R.mipmap.bg_home_skill);
            ll_discount1.setBackgroundResource(R.mipmap.bg_home_coupon);

            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger12 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger12.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger13 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger13.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount1.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount1.startScroll();
            }else {
                rv_discount1.stopScroll();
            }
        }

        //满赠和组合
        if(dataActive.getFullGift()!=null && dataActive.getTeam()!=null) {
            ll_full1.setVisibility(View.VISIBLE);
            ll_team1.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);

            ll_full1.setBackgroundResource(R.mipmap.bg_home_full);
            ll_team1.setBackgroundResource(R.mipmap.bg_home_team);

            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short2,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger14 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger14.setOrientation(RecyclerView.VERTICAL);
            rv_full1.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full1.startScroll();
            }else {
                rv_full1.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger15 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger15.setOrientation(RecyclerView.VERTICAL);
            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
            rv_team1.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team1.startScroll();
            }else {
                rv_team1.stopScroll();
            }
        }

        //满赠和折扣
        if(dataActive.getFullGift()!=null && dataActive.getSpecial()!=null) {
            ll_full1.setVisibility(View.VISIBLE);
            ll_discount1.setVisibility(View.VISIBLE);
            ll_team1.setVisibility(View.GONE);
            ll_skill.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);
            ll_team.setVisibility(View.GONE);
            ll_discount.setVisibility(View.GONE);

            ll_full1.setBackgroundResource(R.mipmap.bg_home_full);
            ll_discount1.setBackgroundResource(R.mipmap.bg_home_coupon);

            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short2,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger16 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger16.setOrientation(RecyclerView.VERTICAL);
            rv_full1.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full1.startScroll();
            }else {
                rv_full1.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger17 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger17.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount1.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount1.startScroll();
            }else {
                rv_discount1.stopScroll();
            }
        }

        //组合和折扣
        if(dataActive.getTeam()!=null && dataActive.getSpecial()!=null) {
            ll_team.setVisibility(View.VISIBLE);
            ll_discount.setVisibility(View.VISIBLE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);
            ll_skill.setVisibility(View.GONE);
            ll_full.setVisibility(View.GONE);

            ll_team.setBackgroundResource(R.mipmap.bg_home_team);
            ll_discount.setBackgroundResource(R.mipmap.bg_home_coupon);

            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger18 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger18.setOrientation(RecyclerView.VERTICAL);
            rv_team.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team.startScroll();
            }else {
                rv_team.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger19 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger19.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount.startScroll();
            }else {
                rv_discount.stopScroll();
            }
        }
    }

    private void getThree(CouponModels.DataBean dataActive) {
        //秒杀为空
        if(dataActive.getSpike()==null && dataActive.getTeam()!=null && dataActive.getSpecial()!=null && dataActive.getFullGift()!=null) {
            ll_team.setVisibility(View.VISIBLE);
            ll_discount.setVisibility(View.VISIBLE);
            ll_full.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);

            ll_full.setBackgroundResource(R.mipmap.bg_home_full_l);
            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_special,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger20 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger20.setOrientation(RecyclerView.VERTICAL);
            rv_full.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full.startScroll();
            }else {
                rv_full.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger21 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger21.setOrientation(RecyclerView.VERTICAL);
            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
            rv_team.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team.startScroll();
            }else {
                rv_team.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger22 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger22.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount.startScroll();
            }else {
                rv_discount.stopScroll();
            }
        }

        //满赠为空
        if(dataActive.getFullGift()==null && dataActive.getSpike()!=null && dataActive.getTeam()!=null && dataActive.getSpecial()!=null) {
            ll_team.setVisibility(View.VISIBLE);
            ll_discount.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.VISIBLE);
            ll_full.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);

            ll_skill.setBackgroundResource(R.mipmap.bg_home_skill_l);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_special,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger23 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger23.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger24 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger24.setOrientation(RecyclerView.VERTICAL);
            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives(),onclickTeam);
            rv_team.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team.startScroll();
            }else {
                rv_team.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger25 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger25.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount.startScroll();
            }else {
                rv_discount.stopScroll();
            }
        }

        //组合为空
        if(dataActive.getTeam()==null && dataActive.getSpike()!=null && dataActive.getSpecial()!=null && dataActive.getFullGift()!=null) {
            ll_full.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.VISIBLE);
            ll_discount.setVisibility(View.VISIBLE);
            ll_team.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);

            ll_discount.setBackgroundResource(R.mipmap.bg_home_coupon_l);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger26 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger26.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short2,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger27 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger27.setOrientation(RecyclerView.VERTICAL);
            rv_full.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full.startScroll();
            }else {
                rv_full.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger28 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger28.setOrientation(RecyclerView.VERTICAL);
            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_special,dataActive.getSpecial().getActives(),onclickDis);
            rv_discount.setAdapter(vpDiscountAdapter);
            if(dataActive.getSpecial().getActives().size()>1) {
                rv_discount.startScroll();
            }else {
                rv_discount.stopScroll();
            }
        }

        //折扣为空
        if(dataActive.getSpecial()==null && dataActive.getSpike()!=null && dataActive.getTeam()!=null && dataActive.getFullGift()!=null) {
            ll_full.setVisibility(View.VISIBLE);
            ll_skill.setVisibility(View.VISIBLE);
            ll_team.setVisibility(View.VISIBLE);

            ll_discount.setVisibility(View.GONE);
            ll_full1.setVisibility(View.GONE);
            ll_discount1.setVisibility(View.GONE);
            ll_team1.setVisibility(View.GONE);

            ll_team.setBackgroundResource(R.mipmap.bg_home_team_l);
            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short1,dataActive.getSpike().getActives(),onclickSkill);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger29 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger29.setOrientation(RecyclerView.VERTICAL);
            rv_skill.setAdapter(vpSkillAdapter);
            if(dataActive.getSpike().getActives().size()>1) {
                rv_skill.startScroll();
            }else {
                rv_skill.stopScroll();
            }

            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short2,dataActive.getFullGift().getActives(),onclick);
            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger30 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger30.setOrientation(RecyclerView.VERTICAL);
            rv_full.setAdapter(vpFullAdapter);
            if(dataActive.getFullGift().getActives().size()>1) {
                rv_full.startScroll();
            }else {
                rv_full.stopScroll();
            }

            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger31 = new ScrollSpeedLinearLayoutManger(mActivity);
            scrollSpeedLinearLayoutManger31.setOrientation(RecyclerView.VERTICAL);
            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_special,dataActive.getTeam().getActives(),onclickTeam);
            rv_team.setAdapter(vpTeamAdapter);
            if(dataActive.getTeam().getActives().size()>1) {
                rv_team.startScroll();
            }else {
                rv_team.stopScroll();
            }
        }
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
                        if (getCommonProductModel.getCode()==1) {
                            listBeans.clear();
                            if(getCommonProductModel.getData()!=null) {
                                if(getCommonProductModel.getData().getList()!=null && getCommonProductModel.getData().getList().size()>0) {
                                    List<ProductNormalModel.DataBean.ListBean> list = getCommonProductModel.getData().getList();
                                    if(list.size()>3) {
                                        listBeans.add(list.get(0));
                                        listBeans.add(list.get(1));
                                        listBeans.add(list.get(2));
                                    }else {
                                        listBeans.addAll(list);
                                    }

                                    ll_city.setVisibility(View.VISIBLE);
                                }else {
                                    ll_city.setVisibility(View.GONE);
                                }
                            }

                            hotAdapter = new HotAdapter(R.layout.item_coupon_listss, listBeans, new HotAdapter.Onclick() {
                                @Override
                                public void tipClick() {
                                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                                        if(!SharedPreferencesUtil.getString(mActivity,"priceType").equals("1")) {
                                            AppHelper.ShowAuthDialog(mActivity,cell);
                                        }
                                    }else {
                                        initDialog();
                                    }
                                }
                            });

                            rv_hot.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                            rv_hot.setAdapter(hotAdapter);

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
    private void getPrivacy() {
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

    long start;
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


    int scrollTip = 0;
    @Override
    public void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
        getCartNum();
//        setStatusBar1();
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

    int couponNum = 0;
    private void getHomeCoupon() {
        IndexHomeAPI.getHomeCoupon(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeCouponModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HomeCouponModel homeCouponModel) {
                        if(homeCouponModel.getCode()==1) {
                            if(homeCouponModel.getData()!=null) {
                                couponNum = homeCouponModel.getData();
                                if(couponNum>0) {
                                    ll_coupon.setVisibility(View.VISIBLE);
                                    tv_coupon_num.setText(couponNum+"张");
                                }else {
                                    ll_coupon.setVisibility(View.GONE);
                                }

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeCouponModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 订单状态
     */
    OrderMarqueeAdapter marqueeAdapter;
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
                        if (indexInfoModel.getCode()==1) {
                            if(indexInfoModel.getData()!=null && indexInfoModel.getData().size() > 0) {
                                marqueeAdapter = new OrderMarqueeAdapter();
                                marqueeAdapter.setData(indexInfoModel.getData(),mActivity);
                                order_marquee.setAdapter(marqueeAdapter);
                                order_marquee.setVisibility(View.VISIBLE);
                                order_marquee.startScroll();
                            }else {
                                order_marquee.setVisibility(View.GONE);
                            }
                        }else {
                            order_marquee.setVisibility(View.GONE);
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
    MarqueeAdapter marqueeAdapters;
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
                        if (indexInfoModel.getCode()==1) {
                            if(indexInfoModel.getData()!=null) {
                                data = indexInfoModel.getData();
                                UserInfoHelper.saveAreaName(mActivity, data.getAreaName());
                                UserInfoHelper.saveCity(mActivity, data.getCityName());
                                UserInfoHelper.saveProvince(mActivity, data.getProvinceName());
                                list.clear();
                                list1.clear();
                                iconList.clear();
                                recommendList.clear();
                                classifyList.clear();
                                lav_activity_loading.hide();

                                if(data.getNoticeInfo()!=null && data.getNoticeInfo().size()> 0) {
                                    marqueeAdapters = new MarqueeAdapter();
                                    marqueeAdapters.setData(data.getNoticeInfo(),getActivity());
                                    marqueeView.setAdapter(marqueeAdapters);
                                    marqueeView.setVisibility(View.VISIBLE);
                                }else {
                                    marqueeView.setVisibility(View.GONE);
                                }

                                if(data.getNoticeInfo()!=null && data.getNoticeInfo().size()> 1) {
                                    marqueeView.startScroll();
                                }

                                if(data.getSendOrder()!=null) {
                                    IndexInfoModel.DataBean.SendOrderBean sendOrder = data.getSendOrder();
                                    HelpPay1Dialog helpPay1Dialog = new HelpPay1Dialog(mActivity,sendOrder) {
                                        @Override
                                        public void sure() {
                                            Intent intent = new Intent(mActivity, HelpPayDeliveryDetailActivity.class);
                                            startActivity(intent);
                                        }
                                    };

                                    helpPay1Dialog.show();
                                }
                                if(indexInfoModel.getData().getIcons()!=null) {
                                    iconList.addAll(data.getIcons());
                                }
                                couponListModels = indexInfoModel.getData();
                                if(null != data.getGiftReceiveBtn()) {
                                    if(data.getGiftReceiveBtn().equals("0")) {
                                        getPrivacy();
                                        getDialog(indexInfoModel);
                                    }else {
                                        getTurn();
                                    }
                                }

                                if(!SharedPreferencesUtil.getString(mActivity,"once").equals("0")) {
                                    getDialog(indexInfoModel);
                                }

                                if(couponListModels.isShowQgSchool()) {
                                    ll_school.setVisibility(View.VISIBLE);
                                }else {
                                    ll_school.setVisibility(View.GONE);
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

                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    tv_city.setText(data.getCompanyName());
                                }else {
                                    tv_city.setText(data.getAddress());
                                }


                                if(null != data.getBanners()) {
                                    if (data.getBanners().size() > 0) {
                                        for (int i = 0; i < data.getBanners().size(); i++) {
                                            list.add(data.getBanners().get(i).getDefaultPic());

                                            if (data.getBanners().get(i).getShowType() == 2) {
                                                list1.add(data.getBanners().get(i).getDetailPic());
                                            }
                                        }

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
//                                        iv_fill.setVisibility(View.GONE);
//                                        ll_bgc.setVisibility(View.VISIBLE);
                                        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                            @Override
                                            public void onPageScrolled(int i, float v, int i1) {

                                            }

                                            @Override
                                            public void onPageSelected(int pos) {
                                                if(data.getBanners().size()>0) {
                                                    if (!TextUtils.isEmpty(banners.get(pos).getRgbColor())) {
                                                        String rgbColor = banners.get(pos).getRgbColor();
                                                          GradientDrawable aDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                                                                new int[]{Color.parseColor("#"+rgbColor),Color.parseColor("#f7f7f7")});
                                                        ll_bgc.setBackground(aDrawable);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onPageScrollStateChanged(int i) {
                                            }
                                        });

                                    } else {
                                        banner.setVisibility(View.GONE);
//                                        iv_fill.setVisibility(View.VISIBLE);
//                                        Glide.with(mActivity).load(data.getHomeBackPic()).into(iv_fill);
//                                        ll_bgc.setVisibility(View.GONE);
                                    }
                                }

                                if(data.getHotKey()!=null && data.getHotKey().size()>0) {
                                    recommendData = data.getHotKey();
                                    recommendList.addAll(recommendData);
                                }

                                indexRecommendAdapter.notifyDataSetChanged();
                                ViewGroup.LayoutParams lp = rv_icon.getLayoutParams();
                                if(data.getClassifyList()!=null) {
                                    if(data.getClassifyList().size()> 0) {
                                        classifyLists = data.getClassifyList();
                                        classifyList.addAll(classifyLists);
                                        rvIconAdapter = new RvIconAdapter(R.layout.item_home_icon,classifyList);

                                        rv_icon.setVisibility(View.VISIBLE);
                                        if(classifyList.size()<=5) {
                                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,5);
                                            rv_icon.setLayoutManager(gridLayoutManager1);
                                            rv_icon.setAdapter(rvIconAdapter);
                                            lp.height = DensityUtil.dip2px(60 * 1,getActivity());
                                        }else if(classifyList.size()>5 && classifyList.size()<=8 && classifyList.size()!=5 ) {
                                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,4);
                                            rv_icon.setLayoutManager(gridLayoutManager1);
                                            rv_icon.setAdapter(rvIconAdapter);
                                            lp.height = DensityUtil.dip2px(120 * 1,getActivity());
                                        }else if(classifyList.size()==9 || classifyList.size()==10) {
                                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,5);
                                            rv_icon.setLayoutManager(gridLayoutManager1);
                                            rv_icon.setAdapter(rvIconAdapter);
                                            lp.height = DensityUtil.dip2px(120 * 1,getActivity());
                                        }else {
                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, RecyclerView.HORIZONTAL, false);
                                            rv_icon.setLayoutManager(gridLayoutManager);
                                            rv_icon.setAdapter(rvIconAdapter);
                                            lp.height = DensityUtil.dip2px(120 * 1,getActivity());
                                        }

                                        if(classifyList.size()>10) {
                                            indicator.setVisibility(View.VISIBLE);
                                        }else {
                                            indicator.setVisibility(View.GONE);
                                        }

                                    }else {
                                        rv_icon.setVisibility(View.GONE);
                                        indicator.setVisibility(View.GONE);
                                    }
                                }

                                rv_icon.setLayoutParams(lp);
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
                            }else {
                                lav_activity_loading.hide();
                            }

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

                if(showType == 6) {
                    mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
                    EventBus.getDefault().post(new GoToMarketEvent());
                    EventBus.getDefault().postSticky(new FromIndexEvent(banners.get(position).getBusinessId() + ""));
                }

                if (showType == 1 || banners.get(position).getLinkSrc() != null) {
                    //链接 banners.get(position).getLinkSrc()
                    Intent intent = new Intent(getActivity(), NewWebViewActivity.class);
                    intent.putExtra("URL", banners.get(position).getLinkSrc());
                    intent.putExtra("TYPE", 2);
                    intent.putExtra("name", "");
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
//                        Intent intent = new Intent(getActivity(), CommonProductActivity.class);
//                        startActivity(intent);
                        appbar.setExpanded(false);
                        getCommonStateTop();
                        switchCommon();
                    } else if (AppConstant.DEDUCT_PROD.equals(banners.get(position).getProdPage())) {
                        appbar.setExpanded(false);
                        getReduceStateTop();
                        switchReduce();
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
        ImageView iv_cancel = (ImageView) mDialog.getWindow().findViewById(R.id.iv_cancel);
        TextView tv_update = (TextView) mDialog.getWindow().findViewById(R.id.tv_update);
        TextView tv_content = (TextView) mDialog.getWindow().findViewById(R.id.tv_content);

        tv_content.setText(content);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_school:
                Intent intentsc = new Intent(mActivity, SchoolActivity.class);
                startActivity(intentsc);
                break;

            case R.id.iv_enter:
                iv_school.setVisibility(View.GONE);
                iv_enter.setVisibility(View.GONE);
                iv_half_school.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_half_school:
                iv_school.setVisibility(View.VISIBLE);
                iv_enter.setVisibility(View.VISIBLE);
                iv_half_school.setVisibility(View.GONE);
                break;
            case R.id.rl_huo:
                if(data.getHllOrderCallNum()>1) {
                    Intent intent = MyOrdersActivity.getIntent(getContext(), MyOrdersActivity.class, AppConstant.ALL);
                    intent.putExtra("orderDeliveryType",0);
                    startActivity(intent);
                }else {
                    Intent intentsss = new Intent(mActivity, NewOrderDetailActivity.class);
                    intentsss.putExtra("orderId",data.getOrderId());
                    intentsss.putExtra("account","0");
                    startActivity(intentsss);
                }
                break;

            case R.id.iv_huo_company:
                isAuth();
                break;

            case R.id.ll_coupon:
                Intent intent3 = new Intent(mActivity,MyCouponsActivity.class);
                startActivity(intent3);
                break;

            case R.id.rl_search:
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
//                    Intent intent2 = new Intent(getActivity(), TestActivity.class);
//                    startActivity(intent2);
//                    appbar.setExpanded(false);
//                    getReduceStateTop();
//                    switchReduce();
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


            case R.id.tv_change:
                Intent changeCityIntent = new Intent(getActivity(), ChangeCityActivity.class);
                startActivityForResult(changeCityIntent, 105);
                break;

            case R.id.tv_change_address:
                chooseAddressDialog = new ChooseHomeDialog(mActivity, "");
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
                    tv_num.setText(newPosition + "");
                } else {
                    tv_num.setVisibility(View.GONE);
                }
            }
        }
    }

    MyOrderNumModel mModelMyOrderNum;
    private void getMessage() {
        MyOrderNumAPI.requestOrderNum(mActivity,SharedPreferencesUtil.getInt(mActivity,"wad"))
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
            tv_num.setText(mModelMyOrderNum.getData().getNotice() + "");
        } else {
            tv_num.setVisibility(View.GONE);
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
        couponNum=0;
        if(couponNum==0) {
            ll_coupon.setVisibility(View.GONE);
        }else {
            ll_coupon.setVisibility(View.VISIBLE);
        }
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            getStyle();
            ll_hot.setVisibility(View.GONE);
            rb_new.setText("热销商品");
            tv_title1.setText("超值人气");
            tv_title3.setText("专宠好物");
            rb_reduce.setText("常购清单");
            rb_new_top.setText("热销商品");
            rb_info_top.setText("常购清单");
            rb_must_common_top.setVisibility(View.GONE);
            rb_must_common.setVisibility(View.GONE);
            ll_must.setVisibility(View.GONE);
            ll_common.setVisibility(View.GONE);
//            tv_title2.setVisibility(View.GONE);
//            tv_title4.setVisibility(View.GONE);
//            v2s.setVisibility(View.GONE);
//            v4s.setVisibility(View.GONE);
            ll_must_top.setVisibility(View.GONE);
            ll_common_top.setVisibility(View.GONE);
            rb_common.setVisibility(View.GONE);
            rb_common_top.setVisibility(View.GONE);
            rg_new.check(R.id.rb_new);
        }else {
            ll_hot.setVisibility(View.VISIBLE);
            rb_reduce.setText("降价商品");
            rb_new.setText("新品上市");
            tv_title1.setText("上新立荐");
            tv_title3.setText("物美价廉");
            rb_new_top.setText("新品上市");
            rb_info_top.setText("降价商品");
//            v2s.setVisibility(View.VISIBLE);
//            v4s.setVisibility(View.VISIBLE);
//            tv_title2.setVisibility(View.VISIBLE);
//            tv_title4.setVisibility(View.VISIBLE);
            ll_must.setVisibility(View.VISIBLE);
            ll_common.setVisibility(View.VISIBLE);
            ll_must_top.setVisibility(View.VISIBLE);
            ll_common_top.setVisibility(View.VISIBLE);
            rb_must_common_top.setVisibility(View.VISIBLE);
            rb_must_common.setVisibility(View.VISIBLE);
            rb_common.setVisibility(View.VISIBLE);
            rb_common_top.setVisibility(View.VISIBLE);
            rg_new.check(R.id.rb_must_common);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginsEvent(AddressEvent event) {
        //刷新UI
        refreshLayout.autoRefresh();
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            getStyle();
            ll_hot.setVisibility(View.GONE);
            rb_new.setText("热销商品");
            tv_title1.setText("超值人气");
            tv_title3.setText("专宠好物");
            rb_reduce.setText("常购清单");
            rb_new_top.setText("热销商品");
            rb_info_top.setText("常购清单");
            rb_must_common_top.setVisibility(View.GONE);
            rb_must_common.setVisibility(View.GONE);
//          tv_title2.setVisibility(View.GONE);
//            tv_title4.setVisibility(View.GONE);
//            v2s.setVisibility(View.GONE);
//            v4s.setVisibility(View.GONE);
            ll_must.setVisibility(View.GONE);
            ll_common.setVisibility(View.GONE);
            ll_must_top.setVisibility(View.GONE);
            ll_common_top.setVisibility(View.GONE);
            rb_common.setVisibility(View.GONE);
            rb_common_top.setVisibility(View.GONE);
            rg_new.check(R.id.rb_new);
        }else {
            ll_hot.setVisibility(View.VISIBLE);
            rb_reduce.setText("降价商品");
            rb_new.setText("新品上市");
            tv_title1.setText("上新立荐");
            tv_title3.setText("物美价廉");
            rb_new_top.setText("新品上市");
            rb_info_top.setText("降价商品");
//            v2s.setVisibility(View.VISIBLE);
//            v4s.setVisibility(View.VISIBLE);
//            tv_title2.setVisibility(View.VISIBLE);
//            tv_title4.setVisibility(View.VISIBLE);
            ll_must.setVisibility(View.VISIBLE);
            ll_common.setVisibility(View.VISIBLE);
            ll_must_top.setVisibility(View.VISIBLE);
            ll_common_top.setVisibility(View.VISIBLE);
            rb_must_common_top.setVisibility(View.VISIBLE);
            rb_must_common.setVisibility(View.VISIBLE);
            rb_common.setVisibility(View.VISIBLE);
            rb_common_top.setVisibility(View.VISIBLE);
            rg_new.check(R.id.rb_must_common);
        }
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


    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    int position;
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_new:
                    position = 1;
                    getNewState();
                    switchNew();
                    break;

                case R.id.rb_must_common:
                    position = 0;
                    getMustState();
                    switchMust();
                    break;

                case R.id.rb_reduce:
                    getReduceState();
                    switchReduce();
                    break;

                case R.id.rb_common:
                    position = 3;
                    getCommonState();
                    switchCommon();
                    break;
            }
        }
    }



    private void setListener() {
        rg_new.setOnCheckedChangeListener(new HomeFragment.MyOnCheckedChangeListener());
        rg_new_top.setOnCheckedChangeListener(new HomeFragment.MyTopOnCheckedChangeListener());
        //设置默认选中框架页面
        rg_new.check(R.id.rb_must_common);
    }

    private class MyTopOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_new_top:
                    getNewStateTop();
                    switchNew();
                    break;

                case R.id.rb_must_common_top:
                    getMustStateTop();
                    switchMust();
                    break;

                case R.id.rb_info_top:
                    getReduceStateTop();
                    switchReduce();
                    break;

                case R.id.rb_common_top:
                    getCommonStateTop();
                    switchCommon();
                    break;
            }
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

    private void getMustState() {
        position = 0;
        view_reduce.setBackgroundResource(R.drawable.shape_white1);
        view_new.setBackgroundResource(R.drawable.shape_white1);
        view_must.setBackgroundResource(R.drawable.shape_orange25);
        view_common.setBackgroundResource(R.drawable.shape_white1);
    }

    private void getNewState() {
        position = 1;
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            view_common.setVisibility(View.GONE);
            view_must.setVisibility(View.GONE);
        }else {
            view_common.setVisibility(View.VISIBLE);
            view_must.setVisibility(View.VISIBLE);
            view_common.setBackgroundResource(R.drawable.shape_white1);
            view_must.setBackgroundResource(R.drawable.shape_white1);
        }
        view_new.setBackgroundResource(R.drawable.shape_orange25);
        view_reduce.setBackgroundResource(R.drawable.shape_white1);
    }

    private void getReduceState() {
        position = 2;
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            view_common.setVisibility(View.GONE);
            view_must.setVisibility(View.GONE);
        }else {
            view_common.setVisibility(View.VISIBLE);
            view_must.setVisibility(View.VISIBLE);
            view_common.setBackgroundResource(R.drawable.shape_white1);
            view_must.setBackgroundResource(R.drawable.shape_white1);
        }

        view_reduce.setBackgroundResource(R.drawable.shape_orange25);
        view_new.setBackgroundResource(R.drawable.shape_white1);
    }

    private void getCommonState() {
        position = 3;
        view_reduce.setBackgroundResource(R.drawable.shape_white1);
        view_new.setBackgroundResource(R.drawable.shape_white1);
        view_must.setBackgroundResource(R.drawable.shape_white1);
        view_common.setBackgroundResource(R.drawable.shape_orange25);
    }

    private void getMustStateTop() {
        position = 0;
        rb_must_common_top.setChecked(true);
        rb_new.setChecked(false);
        rb_reduce.setChecked(false);
        rb_common.setChecked(false);
        rb_must_common.setChecked(true);
        v4s.setVisibility(View.INVISIBLE);
        v1s.setVisibility(View.INVISIBLE);
        v3s.setVisibility(View.INVISIBLE);
        v2s.setVisibility(View.VISIBLE);
}

    private void getNewStateTop() {
        position = 1;
        rb_new_top.setChecked(true);
        rb_new.setChecked(true);
        rb_reduce.setChecked(false);
        rb_common.setChecked(false);
        rb_must_common.setChecked(false);
        v1s.setVisibility(View.VISIBLE);
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            v4s.setVisibility(View.INVISIBLE);
            v2s.setVisibility(View.GONE);
            v3s.setVisibility(View.GONE);
        }else {
            v4s.setVisibility(View.INVISIBLE);
            v2s.setVisibility(View.INVISIBLE);
            v3s.setVisibility(View.INVISIBLE);
        }
    }

    private void getReduceStateTop() {
        rb_info_top.setChecked(true);
        rb_new.setChecked(false);
        rb_reduce.setChecked(true);
        rb_common.setChecked(false);
        rb_must_common.setChecked(false);
        position = 2;
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            v4s.setVisibility(View.GONE);
            v1s.setVisibility(View.INVISIBLE);
            v2s.setVisibility(View.GONE);
        }else {
            v4s.setVisibility(View.INVISIBLE);
            v1s.setVisibility(View.INVISIBLE);
            v2s.setVisibility(View.INVISIBLE);
        }

        v3s.setVisibility(View.VISIBLE);
    }

    private void getCommonStateTop() {
        Log.d("wsssss........","444");
        rb_common_top.setChecked(true);
        rb_new.setChecked(false);
        rb_reduce.setChecked(false);
        rb_common.setChecked(true);
        rb_must_common.setChecked(false);
        position = 3;

        v4s.setVisibility(View.VISIBLE);
        v1s.setVisibility(View.INVISIBLE);
        v2s.setVisibility(View.INVISIBLE);
        v3s.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initViews(View view) {
//        my_scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (!isDown){//惯性滚动时，重新倒计时
//                    scrollCountTimer.cancel();
//                    scrollCountTimer.start();
//                }
//            }
//        });

//        my_scroll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL://抬起开始倒计时
//                        isDown = false;
//                        scrollCountTimer.start();
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_MOVE://按下或移动状态取消倒计时
//                        isDown = true;
//                        scrollCountTimer.cancel();
//                        break;
//                }
//                return onTouchEvent(event);
//            }
//        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    /**
     * 获取首页风格
     */
    HomeStyleModel.DataBean styleData;
    private void getHomeStyle() {
        IndexHomeAPI.getStyle(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStyleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HomeStyleModel homeStyleModel) {
                        if(homeStyleModel.getCode()==1) {
                            if(homeStyleModel.getData()!=null) {
                                styleData = homeStyleModel.getData();
                                if(styleData.getAppHomeUrl()!=null&& !styleData.getAppHomeUrl().equals("")) {
                                    iv_fill.setVisibility(View.VISIBLE);
                                    ll_bgc.setVisibility(View.GONE);
                                    Glide.with(mActivity).load(styleData.getAppHomeUrl()).into(iv_fill);
                                }else {
                                    ll_bgc.setVisibility(View.VISIBLE);
                                    iv_fill.setVisibility(View.GONE);
                                }
//                                normal常规，black_white黑白，may_day五一，national_day国庆，spring_festival春节

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeStyleModel.getMessage());
                        }
                    }
                });
    }
    protected void setStatusBar1() {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }
}
