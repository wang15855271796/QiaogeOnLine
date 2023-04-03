package com.puyue.www.qiaoge.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.puyue.www.qiaoge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/2/25
 */
public class ShopImageViewssAdapter extends RecyclerView.Adapter<ShopImageViewssAdapter.ViewHolder> {
    Onclick onclick;
    List<String> pictureList;
    Activity mActivity;
    private List<LocalMedia> list = new ArrayList<>();
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

            if(pictureList.get(position).contains(".mp4")) {
                viewHolder.iv_player.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_player.setVisibility(View.GONE);
            }

            Glide.with(mActivity).load(pictureList.get(position)).into(viewHolder.iv_pic);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick.deletPic(position);
                }
            });
            viewHolder.ll_del.setVisibility(View.VISIBLE);

            if(mItemClickListener!=null) {
                viewHolder.itemView.setOnClickListener(view -> {
                    int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                    mItemClickListener.onItemClick(view, adapterPosition);
                });
            }
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mItemClickListener = l;
    }

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    private boolean isShowAddItem(int position) {
        int size = pictureList.size() == 0 ? 0 : pictureList.size();
        return position == size;
    }

    public List<LocalMedia> getData() {
        return list == null ? new ArrayList<>() : list;
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

    public void setList(List<LocalMedia> selectList) {
        this.list = selectList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pic;
        LinearLayout ll_del;
        ImageView iv_player;
        public ViewHolder(View view) {
            super(view);
            iv_player = (ImageView) view.findViewById(R.id.iv_player);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
        }
    }

    public interface Onclick {
        void addDialog();
        void deletPic(int pos);
    }
}
