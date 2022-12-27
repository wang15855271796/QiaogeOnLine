package com.puyue.www.qiaoge.activity.mine.wallet;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.AccountAdapter;
import com.puyue.www.qiaoge.adapter.mine.AccountSelectAdapter;

import com.puyue.www.qiaoge.adapter.mine.SearchAccountAdapter;
import com.puyue.www.qiaoge.adapter.mine.StickyListAdapter;
import com.puyue.www.qiaoge.api.home.GetSumPriceAPI;
import com.puyue.www.qiaoge.api.mine.GetWallertRecordByPageAPI;
import com.puyue.www.qiaoge.api.mine.subaccount.SearchAccountAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.DividerItemDecoration;
import com.puyue.www.qiaoge.helper.FVHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.home.GetSumPriceModel;
import com.puyue.www.qiaoge.model.home.SearchListModel;
import com.puyue.www.qiaoge.model.mine.GetWallertRecordByPageModel;
import com.puyue.www.qiaoge.popupwindow.MyWallDetailPopuwindow;
import com.puyue.www.qiaoge.view.datepicker.CustomDatePicker;
import com.puyue.www.qiaoge.view.selectmenu.MenuBarTwo;
import com.puyue.www.qiaoge.view.selectmenu.MyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author ${daff}
 * @date 2018/9/22.
 * 备注 我的明细
 */ //tv_month_select
public class MyWalletDetailActivity extends BaseSwipeActivity {
//    private Toolbar toolbar;
    private TextView textViewDetailed;
    private LinearLayout linearLayoutOnclick;
    //    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView recyclerView;
    private LinearLayout noData;// 没有数据的界面
    private LinearLayout data; // 没有数据的界面
    private int pageNum = 1;

    private List<GetWallertRecordByPageModel.DataBean.RecordsBean> mListData;
    private LoadingDailog dialog;
    StickyListHeadersListView lv;
    private String selectDate;
    private String year;
    private String month;
    private LinearLayout mLlTimeSelect;
    private MyWallDetailPopuwindow popuwindow;
    List listPopuwindow = new ArrayList();
    private String recordType;
    private CustomDatePicker mCpDate;
    private ImageView detailedImage;
    //筛选
    private TextView tv_select;
    TextView tv_all;
    ImageView iv_pic;
    //明细选择
    private TextView tv_detail_select;
    //收入
    private TextView tv_income;
    //支出
    private TextView tv_expenditure;
    //筛选弹窗
    private PopupWindow popupWindow;
    //明细弹窗
    private PopupWindow mPopupWindowOne;
    ImageView iv_back;
    private List<SearchListModel.DataBean.List1Bean> mList1 = new ArrayList<>();//筛选
    private List<SearchListModel.DataBean.List2Bean> mList2 = new ArrayList<>();//账户
    private List<SearchListModel.DataBean.List3Bean> mList3 = new ArrayList<>();//全部明细
    GetWallertRecordByPageModel getWallertRecordByPageModels;
    private boolean isSelected;
    private String phone;
    private String walletRecordChannelType;

    private ArrayList<View> viewList = new ArrayList<>();
    private ArrayList<String> contentThree = new ArrayList<>();
    private ArrayList<String> titles;
    private int showType;
    LinearLayout ll_root;
    StickyListAdapter adapters;
    ImageView iv_all;
    ImageView iv_select;
    SmartRefreshLayout refreshLayout;
    private List<GetWallertRecordByPageModel.DataBean.RecordsBean> records;
    private GetWallertRecordByPageModel.DataBean.RecordsBean recordsBean;
    private int isrefreshormore = 1;//1刷新  2加载

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_wallet_detail);
    }

    @Override
    public void findViewById() {
        refreshLayout = FVHelper.fv(this, R.id.refreshLayout);
        mLlTimeSelect = FVHelper.fv(this, R.id.ll_activity_wallet_time);
        iv_select = (ImageView) findViewById(R.id.iv_select);
        iv_all = (ImageView) findViewById(R.id.iv_all);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        textViewDetailed = (TextView) findViewById(R.id.textViewDetailed);
        linearLayoutOnclick = (LinearLayout) findViewById(R.id.linearLayoutOnclick);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noData = (LinearLayout) findViewById(R.id.noData);
        data = (LinearLayout) findViewById(R.id.data);
        detailedImage = (ImageView) findViewById(R.id.detailedImage);
        tv_all = findViewById(R.id.tv_all);
        tv_select = findViewById(R.id.tv_select);
        tv_detail_select = findViewById(R.id.tv_detail_select);
        tv_income = findViewById(R.id.tv_income);
        tv_expenditure = findViewById(R.id.tv_expenditure);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("123", "onLoadMore");
                if (getWallertRecordByPageModels.getData().getLastMonth() != null) {

                    GetWallertRecordByPageModel.DataBean data = getWallertRecordByPageModels.getData();
                    isrefreshormore = 2;
                    getWallertRecord(recordType, data.getLastYear(), data.getLastMonth(), null, showType, walletRecordChannelType);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mListData.clear();
                isrefreshormore = 1;
                getWallertRecord(recordType, year, month, phone, showType, walletRecordChannelType);
                refreshLayout.finishRefresh();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setViewData() {
        mListData = new ArrayList<>();
        lv = FVHelper.fv(this, R.id.lv);
        adapters = new StickyListAdapter(mActivity, mListData, lists, new StickyListAdapter.Onclick() {
            @Override
            public void clicks() {
                mLlTimeSelect.setVisibility(View.VISIBLE);
                mCpDate.show(selectDate);
            }
        });
        lv.setAdapter(adapters);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mListData.get(position).isNullData()) {
                    Intent intent = new Intent(mContext, MyCountDetailActivity.class);
                    intent.putExtra("id", mListData.get(position).getId());
                    intent.putExtra("type", mListData.get(position).getType());
                    mContext.startActivity(intent);
                }
            }
        });

        showType = getIntent().getIntExtra("showType", 0);

        final Calendar mCalendar = Calendar.getInstance();
        long time = System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        int i = mCalendar.get(Calendar.MONTH) + 1;
        month = i + "";
        year = mCalendar.get(Calendar.YEAR) + "";
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("获取数据中")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        setRecyclerView();
        requsetSearchMsg(showType);
        setTime();
        getWallertRecord(recordType, year, month, phone, showType, walletRecordChannelType);
        requsetPrice(recordType, year, month, phone, walletRecordChannelType, showType);
        titles = new ArrayList<>();
        titles.add("全部账户");


    }

    MyListView myListView2;

    /**
     * 获取搜索条件
     */
    private void requsetSearchMsg(int showType) {
        SearchAccountAPI.requestSearchList(mContext, showType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchListModel searchListModel) {
                        if (searchListModel.isSuccess()) {
                            mList1.clear();
                            mList2.clear();
                            mList3.clear();
                            contentThree.clear();
                            viewList.clear();
                            if (searchListModel.getData().getList1().size() > 0) {
                                mList1.addAll(searchListModel.getData().getList1());
                            }
                            if (searchListModel.getData().getList3().size() > 0) {
                                mList3.addAll(searchListModel.getData().getList3());
                            }
                            if (searchListModel.getData().getList2() != null && searchListModel.getData().getList2().size() > 0) {
                                tv_all.setVisibility(View.VISIBLE);

                                mList2.addAll(searchListModel.getData().getList2());

                                if (mList2.size() > 0) {
                                    for (int j = 0; j < mList2.size(); j++) {
                                        contentThree.add(mList2.get(j).getValue());
                                    }
                                }

                                if (myListView2 == null) {
                                    myListView2 = new MyListView(mActivity, contentThree);
                                }

                                viewList.add(0, myListView2);
                                myListView2.setOnSelectListener(new MyListView.OnSelectListener() {
                                    @Override
                                    public void getValue(String value, int position) {
                                        pageNum = 1;
                                        phone = mList2.get(position).getKey();
                                        mListData.clear();
                                        requsetPrice(recordType, year, month, phone, walletRecordChannelType, showType);
                                    }
                                });

                            } else {
                                tv_all.setVisibility(View.GONE);
                            }


                        } else {
                            AppHelper.showMsg(mContext, searchListModel.getMessage());
                        }


                    }
                });


    }

    @Override
    public void setClickEvent() {

        linearLayoutOnclick.setOnClickListener(noDoubleClickListener);

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //筛选弹窗
                showSelectPup();
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccount();
            }
        });

        tv_detail_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //明细弹窗
                showDetailPup();
            }
        });
    }

    /**
     * 账户弹窗
     */
    String value;

    private void showAccount() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.pop_account, null, false);//引入弹窗布局
        mPopupWindowOne = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        RecyclerView rl_select_two = vPopupWindow.findViewById(R.id.rl_select_two);
        TextView tv_sure = vPopupWindow.findViewById(R.id.tv_sure);
        FrameLayout fl_mark = vPopupWindow.findViewById(R.id.fl_mark);
        fl_mark.setVisibility(View.VISIBLE);
        mPopupWindowOne.setAnimationStyle(R.style.PopupWindowAnimation);
        mPopupWindowOne.setFocusable(true);
        value = mList2.get(0).getValue();
        iv_all.setImageResource(R.mipmap.ic_all_up);
        rl_select_two.setLayoutManager(new GridLayoutManager(mActivity, 2));
        ll_root.setBackgroundResource(R.drawable.shape_white4);
        AccountAdapter accountAdapter = new AccountAdapter(R.layout.my_account, mList2);
        rl_select_two.setAdapter(accountAdapter);
        accountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                accountAdapter.setPos(position);
                value = mList2.get(position).getValue();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindowOne.dismiss();
                fl_mark.setVisibility(View.GONE);
                tv_all.setText(value);
            }
        });

        fl_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindowOne.dismiss();
                fl_mark.setVisibility(View.GONE);
            }
        });
        //设置进出动画
        mPopupWindowOne.showAsDropDown(tv_select, 0, 0);
        iv_pic.setVisibility(View.GONE);
        tv_select.setVisibility(View.GONE);
        iv_select.setVisibility(View.GONE);
        tv_detail_select.setVisibility(View.GONE);
        mPopupWindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_pic.setVisibility(View.VISIBLE);
                tv_select.setVisibility(View.VISIBLE);
                iv_select.setVisibility(View.VISIBLE);
                iv_all.setImageResource(R.mipmap.ic_all_down);
                tv_detail_select.setVisibility(View.VISIBLE);
                ll_root.setBackgroundResource(R.drawable.shape_jianbian11);
            }
        });
    }

    /**
     * 筛选
     */
    String value1 = "筛选";
    private void showSelectPup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popup_select, null, false);//引入弹窗布局
        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        ll_root.setBackgroundResource(R.drawable.shape_white4);
        //设置进出动画
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        RecyclerView rl_select = vPopupWindow.findViewById(R.id.rl_select);
        TextView tv_ok = vPopupWindow.findViewById(R.id.tv_ok);
        FrameLayout fl_mark = vPopupWindow.findViewById(R.id.fl_mark);
        rl_select.setLayoutManager(new GridLayoutManager(mContext, 3));
        SearchAccountAdapter madater = new SearchAccountAdapter(R.layout.search_list, mList1);
        rl_select.setAdapter(madater);
        popupWindow.showAsDropDown(tv_select, 0, 0);
        iv_pic.setVisibility(View.GONE);
        tv_all.setVisibility(View.GONE);
        fl_mark.setVisibility(View.VISIBLE);
        popupWindow.setFocusable(true);
        iv_select.setImageResource(R.mipmap.ic_up);
        iv_all.setVisibility(View.GONE);
        tv_detail_select.setVisibility(View.GONE);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fl_mark.setVisibility(View.GONE);
                iv_pic.setVisibility(View.VISIBLE);
                tv_all.setVisibility(View.VISIBLE);
                tv_detail_select.setVisibility(View.VISIBLE);
                popupWindow.dismiss();

            }
        });
        madater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                madater.selectPosition(position);
                isSelected = true;
                recordType = mList1.get(position).getKey();
                value1 = mList1.get(position).getValue();
            }
        });


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    isSelected = false;
                    popupWindow.dismiss();
                    pageNum = 1;
                    mListData.clear();
                    isrefreshormore = 1;
                    refreshLayout.autoRefresh();
                    getWallertRecord(recordType, year, month, "", showType, null);
                    requsetPrice(recordType, year, month, phone, walletRecordChannelType, 1);

                } else {
                    AppHelper.showMsg(mContext, "请选择筛选类型");
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                fl_mark.setVisibility(View.GONE);
                iv_pic.setVisibility(View.VISIBLE);
                tv_all.setVisibility(View.VISIBLE);
                iv_all.setVisibility(View.VISIBLE);
                iv_select.setImageResource(R.mipmap.ic_down);
                tv_detail_select.setVisibility(View.VISIBLE);
                tv_select.setText(value1);
                ll_root.setBackgroundResource(R.drawable.shape_jianbian11);
            }
        });

        fl_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                fl_mark.setVisibility(View.GONE);
            }
        });

    }


    /**
     * 明细弹窗
     */
    private void showDetailPup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.pop_account_detail, null, false);//引入弹窗布局
        mPopupWindowOne = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置背景透明
        bgAlpha(0.3f);

        //设置进出动画
        mPopupWindowOne.setAnimationStyle(R.style.PopupWindowAni);
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.activity_my_wallet_detail, null);

        mPopupWindowOne.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        RecyclerView rl_select_two = vPopupWindow.findViewById(R.id.rl_select_two);

        rl_select_two.setLayoutManager(new LinearLayoutManager(mContext));

        AccountSelectAdapter accountSelectAdapter = new AccountSelectAdapter(R.layout.search_account_select, mList3);

        rl_select_two.setAdapter(accountSelectAdapter);

        accountSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                walletRecordChannelType = mList3.get(position).getKey();
                mPopupWindowOne.dismiss();
                dialog.show();
                tv_detail_select.setText(mList3.get(position).getValue());
                mListData.clear();
                getWallertRecord(recordType, year, month, phone, showType, walletRecordChannelType);
                requsetPrice(recordType, year, month, phone, walletRecordChannelType, showType);

            }
        });
        //添加分隔线
        DividerItemDecoration dividerPreKillDecoration = new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL_LIST);
        dividerPreKillDecoration.setDivider(R.drawable.app_divider);
        rl_select_two.addItemDecoration(dividerPreKillDecoration);

        mPopupWindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1f);
            }
        });
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.linearLayoutOnclick:
                    detailedImage.setImageResource(R.mipmap.ic_mine_detailed);

                    if (null == popuwindow) {
                        setPopuwindow();
                    }
                    popuwindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                    break;
            }
        }
    };

    /**
     * 网络请求
     */
    List<GetWallertRecordByPageModel.DataBean> lists = new ArrayList();

    private void getWallertRecord(String types, String year, String month, String phone, int showType, String walletRecordChannelType) {
        GetWallertRecordByPageAPI.requestData(mContext, types, year, month, phone, showType, walletRecordChannelType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetWallertRecordByPageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetWallertRecordByPageModel getWallertRecordByPageModel) {
                        if (getWallertRecordByPageModel.isSuccess()) {
                            getWallertRecordByPageModels = getWallertRecordByPageModel;
                            dialog.dismiss();
                            if (isrefreshormore == 1) {
                                lists.clear();
                                if (getWallertRecordByPageModel.getData().getRecords() != null && getWallertRecordByPageModel.getData().getRecords().size() > 0) {
                                    data.setVisibility(View.VISIBLE);
                                    noData.setVisibility(View.GONE);

                                    records = getWallertRecordByPageModel.getData().getRecords();
                                    for (int i = 0; i < records.size(); i++) {
                                        recordsBean = records.get(i);
                                        GetWallertRecordByPageModel.DataBean data = getWallertRecordByPageModel.getData();
                                        lists.add(data);
                                    }

                                    mListData.addAll(records);
                                } else {
                                    GetWallertRecordByPageModel.DataBean data = getWallertRecordByPageModel.getData();
                                    lists.add(data);
                                    GetWallertRecordByPageModel.DataBean.RecordsBean recordsBean = new GetWallertRecordByPageModel.DataBean.RecordsBean();
                                    recordsBean.setNullData(true);
                                    recordsBean.setDateTime(data.getNowYear() + "-" + data.getNowMonth());
                                    mListData.add(recordsBean);
                                }
                            } else {
                                if (getWallertRecordByPageModels.getData() != null && getWallertRecordByPageModels.getData().getRecords().size() > 0) {
                                    records = getWallertRecordByPageModel.getData().getRecords();
                                    for (int i = 0; i < records.size(); i++) {
                                        recordsBean = records.get(i);
                                        GetWallertRecordByPageModel.DataBean data = getWallertRecordByPageModel.getData();
                                        lists.add(data);
                                    }
                                    mListData.addAll(records);
                                    refreshLayout.finishLoadMore();
                                } else {
                                    adapters.notifyDataSetChanged();
                                    refreshLayout.finishLoadMore();
                                }
                            }

                            adapters.notifyDataSetChanged();

                        } else {
                            AppHelper.showMsg(MyWalletDetailActivity.this, getWallertRecordByPageModel.getMessage());
                        }
                    }
                });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    //  明细筛选
    private void setPopuwindow() {
        listPopuwindow.add("全部明细");
        listPopuwindow.add("收入明细");
        listPopuwindow.add("支出明细");
        popuwindow = new MyWallDetailPopuwindow(mContext, listPopuwindow, new MyWallDetailPopuwindow.ItemClick() {
            @Override
            public void GridviewOnClick(int position) {

                switch (position) {
                    case 0: // 全部明细
                        popuwindow.dismiss();
                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
                        textViewDetailed.setText("全部明细");
                        recordType = 0 + "";
                        pageNum = 1;
                        mListData.clear();
//                        getWallertRecord( year, month, phone, walletRecordChannelType, showType);
                        break;
                    case 1: // 收入明细
                        popuwindow.dismiss();
                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
                        textViewDetailed.setText("收入明细");
                        recordType = 2 + "";
                        pageNum = 1;
                        mListData.clear();
//                        getWallertRecord(year, month, phone, walletRecordChannelType, showType);
                        break;
                    case 2: //支出明细
                        popuwindow.dismiss();
                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
                        textViewDetailed.setText("支出明细");
                        recordType = 1 + "";
                        pageNum = 1;
                        mListData.clear();
//                        getWallertRecord(year, month, phone, walletRecordChannelType, showType);
                        break;
                }

            }
        });
    }

    private void setTime() {
        View hiddenView = getLayoutInflater().inflate(R.layout.view_date_picker, mLlTimeSelect, false); //hiddenView是隐藏的View，
        mLlTimeSelect.addView(hiddenView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        selectDate = now.split(" ")[0];
        mCpDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                selectDate = time.split(" ")[0];
                year = selectDate.split("-")[0];
                month = selectDate.split("-")[1];
                dialog.show();
                mListData.clear();
                getWallertRecord(recordType, year, month, null, showType, null);
                requsetPrice(recordType, year, month, phone, walletRecordChannelType, showType);
                mLlTimeSelect.setVisibility(View.GONE);

            }
        }, "2017-12-01 00:00", now, hiddenView); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCpDate.setCancleClickListener(new CustomDatePicker.OnCancleClickListener() {
            @Override
            public void onItemClick(View view) {
                mLlTimeSelect.setVisibility(View.GONE);
            }
        });
        mCpDate.showSpecificTime(false); // 不显示时和分
        mCpDate.setIsLoop(false); // 不允许循环滚动
    }

    /**
     * 账单价格总计
     */

    public void requsetPrice(String types, String year, String month, String phone, String walletRecordChannelType, int showType) {
        GetSumPriceAPI.requestSumPrice(mContext, types, year, month, phone, showType, walletRecordChannelType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetSumPriceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetSumPriceModel getSumPriceModel) {
                        if (getSumPriceModel.isSuccess()) {
                            tv_income.setText("收入" + "￥ " + String.valueOf(getSumPriceModel.getData().getIn()));
                            tv_expenditure.setText("支出" + "￥ " + String.valueOf(getSumPriceModel.getData().getOut()));
                        } else {

                            AppHelper.showMsg(mContext, getSumPriceModel.getMessage());


                        }

                    }
                });

    }
}
