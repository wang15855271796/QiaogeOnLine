package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.StringUtils;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.pictureselectordemo.ShopImageViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/19
 */
public class ShopImageViewsAdapter extends RecyclerView.Adapter<ShopImageViewsAdapter.ViewHolder>  {
    ImageView fiv;
    ImageView iv_add;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    Context context;
    private onAddPicClickListener mOnAddPicClickListener;
    protected OnItemClickListener mItemClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_filter_images,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setList(List<String> picList) {
        this.pictureList = picList;
        notifyDataSetChanged();

    }

    public void setLists(List<String> picList) {
        for (int i = 0; i < picList.size(); i++) {
            pictureList.add(picList.get(i));
        }
        notifyDataSetChanged();
    }

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
        void deletPic(int position);
    }
    List<String> pictureList;
    public ShopImageViewsAdapter(Context context,onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.mipmap.icon_adds);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);

        } else {
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.deletPic(position);
                    notifyDataSetChanged();
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        notifyItemRemoved(index);

                    }
                }
            });

            Glide.with(context).load(pictureList.get(position)).into(viewHolder.mImg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = pictureList.size() == 0 ? 0 : pictureList.size();
        return position == size;
    }
    @Override
    public int getItemCount() {
        if (pictureList.size() < 6) {
            return pictureList.size() + 1;
        } else {
            return pictureList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        }
    }

}
