package com.puyue.www.qiaoge.adapter.home;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.home.SpikeNewQueryModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.Utils;
import com.puyue.www.qiaoge.view.Snap;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by ${王文博} on 2019/4/12
 */
public class SpikeActiveNewAdapter extends RecyclerView.Adapter<SpikeActiveNewAdapter.MarketViewHolder> {
    private int selectPosition;      //记录当前选中的条目索引
    private OnItemClickListener onItemClickListener;


    private Context context;
    private List<SpikeNewQueryModel.DataBean> data;
    private int flag;
    private long startTime;
    private long endTime;
    private long l;
    private Date currents;
    private Date starts;
    private Date ends;
    private boolean exceed2;

    public SpikeActiveNewAdapter(Context context, List<SpikeNewQueryModel.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spike_new_active, parent, false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, final int position) {
        flag = data.get(position).getFlag();
        startTime = data.get(position).getStartTime();
        endTime = data.get(position).getEndTime();
        l = System.currentTimeMillis();
        String current = DateUtils.formatDate(l, "MM月dd日HH时mm分ss秒");
        String start = DateUtils.formatDate(startTime, "MM月dd日HH时mm分ss秒");
        String end = DateUtils.formatDate(endTime, "MM月dd日HH时mm分ss秒");
        try {
            currents = Utils.stringToDate(current, "MM月dd日HH时mm分ss秒");
            starts = Utils.stringToDate(start, "MM月dd日HH时mm分ss秒");
            ends = Utils.stringToDate(end, "MM月dd日HH时mm分ss秒");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectPosition == position) {
//            holder.tv_time.setAlpha(1.0f);
            holder.tv_desc.setAlpha(1.0f);
            holder.tv_desc.setTextColor(Color.parseColor("#FF2925"));
            holder.tv_desc.setBackgroundResource(R.drawable.shape_white3);
            holder.tv_desc.setPadding(8,7,8,7);
        } else {
//            holder.tv_time.setAlpha(0.7f);
            holder.tv_desc.setAlpha(0.7f);
            holder.tv_desc.setBackgroundResource(R.color.transparent);
            holder.tv_desc.setTextColor(Color.parseColor("#ffffff"));
        }

        if (onItemClickListener != null) {
            holder.linearLayoutNewSpike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }

        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_desc.setText(data.get(position).getTimeDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class MarketViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_desc;
        public LinearLayout linearLayoutNewSpike;
        Snap snap;
        TextView tv_title;
        public MarketViewHolder(View itemView) {
            super(itemView);
            tv_title = ((TextView) itemView.findViewById(R.id.tv_title));
            snap = ((Snap) itemView.findViewById(R.id.snap));
            tv_desc = ((TextView) itemView.findViewById(R.id.tv_desc));
            linearLayoutNewSpike = ((LinearLayout) itemView.findViewById(R.id.linearLayout_spike_new));
        }
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
    }
}
