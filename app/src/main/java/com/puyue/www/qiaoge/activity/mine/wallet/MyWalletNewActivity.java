package com.puyue.www.qiaoge.activity.mine.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.cart.ExChangesActivity;
import com.puyue.www.qiaoge.activity.cart.ExchangeActivity;
import com.puyue.www.qiaoge.adapter.RemainAdapter;
import com.puyue.www.qiaoge.api.mine.GetMyBalanceAPI;
import com.puyue.www.qiaoge.api.mine.GetWallertRecordByPageAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.BackEvent;
import com.puyue.www.qiaoge.event.ExBackEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.mine.GetMyBalanceModle;
import com.puyue.www.qiaoge.model.mine.GetWallertRecordByPageModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.umeng.socialize.utils.ContextUtil.getContext;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${daff}
 * on 2018/10/16
 * 备注 我的钱包
 */
public class MyWalletNewActivity extends BaseSwipeActivity {
    private ImageView imageViewBack;
    GetMyBalanceModle getMyBalanceModles;
    private TextView tv_amount;
    RecyclerView recyclerView;
    private RelativeLayout relative_account_detail;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_wallet_new);
    }

    @Override
    public void findViewById() {
        EventBus.getDefault().register(this);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        relative_account_detail = (RelativeLayout) findViewById(R.id.relative_account_detail);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    /**
     * 兑换优惠券返回刷新
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exCoupon(ExBackEvent event) {
        requestGoodsList();

    }

    @Override
    public void setViewData() {
        requestGoodsList();
    }

    RemainAdapter remainAdapter;
    @Override
    public void setClickEvent() {
        imageViewBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });

        getWalletRecord("","","","",0,"1");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        remainAdapter = new RemainAdapter(R.layout.item_remain,lists);
        recyclerView.setAdapter(remainAdapter);

        remainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, MyCountDetailActivity.class);
                intent.putExtra("id", lists.get(position).getId());
                intent.putExtra("type", lists.get(position).getType());
                mContext.startActivity(intent);
            }
        });


    }

    private void requestGoodsList() {
        GetMyBalanceAPI.requestGetMyBalance(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetMyBalanceModle>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetMyBalanceModle getMyBalanceModle) {
                        if (getMyBalanceModle.isSuccess()) {
                            getMyBalanceModles = getMyBalanceModle;
                            tv_amount.setText(getMyBalanceModle.getData().getAmount());

                            relative_account_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent =new Intent(mContext,ExchangeActivity.class);
                                    intent.putExtra("amount",getMyBalanceModles.getData().getAmount());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    List<GetWallertRecordByPageModel.DataBean.RecordsBean> lists = new ArrayList();

    private void getWalletRecord(String types, String year, String month, String phone, int showType, String walletRecordChannelType) {
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
                        if(getWallertRecordByPageModel.getCode()==1) {
                            if(getWallertRecordByPageModel.getData()!=null && getWallertRecordByPageModel.getData().getRecords()!=null
                            &&getWallertRecordByPageModel.getData().getRecords().size()>0) {
                                lists.addAll(getWallertRecordByPageModel.getData().getRecords());
                                remainAdapter.notifyDataSetChanged();
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,getWallertRecordByPageModel.getMessage());
                        }
                    }
                });
    }

}
