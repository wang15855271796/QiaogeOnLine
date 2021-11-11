package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.dialog.FullDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/28
 */
public class MarqueeAdapter extends RecyclerView.Adapter<MarqueeAdapter.ViewHolder>  {

    List<CartGetReductModel.DataBean> data;
    Activity activity;
    CartGetReductModel.DataBean dataBean;
    public void setData(List<CartGetReductModel.DataBean> data, int itemCount, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
        int remainder = data.size() % itemCount;
        if (remainder > 0) {
            for (int i = 0; i >= itemCount; i++) {
                this.data.remove(data.size()-1);
            }
        }
        if (data.size() > itemCount) {
            for (int i = 0; i < itemCount; i++) {
                this.data.add(data.size(), data.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marque_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        dataBean = data.get(position);
        holder.tv_notice.setText(dataBean.getDeductInfo());
        holder.tv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getType()==0) {
                    AppHelper.showFullDialog(activity);
                }else {
                    FullDialog fullDialog = new FullDialog(activity);
                    fullDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_notice;
        TextView tv_desc;
        LinearLayout ll_root;
        public ViewHolder(View view) {
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_notice = (TextView) view.findViewById(R.id.tv_notice);
        }
    }





}
