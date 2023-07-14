package com.puyue.www.qiaoge.adapter.cart;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.event.DeleteGoodsEvent;
import com.puyue.www.qiaoge.fragment.cart.UpdateEvent;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/9
 */
public class CartGoodsAdapter extends BaseQuickAdapter<CartTestModel.DataBean.ProdsBeanX.ProdsBean,BaseViewHolder> {

    CartAdapter.OnRefreshListener mOnRefreshListener;
    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> data;
    CartTestModel.DataBean.ProdsBeanX item;
    List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX;
    CartAdapter cartAdapter;
    boolean isSelect = false;
    public CartGoodsAdapter(int layoutResId, CartAdapter cartAdapter, List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX, CartTestModel.DataBean.ProdsBeanX item, @Nullable List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> data, CartAdapter.OnRefreshListener mOnRefreshListener) {
        super(layoutResId, data);
        this.data = data;
        this.cartAdapter = cartAdapter;
        this.item = item;
        this.prodsBeanX = prodsBeanX;
        this.mOnRefreshListener = mOnRefreshListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.ProdsBean item) {
        TextView tv_delete = helper.getView(R.id.tv_delete);
        TextView tv_buy_tips = helper.getView(R.id.tv_buy_tips);
        ImageView iv_send = helper.getView(R.id.iv_send);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        ImageView iv_operate = helper.getView(R.id.iv_operate);
        RoundImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
        CheckBox cb_spec = helper.getView(R.id.cb_spec);
        tv_spec.setText("规格:"+item.getSpec());


        if(!TextUtils.isEmpty(item.getProdBuyTips())) {
            tv_buy_tips.setText(item.getProdBuyTips());
        }
        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        if(item.getFlagUrl()!=null&&item.getFlagUrl()!="") {
            Glide.with(mContext).load(item.getFlagUrl()).into(iv_icon);
        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteGoodsEvent(helper.getAdapterPosition(),item));
            }
        });


        if(mOnRefreshListener != null){
            if(prodsBeanX.size()>1) {
                for (int i = 0; i < prodsBeanX.size(); i++) {
                    for (int j = 0; j < data.size(); j++) {
                        if(!data.get(j).isSelected()){
                            isSelect = false;
                            break;
                        }else{
                            isSelect = true;
                        }
                    }
                }

            }else {
                for(int i = 0;i < data.size(); i++){
                    if(!data.get(i).isSelected()){
                        isSelect = false;
                        data.get(i).setSelected(false);
                        break;
                    }else{
                        data.get(i).setSelected(true);
                        isSelect = true;
                    }
                }
            }
            mOnRefreshListener.onRefresh(isSelect);
        }


        if(item.getSelfProd()!=null&&!item.getSelfProd().equals("")) {
            Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        }

        tv_title.setText(item.getProductName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);

        RecyclerView rv_price = helper.getView(R.id.rv_price);
        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        CartPriceAdapter cartPriceAdapter = new CartPriceAdapter(R.layout.item_choose_content1,item,item.getProductDescVOList(),cartAdapter,prodsBeanX);
        rv_price.setAdapter(cartPriceAdapter);

        cb_item_out.setChecked(item.isSelected());
        cb_spec.setChecked(item.isSelected());
        cb_item_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()) {
                    item.setSelected(isChecked);
                    cb_spec.setChecked(isChecked);
                }

                for (CartTestModel.DataBean.ProdsBeanX prodsBeanX: prodsBeanX) {
                    prodsBeanX.setSelect(isChecked);
                }

                cartAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new UpdateEvent(cartAdapter.getAllPrice()));
            }
        });

        cb_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isSelected()) {
                    item.setSelected(false);
                    cb_spec.setChecked(false);
                    cb_item_out.setChecked(false);
                }else {
                    item.setSelected(true);
                    cb_item_out.setChecked(true);
                    cb_spec.setChecked(true);
                }
            }
        });

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getBusinessType()==1) {
                    Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                    intent.putExtra("activeId",item.getProductMainId());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    mContext.startActivity(intent);
                }else if(item.getBusinessType()==2){
                    Intent intent = new Intent(mContext,SeckillGoodActivity.class);
                    intent.putExtra("activeId",item.getBusinessId());
                    intent.putExtra("num","-1");
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    mContext.startActivity(intent);

                }else {
                    Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                    intent.putExtra("activeId",item.getBusinessId());
                    intent.putExtra("businessType",item.getBusinessType());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
