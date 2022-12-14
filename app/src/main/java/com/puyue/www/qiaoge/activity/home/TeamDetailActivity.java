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
import com.puyue.www.qiaoge.activity.TopEvent;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.TeamActiveQueryAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.FromIndexEvent;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.fragment.cart.CartFragments;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.fragment.home.HomeFragment;
import com.puyue.www.qiaoge.fragment.home.InfoFragment;
import com.puyue.www.qiaoge.fragment.market.MarketsFragment;
import com.puyue.www.qiaoge.fragment.mine.MineFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.TabModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
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
 *团购列表
 */
public class TeamDetailActivity extends BaseSwipeActivity implements View.OnClickListener {
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
    private FragmentTransaction mFragmentTransaction;
    TeamFragment teamFragment;
    TeamFragment1 team1Fragment;
    private static final String TEAM_HOME1 = "team_home1";
    private static final String TEAM_HOME2 = "team_home2";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_team_list);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(ReduceNumEvent event) {
        getCartNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    private void switchTab(String tab) {
        //开始事务
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的Fragment
        if (teamFragment != null) {
            mFragmentTransaction.hide(teamFragment);
        }

        if (team1Fragment != null) {
            mFragmentTransaction.hide(team1Fragment);
        }
        //切换被选中的tab
        switch (tab) {
            case TEAM_HOME1:
                if (teamFragment == null) {
                    teamFragment = new TeamFragment();
                    mFragmentTransaction.add(R.id.fl_container, teamFragment);
                } else {
                    mFragmentTransaction.show(teamFragment);
                }

                break;

            case TEAM_HOME2:

                if (team1Fragment == null) {
                    team1Fragment = new TeamFragment1();
                    mFragmentTransaction.add(R.id.fl_container, team1Fragment);
                } else {
                    mFragmentTransaction.show(team1Fragment);
                }
                break;

        }
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
    }


    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,7,end)
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
    public void findViewById() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tv_title.setText("超值组合");
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

    @Override
    public void setViewData() {
        tv_start.setOnClickListener(this);
        iv_start.setOnClickListener(this);
        iv_un_start.setOnClickListener(this);
        tv_un_start.setOnClickListener(this);
        switchTab(TEAM_HOME1);
    }

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                iv_start.setVisibility(View.VISIBLE);
                tv_start.setVisibility(View.GONE);

                iv_un_start.setVisibility(View.GONE);
                tv_un_start.setVisibility(View.VISIBLE);
                switchTab(TEAM_HOME1);
                break;

            case R.id.iv_start:
                iv_start.setVisibility(View.GONE);
                tv_start.setVisibility(View.VISIBLE);

                iv_un_start.setVisibility(View.VISIBLE);
                tv_un_start.setVisibility(View.GONE);
                switchTab(TEAM_HOME1);
                break;

            case R.id.tv_un_start:
                iv_un_start.setVisibility(View.VISIBLE);
                tv_un_start.setVisibility(View.GONE);

                iv_start.setVisibility(View.GONE);
                tv_start.setVisibility(View.VISIBLE);
                switchTab(TEAM_HOME2);
                break;

            case R.id.iv_un_start:
                iv_un_start.setVisibility(View.GONE);
                tv_un_start.setVisibility(View.VISIBLE);

                iv_start.setVisibility(View.VISIBLE);
                tv_start.setVisibility(View.GONE);
                switchTab(TEAM_HOME2);
                break;
        }
    }
}
