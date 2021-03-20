package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    public ChooseViews(Activity context, ArrayList<CityChangeModel.DataBean> menuList) {
        super(context);
        this.menuItem = menuList;
        this.context = context;
        init(context);
    }
    String city;
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取区县
            city = location.getCity();
            mLocationClient.stop();
            lav_activity_loading.setVisibility(View.GONE);
            lav_activity_loading.show();
        }
    }

    private void init(final Activity context) {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(true);
        mLocationClient.setLocOption(option);


        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_regionss, this, true);
        lav_activity_loading = findViewById(R.id.lav_activity_loading);
        TextView tv_area = findViewById(R.id.tv_area);
        TextView tv_reset = findViewById(R.id.tv_reset);
        String city = UserInfoHelper.getCity(context);
        tv_area.setText(city);

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
                lav_activity_loading.setVisibility(View.GONE);
                lav_activity_loading.show();
            }
        });
        firstMenuListView = (ListView) findViewById(R.id.listView);
        secondMenuListView = (ListView) findViewById(R.id.listView2);
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
//                        Log.d("wdasasdasd........",menuItem.toString());
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
