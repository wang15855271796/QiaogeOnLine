package com.puyue.www.qiaoge.fragment.market;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.market.ClassIfyModel;
import com.puyue.www.qiaoge.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends BaseFragment {

    public RecyclerView rv_market_detail;
    private ClassifyAdapter classifyAdapter;
    private List<ClassIfyModel.DataBean> list_left_copy = new ArrayList<>();
    ClassIfyModel classIfyModel;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void initViews(View view) {
        rv_market_detail = view.findViewById(R.id.rv_market_detail);
        getDate();
    }

    private void getDate() {

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
