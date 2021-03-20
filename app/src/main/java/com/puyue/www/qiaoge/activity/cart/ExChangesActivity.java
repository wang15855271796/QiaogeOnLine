package com.puyue.www.qiaoge.activity.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CouponListssAdapter;
import com.puyue.www.qiaoge.adapter.CouponListsss;
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
 * Created by ${王涛} on 2020/8/10
 */
public class ExChangesActivity extends BaseSwipeActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.bt_sure)
    Button bt_sure;
    @BindView(R.id.tv_consume)
    TextView tv_consume;
    @BindView(R.id.tv_exchange)
    TextView tv_exchange;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    private List<ItemModel> list = new ArrayList<>();
    private CouponListsss couponListsAdapter;
    private ArrayList<ItemModel> mDatas = new ArrayList<>();
    List<ExChangeModel.DetailListBean> detailListBeans = new ArrayList<>();
    private String amount;
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
        list.add(new ItemModel(""));
        couponListsAdapter = new CouponListsss(R.layout.item_coupon,list,mActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(couponListsAdapter);
        iv_add.setOnClickListener(this);
        tv_exchange.setOnClickListener(this);
        bt_sure.setOnClickListener(this);
        tv_amount.setText(amount);

        couponListsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                couponListsAdapter.remove(position);
                couponListsAdapter.notifyDataSetChanged();
                Log.e("删除了一组数据", couponListsAdapter.getItemCount() + "");
                Amount = 0.0;
                for (int i = 0; i < couponListsAdapter.getItemCount(); i++) {
                    String amount = couponListsAdapter.getItem(i).getNum();
                    Log.e("取到的值", amount);
                    if (TextUtils.isEmpty(amount)) {
                        amount = "0";
                    }
                    Amount = Double.parseDouble(amount) + Amount;
                }
            }

        });

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

    @Override
    public void setViewData() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

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
                couponListsAdapter.getListData();
                break;

            case R.id.iv_add:
                couponListsAdapter.setOnclick(amount);

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
}
