package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.VideoHolder;
import com.puyue.www.qiaoge.model.PicVideoModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.view.GlideApp;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import rx.Subscriber;

public class PicVideoAdapter extends BannerAdapter<PicVideoModel.DatasBean, RecyclerView.ViewHolder> {
    private Context context;
    private SparseArray<RecyclerView.ViewHolder> mVHMap = new SparseArray<>();

    public PicVideoAdapter(Context context, List<PicVideoModel.DatasBean> mDatas) {
        super(mDatas);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
            case 2:
                return new VideoHolder(BannerUtils.getView(parent, R.layout.banner_video));

        }
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
    }


    @Override
    public int getItemViewType(int position) {
        //先取得真实的position,在获取实体
//        return getData(getRealPosition(position)).viewType;
        //直接获取真实的实体
        return getRealData(position).getType();
        //或者自己直接去操作集合
//        return mDatas.get(getRealPosition(position)).viewType;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, PicVideoModel.DatasBean data, int position, int size) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case 1:
                ImageHolder imageHolder = (ImageHolder) holder;
                mVHMap.append(position,imageHolder);
                Glide.with(context).load(data.getUrl()).into(imageHolder.imageView);
                break;
            case 2:
                VideoHolder videoHolder = (VideoHolder) holder;
                mVHMap.append(position,videoHolder);
                videoHolder.player.setUp(data.getUrl(), true, null);
                videoHolder.player.getBackButton().setVisibility(View.GONE);
                //增加封面
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.drawable.image4);
                videoHolder.player.setThumbImageView(imageView);
//                videoHolder.player.startPlayLogic();
                break;
        }
    }

    public SparseArray<RecyclerView.ViewHolder> getVHMap() {
        return mVHMap;
    }
}
