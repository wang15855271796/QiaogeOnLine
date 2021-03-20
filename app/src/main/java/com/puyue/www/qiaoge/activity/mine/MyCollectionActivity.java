package com.puyue.www.qiaoge.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.XLinearLayoutManager;
import com.puyue.www.qiaoge.activity.CartActivity;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.adapter.mine.MyCollectionAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.mine.collection.DeleteCollectionAPI;
import com.puyue.www.qiaoge.api.mine.collection.MyCollectionListAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.MessageEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.collection.CollectionListModel;
import com.puyue.www.qiaoge.model.mine.collection.DeleteCollectionModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.scwang.smartrefresh.layout.constant.RefreshState.Loading;
import static com.scwang.smartrefresh.layout.constant.RefreshState.Refreshing;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyCollectionActivity extends BaseSwipeActivity implements View.OnClickListener {
    String pageSize = "10";
    int pageNum = 1;
    private ImageView mIvBack;
    private RecyclerView mRv;
    RelativeLayout rl_num;
    ImageView iv_carts;
    TextView tv_num;
    SmartRefreshLayout smart;
    private MyCollectionAdapter mAdapterMyCollection;
    private List<ProductNormalModel.DataBean.ListBean> mList = new ArrayList<>();
    private List<Integer> collectionId = new ArrayList<>();
    private Map<Integer, Boolean> isCheck = new HashMap<>();//存储选择状态
    private ProductNormalModel mModelCollectionList;
    private LinearLayout mLlAllSelect;
    private CheckBox mCbAllSelect;
    private TextView mTvDelete;
    private boolean isAllSelected = false;
    private int selectedNum = 0;//被选中的数量,一开始为0
    ImageView iv_no_data;
    private DeleteCollectionModel mModelDeleteCollection;
    private LinearLayout mLlControl;
    TextView tv_buy;
    TextView tv_all;
    TextView tv_manger;
    RelativeLayout rl_foot;
    AVLoadingIndicatorView lav_activity_loading;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_collection);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginsEvent(UpDateNumEvent7 event) {
        //刷新UI
        getCartNum();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void findViewById() {
        EventBus.getDefault().register(this);
        iv_no_data = (ImageView) findViewById(R.id.iv_no_data);
        tv_num = (TextView) findViewById(R.id.tv_num);
        rl_num = (RelativeLayout) findViewById(R.id.rl_num);
        lav_activity_loading = (AVLoadingIndicatorView) findViewById(R.id.lav_activity_loading);
        smart = (SmartRefreshLayout) findViewById(R.id.smart);
        rl_foot = (RelativeLayout) findViewById(R.id.rl_foot);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_manger = findViewById(R.id.tv_manger);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mRv = (RecyclerView) findViewById(R.id.rv_my_collection);
        mLlAllSelect = (LinearLayout) findViewById(R.id.ll_collection_all_select);
        mCbAllSelect = (CheckBox) findViewById(R.id.cb_collection_all_select);
        mTvDelete = (TextView) findViewById(R.id.tv_collection_delete);
        mLlControl = (LinearLayout) findViewById(R.id.ll_my_collection_control);
        getCartNum();
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                if(isChoose.equals("0")) {
                    requestCollectionList("0",pageNum,pageSize);
                }else {
                    requestCollectionList("1",pageNum,pageSize);
                }

                refreshLayout.finishRefresh();


            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (mModelCollectionList.getData()!=null) {
                    if(mModelCollectionList.getData().isHasNextPage()) {
                        pageNum++;
                        if(isChoose.equals("0")) {
                            requestCollectionList("0",pageNum,pageSize);
                        }else {
                            requestCollectionList("1",pageNum,pageSize);
                        }
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = false;
                mAdapterMyCollection.setIsShow(isShow);
                mAdapterMyCollection.notifyDataSetChanged();

                if(isShow) {
                    tv_manger.setText("完成");
                    rl_foot.setVisibility(View.VISIBLE);
                }else {
                    rl_foot.setVisibility(View.GONE);
                    tv_manger.setText("管理");
                }


                Intent intent = new Intent(mContext,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void finshResfesh() {

        RefreshState state = smart.getState();
        if (state.isOpening && state.isHeader) {
            smart.finishRefresh();
        } else if (state.isOpening && state.isFooter) {
            smart.finishLoadMore();
        }
    }

    /**
     * 获取角标数据
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {
                        if (getCartNumModel.isSuccess()) {
                            if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText(getCartNumModel.getData().getNum());
                            }else {
                                tv_num.setVisibility(View.GONE);
                            }
                        } else {
                            AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void setViewData() {
        mAdapterMyCollection = new MyCollectionAdapter(R.layout.item_my_collection, mList, isCheck, new MyCollectionAdapter.Onclick() {
            @Override
            public void addDialog() {

            }

            @Override
            public void getPrice() {

            }
        });
        mAdapterMyCollection.setOnItemClickListener(new MyCollectionAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(View view, int position, String type) {
                if (type.equals("check")) {
                    if (isCheck.get(position)) {
                        //如果取消，则设置map集合中为false
                        isCheck.put(position, false);
                    } else {
                        //如果选中，则设置map集合中为true
                        isCheck.put(position, true);
                    }
                    selectedNum = 0;
                    for (int i = 0; i < isCheck.size(); i++) {
                        if (isCheck.get(i)) {
                            selectedNum++;
                        }
                    }
                    if (selectedNum == mList.size()) {
                        //如果用户一个一个单选,选到全部商品,上面的全选自动选中
                        mCbAllSelect.setChecked(true);
                    } else {
                        mCbAllSelect.setChecked(false);
                    }
                    mAdapterMyCollection.notifyDataSetChanged();
                } else if (type.equals("jump")) {
                    Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, mList.get(position).getProductMainId());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    startActivity(intent);

                    }
            }

            @Override
            public void onEventLongClick(View view, int position, String type) {

            }
        });

        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapterMyCollection);
        mAdapterMyCollection.setEmptyView(AppHelper.getLoadingView(mContext));
        requestCollectionList("0",pageNum,pageSize);

        tv_buy.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_manger.setOnClickListener(this);
    }

    /**
     * 获取收藏列表
     */
    private void requestCollectionList(String state,int pageNum,String pageSize) {
        MyCollectionListAPI.requestCollectionList(mContext,state,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductNormalModel>() {
                    @Override
                    public void onCompleted() {
                        lav_activity_loading.hide();
                        lav_activity_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();
                        lav_activity_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ProductNormalModel collectionListModel) {
                        lav_activity_loading.setVisibility(View.VISIBLE);
                        lav_activity_loading.show();
                        mModelCollectionList = collectionListModel;
                        if (mModelCollectionList.isSuccess()) {
                            if(collectionListModel.getData()!=null) {
                                List<ProductNormalModel.DataBean.ListBean> lists = collectionListModel.getData().getList();

                                if(pageNum ==1) {
                                    mList.clear();
                                    mList.addAll(lists);
                                }else {
                                    mList.addAll(lists);
                                }

                                mAdapterMyCollection.notifyDataSetChanged();

                                isCheck.clear();
                                for (int i = 0; i < mList.size(); i++) {
                                    isCheck.put(i, false);
                                }


                                if(mList.size()>0) {
                                    iv_no_data.setVisibility(View.GONE);
                                }else {
                                    iv_no_data.setVisibility(View.VISIBLE);
                                }

                                if (lists.size() == 0) {
                                    AppHelper.cancleLottieAnimation(mAdapterMyCollection.getEmptyView());
                                    mAdapterMyCollection.setEmptyView(AppHelper.getEmptyView(mContext));
                                }

                                lav_activity_loading.hide();
                                lav_activity_loading.setVisibility(View.GONE);
                            }


//                            updateCollectionList();

                        } else {
                            AppHelper.showMsg(mContext, mModelCollectionList.getMessage());
                        }
                    }
                });
    }

    private void updateCollectionList() {

        if(mModelCollectionList.getData()!=null) {
            mLlControl.setVisibility(View.VISIBLE);
            List<ProductNormalModel.DataBean.ListBean> list = mModelCollectionList.getData().getList();
            mList.addAll(list);
            mAdapterMyCollection.setNewData(mList);
            mAdapterMyCollection.notifyDataSetChanged();
            isCheck.clear();
            for (int i = 0; i < mList.size(); i++) {
                isCheck.put(i, false);
            }
            if(mList.size()>0) {
                iv_no_data.setVisibility(View.GONE);
            }else {
                iv_no_data.setVisibility(View.VISIBLE);
            }

        } else {
            mLlControl.setVisibility(View.GONE);
            mAdapterMyCollection.notifyDataSetChanged();
        }

        if (mAdapterMyCollection.getData().size() == 0) {
            AppHelper.cancleLottieAnimation(mAdapterMyCollection.getEmptyView());
            mAdapterMyCollection.setEmptyView(AppHelper.getEmptyView(mContext));
        }

        lav_activity_loading.hide();
        lav_activity_loading.setVisibility(View.GONE);
    }

    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mLlAllSelect.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (isAllSelected) {
                    //正在被全选
                    mCbAllSelect.setChecked(false);
                    for (int i = 0; i < isCheck.size(); i++) {
                        isCheck.put(i, false);
                    }
                    mAdapterMyCollection.notifyDataSetChanged();
                    isAllSelected = false;
                } else {
                    //没有全选中
                    mCbAllSelect.setChecked(true);
                    for (int i = 0; i < isCheck.size(); i++) {
                        isCheck.put(i, true);
                    }
                    mAdapterMyCollection.notifyDataSetChanged();
                    isAllSelected = true;
                }
            }
        });
        mTvDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                collectionId.clear();
                for (int i = 0; i < isCheck.size(); i++) {
                    if (isCheck.get(i)) {
                        collectionId.add(mList.get(i).getProductId());
                    }
                }
                if (collectionId != null && collectionId.size() > 0) {
                    showDeleteCollection();
                } else {
                    AppHelper.showMsg(mActivity, "请先选中商品");
                }
            }
        });
    }

    private void showDeleteCollection() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_collection);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_dialog_delete_collection_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_dialog_delete_collection_confirm);
        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                deleteCollection(collectionId.toString());
                alertDialog.dismiss();

            }
        });
    }

    private void deleteCollection(String s) {
        DeleteCollectionAPI.requestDeleteCollection(mContext, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteCollectionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DeleteCollectionModel deleteCollectionModel) {
                        mModelDeleteCollection = deleteCollectionModel;
                        if (mModelDeleteCollection.success) {
                            //删除成功
                            AppHelper.showMsg(mContext, "删除成功");
                            EventBus.getDefault().post(new MessageEvent());
                            smart.autoRefresh();
                            mCbAllSelect.setChecked(false);
                        } else {
                            AppHelper.showMsg(mActivity, mModelDeleteCollection.message);
                        }
                    }
                });
    }

    boolean isShow;
    String isChoose = "0";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                pageNum = 1;
                smart.setNoMoreData(false);
                mList.clear();
                lav_activity_loading.setVisibility(View.VISIBLE);
                lav_activity_loading.show();
                mAdapterMyCollection.notifyDataSetChanged();
                requestCollectionList("0",pageNum,pageSize);
                tv_all.setTextColor(Color.parseColor("#333333"));
                tv_buy.setTextColor(Color.parseColor("#666666"));

                for (int i = 0; i < mList.size(); i++) {
                    isCheck.put(i, false);
                }
                isAllSelected = false;
                mCbAllSelect.setChecked(false);
                isChoose = "0";
                break;

            case R.id.tv_buy:
                pageNum = 1;
                smart.setNoMoreData(false);
                mList.clear();
                lav_activity_loading.setVisibility(View.VISIBLE);
                lav_activity_loading.show();
                mAdapterMyCollection.notifyDataSetChanged();
                requestCollectionList("1",pageNum,pageSize);
                tv_all.setTextColor(Color.parseColor("#666666"));
                tv_buy.setTextColor(Color.parseColor("#333333"));
                isChoose = "1";
                for (int i = 0; i < mList.size(); i++) {
                    isCheck.put(i, false);
                }
                isAllSelected = false;
                mCbAllSelect.setChecked(false);
                break;

            case R.id.tv_manger:
                isShow = !isShow;
                mAdapterMyCollection.setIsShow(isShow);
                mAdapterMyCollection.notifyDataSetChanged();

                if(isShow) {
                    tv_manger.setText("完成");
                    rl_foot.setVisibility(View.VISIBLE);
                }else {
                    rl_foot.setVisibility(View.GONE);
                    tv_manger.setText("管理");
                }


                break;
        }
    }
}
