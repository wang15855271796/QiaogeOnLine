package com.puyue.www.qiaoge.fragment.home;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.animation.IntEvaluator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.DensityUtil;
import com.frankfancode.marqueeview.MarqueeView;
import com.google.android.material.appbar.AppBarLayout;
import com.puyue.www.qiaoge.AutoPollRecyclerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.ChooseCompanyActivity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.home.SearchReasultActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.CommonCouponAdapter;
import com.puyue.www.qiaoge.adapter.CommonsAdapter;
import com.puyue.www.qiaoge.adapter.CommonssAdapter;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.adapter.FullAdapter;
import com.puyue.www.qiaoge.adapter.HotAdapter;
import com.puyue.www.qiaoge.adapter.IndexRecommendAdapter;
import com.puyue.www.qiaoge.adapter.MarqueeAdapter;
import com.puyue.www.qiaoge.adapter.OrderMarqueeAdapter;
import com.puyue.www.qiaoge.adapter.Skill2Adapter;
import com.puyue.www.qiaoge.adapter.Skill3Adapter;
import com.puyue.www.qiaoge.adapter.Team3Adapter;
import com.puyue.www.qiaoge.adapter.TeamAdapter;
import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.adapter.VpDiscountAdapter;
import com.puyue.www.qiaoge.adapter.VpFullAdapter;
import com.puyue.www.qiaoge.adapter.VpSkillAdapter;
import com.puyue.www.qiaoge.adapter.VpTeamAdapter;
import com.puyue.www.qiaoge.adapter.home.CommonAdapter;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseHomeDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.HuoOrderDialog;
import com.puyue.www.qiaoge.dialog.PrivacysDialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.ChangeCityModel;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.HomeNewRecommendModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.HIndicators;
import com.puyue.www.qiaoge.view.ScrollSpeedLinearLayoutManger;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView3;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.taobao.library.VerticalBannerView;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class HomeFragment2 extends BaseFragment implements View.OnClickListener {
    Unbinder bind;
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
    @BindView(R.id.tv_full_title1)
    TextView tv_full_title1;

    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.rv_team)
    AutoPollRecyclerView rv_team;
    @BindView(R.id.rv_discount)
    AutoPollRecyclerView rv_discount;
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
    @BindView(R.id.snap1)
    SnapUpCountDownTimerView3 snap1;
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
    @BindView(R.id.rv_full)
    AutoPollRecyclerView rv_full;
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
    @BindView(R.id.rl_hot)
    RelativeLayout rl_hot;
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
//    @BindView(R.id.rl_huos)
//    RelativeLayout rl_huos;
    @BindView(R.id.rv_skill)
    AutoPollRecyclerView rv_skill;
    @BindView(R.id.rv_team1)
    AutoPollRecyclerView rv_team1;
    @BindView(R.id.ll_discount1)
    LinearLayout ll_discount1;
    @BindView(R.id.ll_full1)
    LinearLayout ll_full1;
    @BindView(R.id.rv_full1)
    AutoPollRecyclerView rv_full1;
    @BindView(R.id.rv_discount1)
    AutoPollRecyclerView rv_discount1;
    //    @BindView(R.id.vp_discount)
//    ViewPager2 vp_discount;
//    @BindView(R.id.vp_full)
//    ViewPager2 vp_full;
    @BindView(R.id.ll_skill_bg)
    LinearLayout ll_skill_bg;
    @BindView(R.id.ll_team_bg)
    LinearLayout ll_team_bg;
    @BindView(R.id.ll_discount_bg)
    LinearLayout ll_discount_bg;
    @BindView(R.id.ll_full_bg)
    LinearLayout ll_full_bg;
    @BindView(R.id.ll_full_bg1)
    LinearLayout ll_full_bg1;
    @BindView(R.id.ll_team_bg1)
    LinearLayout ll_team_bg1;
    @BindView(R.id.ll_discount_bg1)
    LinearLayout ll_discount_bg1;
    @BindView(R.id.ll_full)
    LinearLayout ll_full;
    @BindView(R.id.ll_discount)
    LinearLayout ll_discount;
    @BindView(R.id.ll_team)
    LinearLayout ll_team;
    @BindView(R.id.ll_team1)
    LinearLayout ll_team1;
    @BindView(R.id.iv_empty_hot)
    ImageView iv_empty_hot;
    @BindView(R.id.order_marquee)
    MarqueeView order_marquee;

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
    private CouponModels.DataBean dataActive;
    private int showType;
    private CommonCouponAdapter commonCouponAdapter;
    private CommonsAdapter commonsAdapter;
    private CommonAdapter commonAdapter;
    //    private List<CouponModels.DataBean.ActivesBean> actives = new ArrayList<>();
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
    private String deductAmountStr;
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

    @Override
    public void findViewById(View view) {
        bind = ButterKnife.bind(this, view);


        setListener();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

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
                                rb_new_top.setTextColor(Color.parseColor("#17BD60"));
                                rb_must_common_top.setTextColor(Color.parseColor("#333333"));
                                rb_info_top.setTextColor(Color.parseColor("#333333"));
                                rb_common_top.setTextColor(Color.parseColor("#333333"));
                            }else if(rb_reduce.isChecked()) {
                                rb_info_top.setChecked(true);
                                Log.d("efsdfew.....","b");
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

        //顶部推荐
        indexRecommendAdapter = new IndexRecommendAdapter(R.layout.item_index_recommend, recommendList);
        rv_recommend.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_recommend.setAdapter(indexRecommendAdapter);

        indexRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, SearchReasultActivity.class);
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
        rl_address.setOnClickListener(null);
        rl_huo.setOnClickListener(this);
//        rl_huos.setOnClickListener(this);
        lav_activity_loading.show();
//        requestUpdate();
        refreshLayout.autoRefresh();
        couponListAdapter = new CouponListAdapter(R.layout.item_home_coupon_list, lists);
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PageNum = 1;
                newList.clear();
                skillList.clear();
                skillAdvList.clear();
                driverList.clear();
                getBaseLists();
                getSpikeList();
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

    @Override
    public void setViewData() {
        //判断用户是否选择了企业
        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            getStyle();
            rb_new.setText("热销商品");
            tv_title1.setText("超值人气");
            tv_title3.setText("专宠好物");
            rb_reduce.setText("常购清单");
            rb_new_top.setText("热销商品");
            rb_info_top.setText("常购清单");
            rb_must_common_top.setVisibility(View.GONE);
            rb_must_common.setVisibility(View.GONE);
            tv_title2.setVisibility(View.GONE);
            tv_title4.setVisibility(View.GONE);
            v2s.setVisibility(View.GONE);
            v4s.setVisibility(View.GONE);
            rb_common.setVisibility(View.GONE);
            rb_common_top.setVisibility(View.GONE);
            rg_new.check(R.id.rb_new);
        }else {
            rb_reduce.setText("降价商品");
            rb_new.setText("新品上市");
            tv_title1.setText("上新立荐");
            tv_title3.setText("物美价廉");
            rb_new_top.setText("新品上市");
            rb_info_top.setText("降价商品");
            v2s.setVisibility(View.VISIBLE);
            v4s.setVisibility(View.VISIBLE);
            tv_title2.setVisibility(View.VISIBLE);
            tv_title4.setVisibility(View.VISIBLE);
            rb_must_common_top.setVisibility(View.VISIBLE);
            rb_must_common.setVisibility(View.VISIBLE);
            rb_common.setVisibility(View.VISIBLE);
            rb_common_top.setVisibility(View.VISIBLE);
            rg_new.check(R.id.rb_must_common);
        }
    }

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    int position;
    private void setListener() {
        rg_new.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_new_top.setOnCheckedChangeListener(new MyTopOnCheckedChangeListener());
        //设置默认选中框架页面
        rg_new.check(R.id.rb_must_common);
    }

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
                    v2s.setVisibility(View.VISIBLE);
                    if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                        v4s.setVisibility(View.INVISIBLE);
                        v1s.setVisibility(View.GONE);
                        v3s.setVisibility(View.GONE);
                    }else {
                        v4s.setVisibility(View.INVISIBLE);
                        v1s.setVisibility(View.INVISIBLE);
                        v3s.setVisibility(View.INVISIBLE);
                    }

                    switchNew();
                    break;

                case R.id.rb_must_common_top:
                    position = 0;
                    Log.d("efsdfew.....","d");
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
                    v3s.setVisibility(View.INVISIBLE);
                    v2s.setVisibility(View.VISIBLE);
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
                    switchReduce();
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
                                    if(DateUtils.isExceed100(startTime, endTime)) {
                                        String day = DateUtils.getDay(endTime - startTime);
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
                });
    }

    int num = 0;
    //活动区域
    private void getActive(CouponModels.DataBean dataActive) {
        if(dataActive.getTeam()!=null) {
            num++;
        }

        if(dataActive.getSpecial()!=null) {
            num++;
        }

        if(dataActive.getFullGift()!=null) {
            num++;
        }

        if(dataActive.getSpike()!=null) {
            num++;
        }

        Log.d("wdasdwda....",num+"aaa");

        switch (num) {
            case 1:
//                getOne(dataActive);
                break;

            case 2:
//                getTwo(dataActive);
                break;

            case 3:
//                getThree(dataActive);
                break;

            case 4:
//                getFour(dataActive);
                break;
        }

    }
//
//    private void getFour(CouponModels.DataBean dataActive) {
//        ll_team.setVisibility(View.VISIBLE);
//        ll_discount.setVisibility(View.VISIBLE);
//        ll_full.setVisibility(View.VISIBLE);
//        ll_skill.setVisibility(View.VISIBLE);
//        ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount);
//        ll_team_bg.setBackgroundResource(R.mipmap.bg_team);
//        ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls);
//        ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//        VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger = new ScrollSpeedLinearLayoutManger(mActivity);
//        scrollSpeedLinearLayoutManger.setOrientation(RecyclerView.HORIZONTAL);
//        rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger);
//        rv_skill.setAdapter(vpSkillAdapter);
//        if(dataActive.getSpike().getActives().size()>0) {
//            rv_skill.start();
//        }
//
//        VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger1 = new ScrollSpeedLinearLayoutManger(mActivity);
//        scrollSpeedLinearLayoutManger1.setOrientation(RecyclerView.HORIZONTAL);
//        rv_full.setLayoutManager(scrollSpeedLinearLayoutManger1);
//        rv_full.setAdapter(vpFullAdapter);
//        if(dataActive.getFullGift().getActives().size()>0) {
//            rv_full.start();
//        }
//
//
//        VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
////        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger2 = new ScrollSpeedLinearLayoutManger(mActivity);
//        scrollSpeedLinearLayoutManger2.setOrientation(RecyclerView.HORIZONTAL);
//        rv_team.setLayoutManager(scrollSpeedLinearLayoutManger2);
//        rv_team.setAdapter(vpTeamAdapter);
//        if(dataActive.getTeam().getActives().size()>0) {
//            rv_team.start();
//        }
//
//
////        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger3 = new ScrollSpeedLinearLayoutManger(mActivity);
//        scrollSpeedLinearLayoutManger3.setOrientation(RecyclerView.HORIZONTAL);
//        VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//        rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger3);
//        rv_discount.setAdapter(vpDiscountAdapter);
//        if(dataActive.getSpecial().getActives().size()>0) {
//            rv_discount.start();
//        }
//
//    }
//
//    private void getOne(CouponModels.DataBean dataActive) {
//        if(dataActive.getSpecial()!=null) {
//            ll_discount.setVisibility(View.VISIBLE);
//            ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount_long);
//
////            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger4 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger4.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_special,dataActive.getSpecial().getActives());
//            rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger4);
//            rv_discount.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount.start();
//            }
//        }
//
//        if(dataActive.getTeam()!=null) {
//            ll_team.setVisibility(View.VISIBLE);
//            ll_team_bg.setBackgroundResource(R.mipmap.bg_team_long);
//
//            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false);
//////            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_special,dataActive.getTeam().getActives());
////
//            Test3Adapter test3Adapter = new Test3Adapter();
////            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger5 = new ScrollSpeedLinearLayoutManger(mActivity);
////            scrollSpeedLinearLayoutManger5.setOrientation(scrollSpeedLinearLayoutManger5.VERTICAL);
//            rv_team.setLayoutManager(linearLayoutManager1);
//            rv_team.setAdapter(test3Adapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team.start();
//            }
//            test3Adapter.notifyDataSetChanged();
//        }
//
//        if(dataActive.getFullGift()!=null) {
//            ll_full.setVisibility(View.VISIBLE);
//            ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls_long);
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_special,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger6 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger6.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full.setLayoutManager(scrollSpeedLinearLayoutManger6);
//            rv_full.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full.start();
//            }
//        }
//
//        if(dataActive.getSpike()!=null) {
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills_long);
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_special,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger7 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger7.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger7);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//        }
//    }
//
//    private void getTwo(CouponModels.DataBean dataActive) {
//        //秒杀和满赠
//        if(dataActive.getSpike()!=null && dataActive.getFullGift()!=null) {
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_full.setVisibility(View.VISIBLE);
//
//            ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger8 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger8.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger8);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger9 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger9.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full.setLayoutManager(scrollSpeedLinearLayoutManger9);
//            rv_full.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full.start();
//            }
//        }
//
//        //秒杀和组合
//        if(dataActive.getSpike()!=null && dataActive.getTeam()!=null) {
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_team1.setVisibility(View.VISIBLE);
//            ll_team_bg1.setBackgroundResource(R.mipmap.bg_team);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger10 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger10.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger10);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger11 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger11.setOrientation(RecyclerView.HORIZONTAL);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
//            rv_team1.setLayoutManager(scrollSpeedLinearLayoutManger11);
//            rv_team1.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team1.start();
//            }
//        }
//
//        //秒杀和折扣
//        if(dataActive.getSpike()!=null && dataActive.getSpecial()!=null) {
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_discount1.setVisibility(View.VISIBLE);
//
//            ll_discount_bg1.setBackgroundResource(R.mipmap.bg_discount);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger12 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger12.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger12);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger13 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger13.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//            rv_discount1.setLayoutManager(scrollSpeedLinearLayoutManger13);
//            rv_discount1.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount1.start();
//            }
//        }
//
//        //满赠和组合
//        if(dataActive.getFullGift()!=null && dataActive.getTeam()!=null) {
//            ll_full1.setVisibility(View.VISIBLE);
//            ll_team1.setVisibility(View.VISIBLE);
//
//            ll_full_bg1.setBackgroundResource(R.mipmap.bg_fulls);
//            ll_team_bg1.setBackgroundResource(R.mipmap.bg_team);
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger14 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger14.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full1.setLayoutManager(scrollSpeedLinearLayoutManger14);
//            rv_full1.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full1.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger15 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger15.setOrientation(RecyclerView.HORIZONTAL);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
//            rv_team1.setLayoutManager(scrollSpeedLinearLayoutManger15);
//            rv_team1.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team1.start();
//            }
//        }
//
//        //满赠和折扣
//        if(dataActive.getFullGift()!=null && dataActive.getSpecial()!=null) {
//            ll_full1.setVisibility(View.VISIBLE);
//            ll_discount1.setVisibility(View.VISIBLE);
//
//            ll_full_bg1.setBackgroundResource(R.mipmap.bg_fulls);
//            ll_discount_bg1.setBackgroundResource(R.mipmap.bg_discount);
//
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger16 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger16.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full1.setLayoutManager(scrollSpeedLinearLayoutManger16);
//            rv_full1.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full1.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger17 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger17.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//            rv_discount1.setLayoutManager(scrollSpeedLinearLayoutManger17);
//            rv_discount1.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount1.start();
//            }
//        }
//
//        //组合和折扣
//        if(dataActive.getTeam()!=null && dataActive.getSpecial()!=null) {
//            ll_team.setVisibility(View.VISIBLE);
//            ll_discount.setVisibility(View.VISIBLE);
//
//            ll_team_bg.setBackgroundResource(R.mipmap.bg_team);
//            ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger18 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger18.setOrientation(RecyclerView.HORIZONTAL);
//            rv_team.setLayoutManager(scrollSpeedLinearLayoutManger18);
//            rv_team.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger19 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger19.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//            rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger19);
//            rv_discount.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount.start();
//            }
//        }
//    }
//
//
//    private void getThree(CouponModels.DataBean dataActive) {
//        //秒杀为空
//        if(dataActive.getSpike()==null && dataActive.getTeam()!=null && dataActive.getSpecial()!=null && dataActive.getFullGift()!=null) {
//            ll_team.setVisibility(View.VISIBLE);
//            ll_discount.setVisibility(View.VISIBLE);
//            ll_full.setVisibility(View.VISIBLE);
//
//            ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls_long);
//            ll_team_bg.setBackgroundResource(R.mipmap.bg_team);
//            ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount);
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_special,dataActive.getFullGift().getActives());
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger20 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger20.setOrientation(RecyclerView.HORIZONTAL);
////            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            rv_full.setLayoutManager(scrollSpeedLinearLayoutManger20);
//            rv_full.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger21 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger21.setOrientation(RecyclerView.HORIZONTAL);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
//            rv_team.setLayoutManager(scrollSpeedLinearLayoutManger21);
//            rv_team.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team.start();
//            }
//
////            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger22 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger22.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//            rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger22);
//            rv_discount.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount.start();
//            }
//        }
//
//        //满赠为空
//        if(dataActive.getFullGift()==null && dataActive.getSpike()!=null && dataActive.getTeam()!=null && dataActive.getSpecial()!=null) {
//            ll_team.setVisibility(View.VISIBLE);
//            ll_discount.setVisibility(View.VISIBLE);
//            ll_skill.setVisibility(View.VISIBLE);
//
//            ll_team_bg.setBackgroundResource(R.mipmap.bg_team);
//            ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills_long);
//
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_special,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger23 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger23.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger23);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger24 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger24.setOrientation(RecyclerView.HORIZONTAL);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_short,dataActive.getTeam().getActives());
//            rv_team.setLayoutManager(scrollSpeedLinearLayoutManger24);
//            rv_team.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team.start();
//            }
//
////            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger25 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger25.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_short,dataActive.getSpecial().getActives());
//            rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger25);
//            rv_discount.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount.start();
//            }
//        }
//
//        //组合为空
//        if(dataActive.getTeam()==null && dataActive.getSpike()!=null && dataActive.getSpecial()!=null && dataActive.getFullGift()!=null) {
//            ll_full.setVisibility(View.VISIBLE);
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_discount.setVisibility(View.VISIBLE);
//
//            ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls);
//            ll_discount_bg.setBackgroundResource(R.mipmap.bg_discount_long);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger26 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger26.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger26);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger27 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger27.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full.setLayoutManager(scrollSpeedLinearLayoutManger27);
//            rv_full.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full.start();
//            }
//
////            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger28 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger28.setOrientation(RecyclerView.HORIZONTAL);
//            VpDiscountAdapter vpDiscountAdapter = new VpDiscountAdapter(mActivity,R.layout.item_active_special,dataActive.getSpecial().getActives());
//            rv_discount.setLayoutManager(scrollSpeedLinearLayoutManger28);
//            rv_discount.setAdapter(vpDiscountAdapter);
//            if(dataActive.getSpecial().getActives().size()>1) {
//                rv_discount.start();
//            }
//        }
//
//        //折扣为空
//        if(dataActive.getSpecial()==null && dataActive.getSpike()!=null && dataActive.getTeam()!=null && dataActive.getFullGift()!=null) {
//            ll_full.setVisibility(View.VISIBLE);
//            ll_skill.setVisibility(View.VISIBLE);
//            ll_team.setVisibility(View.VISIBLE);
//
//            ll_full_bg.setBackgroundResource(R.mipmap.bg_fulls);
//            ll_team_bg.setBackgroundResource(R.mipmap.bg_team_long);
//            ll_skill_bg.setBackgroundResource(R.mipmap.bg_skills);
//
//            VpSkillAdapter vpSkillAdapter = new VpSkillAdapter(mActivity,R.layout.item_active_short,dataActive.getSpike().getActives());
////            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger29 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger29.setOrientation(RecyclerView.HORIZONTAL);
//            rv_skill.setLayoutManager(scrollSpeedLinearLayoutManger29);
//            rv_skill.setAdapter(vpSkillAdapter);
//            if(dataActive.getSpike().getActives().size()>1) {
//                rv_skill.start();
//            }
//
//            VpFullAdapter vpFullAdapter = new VpFullAdapter(mActivity,R.layout.item_active_short,dataActive.getFullGift().getActives());
////            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger30 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger30.setOrientation(RecyclerView.HORIZONTAL);
//            rv_full.setLayoutManager(scrollSpeedLinearLayoutManger30);
//            rv_full.setAdapter(vpFullAdapter);
//            if(dataActive.getFullGift().getActives().size()>1) {
//                rv_full.start();
//            }
//
////            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
//            ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger31 = new ScrollSpeedLinearLayoutManger(mActivity);
//            scrollSpeedLinearLayoutManger31.setOrientation(RecyclerView.HORIZONTAL);
//            VpTeamAdapter vpTeamAdapter = new VpTeamAdapter(mActivity,R.layout.item_active_special,dataActive.getTeam().getActives());
//            rv_team.setLayoutManager(scrollSpeedLinearLayoutManger31);
//            rv_team.setAdapter(vpTeamAdapter);
//            if(dataActive.getTeam().getActives().size()>1) {
//                rv_team.start();
//            }
//        }
//    }

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
                        if (indexInfoModel.getCode()==1) {
                            if(indexInfoModel.getData()!=null) {
                                data = indexInfoModel.getData();

                                UserInfoHelper.saveAreaName(mActivity, data.getAreaName());
                                UserInfoHelper.saveCity(mActivity, data.getCityName());
                                UserInfoHelper.saveProvince(mActivity, data.getProvinceName());

                                iconList.clear();
                                lav_activity_loading.hide();
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    rl_hot.setVisibility(View.GONE);
                                }else {
                                    getHot(1, 10, "hot");
                                }


                                getCustomerPhone();
                                isShow();
//                                getOrder();
                                if(data.getNoticeInfo()!=null && data.getNoticeInfo().size()> 0) {
                                    MarqueeAdapter marqueeAdapter = new MarqueeAdapter();
                                    marqueeAdapter.setData(data.getNoticeInfo(),getActivity());
                                    marqueeView.setAdapter(marqueeAdapter);
                                    marqueeView.setVisibility(View.VISIBLE);
                                    marqueeView.startScroll();
                                }else {
                                    marqueeView.setVisibility(View.GONE);
                                }
                                if(indexInfoModel.getData().getIcons()!=null) {
                                    iconList.addAll(data.getIcons());
                                }
                                couponListModels = indexInfoModel.getData();
                                if (data.getDeductAmountStr() != null) {
                                    deductAmountStr = data.getDeductAmountStr();
                                }
                                if(indexInfoModel.getData().getGiftReceiveBtn().equals("0")) {
//                                    getPrivacy(indexInfoModel);
//                                    getDialog(indexInfoModel);
                                }else {
//                                    getTurn();
                                }

                                if(!SharedPreferencesUtil.getString(mActivity,"once").equals("0")) {
//                                    getDialog(indexInfoModel);
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
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    tv_city.setText(data.getCompanyName());
                                }else {
                                    tv_city.setText(data.getAddress());
                                }
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
//                                    ClickBanner(data.getBanners());
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
                                                    Log.d("wwwwwwww........","bbb");
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
//                                classifyLists = indexInfoModel.getData().getClassifyList();
//                                classifyList.clear();
//                                classifyList.addAll(classifyLists);
//                                rvIconAdapter = new RvIconAdapter(R.layout.item_home_icon,classifyList);
//                                if(classifyList.size()>0) {
//                                    rv_icon.setVisibility(View.VISIBLE);
//                                    if(classifyList.size()==5||classifyList.size()==9||classifyList.size()==10) {
//                                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,5);
//                                        rv_icon.setLayoutManager(gridLayoutManager1);
//                                        rv_icon.setAdapter(rvIconAdapter);
//                                        indicator.setVisibility(View.GONE);
//                                    }else if(classifyList.size()<=4||classifyList.size()<=8 &&classifyList.size()!=5){
//                                        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mActivity,4);
//                                        rv_icon.setLayoutManager(gridLayoutManager2);
//                                        rv_icon.setAdapter(rvIconAdapter);
//                                        indicator.setVisibility(View.GONE);
//                                    }else {
//                                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, RecyclerView.HORIZONTAL, false);
//                                        rv_icon.setLayoutManager(gridLayoutManager);
//                                        rv_icon.setAdapter(rvIconAdapter);
//                                        indicator.setVisibility(View.VISIBLE);
//                                    }
//                                }else {
//                                    rv_icon.setVisibility(View.GONE);
//                                    indicator.setVisibility(View.GONE);
//                                }


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
     * 订单状态
     */
    OrderMarqueeAdapter marqueeAdapter;
//    private void getOrder() {
//        IndexHomeAPI.indexOrder(mActivity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(mainThread())
//                .subscribe(new Subscriber<OrderModel>() {
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
//                    public void onNext(OrderModel indexInfoModel) {
//                        if (indexInfoModel.getCode()==1) {
//                            if(indexInfoModel.getData()!=null && indexInfoModel.getData().size() > 0) {
//                                marqueeAdapter = new OrderMarqueeAdapter();
//                                marqueeAdapter.setData(indexInfoModel.getData(),mActivity);
//                                order_marquee.setAdapter(marqueeAdapter);
//                                order_marquee.setVisibility(View.VISIBLE);
//                                order_marquee.startScroll();
//                            }else {
//                                order_marquee.setVisibility(View.GONE);
//                            }
//                        }else {
//                            order_marquee.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }

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
                                    listBeans.addAll(list);
                                    iv_empty_hot.setVisibility(View.GONE);
                                }else {
                                    iv_empty_hot.setVisibility(View.VISIBLE);
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
}
