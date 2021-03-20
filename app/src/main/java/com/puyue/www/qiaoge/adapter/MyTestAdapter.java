package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/9
 */
public class MyTestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    List<String> list = new ArrayList<>();
    public MyTestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        for (int i = 0; i < 30; i++) {
            list.add("ss");
        }
        MyTest1Adapter myTest1Adapter = new MyTest1Adapter(R.layout.item_full,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(myTest1Adapter);

    }
}
