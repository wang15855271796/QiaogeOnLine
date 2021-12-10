package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.DateAdapter;
import com.puyue.www.qiaoge.adapter.DsrAdapter;
import com.puyue.www.qiaoge.api.market.MarketAlreadyGoodAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.FundMode;
import com.puyue.www.qiaoge.model.PriceTrendModel;
import com.puyue.www.qiaoge.model.TabEntity;
import com.puyue.www.qiaoge.model.market.MarketAlreadyGoodModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.view.FundView;
import com.puyue.www.qiaoge.view.NavigationBar;
import com.puyue.www.qiaoge.view.ViewPagerIndicator;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PriceTrendActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.fund)
    FundView fund;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_desc1)
    TextView tv_desc1;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_spec)
    TextView tv_spec;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_price)
    TextView tv_price;
    String priceId;
    String productId;
    int month = 1;
    String date[]  = {"近1月","近3月","近6月","近一年"};
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_price_trend);
    }

    @Override
    public void findViewById() {

    }

    int pos;
    @Override
    public void setViewData() {
        priceId = getIntent().getStringExtra("priceId");
        productId = getIntent().getStringExtra("productId");
        getTrends(priceId,productId,month);
        List<String> dates = Arrays.asList(date);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        DateAdapter dateAdapter = new DateAdapter(R.layout.item_date,dates );
        recyclerView.setAdapter(dateAdapter);
        dateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dateAdapter.setSelectionPosition(position);
                dateAdapter.notifyDataSetChanged();
                pos = position;
                if(position==0) {
                    month = 1;
                }else if(position ==1) {
                    month = 3;
                }else if(position ==2) {
                    month = 6;
                }else {
                    month = 12;
                }
                getTrends(priceId,productId,month);
            }
        });
        fund.setBasePaddingLeft(80);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    List<PriceTrendModel.DataBean.TrendsBean> trends;
    private void getTrends(String priceId,String productId,int month) {
        MarketAlreadyGoodAPI.getTrends(mActivity, priceId, productId,month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PriceTrendModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PriceTrendModel priceTrendModel) {
                        if (priceTrendModel.getCode()==1) {
                            trends = priceTrendModel.getData().getTrends();
                            adapterData(trends);
                            PriceTrendModel.DataBean data = priceTrendModel.getData();
                            tv_title.setText(data.getProductName());
                            tv_spec.setText(data.getSpec());
                            tv_date.setText(data.getDateTime());
                            tv_price.setText(data.getSalePrice());
                            if(data.getUpOrDown()==0) {
                                tv_desc.setVisibility(View.VISIBLE);
                                tv_desc1.setVisibility(View.GONE);
                                tv_desc.setText(Arrays.asList(date).get(pos)+"涨跌幅"+data.getQuoteChange());
                            }else{
                                tv_desc1.setVisibility(View.VISIBLE);
                                tv_desc.setVisibility(View.GONE);
                                tv_desc1.setText(Arrays.asList(date).get(pos)+"涨跌幅"+data.getQuoteChange());
                            }
                        } else {
                            AppHelper.showMsg(mActivity, priceTrendModel.getMessage());
                        }

                    }
                });
    }

    private void adapterData(List<PriceTrendModel.DataBean.TrendsBean> originFundModeList) {
        List<FundMode> fundModeList = new ArrayList<>();//适配后的数据

        for (PriceTrendModel.DataBean.TrendsBean originFundMode : originFundModeList) {
            FundMode fundMode = new FundMode(DateUtils.getLong(originFundMode.getDateTime(),"yyyy-MM-dd"), originFundMode.getPrice());
            fundModeList.add(fundMode);
        }

        fund.setDataList(fundModeList);
    }





}
