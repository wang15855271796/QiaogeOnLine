package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.MessageDetailActivity;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;

import java.util.List;

public class MarqueeWyAdapter extends RecyclerView.Adapter<MarqueeAdapter.ViewHolder>{
    List<IndexInfoModel.DataBean.NoticeInfoBean> data;
    Activity activity;
    IndexInfoModel.DataBean.NoticeInfoBean dataBean;
    public void setData(List<IndexInfoModel.DataBean.NoticeInfoBean> data, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public MarqueeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marquee_wy, null);
        return new MarqueeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarqueeAdapter.ViewHolder holder, int position) {
        dataBean = data.get(position % data.size());

        holder.tv_content.setText(dataBean.getContent());
        holder.tv_notice_desc.setText(dataBean.getTitle());
        holder.rl_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MessageDetailActivity.class);
                intent.putExtra("id",data.get(position % data.size()).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        TextView tv_notice_desc;
        RelativeLayout rl_notice;
        public ViewHolder(View view) {
            super(view);
            rl_notice = (RelativeLayout) view.findViewById(R.id.rl_notice);
            tv_notice_desc = (TextView) view.findViewById(R.id.tv_notice_desc);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
