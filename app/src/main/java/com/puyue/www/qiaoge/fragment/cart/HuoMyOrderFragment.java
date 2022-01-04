package com.puyue.www.qiaoge.fragment.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HuoDetailActivity;
import com.puyue.www.qiaoge.adapter.MyHuoAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.fragment.home.UnOperateFragment;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.HuoListModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoMyOrderFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    String type;
    MyHuoAdapter myHuoAdapter;
    public static Fragment getInstance(String type) {
        HuoMyOrderFragment fragment = new HuoMyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void setViewData() {
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
        smart.autoRefresh();
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getHuoList(pageSize,pageNum,type);
                refreshLayout.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(data!= null) {
                    if(data.isHasNextPage()) {
                        pageNum++;
                        getHuoList(pageSize,pageNum,type);
                        refreshLayout.finishLoadMore();      //加载完成
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        myHuoAdapter = new MyHuoAdapter(R.layout.item_my_huo,list,type);
        recyclerView.setAdapter(myHuoAdapter);

        myHuoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, HuoDetailActivity.class);
                intent.putExtra("id",data.getList().get(position).getOrder_display_id());
                startActivity(intent);
            }
        });


    }

    @Override
    public void setClickEvent() {

    }

    //订单列表
    int pageNum = 1;
    int pageSize = 10;
    List<HuoListModel.DataBean.ListBean> list = new ArrayList<>();
    HuoListModel.DataBean data;
    private void getHuoList(int pageSize,int pageNum,String type) {
        HuolalaAPI.getHuoList(mActivity,pageSize,pageNum,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoListModel huoListModel) {
                        if(huoListModel.getCode()==1) {
                            if(huoListModel.getData().getList()!=null&&huoListModel.getData().getList().size()>0) {
                                data = huoListModel.getData();
                                list.clear();
                                list.addAll(huoListModel.getData().getList());

                                myHuoAdapter.notifyDataSetChanged();
                                iv_no_data.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);


                            }else {
                                recyclerView.setVisibility(View.GONE);
                                iv_no_data.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoListModel.getMessage());
                        }
                    }
                });
    }



}
