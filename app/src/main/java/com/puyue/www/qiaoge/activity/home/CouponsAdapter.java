package com.puyue.www.qiaoge.activity.home;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.TeamActiveQueryModel;

import java.util.List;

/**
 * Created by ${王涛} on 2019/12/25
 */
public class CouponsAdapter extends BaseQuickAdapter<TeamActiveQueryModel.DataBean,BaseViewHolder> {

    RecyclerView recyclerView;
    Onclick onclick;
    public CouponsAdapter(int layoutResId, @Nullable List<TeamActiveQueryModel.DataBean> data,Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamActiveQueryModel.DataBean item) {

        recyclerView = helper.getView(R.id.recyclerView);
        helper.setText(R.id.tv_time,item.getTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        CouponsInnerAdapter couponsInnerAdapter = new CouponsInnerAdapter(R.layout.coupon_inner,item.getActives(),onclick);
        recyclerView.setAdapter(couponsInnerAdapter);



    }

    public interface Onclick {
        void addDialog();
    }

}
