package com.puyue.www.qiaoge.activity;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.OpenShopAdapter;
import com.puyue.www.qiaoge.api.home.SchoolVideoApi;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.OpenShopInfoModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class OpenShopActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    OpenShopAdapter openShopAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop);
    }

    @Override
    public void findViewById() {
        getOpenShopInfo();
    }

    @Override
    public void setViewData() {
        iv_back.setOnClickListener(this);
        iv_back1.setOnClickListener(this);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0) {
                    rl_title.setVisibility(View.GONE);
                }else {
                    rl_title.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        openShopAdapter = new OpenShopAdapter(R.layout.item_open_shop,shops);
        recyclerView.setAdapter(openShopAdapter);

        openShopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(shops.get(position).getCompanyType() ==0) {
                    //城市合伙人
                    Intent cityIntent = new Intent(mContext, CityWareHouseActivity.class);
                    mContext.startActivity(cityIntent);
                }else if(shops.get(position).getCompanyType() ==1) {
                    //前置仓
                    Intent frontIntent = new Intent(mContext, FrontWareHouseActivity.class);
                    mContext.startActivity(frontIntent);
                }else {
                    //成为供应商
                    Intent providerIntent = new Intent(mContext, ProviderWareHouseActivity.class);
                    mContext.startActivity(providerIntent);
                }
            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    List<OpenShopInfoModel.DataBean.ShopsBean> shops = new ArrayList<>();
    private void getOpenShopInfo() {
        SchoolVideoApi.getOpenShopInfo(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<OpenShopInfoModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OpenShopInfoModel openShopInfoModel) {
                        if (openShopInfoModel.getCode()==1) {
                            if(openShopInfoModel.getData()!=null) {
                                OpenShopInfoModel.DataBean data = openShopInfoModel.getData();
                                Glide.with(mContext).load(data.getBanner()).into(iv_top);
                                shops.addAll(data.getShops());
                                openShopAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,openShopInfoModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.iv_back1:
                finish();
                break;

        }
    }
}
