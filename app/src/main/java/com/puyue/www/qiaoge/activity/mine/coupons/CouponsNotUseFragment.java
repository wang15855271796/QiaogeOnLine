package com.puyue.www.qiaoge.activity.mine.coupons;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.RoleAdapter;
import com.puyue.www.qiaoge.adapter.coupon.MyCouponsAdapter;
import com.puyue.www.qiaoge.api.mine.coupon.MyCouponsAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * Created by ${王涛} on 2020/8/7我的优惠券界面（未使用）
 */
public class CouponsNotUseFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private PtrClassicFrameLayout ptrClassicFrameLayout ;
    private RoleAdapter roleAdapter;
    private int pageNum = 1;
    private LinearLayout data;
    private  LinearLayout noData;
    private RecyclerView rv_role;
    TextView tv_desc;
    MyCouponsAdapter adapter;
    private List<queryUserDeductByStateModel.DataBean.ListBean > lists =new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.fragment_cupons_overdue;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void findViewById(View view) {
        EventBus.getDefault().register(this);
        rv_role = view.findViewById(R.id.rv_role);
        tv_desc = view.findViewById(R.id.tv_desc);
        recyclerView=view.findViewById(R.id.recyclerView);
        data= view .findViewById(R.id.data);
        noData= view.findViewById(R.id.noData);
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
                lists.clear();
                requestMyCoupons();
                adapter.notifyDataSetChanged();
            }
        });

//        rv_role.setLayoutManager(new LinearLayoutManager(mActivity));
//        roleAdapter = new RoleAdapter(R.layout.item_text1,lists);
//        rv_role.setAdapter(adapter);
        adapter = new MyCouponsAdapter(R.layout.item_my_coupons,lists,getActivity());
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
        MyCouponsAPI.requestCoupons(getActivity(), pageNum, 10,"ENABLED")
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

    private void updateNoticeList(queryUserDeductByStateModel info) {

        if (pageNum == 1) {
            if (info.getData() != null && info.getData().getList().size() > 0) {
                data.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                lists.addAll(info.getData().getList());
                adapter.notifyDataSetChanged();
            } else {
                data.setVisibility(View.GONE);
                tv_desc.setText("您还没有优惠券可以使用哦");
                noData.setVisibility(View.VISIBLE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getList(CityEvent cityEvent) {
//        requestMyCoupons();
        ptrClassicFrameLayout.autoRefresh();
    }

}
