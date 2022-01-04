package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoDetailModel;

import java.util.List;

public class HuoPayedAdapter extends BaseQuickAdapter<HuoDetailModel.DataBean.PriceInfoBean, BaseViewHolder> {
    List<HuoDetailModel.DataBean.PriceInfoBean> data;
    boolean isOpen = true;
    List<Integer> payPriceList;
    public HuoPayedAdapter(int layoutResId, @Nullable List<HuoDetailModel.DataBean.PriceInfoBean> data, List<Integer> payPriceList) {
        super(layoutResId, data);
        this.data = data;
        this.payPriceList = payPriceList;
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoDetailModel.DataBean.PriceInfoBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        TextView tv_payed = helper.getView(R.id.tv_payed);
        TextView tv_payed_money = helper.getView(R.id.tv_payed_money);
        int payStatus = item.getPayStatus();
        getState(payStatus,tv_payed);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        HuoPayAdapter huoPayAdapter = new HuoPayAdapter(R.layout.item_huo_pay,data);
        recyclerView.setAdapter(huoPayAdapter);
        tv_payed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpen) {
                    isOpen = true;
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    isOpen = false;
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });


    }

    private void getState(int payStatus, TextView tv_payed) {
        switch (payStatus) {
            case 0:
                tv_payed.setText("未支付");
                break;

            case 1:
                tv_payed.setText("已支付");
                break;

            case 2:
                tv_payed.setText("支付失败");
                break;

            case 3:
                tv_payed.setText("退款中");
                break;

            case 4:
                tv_payed.setText("退款成功");
                break;

            case 5:
                tv_payed.setText("支付中");
                break;

            case 7:
                tv_payed.setText("申诉中");
                break;

        }
    }
}
