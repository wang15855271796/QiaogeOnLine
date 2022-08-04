package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.MessageCenterActivity;
import com.puyue.www.qiaoge.activity.mine.MessageDetailActivity;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.dialog.FullDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/28
 */
public class MarqueeAdapter extends RecyclerView.Adapter<MarqueeAdapter.ViewHolder>  {

    List<IndexInfoModel.DataBean.NoticeInfoBean> data;
    Activity activity;
    IndexInfoModel.DataBean.NoticeInfoBean dataBean;
    public void setData(List<IndexInfoModel.DataBean.NoticeInfoBean> data, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marquee, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dataBean = data.get(position % data.size());
        holder.tv_content.setText(dataBean.getContent());
        holder.tv_notice_desc.setText(dataBean.getTitle());
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
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
        public ViewHolder(View view) {
            super(view);
            tv_notice_desc = (TextView) view.findViewById(R.id.tv_notice_desc);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
