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
        setContentView(R.layout.test6);
    }

    @Override
    public void findViewById() {
        bind = ButterKnife.bind(this);
        myScrollview = findViewById(R.id.myScrollview);
//        myScrollview.setListener(this);
        titleView = findViewById(R.id.titleView);
        navigation = findViewById(R.id.navigation);
        lineAddNavigationSuspension = findViewById(R.id.lineAddNavigationSuspension);
        lineAddNavigationFixation = findViewById(R.id.lineAddNavigationFixation);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }

//    @Override
//    public void onScrollChanged(int l, int t, int oldl, int oldt) {
//        int[] location = new int[2];
//        lineAddNavigationFixation.getLocationOnScreen(location);
//        //lineAddNavigationFixation是包裹导航栏的LinearLayout
//        int y = location[1];//导航栏距屏幕顶部的距离，会随着scrollview的滑动而改变
//        if (y <= titleView.getHeight() + getStatusHeight()) {//导航栏距屏幕顶部的距离小于等于标题栏的高度时 悬浮，即上滑到紧贴标题栏时 悬浮
//            addNavigationSuspension();
//        } else {//反之下滑到距离大于标题栏的高度时放回原来的位置
//            addNavigationFixation();
//        }
//
//        Log.d("swdasdad.....",t+"aa");
//        titleView.setAlpha(1);
//
//    }

    private void addNavigationSuspension() {
        if (lineAddNavigationSuspension.getChildCount() == 0) {

            if (navigation.getParent() != null)
                ((ViewGroup) navigation.getParent()).removeView(navigation);

            lineAddNavigationSuspension.addView(navigation);
        }
    }

    private void addNavigationFixation() {
        if (lineAddNavigationFixation.getChildCount() == 0) {
            if (navigation.getParent() != null)
                ((ViewGroup) navigation.getParent()).removeView(navigation);

            lineAddNavigationFixation.addView(navigation);
        }
    }
    /**
     * 状态栏高度
     * @return
     */
    private int getStatusHeight() {
        if (statusBarHeight == 0) {
            Rect rect = new Rect();
            this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return statusBarHeight = rect.top; //状态栏高度
        } else return statusBarHeight;
    }


}
