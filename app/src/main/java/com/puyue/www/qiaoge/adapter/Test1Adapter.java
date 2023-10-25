package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.view.EasyView;

import java.util.List;

public class Test1Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public Test1Adapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        EasyView swipeMenuLayout = helper.getView(R.id.swipeMenuLayout);
        TextView item_delete = helper.getView(R.id.item_delete);

        LinearLayout item_Layout = helper.getView(R.id.item_Layout);
        swipeMenuLayout.setIos(false);//设置是否开启IOS阻塞式交互
        swipeMenuLayout.setLeftSwipe(true);//true往左滑动,false为往右侧滑动
        swipeMenuLayout.setSwipeEnable(true);//设
        item_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                swipeMenuLayout.quickClose();
            }
        });

        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                swipeMenuLayout.quickClose();
            }
        });

    }
}
