package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.tools.ToastUtils;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.PointShopAdapter;
import com.puyue.www.qiaoge.api.mine.PointApI;
import com.puyue.www.qiaoge.api.mine.PointShopModel;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.wallet.MinerIntegralModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JiFenShopActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jifen_shop);
    }

    @Override
    public void findViewById() {

    }

    PointShopAdapter shopAdapter;
    @Override
    public void setViewData() {
        iv_back.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        shopAdapter = new PointShopAdapter(R.layout.item_point,deducts);
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                exchangePoint(deducts.get(position).getPoolNo());
            }
        });

        getPointList();
    }

    @Override
    public void setClickEvent() {

    }

    // 获取积分
    List<PointShopModel.DataBean.DeductsBean> deducts = new ArrayList<>();
    private void getPointList() {
        PointApI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PointShopModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PointShopModel pointShopModel) {
                        if(pointShopModel.getCode()==1) {
                            if(pointShopModel.getData()!=null) {
                                if(pointShopModel.getData().getDeducts()!=null && pointShopModel.getData().getDeducts().size()>0) {
                                    deducts.addAll(pointShopModel.getData().getDeducts());
                                    tv_point.setText(pointShopModel.getData().getUserPoint());
                                    shopAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });
    }

    private void exchangePoint(String poolNo) {
        PointApI.exchangePoint(mContext,poolNo)
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
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
