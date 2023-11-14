package com.puyue.www.qiaoge.activity.cart;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.animation.IntEvaluator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.xrecyclerview.DensityUtil;
import com.frankfancode.marqueeview.MarqueeView;
import com.google.android.material.appbar.AppBarLayout;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.BannerActivity;
import com.puyue.www.qiaoge.activity.ChooseCompanyActivity;
import com.puyue.www.qiaoge.activity.CommonH6Activity;
import com.puyue.www.qiaoge.activity.HelpPayDeliveryDetailActivity;
import com.puyue.www.qiaoge.activity.HelpPaySelfDetailActivity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.activity.home.ChangeCityActivity;
import com.puyue.www.qiaoge.activity.home.ChooseAddressActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.activity.home.HomeGoodsListActivity;
import com.puyue.www.qiaoge.activity.home.SearchReasultActivity;
import com.puyue.www.qiaoge.activity.home.SearchStartActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.activity.home.TeamDetailActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.MyCouponsActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
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
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveNewAdapter;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveQueryAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.ProductListAPI;
import com.puyue.www.qiaoge.api.home.SecKillMoreListAPI;
import com.puyue.www.qiaoge.api.home.SpikeNewActiveQueryAPI;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.api.mine.UpdateAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderNumAPI;
import com.puyue.www.qiaoge.banner.Banner;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.banner.listener.OnBannerListener;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.calendar.utils.SelectBean;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseHomeDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
import com.puyue.www.qiaoge.dialog.HelpPay1Dialog;
import com.puyue.www.qiaoge.dialog.HomeActivityDialog;
import com.puyue.www.qiaoge.dialog.HuoOrderDialog;
import com.puyue.www.qiaoge.dialog.Privacy4Dialog;
import com.puyue.www.qiaoge.dialog.TurnTableDialog;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.fragment.home.CommonFragment;
import com.puyue.www.qiaoge.fragment.home.HomeFragment;
import com.puyue.www.qiaoge.fragment.home.MustFragment;
import com.puyue.www.qiaoge.fragment.home.NewFragment;
import com.puyue.www.qiaoge.fragment.home.ReduceFragment;
import com.puyue.www.qiaoge.fragment.home.RvIconAdapter;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.ChangeCityModel;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.HomeCouponModel;
import com.puyue.www.qiaoge.model.HomeStyleModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.SeckillListModel;
import com.puyue.www.qiaoge.model.home.SpikeNewQueryModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;
import com.puyue.www.qiaoge.model.mine.order.MyOrderNumModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.CustomAppbarLayout;
import com.puyue.www.qiaoge.view.HIndicators;
import com.puyue.www.qiaoge.view.MyScrollView2;
import com.puyue.www.qiaoge.view.ScrollSpeedLinearLayoutManger;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViewss;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity {

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




    CountDownTimer animTimer;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test5);
    }

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
//                        int totalScrollRange = appBarLayout.getTotalScrollRange()-DensityUtil.dip2px(88,mContext)
                        if (abs>=totalScrollRange) {
                            ll_parent_top.setVisibility(View.VISIBLE);
//                            snap10.setTime(false, currentTime1, startTime1,endTime1);
//                            snap10.start();

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

                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                    //企业
//                                    iv_location.setImageResource(R.mipmap.icon_company_white);
                                }else {
                                    //城市
//                                    iv_location.setImageResource(R.mipmap.icon_address2);
                                }

//                                homeMessage.setImageResource(R.mipmap.ic_mine_email);

                                rl_search.requestLayout();
                            }
                        } else {
                            rl_bar.setAlpha(1);
                            if(layoutParams!=null){
                                layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,mActivity),85, DensityUtil.dip2px(ENDMARGINLEFT,mActivity), 0);
                                rl_search.requestLayout();
                                if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
//                                    iv_location.setImageResource(R.mipmap.icon_company_black);
                                }else {
//                                    iv_location.setImageResource(R.mipmap.icon_address1);
                                }


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

                EventBus.getDefault().post(new BackEvent());
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
    }

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
}
