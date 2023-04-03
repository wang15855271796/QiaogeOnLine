package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.OnPhotoTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.puyue.www.qiaoge.adapter.market.PhotoViewAdapter;

import java.util.List;

public class PhotoVideoViewAdapter extends PagerAdapter {
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

    public PhotoVideoViewAdapter(List<String> imagesUrl, Activity context) {
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
        PhotoView photoView = new PhotoView(context);
        Glide.with(context).load(url).into(photoView);

        container.addView(photoView);
        photoView.isEnabled();
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (photoListener != null) {
                    photoListener.onPhotoListenter();
                }
            }
        });

        return photoView;
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
