package com.puyue.www.qiaoge.activity.cart;

import android.os.Bundle;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.base.BaseActivity;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test11);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);

//        banner.addBannerLifecycleObserver(this);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
