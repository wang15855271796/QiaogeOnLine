package com.puyue.www.qiaoge;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

public class MyStandardGSYVideoPlayer extends StandardGSYVideoPlayer {


    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public MyStandardGSYVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyStandardGSYVideoPlayer(Context context) {
        super(context);
    }

    public MyStandardGSYVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_video;
    }
}
