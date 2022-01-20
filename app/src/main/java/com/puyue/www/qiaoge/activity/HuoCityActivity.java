package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CityListsAdapter;
import com.puyue.www.qiaoge.adapter.SearchAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.HuoCityEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.HotCityModel;
import com.puyue.www.qiaoge.model.HuoCityModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.zaaach.citypicker.adapter.CityListAdapter;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.view.SideLetterBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoCityActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.list_all)
    ListView list_all;
    @BindView(R.id.side_letter_bar)
    SideLetterBar side_letter_bar;
    @BindView(R.id.list_result)
    ListView list_result;
    @BindView(R.id.tv_over)
    TextView tv_over;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.empty_view)
    LinearLayout empty_view;
    SearchAdapter searchAdapter;
    private CityListsAdapter mCityAdapter;
    public static String[] ziList = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private List<String> mListHistory = new ArrayList<>();
    private List<String> mListIdHistory = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.huo_city_activity);
    }

    @Override
    public void findViewById() {

    }


    @Override
    public void setViewData() {
        String history = UserInfoHelper.getUserSearchHistory(mContext);
        String historyId = UserInfoHelper.getCityIdHistory(mContext);
        if (StringHelper.notEmptyAndNull(history)) {
            for (Object o : history.split(",")) {
                mListHistory.add((String) o);
            }
        }

        if (StringHelper.notEmptyAndNull(historyId)) {
            for (Object o : historyId.split(",")) {
                mListIdHistory.add((String) o);
            }
        }

        Log.d("dsfxdewfxds.....",mListHistory.size()+"s");
        getCity();
    }

    private void saveHistory(String text,String cityId) {
        //移除相同的元素
        for (int i = 0; i < mListHistory.size(); i++) {
            if (mListHistory.get(i).equals(text)) {
                mListHistory.remove(i);
            }
        }

        for (int i = 0; i < mListIdHistory.size(); i++) {
            cityId = cityId + "," + mListIdHistory.get(i);
        }

        UserInfoHelper.saveUserHistory(mContext, text);
        UserInfoHelper.saveCityIdHistory(mContext, cityId);
    }

    //搜索记录集合
    List<String> fitList = new ArrayList<>();
    List<String> fitIdList = new ArrayList<>();
    @Override
    public void setClickEvent() {
        ll_back.setOnClickListener(this);
        side_letter_bar.setOverlay(tv_over);
        side_letter_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                list_all.setSelection(position);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWords = s.toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!keyWords.equals("")) {
                            for (int i = 0; i < cityList.size(); i++) {
                                fiter(keyWords,i);
                            }
                            searchAdapter.notifyDataSetChanged();
                        }else {
                            list_result.setVisibility(View.GONE);
                            fitList.clear();
                            fitIdList.clear();
                            empty_view.setVisibility(View.GONE);
                        }
                    }
                },1000);
            }
        });

        searchAdapter = new SearchAdapter(mContext,fitList);
        list_result.setAdapter(searchAdapter);
        list_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveHistory(fitList.get(position),fitIdList.get(position));
            }
        });
    }

    private void fiter(String keyWords, int i) {
        Pattern p=Pattern.compile("[a-zA-Z]");
        Matcher m=p.matcher(keyWords);
        if(m.matches()){
            if(cityList.get(i).getName_en().contains(keyWords)) {
                fitList.add(cityList.get(i).getName());
                fitIdList.add(cityList.get(i).getCity_id());
            }else {
                fitList.remove(cityList.get(i).getName());
                fitIdList.remove(cityList.get(i).getCity_id());
            }
        }

        p=Pattern.compile("[\u4e00-\u9fa5]");
        m=p.matcher(keyWords);
        if(m.matches()){
            if(m.matches()){
                if(cityList.get(i).getName().contains(keyWords)) {
                    fitList.add(cityList.get(i).getName());
                    fitIdList.add(cityList.get(i).getCity_id());
                }else {
                    fitList.remove(cityList.get(i).getName());
                    fitIdList.remove(cityList.get(i).getCity_id());
                }
            }
        }

        if(fitList.size()>0) {
            empty_view.setVisibility(View.GONE);
            list_result.setVisibility(View.VISIBLE);
        }else {
            empty_view.setVisibility(View.VISIBLE);
            list_result.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    List<HuoCityModel.DataBean.CityListBean> cityList = new ArrayList<>();
    List<String> hotList = new ArrayList<>();
    List<String> hotListId = new ArrayList<>();
    private void getCity() {
        HuolalaAPI.getCity(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoCityModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoCityModel huoCityModel) {
                        if(huoCityModel.getCode()==1) {
                            if(huoCityModel.getData()!=null) {
                                hotList.clear();
                                cityList.clear();
                                hotListId.clear();
                                cityList.addAll(huoCityModel.getData().getCity_list());
                                for (int i = 0; i < cityList.size(); i++) {
                                    if(cityList.get(i).getIs_hot()==1) {
                                        //热门
                                        hotList.add(cityList.get(i).getName());
                                        hotListId.add(cityList.get(i).getCity_id());
                                    }
                                }

                                Collections.sort(cityList, new CityComparator());
                                List<String> ziLists = Arrays.asList(ziList);
                                mCityAdapter = new CityListsAdapter(mContext, cityList,hotList,hotListId,ziLists,mListHistory,mListIdHistory);
                                list_all.setAdapter(mCityAdapter);

                                mCityAdapter.setOnCityClickListener(new CityListsAdapter.OnCityClickListener() {
                                    @Override
                                    public void onCityClick(String name,String cityId) {
                                        EventBus.getDefault().post(new HuoCityEvent(name,cityId));
                                        saveHistory(name,cityId);
                                        finish();
                                    }

                                    @Override
                                    public void getPosition(int pos) {
                                        list_all.setSelection(pos);
                                    }
                                });

                                mCityAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoCityModel.getMessage());
                        }
                    }
                });
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<HuoCityModel.DataBean.CityListBean> {
        @Override
        public int compare(HuoCityModel.DataBean.CityListBean lhs, HuoCityModel.DataBean.CityListBean rhs) {
            String a = lhs.getName_en().substring(0, 1);
            String b = rhs.getName_en().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
