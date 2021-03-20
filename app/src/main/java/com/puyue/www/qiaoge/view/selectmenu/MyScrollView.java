package com.puyue.www.qiaoge.view.selectmenu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by ${王文博} on 2019/6/26
 */
public class MyScrollView  extends ScrollView {
    private ScrollChangedListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollChangeListener(ScrollChangedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScrollChangedListener(l, t, oldl, oldt);
        }
    }

    public interface ScrollChangedListener {
        void onScrollChangedListener(int x, int y, int oldX, int oldY);
    }
}
