package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.MenuItemsAdapter;
import com.puyue.www.qiaoge.adapter.MenuSecondItemAdapter;
import com.puyue.www.qiaoge.fragment.home.InfoFragment;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.view.CascadingMenuView;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;
import com.puyue.www.qiaoge.view.MenuItemAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static io.dcloud.common.util.ReflectUtils.getApplicationContext;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class ChooseViews extends LinearLayout {
    private static final String TAG = CascadingMenuView.class.getSimpleName();
    // 三级菜单选择后触发的接口，即最终选择的内容
    private CascadingMenuViewOnSelectListener mOnSelectListener;
    private ListView firstMenuListView;
    private ListView secondMenuListView;
    private ListView thirdMenuListView;

    // 每次选择的子菜单内容
    private List<CityChangeModel.DataBean.CityNamesBean> secondItem = new ArrayList<>();
    private ArrayList<CityChangeModel.DataBean> menuItem;

    private MenuItemsAdapter firstMenuListViewAdapter;

    private MenuSecondItemAdapter secondMenuListViewAdapter;

    ImageView iv_close;
    private int firstPosition = 0;
    private int secondPosition = 0;
    private Activity context;
    AVLoadingIndicatorView lav_activity_loading;
    /**
     * @param context
     *            上下文
     */

    public ChooseViews(Activity context, ArrayList<CityChangeModel.DataBean> menuList) {
        super(context);
        this.menuItem = menuList;
        this.context = context;
        init(context);
    }
    String city;
    private void init(final Activity context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_regionss, this, true);
        lav_activity_loading = findViewById(R.id.lav_activity_loading);
        TextView tv_area = findViewById(R.id.tv_area);
        TextView tv_reset = findViewById(R.id.tv_reset);
        LinearLayout ll_all = findViewById(R.id.ll_all);
        String city = UserInfoHelper.getCity(context);
        tv_area.setText(city);

        ll_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectListener != null) {
                    mOnSelectListener.cloese();
                    firstMenuListViewAdapter.setCustText("");
                    firstMenuListViewAdapter.notifyDataSetChanged();
                    secondMenuListViewAdapter.setCustText("");
                    secondMenuListViewAdapter.notifyDataSetChanged();
                }
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lav_activity_loading.setVisibility(View.GONE);
                lav_activity_loading.show();
            }
        });
        firstMenuListView = (ListView) findViewById(R.id.listView);
        secondMenuListView = (ListView) findViewById(R.id.listView2);
        View view = inflater.inflate(R.layout.footer_area,this,false);
//        firstMenuListView.addHeaderView(view);
//        secondMenuListView.addHeaderView(view);
        // setBackgroundDrawable(getResources().getDrawable(
        // R.drawable.choosearea_bg_left));
        // 初始化一级主菜单
        firstMenuListViewAdapter = new MenuItemsAdapter(context, menuItem,
                R.drawable.choose_eara_item_selector,
                R.drawable.choose_eara_item_selector);
        firstMenuListViewAdapter.setTextSize(17);
        firstMenuListViewAdapter.setSelectedPositionNoNotify(firstPosition, menuItem);
        firstMenuListView.setAdapter(firstMenuListViewAdapter);
        firstMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                secondItem = menuItem.get(position).getCityNames();

                // 通知适配器刷新
                secondMenuListViewAdapter.notifyDataSetChanged();
                secondMenuListViewAdapter.setSelectedPositionNoNotify(0, secondItem);

                if (mOnSelectListener != null) {
                    mOnSelectListener.getValue(menuItem.get(position));
                }

                secondMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {
                        if (mOnSelectListener != null) {
                            mOnSelectListener.getValues(secondItem.get(position));
                        }
                    }
                });

            }
        });


        // 初始化二级主菜单
        secondItem = menuItem.get(firstPosition).getCityNames();
        secondMenuListViewAdapter = new MenuSecondItemAdapter(context, secondItem, R.drawable.choose_eara_item_selector, R.drawable.choose_eara_item_selector);
        secondMenuListViewAdapter.setTextSize(15);
        secondMenuListViewAdapter.setSelectedPositionNoNotify(secondPosition, secondItem);
        secondMenuListView.setAdapter(secondMenuListViewAdapter);

    }

    public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
}
