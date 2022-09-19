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
import com.puyue.www.qiaoge.dialog.CouponCartListDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.CouponFullListDialog;
import com.puyue.www.qiaoge.dialog.CouponListDialog;
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
        ImageView iv_head = helper.getView(R.id.iv_head);
        LinearLayout rl_root = helper.getView(R.id.rl_root);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_num.setText("*"+item.getSendNumDesc());

        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CouponCartListDialog couponFullListDialog = new CouponCartListDialog(mContext,item.getGiftPoolNo(),item.getType(),item.getName());
                couponFullListDialog.show();
            }
        });

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
