package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        //MeasureSpec.UNSPECIFIED 模式会导致LinearLayoutManager中的fill方法中判断是否可以无限制填冲整个recycleview为true，从而导致RecyclerView的复用失效
        if (MeasureSpec.getMode(heightSpec) == MeasureSpec.UNSPECIFIED) {
            int size = MeasureSpec.getSize(heightSpec);
            heightSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
