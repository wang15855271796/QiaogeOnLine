package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;


import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.activity.view.LineGraph;
import com.puyue.www.qiaoge.activity.view.LinePoint;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DsrAdapter extends PagerAdapter {

    private Context mContext = null;
    private int mChildCount = 0;
    private int rangeType = 30,dataType = 1;

    public DsrAdapter(Context context) {
        super();
        this.mContext = context;

    }

    public void setRangeType(int rangeType) {
        this.rangeType = rangeType;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
    //写死，设计的效果
    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.activity_dsr_pager_item, null);
        ViewHolder viewHolder = null;
        if (null == viewHolder) {
            viewHolder = new ViewHolder(itemView);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }
//        setTabName(viewHolder, position);
        initGraphView(viewHolder);
        container.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        return itemView;
    }

    private void initGraphView(ViewHolder viewHolder) {
        Line line = new Line();
        for (int i = 0; i <= 5; i++) {
            line.addPoint(new LinePoint("11/0"+i, (int) (Math.random()*5)));
        }
        viewHolder.dsrTrendLine.setTipPrefix("DSR");
        viewHolder.dsrTrendLine.setLine(line);
    }



    public class ViewHolder {

        @BindView(R.id.dsr_trend_line)
        LineGraph dsrTrendLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        public void reset() {

        }
    }


}
