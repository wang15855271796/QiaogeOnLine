package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CouponUseActivity;
import com.puyue.www.qiaoge.activity.home.CouponDetailActivity;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.List;

public class Coupon4Adapter extends BaseQuickAdapter<FullDetailModel.DataBean.SendGiftsBean, BaseViewHolder> {

    boolean isOpen;
    public Coupon4Adapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_num,item.getSendNum());
        RecyclerView rv_role = helper.getView(R.id.rv_role);
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        helper.setText(R.id.tv_spec,item.getUseInfo());
        TextView tv_detail = helper.getView(R.id.tv_detail);

        if(item.getGiftProdUseType().equals("1")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(item.getGiftProdUseType().equals("2")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }


        rv_role.setLayoutManager(new LinearLayoutManager(mContext));
        RoleAdapter roleAdapter = new RoleAdapter(R.layout.item_text1,item.getGiftUseRole());
        rv_role.setAdapter(roleAdapter);

        iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
        ViewGroup.LayoutParams lp = rv_role.getLayoutParams();
        roleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!isOpen) {
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_up);
                    lp.height = DensityUtil.dip2px(RecyclerView.LayoutParams.WRAP_CONTENT,mContext);
                    rv_role.setLayoutParams(lp);
                }else {
                    lp.height = DensityUtil.dip2px(15,mContext);
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                    rv_role.setLayoutParams(lp);
                }
                isOpen = !isOpen;
            }
        });

        lp.height = DensityUtil.dip2px(15,mContext);
        rv_role.setLayoutParams(lp);



        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("1")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else if(item.getGiftProdUseType().equals("2")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else {
                    tv_detail.setVisibility(View.GONE);
                }
            }
        });
    }
}
