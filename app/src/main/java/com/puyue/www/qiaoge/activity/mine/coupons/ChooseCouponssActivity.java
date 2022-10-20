package com.puyue.www.qiaoge.activity.mine.coupons;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ChooseSelfCouponsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ViewPagerAdapter;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.fragment.ChooseSelfCouponFragment;
import com.puyue.www.qiaoge.fragment.CouponsSelfUnUseFragment;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2020/9/9
 */
public class ChooseCouponssActivity extends BaseSwipeActivity {

    private Toolbar toolbar;
    private String activityBalanceVOStr;
    private String normalProductBalanceVOStr;
    private String giftDetailNo="";
    boolean statModel;
    private List<UserChooseDeductModel.DataBean> list = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    private List<String> stringList =new ArrayList<>();
    private List<Fragment> list_fragment=new ArrayList<>();
    int disType;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.acticity_choose_coupons);
    }

    @Override
    public void findViewById() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        stringList.add("可使用");
        stringList.add("不可使用");


    }

    @Override
    public void setViewData() {
        activityBalanceVOStr = getIntent().getStringExtra("activityBalanceVOStr");
        normalProductBalanceVOStr = getIntent().getStringExtra("normalProductBalanceVOStr");
        giftDetailNo = getIntent().getStringExtra("giftDetailNo");
        statModel = getIntent().getBooleanExtra("statModel",false);
        disType = getIntent().getIntExtra("deliveryModel",0);
        //可使用
        list_fragment.add(ChooseSelfCouponFragment.newInstance(giftDetailNo,normalProductBalanceVOStr,activityBalanceVOStr,statModel,disType+""));
        //不可使用
        list_fragment.add(CouponsSelfUnUseFragment.newInstance(giftDetailNo,normalProductBalanceVOStr,activityBalanceVOStr,disType+""));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),list_fragment,stringList);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void setClickEvent() {
        toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
    }
}
