package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.OnPhotoTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.puyue.www.qiaoge.adapter.market.PhotoViewAdapter;

import java.util.List;

public class VideoAdapter  extends PagerAdapter {
    List<String> imagesUrl;
    Context context;

    private OnPhotoListener photoListener;
    ScaleGestureDetector mScaleGestureDetector;
    GestureDetector mGestureDetector;

    public OnPhotoListener getPhotoListener() {
        return photoListener;
    }

    public void setPhotoListener(OnPhotoListener photoListener) {
        this.photoListener = photoListener;
    }

    public interface OnPhotoListener {
        void onPhotoListenter();
    }

    public VideoAdapter(List<String> imagesUrl, Context context) {
        this.imagesUrl = imagesUrl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (imagesUrl == null || imagesUrl.size() == 0) ? 0 : imagesUrl.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imagesUrl.get(position);
//        TestVideoView videoView = new TestVideoView();
        VideoView videoView = new VideoView(context);
        if(url.contains(".mp4")) {
            videoView.setVideoPath(url);
            videoView.start();
        }
        Log.d("wdasdwdas......",url);
        container.addView(videoView);
//        PhotoView photoView = new PhotoView(context);
//        Glide.with(context).load(url).into(photoView);
//        container.addView(photoView);
//        photoView.isEnabled();
//        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(ImageView view, float x, float y) {
//                if (photoListener != null) {
//                    photoListener.onPhotoListenter();
//                }
//            }
//        });


        return videoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
