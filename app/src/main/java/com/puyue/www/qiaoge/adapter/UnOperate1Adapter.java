package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UnOperate1Adapter extends RecyclerView.Adapter<UnOperate1Adapter.MyHolder> {

    private Activity context;
    private List<CartBalanceModel.DataBean.ProductVOListBean> list;
    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList1 = new ArrayList<>();
    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList2 = new ArrayList<>();
    public UnOperate1Adapter(Activity context) {
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_confirm_order_new, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        CartBalanceModel.DataBean.ProductVOListBean productVOListBean = list.get(position);
        holder.textTitle.setText(productVOListBean.getName());
        holder.Price.setText(productVOListBean.getAmount());
        holder.textSpe.setText(productVOListBean.getSpec());
        if(productVOListBean.getOldPrice()!=null) {
            holder.oldPrice.setText(productVOListBean.getOldPrice()+"");
        }
        holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.oldPrice.getPaint().setAntiAlias(true);//抗锯齿
        holder.textTitle.setText(productVOListBean.getName());
        holder.textTitle.setText(productVOListBean.getName());
        if(productVOListBean.getProdTypeUrl()!=null&&!productVOListBean.getProdTypeUrl().equals("")) {
            holder.imageIcon.setVisibility(View.VISIBLE);
            Glide.with(context).load(productVOListBean.getProdTypeUrl()).into(holder.imageIcon);
        }else {
            holder.imageIcon.setVisibility(View.GONE);
        }
        Glide.with(context).load(productVOListBean.getPicUrl()).into(holder.imageView);

        additionVOList1.clear();
        additionVOList2.clear();

        if(productVOListBean.getAdditionVOList()!=null) {
            for (int i = 0; i < productVOListBean.getAdditionVOList().size(); i++) {
                List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList> additionVOList = productVOListBean.getAdditionVOList();
                if(additionVOList.get(i).getType().equals("1")) {
                    additionVOList1.add(productVOListBean.getAdditionVOList().get(i));
                }else {
                    additionVOList2.add(productVOListBean.getAdditionVOList().get(i));
                }
            }
        }

        holder.rv_given.setLayoutManager(new LinearLayoutManager(context));
        FullGivenConfirmAdapter fullGivenConfirmAdapter = new FullGivenConfirmAdapter(R.layout.item_given,additionVOList2);
        holder.rv_given.setAdapter(fullGivenConfirmAdapter);

        holder.rv_full.setLayoutManager(new LinearLayoutManager(context));
        FullConfirmAdapter fullConfirmAdapter = new FullConfirmAdapter(R.layout.item_full,additionVOList1);
        holder.rv_full.setAdapter(fullConfirmAdapter);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //隐藏
    public void setHideList(List<CartBalanceModel.DataBean.ProductVOListBean> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    //展开
    public void setOpenList(List<CartBalanceModel.DataBean.ProductVOListBean> openList) {
        this.list = openList;
        notifyDataSetChanged();
    }

    //不需要隐藏/展开
    public void setRealList(List<CartBalanceModel.DataBean.ProductVOListBean> realList) {
        this.list = realList;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView Price;
        TextView oldPrice;
        TextView textSpe;
        ImageView imageIcon;
        RecyclerView rv_given;
        RecyclerView rv_full;
        ImageView imageView;
        public MyHolder(View view) {
            super(view);

            textTitle = view.findViewById(R.id.textTitle);
            Price = view.findViewById(R.id.Price);
            oldPrice = view.findViewById(R.id.oldPrice);
            textSpe = view.findViewById(R.id.textSpe);
            imageIcon = view.findViewById(R.id.imageIcon);
            rv_given = view.findViewById(R.id.rv_given);
            rv_full = view.findViewById(R.id.rv_full);
            imageView = view.findViewById(R.id.imageView);



        }
    }

}
