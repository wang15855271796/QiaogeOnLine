package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletDetailActivity;
import com.puyue.www.qiaoge.adapter.RechargeAdapter;
import com.puyue.www.qiaoge.adapter.ShopImageViewssAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_coupon)
    RecyclerView rv_coupon;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.tv_agree)
    TextView tv_agree;
    @BindView(R.id.ll_header)
    RelativeLayout ll_header;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.tv_amount_detail)
    TextView tv_amount_detail;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    List<String> list = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_wallet);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));
//        BillCouponAdapter billCouponAdapter = new BillCouponAdapter(R.layout.item_bill_coupon);
//        rv_coupon.setAdapter(billCouponAdapter);

        for (int i = 0; i < 15; i++) {
            list.add(i+"");
        }
//        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
//        RechargeAdapter rechargeAdapter = new RechargeAdapter(R.layout.item_recharge_coupon,list);
//        recyclerView.setAdapter(rechargeAdapter);

        RechargeAdapter rechargeAdapter = new RechargeAdapter(mActivity,list);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(rechargeAdapter);

        rechargeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                rechargeAdapter.setPos(position);
            }
        });

        iv_back.setOnClickListener(this);

        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(editable.toString().trim().length() == 0) {
//                    et_amount.setBackgroundResource(R.drawable.shape_grey4);
//                }else {
//                    et_amount.setBackgroundResource(R.drawable.shape_pink7);
//                }
            }
        });

        et_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    et_amount.setBackgroundResource(R.drawable.shape_pink7);
                }else {
                    et_amount.setBackgroundResource(R.drawable.shape_grey4);
                }
            }
        });

        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", "");
                intent.putExtra("name","");
                startActivity(intent);
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY!=0) {
                    ll_header.setVisibility(View.VISIBLE);
                }else {
                    ll_header.setVisibility(View.GONE);
                }
            }
        });

        tv_amount_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyWalletDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

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
