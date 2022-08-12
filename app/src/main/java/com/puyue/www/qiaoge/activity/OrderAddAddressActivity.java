package com.puyue.www.qiaoge.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.EditAndAddActivity;
import com.puyue.www.qiaoge.adapter.mine.SuggestAdressAdapter;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.mine.address.AddAddressAPI;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.AreaModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderAddAddressActivity extends BaseSwipeActivity {

    public static final String TYPE = "type";//是来编辑地址,还是来新建地址的
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";
    public static final String STORE_NAME = "store_name";
    public static final String AREA = "area";//三级城市
    public static final String ADDRESS = "address";//详细地址
    public static final String DEFAULT = "default";//是否是默认地址
    public static final String ADDRESS_ID = "address_id";//地址id
    public static final String PROVINCE_CODE = "province_code";//省ID
    public static final String CITY_CODE = "city_code";//市ID
    public static final String AREA_CODE = "area_code";
    public static final String ORDERID = "orderId";
    AVLoadingIndicatorView lav_activity_loading;
    private String mType;
    private String mUserName;
    private String mUserPhone;
    private String mStoreName;
    private String mArea;
    private String mAddress;
    private String mDefault;
    private String mAddressId;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;
    private ImageView mIvBack;
    private EditText mEditName;
    private EditText mEditPhone;
    private EditText mEditStore;
    private TextView mTvArea;
    private AutoCompleteTextView keyWorldsView;
    private Button mBtnConfirm;
    private NestedScrollView mLlEditAddress;
    private TextView mTvTitle;
    //申明对象
    private CityPickerView mPicker = new CityPickerView();
    private String proviceCode;
    private String cityCode;
    private String areaCode;
    private int isDefault;
    private BaseModel mModelAddAddress;
    private BaseModel mModelEditAddress;
    private boolean isDefaultNow = false;
    private boolean isLoaded = false;
    //private PoiSearch poiSearch= PoiSearch.newInstance();
//    private SuggestionSearch mSuggestionSearch;

    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;


    private SuggestAdressAdapter adressAdapter;
    private RecyclerView ry_suggest;
    private TextView tv_target;
    TextView tv_edit_address_area;

    //  省
    private List<AreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean>>> options3Items = new ArrayList<>();

    private String cityName;
    private String orderId;
    public static Intent getIntent(Context context, Class<?> cls, String type, String userName, String userPhone, String storeName, String area, String address, String defaultOr, String addressId, String proviceCode, String cityCode, String areaCode, String orderId) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(TYPE, type);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(USER_PHONE, userPhone);
        intent.putExtra(STORE_NAME, storeName);
        intent.putExtra(AREA, area);
        intent.putExtra(ADDRESS, address);
        intent.putExtra(DEFAULT, defaultOr);
        intent.putExtra(ADDRESS_ID, addressId);
        intent.putExtra(PROVINCE_CODE, proviceCode);
        intent.putExtra(CITY_CODE, cityCode);
        intent.putExtra(AREA_CODE, areaCode);
        intent.putExtra(ORDERID, orderId);
        return intent;
    }

    private final MyHandler handler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<OrderAddAddressActivity> mActivity;

        public MyHandler(OrderAddAddressActivity activity) {
            // TODO Auto-generated constructor stub
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            OrderAddAddressActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mType = getIntent().getStringExtra(TYPE);
        mUserName = getIntent().getStringExtra(USER_NAME);
        mUserPhone = getIntent().getStringExtra(USER_PHONE);
        mStoreName = getIntent().getStringExtra(STORE_NAME);
        mArea = getIntent().getStringExtra(AREA);
        mAddress = getIntent().getStringExtra(ADDRESS);
        mDefault = getIntent().getStringExtra(DEFAULT);
        mAddressId = getIntent().getStringExtra(ADDRESS_ID);
        mProvinceCode = getIntent().getStringExtra(PROVINCE_CODE);
        mCityCode = getIntent().getStringExtra(CITY_CODE);
        mAreaCode = getIntent().getStringExtra(AREA_CODE);
        orderId = getIntent().getStringExtra(ORDERID);
        if (savedInstanceState != null) {
            mType = savedInstanceState.getString(TYPE);
            mUserName = savedInstanceState.getString(USER_NAME);
            mUserPhone = savedInstanceState.getString(USER_PHONE);
            mStoreName = savedInstanceState.getString(STORE_NAME);
            mArea = savedInstanceState.getString(AREA);

            mAddress = savedInstanceState.getString(ADDRESS);
            mDefault = savedInstanceState.getString(DEFAULT);
            mAddressId = savedInstanceState.getString(ADDRESS_ID);
            mProvinceCode = savedInstanceState.getString(PROVINCE_CODE);
            mCityCode = savedInstanceState.getString(CITY_CODE);
            mAreaCode = savedInstanceState.getString(AREA_CODE);
            orderId = savedInstanceState.getString(ORDERID);
        }
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_order_address);
    }


    @Override
    public void findViewById() {

        lav_activity_loading = (AVLoadingIndicatorView) findViewById(R.id.lav_activity_loading);
        mIvBack = (ImageView) findViewById(R.id.iv_edit_address_back);
        mEditName = (EditText) findViewById(R.id.edit_edit_address_name);
        mEditPhone = (EditText) findViewById(R.id.edit_edit_address_phone);
        mEditStore = (EditText) findViewById(R.id.edit_edit_address_store);//店名选填
        mTvArea = (TextView) findViewById(R.id.tv_edit_address_area);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.edit_edit_address_address);
        mBtnConfirm = (Button) findViewById(R.id.btn_edit_address_confirm);
        //  mLlEditAddress = (NestedScrollView) findViewById(R.id.ll_edit_address);
        mTvTitle = (TextView) findViewById(R.id.tv_edit_address_title);
        ry_suggest = (RecyclerView) findViewById(R.id.ry_suggest);
        tv_edit_address_area = (TextView) findViewById(R.id.tv_edit_address_area);
        tv_target = (TextView) findViewById(R.id.tv_target);
    }

    @Override
    public void setViewData() {
        mPicker.init(mContext);
        if (StringHelper.notEmptyAndNull(mType)) {
            if (mType.equals("add")) {
                mBtnConfirm.setEnabled(false);
                mBtnConfirm.setTextColor(getResources().getColor(R.color.app_btn_unable));
                mTvTitle.setText("新增收货地址");
                mTvArea.setText("请选择所在地区");
                mTvArea.setTextColor(Color.parseColor("#D5D5D5"));
            } else if (mType.equals("edit")) {
                mBtnConfirm.setEnabled(true);
                mBtnConfirm.setTextColor(getResources().getColor(R.color.app_color_white));
                mTvTitle.setText("编辑收货地址");
                mTvArea.setText(mArea);
                mTvArea.setTextColor(Color.parseColor("#232131"));
                mEditName.setText(mUserName);
                mEditPhone.setText(mUserPhone);
                keyWorldsView.setText(mAddress);
                if (StringHelper.notEmptyAndNull(mStoreName)) {
                    mEditStore.setText(mStoreName);

                }
            }
        }
        selectCity();

        /* 当输入关键字变化时，动态更新建议列表 */
        keyWorldsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    ry_suggest.setVisibility(View.GONE);
                    tv_target.setVisibility(View.GONE);
                    return;
                }

                showSugDialog(cs);

            }
        });

    }
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1000:
                showAutoComplete((SuggestionResultObject)msg.obj);
                break;

            default:
                break;
        }
    }

    /**
     * 显示完整ListView
     * @param obj
     */
    protected void showAutoComplete(SuggestionResultObject obj) {
        if (obj.data.size() == 0) {
            ry_suggest.setVisibility(View.GONE);
            return;
        }
        adressAdapter = new SuggestAdressAdapter(obj.data, mContext, new SuggestAdressAdapter.onClick() {
            @Override
            public void setLocation(int pos) {

            }
        });
        ry_suggest.setLayoutManager(new LinearLayoutManager(mContext));
        ry_suggest.setAdapter(adressAdapter);
        adressAdapter.notifyDataSetChanged();
        ry_suggest.setVisibility(View.GONE);

    }

    private void showSugDialog(CharSequence cs) {
        if (tv_edit_address_area.getText().toString()!=null&&!tv_edit_address_area.getText().toString().equals("")) {
            suggestion(cs.toString());
        }
    }

    protected void suggestion(String keyword) {
        if (keyword.trim().length() == 0) {
            ry_suggest.setVisibility(View.GONE);
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        SuggestionParam suggestionParam = new SuggestionParam(keyword, tv_edit_address_area.getText().toString());
        //suggestion也提供了filter()方法和region方法
        //具体说明见文档，或者官网的webservice对应接口
        tencentSearch.suggestion(suggestionParam, new HttpResponseListener<BaseObject>() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = arg1;
                handler.sendMessage(msg);

            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
            }
        });
    }

    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(noDoubleClickListener);
        mTvArea.setOnClickListener(noDoubleClickListener);
        mBtnConfirm.setOnClickListener(noDoubleClickListener);
        mEditName.addTextChangedListener(textWatcher);
        mEditPhone.addTextChangedListener(textWatcher);
        keyWorldsView.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringHelper.notEmptyAndNull(mEditName.getText().toString())
                    && StringHelper.notEmptyAndNull(mEditPhone.getText().toString())
                    && StringHelper.notEmptyAndNull(keyWorldsView.getText().toString())
                    && StringHelper.notEmptyAndNull(mTvArea.getText().toString())
                    && !mTvArea.getText().toString().equals("请选择所在地区")) {
                mBtnConfirm.setEnabled(true);
                mBtnConfirm.setTextColor(getResources().getColor(R.color.app_color_white));
            } else {
                mBtnConfirm.setEnabled(false);
                mBtnConfirm.setTextColor(getResources().getColor(R.color.app_btn_unable));
            }
        }
    };

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == mIvBack) {
                //这里要判断,如果用户对文案进行了操作,但是要退出,需要弹框提示
                //如果没有对文案进行过操作,直接退出
                finish();
            } else if (view == mTvArea) {
                hintKbTwo();
                if(isLoaded) {
                    showPickerView();
                }

            } else if (view == mBtnConfirm) {
                //在这里点击确定了,判断数据是否完整,完整才能请求接口
                //请求接口反成功之后,调用onResult,finish这个activity
                //回到上个地址列表页,重新请求一次地址列表数据
                if (mEditPhone.getText().toString().length() == 11) {
                    if (mType.equals("add")) {
                        mBtnConfirm.setEnabled(false);
                        requestAddAddress();
                    } else if (mType.equals("edit")) {
                        mBtnConfirm.setEnabled(false);
                        requestEditAddress();
                    }
                } else {
                    AppHelper.showMsg(mContext, "手机号位数错误");
                }
            }
        }


    };

    private void requestEditAddress() {

        if (mTvArea.getText().toString().equals(mArea)) {
            //说明用户可能修改了其他的数据,但是没有修改所在区域,这时候直接将上个界面传过来的值赋值给现在要上传的数据
            proviceCode = mProvinceCode;
            cityCode = mCityCode;
            areaCode = mAreaCode;
        }
        AddAddressAPI.requestAddAddress(mContext, mEditName.getText().toString(), mEditPhone.getText().toString(), mProvinceCode, mCityCode, mAreaCode, isDefault, keyWorldsView.getText().toString(), mEditStore.getText().toString(), mAddressId,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        mModelEditAddress = baseModel;
                        if (mModelEditAddress.success) {
                            AppHelper.showMsg(mContext, "修改地址成功!");
                            Intent intent = new Intent();
                            intent.putExtra("type", "edit");
                            intent.putExtra("defaultNum",isDefault);
                            OrderAddAddressActivity.this.setResult(22, intent);
                            mBtnConfirm.setEnabled(true);
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            finish();
                        } else {
                            mBtnConfirm.setEnabled(true);
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            AppHelper.showMsg(mContext, mModelEditAddress.message);
                        }
                    }
                });
    }

    private void requestAddAddress() {
        /**
         * 新增地址
         */
        AddAddressAPI.requestAddAddress(mContext, mEditName.getText().toString(), mEditPhone.getText().toString(), mProvinceCode, mCityCode, mAreaCode, isDefault, keyWorldsView.getText().toString(), mEditStore.getText().toString(), "","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        logoutAndToHome(mContext, baseModel.code);
                        mModelAddAddress = baseModel;
                        if (mModelAddAddress.success) {
                            AppHelper.showMsg(mContext, "新增地址成功!");
                            Intent intent = new Intent();
                            intent.putExtra("type", "add");
                            OrderAddAddressActivity.this.setResult(11, intent);
                            mBtnConfirm.setEnabled(true);
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            finish();
                        } else {
                            mBtnConfirm.setEnabled(true);
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            AppHelper.showMsg(mContext, mModelAddAddress.message);
                        }
                    }
                });
    }

    private void selectCity() {
        AddressListAPI.getArea(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AreaModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AreaModel areaModel) {
                        if (areaModel.isSuccess()) {
                            parseData(areaModel.getData());
                        } else {
                            AppHelper.showMsg(mContext, areaModel.getMessage());
                        }
                    }
                });
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mProvinceCode = options1Items.get(options1).getCode();
                mCityCode = options2Items.get(options1).get(options2).getCode();
                mAreaCode = options3Items.get(options1).get(options2).get(options3).getCode();
                String tx = options1Items.get(options1).getName() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tv_edit_address_area.setText(tx);
                tv_edit_address_area.setTextColor(Color.parseColor("#333333"));
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();


    }
    private void parseData(List<AreaModel.DataBean> data) {
        options1Items = data;
////     遍历省
        for(int i = 0; i <data.size() ; i++) {
//         存放城市
            ArrayList<AreaModel.DataBean.ChildrenBeanX> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean>> province_AreaList = new ArrayList<>();
            List<AreaModel.DataBean.ChildrenBeanX> children1 = data.get(i).getChildren();
            cityList.addAll(children1);
//         遍历市
            for(int c = 0; c <data.get(i).getChildren().size() ; c++) {
                //该城市的所有地区列表
                ArrayList<AreaModel.DataBean.ChildrenBeanX.ChildrenBean> city_AreaList = new ArrayList<>();

                if (data.get(i).getChildren().get(c).getChildren() == null || data.get(i).getChildren().get(c).getChildren().size() == 0) {
                    AreaModel.DataBean.ChildrenBeanX.ChildrenBean childrenBean = new AreaModel.DataBean.ChildrenBeanX.ChildrenBean();
                    childrenBean.setName("");
                    city_AreaList.add(childrenBean);
                } else {

                    List<AreaModel.DataBean.ChildrenBeanX.ChildrenBean> children = data.get(i).getChildren().get(c).getChildren();
                    city_AreaList.addAll(children);
                    province_AreaList.add(city_AreaList);

                }
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);

        }

        isLoaded = true;


    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void showBackDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.CommonDialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_edit_address_back);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_back_address_dialog_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_back_address_dialog_confirm);
        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //这里要判断,如果用户对文案进行了操作,但是要退出,需要弹框提示
            //如果没有对文案进行过操作,直接退出
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
