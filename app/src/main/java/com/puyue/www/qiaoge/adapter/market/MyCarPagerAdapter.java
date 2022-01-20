package com.puyue.www.qiaoge.adapter.market;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.tools.ScreenUtils;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.activity.view.LineGraph;
import com.puyue.www.qiaoge.activity.view.LinePoint;
import com.puyue.www.qiaoge.adapter.DsrAdapter;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.view.ExpandTextView;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCarPagerAdapter extends PagerAdapter {
    List<CarStyleModel.DataBean.VehicleListBean> list;
    Context context;
    int width;
    public MyCarPagerAdapter(List<CarStyleModel.DataBean.VehicleListBean> list, Context context) {
        this.list = list;
        this.context = context;
        width = ScreenUtils.getScreenWidth(context) - ScreenUtils.dip2px(context, 16 * 2);

    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getVehicle_name();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(
                R.layout.car_fragment, null);
        ViewHolder viewHolder = null;
        if (null == viewHolder) {
            viewHolder = new ViewHolder(itemView);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }
        initGraphView(viewHolder,position);
        container.addView(itemView);
        return itemView;
    }

    private void initGraphView(ViewHolder viewHolder, int position) {
        Glide.with(context).load(list.get(position).getImage_url_high_light()).into(viewHolder.iv_car);
        viewHolder.tv_length.setText(list.get(position).getVehicle_size());
        viewHolder.tv_bulky.setText(list.get(position).getVehicle_volume_text());
        viewHolder.tv_weight.setText(list.get(position).getVehicle_weight_text());
        if(list.get(position).getText_desc().equals("")|| TextUtils.isEmpty(list.get(position).getText_desc())) {
            viewHolder.tv_car_desc.setVisibility(View.GONE);
        }else {
            viewHolder.tv_car_desc.initWidth(width);
            viewHolder.tv_car_desc.setMaxLines(1);
            viewHolder.tv_car_desc.setCloseText(list.get(position).getText_desc());
            viewHolder.tv_car_desc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public class ViewHolder {

        @BindView(R.id.tv_length)
        TextView tv_length;
        @BindView(R.id.tv_bulky)
        TextView tv_bulky;
        @BindView(R.id.tv_weight)
        TextView tv_weight;
        @BindView(R.id.iv_car)
        ImageView iv_car;
        @BindView(R.id.tv_car_desc)
        ExpandTextView tv_car_desc;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        public void reset() {

        }
    }
}
