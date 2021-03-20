package com.puyue.www.qiaoge.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.FullGiftAdapter;
import com.puyue.www.qiaoge.adapter.FullGiftsAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.TeamActiveQueryAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.model.home.TeamActiveQueryModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/8/18
 */
public class FullGiftActivity extends BaseSwipeActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    List<CouponModel.DataBean.ActivesBean> list = new ArrayList<>();
    FullGiftsAdapter fullGiftAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full);
    }

    @Override
    public void findViewById() {
        setTranslucentStatus();
    }

    @Override
    public void setViewData() {
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fullGiftAdapter = new FullGiftsAdapter(R.layout.item_full_gift,list);
        fullGiftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID, list.get(position).getProductMainId());
                intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(fullGiftAdapter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setClickEvent() {
        getSpikeList();
    }

    /**
     * 获取满赠列表
     */
    private void getSpikeList() {
        IndexHomeAPI.getCouponList(mActivity,12+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponModel>() {


                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CouponModel couponModel) {
                        if(couponModel.isSuccess()) {
                            if(couponModel.getData()!=null) {
                                List<CouponModel.DataBean.ActivesBean> actives = couponModel.getData().getActives();
                                list.addAll(actives);
                                fullGiftAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}
