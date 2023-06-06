package com.puyue.www.qiaoge.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.puyue.www.qiaoge.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class MyStandard1GSYVideoPlayer extends StandardGSYVideoPlayer {
    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public MyStandard1GSYVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyStandard1GSYVideoPlayer(Context context) {
        super(context);
    }

    public MyStandard1GSYVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_video_phone;
    }
}
