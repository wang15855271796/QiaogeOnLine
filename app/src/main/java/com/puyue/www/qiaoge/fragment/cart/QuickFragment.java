package com.puyue.www.qiaoge.fragment.cart;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CouponFragment;
import com.puyue.www.qiaoge.activity.home.CouponFragment1;
import com.puyue.www.qiaoge.activity.home.ViewPagerAdapters;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuickFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    ViewPagerAdapters adapter;
    private final String[] mTitles = {"进行中", "已完成","已取消"};
    @Override
    public int setLayoutId() {
        return R.layout.quick_fragment;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {
        adapter = new ViewPagerAdapters(getChildFragmentManager());
        adapter.addFragment(HuoMyOrderFragment.getInstance("1"));
        adapter.addFragment(HuoMyOrderFragment.getInstance("2"));
        adapter.addFragment(HuoMyOrderFragment.getInstance("3"));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tab_layout.setViewPager(viewPager, mTitles);

    }

    @Override
    public void setClickEvent() {

    }
}
