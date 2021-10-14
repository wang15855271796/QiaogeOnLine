package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.FullActiveActivity;
import com.puyue.www.qiaoge.event.FullListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullListAdapter extends BaseQuickAdapter<FullListModel.DataBean,BaseViewHolder> {

    public FullListAdapter(int layoutResId, @Nullable List<FullListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullListModel.DataBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        tv_title.setText(item.getName());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        FullImageViewAdapter fullImageViewAdapter = new FullImageViewAdapter(R.layout.item_full_img,item,item.getPics());
        recyclerView.setAdapter(fullImageViewAdapter);

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FullActiveActivity.class);
                intent.putExtra("fullId",item.getFullId());
                mContext.startActivity(intent);
            }
        });
//        fullImageViewAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(mContext,FullActiveActivity.class);
//                intent.putExtra("fullId",item.getFullId());
//                mContext.startActivity(intent);
//            }
//        });

    }
}
