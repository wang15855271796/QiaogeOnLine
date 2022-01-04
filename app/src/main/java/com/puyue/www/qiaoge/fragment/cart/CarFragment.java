package com.puyue.www.qiaoge.fragment.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.PaymentOrdersFragment;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.model.CarStyleModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.tv_car_desc)
    TextView tv_car_desc;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_length)
    TextView tv_length;
    @BindView(R.id.tv_bulky)
    TextView tv_bulky;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    List<CarStyleModel.DataBean.VehicleListBean> list;
    public static Fragment getInstance(List<CarStyleModel.DataBean.VehicleListBean> vehicle_list) {
        PaymentOrdersFragment fragment = new PaymentOrdersFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) vehicle_list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = (List<CarStyleModel.DataBean.VehicleListBean>) getArguments().getSerializable("list");
    }

    @Override
    public int setLayoutId() {
        return R.layout.car_fragment;
    }

    @Override
    public void initViews(View view) {
        bind = ButterKnife.bind(this, view);

    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
