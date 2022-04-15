package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.adapter.CouponUseAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.CouponListsModel;
import com.puyue.www.qiaoge.model.FullCouponListModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class CouponUseActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.smart)
    SmartRefreshLayout smartRefreshLayout;
    int pageNum = 1;
    int pageSize = 10;
    String type;
    String poolNo;
    String name;
    CouponUseAdapter couponUseAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        poolNo = getIntent().getStringExtra("poolNo");
        name = getIntent().getStringExtra("name");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coupon_use);
    }

    @Override
    public void findViewById() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        couponUseAdapter = new CouponUseAdapter(R.layout.item_coupon_use,list);
        recyclerView.setAdapter(couponUseAdapter);
        smartRefreshLayout.autoRefresh();
        if(type.equals("1")) {
            tv_desc.setText("以下商品可使用"+name);
        }else if(type.equals("2")) {
            tv_desc.setText("以下商品不可使用"+name);
        }



        couponUseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                intent.putExtra("activeId",list.get(position).getProductMainId());
                intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                startActivity(intent);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getFullList(poolNo);
                refreshLayout.finishRefresh();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(fullCouponListModels != null) {
                    if (fullCouponListModels.getData().isHasNextPage()) {
                        pageNum++;
                        getFullList(poolNo);
                        refreshLayout.finishLoadMore();      //加载完成
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    CouponListsModel fullCouponListModels;
    List<CouponListsModel.DataBean.ListBean> list = new ArrayList<>();
    private void getFullList(String poolNo) {
        IndexHomeAPI.getCouponsList(mContext,poolNo,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CouponListsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponListsModel fullCouponListModel) {
                        if(fullCouponListModel.getCode()==1) {
                            if(fullCouponListModel.getData()!=null) {
                                fullCouponListModels = fullCouponListModel;
                                list.addAll(fullCouponListModel.getData().getList());
                                couponUseAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,fullCouponListModel.getMessage());
                        }
                    }
                });
    }

}
