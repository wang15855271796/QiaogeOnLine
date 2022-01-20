package com.zaaach.citypicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zaaach.citypicker.R;

import java.util.List;

public class ZiGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mCities;
    private List<String> hotListId;
    List<String> ziLists;

    public ZiGridAdapter(Context mContext, List<String> ziLists) {
        this.ziLists = ziLists;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return ziLists == null ? 0 : ziLists.size();
    }

    @Override
    public String getItem(int position) {
        return ziLists == null ? null : ziLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(ziLists.get(position));
        return view;
    }

    public static class ViewHolder{
        TextView name;
    }

}
