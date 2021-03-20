package com.puyue.www.qiaoge.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.FullConfirmAdapter;
import com.puyue.www.qiaoge.adapter.FullGiftAdapter;
import com.puyue.www.qiaoge.adapter.FullGivenConfirmAdapter;
import com.puyue.www.qiaoge.helper.GlideRoundTransform;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.view.GlideModel;
import com.puyue.www.qiaoge.view.LineBreakLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daff
 * @date 2018/9/23.
 * 备注    确认订单
 */
public class ConfirmOrderNewAdapter extends BaseQuickAdapter<CartBalanceModel.DataBean.ProductVOListBean, BaseViewHolder> {
    private ImageView imageView;
    private ImageView imageIcon;
    private TextView oldPrice;
    private LineBreakLayout lineBreakLayout;
    private TextView textSpe;

    ImageView iv_add;
    ImageView iv_flag_add;
    TextView tv_iv_bg;
    RecyclerView rv_full;
    TextView tv_full_desc;
    RecyclerView rv_given;
    List<CartBalanceModel.DataBean.ProductVOListBean> data;
    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList1 = new ArrayList<>();
    List<CartBalanceModel.DataBean.ProductVOListBean.AdditionVOList>additionVOList2 = new ArrayList<>();
    public ConfirmOrderNewAdapter(int layoutResId, @Nullable List<CartBalanceModel.DataBean.ProductVOListBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBalanceModel.DataBean.ProductVOListBean item) {
        tv_full_desc = helper.getView(R.id.tv_full_desc);
        rv_given = helper.getView(R.id.rv_given);
        rv_full = helper.getView(R.id.rv_full);
        tv_iv_bg = helper.getView(R.id.tv_iv_bg);
        imageView = helper.getView(R.id.imageView);
        imageIcon = helper.getView(R.id.imageIcon);
        textSpe = helper.getView(R.id.textSpe);
        oldPrice = helper.getView(R.id.oldPrice);
        lineBreakLayout = helper.getView(R.id.lineBreakLayout);
        iv_add = helper.getView(R.id.iv_item_cart_img_add);
        iv_flag_add = helper.getView(R.id.iv_flag_add);

        lineBreakLayout.removeAllViews();

        additionVOList1 = new ArrayList<>();
        additionVOList2 = new ArrayList<>();

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

        // 添加 什么规格购买了多少
        for (int i = 0; i < item.getProductDescVOList().size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setTextColor(Color.parseColor("#939393"));
            tv.setTextSize(11);
            if (!TextUtils.isEmpty(item.getProductDescVOList().get(i).getNewDesc())) {
                tv.setText(item.getProductDescVOList().get(i).getNewDesc() + "  ");
            } else {
                tv.setText("");
            }

            lineBreakLayout.addView(tv);

        }
        if (item.getBusinessType() == 11) {
            if (item.getOldPrice() != null) {
                oldPrice.setVisibility(View.VISIBLE);
                oldPrice.setText(item.getOldPrice() + "");
                if (item.getOldPrice() != null) {
                    oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                }
                textSpe.setVisibility(View.VISIBLE);
            } else {
                oldPrice.setVisibility(View.GONE);
            }
        } else if (item.getBusinessType() == 2) { // 有原价 有规格
            if (item.getOldPrice() != null) {
                oldPrice.setVisibility(View.VISIBLE);
                oldPrice.setText(String.valueOf(item.getOldPrice()));
                if (item.getOldPrice() != null) {
                    oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                }
                textSpe.setVisibility(View.VISIBLE);
            } else {
                oldPrice.setVisibility(View.GONE);
            }
        } else if (item.getBusinessType() == 1) {  // 没有原价 有规格
            oldPrice.setVisibility(View.GONE);
            textSpe.setVisibility(View.VISIBLE);

        } else if (item.getBusinessType() == 3) {// 没有原价 没有规格
            oldPrice.setVisibility(View.GONE);
            textSpe.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.textTitle, item.getName());
        }
        if (StringHelper.notEmptyAndNull(item.getProdTypeUrl())) {
            GlideModel.disPlayError(mContext, item.getProdTypeUrl(), imageIcon);

           /* Glide.with(mContext).load(item.getProdTypeUrl()).
                    transform(new GlideRoundTransform(mContext, 3)).into(imageIcon);*/
        }

        if (StringHelper.notEmptyAndNull(item.getPicUrl())) {
            GlideModel.disPlayError(mContext, item.getPicUrl(), imageView);
          /*  Glide.with(mContext).load(item.getPicUrl()).crossFade().transform(new GlideRoundTransform(mContext, 3)).
                    error(R.mipmap.icon_default_rec).into(imageView);*/
        }

        if (!TextUtils.isEmpty(item.getSpec())) {
            helper.setText(R.id.textSpe, item.getSpec());
        }
        if (!TextUtils.isEmpty(item.getAmount())) {
            helper.setText(R.id.Price, "¥" + item.getAmount());
        }
        if (item.cartAdditionProductVOList!=null&&item.cartAdditionProductVOList.size()>0) {
            Glide.with(mContext).load(item.cartAdditionProductVOList.get(0).flagUrl).into(iv_flag_add);

            helper.getView(R.id.rl_cart_item_add).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_item_cart_standard_add, item.cartAdditionProductVOList.get(0).spec);
            helper.setText(R.id.tv_cart_stock_add, "库存" + item.cartAdditionProductVOList.get(0).inventory);
            helper.setText(R.id.tv_item_cart_title_add, item.cartAdditionProductVOList.get(0).name);
            Glide.with(mContext).load(item.cartAdditionProductVOList.get(0).picUrl).into(iv_add);


            if (item.cartAdditionProductVOList.get(0).additionFlag == 1) {
                tv_iv_bg.setVisibility(View.GONE);
                helper.getView(R.id.tv_add).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_add_two).setVisibility(View.GONE);
                helper.setText(R.id.tv_add, item.cartAdditionProductVOList.get(0).additionNum);
            } else {
                tv_iv_bg.setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_add_two).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_add).setVisibility(View.GONE);
                helper.setText(R.id.tv_add_two, item.cartAdditionProductVOList.get(0).additionNum);
                tv_iv_bg.getBackground().setAlpha(70);

            }


        } else {
            helper.getView(R.id.rl_cart_item_add).setVisibility(View.GONE);
        }


    }
}
