package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.adapter.FullActiveAdapter;
import com.puyue.www.qiaoge.adapter.FullGivenAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.CouponFullListDialog;
import com.puyue.www.qiaoge.event.FullListModel;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent11;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullActiveActivity extends BaseSwipeActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_full_given)
    RecyclerView rv_full_given;
    @BindView(R.id.tv_roll)
    TextView tv_roll;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.ll_tips)
    LinearLayout ll_tips;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    int fullId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_active);
    }

    @Override
    public void findViewById() {
        setTranslucentStatus();
    }

    FullActiveAdapter fullActiveAdapter;
    FullGivenAdapter fullGivenAdapter;
    @Override
    public void setViewData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fullId = getIntent().getIntExtra("fullId",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fullActiveAdapter = new FullActiveAdapter(R.layout.item_active_full_list,list);
        recyclerView.setAdapter(fullActiveAdapter);

        fullGivenAdapter = new FullGivenAdapter(R.layout.item_full_desc,sendGifts);
        rv_full_given.setLayoutManager(new LinearLayoutManager(mContext));
        rv_full_given.setAdapter(fullGivenAdapter);
        getOrder();


        fullGivenAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if(sendGifts.get(position).getType()==0) {
                    Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                    intent.putExtra("activeId",sendGifts.get(position).getProductMainId());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                    startActivity(intent);
                }else {
                    CouponFullListDialog couponFullListDialog = new CouponFullListDialog(mContext,sendGifts.get(position).getPoolNo());
                    couponFullListDialog.show();
                }
            }
        });

        fullActiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra("activeId",list.get(position).getProductMainId());
                intent.putExtra("priceType", SharedPreferencesUtil.getString(mActivity, "priceType"));
                startActivity(intent);
            }
        });

        tv_buy.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void setClickEvent() {

    }

    List<FullDetailModel.DataBean.ProdsBean> list = new ArrayList<>();

    List<FullDetailModel.DataBean.SendGiftsBean> sendGifts = new ArrayList<>();
    private void getOrder() {
        IndexHomeAPI.getFullDetail(mActivity,fullId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<FullDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FullDetailModel fullDetailModel) {
                        if(fullDetailModel.getCode()==1) {
                            sendGifts.clear();
                            list.clear();
                            if(fullDetailModel.getData()!=null) {
                                FullDetailModel.DataBean data = fullDetailModel.getData();
                                tv_roll.setText(data.getRoleDesc());
                                if(!data.getTips().getTips().equals("")&&data.getTips().getTips()!=null) {
                                    ll_tips.setVisibility(View.VISIBLE);
                                    tv_tip.setText(data.getTips().getTips());
                                }else {
                                    ll_tips.setVisibility(View.GONE);
                                }
                                if(data.getTips().getRoleType() ==0) {
                                    tv_total.setText(data.getTips().getBuyNum());
                                    tv_total_price.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.VISIBLE);
                                }else {
                                    tv_total.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.GONE);
                                }

                                sendGifts.addAll(data.getSendGifts());
                                list.addAll(data.getProds());
                                fullGivenAdapter.notifyDataSetChanged();
                                fullActiveAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,fullDetailModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_buy:
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
                EventBus.getDefault().post(new GoToCartFragmentEvent());
                finish();
                break;

            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartPoductNum(UpDateNumEvent11 event) {
        //刷新UI
        getOrder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
