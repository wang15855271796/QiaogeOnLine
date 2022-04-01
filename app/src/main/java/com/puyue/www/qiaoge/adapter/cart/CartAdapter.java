package com.puyue.www.qiaoge.adapter.cart;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.FullActiveActivity;
import com.puyue.www.qiaoge.fragment.cart.UpdateEvent;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.view.Arith;
import com.puyue.www.qiaoge.view.SlideRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class CartAdapter extends BaseQuickAdapter<CartTestModel.DataBean.ProdsBeanX, BaseViewHolder> {

    List<CartTestModel.DataBean.ProdsBeanX.AdditionsBean> addition1 = new ArrayList<>();
    List<CartTestModel.DataBean.ProdsBeanX.AdditionsBean> addition2 = new ArrayList<>();
    List<CartTestModel.DataBean.ProdsBeanX> data;
    CartGoodsAdapter cartGoodsAdapter;
    private OnRefreshListener mOnRefreshListener;
    boolean isSelect;
    private Onclick onclick;

    public CartAdapter(int layoutResId, @Nullable List<CartTestModel.DataBean.ProdsBeanX> data,Onclick onclick) {
        super(layoutResId, data);
        this.data = data;
        this.onclick = onclick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX item) {
        TextView tv_tip = helper.getView(R.id.tv_tip);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        RelativeLayout rl_tip = helper.getView(R.id.rl_tip);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        RecyclerView rv_coupon = helper.getView(R.id.rv_coupon);
        TextView tv_desc = helper.getView(R.id.tv_desc);

        if(item.getSendFullType()==0) {
            tv_desc.setText("去凑单");
        }else {
            tv_desc.setText("查看活动");
        }
//        if(mOnRefreshListener != null){
//            for(int i = 0;i < data.size(); i++){
//                if(!data.get(i).isSelect()){
//                    isSelect = false;
//                    break;
//                }else{
//                    isSelect = true;
//                }
//            }
//            mOnRefreshListener.onRefresh(isSelect);
//        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.deleteItem(helper.getAdapterPosition(),item);
                }
            }
        });

        rl_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FullActiveActivity.class);
                intent.putExtra("fullId",item.getFullId());
                mContext.startActivity(intent);
            }
        });
        if(item.getFullType()==1) {
            tv_tip.setText(item.getTips());
            rl_tip.setVisibility(View.VISIBLE);
        }else {
            rl_tip.setVisibility(View.GONE);
        }

        addition1 = new ArrayList<>();
        addition2 = new ArrayList<>();

        if(item.getAdditions()!=null) {
            for (int i = 0; i < item.getAdditions().size(); i++) {
                if(item.getAdditions().get(i).getType()==0) {
                    addition1.add(item.getAdditions().get(i));
                }else {
                    addition2.add(item.getAdditions().get(i));
                }
            }
        }

        CartGivenAdapter givenAdapter = new CartGivenAdapter(R.layout.item_given,addition1);
        rv_goods.setAdapter(givenAdapter);
        rv_goods.setLayoutManager(new LinearLayoutManager(mContext));

        CartCouponAdapter cartCouponAdapter = new CartCouponAdapter(R.layout.item_full,addition2);
        rv_coupon.setAdapter(cartCouponAdapter);
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));

        SlideRecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        cartGoodsAdapter = new CartGoodsAdapter(R.layout.item_goods,this,data,item,item.getProds(),mOnRefreshListener);
        recyclerView.setAdapter(cartGoodsAdapter);
        cartGoodsAdapter.notifyDataSetChanged();
    }

    //是否全选
    public void setSelectAll(boolean isChoose) {
        for (int i = 0; i < data.size(); i++) {
            List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prods = data.get(i).getProds();
            data.get(i).setSelect(isChoose);
            for (int j = 0; j < prods.size(); j++) {
                prods.get(j).setSelected(isChoose);
            }
        }
        notifyDataSetChanged();
        EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
    }


    public String getAllPrice() {
        BigDecimal allPrice =new BigDecimal("0");
        for (int i = 0; i < data.size(); i++) {
            List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prods1 = data.get(i).getProds();
            for (int j = 0; j < prods1.size(); j++) {
                if(prods1.get(j).isSelected()) {
                    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> productDescVOList = prods1.get(j).getProductDescVOList();
                    for (int k = 0; k < productDescVOList.size(); k++) {
                        BigDecimal cartNum = new BigDecimal(productDescVOList.get(k).getProductNum());
                        double totals = Arith.mul(Double.parseDouble(productDescVOList.get(k).getPrice()),cartNum);
                        allPrice = allPrice.add(BigDecimal.valueOf(totals));
                    }
                }
            }
        }
        return allPrice.toString();
    }

    public interface OnRefreshListener{
        void onRefresh(boolean isSelect);
    }

    public void setRefreshListener(OnRefreshListener mOnRefreshListener){
        this.mOnRefreshListener = mOnRefreshListener;
    }

    public interface Onclick {
        void deleteItem(int pos,CartTestModel.DataBean.ProdsBeanX prodsBeanX);
    }
}
