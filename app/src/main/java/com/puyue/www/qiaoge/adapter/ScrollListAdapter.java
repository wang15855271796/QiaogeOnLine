package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.scrollview.ViewHolder;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScrollListAdapter extends RecyclerView.Adapter<ScrollListAdapter.MarqueHolder> {
    List<CartGetReductModel.DataBean> scrollList;
    FragmentActivity mActivity;
    private LayoutInflater mLayoutInflater;
    CartGetReductModel.DataBean dataBean;
    public ScrollListAdapter(List<CartGetReductModel.DataBean> scrollList, FragmentActivity mActivity) {
        this.scrollList = scrollList;
        this.mActivity = mActivity;
        mLayoutInflater= LayoutInflater.from(mActivity);
    }

    @NonNull
    @Override
    public MarqueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.marque_item,parent,false);
        return new MarqueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarqueHolder holder, int position) {
        dataBean = scrollList.get(position % scrollList.size());
        holder.tv_notice.setText(dataBean.getDeductInfo());
//        if(dataBean.getType()==0) {
//            //满减
//            holder.tv_desc.setText("满减");
//        }else {
//            holder.tv_desc.setText("满赠");
//        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFullDetail();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class MarqueHolder extends RecyclerView.ViewHolder {
        TextView tv_notice;
        TextView tv_desc;
        LinearLayout ll_root;
        public MarqueHolder(View view) {
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_notice = (TextView) view.findViewById(R.id.tv_notice);
        }
    }

    /**
     *满减详情
     */
    private void getFullDetail() {
        CartListAPI.getFullDetail(mActivity,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartFullModel>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CartFullModel cartFullModel) {
                        if(cartFullModel.getCode()==1) {
                            if(cartFullModel.getData()!=null&&cartFullModel.getData().size()>0) {
                                if(dataBean.getType()==0) {
                                    AppHelper.showFullDialog(mActivity);
                                }else {
                                    for (int i = 0; i < cartFullModel.getData().size(); i++) {
                                        List<CartFullModel.DataBean> data = cartFullModel.getData();
                                        for (int j = 0; j < data.size(); j++) {
                                            if(data.get(j).getSendProds()!=null&&data.get(j).getSendProds().size()>0) {

                                            }
                                        }
                                    }
//                                    if(cartFullModel.getData().get(0)) {
//
//                                    }
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,cartFullModel.getMessage());
                        }

                    }
                });
    }

}
