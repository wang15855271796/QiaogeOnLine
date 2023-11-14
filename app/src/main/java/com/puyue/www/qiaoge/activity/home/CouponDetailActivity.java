package com.puyue.www.qiaoge.activity.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;


import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.utils.Time;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2019/11/11
 * 折扣列表
 */
public class CouponDetailActivity extends BaseSwipeActivity implements View.OnClickListener{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.tv_start)
    TextView tv_start;
    @BindView(R.id.iv_start)
    ImageView iv_start;
    @BindView(R.id.tv_un_start)
    TextView tv_un_start;
    @BindView(R.id.iv_un_start)
    ImageView iv_un_start;
    @BindView(R.id.iv_404)
    ImageView iv_404;
    private FragmentTransaction mFragmentTransaction;
    private static final String Coupon_HOME1 = "coupon_home1";
    private static final String Coupon_HOME2 = "coupon_home2";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coupon_list);
    }

    long start;
    @Override
    protected void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        long end = (System.currentTimeMillis()-start)/1000;
        long time = Time.getTime(end);
        getDatas(time);

    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,5,end)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(ReduceNumEvent event) {
        getCartNum();
    }
    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tv_title.setText("超值折扣");

        getCartNum();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
//                    startActivity(new Intent(mContext, CartActivity.class));
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }
            }
        });
    }

    /**
     * 购物车数量
     */
    private void getCartNum() {
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
            iv_404.setImageResource(R.mipmap.ic_404);
            iv_404.setVisibility(View.VISIBLE);
            fl_container.setVisibility(View.GONE);
        }else {
            fl_container.setVisibility(View.VISIBLE);
            iv_404.setImageResource(R.mipmap.ic_no_data);
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
                                }else {
                                    tv_num.setVisibility(View.VISIBLE);

                                    tv_num.setText(getCartNumModel.getData().getNum());
                                }
                            } else {
                                AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                            }
                        }
                    });

        }

    }

    @Override
    public void setViewData() {
        tv_start.setOnClickListener(this);
        iv_start.setOnClickListener(this);
        iv_un_start.setOnClickListener(this);
        tv_un_start.setOnClickListener(this);
        switchTab(Coupon_HOME1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                iv_start.setVisibility(View.VISIBLE);
                tv_start.setVisibility(View.GONE);

                iv_un_start.setVisibility(View.GONE);
                tv_un_start.setVisibility(View.VISIBLE);
                switchTab(Coupon_HOME1);
                break;

            case R.id.iv_start:
                iv_start.setVisibility(View.GONE);
                tv_start.setVisibility(View.VISIBLE);

                iv_un_start.setVisibility(View.VISIBLE);
                tv_un_start.setVisibility(View.GONE);
                switchTab(Coupon_HOME1);
                break;

            case R.id.tv_un_start:
                iv_un_start.setVisibility(View.VISIBLE);
                tv_un_start.setVisibility(View.GONE);

                iv_start.setVisibility(View.GONE);
                tv_start.setVisibility(View.VISIBLE);
                switchTab(Coupon_HOME2);
                break;

            case R.id.iv_un_start:
                iv_un_start.setVisibility(View.GONE);
                tv_un_start.setVisibility(View.VISIBLE);

                iv_start.setVisibility(View.VISIBLE);
                tv_start.setVisibility(View.GONE);
                switchTab(Coupon_HOME2);
                break;
        }
    }

    CouponFragment couponFragment;
    CouponFragment1 coupon1Fragment;
    private void switchTab(String tab) {
        //开始事务
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的Fragment
        if (couponFragment != null) {
            mFragmentTransaction.hide(couponFragment);
        }

        if (coupon1Fragment != null) {
            mFragmentTransaction.hide(coupon1Fragment);
        }
        //切换被选中的tab
        switch (tab) {
            case Coupon_HOME1:
                if (couponFragment == null) {
                    couponFragment = new CouponFragment();
                    mFragmentTransaction.add(R.id.fl_container, couponFragment);
                } else {
                    mFragmentTransaction.show(couponFragment);
                }

                break;

            case Coupon_HOME2:

                if (coupon1Fragment == null) {
                    coupon1Fragment = new CouponFragment1();
                    mFragmentTransaction.add(R.id.fl_container, coupon1Fragment);
                } else {
                    mFragmentTransaction.show(coupon1Fragment);
                }
                break;

        }
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void setClickEvent() {

    }
}
