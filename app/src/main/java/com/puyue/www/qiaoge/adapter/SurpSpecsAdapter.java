package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.home.SearchSpecsAdapter;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/4/24
 */
public class SurpSpecsAdapter extends BaseAdapter {
    Context context;
//    List<SurpliListModel.DataBean.ListBean.ProdSpecsBean> prodSpecs;
    List<ExchangeProductModel.DataBean.ProdSpecsBean> prodSpecs;
    int selectPosition;


//    public SurpSpecsAdapter(Context mContext, List<SurpliListModel.DataBean.ListBean.ProdSpecsBean> prodSpecs) {
//        this.context = mContext;
//        this.prodSpecs = prodSpecs;
//    }

    public SurpSpecsAdapter(Context context, List<ExchangeProductModel.DataBean.ProdSpecsBean> prodSpecs) {
        this.context = context;
        this.prodSpecs = prodSpecs;
    }

    @Override
    public int getCount() {
        return prodSpecs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spec, null);
            holder = new Holder();
            holder.tv_spec = convertView.findViewById(R.id.tv_spec);
            holder.iv_reduce = convertView.findViewById(R.id.iv_reduce);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if(selectPosition==position) {
            holder.tv_spec.setTextColor(Color.parseColor("#FF680A"));
            holder.tv_spec.setBackgroundColor(Color.parseColor("#FEF5EF"));

        }else {
            holder.tv_spec.setTextColor(Color.parseColor("#333333"));
            holder.tv_spec.setBackgroundColor(Color.parseColor("#eeeeee"));

        }


        if(prodSpecs.get(position).getProdDeduct()==0) {
            holder.iv_reduce.setVisibility(View.GONE);
        }else {
            holder.iv_reduce.setBackgroundResource(R.mipmap.icon_reduce);
            holder.iv_reduce.setVisibility(View.GONE);
        }

        holder.tv_spec.setText(prodSpecs.get(position).getSpec());
        holder.tv_spec.setEnabled(true);
        holder.tv_spec.setSelected(false);

        return convertView;
    }

    static class Holder {
        public TextView tv_spec;
        public ImageView iv_reduce;
    }

    public void selectPosition(int position) {
        this.selectPosition = position;

        notifyDataSetChanged();
    }
}
