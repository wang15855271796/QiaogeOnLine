package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ChooseRequireAdapter;
import com.puyue.www.qiaoge.adapter.market.MyCarPagerAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.fragment.cart.HuoOrderFragment;
import com.puyue.www.qiaoge.fragment.cart.QuickFragment;
import com.puyue.www.qiaoge.fragment.home.NewFragment;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
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
    ChooseRequireAdapter chooseRequireAdapter;
    int position = 0;
    //附加要求选择
    List<Integer> list = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.huo_home_activity);
    }

    @Override
    public void findViewById() {
    }

    int type;
    @Override
    public void setViewData() {

        getCarStyle("1011");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        chooseRequireAdapter =  new ChooseRequireAdapter(R.layout.item_choose_req,reqList);
        recyclerView.setAdapter(chooseRequireAdapter);

        chooseRequireAdapter.setOnItemClickListener(new ChooseRequireAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, boolean hasFocus) {
                type = reqList.get(list.size()).getType();
                if(hasFocus) {
                    list.add(type);
                }else {
                    list.remove(type);
                }
            }

        });

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
                        switchOrder(data);
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
    private void switchOrder(CarStyleModel.DataBean data) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (huoOrderFragment == null) {
            huoOrderFragment = new HuoOrderFragment();
            fragmentTransaction.add(R.id.content, huoOrderFragment, HuoOrderFragment.class.getCanonicalName());
            Bundle bundle = new Bundle();
            bundle.putString("id",data.getVehicle_list().get(position).getOrder_vehicle_id());
            bundle.putSerializable("vehicleStdItem", (Serializable) data.getVehicle_list());
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
    }
    CarStyleModel.DataBean data;
    //附加要求集合
    List<CarStyleModel.DataBean.SpecReqItemBean> reqList = new ArrayList<>();
    private void getCarStyle(String cityId) {
        HuolalaAPI.getCarStyle(mActivity,cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarStyleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CarStyleModel carStyleModel) {
                        if(carStyleModel.getCode()==1) {
                            if(carStyleModel.getData()!=null) {
                                reqList.clear();
                                data = carStyleModel.getData();
                                tv_location.setText(data.getName());

                                List<CarStyleModel.DataBean.VehicleListBean> vehicle_list = data.getVehicle_list();
                                reqList.addAll(data.getSpec_req_item());
                                MyCarPagerAdapter myCarPagerAdapter = new MyCarPagerAdapter(vehicle_list,mContext);
                                viewPager.setAdapter(myCarPagerAdapter);
                                tab_layout.setupWithViewPager(viewPager);
                                switchOrder(data);
                                chooseRequireAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,carStyleModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zhuang:
                Intent intent = new Intent(mContext,HuoEditAddressActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;

            case R.id.tv_unload:
                Intent intents = new Intent(mContext,HuoEditAddressActivity.class);
                intents.putExtra("type",2);
                startActivity(intents);
                break;
        }
    }


}
