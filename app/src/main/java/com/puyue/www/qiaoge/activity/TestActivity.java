package com.puyue.www.qiaoge.activity;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.view.MyScrollView1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2019/9/29
 */
public class TestActivity extends BaseActivity  {
    Unbinder bind;
    MyScrollView1 myScrollview;
    LinearLayout lineAddNavigationSuspension;
    LinearLayout lineAddNavigationFixation;
    View titleView;
    View navigation;
    int statusBarHeight;

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
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
