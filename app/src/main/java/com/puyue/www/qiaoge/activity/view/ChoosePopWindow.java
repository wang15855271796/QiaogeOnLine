package com.puyue.www.qiaoge.activity.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CateAdapter;
import com.puyue.www.qiaoge.adapter.SearchOperaAdapter;
import com.puyue.www.qiaoge.listener.PopWindowListener;

import java.util.List;

public class ChoosePopWindow extends PopupWindow {

    Activity mActivity;
    List<String> list;
    PopWindowListener popWindowListener;
    public ChoosePopWindow(Activity mActivity, List<String> list) {
        super(mActivity);
        this.mActivity=mActivity;
        this.list = list;
        init();
    }


    public void setPopWindowListener(PopWindowListener popWindowListener) {
        this.popWindowListener = popWindowListener;
    }

    SearchOperaAdapter searchOperaAdapter;
    private void init() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_popup, null, false);
        RecyclerView recyclerView =  (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        searchOperaAdapter = new SearchOperaAdapter(R.layout.item_search_operate,list);
        recyclerView.setAdapter(searchOperaAdapter);
        setContentView(view);
        searchOperaAdapter.notifyDataSetChanged();
        //1.构造一个PopupWindow，参数依次是加载的View，宽高

        searchOperaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                searchOperaAdapter.setOnItemPosition(position);
                if(popWindowListener!=null) {
                    popWindowListener.getCateStyle(list.get(position),position);
                }

            }
        });

    }

}
