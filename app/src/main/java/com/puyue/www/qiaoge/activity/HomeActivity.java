package com.puyue.www.qiaoge.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.puyue.www.qiaoge.AutoPollRecyclerView;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.api.PostLoadAmountAPI;
import com.puyue.www.qiaoge.api.SendJsPushAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.SendLocationAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.event.GoToMineEvent;
import com.puyue.www.qiaoge.event.InitEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.RentEvent;
import com.puyue.www.qiaoge.event.changeEvent;
import com.puyue.www.qiaoge.event.setFragmentEvent;
import com.puyue.www.qiaoge.event.setFragmentsEvent;
import com.puyue.www.qiaoge.fragment.cart.CartFragment;
import com.puyue.www.qiaoge.fragment.cart.CartFragments;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.fragment.home.HomeFragment;
import com.puyue.www.qiaoge.fragment.home.HomeFragment10;
import com.puyue.www.qiaoge.fragment.home.HomeFragment2;
import com.puyue.www.qiaoge.fragment.home.InfoFragment;
import com.puyue.www.qiaoge.fragment.market.MarketsFragment;
import com.puyue.www.qiaoge.fragment.mine.MineFragment;
import com.puyue.www.qiaoge.fragment.mine.MineWyFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.HomeStyleModel;
import com.puyue.www.qiaoge.model.HomeStyleTabModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.StatusBarUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity implements CartFragment.FragmentInteraction, CartFragment.GoToMarket{
    private static final String TAB_HOME = "tab_home";
    private static final String TAB_MARKET = "tab_market";
    private static final String TAB_CART = "tab_cart";
    private static final String TAB_MINE = "tab_mine";
    private static final String TAB_INFO = "tab_info";
    private static final String TAB_HOME1 = "tab_home1";
    private Fragment mTabHome;
    private Fragment mTabMarket;
    private Fragment mTabCart;
    private Fragment mTabInfo;
    private Fragment mTabMine;
    private FragmentTransaction mFragmentTransaction;
    private LinearLayout mLlHome;
    LinearLayout ll_info;
    private ImageView mIvHome;
    private TextView mTvHome;
    private LinearLayout mLlMarket;
    private ImageView mIvMarket;
    private TextView mTvMarket;
    private LinearLayout mLlCart;
    private ImageView mIvCart;
    private TextView mTvCart;
    private LinearLayout mLlMine;
    private ImageView mIvMine;
    private TextView mTvMine;
    private ImageView iv_info;
    private TextView tv_info;
    private long mExitTime = 0;
    private TextView mTvCarNum;
//    private ImageView iv_home;
    private String token;
    private String locationMessage = "";
    private boolean isSend = false;
    private String city;
    private String type;
    private String district;
    private ImageView iv_home;
    CouponDialog couponDialog;
    TencentLocationManager instance;
    @Override
    public void onAttachFragment(Fragment fragment) {
        //重新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
        if (mTabHome == null && fragment instanceof HomeFragment2)
            mTabHome = fragment;
        if (mTabMarket == null && fragment instanceof MarketsFragment)
            mTabMarket = fragment;
        if (mTabCart == null && fragment instanceof CartFragments)
            mTabCart = fragment;
        if (mTabInfo == null && fragment instanceof InfoFragment)
            mTabInfo = fragment;
        if (mTabMine == null && fragment instanceof MineWyFragment)
            mTabMine = fragment;
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

        @Override
    public void setContentView() {
            //腾讯定位
        instance = TencentLocationManager.getInstance(QiaoGeApplication.getContext());
        instance.requestSingleFreshLocation(null, mLocationListener, Looper.getMainLooper());
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(3);
        OneKeyLoginManager.getInstance().init(getApplicationContext(), "cuRwbnsv", new InitListener() {
                          @Override
            public void getInitStatus(int code, String result) {
                //初始化回调

            }
        });

        //闪验SDK预取号（可缩短拉起授权页时间）
        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
            @Override
            public void getPhoneInfoStatus(int code, String result) {
                //预取号回调
                Log.e("VVV", "预取号： code==" + code + "   result==" + result);
            }
        });

        setContentView(R.layout.activity_home);

    }

    TencentLocationListener mLocationListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(TencentLocation location, int i, String s) {
            district = location.getDistrict();
            city = location.getCity();
            String province = location.getProvince();
            UserInfoHelper.saveProvince(mContext, province);
            SharedPreferencesUtil.saveString(mActivity,"provinceName",province);
            UserInfoHelper.saveAreaName(mContext, district);

            SharedPreferencesUtil.saveString(mContext,"lat",location.getLatitude()+"");
            SharedPreferencesUtil.saveString(mContext,"lon",location.getLongitude()+"");

            if (city != null) {
                UserInfoHelper.saveCity(mContext, city);
            } else {
                UserInfoHelper.saveCity(mContext, "");
            }
            type = "";
            locationMessage = location.getAddress();    //获取详细地址信息
            if (token != null) {
                sendLocation();
            }
        }

        @Override
        public void onStatusUpdate(String s, int i, String s1) {
        }
    };

    @Override
    public void findViewById() {
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        tv_info = (TextView) findViewById(R.id.tv_info);
        ll_info = (LinearLayout) findViewById(R.id.ll_info);
        mLlHome = (LinearLayout) findViewById(R.id.layout_tab_bar_home);
        mIvHome = (ImageView) findViewById(R.id.iv_tab_bar_home_icon);
        mTvHome = (TextView) findViewById(R.id.tv_tab_bar_home_title);
        mLlMarket = (LinearLayout) findViewById(R.id.layout_tab_bar_market);
        mIvMarket = (ImageView) findViewById(R.id.iv_tab_bar_market_icon);
        mTvMarket = (TextView) findViewById(R.id.tv_tab_bar_market_title);
        mLlCart = (LinearLayout) findViewById(R.id.layout_tab_bar_cart);
        mIvCart = (ImageView) findViewById(R.id.iv_tab_bar_cart_icon);
        mTvCart = (TextView) findViewById(R.id.tv_tab_bar_cart_title);
        mLlMine = (LinearLayout) findViewById(R.id.layout_tab_bar_mine);
        mIvMine = (ImageView) findViewById(R.id.iv_tab_bar_mine_icon);
        mTvMine = (TextView) findViewById(R.id.tv_tab_bar_mine_title);
        mTvCarNum = (TextView) findViewById(R.id.tv_home_car_number);
    }


    @Override
    public void setViewData() {
        StatusBarUtil.setStatusBarLightMode(mActivity);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (getIntent() != null) {
            type = getIntent().getStringExtra("go_home");
        }
        token = AppConstant.TOKEN;
        EventBus.getDefault().post(new InitEvent());
        UserInfoHelper.saveChangeFlag(mContext,0+"");

        UserInfoHelper.saveCity(mActivity, "杭州市");
        UserInfoHelper.saveAreaName(mContext, "上城区");
        getHomeStyle();

}

    /**
     * 获取首页风格
     */
    HomeStyleModel.DataBean data;
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
                                data = homeStyleModel.getData();

                                getHomeTabStyle();

//                                normal常规，black_white黑白，may_day五一，national_day国庆，spring_festival春节

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeStyleModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取首页Tab风格
     */
    String picUrl1;
    String choosePicUrl1;
    String picUrl2;
    String choosePicUrl2;
    String picUrl3;
    String choosePicUrl3;
    String picUrl4;
    String choosePicUrl4;
    String picUrl5;
    String choosePicUrl5;
    String rentName;
    HomeStyleTabModel.DataBean homeTabData;
    private void getHomeTabStyle() {
        IndexHomeAPI.getTabStyle(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStyleTabModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeStyleTabModel homeStyleTabModel) {
                        if(homeStyleTabModel.getCode()==1) {
                            if(homeStyleTabModel.getData()!=null) {
                                homeTabData = homeStyleTabModel.getData();
                                if(homeTabData.getChangeFlag()==1) {
                                    //是
                                    picUrl1 = homeTabData.getTab1().getPicUrl();
                                    choosePicUrl1 = homeTabData.getTab1().getChoosePicUrl();
                                    picUrl2 = homeTabData.getTab2().getPicUrl();
                                    choosePicUrl2 = homeTabData.getTab2().getChoosePicUrl();
                                    picUrl3 = homeTabData.getTab3().getPicUrl();
                                    choosePicUrl3 = homeTabData.getTab3().getChoosePicUrl();
                                    picUrl4 = homeTabData.getTab4().getPicUrl();
                                    choosePicUrl4 = homeTabData.getTab4().getChoosePicUrl();
                                    picUrl5 = homeTabData.getTab5().getPicUrl();
                                    choosePicUrl5 = homeTabData.getTab5().getChoosePicUrl();
                                }

//                                if(homeTabData.getChangeFlag()==1) {
//                                    Glide.with(mContext).load(choosePicUrl1).into(mIvHome);
//                                    mTvHome.setVisibility(View.VISIBLE);
//                                }else {
//                                    mIvHome.setImageResource(R.mipmap.ic_tab_home_enable);
//                                    mTvHome.setVisibility(View.GONE);
//                                }

                                if(!homeTabData.getRentName().equals("") && homeTabData.getRentName()!=null) {
                                    rentName = homeTabData.getRentName();
                                    tv_info.setText(rentName);
                                }else {
                                    tv_info.setText("行业信息");
                                }


                                switchTab(TAB_HOME);
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeStyleTabModel.getMessage());
                        }
                    }
                });
    }

    private void sendLocation() {
        SendLocationAPI.requestData(mContext, locationMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetAddressModel getAddressModel) {
                        if (getAddressModel.isSuccess()) {

                        }
                    }
                });

    }

    @Override
    public void setClickEvent() {
        ll_info.setOnClickListener(noDoubleClickListener);
        mLlHome.setOnClickListener(noDoubleClickListener);
        mLlMarket.setOnClickListener(noDoubleClickListener);
        mLlCart.setOnClickListener(noDoubleClickListener);
        mLlMine.setOnClickListener(noDoubleClickListener);
    }

    int flag = 0;
    changeEvent changeEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change1(changeEvent changeEvent) {
        this.changeEvent = changeEvent;
        if(changeEvent.isB()) {
            iv_home.setImageResource(R.mipmap.icon_go_top);
            iv_home.setVisibility(View.VISIBLE);
            mIvHome.setVisibility(View.GONE);
            mTvHome.setVisibility(View.GONE);
            SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",true);
        }else {
//                iv_home.setImageResource(R.mipmap.ic_tab_home_enable);
            iv_home.setVisibility(View.GONE);
            mIvHome.setVisibility(View.VISIBLE);
            mTvHome.setVisibility(View.VISIBLE);
            SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",false);
        }
        if(homeTabData.getChangeFlag() == 0) {
//            if(changeEvent.isB()) {
//                iv_home.setImageResource(R.mipmap.icon_go_top);
//                iv_home.setVisibility(View.VISIBLE);
//                mIvHome.setVisibility(View.GONE);
//                SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",true);
//            }else {
////                iv_home.setImageResource(R.mipmap.ic_tab_home_enable);
//                iv_home.setVisibility(View.GONE);
//                mIvHome.setVisibility(View.VISIBLE);
//                mTvHome.setVisibility(View.VISIBLE);
//                SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",false);
//            }
//            mIvHome.setVisibility(View.GONE);
//            mTvHome.setVisibility(View.GONE);
        }else {
            if(changeEvent.isB()) {
                SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",true);
            }else {
                SharedPreferencesUtil.saveBoolean(mActivity,"isScroll",false);
            }
//            Glide.with(mContext).load(choosePicUrl1).into(mIvHome);
//            mTvHome.setVisibility(View.VISIBLE);
//            mTvHome.setTextColor(getResources().getColor(R.color.app_tab_selected));
        }

    }

    //在这个方法里面拿到回传回来的数据修改UI即可
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == mLlHome) {

                switchTab(TAB_HOME);
            } else if (view == mLlMarket&&!mLlMarket.isSelected()) {

                switchTab(TAB_MARKET);

            } else if (view == mLlCart&&!mLlCart.isSelected())  {

                //从首页判断用户没有登录跳转到登录界面,登录成功回来的时候要重新请求数据,
                //由于是从首页和商城页点击进入的登录界面,回到原来界面的时候需要首页刷新或者商城界面刷新分类和细节数据
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_CART);
                } else {
                    initDialog();
                }
            } else if (view == mLlMine&&!mLlMine.isSelected()) {

                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_MINE);
                } else {
                    initDialog();
                }
            }else if(view == ll_info&&!ll_info.isSelected()) {

                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_INFO);
                } else {
                    initDialog();
                }
            }
        }
    };

    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
                LoginUtil.initRegister(getContext());
                dismiss();
            }
        };
        couponDialog.show();
    }

    private void switchTab(String tab) {
        //开始事务
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的Fragment
        if (mTabHome != null) {
            mFragmentTransaction.hide(mTabHome);
        }
        if (mTabMarket != null) {
            mFragmentTransaction.hide(mTabMarket);
        }
        if (mTabCart != null) {
            mFragmentTransaction.hide(mTabCart);
        }
        if (mTabInfo != null) {
            mFragmentTransaction.hide(mTabInfo);
        }
        if (mTabMine != null) {
            mFragmentTransaction.hide(mTabMine);
        }

        //重置所有的tabStyle
        if(homeTabData!=null) {
            if(homeTabData.getChangeFlag() == 0) {
                mIvHome.setImageResource(R.mipmap.ic_tab_home_unable);
                mIvMarket.setImageResource(R.mipmap.ic_tab_goods_unable);
                mIvCart.setImageResource(R.mipmap.ic_tab_cart_unable);
                mIvMine.setImageResource(R.mipmap.ic_tab_mine_unable);
                iv_info.setImageResource(R.mipmap.ic_tab_info_un);
            }else {
                Glide.with(mContext).load(picUrl1).into(mIvHome);
                Glide.with(mContext).load(picUrl2).into(mIvMarket);
                Glide.with(mContext).load(picUrl3).into(mIvCart);
                Glide.with(mContext).load(picUrl4).into(iv_info);
                Glide.with(mContext).load(picUrl5).into(mIvMine);
            }
        }


        mTvMarket.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        mTvCart.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        mTvMine.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        tv_info.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        mTvHome.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));

        //切换被选中的tab
        switch (tab) {
            case TAB_HOME:
                boolean isScroll = SharedPreferencesUtil.getBoolean(mActivity, "isScroll");
                SharedPreferencesUtil.saveString(mActivity,"index1","1");
                SharedPreferencesUtil.saveString(mActivity,"index","1");
                SharedPreferencesUtil.saveString(mActivity,"index2","2");

                if(homeTabData.getChangeFlag() == 0) {
                    mTvHome.setVisibility(View.VISIBLE);
                    mIvHome.setVisibility(View.VISIBLE);
                    mIvHome.setImageResource(R.mipmap.ic_home_selected);
                    iv_home.setVisibility(View.GONE);
//                    iv_home.setImageResource(R.mipmap.ic_home_selected);
                }else {
                    Glide.with(mContext).load(choosePicUrl1).into(mIvHome);
                    mTvHome.setVisibility(View.VISIBLE);
                    mIvHome.setVisibility(View.VISIBLE);
                    iv_home.setVisibility(View.GONE);
                    mTvHome.setTextColor(getResources().getColor(R.color.app_tab_selected));

                }

                if(isScroll) {
                    EventBus.getDefault().postSticky(new TopEvent(true));
                }



                switch (data.getTemplateStyle()) {
                    case "normal":
                        if (mTabHome == null) {
                            mTabHome = new HomeFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabHome);
                        } else {
                            mFragmentTransaction.show(mTabHome);
                        }
                        break;

                    case "black_white":

                        break;

                    case "may_day":
                        if (mTabHome == null) {
                            mTabHome = new HomeFragment2();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabHome);
                        } else {
                            mFragmentTransaction.show(mTabHome);
                        }

                        break;

                    case "national_day":

                        break;

                    case "spring_festival":

                        break;
                    default:


                }
                break;

            case TAB_MARKET:
                SharedPreferencesUtil.saveString(mActivity,"index2","1");
                SharedPreferencesUtil.saveString(mActivity,"index","2");
                SharedPreferencesUtil.saveString(mActivity,"index1","1");
                EventBus.getDefault().postSticky(new TopEvent(true));
                if (mTabMarket == null) {
                    mTabMarket = new MarketsFragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabMarket);
                    EventBus.getDefault().postSticky(new FromIndexEvent(""));
                } else {
                    mFragmentTransaction.show(mTabMarket);
                }

                mTvMarket.setTextColor(getResources().getColor(R.color.app_tab_selected));
                getCartPoductNum();

                if(homeTabData.getChangeFlag() == 0) {
                    mTvHome.setVisibility(View.VISIBLE);
                    mIvHome.setImageResource(R.mipmap.ic_tab_home_unable);
                    mIvMarket.setImageResource(R.mipmap.ic_tab_goods_enable);
                }else {
                    mTvHome.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(picUrl1).into(mIvHome);
                    Glide.with(mContext).load(choosePicUrl2).into(mIvMarket);
                }
                mIvHome.setVisibility(View.VISIBLE);
                iv_home.setVisibility(View.GONE);
                switch (data.getTemplateStyle()) {
                    case "normal":
//                        mIvMarket.setImageResource(R.mipmap.ic_tab_goods_enable);
                        break;

                    case "black_white":

                        break;

                    case "may_day":
//                        Glide.with(mContext).load(choosePicUrl2).into(mIvMarket);
                        break;

                    case "national_day":

                        break;

                    case "spring_festival":

                        break;
                    default:


                }

                break;
            case TAB_CART:
                SharedPreferencesUtil.saveString(mActivity,"index2","1");
                SharedPreferencesUtil.saveString(mActivity,"index","3");
                SharedPreferencesUtil.saveString(mActivity,"index1","1");
                if (mTabCart == null) {
                    mTabCart = new CartFragments();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabCart);
                } else {
                    mFragmentTransaction.show(mTabCart);
                }
                EventBus.getDefault().postSticky(new TopEvent(true));
                if(homeTabData.getChangeFlag() == 0) {
                    mTvHome.setVisibility(View.VISIBLE);
                    mIvHome.setImageResource(R.mipmap.ic_tab_home_unable);
                    mIvCart.setImageResource(R.mipmap.ic_tab_cart_enable);
                }else {
                    mTvHome.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(picUrl1).into(mIvHome);
                    Glide.with(mContext).load(choosePicUrl3).into(mIvCart);
                }
                mIvHome.setVisibility(View.VISIBLE);
                iv_home.setVisibility(View.GONE);
                switch (data.getTemplateStyle()) {
                    case "normal":
                        if (mTabCart == null) {
                            mTabCart = new MineFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabCart);
                        } else {
                            mFragmentTransaction.show(mTabCart);
                        }
//                        mIvCart.setImageResource(R.mipmap.ic_tab_cart_enable);
                        break;

                    case "black_white":

                        break;

                    case "may_day":

                        if (mTabCart == null) {
                            mTabCart = new MineWyFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabCart);
                        } else {
                            mFragmentTransaction.show(mTabCart);
                        }
//                        Glide.with(mContext).load(choosePicUrl3).into(mIvCart);
                        break;

                    case "national_day":

                        break;

                    case "spring_festival":

                        break;
                    default:


                }

                mTvCart.setTextColor(getResources().getColor(R.color.app_tab_selected));
                getCartPoductNum();
                break;
            case TAB_MINE:
                EventBus.getDefault().postSticky(new TopEvent(true));
                SharedPreferencesUtil.saveString(mActivity,"index2","1");
                SharedPreferencesUtil.saveString(mActivity,"index","5");
                SharedPreferencesUtil.saveString(mActivity,"index1","1");
                if(homeTabData.getChangeFlag() == 0) {
                    mTvHome.setVisibility(View.VISIBLE);
                    mIvHome.setImageResource(R.mipmap.ic_tab_home_unable);
                    mIvMine.setImageResource(R.mipmap.ic_tab_mine_enable);
                }else {
                    Glide.with(mContext).load(choosePicUrl5).into(mIvMine);
                    Glide.with(mContext).load(picUrl1).into(mIvHome);
                    mTvHome.setVisibility(View.VISIBLE);
                }
                mIvHome.setVisibility(View.VISIBLE);
                iv_home.setVisibility(View.GONE);
                switch (data.getTemplateStyle()) {
                    case "normal":
                        if (mTabMine == null) {
                            mTabMine = new MineFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabMine);
                        } else {
                            mFragmentTransaction.show(mTabMine);
                        }
//                        mIvMine.setImageResource(R.mipmap.ic_tab_mine_enable);
                        break;

                    case "black_white":

                        break;

                    case "may_day":
                        if (mTabMine == null) {
                            mTabMine = new MineWyFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabMine);
                        } else {
                            mFragmentTransaction.show(mTabMine);
                        }
//                        Glide.with(mContext).load(choosePicUrl5).into(mIvMine);
                        break;

                    case "national_day":

                        break;

                    case "spring_festival":

                        break;
                    default:


                }

                mTvMine.setTextColor(getResources().getColor(R.color.app_tab_selected));
                getCartPoductNum();
                break;

            case TAB_INFO:
                SharedPreferencesUtil.saveString(mActivity,"index2","1");
                SharedPreferencesUtil.saveString(mActivity,"index","4");
                SharedPreferencesUtil.saveString(mActivity,"index1","1");
                EventBus.getDefault().postSticky(new TopEvent(true));

                if (mTabInfo == null) {
                    mTabInfo = new InfoFragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabInfo);
                } else {
                    mFragmentTransaction.show(mTabInfo);
                }
                if(homeTabData.getChangeFlag() == 0) {
                    mIvHome.setImageResource(R.mipmap.ic_tab_home_unable);
                    iv_info.setImageResource(R.mipmap.ic_tab_info_en);
                    mTvHome.setVisibility(View.VISIBLE);
                }else {
                    Glide.with(mContext).load(choosePicUrl4).into(iv_info);
                    mTvHome.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(picUrl1).into(mIvHome);
                }
                mIvHome.setVisibility(View.VISIBLE);
                iv_home.setVisibility(View.GONE);
                switch (data.getTemplateStyle()) {
                    case "normal":
                        if (mTabInfo == null) {
                            mTabInfo = new MineFragment();
                            mFragmentTransaction.add(R.id.layout_home_container, mTabInfo);
                        } else {
                            mFragmentTransaction.show(mTabInfo);
                        }
//                        iv_info.setImageResource(R.mipmap.ic_tab_info_en);
//                        tv_info.setText("行业信息");
                        break;

                    case "black_white":

                        break;

                    case "may_day":

//                        if(!rentName.equals("") &&rentName!=null) {
//                            tv_info.setText(rentName);
//                        }
//                        Glide.with(mContext).load(choosePicUrl4).into(iv_info);
                        break;

                    case "national_day":

                        break;

                    case "spring_festival":

                        break;
                    default:


                }

                tv_info.setTextColor(getResources().getColor(R.color.app_tab_selected));
                getCartPoductNum();
                break;
        }
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(LogoutEvent logoutEvent) {
        SharedPreferencesUtil.saveString(mActivity,"index1","7");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setEmptys(setFragmentEvent setFragmentEvent) {
        mTabMine = null;
        mTabMarket = null;
        mTabInfo = null;
        mTabCart = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setEmptyMine(setFragmentsEvent setFragmentsEvent) {
        mTabHome = null;
        mTabMarket = null;
        mTabInfo = null;
        mTabCart = null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(GoToMineEvent goToMineEvent) {
        switchTab(TAB_MINE);
        SharedPreferencesUtil.saveString(mActivity,"index1","6");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(GoToMarketEvent goToMarketEvent) {
//        if(homeTabData.getChangeFlag() == 0) {
//            mTvHome.setVisibility(View.GONE);
//        }else {
//            mTvHome.setVisibility(View.VISIBLE);
//        }

//        mIvHome.setVisibility(View.VISIBLE);
//        iv_home.setVisibility(View.GONE);
        switchTab(TAB_MARKET);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goToCartFragment(GoToCartFragmentEvent goToCartFragmentEvent) {
        switchTab(TAB_CART);
//        if(homeTabData.getChangeFlag() == 0) {
//            mTvHome.setVisibility(View.GONE);
//        }else {
//            mTvHome.setVisibility(View.VISIBLE);
//        }
//        mIvHome.setVisibility(View.VISIBLE);
//        iv_home.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goToHomeFragment(HomeFragmentEvent homeFragmentEvent) {
        mTabHome = null;
        switchTab(TAB_HOME);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                AppHelper.showMsg(this, "再按一次退出程序！");
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getCartPoductNum() {
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            getCartNum();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartNum();
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            sendRegistionId();
            if (!isSend) {
                if (UserInfoHelper.getLoadAmount(mActivity) != null && StringHelper.notEmptyAndNull(UserInfoHelper.getLoadAmount(mContext))) {
                    sendLoadNum();
                }
            }

        } else {
            mTvCarNum.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBuss(ReduceNumEvent event) {
        //刷新UI
        getCartPoductNum();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogoutsEvent mainEvent) {
//        mTvHome.setVisibility(View.GONE);
//        switchTab(TAB_HOME);
        getHomeStyle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RentEvent rentEvent) {
        getHomeTabStyle1();
    }

    private void getHomeTabStyle1() {
        IndexHomeAPI.getTabStyle(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStyleTabModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeStyleTabModel homeStyleTabModel) {
                        if(homeStyleTabModel.getCode()==1) {
                            if(homeStyleTabModel.getData()!=null) {
                                homeTabData = homeStyleTabModel.getData();
                                if(!homeTabData.getRentName().equals("") && homeTabData.getRentName()!=null) {
                                    rentName = homeTabData.getRentName();
                                    tv_info.setText(rentName);
                                }else {
                                    tv_info.setText("行业信息");
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeStyleTabModel.getMessage());
                        }
                    }
                });
    }


    private void sendLoadNum() {
        PostLoadAmountAPI.requestData(mContext)
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
                            isSend = true;
                        }
                    }
                });
    }

    private void sendRegistionId() {
        SendJsPushAPI.requestData(mContext, UserInfoHelper.getRegistionid(mContext))
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

                        }
                    }
                });
    }


    /**
     * 获取购物车角标数据
     */
    private void getCartNum() {
        PublicRequestHelper.getCartNum(mContext, new OnHttpCallBack<GetCartNumModel>() {
            @Override
            public void onSuccessful(GetCartNumModel getCartNumModel) {
                if (getCartNumModel.isSuccess()) {
                    if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                        mTvCarNum.setVisibility(View.VISIBLE);
                        mTvCarNum.setText(getCartNumModel.getData().getNum());
                    } else {
                        mTvCarNum.setVisibility(View.GONE);
                    }
                } else {
                    AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String marketGood = intent.getStringExtra("collect");

            if (marketGood != null) {
                switchTab(TAB_MARKET);
            }
        }
    }

    @Override
    public void refreshCarNum() {
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            //新改
            getCartNum();
        } else {
            mTvCarNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void jumpMarket() {
        switchTab(TAB_MARKET);
    }
}