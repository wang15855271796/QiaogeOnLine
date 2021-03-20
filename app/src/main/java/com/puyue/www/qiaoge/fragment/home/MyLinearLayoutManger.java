package com.puyue.www.qiaoge.fragment.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ${王涛} on 2020/9/10
 */
public class MyLinearLayoutManger extends LinearLayoutManager {
    public MyLinearLayoutManger(Context context) {
        super(context);
    }

    public MyLinearLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            //try catch一下
            super.onLayoutChildren( recycler, state );
            Log.d("swdasssssss......","000");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("swdasssssss......",e.getMessage()+"ss");
        }

    }
}
