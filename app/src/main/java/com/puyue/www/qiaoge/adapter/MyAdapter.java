package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2020/12/23
 */
public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final Context mContext;
    private final List<String> mData;
    public MyAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_test, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 100;
    }
}
