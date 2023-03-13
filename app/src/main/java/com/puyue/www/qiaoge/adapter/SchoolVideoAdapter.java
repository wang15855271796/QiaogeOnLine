package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class SchoolVideoAdapter extends BannerAdapter<String, RecyclerView.ViewHolder> {

    public SchoolVideoAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, String data, int position, int size) {

    }
}
