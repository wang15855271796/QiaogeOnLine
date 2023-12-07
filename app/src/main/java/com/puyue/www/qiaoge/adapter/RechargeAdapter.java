package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.puyue.www.qiaoge.R;

import java.util.ArrayList;
import java.util.List;

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder>  {
    List<String> pictureList;
    Activity mActivity;
    private List<LocalMedia> list = new ArrayList<>();
    public RechargeAdapter(Activity mActivity, List<String> pictureList) {
        this.pictureList = pictureList;
        this.mActivity = mActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_recharge_coupon, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.tv_tip.setVisibility(View.GONE);
            viewHolder.tv_given.setVisibility(View.GONE);
            viewHolder.tv_amount.setText("自定义");
//            viewHolder.ll_amount.setBackgroundResource(R.drawable.shape_recharge1);
        }else {
            viewHolder.tv_tip.setVisibility(View.VISIBLE);
            viewHolder.tv_given.setVisibility(View.VISIBLE);
            viewHolder.tv_amount.setText("1000");

        }

        if(pos == position) {
            viewHolder.tv_desc.setTextColor(Color.parseColor("#7E4E14"));
            viewHolder.tv_tip.setTextColor(Color.parseColor("#7E4E14"));
            viewHolder.tv_amount.setTextColor(Color.parseColor("#7E4E14"));
            viewHolder.ll_amount.setBackgroundResource(R.drawable.shape_recharge1);

        }else {
            viewHolder.tv_desc.setTextColor(Color.parseColor("#000000"));
            viewHolder.tv_tip.setTextColor(Color.parseColor("#000000"));
            viewHolder.tv_amount.setTextColor(Color.parseColor("#000000"));
            viewHolder.ll_amount.setBackgroundResource(R.drawable.shape_recharge);

        }

//        if(mItemClickListener!=null) {
//            viewHolder.itemView.setOnClickListener(view -> {
//                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
//                mItemClickListener.onItemClick(view, adapterPosition);
//            });
//        }
        viewHolder.ll_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
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
        return pictureList.size() + 1;
    }

    public void setList(List<LocalMedia> selectList) {
        this.list = selectList;
    }

    int pos = -1;
    public void setPos(int position) {
        pos = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_given;
        TextView tv_amount;
        TextView tv_tip;
        LinearLayout ll_amount;
        TextView tv_desc;
        public ViewHolder(View view) {
            super(view);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            ll_amount = (LinearLayout) view.findViewById(R.id.ll_amount);
            tv_tip = (TextView) view.findViewById(R.id.tv_tip);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            tv_given = (TextView) view.findViewById(R.id.tv_given);
        }
    }

    public interface Onclick {
        void addDialog();
        void deletPic(int pos);
    }
}
