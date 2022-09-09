package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


import com.google.android.material.appbar.AppBarLayout;

public class CustomAppbarLayout extends AppBarLayout {
    ScrollChangedListener mListener;
    int slop;
    public CustomAppbarLayout(Context context) {
        super(context);
    }

    public CustomAppbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollChangeListener(ScrollChangedListener mListener) {
        this.mListener = mListener;
    }

    private void setSlop(Context context) {
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public interface ScrollChangedListener {
        void onTouch(boolean isDown);

    }


//    private int touch;
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //  保存当前touch的纵坐标值
//                touch = (int) ev.getRawY();
//                if (mListener != null) {
//                    mListener.onTouch(true);
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mListener != null) {
//                    mListener.onTouch(true);
//                }
//                break;
//
//            case MotionEvent.ACTION_UP:
//                if (mListener != null) {
//                    mListener.onTouch(false);
//                }
//                break;
//
//            case MotionEvent.ACTION_CANCEL:
//                if (mListener != null) {
//                    mListener.onTouch(false);
//                }
//                break;
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }

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
                if (mListener != null) {
                    mListener.onTouch(true);
                }
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onTouch(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

}
