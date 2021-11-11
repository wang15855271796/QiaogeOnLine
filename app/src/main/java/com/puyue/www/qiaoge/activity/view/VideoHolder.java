package com.puyue.www.qiaoge.activity.view;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class VideoHolder extends RecyclerView.ViewHolder {
    public MyControllView player;
    public RelativeLayout rl_sound;
    public VideoHolder(@NonNull View view) {
        super(view);
        player = view.findViewById(R.id.player);
        rl_sound = view.findViewById(R.id.rl_sound);
    }
}
