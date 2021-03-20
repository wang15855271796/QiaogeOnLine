package com.puyue.www.qiaoge.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.puyue.www.qiaoge.AutoPollRecyclerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.MyAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2020/12/28
 */
public class Test3Activity extends BaseActivity {
    @BindView(R.id.recyclerView1)
    AutoPollRecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test3);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        for (int i = 0; i < 30; i++) {
            list.add("ssaa");
        }
        MyAdapter myAdapter = new MyAdapter(mActivity,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(myAdapter);
        recyclerView.start();
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
