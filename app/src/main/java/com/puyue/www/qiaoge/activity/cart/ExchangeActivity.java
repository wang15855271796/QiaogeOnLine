package com.puyue.www.qiaoge.activity.cart;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CouponListssAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.BigDecimalUtils;
import com.puyue.www.qiaoge.model.cart.ExChangeModel;
import com.puyue.www.qiaoge.model.cart.ItemModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2020/6/26
 */
public class ExchangeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.bt_sure)
    TextView bt_sure;
    @BindView(R.id.tv_consume)
    TextView tv_consume;
    @BindView(R.id.tv_exchange)
    TextView tv_exchange;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    private List<ItemModel> list = new ArrayList<>();
    private CouponListssAdapter couponListsAdapter;
    private ArrayList<ItemModel> mDatas = new ArrayList<>();
    List<ExChangeModel.DetailListBean> detailListBeans = new ArrayList<>();
    private String amount;
    public List<Double> amounts = new ArrayList<>();
    private Double Amount = 0.0;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(detailListBeans!=null) {
            detailListBeans.clear();
        }

    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        amount = getIntent().getStringExtra("amount");
        iv_back.setOnClickListener(this);
        list = initData();
        couponListsAdapter = new CouponListssAdapter(R.layout.item_coupon,list,amount,mActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(couponListsAdapter);
        tv_add.setOnClickListener(this);
        tv_exchange.setOnClickListener(this);
        bt_sure.setOnClickListener(this);
        tv_amount.setText(amount);

        couponListsAdapter.setOnArticleClickListener(new CouponListssAdapter.OnArticleClickListener() {

            @Override
            public void onArticleOnItemClick(EditText et) {
                Amount = 0.0;
                for (int i = 0; i < couponListsAdapter.getItemCount(); i++) {
                    String amount = couponListsAdapter.getItem(i).getNum();
                    if (TextUtils.isEmpty(amount)) {
                        amount = "0";
                    }
                    Amount = BigDecimalUtils.add(Double.parseDouble(amount),Amount);
                }
                tv_consume.setText("消费金额"+Amount+"元");
                tv_amount.setText((BigDecimalUtils.sub(Double.parseDouble(amount),Amount))+"");
                Double amounts = Double.valueOf(amount);
                if(Amount-amounts==0.0) {
                    tv_amount.setText(0+"");
                    tv_consume.setText("消费金额"+amount+"元");
                }else if(BigDecimalUtils.sub(Amount,amounts)>0.0){
                    tv_amount.setText(0+"");
                    tv_consume.setText("消费金额"+amount+"元");
                }
            }
        });

    }

    private List<ItemModel> initData() {
        for (int i = 0; i < 1; i++) {
            mDatas.add(new ItemModel());
        }
        return mDatas;
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.bt_sure:
                detailListBeans.clear();
                couponListsAdapter.addDatas(tv_consume);

                break;
            case R.id.tv_add:
                if(tv_amount.getText().toString().equals("0")) {
                    ToastUtil.showSuccessMsg(mActivity,"兑换金额已为0");
                    return;
                }else {
                    couponListsAdapter.addData(new ItemModel(),amount);
                }

                break;

            case R.id.tv_exchange:
                if(Utils.isFastClick()) {
                    return;
                }else {
                    if(amount.equals("0.0")) {
                        ToastUtil.showSuccessMsg(mActivity,"余额不足,无法兑换");
                    }else {
                        couponListsAdapter.setCoupon(amount);

                    }
                }



                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
