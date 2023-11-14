package com.puyue.www.qiaoge.fragment.mine.coupons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.mine.coupon.MyCouponsAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${daff} on 2018/9/20
 * 我的优惠券界面已使用
 */
public class CouponsUseFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private PtrClassicFrameLayout ptrClassicFrameLayout ;
    private MyCouponsUsedAdapter adapter;
    private int pageNum = 1;
    ImageView iv_no_data;
//    private LinearLayout data;
//    private  LinearLayout noData;
    TextView tv_desc;
    private List<queryUserDeductByStateModel.DataBean.ListBean > lists =new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.fragment_cupons_overdue;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {
        tv_desc = view.findViewById(R.id.tv_desc);
        recyclerView=view.findViewById(R.id.recyclerView);
        iv_no_data = view.findViewById(R.id.iv_no_data);
//        data= view .findViewById(R.id.data);
//        noData= view.findViewById(R.id.noData);
        ptrClassicFrameLayout=view.findViewById(R.id.ptrClassicFrameLayout);
    }

    @Override
    public void setViewData() {
        pageNum = 1;
        requestMyCoupons();
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                requestMyCoupons();
            }
        });

        adapter = new MyCouponsUsedAdapter(R.layout.item_my_coupons,lists,getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    ptrClassicFrameLayout.setEnabled(false);
                } else {
                    ptrClassicFrameLayout.setEnabled(true);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                requestMyCoupons();
            }
        }, recyclerView);
    }

    @Override
    public void setClickEvent() {

    }


    private void requestMyCoupons() {
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
            iv_no_data.setImageResource(R.mipmap.ic_404);
            iv_no_data.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            iv_no_data.setImageResource(R.mipmap.ic_no_data);
            MyCouponsAPI.requestCoupons(getActivity(), pageNum, 10,"USED")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<queryUserDeductByStateModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(queryUserDeductByStateModel info) {
                            ptrClassicFrameLayout.refreshComplete();
                            if (info.isSuccess()) {
                                updateNoticeList(info);
                            } else {
                                AppHelper.showMsg(getContext(), info.getMessage());
                            }
                        }
                    });
        }

    }

    private void updateNoticeList(queryUserDeductByStateModel info) {

        if (pageNum == 1) {
            if (info.getData() != null && info.getData().getList().size() > 0) {
                iv_no_data.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                lists.clear();
                lists.addAll(info.getData().getList());
                adapter.notifyDataSetChanged();
            } else {
                recyclerView.setVisibility(View.GONE);
                iv_no_data.setVisibility(View.VISIBLE);
                tv_desc.setText("您还没有使用优惠券哦");
            }

        } else {
            lists.addAll(info.getData().getList());
            adapter.notifyDataSetChanged();
        }
        if (info.getData().isHasNextPage()) {
            //还有下一页数据
            adapter.loadMoreComplete();
        } else {
            //没有下一页数据了
            adapter.loadMoreEnd();
        }
    }


}
