package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.puyue.www.qiaoge.activity.HuoCityActivity;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.HuoCityModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.adapter.CityListAdapter;
import com.zaaach.citypicker.adapter.HotCityGridAdapter;
import com.zaaach.citypicker.adapter.ZiGridAdapter;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.utils.MyShareUtil;
import com.zaaach.citypicker.utils.PinyinUtils;
import com.zaaach.citypicker.view.WrapHeightGridView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityListsAdapter extends BaseAdapter {

    private Context mContext;
    private List<HuoCityModel.DataBean.CityListBean> mCities;
    private LayoutInflater inflater;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    List<String> hotList;
    List<String> hotListId;
    private static final int VIEW_TYPE_COUNT = 5;
    List<String> ziLists;
    List<String> mListHistory;
    List<String> mListIdHistory;
    public CityListsAdapter(Context mContext, List<HuoCityModel.DataBean.CityListBean> cityList, List<String> hotList, List<String> hotListId, List<String> ziLists, List<String> mListHistory, List<String> mListIdHistory) {
        this.mContext = mContext;
        this.mCities = cityList;
        this.inflater = LayoutInflater.from(mContext);
        this.hotList = hotList;
        this.hotListId = hotListId;
        this.ziLists = ziLists;
        this.mListHistory = mListHistory;
        this.mListIdHistory = mListIdHistory;
        mCities.add(0,new HuoCityModel.DataBean.CityListBean("0",1,"0","0","0","0"));
        mCities.add(1,new HuoCityModel.DataBean.CityListBean("1",0,"1","1","1","1"));
        mCities.add(2,new HuoCityModel.DataBean.CityListBean("2",0,"1","3","1","1"));
        mCities.add(3,new HuoCityModel.DataBean.CityListBean("3",0,"1","4","1","1"));
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getName_en());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getName_en()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }
    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);

        if (view == null){
            view = inflater.inflate(com.zaaach.citypicker.R.layout.cp_item_city_listview, parent, false);
            holder = new CityViewHolder();
            holder.letter = (TextView) view.findViewById(com.zaaach.citypicker.R.id.tv_item_city_listview_letter);
            holder.name = (TextView) view.findViewById(com.zaaach.citypicker.R.id.tv_item_city_listview_name);
            view.setTag(holder);
        }else{
            holder = (CityViewHolder) view.getTag();
        }
        if (CityPickerActivity.HIDE_ALL_CITY) {
            view.setVisibility(View.GONE);
        }
        switch (viewType) {
            case 0: //定位
                view = inflater.inflate(com.zaaach.citypicker.R.layout.cp_view_locate_city, parent, false);
                TextView tv_city = (TextView) view.findViewById(com.zaaach.citypicker.R.id.tv_located_city);
                String huoCityName = UserInfoHelper.getCity(mContext);
                tv_city.setText(huoCityName);
                break;

            case 1: //最近
                view = inflater.inflate(com.zaaach.citypicker.R.layout.cp_history, parent, false);
                WrapHeightGridView gridView2 = (WrapHeightGridView) view.findViewById(com.zaaach.citypicker.R.id.gridview_hot_city);
                final ZiGridAdapter hotCityGridAdapter2 = new ZiGridAdapter(mContext,mListHistory);
                gridView2.setAdapter(hotCityGridAdapter2);
                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(mListHistory.get(position),mListIdHistory.get(position));
                        }
                    }
                });

                break;

            case 2://热门
                view = inflater.inflate(com.zaaach.citypicker.R.layout.cp_view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(com.zaaach.citypicker.R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext,hotList,hotListId);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(hotList.get(position),hotListId.get(position));
                        }
                    }
                });

                break;

            case 3://索引
                view = inflater.inflate(com.zaaach.citypicker.R.layout.cp_zi_mu, parent, false);
                WrapHeightGridView gridView1 = (WrapHeightGridView) view.findViewById(com.zaaach.citypicker.R.id.grid_zi);
                final ZiGridAdapter hotCityGridAdapter1 = new ZiGridAdapter(mContext,ziLists);
                gridView1.setAdapter(hotCityGridAdapter1);
                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            onCityClickListener.getPosition(letterIndexes.get(ziLists.get(position)));
                        }
                    }
                });
                break;

            case 4:
                if (position >= 1){
                    final String city = mCities.get(position).getName();
                    final String cityId = mCities.get(position).getCity_id();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getName_en());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getName_en()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(city,cityId);
                            }
                        }
                    });
                }
                break;


        }

        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    private OnCityClickListener onCityClickListener;
    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(String name,String cityId);
        void getPosition(int pos);
    }

}
