package com.puyue.www.qiaoge.adapter.cart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.CartGoodsEvent;
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
        ImageView iv_send = helper.getView(R.id.iv_send);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ImageView iv_operate = helper.getView(R.id.iv_operate);
        RoundImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
        CheckBox cb_spec = helper.getView(R.id.cb_spec);
        tv_spec.setText(item.getSpec());

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
        if(item.getSelfOrNot()==0) {
            iv_operate.setImageResource(R.mipmap.icon_operate);
        }else {
            iv_operate.setImageResource(R.mipmap.icon_unoperate);
        }

        tv_title.setText(item.getProductName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);

        RecyclerView rv_price = helper.getView(R.id.rv_price);
        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        CartPriceAdapter cartPriceAdapter = new CartPriceAdapter(R.layout.item_choose_content,item,item.getProductDescVOList(),cartAdapter);
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
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
