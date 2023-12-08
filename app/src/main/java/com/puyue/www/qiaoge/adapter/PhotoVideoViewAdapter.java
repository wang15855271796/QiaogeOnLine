package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.photoview.OnPhotoTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.puyue.www.qiaoge.MyStandardGSYVideoPlayer;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.ShopDetailActivity;
import com.puyue.www.qiaoge.adapter.market.PhotoViewAdapter;
import com.puyue.www.qiaoge.event.StopSoundEvent;
import com.puyue.www.qiaoge.event.setFragmentsEvent;
import com.puyue.www.qiaoge.view.MyStandard1GSYVideoPlayer;
import com.puyue.www.qiaoge.view.PhotoVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class PhotoVideoViewAdapter extends PagerAdapter {
    List<String> imagesUrl;
    Activity context;

    private OnPhotoListener photoListener;
    ScaleGestureDetector mScaleGestureDetector;
    GestureDetector mGestureDetector;
    MyStandardGSYVideoPlayer myStandardGSYVideoPlayer;
    public OnPhotoListener getPhotoListener() {
        return photoListener;
    }

    public void setPhotoListener(OnPhotoListener photoListener) {
        this.photoListener = photoListener;
    }

    public interface OnPhotoListener {
        void onPhotoListenter();
    }

    public PhotoVideoViewAdapter(List<String> imagesUrl, Activity context) {
        this.imagesUrl = imagesUrl;
        this.context = context;
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int getCount() {
        return (imagesUrl == null || imagesUrl.size() == 0) ? 0 : imagesUrl.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imagesUrl.get(position);
        PhotoView photoView = new PhotoView(context);
        myStandardGSYVideoPlayer = new MyStandardGSYVideoPlayer(context);
        ImageView fullscreen = myStandardGSYVideoPlayer.findViewById(R.id.fullscreen);
        fullscreen.setVisibility(View.GONE);
        if(url.contains(".mp4")) {
            myStandardGSYVideoPlayer.setUp(url,false,"");
            myStandardGSYVideoPlayer.startPlayLogic();
            container.addView(myStandardGSYVideoPlayer);
        }else {
            Glide.with(context).load(url).into(photoView);
            container.addView(photoView);
        }


        photoView.isEnabled();
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (photoListener != null) {
                    photoListener.onPhotoListenter();
                }
            }
        });

        if(url.contains(".mp4")) {
            return myStandardGSYVideoPlayer;
        }else {
            return photoView;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        myStandardGSYVideoPlayer.onVideoPause();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setStop(StopSoundEvent stop) {
        if(myStandardGSYVideoPlayer!=null) {
            myStandardGSYVideoPlayer.onVideoPause();
        }
    }
}
