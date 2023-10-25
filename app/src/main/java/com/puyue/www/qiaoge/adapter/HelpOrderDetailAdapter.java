package com.puyue.www.qiaoge.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;

import java.util.List;

public class HelpOrderDetailAdapter extends BaseQuickAdapter<GetOrderDetailModel.DataBean.OrderProdsBean, BaseViewHolder> {


    public HelpOrderDetailAdapter(int layoutResId, @Nullable List<GetOrderDetailModel.DataBean.OrderProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderDetailModel.DataBean.OrderProdsBean item) {
        helper.setText(R.id.tv_num,"订单编号:"+item.orderId);
        if(!TextUtils.isEmpty(item.sendTimeStr)) {
            helper.setText(R.id.tv_time,item.sendTimeStr);
        }
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(mContext));
        HelpOrderDetailInnerAdapter helpOrderDetailInnerAdapter = new HelpOrderDetailInnerAdapter(R.layout.item_help_inner_order_detail,item.products);
        rv_goods.setAdapter(helpOrderDetailInnerAdapter);
        TextView tv_copy = helper.getView(R.id.tv_copy);
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板管理器：
                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData1 = ClipData.newPlainText("Label", item.orderId);
                // 将ClipData内容放到系统剪贴板里。
                cm1.setPrimaryClip(mClipData1);
                AppHelper.showMsg(mContext, "复制成功");
            }
        });
    }
}
