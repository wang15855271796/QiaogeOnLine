package com.puyue.www.qiaoge.activity.mine.order;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.ChangeDeliver1Event;
import com.puyue.www.qiaoge.event.ChangeDeliverEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.fragment.order.ConfirmOrderDeliverFragment;
import com.puyue.www.qiaoge.fragment.order.ConfirmOrderSufficiencyFragment;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ${王文博} on 2019/7/18
 * 最新版确认订单，增加门店自提
 */

public class ConfirmNewOrderActivity extends BaseSwipeActivity implements ConfirmOrderSufficiencyFragment.GoToConfirmDeliver{

    private static final String TAB_DELIVER = "tab_deliver";
    private static final String TAB_SUFFICIENCY = "tab_sufficiency";
    private TextView tv_deliver_order;
    private TextView tv_sufficiency_order;
    private Fragment mFragmentDeliver;//送货上门
    private Fragment mFragmentSufficiency;//自提货物
    private FragmentTransaction mFragmentTransaction;
    private ImageView iv_back;
    LinearLayout ll_bg;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.confirm_new_order_acitivity);
    }

    @Override
    public void findViewById() {
        ll_bg = (LinearLayout) findViewById(R.id.ll_bg);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_deliver_order = findViewById(R.id.tv_deliver_order);
        tv_sufficiency_order = findViewById(R.id.tv_sufficiency_order);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        switchTab(TAB_DELIVER);
        setTranslucentStatus();
        ll_bg.setBackgroundResource(R.mipmap.bg_left);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });;

        tv_deliver_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(TAB_DELIVER);
                ll_bg.setBackgroundResource(R.mipmap.bg_left);
                tv_deliver_order.setTextColor(Color.parseColor("#FB4800"));
                tv_deliver_order.setTextSize(16);
                tv_sufficiency_order.setTextColor(Color.parseColor("#000000"));
                tv_sufficiency_order.setTextSize(14);
                EventBus.getDefault().postSticky(new ChangeDeliverEvent());
            }
        });

        tv_sufficiency_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_bg.setBackgroundResource(R.mipmap.bg_right);
                switchTab(TAB_SUFFICIENCY);
                tv_sufficiency_order.setTextColor(Color.parseColor("#FB4800"));
                tv_sufficiency_order.setTextSize(16);
                tv_deliver_order.setTextColor(Color.parseColor("#000000"));
                tv_deliver_order.setTextSize(14);
            }
        });
    }

    private void switchTab(String tab) {
        //开始事务
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mFragmentDeliver != null) {
            mFragmentTransaction.hide(mFragmentDeliver);
        }

        if (mFragmentSufficiency != null) {
            mFragmentTransaction.hide(mFragmentSufficiency);
        }

        switch (tab) {
            case TAB_DELIVER:
                if (mFragmentDeliver == null) {
                    mFragmentDeliver = new ConfirmOrderDeliverFragment();
                    mFragmentTransaction.add(R.id.fr_confirm_oder, mFragmentDeliver);

                } else {
                    mFragmentTransaction.show(mFragmentDeliver);
                }

                break;

            case TAB_SUFFICIENCY:
                if (mFragmentSufficiency == null) {
                    mFragmentSufficiency = new ConfirmOrderSufficiencyFragment();
                    mFragmentTransaction.add(R.id.fr_confirm_oder, mFragmentSufficiency);
                } else {
                    mFragmentTransaction.show(mFragmentSufficiency);
                }
                break;
        }
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
    }


    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(RefreshEvent refreshEvent) {
        switchTab(TAB_DELIVER);
    }

    @Override
    public void jumpConfirmDeliver() {

    }
}