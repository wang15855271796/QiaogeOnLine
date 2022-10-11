package com.puyue.www.qiaoge.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;

import java.util.ArrayList;
import java.util.List;

public class UnOperate1Adapter extends BaseQuickAdapter<CartBalanceModel.DataBean.ProductVOListBean, BaseViewHolder> {

    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList1 = new ArrayList<>();
    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList2 = new ArrayList<>();

    public UnOperate1Adapter(int layoutResId, @Nullable List<CartBalanceModel.DataBean.ProductVOListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBalanceModel.DataBean.ProductVOListBean item) {
        TextView textTitle = helper.getView(R.id.textTitle);
        TextView Price = helper.getView(R.id.Price);
        TextView oldPrice = helper.getView(R.id.oldPrice);
        TextView textSpe = helper.getView(R.id.textSpe);
        ImageView imageIcon = helper.getView(R.id.imageIcon);
        RecyclerView rv_given = helper.getView(R.id.rv_given);
        RecyclerView rv_full = helper.getView(R.id.rv_full);
        ImageView imageView =  helper.getView(R.id.imageView);
        Glide.with(mContext).load(item.getPicUrl()).into(imageView);
        if(item.getProdTypeUrl()!=null&&!item.getProdTypeUrl().equals("")) {
            imageIcon.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getProdTypeUrl()).into(imageIcon);
        }else {
            imageIcon.setVisibility(View.GONE);
        }
        imageIcon.setVisibility(View.GONE);

        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        oldPrice.getPaint().setAntiAlias(true);//抗锯齿

        textSpe.setText(item.getSpec());
        Price.setText(item.getAmount()+"");
        additionVOList1.clear();
        additionVOList2.clear();

        if(item.getAdditionVOList()!=null) {

            for (int i = 0; i < item.getAdditionVOList().size(); i++) {
                if(item.getAdditionVOList().get(i).getType().equals("1")) {
                    additionVOList1.add(item.getAdditionVOList().get(i));
                }else {
                    additionVOList2.add(item.getAdditionVOList().get(i));
                }
            }
        }

        rv_given.setLayoutManager(new LinearLayoutManager(mContext));
        FullGivenConfirmAdapter fullGivenConfirmAdapter = new FullGivenConfirmAdapter(R.layout.item_given,additionVOList2);
        rv_given.setAdapter(fullGivenConfirmAdapter);

        rv_full.setLayoutManager(new LinearLayoutManager(mContext));
        FullConfirmAdapter fullConfirmAdapter = new FullConfirmAdapter(R.layout.item_full,additionVOList1);
        rv_full.setAdapter(fullConfirmAdapter);

        if(item.getOldPrice()!=null) {
            oldPrice.setText(item.getOldPrice()+"");
        }
        textTitle.setText(item.getName());
    }
}
