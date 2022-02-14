package com.puyue.www.qiaoge.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ChooseRequireAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.event.HuoCityEvent;
import com.puyue.www.qiaoge.fragment.cart.HuoOrderFragment;
import com.puyue.www.qiaoge.fragment.cart.QuickFragment;
import com.puyue.www.qiaoge.fragment.home.NewFragment;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.AppointModel;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.model.HuoCityIdModel;
import com.puyue.www.qiaoge.model.HuoCityModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoHomeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_zhuang)
    TextView tv_zhuang;
    @BindView(R.id.tv_unload)
    TextView tv_unload;
    @BindView(R.id.rb_quick)
    RadioButton rb_quick;
    @BindView(R.id.rb_order)
    RadioButton rb_order;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.rl_info)
    RelativeLayout rl_info;
    @BindView(R.id.iv_huo)
    ImageView iv_huo;
    @BindView(R.id.iv1)
    ImageView iv1;
    int position = 0;
    String orderId;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.huo_home_activity);
    }

    @Override
    public void findViewById() {
    }


    @Override
    public void setViewData() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getCityId(orderId);
        SharedPreferencesUtil.saveString(mContext,"etName","");
        SharedPreferencesUtil.saveString(mContext,"etDesc","");
        SharedPreferencesUtil.saveString(mContext,"etPhone","");
        SharedPreferencesUtil.saveString(mContext,"address","");

        SharedPreferencesUtil.saveString(mContext,"etName1","");
        SharedPreferencesUtil.saveString(mContext,"etDesc1","");
        SharedPreferencesUtil.saveString(mContext,"etPhone1","");
        SharedPreferencesUtil.saveString(mContext,"address1","");

        if(TextUtils.isEmpty(orderId)||orderId.equals("")) {
            tv_location.setEnabled(true);
        }else {
            tv_location.setEnabled(false);
        }

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_quick:
                        switchOrder(cityId);
                        rl_info.setVisibility(View.VISIBLE);
                        rb_quick.setBackgroundResource(R.drawable.shape_white3);
                        rb_order.setBackgroundResource(R.drawable.ysf_action_bar_icon_transparent);
                        rb_quick.setTextColor(Color.parseColor("#FD6601"));
                        rb_order.setTextColor(Color.parseColor("#ffffff"));
                        break;

                    case R.id.rb_order:
                        switchQuick();
                        rl_info.setVisibility(View.GONE);
                        rb_order.setTextColor(Color.parseColor("#FD6601"));
                        rb_quick.setTextColor(Color.parseColor("#ffffff"));
                        rb_order.setBackgroundResource(R.drawable.shape_white3);
                        rb_quick.setBackgroundResource(R.drawable.ysf_action_bar_icon_transparent);
                        break;
                }
            }
        });
    }
    FragmentTransaction fragmentTransaction;
    HuoOrderFragment huoOrderFragment;
    QuickFragment qucikFragment;
    private void switchOrder(String cityId) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        rb_order.setClickable(true);
        if (huoOrderFragment == null) {
            huoOrderFragment = new HuoOrderFragment();
            fragmentTransaction.add(R.id.content, huoOrderFragment, HuoOrderFragment.class.getCanonicalName());
            Bundle bundle = new Bundle();
            bundle.putString("orderId",orderId);
            bundle.putString("cityId",cityId);
            huoOrderFragment.setArguments(bundle);
        }
        fragmentTransaction.show(huoOrderFragment);
        if (qucikFragment != null) {
            fragmentTransaction.hide(qucikFragment);
        }


        fragmentTransaction.commitAllowingStateLoss();
    }


    private void switchQuick() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (qucikFragment == null) {
            qucikFragment = new QuickFragment();
            fragmentTransaction.add(R.id.content, qucikFragment, QuickFragment.class.getCanonicalName());
        }
        fragmentTransaction.show(qucikFragment);

        if (huoOrderFragment != null) {
            fragmentTransaction.hide(huoOrderFragment);
        }


        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void setClickEvent() {
        tv_zhuang.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        iv_huo.setOnClickListener(this);
        tv_location.setOnClickListener(this);

        isAuth();
    }

    String cityId;
    private void getCityId(String orderId) {
        HuolalaAPI.getCityId(mActivity,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoCityIdModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoCityIdModel huoCityIdModel) {
                        if(huoCityIdModel.getCode()==1) {
                            if(huoCityIdModel.getData()!=null) {
                                cityId = huoCityIdModel.getData().getCity_id();
                                switchOrder(huoCityIdModel.getData().getCity_id());
                                tv_location.setText(huoCityIdModel.getData().getName());

                                SharedPreferencesUtil.saveString(mContext,"huoCityName",huoCityIdModel.getData().getName());
                                SharedPreferencesUtil.saveString(mContext,"huoCityId",huoCityIdModel.getData().getCity_id());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoCityIdModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location:
                Intent intentss = new Intent(mContext,HuoCityActivity.class);
                startActivity(intentss);
                break;

            case R.id.iv_huo:
                finish();
                break;
            case R.id.tv_zhuang:
                Intent intent = new Intent(mContext,HuoEditAddressActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;

            case R.id.tv_unload:
                Intent intents = new Intent(mContext,HuoEditxActivity.class);
                intents.putExtra("type",2);
                startActivity(intents);
                break;

            case R.id.tv_phone:
                getAuth();
                break;
        }
    }

    /**
     * 判断是否需要授权
     */
    private void isAuth() {
        HuolalaAPI.isAuthorize(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsAuthModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsAuthModel isAuthModel) {
                        if(isAuthModel.getCode()==1) {
                            if(isAuthModel.getData()!=null) {
                                tv_phone.setText(isAuthModel.getData().getAuthPhone());
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,isAuthModel.getMessage());
                        }
                    }
                });
    }

    private void getAuth() {
        HuolalaAPI.getAuthUrl(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryProdModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QueryProdModel queryProdModel) {
                        if(queryProdModel.getCode()==1) {
                            if(queryProdModel.getData()!=null) {
                                startActivity(CommonH6Activity.getIntent(mContext,CommonH6Activity.class,queryProdModel.getData(),orderId));
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,queryProdModel.getMessage());
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCity(HuoCityEvent huoCityEvent) {
        tv_location.setText(huoCityEvent.getName());
        cityId = huoCityEvent.getCityId();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
