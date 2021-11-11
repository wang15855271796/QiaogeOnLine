package com.puyue.www.qiaoge.activity.view;

import android.content.Context;
import android.util.AttributeSet;

import com.puyue.www.qiaoge.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class MyControllView extends StandardGSYVideoPlayer {
    public MyControllView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyControllView(Context context) {
        super(context);
    }

    public MyControllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_controll;
    }


}
