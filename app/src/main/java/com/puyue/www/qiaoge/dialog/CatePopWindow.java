package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CateAdapter;
import com.puyue.www.qiaoge.listener.PopWindowListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class CatePopWindow extends PopupWindow {
    Activity context;
    List<String> strings;
    PopWindowListener popWindowListener;
    public CatePopWindow(Activity context, List<String> strings) {
        super(context);
        this.context=context;
        this.strings = strings;
        init();
    }

    public void setPopWindowListener(PopWindowListener popWindowListener) {
        this.popWindowListener = popWindowListener;
    }


    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_recycleview, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(view);
        CateAdapter cateAdapter = new CateAdapter(R.layout.item_market, strings, new CateAdapter.Onclick() {
            @Override
            public void getCate(String item) {

            }
        });
        recyclerView.setAdapter(cateAdapter);
        cateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cateAdapter.setPosition(position);
                cateAdapter.notifyDataSetChanged();

                if(popWindowListener!=null) {
                    popWindowListener.getCateStyle(strings.get(position),position);
                }
            }
        });
    }


}
