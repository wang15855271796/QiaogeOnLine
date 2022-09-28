package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AutoScrollManger extends LinearLayoutManager {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private boolean isPageUp = true; //是否自动翻页
    private int pageUpTime = 3 * 1000;//翻页间隔时间

    public AutoScrollManger(Context context) {
        super(context);
    }

    public AutoScrollManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public AutoScrollManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPageUp(boolean pageUp) {
        isPageUp = pageUp;
        if (pageUp) {
            startPageUp();
        }
    }

    public void setPageUpTime(int pageUpTime) {
        this.pageUpTime = pageUpTime;
    }

    public int getPageUpTime() {
        if (pageUpTime == 0) return 1 * 1000;
        return pageUpTime;
    }

    private void startPageUp() {
        handler.removeCallbacks(pageUpRunnable);
        handler.postDelayed(pageUpRunnable, getPageUpTime());
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
        this.recyclerView = recyclerView;
        startPageUp();
    }

    private Runnable pageUpRunnable = new Runnable() {
        @Override
        public void run() {
            if (getPageUpTime() <= 0) return;
            if (!isPageUp) return;
            pageUp();

            startPageUp();
        }
    };

    private int slideCountHeight;

    private void pageUp() {
        if (recyclerView == null) {
            try {
                throw new Exception("如果要使用翻页功能则不能直接加载数据，应通过notifyDataSetChanged 把数据更新过去");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (getOrientation() != RecyclerView.VERTICAL) {
            try {
                throw new Exception("翻页只暂时只支持垂直翻页，不支持水平翻页。");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recyclerView.getScrollState() != 0 || getItemCount() == 0 || getChildCount() == 0)
            return;
        if (getItemCount() == 0 || getChildCount() == 0) return;
        View childAt = getChildAt(0);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childAt.getLayoutParams();
        int height = childAt.getHeight() + params.topMargin + params.bottomMargin;
        int itemSize = findLastVisibleItemPosition() - findFirstVisibleItemPosition();

        int slideHeight;
        slideCountHeight += slideHeight = height * itemSize;

        int countHeight = height * getItemCount();
        if ((countHeight - slideCountHeight) <= 0) {
            slideHeight = 0 - countHeight;
            slideCountHeight = 0;
        }
        recyclerView.smoothScrollBy(0, slideHeight);

    }
}
