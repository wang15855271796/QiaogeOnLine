package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.Coupon1Adapter;
import com.puyue.www.qiaoge.adapter.Coupon4Adapter;
import com.puyue.www.qiaoge.adapter.FullGoodsAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.model.FullDetailModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FullActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_404)
    ImageView iv_404;
    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    List<FullDetailModel.DataBean.SendGiftsBean> sendGifts;
    List<FullDetailModel.DataBean.SendGiftsBean> list1 = new ArrayList<>();
    List<FullDetailModel.DataBean.SendGiftsBean> list2 = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_lists);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
            iv_404.setImageResource(R.mipmap.ic_404);
            iv_404.setVisibility(View.VISIBLE);
            ll_root.setVisibility(View.GONE);
        }else {
            ll_root.setVisibility(View.VISIBLE);
            iv_404.setImageResource(R.mipmap.ic_no_data);
        }

        sendGifts = (List<FullDetailModel.DataBean.SendGiftsBean>) getIntent().getSerializableExtra("sendGifts");

        for (int i = 0; i < sendGifts.size(); i++) {
            if(sendGifts.get(i).getType()==0) {
                //赠品
                list1.add(sendGifts.get(i));
            }else {
                list2.add(sendGifts.get(i));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FullGoodsAdapter fullGoodsAdapter = new FullGoodsAdapter(R.layout.item_fulls_cart,list1);
        recyclerView.setAdapter(fullGoodsAdapter);
        fullGoodsAdapter.notifyDataSetChanged();

        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        Coupon4Adapter coupon4Adapter = new Coupon4Adapter(R.layout.item_coupons,list2);
        recycler.setAdapter(coupon4Adapter);
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
}
