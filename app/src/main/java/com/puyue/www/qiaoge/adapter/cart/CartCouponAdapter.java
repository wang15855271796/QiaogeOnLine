package com.puyue.www.qiaoge.adapter.cart;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartTestModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/9
 */
public class CartCouponAdapter extends BaseQuickAdapter<CartTestModel.DataBean.ProdsBeanX.AdditionsBean,BaseViewHolder> {

    public CartCouponAdapter(int layoutResId, @Nullable List<CartTestModel.DataBean.ProdsBeanX.AdditionsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.AdditionsBean item) {
//        TextView tv_name = helper.getView(R.id.tv_name);
//        TextView tv_num = helper.getView(R.id.tv_num);
//        tv_name.setText(item.getName());
//        tv_num.setText(item.getSendNumDesc());
        LinearLayout rl_root = helper.getView(R.id.rl_root);
        ImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_desc = helper.getView(R.id.tv_desc);
//        if(additionVOList.toString().equals(null)) {
//            rl_root.setVisibility(View.GONE);
//            return;
//        }else {
//            rl_root.setVisibility(View.VISIBLE);
//        }
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());

        TextView tv_num = helper.getView(R.id.tv_num);
        tv_num.setText("*"+item.getSendNumDesc());


        if(item.getAdditionFlag()==2) {
            iv_head.setImageResource(R.mipmap.icon_grey_head);
            tv_name.setBackgroundResource(R.mipmap.icon_grey_content);
            tv_name.setTextColor(Color.parseColor("#ffffff"));
            tv_num.setTextColor(Color.parseColor("#B2B2B2"));
            tv_desc.setVisibility(View.VISIBLE);
        }else {
            iv_head.setImageResource(R.mipmap.icon_red_head);
            tv_name.setBackgroundResource(R.mipmap.icon_pink_content);
            tv_name.setTextColor(Color.parseColor("#FF0026"));
            tv_num.setTextColor(Color.parseColor("#FF0026"));
            tv_desc.setVisibility(View.GONE);
        }
    }
}
