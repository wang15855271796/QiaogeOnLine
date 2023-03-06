package com.puyue.www.qiaoge.adapter.mine;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;

import java.util.List;

/**
 * Created by ${daff}
 * on 2018/10/20
 * 备注 改版后的 订单详情
 */
public class NewOrderDetailAdapter extends BaseQuickAdapter<GetOrderDetailModel.DataBean.OrderProdsBean, BaseViewHolder> {

    String orderId;
    RecyclerView rv1;
    TextView tv_title;
    TextView tv_time;
    TextView tv_num;
    RelativeLayout rl;
    TextView tv_copy;
    Activity mActivity;
    public NewOrderDetailAdapter(Activity mActivity,int layoutResId, @Nullable List<GetOrderDetailModel.DataBean.OrderProdsBean> data, String orderId) {
        super(layoutResId, data);
        this.orderId = orderId;
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderDetailModel.DataBean.OrderProdsBean item) {
        tv_time = helper.getView(R.id.tv_time);
        tv_num = helper.getView(R.id.tv_num);
        rv1 = helper.getView(R.id.rv1);
        rl = helper.getView(R.id.rl);
        tv_copy = helper.getView(R.id.tv_copy);
        tv_num.setText("订单编号:"+item.orderId);
        if(null!=item.sendTimeStr && !item.sendTimeStr.equals("")) {
            tv_time.setText(item.sendTimeStr);
            tv_time.setVisibility(View.VISIBLE);
        }else {
            tv_time.setVisibility(View.GONE);
        }

        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm1 = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData1 = ClipData.newPlainText("Label", item.orderId);
                // 将ClipData内容放到系统剪贴板里。
                cm1.setPrimaryClip(mClipData1);
                AppHelper.showMsg(mContext, "复制成功");
            }
        });

        rv1.setLayoutManager(new LinearLayoutManager(mContext));
        OrderAdapter orderAdapter = new OrderAdapter(R.layout.item_order_good, item.products,item.orderId);
        rv1.setAdapter(orderAdapter);

    }



}