package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;

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

/**
 * Created by ${王涛} on 2019/9/29
 */
public class TestActivity extends BaseActivity {

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

    }

    @Override
    public void setViewData() {
        String tip = "您的账号存在一定风险，需要企业二次审核后方可使用，可联系宁波翘歌网络科技有限公司（公司名称）进行处理。";
        String companyName =  "宁波翘歌网络科技有限公司（公司名称）";
        int index = tip.lastIndexOf(companyName);
        String begin = tip.substring(0,index);
        String end = tip.substring(index+companyName.length());
        Log.d("wdasdasd........",begin+companyName+end);
    }

    @Override
    public void setClickEvent() {

    }
}
