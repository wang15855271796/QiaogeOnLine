package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.puyue.www.qiaoge.view.selectmenu.MyScrollView;

public class MyScrollView1 extends ScrollView {
    private ScrollChangedListener mListener;

    public MyScrollView1(Context context) {
        super(context);
    }

    public MyScrollView1(Context context, AttributeSet attrs) {
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
        void onTouch(boolean isDown);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mListener != null) {
                    mListener.onTouch(false);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onTouch(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
