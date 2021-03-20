package com.puyue.www.qiaoge.fragment.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by ${王涛} on 2021/2/1
 */
public class MyRecycleView extends RecyclerView {
    public MyRecycleView(@NonNull Context context) {
        super(context);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int Hightspec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, Hightspec);
    }
}
