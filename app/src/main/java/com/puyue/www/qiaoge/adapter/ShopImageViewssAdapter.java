package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王涛} on 2021/2/25
 */
public class ShopImageViewssAdapter extends RecyclerView.Adapter<ShopImageViewssAdapter.ViewHolder> {
    Onclick onclick;
    List<String> pictureList;
    Activity mActivity;
    public ShopImageViewssAdapter(Activity mActivity, List<String> pictureList, Onclick onclick) {
        this.onclick  = onclick;
        this.pictureList = pictureList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_test, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.iv_pic.setImageResource(R.mipmap.post_photo);
            viewHolder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.addDialog();
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        }else {
            Glide.with(mActivity).load(pictureList.get(position)).into(viewHolder.iv_pic);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick.deletPic(position);

                }
            });
            viewHolder.ll_del.setVisibility(View.VISIBLE);
        }
    }

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    private boolean isShowAddItem(int position) {
        int size = pictureList.size() == 0 ? 0 : pictureList.size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }


    @Override
    public int getItemCount() {
        if(pictureList!=null) {
            if (pictureList.size() < 6) {
                return pictureList.size() + 1;
            } else {
                return pictureList.size();
            }
        }
      return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pic;
        LinearLayout ll_del;
        public ViewHolder(View view) {
            super(view);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
        }
    }

    public interface Onclick {
        void addDialog();
        void deletPic(int pos);
    }
    public interface Onclick1 {


    }

}
