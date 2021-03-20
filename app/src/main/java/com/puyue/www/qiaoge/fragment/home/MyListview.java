package com.puyue.www.qiaoge.fragment.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ${王涛} on 2021/2/1
 */
public class MyListview extends ListView {

    public MyListview(Context context) {
        super(context);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int Hightspec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, Hightspec);
    }
}
