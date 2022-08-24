package com.puyue.www.qiaoge.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.base.BaseActivity;

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
public class TestActivity extends BaseActivity {
    Unbinder bind;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    Log.d("cswdadwdsd.........","123");
                }
            });
        }
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
