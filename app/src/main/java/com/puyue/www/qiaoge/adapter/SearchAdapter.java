package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.adapter.HotCityGridAdapter;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    List<String> fitList;
    Context mContext;
    public SearchAdapter(Context mContext, List<String> fitList) {
        this.fitList = fitList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return fitList.size();
    }

    @Override
    public Object getItem(int position) {
        return fitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(com.zaaach.citypicker.R.layout.cp_item_city_listview, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(com.zaaach.citypicker.R.id.tv_item_city_listview_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(fitList.get(position));
        return view;
    }

    public static class ViewHolder{
        TextView name;
    }
}
